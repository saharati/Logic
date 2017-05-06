package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import objects.SexOffenderNode;
import util.SpringUtilities;

public final class CreationWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 5828369184907352027L;
	
	private final JTextField _name = new JTextField(15);
	private final JTextField _life = new JTextField(15);
	private final JButton _submit = new JButton("OK");
	
	private int _xPercent;
	private int _yPercent;
	
	private CreationWindow()
	{
		super("Object Settings");
		
		final Font font = new Font("Arial", Font.BOLD, 15);
		
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
	}
	
	public void create(final int xPercent, final int yPercent)
	{
		_xPercent = xPercent;
		_yPercent = yPercent;
		
		_name.setText("");
		_life.setText("");
		
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
		for (final SexOffenderNode object : ObjectsPanel.getInstance().getObjects())
		{
			if (object.getName().equalsIgnoreCase(name))
			{
				JOptionPane.showMessageDialog(null, "This name is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		int life;
		try
		{
			if (_life.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Life cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			life = Integer.parseInt(_life.getText());
			if (life < 1)
 			{
 				JOptionPane.showMessageDialog(null, "Life must be a positive natural number.", "Error", JOptionPane.ERROR_MESSAGE);
 				return;
 			}
		}
		catch (final Exception t)
		{
			JOptionPane.showMessageDialog(null, "Life must be a positive natural number.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setVisible(false);
		
		ObjectsPanel.getInstance().addObject(new SexOffenderNode(name, life, _xPercent, _yPercent));
	}
	
	public static CreationWindow getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		private static final CreationWindow INSTANCE = new CreationWindow();
	}
}