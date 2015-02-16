package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import com.lqtz.globaldomination.graphics.GameWindow;
import com.lqtz.globaldomination.graphics.ImageContentPane;
import com.lqtz.globaldomination.io.Utils;

public class Welcome extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Utils utils;
	private JPanel panel;

	/**
	 * Welcome screen for links to info pages and new game
	 *
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public Welcome(Utils utils)
	{
		this.utils = utils;

		setUndecorated(true);
		setSize(new Dimension(1000, 400));
		setLocationRelativeTo(null);

		// Setup screen attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Global Domination");
		setContentPane(new ImageContentPane(utils));
		addComponents();
		setVisible(true);
	}

	private void addComponents()
	{
		panel = new WelcomePanel(utils, this);
		add(panel);
		pack();
	}

	/**
	 * Start Global Domination
	 *
	 * @param args
	 *            command-line arguments (unused)
	 */
	public static void main(String[] args)
	{
		new Welcome(new Utils());
	}

	private class WelcomePanel extends JPanel implements MouseInputListener
	{
		private static final long serialVersionUID = 1L;
		private JLabel title1;
		private JLabel title2;
		private int selected = -1;
		private Font labelFont;
		private Utils utils;
		private JFrame frame;
		private int[][] locations;
		private String[] labels;

		private WelcomePanel(Utils utils, JFrame frame)
		{
			locations = new int[][] {new int[] {50, 310}, new int[] {200, 370},
					new int[] {350, 310}, new int[] {500, 370},
					new int[] {650, 310}, new int[] {800, 370}};
			labels = new String[] {"new game", "settings", "load game",
					"about", "how to play", "exit"};
			this.utils = utils;
			this.frame = frame;
			labelFont = this.utils.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setPreferredSize(getSize());
			setBorder(new EmptyBorder(10, 10, 10, 10));
			setOpaque(false);
			title1 = new JLabel();
			title1.setOpaque(false);
			title1.setText("GLOBAL");
			title1.setFont(this.utils.fonts.goudy.deriveFont(Font.PLAIN, 72));
			title1.setForeground(new Color(204, 204, 204));
			title2 = new JLabel();
			title2.setOpaque(false);
			title2.setText("DOMINATION");
			title2.setFont(this.utils.fonts.goudy.deriveFont(Font.PLAIN, 128));
			title2.setForeground(Color.WHITE);
			add(Box.createVerticalStrut(50));
			add(title1);
			add(title2);
			add(Box.createVerticalStrut(150));
			setPreferredSize(new Dimension(1000, 400));

			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(new Color(192, 192, 192));
			g.setFont(labelFont);
			for (int i = 0; i < 6; i++)
			{
				g.drawString(labels[i], locations[i][0], locations[i][1]);
			}
			if (selected != -1)
			{
				g.setColor(new Color(240, 192, 48));
				g.fillPolygon(
						new int[] {locations[selected][0] - 15,
								locations[selected][0] - 15,
								locations[selected][0] - 5}, new int[] {
								locations[selected][1] - 5,
								locations[selected][1] - 25,
								locations[selected][1] - 15}, 3);
			}
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			switch (selected)
			{
				case -1: // Invalid button push
				{
					break;
				}
				case 0: // New game button
				{
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					new GameWindow(utils);
					break;
				}
				case 1: // Settings button
				{
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					new Settings(utils);
					break;
				}
				case 2: // New game button
				{
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					new GameWindow(utils);
					break;
				}
				case 3: // About button
				{
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					try
					{
						new InfoScreen(getClass().getResourceAsStream(
								"/text/AboutText.txt"), "About", utils);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					break;
				}
				case 4:
				{
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					try
					{
						new InfoScreen(getClass().getResourceAsStream(
								"/text/HowToPlayText.txt"), "How to Play",
								utils);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					break;
				}
				case 5: // Exit button
				{
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					break;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			boolean onString = false;
			for (int i = 0; i < 6; i++)
			{
				Rectangle itemRect = new Rectangle(locations[i][0],
						locations[i][1] - 30, 150, 30);
				if (itemRect.contains(e.getPoint()))
				{
					selected = i;
					onString = true;
				}
			}

			if (!onString)
			{
				selected = -1;
			}
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{}

		@Override
		public void mouseReleased(MouseEvent e)
		{}

		@Override
		public void mouseEntered(MouseEvent e)
		{}

		@Override
		public void mouseExited(MouseEvent e)
		{}

		@Override
		public void mouseDragged(MouseEvent e)
		{}
	}
}
