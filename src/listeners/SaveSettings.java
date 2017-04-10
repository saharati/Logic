package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import components.ObjectsPanel;
import components.SettingsWindow;
import objects.LogicObject;

public final class SaveSettings implements ActionListener
{
	private final LogicObject _object;
	
	public SaveSettings(final LogicObject object)
	{
		_object = object;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		int health;
		int damage;
		try
		{
			health = Integer.valueOf(SettingsWindow.getInstance().getHealth().getText());
			if (health < 1 || health > 9)
			{
				JOptionPane.showMessageDialog(null, "Health must be between 1-10.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		catch (final Exception t)
		{
			JOptionPane.showMessageDialog(null, "Health must be a number between 1-10.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try
		{
			damage = Integer.valueOf(SettingsWindow.getInstance().getDamage().getText());
			if (damage < 1 || damage > 9)
			{
				JOptionPane.showMessageDialog(null, "Damage must be between 1-10.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		catch (final Exception t)
		{
			JOptionPane.showMessageDialog(null, "Damage must be a number between 1-10.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		_object.getAttackList().clear();
		final DefaultListModel<Character> model = (DefaultListModel<Character>) SettingsWindow.getInstance().getAttackList().getModel();
		for (final int index : SettingsWindow.getInstance().getAttackList().getSelectedIndices())
		{
			final char letter = model.getElementAt(index);
			for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
			{
				if (object.getLetter() == letter)
				{
					_object.getAttackList().add(object);
					break;
				}
			}
		}
		_object.setHealth(health);
		_object.setDamage(damage);
		
		SettingsWindow.getInstance().setVisible(false);
		ObjectsPanel.getInstance().repaint();
	}
}