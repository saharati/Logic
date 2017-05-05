package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import components.ObjectsPanel;
import components.SettingsWindow;
import components.Point;
import objects.LogicObject;

public final class ObjectsListener extends MouseAdapter
{
	private LinkedList<Point> points = new LinkedList<Point>();
	@Override
	public void mousePressed(final MouseEvent e)
	{
		final int windowWidth = ObjectsPanel.getInstance().getWidth();
		final int windowHeight = ObjectsPanel.getInstance().getHeight();
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
				else{
					SettingsWindow.getInstance().edit(object);
				}
				
				return;
			}
		}
		points.add(new Point(e.getX(), e.getY()));
		for(int i=0; i < points.size() - 1; i++){//make no option for too much close rectangles
			if(Math.abs(clickedX - points.get(i).getX()) < 1.3 * objectWidth && Math.abs(clickedY - points.get(i).getY()) < 1.3 * objectHeight){
				points.removeLast();
				return;
			}
		}
		
		final int xPercent = (int) (((double) clickedX / windowWidth) * 100);
		final int yPercent = (int) (((double) clickedY / windowHeight) * 100);
		final LogicObject object = new LogicObject(xPercent, yPercent);
		SettingsWindow.getInstance().create(object);//make new circle
	}
	
	@Override
	public void mouseMoved(final MouseEvent e)
	{
		ObjectsPanel.getInstance().getCurrentObject().setLocation(e.getX(), e.getY());
		ObjectsPanel.getInstance().repaint();
	}
}