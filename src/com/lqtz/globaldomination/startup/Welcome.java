package com.lqtz.globaldomination.startup;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.lqtz.globaldomination.Game;
import com.lqtz.globaldomination.ImageContentPane;

/**
 * 
 * Welcome screen
 * 
 * @author Gitdropcode
 * 
 */
public class Welcome extends JFrame 
{
	private static final long serialVersionUID = 1L;

	public Welcome()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		if (gd.isFullScreenSupported())
		{
			gd.setFullScreenWindow(this);
		}
		else
		{
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		
		// Setup screen attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Ends the program when closed
		setTitle("Global Domination");
		setContentPane(new ImageContentPane());
		
		setVisible(true);
	}
	

	public static void main(String[] args)
	{
		

		new Welcome();
	}
}
