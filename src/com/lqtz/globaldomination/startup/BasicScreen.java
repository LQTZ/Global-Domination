package com.lqtz.globaldomination.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.lqtz.globaldomination.graphics.ImageContentPane;
import com.lqtz.globaldomination.io.Game;

/**
 * 
 * Class for a basic window with title
 * 
 * @author Gitdropcode
 * 
 */
public abstract class BasicScreen extends JFrame
{
	private static final long serialVersionUID = 1L;

	private String titleStr;
	protected Game game;

	private JLabel titleLabel;
	protected JPanel bodyPanel;
	protected JPanel footPanel;

	public BasicScreen(String titleStr, Game game)
	{
		this.titleStr = titleStr;
		this.game = game;

		// Removes buttons
		setUndecorated(true);

		// Makes full screen
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
		setContentPane(new ImageContentPane(game));

		setVisible(true);
	}

	protected void addComponents()
	{
		setLayout(new BorderLayout());

		// Draw title
		titleLabel = new JLabel(titleStr);
		titleLabel.setPreferredSize(new Dimension(getWidth(), 150));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(0, 0, 0, 0));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(game.fonts.goudy.deriveFont(Font.PLAIN, 100));
		titleLabel.setOpaque(false);
		add(titleLabel, BorderLayout.NORTH);

		// Draw title
		bodyPanel = new JPanel();
		bodyPanel
				.setPreferredSize(new Dimension(getWidth(), getHeight() - 250));
		bodyPanel.setOpaque(false);
		add(bodyPanel, BorderLayout.CENTER);

		footPanel = new JPanel();
		footPanel.setOpaque(false);
		add(footPanel, BorderLayout.SOUTH);
	}
}
