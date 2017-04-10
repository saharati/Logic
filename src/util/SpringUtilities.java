package util;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SpringLayout.Constraints;

public final class SpringUtilities
{
	public static void printSizes(final Component c)
	{
		System.out.println("minimumSize = " + c.getMinimumSize());
		System.out.println("preferredSize = " + c.getPreferredSize());
		System.out.println("maximumSize = " + c.getMaximumSize());
	}
	
	public static void makeGrid(final Container parent, final int rows, final int cols, final int initialX, final int initialY, final int xPad, final int yPad)
	{
		final SpringLayout layout = (SpringLayout) parent.getLayout();
		final Spring xPadSpring = Spring.constant(xPad);
		final Spring yPadSpring = Spring.constant(yPad);
		final Spring initialXSpring = Spring.constant(initialX);
		final Spring initialYSpring = Spring.constant(initialY);
		final int max = rows * cols;
		
		Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
		Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
		for (int i = 1;i < max;i++)
		{
			final Constraints cons = layout.getConstraints(parent.getComponent(i));
			
			maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
			maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
		}
		
		for (int i = 0;i < max;i++)
		{
			final Constraints cons = layout.getConstraints(parent.getComponent(i));
			
			cons.setWidth(maxWidthSpring);
			cons.setHeight(maxHeightSpring);
		}
		
		Constraints lastCons = null;
		Constraints lastRowCons = null;
		for (int i = 0;i < max;i++)
		{
			final Constraints cons = layout.getConstraints(parent.getComponent(i));
			if (i % cols == 0)
			{
				lastRowCons = lastCons;
				cons.setX(initialXSpring);
			}
			else if (lastCons != null)
				cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
			
			if (i / cols == 0)
				cons.setY(initialYSpring);
			else if (lastRowCons != null)
				cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH), yPadSpring));
			
			lastCons = cons;
		}
		
		final Constraints pCons = layout.getConstraints(parent);
		if (lastCons != null)
		{
			pCons.setConstraint(SpringLayout.SOUTH, Spring.sum(Spring.constant(yPad), lastCons.getConstraint(SpringLayout.SOUTH)));
			pCons.setConstraint(SpringLayout.EAST, Spring.sum(Spring.constant(xPad), lastCons.getConstraint(SpringLayout.EAST)));
		}
	}
	
	public static void makeCompactGrid(final Container parent, final int rows, final int cols, final int initialX, final int initialY, final int xPad, final int yPad)
	{
		final SpringLayout layout = (SpringLayout) parent.getLayout();
		
		Spring x = Spring.constant(initialX);
		for (int c = 0;c < cols;c++)
		{
			Spring width = Spring.constant(0);
			for (int r = 0;r < rows;r++)
				width = Spring.max(width, getConstraintsForCell(r, c, parent, cols).getWidth());
			for (int r = 0;r < rows;r++)
			{
				final Constraints constraints = getConstraintsForCell(r, c, parent, cols);
				
				constraints.setX(x);
				constraints.setWidth(width);
			}
			
			x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
		}
		
		Spring y = Spring.constant(initialY);
		for (int r = 0;r < rows;r++)
		{
			Spring height = Spring.constant(0);
			for (int c = 0;c < cols;c++)
				height = Spring.max(height, getConstraintsForCell(r, c, parent, cols).getHeight());
			for (int c = 0; c < cols; c++)
			{
				final Constraints constraints = getConstraintsForCell(r, c, parent, cols);
				
				constraints.setY(y);
				constraints.setHeight(height);
			}
			
			y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
		}
		
		final Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, y);
		pCons.setConstraint(SpringLayout.EAST, x);
	}
	
	private static Constraints getConstraintsForCell(final int row, final int col, final Container parent, final int cols)
	{
		final SpringLayout layout = (SpringLayout) parent.getLayout();
		final Component c = parent.getComponent(row * cols + col);
		
		return layout.getConstraints(c);
	}
}