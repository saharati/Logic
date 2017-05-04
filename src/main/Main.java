package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import components.ObjectsPanel;
import components.RunPanel;

public final class Main
{
	public static void main(final String[] args)
	{
		final JFrame window = new JFrame("Article 587 - Abstract Argumentation with Many Lives");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1024, 768);
		window.setLocationRelativeTo(null);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.add(ObjectsPanel.getInstance(), BorderLayout.CENTER);
		window.add(RunPanel.getInstance(), BorderLayout.EAST);
		window.setVisible(true);
	}
}