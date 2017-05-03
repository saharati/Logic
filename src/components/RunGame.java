package components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import objects.LogicObject;

public final class RunGame extends JFrame
{
	private static final long serialVersionUID = 2134290311439725102L;
	
	private static final Dimension SPACING = new Dimension(20, 1);
	private static final String[] HEAD = {"Name", "Health Remaining", "Damage Done"};
	
	public RunGame(final String mode, final char starter)
	{
		super("Result - " + mode);
		
		final JTable table = new JTable()
		{
			private static final long serialVersionUID = -4831407940640078121L;
			
			@Override
			public boolean isCellEditable(final int r, final int c)
			{
				return false;
			}
		};
		
		final Map<Character, LogicObject> objects = new HashMap<>();
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
			objects.put(object.getLetter(), object.clone());
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
			for (final LogicObject attack : object.getAttackList())
				objects.get(object.getLetter()).getAttackList().add(objects.get(attack.getLetter()));
		
		switch (mode)
		{
			case "Marriege":
				doMarriege(objects, starter);
				break;
			case "Survival":
				doSurvival(objects, starter);
				break;
			case "Complain":
				doComplain(objects, starter);
				break;
		}
		
		final Object[][] data = new Object[objects.size()][3];
		int i = 0;
		for (final LogicObject object : objects.values())
		{
			data[i][0] = object.getLetter();
			data[i][1] = object.getHealth();
			data[i][2] = object.getDamageDone();
			
			i++;
		}
		
		table.setIntercellSpacing(SPACING);
		table.setRowHeight(table.getRowHeight() + 10);
		table.getTableHeader().setBackground(Color.YELLOW);
		table.setModel(new DefaultTableModel(data, HEAD));
		table.setAutoCreateRowSorter(true);
		
		final DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(SwingConstants.CENTER);
		for (final String s : HEAD)
		{
			table.getColumn(s).setPreferredWidth(200);
			table.getColumn(s).setCellRenderer(centerAlign);
		}
		
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		add(new JScrollPane(table));
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private static void doMarriege(final Map<Character, LogicObject> objects, final char starter)
	{
		boolean todo = false;
		for (final LogicObject object : objects.values())
		{
			if (object.getHealth() > 0 && object.hasAliveTargets())
			{
				todo = true;
				break;
			}
		}
		if (!todo)
			return;
		
		final Set<LogicObject> used = new HashSet<>();
		final LogicObject initialAttacker = objects.get(starter);
		if (initialAttacker.getHealth() > 0)
		{
			for (final LogicObject object : objects.values())
				if (object.getAttackList().contains(initialAttacker))
					object.setHealth(-1);
			
			initialAttacker.doAttack(used);
		}
		if (used.size() < objects.size())
		{
			for (final LogicObject object : objects.values())
			{
				if (!used.contains(object))
				{
					if (object.getHealth() > 0)
						object.doAttack(used);
					else
						used.add(object);
				}
			}
		}
		
		doMarriege(objects, starter);
	}
	
	private static void doSurvival(final Map<Character, LogicObject> objects, final char starter)
	{
		boolean todo = false;
		for (final LogicObject object : objects.values())
		{
			if (object.getHealth() > 0 && object.hasAliveTargets())
			{
				todo = true;
				break;
			}
		}
		if (!todo)
			return;
		
		final Set<LogicObject> used = new HashSet<>();
		final LogicObject initialAttacker = objects.get(starter);
		if (initialAttacker.getHealth() > 0)
		{
			for (final LogicObject object : objects.values())
				if (object.getAttackList().contains(initialAttacker))
					object.setHealth(-1);
			
			initialAttacker.doAttack(used);
		}
		if (used.size() < objects.size())
		{
			for (final LogicObject object : objects.values())
			{
				if (!used.contains(object))
				{
					if (object.getHealth() > 0)
						object.doAttack(used);
					else
						used.add(object);
				}
			}
		}
		
		doSurvival(objects, starter);
	}
	
	private static void doComplain(final Map<Character, LogicObject> objects, final char starter)
	{
		// Not yet supported TODO include times.
	}
}