package com.lqtz.globaldomination.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.lqtz.globaldomination.io.Game;

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
	
	public InfoScreen(Path path, String titleStr, Game game) throws IOException
	{
		this(new String(Files.readAllBytes(path)), titleStr, game);
	}

	/**
	 * Screen with only a header, a body, and an exit button (e.g. the about
	 * page)
	 * 
	 * @param text
	 *            Text to display in window
	 * @param titleStr
	 *            Text to display in the title
	 * @param game
	 *            Game object for loading res
	 */
	public InfoScreen(String text, String titleStr, Game game)
	{
		super(titleStr, game);
		bodyText = text;
		addComponents();
	}

	@Override
	protected void addComponents()
	{
		super.addComponents();

		// Draw title
		bodyTextArea = new JTextArea(bodyText);
		bodyTextArea.setBackground(new Color(0, 0, 0, 0));
		bodyTextArea.setForeground(Color.WHITE);
		bodyTextArea.setFont(game.fonts.sourcesans.deriveFont(Font.PLAIN, 36));
		bodyTextArea.setOpaque(false);
		bodyTextArea.setLineWrap(true);
		bodyTextArea.setWrapStyleWord(true);
		bodyTextArea.setEditable(false);
		bodyScrollPane = new JScrollPane(bodyTextArea);
		bodyScrollPane.setPreferredSize(new Dimension(getWidth(),
				getHeight() - 250));
		bodyScrollPane.setOpaque(false);
		bodyScrollPane.getViewport().setOpaque(false);

		// Removed border
		// TODO beautify scroll bars
		Border nullBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		bodyScrollPane.setBorder(nullBorder);
		bodyPanel.add(bodyScrollPane);

		footPanel = new InfoPanel(game, this);
		add(footPanel, BorderLayout.SOUTH);
	}

	private class InfoPanel extends JPanel implements MouseListener,
			MouseMotionListener
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

			setPreferredSize(new Dimension(frame.getWidth(), 100));
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
			g.drawString("back", frame.getWidth() - 100, 65);
			if (selected != -1)
			{
				g.setColor(new Color(240, 192, 48));
				g.fillPolygon(
						new int[] {frame.getWidth() - 115,
								frame.getWidth() - 115, frame.getWidth() - 105},
						new int[] {60, 40, 50}, 3);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
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
			Rectangle itemRect = new Rectangle(getWidth() - 100, 35, 100, 30);
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
		public void mousePressed(MouseEvent e)
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
