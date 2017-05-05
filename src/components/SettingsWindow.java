package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import objects.LogicObject;
import util.SpringUtilities;

public final class SettingsWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 5828369184907352027L;
	
	private final JTextField _name = new JTextField(15);
	private final JTextField _life = new JTextField(15);
	private final JTextField _attack = new JTextField(15);
	private final JList<String> _targets = new JList<>();
	private final JButton _submit = new JButton("OK");
	
	private LogicObject _object;
	private int _initialHeight;
	
	private SettingsWindow()
	{
		super("Object Settings");
		
		final Font font = new Font("Arial", Font.BOLD, 15);
		
		_targets.setModel(new DefaultListModel<>());
		_targets.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		final JLabel name = new JLabel("Name: ", SwingConstants.LEFT);
		name.setFont(font);
		name.setLabelFor(_name);
		add(name);
		_name.setFont(font);
		add(_name);
		
		final JLabel life = new JLabel("Life: ", SwingConstants.LEFT);
		life.setFont(font);
		life.setLabelFor(_life);
		add(life);
		_life.setFont(font);
		add(_life);
		
		final JLabel attack = new JLabel("Attack: ", SwingConstants.LEFT);
		attack.setFont(font);
		attack.setLabelFor(_attack);
		add(attack);
		_attack.setFont(font);
		add(_attack);
		
		final JLabel targets = new JLabel("Targets: ", SwingConstants.LEFT);
		targets.setFont(font);
		targets.setLabelFor(_targets);
		add(targets);
		_targets.setFont(font);
		add(_targets);
		
		final JButton cancel = new JButton("Cancel");
		cancel.setFont(font);
		cancel.addActionListener(a -> setVisible(false));
		add(cancel);
		_submit.setFont(font);
		_submit.addActionListener(this);
		add(_submit);
		
		setLayout(new SpringLayout());
		
		SpringUtilities.makeCompactGrid(getContentPane(), 5, 2, 5, 5, 5, 5);
		
		getContentPane().setBackground(Color.CYAN.darker());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		
		_initialHeight = getHeight();
	}
	
	public LogicObject getObject()
	{
		return _object;
	}
	
	public void create(final LogicObject object)
	{
		final DefaultListModel<String> model = (DefaultListModel<String>) _targets.getModel();
		model.removeAllElements();
		for (final LogicObject obj : ObjectsPanel.getInstance().getObjects())
			model.addElement(obj.getName());
		
		_object = object;
		_name.setText("");
		_life.setText("");
		_attack.setText("");
		
		setSize(getWidth(), _initialHeight + model.getSize() * 20);
		setVisible(true);
	}
	
	public void edit(final LogicObject object)
	{
		final DefaultListModel<String> model = (DefaultListModel<String>) _targets.getModel();
		model.removeAllElements();
		for (final LogicObject obj : ObjectsPanel.getInstance().getObjects())
			model.addElement(obj.getName());
		final List<Integer> items = new ArrayList<>();
		for (final LogicObject obj : object.getTargets())
			items.add(model.indexOf(obj.getName()));
		
		_object = object;
		_targets.setSelectedIndices(items.stream().mapToInt(i -> i).toArray());
		_name.setText(object.getName());
		_life.setText(object.getLife() == 0 ? "" : String.valueOf(object.getLife()));
		_attack.setText(object.getAttack() == 0 ? "" : String.valueOf(object.getAttack()));
		
		setSize(getWidth(), _initialHeight + model.getSize() * 20);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final String name = _name.getText();
		if (name.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
		{
			if (_object == object)
				continue;
			
			if (object.getName().equalsIgnoreCase(name))
			{
				JOptionPane.showMessageDialog(null, "This name is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		int life;
		int attack;
		try
		{
			if (_life.getText().isEmpty())
				life = 0;
			else
			{
				life = Integer.parseInt(_life.getText());
				if (life < 1)
	 			{
	 				JOptionPane.showMessageDialog(null, "Life must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
	 				return;
	 			}
			}
		}
		catch (final Exception t)
		{
			JOptionPane.showMessageDialog(null, "Life must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try
		{
			if (_attack.getText().isEmpty())
				attack = 0;
			else
			{
				attack = Integer.valueOf(_attack.getText());
				if (attack < 1)
	 			{
	 				JOptionPane.showMessageDialog(null, "Attack must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
	 				return;
	 			}
			}
		}
		catch (final Exception t)
		{
			JOptionPane.showMessageDialog(null, "Attack must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		_object.getTargets().clear();
		final DefaultListModel<String> model = (DefaultListModel<String>) _targets.getModel();
		for (final int index : _targets.getSelectedIndices())
		{
			final String selectedName = model.getElementAt(index);
			for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
			{
				if (object.getName().equals(selectedName))
				{
					_object.getTargets().add(object);
					break;
				}
			}
		}
		_object.setName(name);
		_object.setLife(life);
		_object.setAttack(attack);
		
		SettingsWindow.getInstance().setVisible(false);
		
		if (ObjectsPanel.getInstance().getObjects().contains(_object))
			ObjectsPanel.getInstance().repaint();
		else
			ObjectsPanel.getInstance().addObject(_object);
	}
	
	public static SettingsWindow getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		private static final SettingsWindow INSTANCE = new SettingsWindow();
	}
}