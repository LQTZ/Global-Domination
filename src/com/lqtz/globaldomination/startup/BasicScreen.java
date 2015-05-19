package com.lqtz.globaldomination.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.lqtz.globaldomination.graphics.ImageContentPane;
import com.lqtz.globaldomination.io.Utils;

public abstract class BasicScreen extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected Utils utils;
	private String titleStr;
	private JLabel titleLabel;

	protected JComponent bodyComponent;
	protected JComponent footComponent;

	/**
	 * Basic fullscreen {@code JFrame}
	 * 
	 * @param titleStr
	 *            {@code String} for the title text
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public BasicScreen(String titleStr, Utils utils)
	{
		this.titleStr = titleStr;
		this.utils = utils;
	}

	/**
	 * Creates window; not automatically called
	 */
	protected void createWindow()
	{
		setContentPane(new ImageContentPane(utils));

		if (utils.fullScreen)
		{
			// Removes buttons
			setUndecorated(true);

			// Makes full screen
			GraphicsDevice gd = GraphicsEnvironment
					.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			if (gd.isFullScreenSupported())
			{
				gd.setFullScreenWindow(this);
			}
			else
			{
				setSize(Toolkit.getDefaultToolkit().getScreenSize());
			}
		}
		else
		{
			getContentPane().setPreferredSize(utils.resolution);
			pack();
			setResizable(false);
			setLocationRelativeTo(null);
		}

		// Setup screen attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Ends the program when closed
		setTitle("Global Domination");
		addComponents();

		setVisible(true);
	}

	protected void addComponents()
	{
		setLayout(new BorderLayout());

		// Draw title
		titleLabel = new JLabel(titleStr);
		titleLabel.setPreferredSize(new Dimension(utils.resolution.width, 150));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(0, 0, 0, 0));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(utils.fonts.goudy.deriveFont(Font.PLAIN, 100));
		titleLabel.setOpaque(false);
		add(titleLabel, BorderLayout.NORTH);

		bodyComponent = createBody();
		add(bodyComponent, BorderLayout.CENTER);

		footComponent = createFoot();
		add(footComponent, BorderLayout.SOUTH);
	}

	protected abstract JComponent createBody();

	protected abstract JComponent createFoot();
}
