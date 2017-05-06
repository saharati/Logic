package objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
	private boolean _isAttackingNow;
	
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
	
	public Set<LogicObject> getValidAttackedBy()
	{
		return _attackedBy.stream().filter(obj -> obj.getLife() == 0 || obj.getLifeAfterAttack() > 0).collect(Collectors.toSet());
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
	
	public boolean isAttackingNow()
	{
		return _isAttackingNow;
	}
	
	public void setIsAttackingNow(final boolean isAttackingNow)
	{
		_isAttackingNow = isAttackingNow;
	}
}