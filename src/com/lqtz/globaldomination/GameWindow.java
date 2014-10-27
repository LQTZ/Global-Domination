package com.lqtz.globaldomination;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Class for the game window
 */
public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	// Declares components
	private JPanel left;
	private JPanel center;
	private JTextPane infoPane;
	private GameScreen game;
	private JPanel control;
	private JPanel buttonsCont;
	private JButton[] buttons;
	private JLabel infoBox;
	
	public GameWindow()
	{
		// Removes buttons
		setUndecorated(true);
		
		// Makes full screen
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (gd.isFullScreenSupported())
		{
			gd.setFullScreenWindow(this);
		}
		else
		{
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		
		// Set attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Ends the program when closed
		setTitle("Global Domination");
		addComponents();
		setVisible(true);
		
		start();
	}
	
	/**
	 * Adds components to frame
	 */
	private void addComponents()
	{
		
	}
	
	/**
	 * Begins game
	 */
	private void start()
	{
		
	}
}
