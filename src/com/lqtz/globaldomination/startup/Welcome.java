package com.lqtz.globaldomination.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
	private Game game;
	private JTextArea title;

	/**
	 * Welcome screen for links to info pages and new game
	 */
	public Welcome()
	{
		game = new Game(); // Get Game object for resources

		setUndecorated(true);

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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Global Domination");
		setContentPane(new ImageContentPane());
		addComponents();
		setVisible(true);
	}

	private void addComponents()
	{
		setLayout(new BorderLayout());
		title = new JTextArea();
		title.setOpaque(false);
		title.setEditable(false);
		title.setText("Global\nDomination");
		title.setFont(game.fonts.goudy.deriveFont(Font.PLAIN, 200));
		title.setForeground(new Color(160, 160, 160));
		add(title);
	}

	public static void main(String[] args)
	{
		// new Game();
		new Welcome();
	}
}
