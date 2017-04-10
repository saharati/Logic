package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import components.ObjectsPanel;
import components.SettingsWindow;
import objects.LogicObject;

public final class ObjectsListener extends MouseAdapter
{
	@Override
	public void mousePressed(final MouseEvent e)
	{
		final int windowWidth = ObjectsPanel.getInstance().getRootPane().getWidth();
		final int windowHeight = ObjectsPanel.getInstance().getRootPane().getHeight();
		final int diameter = Math.min(windowWidth, windowHeight) / 10;
		final int clickedX = e.getX();
		final int clickedY = e.getY();
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
		{
			final int startX = object.getX(windowWidth);
			final int startY = object.getY(windowHeight);
			final int endX = startX + diameter;
			final int endY = startY + diameter;
			if (clickedX > startX && clickedX < endX && clickedY > startY && clickedY < endY)
			{
				if (e.isControlDown())
				{
					ObjectsPanel.getInstance().removeObject(object);
					ObjectsPanel.getInstance().repaint();
				}
				else
					SettingsWindow.getInstance().edit(object);
				return;
			}
		}
		
		if (ObjectsPanel.getInstance().getObjects().size() == 26)
			JOptionPane.showMessageDialog(null, "You have reached the maximum amount of objects that can be added.", "Error", JOptionPane.ERROR_MESSAGE);
		else
		{
			final int xPercent = (int) (((double) clickedX / windowWidth) * 100);
			final int yPercent = (int) (((double) clickedY / windowHeight) * 100);
			ObjectsPanel.getInstance().addObject(xPercent, yPercent);
			ObjectsPanel.getInstance().repaint();
		}
	}
	
	@Override
	public void mouseMoved(final MouseEvent e)
	{
		ObjectsPanel.getInstance().getCurrentObject().setLocation(e.getX(), e.getY());
		ObjectsPanel.getInstance().repaint();
	}
}