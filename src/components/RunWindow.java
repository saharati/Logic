package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import objects.LogicObject;
import util.SpringUtilities;

public final class RunWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 2875660496863443485L;
	
	private final JComboBox<String> _mode = new JComboBox<>();
	private final JComboBox<Character> _starter = new JComboBox<>();
	private final JButton _submit = new JButton("Run");
	
	private RunWindow()
	{
		super("Run");
		
		final Font font = new Font("Arial", Font.BOLD, 15);
		
		final JLabel mode = new JLabel("Mode: ", SwingConstants.LEFT);
		mode.setFont(font);
		mode.setLabelFor(_mode);
		add(mode);
		_mode.addItem("Marriege");
		_mode.addItem("Survival");
		_mode.addItem("Complain");
		_mode.setFont(font);
		add(_mode);
		
		final JLabel starter = new JLabel("Starter: ", SwingConstants.LEFT);
		starter.setFont(font);
		starter.setLabelFor(_starter);
		add(starter);
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
			_starter.addItem(object.getLetter());
		_starter.setFont(font);
		add(_starter);
		
		final JButton cancel = new JButton("Cancel");
		cancel.setFont(font);
		cancel.addActionListener(a -> setVisible(false));
		add(cancel);
		_submit.setFont(font);
		_submit.addActionListener(this);
		add(_submit);
		
		setLayout(new SpringLayout());
		
		SpringUtilities.makeCompactGrid(getContentPane(), 3, 2, 5, 5, 5, 5);
		
		getContentPane().setBackground(Color.CYAN.darker());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public String getMode()
	{
		return (String) _mode.getSelectedItem();
	}
	
	public char getStarter()
	{
		return (char) _starter.getSelectedItem();
	}
	
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (_starter.getSelectedItem() == null)
		{
			JOptionPane.showMessageDialog(null, "You must select a starting attacker.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setVisible(false);
		
		// TODO rounds.
		new RunGame(getMode(), getStarter());
	}
	
	public static RunWindow getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		private static final RunWindow INSTANCE = new RunWindow();
	}
}