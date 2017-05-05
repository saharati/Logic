package objects;

import java.util.HashSet;
import java.util.Set;

public final class LogicObject
{
	private final int _xPercent;
	private final int _yPercent;
	private final Set<LogicObject> _targets = new HashSet<>();
	private final Set<LogicObject> _attackedBy = new HashSet<>();
	
	private String _name;
	private int _life;
	private int _attack;
	private int _lifeAfterAttack;
	
	public LogicObject(final int xPercent, final int yPercent)
	{
		_xPercent = xPercent;
		_yPercent = yPercent;
	}
	
	public int getX(final int wholeX)
	{
		return (int) ((_xPercent / 100d) * wholeX);
	}
	
	public int getY(final int wholeY)
	{
		return (int) ((_yPercent / 100d) * wholeY);
	}
	
	public Set<LogicObject> getTargets()
	{
		return _targets;
	}
	
	public Set<LogicObject> getAttackedBy()
	{
		return _attackedBy;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(final String name)
	{
		_name = name;
	}
	
	public int getLife()
	{
		return _life;
	}
	
	public void setLife(final int life)
	{
		_life = life;
		_lifeAfterAttack = life;
	}
	
	public int getAttack()
	{
		return _attack;
	}
	
	public void setAttack(final int attack)
	{
		_attack = attack;
	}
	
	public int getLifeAfterAttack()
	{
		return _lifeAfterAttack;
	}
	
	public void setLifeAfterAttack(final int lifeAfterAttack)
	{
		_lifeAfterAttack = lifeAfterAttack;
	}
}