package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import objects.SexOffenderNode;

public final class RunPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -4016942900269606346L;
	
	private final JTextArea _chat = new JTextArea();
	private final JButton _button = new JButton("Get Network Info");
	
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
		addText("βm - A bonus of 1 attack power when there are more than m accusers, we assign m = " + SexOffenderNode.M + ".");
		addText("-=====-");
	}
	
	public void addText(final String text)
	{
		_chat.setText(_chat.getText() + text + "\r\n");
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
		for (final SexOffenderNode object : ObjectsPanel.getInstance().getObjects())
		{
			if (object.getLifeAfterAttack() > 0)
				addText(object.getName() + "'s life: " + object.getLifeAfterAttack() + ".");
			else
				addText(object.getName() + "'s life: 0, he's dead.");
		}
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