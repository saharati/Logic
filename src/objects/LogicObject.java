package objects;

import java.util.ArrayList;
import java.util.List;

public final class LogicObject
{
	private final int _xPercent;
	private final int _yPercent;
	private final List<LogicObject> _attackList = new ArrayList<>();
	
	private String _name;
	private int _health;
	private int _damage;
	
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
	
	public List<LogicObject> getAttackList()
	{
		return _attackList;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(final String name)
	{
		_name = name;
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
}