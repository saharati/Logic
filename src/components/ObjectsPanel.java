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
import java.util.Map.Entry;

import javax.swing.JPanel;

import listeners.ObjectsListener;
import objects.SexOffenderNode;

public final class ObjectsPanel extends JPanel
{
	private static final long serialVersionUID = -3000147490780419817L;
	private static final int FONT_DIVISION = 26;
	
	public static final int ELEMENTS_PER_ROW = 5;
	public static final int ELEMENTS_PER_COL = 5;
	
	private final List<SexOffenderNode> _objects = new ArrayList<>();
	private final Point _currentObject = new Point();
	
	private ObjectsPanel()
	{
		final ObjectsListener ol = new ObjectsListener();
		
		addMouseListener(ol);
		addMouseMotionListener(ol);
	}
	
	public List<SexOffenderNode> getObjects()
	{
		return _objects;
	}
	
	public boolean canPlaceObjectAt(final int clickedX, final int clickedY)
	{
		final int windowWidth = getWidth();
		final int windowHeight = getHeight();
		final int objectWidth = windowWidth / ELEMENTS_PER_ROW;
		final int objectHeight = windowHeight / ELEMENTS_PER_COL;
		for (final SexOffenderNode object : _objects)
			if (Math.abs(clickedX - object.getX(windowWidth)) < 1.3 * objectWidth && Math.abs(clickedY - object.getY(windowHeight)) < 1.3 * objectHeight)
				return false;
		
		return true;
	}
	
	public void addObject(final SexOffenderNode object)
	{
		RunPanel.getInstance().addText(object.getName() + " has been added to the network with " + object.getLife() + " life.");
		
		_objects.add(object);
		
		repaint();
	}
	
	public Point getCurrentObject()
	{
		return _currentObject;
	}
	
	private void drawArrow(final Graphics g, final int x1, final int y1, final int x2, final int y2, final boolean self, final int attack)
	{
		// TODO: draw number near arrow:
		// g.setColor(Color.RED);
		// g.drawString(String.valueOf(attack), x, y);
		
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
		
		for (final SexOffenderNode object : _objects)
		{
			final int startX = object.getX(windowWidth);
			final int startY = object.getY(windowHeight);
			final int centerX = startX + (objectWidth / 2);
			final int centerY = startY + (objectHeight / 2);
			
			g.setColor(Color.GRAY);
			for (final Entry<SexOffenderNode, Integer> entry : object.getTargets().entrySet())
			{
				final SexOffenderNode target = entry.getKey();
				if (object == target)
				{
					final int newStartX = startX - (objectWidth / 2);
					final int newStartY = startY + (objectHeight / 2);
					
					g.drawOval(newStartX, newStartY, objectWidth, objectHeight / 3);
					drawArrow(g, centerX, centerY, centerX, centerY, true, entry.getValue());
				}
				else
				{
					final int targetX = target.getX(windowWidth) + (objectWidth / 2);
					final int targetY = target.getY(windowHeight) + (objectHeight / 2);
					
					drawArrow(g, centerX, centerY, targetX, targetY, false, entry.getValue());
				}
			}
			
			g.setColor(Color.GREEN.darker());
			g.fillRect(startX, startY, objectWidth, objectHeight);
			
			final int textStartY = startY + metrics.getAscent() + (objectHeight - metrics.getHeight()) / 2;
			int textStartX;
			if (object.getLife() > 0)
				textStartX = startX + (objectWidth - metrics.stringWidth(object.getName() + " (" + object.getLife() + ")")) / 2;
			else
				textStartX = startX + (objectWidth - metrics.stringWidth(object.getName())) / 2;
			
			g.setColor(Color.WHITE);
			g.drawString(object.getName() + " ", textStartX, textStartY);
			textStartX += metrics.stringWidth(object.getName() + " ");
			if (object.getLife() > 0)
			{
				g.setColor(Color.BLUE);
				g.drawString("(" + object.getLife() + ")", textStartX, textStartY);
			}
		}
		
		if (!CreationWindow.getInstance().isVisible())
		{
			g.setColor(Color.GREEN.darker());
			g.fillRect(_currentObject.x, _currentObject.y, objectWidth, objectHeight);
		}
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