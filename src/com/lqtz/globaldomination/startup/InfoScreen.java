package com.lqtz.globaldomination.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.lqtz.globaldomination.graphics.GameWindow;
import com.lqtz.globaldomination.graphics.ImageContentPane;
import com.lqtz.globaldomination.io.Game;

/**
 * 
 * Class for about page
 * 
 * @author Gandalf
 * 
 */
public class InfoScreen extends JFrame
{
	private static final long serialVersionUID = 1L;

	private String fileNameStr;
	private String titleStr;
	private Game game;

	private JLabel titleLabel;
	private JTextArea bodyTextArea;
	private JScrollPane bodyScrollPane;
	private InfoPanel footPanel;

	/**
	 * Text to display in body (read from file)
	 */
	private String bodyText;

	/**
	 * Screen with only a header, a body, and an exit button (e.g. the about
	 * page)
	 * 
	 * @param fileNameStr
	 *            Name of the txt file with the text to display in the body (do
	 *            not include path)
	 * @param titleStr
	 *            Text to display in the title
	 * @param game
	 *            Game object for loading res
	 */
	public InfoScreen(String fileNameStr, String titleStr, Game game)
	{
		this.fileNameStr = fileNameStr;
		this.titleStr = titleStr;
		this.game = game;

		try
		{
			this.bodyText = readText();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

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
		addComponents();

		setVisible(true);
	}

	private void addComponents()
	{
		setLayout(new BorderLayout());

		// Draw title
		titleLabel = new JLabel(titleStr);
		titleLabel.setPreferredSize(new Dimension(getWidth(), 100));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(0, 0, 0, 0));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(game.fonts.goudy.deriveFont(Font.PLAIN, 100));
		titleLabel.setOpaque(false);
		add(titleLabel, BorderLayout.NORTH);

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
				getHeight() - 200));
		bodyScrollPane.setOpaque(false);
		bodyScrollPane.getViewport().setOpaque(false);
		
		// Removed border
		// TODO beautify scroll bars
		Border nullBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		bodyScrollPane.setBorder(nullBorder);
		add(bodyScrollPane, BorderLayout.CENTER);

		footPanel = new InfoPanel(game, this);
		add(footPanel, BorderLayout.SOUTH);
	}

	/**
	 * Closes window
	 */
	public void goBack()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Gets the text to display on the about screen from file
	 * 
	 * @return text in the file
	 * @throws IOException
	 */
	private String readText() throws IOException
	{
		return new String(
				Files.readAllBytes(Paths.get("res/text", fileNameStr)));
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
