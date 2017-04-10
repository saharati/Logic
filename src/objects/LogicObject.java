package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class LogicObject implements Cloneable
{
	private final char _letter;
	private final int _xPercent;
	private final int _yPercent;
	private final List<LogicObject> _attackList = new ArrayList<>();
	private int _health = 1;
	private int _damage = 1;
	private int _damageDone;
	
	public LogicObject(final char letter, final int xPercent, final int yPercent)
	{
		_letter = letter;
		_xPercent = xPercent;
		_yPercent = yPercent;
	}
	
	public char getLetter()
	{
		return _letter;
	}
	
	public int getXPercent()
	{
		return _xPercent;
	}
	
	public int getYPercent()
	{
		return _yPercent;
	}
	
	public int getX(final int wholeX)
	{
		return (int) ((_xPercent / 100d) * wholeX);
	}
	
	public int getY(final int wholeY)
	{
		return (int) ((_yPercent / 100d) * wholeY);
	}
	
	public List<LogicObject> getAttackList()
	{
		return _attackList;
	}
	
	public int getHealth()
	{
		return _health;
	}
	
	public void setHealth(final int health)
	{
		_health = health;
	}
	
	public int getDamage()
	{
		return _damage;
	}
	
	public void setDamage(final int damage)
	{
		_damage = damage;
	}
	
	public int getDamageDone()
	{
		return _damageDone;
	}
	
	public void setDamageDone(final int damageDone)
	{
		_damageDone = damageDone;
	}
	
	public boolean hasAliveTargets()
	{
		for (final LogicObject target : _attackList)
			if (target.getHealth() > 0)
				return true;
		
		return false;
	}
	
	public void doAttack(final Set<LogicObject> used)
	{
		used.add(this);
		
		for (final LogicObject target : _attackList)
		{
			final int damage = Math.min(target.getHealth(), _damage);
			_damageDone += damage;
			
			target.setHealth(target.getHealth() - damage);
			if (target.getHealth() > 0)
				target.doAttack(used);
		}
	}
	
	@Override
	public LogicObject clone()
	{
		final LogicObject logicObject = new LogicObject(_letter, _xPercent, _yPercent);
		logicObject.setHealth(_health);
		logicObject.setDamage(_damage);
		
		return logicObject;
	}
}