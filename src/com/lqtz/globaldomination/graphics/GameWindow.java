package com.lqtz.globaldomination.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.lqtz.globaldomination.gameplay.Nationality;
import com.lqtz.globaldomination.gameplay.Settler;
import com.lqtz.globaldomination.io.Utils;
import com.lqtz.globaldomination.startup.Welcome;

public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Utils utils;

	private boolean newGame;

	// Components
	private JPanel leftPanel; // Panel with units info pane and event log pane
	private ClickableTextPane unitsPane; // Units info pane
	private JScrollPane unitsScroll;
	private JTextPane eventLogPane; // Event log pane
	private JScrollPane eventLogScroll;
	private JPanel centerPanel; // Panel with map pane, action buttons pane, and
								// combat info pane
	private GameScreen mapPane; // Map pane
	private JPanel controlPane; // Pane with buttons pane and combat odds pane
	private AlphaJPanel unitButtonsPane; // Pane with action buttons
	private AlphaJPanel tileButtonsPane; // Pane with action buttons
	private AlphaJPanel miscButtonsPane; // Pane with action buttons
	private AlphaJPanel buttonsPane;
	private JLabel infoBox; // Info box
	private JPanel rightPanel;
	private JTextPane selectedUnitInfoPane; // Pane with selected unit's info
	private JScrollPane selectedUnitInfoScroll;
	private JTextPane tileInfoPane; // Pane with tile, city, and game info
	private JScrollPane tileInfoScroll;

	/**
	 * Action {@code JButton}s of the {@code GameWindow}
	 */
	public JButton[] unitButtons;
	public JButton[] tileButtons;
	public JButton[] miscButtons;

	/**
	 * {@code Style} for the body text
	 */
	public Style body;

	/**
	 * {@code Style} for the head text
	 */
	public Style head;

	/**
	 * {@code Style} for the soldier images
	 */
	public Style[] soldierImages;

	/**
	 * {@code Style} for the settler images
	 */
	public Style[] settlerImages;

	/**
	 * {@code Style} of the selected {@code Unit}
	 */
	public Style pointer;

	/**
	 * {@code null} character used for the {@code String} part of icons in text
	 * panes
	 */
	public static final String IMAGE_STRING = "\0";

	/**
	 * Main game interface window
	 * 
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public GameWindow(Utils utils, boolean newGame)
	{
		this.utils = utils;
		this.newGame = newGame;
		utils.gw = this;
		setContentPane(new ImageContentPane(utils));

		if (utils.fullScreen)
		{
			setExtendedState(Frame.MAXIMIZED_BOTH);
			setUndecorated(true);
			setAlwaysOnTop(true);
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
		if (newGame)
		{
			utils.game = new Game(utils, this, mapPane.tiles);
			utils.game.init();
		}
		else
		{
			boolean bad = true;
			Game game = null;
			try
			{
				game = utils.deserializeGame();
				bad = false;
			}
			catch (IOException e)
			{}

			while (bad)
			{
				JOptionPane.showMessageDialog(this,
						"The file may be corrupted or be outdated.",
						"Bad File", JOptionPane.ERROR_MESSAGE);
				try
				{
					game = utils.deserializeGame();
					bad = false;
				}
				catch (IOException e)
				{}
			}

			if (game == null)
			{
				exit();
				return;
			}
			else
			{
				utils.game = game;
				mapPane.tiles = game.tiles;
			}
		}
		utils.game.updateWindow();
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

		unitsPane = new ClickableTextPane(utils);
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
		mapPane.init(utils.resolution.width - 400,
				utils.resolution.height - 150, newGame);
		controlPane = new JPanel(new BorderLayout());
		controlPane.setOpaque(false);

		// Creates buttons and add them to the buttonsPane
		String[] unitButtonText = new String[] {"Move", "Attack", "Settle"};
		String[] tileButtonText = new String[] {"Grow"};
		String[] miscButtonText = new String[] {"Next", "Save", "Exit"};

		unitButtonsPane = new AlphaJPanel();
		unitButtonsPane.setBackground(new Color(50, 50, 50, 210));
		unitButtonsPane.setLayout(new BoxLayout(unitButtonsPane,
				BoxLayout.LINE_AXIS));
		unitButtons = new JButton[3];
		unitButtonsPane.add(Box.createHorizontalGlue());
		for (int i = 0; i < 3; i++)
		{
			unitButtons[i] = new JButton(unitButtonText[i]);
			unitButtons[i].setFont(utils.fonts.sourcesans.deriveFont(
					Font.PLAIN, 20));
			unitButtonsPane.add(unitButtons[i]); // Add button

			// Spacing
			unitButtonsPane.add(Box.createHorizontalGlue());
			unitButtons[i].setMargin(new Insets(5, 5, 5, 5));
			unitButtons[i].setMinimumSize(new Dimension(100, 60));
			unitButtons[i].setMaximumSize(new Dimension(100, 60));
			unitButtons[i].setPreferredSize(new Dimension(100, 60));

			// Colors of buttons
			unitButtons[i].setBackground(utils.buttonColors // Button color
					.get(unitButtonText[i]));
			unitButtons[i].setForeground(Color.WHITE); // Text color
			unitButtons[i].setFocusPainted(false); // Eliminate inner focus
			// border
			unitButtons[i].setOpaque(true);
		}

		unitButtonsPane.setPreferredSize(new Dimension(
				utils.resolution.width - 400, 100));

		tileButtonsPane = new AlphaJPanel();
		tileButtonsPane.setBackground(new Color(50, 50, 50, 210));
		tileButtonsPane.setLayout(new BoxLayout(tileButtonsPane,
				BoxLayout.LINE_AXIS));
		tileButtons = new JButton[1];
		tileButtonsPane.add(Box.createHorizontalGlue());
		for (int i = 0; i < 1; i++)
		{
			tileButtons[i] = new JButton(tileButtonText[i]);
			tileButtons[i].setFont(utils.fonts.sourcesans.deriveFont(
					Font.PLAIN, 20));
			tileButtonsPane.add(tileButtons[i]); // Add button

			// Spacing
			tileButtonsPane.add(Box.createHorizontalGlue());
			tileButtons[i].setMargin(new Insets(5, 5, 5, 5));
			tileButtons[i].setMinimumSize(new Dimension(100, 60));
			tileButtons[i].setMaximumSize(new Dimension(100, 60));
			tileButtons[i].setPreferredSize(new Dimension(100, 60));

			// Colors of buttons
			tileButtons[i].setBackground(utils.buttonColors // Button color
					.get(tileButtonText[i]));
			tileButtons[i].setForeground(Color.WHITE); // Text color
			tileButtons[i].setFocusPainted(false); // Eliminate inner focus
			// border
			tileButtons[i].setOpaque(true);
		}

		tileButtonsPane.setPreferredSize(new Dimension(
				utils.resolution.width - 400, 100));

		miscButtonsPane = new AlphaJPanel();
		miscButtonsPane.setBackground(new Color(50, 50, 50, 210));
		miscButtonsPane.setLayout(new BoxLayout(miscButtonsPane,
				BoxLayout.LINE_AXIS));
		miscButtons = new JButton[3];
		miscButtonsPane.add(Box.createHorizontalGlue());
		for (int i = 0; i < 3; i++)
		{
			miscButtons[i] = new JButton(miscButtonText[i]);
			miscButtons[i].setFont(utils.fonts.sourcesans.deriveFont(
					Font.PLAIN, 20));
			miscButtonsPane.add(miscButtons[i]); // Add button

			// Spacing
			miscButtonsPane.add(Box.createHorizontalGlue());
			miscButtons[i].setMargin(new Insets(5, 5, 5, 5));
			miscButtons[i].setMinimumSize(new Dimension(100, 60));
			miscButtons[i].setMaximumSize(new Dimension(100, 60));
			miscButtons[i].setPreferredSize(new Dimension(100, 60));

			// Colors of buttons
			miscButtons[i].setBackground(utils.buttonColors // Button color
					.get(miscButtonText[i]));
			miscButtons[i].setForeground(Color.WHITE); // Text color
			miscButtons[i].setFocusPainted(false); // Eliminate inner focus
													// border
			miscButtons[i].setOpaque(true);
		}

		miscButtonsPane.setPreferredSize(new Dimension(
				utils.resolution.width - 400, 100));

		// Set up the info box and pane to go below the action buttons
		infoBox = new JLabel("<Player to move will be displayed here>",
				SwingConstants.CENTER);
		infoBox.setOpaque(true);
		infoBox.setBackground(new Color(0, 0, 0, 0));
		infoBox.setForeground(Color.WHITE);
		infoBox.setPreferredSize(new Dimension(utils.resolution.width - 400, 50));
		infoBox.setFont(utils.fonts.sourcesans.deriveFont(Font.PLAIN, 20));
		buttonsPane = miscButtonsPane;
		controlPane.add(buttonsPane, BorderLayout.NORTH);
		controlPane.add(infoBox, BorderLayout.SOUTH);

		// Add Containers to the main center panel
		centerPanel.add(mapPane, BorderLayout.NORTH);
		centerPanel.add(controlPane, BorderLayout.SOUTH);

		// Add Containers to the main right components
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setOpaque(false);

		selectedUnitInfoPane = new JTextPane();
		selectedUnitInfoPane.setOpaque(false);
		selectedUnitInfoPane.setEditable(false);
		selectedUnitInfoPane.setFocusable(false);
		selectedUnitInfoPane.setFont(utils.fonts.sourcesans);
		selectedUnitInfoScroll = new JScrollPane();
		selectedUnitInfoScroll.setViewport(new AlphaJViewport());
		selectedUnitInfoScroll.setViewportView(selectedUnitInfoPane);
		selectedUnitInfoScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 2));
		selectedUnitInfoScroll.getViewport().setBackground(
				new Color(64, 64, 64, 160));
		selectedUnitInfoScroll.setOpaque(false);
		selectedUnitInfoScroll.setBorder(BorderFactory.createEmptyBorder(0, 0,
				0, 0));

		tileInfoPane = new JTextPane();
		tileInfoPane.setOpaque(false);
		tileInfoPane.setEditable(false);
		tileInfoPane.setFocusable(false);
		tileInfoPane.setFont(utils.fonts.sourcesans);
		tileInfoScroll = new JScrollPane();
		tileInfoScroll.setViewport(new AlphaJViewport());
		tileInfoScroll.setViewportView(tileInfoPane);
		tileInfoScroll.setPreferredSize(new Dimension(200,
				utils.resolution.height / 2));
		tileInfoScroll.getViewport().setBackground(new Color(64, 64, 64, 160));
		tileInfoScroll.setOpaque(false);
		tileInfoScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		rightPanel.add(selectedUnitInfoScroll, BorderLayout.NORTH);
		rightPanel.add(tileInfoScroll, BorderLayout.SOUTH);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}

	private void initStyles()
	{
		body = unitsPane.addStyle(null, null);
		StyleConstants.setForeground(body, Color.WHITE);
		StyleConstants.setFontSize(body, 18);
		head = unitsPane.addStyle(null, body);
		StyleConstants.setBold(head, true);
		StyleConstants.setUnderline(head, true);
		StyleConstants.setFontSize(head, 24);

		soldierImages = new Style[10];
		for (int i = 0; i < soldierImages.length; i++)
		{
			soldierImages[i] = unitsPane.addStyle(null, null);
			StyleConstants.setIcon(soldierImages[i], new ImageIcon(
					utils.images.soldiers[i]));
		}

		settlerImages = new Style[5];
		for (int i = 0; i < settlerImages.length; i++)
		{
			settlerImages[i] = unitsPane.addStyle(null, null);
			StyleConstants.setIcon(settlerImages[i], new ImageIcon(
					utils.images.settlers[i]));
		}

		pointer = unitsPane.addStyle(null, null);
		StyleConstants.setIcon(pointer, new ImageIcon(utils.images.pointer));

		try
		{
			eventLogPane.getStyledDocument().insertString(0, "Event Log:\n",
					head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			unitsPane.getStyledDocument().insertString(0, "Units:\n", head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			tileInfoPane.getStyledDocument().insertString(0, "Tile Info:\n",
					head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		try
		{
			selectedUnitInfoPane.getStyledDocument().insertString(0,
					"Selected Unit Info:\n", head);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	private void addButtonFunctionality()
	{
		// Move button
		unitButtons[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				utils.game.moveSelected = !utils.game.moveSelected;
				utils.game.updateWindow();
			}
		});

		// Settle button
		unitButtons[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int buildStatus = ((Settler) utils.game.selectedUnit)
						.buildCity();
				switch (buildStatus)
				{
					case -1:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"This Settler is already building.",
								"Already Building", JOptionPane.ERROR_MESSAGE);
						break;
					}
					case -2:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"This Settler is already on a city.",
								"Already On City", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
				utils.game.updateWindow();
			}
		});

		// Attack button
		unitButtons[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				utils.game.attackSelected = !utils.game.attackSelected;
				utils.game.updateWindow();
			}
		});

		// Next button
		miscButtons[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				utils.game.nextTurn();
				utils.game.updateWindow();
			}
		});

		// Save button
		miscButtons[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				utils.serializeGame();
				utils.game.updateWindow();
			}
		});

		// Pause button
		// TODO Create Pause screen
		miscButtons[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				exit();
			}
		});

		// Grow unit button
		tileButtons[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int growStatus = utils.game.growUnit();

				switch (growStatus)
				{
					case -1:
					{
						JOptionPane.showMessageDialog(utils.gw, "This city is "
								+ utils.game.selectedTile.nat
								+ ", you cannot grow units here.",
								"Cannot Grow Unit", JOptionPane.ERROR_MESSAGE);
						break;
					}

					case -2:
					{
						JOptionPane.showMessageDialog(utils.gw,
								"The city is already growing a unit.",
								"Cannot Grow Unit", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
				utils.game.updateWindow();
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
		int start = "Event Log:\n".length();
		try
		{
			doc.insertString(start, s + "\n", body);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		eventLogPane.setCaretPosition(0);
	}

	/**
	 * Set the text of {@code infoBox}
	 * 
	 * @param s
	 *            text to set {@code infoBox} to
	 */
	public void infoBox(String s)
	{
		infoBox.setText(s);
	}

	public void newTurn(Nationality n)
	{
		infoBox.setBackground(utils.infoBoxColors.get(n));
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
	 * <b>Note:</b> The strings or documents should not include the title. They
	 * should contain newlines at the end.
	 * 
	 * <p>
	 * Use the <code>eventLog</code> method to access the event log.
	 * 
	 * @param diffs
	 *            {@code diffs} map
	 * @throws IllegalArgumentException
	 *             invalid value for {@code diffs}
	 */
	public void updateTextPanes(Map<String, Object> diffs)
			throws IllegalArgumentException
	{
		Object units = diffs.get("units");
		Object tile = diffs.get("tile");
		Object selectedUnit = diffs.get("selectedUnit");

		if (units != null)
		{
			if (units instanceof String)
			{
				String str = (String) units;
				StyledDocument doc = unitsPane.getStyledDocument();
				try
				{
					doc.remove("Units:\n".length(), doc.getLength()
							- "Units:\n".length());
					doc.insertString("Units:\n".length(), str, body);
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
			else if (units instanceof StyledDocument)
			{
				StyledDocument doc = (StyledDocument) units;
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
		}

		if (tile != null)
		{
			if (tile instanceof String)
			{
				String str = (String) tile;
				StyledDocument doc = tileInfoPane.getStyledDocument();
				try
				{
					doc.remove("Tile Info:\n".length(), doc.getLength()
							- "Tile Info:\n".length());
					doc.insertString("Tile Info:\n".length(), str, body);
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
			else if (tile instanceof StyledDocument)
			{
				StyledDocument doc = (StyledDocument) tile;
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
		}

		if (selectedUnit != null)
		{
			if (selectedUnit instanceof String)
			{
				String str = (String) selectedUnit;
				StyledDocument doc = selectedUnitInfoPane.getStyledDocument();
				try
				{
					doc.remove("Selected Unit Info:\n".length(),
							doc.getLength() - "Selected Unit Info:\n".length());
					doc.insertString("Selected Unit Info:\n".length(), str,
							body);
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
			else if (selectedUnit instanceof StyledDocument)
			{
				StyledDocument doc = (StyledDocument) selectedUnit;
				try
				{
					doc.insertString(0, "Selected Unit Info:\n", head);
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
				selectedUnitInfoPane.setStyledDocument(doc);
			}
			else
			{
				throw new IllegalArgumentException(
						"Was not passed String nor StyledDocument");
			}
		}
	}

	/**
	 * Change the visible button pane
	 * 
	 * 0 - misc 1 - unit 2 - tile
	 * 
	 * @param n
	 */
	public void togglePane(int n)
	{
		controlPane.remove(buttonsPane);
		switch (n)
		{
			case 0:
			{
				buttonsPane = miscButtonsPane;
				break;
			}
			case 1:
			{
				buttonsPane = unitButtonsPane;
				break;
			}
			case 2:
			{
				buttonsPane = tileButtonsPane;
				break;
			}
		}
		controlPane.add(buttonsPane);
		controlPane.revalidate();
	}

	public void exit()
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dispatchEvent(new WindowEvent(GameWindow.this,
				WindowEvent.WINDOW_CLOSING));
		utils.game = null;
		new Welcome(utils);
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
