package components;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public final class RunPanel extends JPanel
{
	private static final long serialVersionUID = -4016942900269606346L;
	
	private final JTextArea _chat = new JTextArea(10, 30);
	
	private RunPanel()
	{
		super(new BorderLayout());
		
		final Font chatFont = new Font("Arial", Font.BOLD, 15);
		final JScrollPane chat = new JScrollPane(_chat);
		_chat.setFont(chatFont);
		_chat.setLineWrap(true);
		_chat.setWrapStyleWord(true);
		_chat.setEditable(false);
		add(chat, BorderLayout.CENTER);
		
		final JButton run = new JButton("Run");
		// send.addActionListener(e -> run());
		add(run, BorderLayout.SOUTH);
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