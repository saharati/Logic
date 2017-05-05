package objects;

import java.util.ArrayList;
import java.util.List;

public final class LogicObject
{
	private final int _xPercent;
	private final int _yPercent;
	private final List<LogicObject> _targets = new ArrayList<>();
	
	private String _name;
	private int _life;
	private int _attack;
	
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
	
	public List<LogicObject> getTargets()
	{
		return _targets;
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
	}
	
	public int getAttack()
	{
		return _attack;
	}
	
	public void setAttack(final int attack)
	{
		_attack = attack;
	}
}