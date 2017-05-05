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
	private static final int FONT_DIVISION = 26;
	
	public static final int ELEMENTS_PER_ROW = 5;
	public static final int ELEMENTS_PER_COL = 5;
	
	private final List<LogicObject> _objects = new ArrayList<>();
	private final Point _currentObject = new Point();
	
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
	
	public boolean canPlaceObjectAt(final int clickedX, final int clickedY)
	{
		final int windowWidth = getWidth();
		final int windowHeight = getHeight();
		final int objectWidth = windowWidth / ELEMENTS_PER_ROW;
		final int objectHeight = windowHeight / ELEMENTS_PER_COL;
		for (final LogicObject object : _objects)
			if (Math.abs(clickedX - object.getX(windowWidth)) < 1.3 * objectWidth && Math.abs(clickedY - object.getY(windowHeight)) < 1.3 * objectHeight)
				return false;
		
		return true;
	}
	
	public void addObject(final LogicObject object)
	{
		String text = "Adding " + object.getName();
		if (object.getLife() > 0 && object.getAttack() > 0)
			text += " with life = " + object.getLife() + " and attack = " + object.getAttack();
		else if (object.getLife() > 0)
			text += " with life = " + object.getLife();
		else if (object.getAttack() > 0)
			text += " with attack = " + object.getAttack();
		if (!object.getTargets().isEmpty())
		{
			text += " targets: ";
			for (final LogicObject target : object.getTargets())
				text += target.getName() + ", ";
			text = text.substring(0, text.length() - 2);
		}
		
		RunPanel.getInstance().addText(text);
		
		_objects.add(object);
		
		repaint();
	}
	
	public void removeObject(final LogicObject object)
	{
		RunPanel.getInstance().addText("Removing " + object.getName());
		
		_objects.remove(object);
		
		for (final LogicObject obj : _objects)
		{
			if (obj.getTargets().contains(object))
				obj.getTargets().remove(object);
			if (obj.getAttackedBy().contains(object))
				obj.getAttackedBy().remove(object);
		}
	}
	
	public Point getCurrentObject()
	{
		return _currentObject;
	}
	
	private void drawArrow(final Graphics g, int x1, int y1, int x2, int y2, boolean self)
	{
		final Graphics2D g2d = (Graphics2D) g.create();
		final int objectWidth = getWidth() / ELEMENTS_PER_ROW;
		final int objectHeight = getHeight() / ELEMENTS_PER_COL;
		
		int dx, dy, len;
		if (self)
		{
			dx = x2 - x1;
			dy = y2 - y1;
			len = (int) (Math.sqrt(dx * dx + dy * dy) - objectWidth / 2);
		}
		else
		{
			if (x1 < x2)
			{
				if (Math.abs(x1 - x2) > objectWidth / 2 + 20 && Math.abs(y1 - y2) < 7 * objectHeight / 4 )
				{
					dx = x2 - x1 - objectWidth / 2 - 5;
					dy = y2 - y1;
				}
				else
				{
					if (y1 < y2)
					{
						dx = x2 - x1;
						dy = y2 - y1 - objectHeight / 2 - 5;
					}
					else
					{
						dx = x2 - x1;
						dy = y2 - y1 + objectHeight / 2 + 5;
					}
				}
			}
			else
			{
				if (Math.abs(x1 - x2) > objectWidth / 2 + 20 && Math.abs(y1 - y2) < 7 * objectHeight / 4 )
				{
					dx = x2 - x1 + objectWidth / 2 + 5;
					dy = y2 - y1;
				}
				else
				{
					if (y1 < y2)
					{
						dx = x2 - x1;
						dy = y2 - y1 - objectHeight / 2 - 5;
					}
					else
					{
						dx = x2 - x1;
						dy = y2 - y1 + objectHeight / 2 + 5;
					}
				}
			}
			
			len = (int) Math.sqrt(dx * dx + dy * dy);
		}
		
		final double angle = Math.atan2(dy, dx);
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
		
		final int windowWidth = getWidth();
		final int windowHeight = getHeight();
		final int objectWidth = windowWidth / ELEMENTS_PER_ROW;
		final int objectHeight = windowHeight / ELEMENTS_PER_COL;
		final Font font = new Font("Arial", Font.BOLD, Math.min(windowWidth, windowHeight) / FONT_DIVISION);
		final FontMetrics metrics = g.getFontMetrics(font);
		
		g.setFont(font);
		
		for (final LogicObject object : _objects)
		{
			final int startX = object.getX(windowWidth);
			final int startY = object.getY(windowHeight);
			final int centerX = startX + (objectWidth / 2);
			final int centerY = startY + (objectHeight / 2);
			
			if (object.isAttackingNow())
				g.setColor(Color.RED);
			else
				g.setColor(Color.GRAY);
			for (final LogicObject target : object.getTargets())
			{
				if (object == target)
				{
					final int newStartX = startX - (objectWidth / 2);
					final int newStartY = startY + (objectHeight / 2);
					
					g.drawOval(newStartX, newStartY, objectWidth, objectHeight / 3);
					drawArrow(g, centerX, centerY, centerX, centerY, true);
				}
				else
				{
					final int targetX = target.getX(windowWidth) + (objectWidth / 2);
					final int targetY = target.getY(windowHeight) + (objectHeight / 2);
					
					drawArrow(g, centerX, centerY, targetX, targetY, false);
				}
			}
			
			g.setColor(Color.GREEN.darker());
			g.fillRect(startX, startY, objectWidth, objectHeight);
			
			final int textStartX = startX + 10;
			int textStartY = startY + metrics.getHeight();
			
			g.setColor(Color.WHITE);
			g.drawString("Name: " + object.getName(), textStartX, textStartY);
			textStartY += metrics.getHeight();
			if (object.getLife() > 0)
			{
				g.setColor(Color.BLUE);
				g.drawString("Life: " + object.getLife(), textStartX, textStartY);
				textStartY += metrics.getHeight();
			}
			if (object.getAttack() > 0)
			{
				g.setColor(Color.RED);
				g.drawString("Attack: " + object.getAttack(), textStartX, textStartY);
			}
		}
		
		g.setColor(Color.GREEN.darker());
		if (SettingsWindow.getInstance().isVisible())
		{
			final LogicObject object = SettingsWindow.getInstance().getObject();
			if (!_objects.contains(object))
				g.fillRect(object.getX(windowWidth), object.getY(windowHeight), objectWidth, objectHeight);
		}
		else
			g.fillRect(_currentObject.x, _currentObject.y, objectWidth, objectHeight);
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