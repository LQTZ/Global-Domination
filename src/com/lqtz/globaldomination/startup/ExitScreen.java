package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.lqtz.globaldomination.gameplay.Game;

/**
 * 
 * Class for about page
 * 
 * @author Gandalf
 * 
 */
public class ExitScreen extends BasicScreen
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
	public ExitScreen(Path path, String titleStr, Game game) throws IOException
	{
		this(new String(Files.readAllBytes(path)), titleStr, game);
	}

	/**
	 * Screen with only a header, a body, and an exit button (e.g. the about
	 * page)
	 * 
	 * @param text
	 *            text to display in window
	 * @param titleStr
	 *            text to display in the title
	 * @param game
	 *            game object for loading res
	 */
	public ExitScreen(String text, String titleStr, Game game)
	{
		super(titleStr, game);
		bodyText = text;
		createWindow();

		TimerTask timerTask = new DelayedScreenCloserTask();
		((DelayedScreenCloserTask) timerTask).setFrame(this);
		Timer timer = new Timer(true);
		timer.schedule(timerTask, 3000);
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
		bodyScrollPane.setPreferredSize(new Dimension(getWidth(),
				getHeight() - 250));
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

	private class DelayedScreenCloserTask extends TimerTask
	{
		public JFrame frame;

		/**
		 * @param frame
		 *            the frame to set
		 */
		public void setFrame(JFrame frame)
		{
			this.frame = frame;
		}

		@Override
		public void run()
		{
			frame.dispose();
			System.exit(0);
		}
	}

	private class InfoPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		private InfoPanel(Game game, JFrame frame)
		{
			game.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

			setPreferredSize(new Dimension(frame.getWidth(), 100));
			setOpaque(false);
		}
	}
}
