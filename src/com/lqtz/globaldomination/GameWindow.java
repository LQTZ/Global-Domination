package com.lqtz.globaldomination;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public GameWindow()
	{
		setUndecorated(true);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (gd.isFullScreenSupported())
		{
			gd.setFullScreenWindow(this);
		}
		else
		{
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addComponents();
		setVisible(true);
		
		start();
	}
	
	private void addComponents()
	{
		
	}
	
	private void start()
	{
		
	}
	
	@SuppressWarnings("unused")
	private void stop()
	{
		
	}
}
