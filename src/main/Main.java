package main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import components.ObjectsPanel;
import components.RunWindow;

public final class Main
{
	public static void main(final String[] args)
	{
		final JButton runButton = new JButton("Run");
		runButton.addActionListener(al -> RunWindow.getInstance().setVisible(true));
		
		final JFrame window = new JFrame("Article 587 - Abstract Argumentation with Many Lives");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 600);
		window.setLocationRelativeTo(null);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.add(ObjectsPanel.getInstance(), BorderLayout.CENTER);
		window.add(runButton, BorderLayout.SOUTH);
		window.setVisible(true);
	}
}