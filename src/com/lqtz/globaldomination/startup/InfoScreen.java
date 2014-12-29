package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
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
public class InfoScreen extends BasicScreen
{
	private static final long serialVersionUID = 1L;

	private JTextArea bodyTextArea;
	private JScrollPane bodyScrollPane;
	private OptionsPanel footPanel;

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
	public InfoScreen(InputStream input, String titleStr, Game game) throws IOException
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
	 *            text to display in t  he title
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
		footPanel = new OptionsPanel(game, this);
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
}
