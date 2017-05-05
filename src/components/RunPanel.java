package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import objects.LogicObject;

public final class RunPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -4016942900269606346L;
	private static final int M = 1;
	
	private final JTextArea _chat = new JTextArea();
	private final JButton _button = new JButton("Run");
	private final Set<LogicObject> _targets = ConcurrentHashMap.newKeySet();
	
	private RunPanel()
	{
		super(new BorderLayout());
		
		final JScrollPane chat = new JScrollPane(_chat);
		final Font chatFont = new Font("Arial", Font.BOLD, 26);
		_chat.setFont(chatFont);
		_chat.setLineWrap(true);
		_chat.setWrapStyleWord(true);
		_chat.setEditable(false);
		add(chat, BorderLayout.CENTER);
		
		_button.setFont(chatFont);
		_button.addActionListener(this);
		add(_button, BorderLayout.SOUTH);
		
		addText("Variables:");
		addText("S - The participants in the case.");
		addText("R - Relation between the participants.");
		addText("M - The life of a participant.");
		addText("K - The attack power of a participant.");
		addText("βm - A bonus of 1 attack power when there are more than m accusers, we assign m = " + M + ".");
		addText("-=====-");
	}
	
	public void addText(final String text)
	{
		_chat.setText(_chat.getText() + text + "\r\n");
	}
	
	public boolean isRunning()
	{
		return !_button.getText().equals("Run");
	}
	
	public void recalculateSize(final int width)
	{
		setPreferredSize(new Dimension((int) ((width / 100d) * 25), getRootPane().getHeight()));
		repaint();
	}
	
	@Override
	protected void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		
		setPreferredSize(new Dimension((int) ((getRootPane().getWidth() / 100d) * 25), getRootPane().getHeight()));
	}
	
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
			object.setIsAttackingNow(false);
		
		if (_button.getText().equals("Restart"))
		{
			addText("-=====-");
			
			for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
				object.setLifeAfterAttack(object.getLife());
			
			_button.setText("Run");
		}
		else
		{
			if (_button.getText().equals("Run"))
			{
				addText("-=====-");
				
				for (final LogicObject object : ObjectsPanel.getInstance().getObjects())
					if (object.getAttackedBy().isEmpty())
						_targets.addAll(object.getTargets());
				
				_button.setText("Next");
				
				ObjectsPanel.getInstance().getCurrentObject().setLocation(9999, 9999);
			}
			
			for (final LogicObject target : _targets)
			{
				for (final LogicObject attacker : target.getAttackedBy())
				{
					attacker.setIsAttackingNow(true);
					target.setLifeAfterAttack(Math.max(0, target.getLifeAfterAttack() - attacker.getAttack()));
				}
				if (target.getLifeAfterAttack() > 0 && target.getAttackedBy().size() > 1)
					target.setLifeAfterAttack(Math.max(0, target.getLifeAfterAttack() - M));
				
				String text = target.getName() + " is being attacked by: ";
				for (final LogicObject attacker : target.getAttackedBy())
					text += attacker.getName() + " (" + attacker.getAttack() + "), ";
				if (target.getAttackedBy().size() > 1)
					text += "there's more than 1 attacker and therefore βm = " + M + ".";
				else
					text += "there's only 1 attacker and therefore βm = 0.";
				text += "\r\nFinal calculation: " + target.getName() + " life => " + target.getLife() + " - [";
				for (final LogicObject attacker : target.getAttackedBy())
					text += attacker.getAttack() + " + ";
				if (target.getAttackedBy().size() > 1)
					text += "1 + ";
				text = text.substring(0, text.length() - 3);
				text += "] = " + target.getLifeAfterAttack() + ".";
				
				addText(text);
				
				_targets.remove(target);
				if (target.getAttack() > 0 && !target.getTargets().isEmpty())
				{
					if (target.getLifeAfterAttack() > 0)
						_targets.addAll(target.getTargets());
					else
						for (final LogicObject obj : target.getTargets())
							if (obj.getLifeAfterAttack() > 0 && obj.getAttack() > 0 && !obj.getTargets().isEmpty())
								_targets.addAll(obj.getTargets());
				}
			}
			if (_targets.isEmpty())
				_button.setText("Restart");
		}
		
		ObjectsPanel.getInstance().repaint();
	}
	
	public static RunPanel getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		private static final RunPanel INSTANCE = new RunPanel();
	}
}