package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

import com.lqtz.globaldomination.gameplay.Game;

/**
 * 
 * Class for about page
 * 
 * @author Gandalf
 * 
 */
public class InfoScreen extends BasicScreen
{
	private static final long serialVersionUID = 1L;

	private JTextArea bodyTextArea;
	private JScrollPane bodyScrollPane;
	private InfoPanel footPanel;

	/**
	 * Text to display in body (read from file)
	 */
	private String bodyText;

	/**
	 * Read the text for the page
	 * 
	 * @param path
	 *            path to the txt file with the text for the page
	 * @param titleStr
	 *            the title of the page
	 * @param game
	 *            the game object for loading res
	 * @throws IOException
	 */
	public InfoScreen(InputStream input, String titleStr, Game game)
			throws IOException
	{
		this(read(input), titleStr, game);
	}

	/**
	 * Screen with only a header, a body, and an exit button (e.g. the about
	 * page)
	 * 
	 * @param text
	 *            text to display in window
	 * @param titleStr
	 *            text to display in t he title
	 * @param game
	 *            game object for loading res
	 */
	public InfoScreen(String text, String titleStr, Game game)
	{
		super(titleStr, game);
		bodyText = text;
		createWindow();
	}

	protected JComponent createBody()
	{
		bodyTextArea = new JTextArea(bodyText);
		bodyTextArea.setBackground(new Color(0, 0, 0, 0));
		bodyTextArea.setForeground(Color.WHITE);
		bodyTextArea.setFont(game.fonts.sourcesans.deriveFont(Font.PLAIN, 36));
		bodyTextArea.setOpaque(false);
		bodyTextArea.setLineWrap(true);
		bodyTextArea.setWrapStyleWord(true);
		bodyTextArea.setEditable(false);
		bodyScrollPane = new JScrollPane(bodyTextArea);
		bodyScrollPane.setPreferredSize(new Dimension(game.resolution.width,
				game.resolution.height - 250));
		bodyScrollPane.setOpaque(false);
		bodyScrollPane.getViewport().setOpaque(false);

		// Removed border
		// TODO beautify scroll bars
		Border nullBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		bodyScrollPane.setBorder(nullBorder);

		return bodyScrollPane;
	}

	protected JComponent createFoot()
	{
		footPanel = new InfoPanel(game, this);
		return footPanel;
	}

	private static String read(InputStream input)
	{
		Scanner unmodscan = new Scanner(input);
		Scanner s = unmodscan.useDelimiter("\\A");
		String str = s.next();
		unmodscan.close();
		s.close();
		return str;
	}

	private class InfoPanel extends JPanel implements MouseInputListener
	{
		private static final long serialVersionUID = 1L;
		private int selected = -1;
		private Font labelFont;
		private Game game;
		private JFrame frame;

		private InfoPanel(Game game, JFrame frame)
		{
			this.game = game;
			this.frame = frame;
			labelFont = game.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

			setPreferredSize(new Dimension(game.resolution.width, 100));
			setOpaque(false);

			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(new Color(192, 192, 192));
			g.setFont(labelFont);
			g.drawString("back", game.resolution.width - 100, 65);
			if (selected != -1)
			{
				g.setColor(new Color(240, 192, 48));
				g.fillPolygon(new int[] {game.resolution.width - 115,
						game.resolution.width - 115,
						game.resolution.width - 105}, new int[] {60, 40, 50}, 3);
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
				case 0: // Welcome
				{
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					new Welcome(game);
					break;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			Rectangle itemRect = new Rectangle(game.resolution.width - 100, 35,
					100, 30);
			if (itemRect.contains(e.getPoint()))
			{
				selected = 0;
			}
			else
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
