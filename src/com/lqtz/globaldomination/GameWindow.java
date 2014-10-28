package com.lqtz.globaldomination;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
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
	private JTextPane units;
	private JTextPane eventLog;
	private JPanel center;
	private JTextPane infoPane;
	private GameScreen gameScreen;
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
		// FIXME components not showing up
		// Left components
		left = new JPanel(new BorderLayout());
		units = new JTextPane();
		eventLog = new JTextPane();
		left.add(units, BorderLayout.NORTH);
		left.add(eventLog, BorderLayout.SOUTH);
		
		// Center components
		center = new JPanel(new BorderLayout());
		gameScreen = new GameScreen();
		control = new JPanel(new BorderLayout());
		
		// Creates buttons
		buttonsCont = new JPanel();
		buttonsCont.setLayout(new BoxLayout(buttonsCont, BoxLayout.X_AXIS));
		buttons = new JButton[5];
		for (int i = 0; i < 5; i++)
		{
			buttons[i] = new JButton();
			buttonsCont.add(buttons[i]);
		}
		
		infoBox = new JLabel();
		control.add(buttonsCont, BorderLayout.NORTH);
		control.add(infoBox, BorderLayout.SOUTH);
		center.add(gameScreen, BorderLayout.NORTH);
		center.add(control, BorderLayout.SOUTH);
		
		// Right components
		infoPane = new JTextPane();
		
		add(left, BorderLayout.EAST);
		add(center, BorderLayout.CENTER);
		add(infoPane, BorderLayout.WEST);
	}
	
	/**
	 * Begins game
	 */
	private void start()
	{
		
	}
}
