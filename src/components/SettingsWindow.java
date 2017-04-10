package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import listeners.SaveSettings;
import objects.LogicObject;
import util.SpringUtilities;

public final class SettingsWindow extends JFrame
{
	private static final long serialVersionUID = 5828369184907352027L;
	
	private final JTextField _health = new JTextField(15);
	private final JTextField _damage = new JTextField(15);
	private final JList<Character> _attackList = new JList<>();
	private final JButton _submit = new JButton("OK");
	
	private int _initialHeight;
	
	private SettingsWindow()
	{
		final Font font = new Font("Arial", Font.BOLD, 15);
		
		_attackList.setModel(new DefaultListModel<Character>());
		_attackList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		final JLabel health = new JLabel("Health: ", SwingConstants.LEFT);
		health.setFont(font);
		health.setLabelFor(_health);
		add(health);
		_health.setFont(font);
		add(_health);
		
		final JLabel damage = new JLabel("Damage: ", SwingConstants.LEFT);
		damage.setFont(font);
		damage.setLabelFor(_damage);
		add(damage);
		_damage.setFont(font);
		add(_damage);
		
		final JLabel attack = new JLabel("Attack: ", SwingConstants.LEFT);
		attack.setFont(font);
		attack.setLabelFor(_attackList);
		add(attack);
		_attackList.setFont(font);
		add(_attackList);
		
		final JButton cancel = new JButton("Cancel");
		cancel.setFont(font);
		cancel.addActionListener(a -> setVisible(false));
		add(cancel);
		_submit.setFont(font);
		add(_submit);
		
		setLayout(new SpringLayout());
		
		SpringUtilities.makeCompactGrid(getContentPane(), 4, 2, 5, 5, 5, 5);
		
		getContentPane().setBackground(Color.CYAN.darker());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		
		_initialHeight = getHeight();
	}
	
	public JTextField getHealth()
	{
		return _health;
	}
	
	public JTextField getDamage()
	{
		return _damage;
	}
	
	public JList<Character> getAttackList()
	{
		return _attackList;
	}
	
	public void edit(final LogicObject object)
	{
		final DefaultListModel<Character> model = (DefaultListModel<Character>) _attackList.getModel();
		model.removeAllElements();
		
		for (final LogicObject obj : ObjectsPanel.getInstance().getObjects())
			model.addElement(obj.getLetter());
		final List<Integer> items = new ArrayList<>();
		for (final LogicObject obj : object.getAttackList())
			items.add(model.indexOf(obj.getLetter()));
		
		_attackList.setSelectedIndices(items.stream().mapToInt(i -> i).toArray());
		_health.setText(object.getHealth() == 0 ? "" : String.valueOf(object.getHealth()));
		_damage.setText(object.getDamage() == 0 ? "" : String.valueOf(object.getDamage()));
		for (final ActionListener al : _submit.getActionListeners())
			_submit.removeActionListener(al);
		_submit.addActionListener(new SaveSettings(object));
		
		setSize(getWidth(), _initialHeight + (model.getSize() * 20));
		setTitle("Editing " + object.getLetter());
		setVisible(true);
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