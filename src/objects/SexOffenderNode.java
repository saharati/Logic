package objects;

import java.util.HashMap;
import java.util.Map;

public final class SexOffenderNode
{
	public static final int M = 1;
	
	private final String _name;
	private final int _life;
	private final int _xPercent;
	private final int _yPercent;
	private final Map<SexOffenderNode, Integer> _attackers = new HashMap<>();
	private final Map<SexOffenderNode, Integer> _targets = new HashMap<>();
	private int _lifeAfterAttack;
	
	public SexOffenderNode(final String name, final int life, final int xPercent, final int yPercent)
	{
		_name = name;
		_life = life;
		_lifeAfterAttack = life;
		_xPercent = xPercent;
		_yPercent = yPercent;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public int getLife()
	{
		return _life;
	}
	
	public int getX(final int wholeX)
	{
		return (int) ((_xPercent / 100d) * wholeX);
	}
	
	public int getY(final int wholeY)
	{
		return (int) ((_yPercent / 100d) * wholeY);
	}
	
	public Map<SexOffenderNode, Integer> getAttackers()
	{
		return _attackers;
	}
	
	public Map<SexOffenderNode, Integer> getTargets()
	{
		return _targets;
	}
	
	public int getLifeAfterAttack()
	{
		return _lifeAfterAttack;
	}
	
	public void setLifeAfterAttack()
	{
		final int beta = _attackers.entrySet().stream().filter(e -> e.getKey().getLifeAfterAttack() > 0).count() > M ? 1 : 0;
		final int sum = _attackers.entrySet().stream().filter(e -> e.getKey().getLifeAfterAttack() > 0).mapToInt(e -> e.getValue()).sum();
		
		if (_lifeAfterAttack > 0)
			_lifeAfterAttack = _life - (beta + sum);
		if (_lifeAfterAttack < 0)
			_lifeAfterAttack = 0;
	}
}