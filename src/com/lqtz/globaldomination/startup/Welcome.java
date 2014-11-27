package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	private JPanel panel;

	/**
	 * Welcome screen for links to info pages and new game
	 */
	public Welcome()
	{
		game = new Game(); // Get Game object for resources

		setUndecorated(true);
		setSize(new Dimension(1000, 400));
		setLocationRelativeTo(null);

		// Setup screen attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Global Domination");
		setContentPane(new ImageContentPane());
		addComponents();
		setVisible(true);
	}

	private void addComponents()
	{
		panel = new WelcomePanel(game);
		add(panel);
		pack();
	}

	public static void main(String[] args)
	{
		// new Game();
		new Welcome();
	}

	private class WelcomePanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private JLabel title1;
		private JLabel title2;
		private int selected = 0;
		private Font labelFont;
		private Game game;
		private int[][] locations;
		private String[] labels;

		private WelcomePanel(Game game)
		{
			locations = new int[][] {new int[] {50, 310}, new int[] {200, 370},
					new int[] {350, 310}, new int[] {500, 370},
					new int[] {650, 310}, new int[] {800, 370}};
			labels = new String[] {"new game", "settings", "load game",
					"about", "how to play", "exit"};
			this.game = game;
			labelFont = this.game.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setPreferredSize(getSize());
			setBorder(new EmptyBorder(10, 10, 10, 10));
			setOpaque(false);
			title1 = new JLabel();
			title1.setOpaque(false);
			title1.setText("GLOBAL");
			title1.setFont(this.game.fonts.goudy.deriveFont(Font.PLAIN, 72));
			title1.setForeground(new Color(204, 204, 204));
			title2 = new JLabel();
			title2.setOpaque(false);
			title2.setText("DOMINATION");
			title2.setFont(this.game.fonts.goudy.deriveFont(Font.PLAIN, 128));
			title2.setForeground(Color.WHITE);
			add(Box.createVerticalStrut(50));
			add(title1);
			add(title2);
			add(Box.createVerticalStrut(150));
			setPreferredSize(new Dimension(1000, 400));
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			g.setColor(new Color(192, 192, 192));
			g.setFont(labelFont);
			for (int i = 0; i < 6; i++)
			{
				g.drawString(labels[i], locations[i][0], locations[i][1]);
			}
			if (selected != -1)
			{
				g.setColor(new Color(240, 192, 48));
				g.fillPolygon(new int[] {locations[selected][0] - 15,
						locations[selected][0] - 15,
						locations[selected][0] - 5}, new int[] {
						locations[selected][1] - 5, locations[selected][1] - 25,
						locations[selected][1] - 15}, 3);
			}
		}
	}
}
