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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import objects.SexOffenderNode;
import util.SpringUtilities;

public final class AttackSettingsWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 5828369184907352027L;
	
	private final SexOffenderNode _object;
	private final JComboBox<String> _targets = new JComboBox<>();
	private final JTextField _attack = new JTextField(15);
	private final JButton _submit = new JButton("OK");
	
	public AttackSettingsWindow(final SexOffenderNode object)
	{
		super("Object Settings");
		
		_object = object;
		
		final Font font = new Font("Arial", Font.BOLD, 15);
		
		final JLabel targets = new JLabel("Targets: ", SwingConstants.LEFT);
		targets.setFont(font);
		targets.setLabelFor(_targets);
		add(targets);
		_targets.setFont(font);
		for (final SexOffenderNode node : ObjectsPanel.getInstance().getObjects())
			_targets.addItem(node.getName());
		add(_targets);
		
		final JLabel attack = new JLabel("Attack: ", SwingConstants.LEFT);
		attack.setFont(font);
		attack.setLabelFor(_attack);
		add(attack);
		_attack.setFont(font);
		add(_attack);
		
		final JButton cancel = new JButton("Close");
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
	
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final String target = _targets.getSelectedItem().toString();
		int attack;
		try
		{
			if (_attack.getText().isEmpty())
			{
 				JOptionPane.showMessageDialog(null, "Attack cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
 				return;
 			}
			
			attack = Integer.valueOf(_attack.getText());
			if (attack < 1)
 			{
 				JOptionPane.showMessageDialog(null, "Attack must be a positive natural number.", "Error", JOptionPane.ERROR_MESSAGE);
 				return;
 			}
		}
		catch (final Exception t)
		{
			JOptionPane.showMessageDialog(null, "Attack must be a positive natural number.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		SexOffenderNode node = null;
		for (final SexOffenderNode object : ObjectsPanel.getInstance().getObjects())
		{
			if (object.getName().equals(target))
			{
				node = object;
				break;
			}
		}
		if (node == null)
		{
			JOptionPane.showMessageDialog(null, "The target could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (_object.getTargets().containsKey(node))
		{
			JOptionPane.showMessageDialog(null, "Cannot modify target after its already set.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setVisible(false);
		
		RunPanel.getInstance().addText(_object.getName() + " attacked " + node.getName() + " with power: " + attack);
		
		_object.getTargets().put(node, attack);
		node.getAttackers().put(_object, attack);
		node.setLifeAfterAttack();
		if (node.getLifeAfterAttack() == 0)
			for (final SexOffenderNode nodeTarget : node.getTargets().keySet())
				if (nodeTarget.getLifeAfterAttack() > 0)
					nodeTarget.setLifeAfterAttack();
		
		ObjectsPanel.getInstance().repaint();
	}
}