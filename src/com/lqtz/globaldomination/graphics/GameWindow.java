package com.lqtz.globaldomination.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.lqtz.globaldomination.gameplay.Game;
import com.lqtz.globaldomination.io.Utils;
import com.lqtz.globaldomination.startup.Welcome;

/**
 * 
 * Class for the game window
 * 
 * @author Gitdropcode
 * 
 */
public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;

	// Declare components
	private JPanel leftPanel; // Panel with units info pane and event log pane
	private JTextPane unitsPane; // Units info pane
	private JScrollPane unitsScroll;
	private JTextPane eventLogPane; // Event log pane
	private JScrollPane eventLogScroll;

	private JPanel centerPanel; // Panel with map pane, action buttons pane, and
								// combat info pane
	private GameScreen mapPane; // Map pane
	private JPanel controlPane; // Pane with buttons pane and combat odds pane
	private AlphaJPanel buttonsPane; // Pane with action buttons
	private JButton[] buttons; // Action buttons themselves
	protected JLabel infoBox; // Info box

	private JPanel rightPanel;
	private JTextPane tileInfoPane; // Pane with tile, city, and game info
	private JScrollPane tileInfoScroll;
	private JTextPane cityInfoPane; // Pane with tile, city, and game info
	private JScrollPane cityInfoScroll;
	private JTextPane gameInfoPane; // Pane with tile, city, and game info
	private JScrollPane gameInfoScroll;

	public Style body;
	public Style head;
	public Style[] unitImages;

	private Utils utils;
	
	public static final String IMAGE_STRING = "\0";

	/**
	 * Main game interface window
	 * 
	 * @param utils
	 *            game object for loading res
	 */
	public GameWindow(Utils utils)
	{
		this.utils = utils;
		setContentPane(new ImageContentPane(utils));

		if (utils.fullScreen)
		{
			// Removes buttons
			setUndecorated(true);

			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();

			// Makes full screen
			GraphicsDevice gd = ge.getDefaultScreenDevice();
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
		initStyles();
		addButtonFunctionality();

		pack();
		utils.game = new Game(utils, this, mapPane.tiles);
		setVisible(true);
	}

	/**
	 * Adds components to frame
	 */
	private void addComponents()
	{
		setLayout(new BorderLayout());

		// Left components
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setOpaque(false);

		unitsPane = new JTextPane();
		unitsPane.setOpaque(false);
		unitsPane.setEditable(false);
		unitsPane.setFocusable(false);
		unitsPane.setFont(utils.fonts.sourcesans);
		unitsScroll = new JScrollPane();
		unitsScroll.setViewport(new AlphaJViewport());
		unitsScroll.setViewportView(unitsPane);
		unitsScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 2));
		unitsScroll.getViewport().setBackground(new Color(64, 64, 64, 160));
		unitsScroll.setOpaque(false);
		unitsScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		eventLogPane = new JTextPane();
		eventLogPane.setOpaque(false);
		eventLogPane.setEditable(false);
		eventLogPane.setFocusable(false);
		eventLogPane.setFont(utils.fonts.sourcesans);
		eventLogScroll = new JScrollPane();
		eventLogScroll.setViewport(new AlphaJViewport());
		eventLogScroll.setViewportView(eventLogPane);
		eventLogScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 2));
		eventLogScroll.getViewport().setBackground(new Color(64, 64, 64, 160));
		eventLogScroll.setOpaque(false);
		eventLogScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		leftPanel.add(unitsScroll, BorderLayout.NORTH);
		leftPanel.add(eventLogScroll, BorderLayout.SOUTH);

		// Center components
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);
		mapPane = new GameScreen(this, utils);
		mapPane.setBackground(new Color(0, 0, 0, 0));
		mapPane.setPreferredSize(new Dimension(utils.resolution.width - 400,
				utils.resolution.height - 150));
		mapPane.addTiles(utils.resolution.width - 400,
				utils.resolution.height - 150);
		controlPane = new JPanel(new BorderLayout());
		controlPane.setOpaque(false);

		// Creates buttons and add them to the buttonsPane
		buttonsPane = new AlphaJPanel();
		buttonsPane.setBackground(new Color(50, 50, 50, 210));
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttons = new JButton[5];
		String[] buttonText = new String[] {"Move", "Settle", "Upgrade",
				"Attack", "Pause"};
		Color[] buttonColor = new Color[] {new Color(39, 78, 19),
				new Color(116, 27, 71), new Color(11, 83, 148),
				new Color(153, 0, 0), new Color(127, 127, 127)};
		buttonsPane.add(Box.createHorizontalGlue());
		for (int i = 0; i < 5; i++)
		{
			buttons[i] = new JButton(buttonText[i]);
			buttons[i].setFont(utils.fonts.sourcesans
					.deriveFont(Font.PLAIN, 24));
			buttonsPane.add(buttons[i]); // Add button

			// Spacing
			buttonsPane.add(Box.createHorizontalGlue());
			buttons[i].setMargin(new Insets(5, 5, 5, 5));
			buttons[i].setMinimumSize(new Dimension(120, 60));
			buttons[i].setMaximumSize(new Dimension(120, 60));
			buttons[i].setPreferredSize(new Dimension(120, 60));

			// Colors of buttons
			buttons[i].setBackground(buttonColor[i]); // Button color
			buttons[i].setForeground(Color.WHITE); // Text color
			buttons[i].setOpaque(true);
		}
		buttonsPane.setPreferredSize(new Dimension(
				utils.resolution.width - 400, 100));

		// Set up the info box and pane to go below the action buttons
		// TODO Get rid of "Under Construction"
		infoBox = new JLabel("Under Construction", SwingConstants.CENTER);
		infoBox.setForeground(Color.WHITE);
		infoBox.setPreferredSize(new Dimension(utils.resolution.width - 400, 50));
		infoBox.setFont(utils.fonts.sourcesans.deriveFont(Font.PLAIN, 20));
		controlPane.add(buttonsPane, BorderLayout.NORTH);
		controlPane.add(infoBox, BorderLayout.SOUTH);

		// Add Containers to the main center panel
		centerPanel.add(mapPane, BorderLayout.NORTH);
		centerPanel.add(controlPane, BorderLayout.SOUTH);

		// Add Containers to the main right components
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setOpaque(false);

		tileInfoPane = new JTextPane();
		tileInfoPane.setOpaque(false);
		tileInfoPane.setEditable(false);
		tileInfoPane.setFocusable(false);
		tileInfoPane.setFont(utils.fonts.sourcesans);
		tileInfoScroll = new JScrollPane();
		tileInfoScroll.setViewport(new AlphaJViewport());
		tileInfoScroll.setViewportView(tileInfoPane);
		tileInfoScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 3));
		tileInfoScroll.getViewport().setBackground(new Color(64, 64, 64, 160));
		tileInfoScroll.setOpaque(false);
		tileInfoScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		cityInfoPane = new JTextPane();
		cityInfoPane.setOpaque(false);
		cityInfoPane.setEditable(false);
		cityInfoPane.setFocusable(false);
		cityInfoPane.setFont(utils.fonts.sourcesans);
		cityInfoScroll = new JScrollPane();
		cityInfoScroll.setViewport(new AlphaJViewport());
		cityInfoScroll.setViewportView(cityInfoPane);
		cityInfoScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 3));
		cityInfoScroll.getViewport().setBackground(new Color(64, 64, 64, 160));
		cityInfoScroll.setOpaque(false);
		cityInfoScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		gameInfoPane = new JTextPane();
		gameInfoPane.setOpaque(false);
		gameInfoPane.setEditable(false);
		gameInfoPane.setFocusable(false);
		gameInfoPane.setFont(utils.fonts.sourcesans);
		gameInfoScroll = new JScrollPane();
		gameInfoScroll.setViewport(new AlphaJViewport());
		gameInfoScroll.setViewportView(gameInfoPane);
		gameInfoScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 3));
		gameInfoScroll.getViewport().setBackground(new Color(64, 64, 64, 160));
		gameInfoScroll.setOpaque(false);
		gameInfoScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		rightPanel.add(tileInfoScroll, BorderLayout.NORTH);
		rightPanel.add(cityInfoScroll, BorderLayout.CENTER);
		rightPanel.add(gameInfoScroll, BorderLayout.SOUTH);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}

	private void initStyles()
	{
		body = unitsPane.addStyle("body", null);
		StyleConstants.setForeground(body, Color.WHITE);
		StyleConstants.setFontSize(body, 18);
		head = unitsPane.addStyle("head", body);
		StyleConstants.setBold(head, true);
		StyleConstants.setUnderline(head, true);
		StyleConstants.setFontSize(head, 24);
		eventLogPane.addStyle("body", body);
		eventLogPane.addStyle("head", head);
		tileInfoPane.addStyle("body", body);
		tileInfoPane.addStyle("head", head);
		cityInfoPane.addStyle("body", body);
		cityInfoPane.addStyle("head", head);
		gameInfoPane.addStyle("body", body);
		gameInfoPane.addStyle("head", head);

		unitImages = new Style[10];
		for (int i = 0; i < unitImages.length; i++)
		{
			unitImages[i] = unitsPane.addStyle("unit" + i, null);
			StyleConstants.setIcon(unitImages[i], new ImageIcon(
					utils.images.units[i]));
		}

		try
		{
			eventLogPane.getStyledDocument()
					.insertString(0, "Event Log:", head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			unitsPane.getStyledDocument().insertString(0, "Units:", head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			tileInfoPane.getStyledDocument()
					.insertString(0, "Tile Info:", head);
			tileInfoPane.getStyledDocument().insertString(
					"Tile Info:".length(), "\n(none)", body);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			cityInfoPane.getStyledDocument()
					.insertString(0, "City Info:", head);
			cityInfoPane.getStyledDocument().insertString(
					"City Info:".length(), "\n(none)", body);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			gameInfoPane.getStyledDocument()
					.insertString(0, "Game Info:", head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	private void addButtonFunctionality()
	{
		// TODO Create Pause screen
		buttons[4].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dispatchEvent(new WindowEvent(GameWindow.this,
						WindowEvent.WINDOW_CLOSING));
				utils.game = null;
				new Welcome(utils);
			}
		});
	}

	/**
	 * Logs an event
	 * 
	 * @param s
	 *            the event to be logged
	 */
	public void eventLog(String s)
	{
		StyledDocument doc = eventLogPane.getStyledDocument();
		int start = "Event Log:".length();
		try
		{
			doc.insertString(start, "\n" + s, doc.getStyle("body"));
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		eventLogPane.setCaretPosition(0);
	}

	/**
	 * Updates text pane contents.
	 * 
	 * <p>
	 * The {@code Map} should be of the form
	 * <code>{paneName : newContents, paneName : newContents... }</code> Note
	 * that the new contents can be both {@code String}s or
	 * {@code StyledDocument}s.
	 * 
	 * <p>
	 * The {@code paneName}s can be
	 * <code><ul><li>"units"<li>"tile"<li>"city"<li>"game"</ul></code>
	 * 
	 * <p>
	 * <b>Note:</b> The strings or documents should not include the title.
	 * 
	 * <p>
	 * Use the <code>eventLog</code> method to access the event log.
	 * 
	 * @param diffs
	 *            the map
	 */
	public void updateTextPanes(Map<String, Object> diffs)
			throws IllegalArgumentException
	{
		Object units = diffs.get("units");
		Object tile = diffs.get("tile");
		Object city = diffs.get("city");
		Object game = diffs.get("game");

		if (units == null)
		{}
		else if (units instanceof String)
		{
			StyledDocument doc = unitsPane.getStyledDocument();
			try
			{
				doc.remove("Units:".length(),
						doc.getLength() - "Units:".length());
				doc.insertString("Units:".length(), "\n" + units, body);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
		}
		else if (units instanceof StyledDocument)
		{
			StyledDocument doc = (StyledDocument) units;
			doc.addStyle("body", body);
			doc.addStyle("head", head);
			try
			{
				doc.insertString(0, "Units:\n", head);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
			unitsPane.setStyledDocument(doc);
		}
		else
		{
			throw new IllegalArgumentException(
					"Was not passed String nor StyledDocument");
		}

		if (tile == null)
		{}
		else if (tile instanceof String)
		{
			StyledDocument doc = tileInfoPane.getStyledDocument();
			try
			{
				doc.remove("Tile Info:".length(), doc.getLength()
						- "Tile Info:".length());
				doc.insertString("Tile Info:".length(), "\n" + tile, body);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
		}
		else if (tile instanceof StyledDocument)
		{
			StyledDocument doc = (StyledDocument) tile;
			doc.addStyle("body", body);
			doc.addStyle("head", head);
			try
			{
				doc.insertString(0, "Tile Info:\n", head);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
			tileInfoPane.setStyledDocument(doc);
		}
		else
		{
			throw new IllegalArgumentException(
					"Was not passed String nor StyledDocument");
		}

		if (city == null)
		{}
		else if (city instanceof String)
		{
			StyledDocument doc = cityInfoPane.getStyledDocument();
			try
			{
				doc.remove("City Info:".length(), doc.getLength()
						- "City Info:".length());
				doc.insertString("City Info:".length(), "\n" + city, body);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
		}
		else if (city instanceof StyledDocument)
		{
			StyledDocument doc = (StyledDocument) city;
			doc.addStyle("body", body);
			doc.addStyle("head", head);
			try
			{
				doc.insertString(0, "City Info:\n", head);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
			cityInfoPane.setStyledDocument(doc);
		}
		else
		{
			throw new IllegalArgumentException(
					"Was not passed String nor StyledDocument");
		}

		if (game == null)
		{}
		else if (game instanceof String)
		{
			StyledDocument doc = gameInfoPane.getStyledDocument();
			try
			{
				doc.remove("Game Info:".length(), doc.getLength()
						- "Game Info:".length());
				doc.insertString("Game Info:".length(), "\n" + game, body);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
		}
		else if (game instanceof StyledDocument)
		{
			StyledDocument doc = (StyledDocument) game;
			doc.addStyle("body", body);
			doc.addStyle("head", head);
			try
			{
				doc.insertString(0, "Game Info:\n", head);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}
			gameInfoPane.setStyledDocument(doc);
		}
		else
		{
			throw new IllegalArgumentException(
					"Was not passed String nor StyledDocument");
		}
	}

	private class AlphaJPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public AlphaJPanel()
		{
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			super.paintComponent(g);
		}
	}

	private class AlphaJViewport extends JViewport
	{
		private static final long serialVersionUID = 1L;

		public AlphaJViewport()
		{
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			super.paintComponent(g);
		}
	}
}
