package main;

import java.awt.BorderLayout;
import java.awt.Frame;

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
		window.setExtendedState(Frame.MAXIMIZED_BOTH);
		window.add(ObjectsPanel.getInstance(), BorderLayout.CENTER);
		window.add(RunPanel.getInstance(), BorderLayout.EAST);
		window.addWindowStateListener(e -> RunPanel.getInstance().recalculateSize(window.getWidth()));
		window.setVisible(true);
		
		RunPanel.getInstance().recalculateSize(window.getWidth());
	}
}