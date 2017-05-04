package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		final int objectWidth = windowWidth / ObjectsPanel.ELEMENTS_PER_ROW;
		final int objectHeight = windowHeight / ObjectsPanel.ELEMENTS_PER_COL;
		final int clickedX = e.getX();
		final int clickedY = e.getY();
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
		{
			final int startX = object.getX(windowWidth);
			final int startY = object.getY(windowHeight);
			final int endX = startX + objectWidth;
			final int endY = startY + objectHeight;
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
		
		final int xPercent = (int) (((double) clickedX / windowWidth) * 100);
		final int yPercent = (int) (((double) clickedY / windowHeight) * 100);
		final LogicObject object = new LogicObject(xPercent, yPercent);
		SettingsWindow.getInstance().create(object);
	}
	
	@Override
	public void mouseMoved(final MouseEvent e)
	{
		ObjectsPanel.getInstance().getCurrentObject().setLocation(e.getX(), e.getY());
		ObjectsPanel.getInstance().repaint();
	}
}