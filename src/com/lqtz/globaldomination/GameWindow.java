package com.lqtz.globaldomination;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JPanel
{
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	
	public GameWindow()
	{
		frame = new JFrame();
		frame.add(this);
		frame.setUndecorated(true);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (gd.isFullScreenSupported())
		{
			gd.setFullScreenWindow(frame);
		}
		else
		{
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		init();
		start();
	}
	
	private void init()
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
