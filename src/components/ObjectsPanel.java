package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import listeners.ObjectsListener;
import objects.LogicObject;

public final class ObjectsPanel extends JPanel
{
	private static final long serialVersionUID = -3000147490780419817L;
	
	private final List<LogicObject> _objects = new ArrayList<>();
	private final Point _currentObject = new Point();
	private final boolean[] _takenLetters = new boolean[26];
	
	private ObjectsPanel()
	{
		final ObjectsListener ol = new ObjectsListener();
		
		addMouseListener(ol);
		addMouseMotionListener(ol);
	}
	
	public List<LogicObject> getObjects()
	{
		return _objects;
	}
	
	public void addObject(final int xPercent, final int yPercent)
	{
		char letter = '\0';
		for (int i = 0;i < _takenLetters.length;i++)
		{
			if (!_takenLetters[i])
			{
				letter = (char) ('A' + i);
				
				_takenLetters[i] = true;
				break;
			}
		}
		
		_objects.add(new LogicObject(letter, xPercent, yPercent));
	}
	
	public void removeObject(final LogicObject object)
	{
		_takenLetters[object.getLetter() - 'A'] = false;
		_objects.remove(object);
		
		for (final LogicObject obj : _objects)
			if (obj.getAttackList().contains(object))
				obj.getAttackList().remove(object);
	}
	
	public Point getCurrentObject()
	{
		return _currentObject;
	}
	
	private void drawArrow(final Graphics g, int x1, int y1, int x2, int y2, int radius)
	{
		final Graphics2D g2d = (Graphics2D) g.create();
		final double dx = x2 - x1;
		final double dy = y2 - y1;
		final double angle = Math.atan2(dy, dx);
		final int len = (int) (Math.sqrt(dx * dx + dy * dy) - radius);
		
		final AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		
		g2d.transform(at);
		g2d.drawLine(0, 0, len, 0);
		g2d.fillPolygon(new int[] {len, len - 10, len - 10, len}, new int[] {0, -10, 10, 0}, 4);
	}
	
	@Override
	protected void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		
		final int windowWidth = getRootPane().getWidth();
		final int windowHeight = getRootPane().getHeight();
		final int diameter = Math.min(windowWidth, windowHeight) / 7;
		final Font font = new Font("Arial", Font.BOLD, windowWidth / 30);
		final FontMetrics metrics = g.getFontMetrics(font);
		
		g.setFont(font);
		
		for (final LogicObject object : _objects)
		{
			final int startX = object.getX(windowWidth);
			final int startY = object.getY(windowHeight);
			final int centerX = startX + (diameter / 2);
			final int centerY = startY + (diameter / 2);
			
			g.setColor(Color.GRAY);
			for (final LogicObject target : object.getAttackList())
			{
				if (object == target)
				{
					final int newStartX = startX - (diameter / 2);
					final int newStartY = startY + (diameter / 2);
					
					g.drawOval(newStartX, newStartY, diameter, diameter / 3);
					drawArrow(g, centerX, centerY, centerX, centerY, diameter / 2);
				}
				else
				{
					final int targetX = target.getX(windowWidth) + (diameter / 2);
					final int targetY = target.getY(windowHeight) + (diameter / 2);
					drawArrow(g, centerX, centerY, targetX, targetY, diameter / 2);
				}
			}
			
			g.setColor(Color.GREEN.darker());
			g.fillOval(startX, startY, diameter, diameter / 2);
			
			final String letter = String.valueOf(object.getLetter());
			final String health = String.valueOf(object.getHealth());
			final String damage = String.valueOf(object.getDamage());
			final int textStartY = startY + metrics.getAscent() + (diameter - metrics.getHeight()) / 2;
			int textStartX = startX + (diameter - metrics.stringWidth(letter + health + damage)) / 2;
			
			g.setColor(Color.BLUE);
			g.drawString(health, textStartX, textStartY);
			textStartX += metrics.stringWidth(health);
			
			g.setColor(Color.WHITE);
			g.drawString(letter, textStartX, textStartY);
			textStartX += metrics.stringWidth(letter);
			
			g.setColor(Color.RED);
			g.drawString(damage, textStartX, textStartY);
		}
		
		g.setColor(Color.GREEN.darker());
		g.fillOval(_currentObject.x, _currentObject.y, diameter, diameter);
	}
	
	public static ObjectsPanel getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		private static final ObjectsPanel INSTANCE = new ObjectsPanel();
	}
}