package com.lqtz.globaldomination;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import com.lqtz.globaldomination.graphics.GameScreen;
import com.lqtz.globaldomination.io.Fonts;

/**
 * 
 * Class for the game window
 * 
 * @author Gitdropcode
 * 
 */
public class GameWindow extends JFrame
{
	private static final int BUTTON_FONT_SIZE = 14;

	private static final long serialVersionUID = 1L;

	// Declare components
	/**
	 * Background for drawing stuff on
	 */
	public JLabel background = new JLabel(
			new ImageIcon(
					"C://Users//Tse//git//Global-Domination//res//images//background_1920-1080.png"));

	private JPanel leftPanel; // Panel with units info pane and event log pane
	private JTextPane unitsPane; // Units info pane
	private JTextPane eventLogPane; // Event log pane
	private JPanel centerPanel; // Panel with map pane, action buttons pane, and
								// combat info pane
	private GameScreen mapPane; // Map pane
	private JPanel controlPane; // Pane with buttons pane and combat odds pane
	private JPanel buttonsPane; // Pane with action buttons
	private JButton[] buttons; // Action buttons themselves
	private JLabel combatOddsPane; // Pane with combat odds info
	private JTextPane infoPanel; // Pane with tile, city, and game info
	private Fonts fonts;

	/**
	 * Main game interface window
	 */
	public GameWindow()
	{
		// Initializes fonts
		try
		{
			fonts = new Fonts();
		}
		catch (FontFormatException e)
		{
			System.err.println("Fonts corrupted");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.err.println("Fonts not found");
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
		addComponents(gd);

//		// Screen refresh
//		if (gd.isFullScreenSupported())
//		{
//			gd.setFullScreenWindow(this);
//		}
//		else
//		{
//			setSize(Toolkit.getDefaultToolkit().getScreenSize());
//		}

		setVisible(true);

		start();
	}

	/**
	 * Adds components to frame
	 */
	private void addComponents(GraphicsDevice gd)
	{
		// Set background
//		setLayout(new BorderLayout());
		add(this.background);
		this.background.setLayout(new BorderLayout());

		// Left components
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setBackground(new Color(30, 30, 30, 0));
		unitsPane = new JTextPane();
		unitsPane.setBackground(new Color(30, 30, 30, 150));
		unitsPane.setPreferredSize(new Dimension(200, getHeight() / 2));

		eventLogPane = new JTextPane();
		eventLogPane.setBackground(new Color(30, 30, 30, 150));
		eventLogPane.setPreferredSize(new Dimension(200, getHeight() / 2));

		leftPanel.add(unitsPane, BorderLayout.NORTH);
		leftPanel.add(eventLogPane, BorderLayout.SOUTH);

		// Center components
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(new Color(30, 30, 30, 50));
		mapPane = new GameScreen();
		mapPane.setBackground(new Color(30, 30, 30, 150));
		mapPane.setPreferredSize(new Dimension(getWidth() - 400,
				getHeight() - 152));
		mapPane.addTiles(getWidth() - 400, getHeight() - 152);
		controlPane = new JPanel(new BorderLayout());
		controlPane.setBackground(new Color(30, 30, 30, 150));

		// Creates buttons and add them to the buttonsPane
		buttonsPane = new JPanel();
		buttonsPane.setBackground(new Color(30, 30, 30, 150));
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttons = new JButton[5];
		String[] buttonText = new String[] {"Move Unit", "Settle",
				"Upgrade City", "Upgrade Unit", "Attack"};
		Color[] buttonColor = new Color[] {new Color(39, 78, 19),
				new Color(116, 27, 71), new Color(11, 83, 148),
				new Color(53, 28, 117), new Color(153, 0, 0)};
		buttonsPane.add(Box.createHorizontalGlue());
		for (int i = 0; i < 5; i++)
		{
			buttons[i] = new JButton(buttonText[i]); // Create new button
			buttons[i].setFont(fonts.sourcesans.deriveFont(Font.PLAIN,
					BUTTON_FONT_SIZE)); // Set font
			buttonsPane.add(buttons[i]); // Add button

			// Spacing
			buttonsPane.add(Box.createHorizontalGlue());
			buttons[i].setMargin(new Insets(5, 5, 5, 5));
			buttons[i].setMinimumSize(new Dimension(120, 60));
			buttons[i].setMaximumSize(new Dimension(120, 60));
			buttons[i].setPreferredSize(new Dimension(120, 60));

			// Colors of buttons
			buttons[i].setBackground(buttonColor[i]); // Button color
			buttons[i].setForeground(Color.white); // Text color
			buttons[i].setOpaque(true);
		}
		buttonsPane.setPreferredSize(new Dimension(getWidth() - 400, 100));

		// Set up the combat odds label and pane to go below the action buttons
		combatOddsPane = new JLabel("blah", SwingConstants.CENTER);
		combatOddsPane.setBackground(new Color(30, 30, 30, 150));
		combatOddsPane.setPreferredSize(new Dimension(getWidth() - 400, 50));
		combatOddsPane.setFont(fonts.sourcesans.deriveFont(Font.PLAIN, 20));
		combatOddsPane.setText("blah");
		controlPane.add(buttonsPane, BorderLayout.NORTH);
		controlPane.add(combatOddsPane, BorderLayout.SOUTH);

		controlPane.setBackground(new Color(30, 30, 30, 150));

		// Add Containers to the main center panel
		centerPanel.add(mapPane, BorderLayout.NORTH);
		centerPanel.add(controlPane, BorderLayout.SOUTH);

		// Add Containers to the main right components (only the info panel)
		infoPanel = new JTextPane();
		infoPanel.setBackground(new Color(30, 30, 30, 150));
		infoPanel.setPreferredSize(new Dimension(200, getHeight()));

		this.background.add(leftPanel, BorderLayout.WEST);
		this.background.add(centerPanel, BorderLayout.CENTER);
		this.background.add(infoPanel, BorderLayout.EAST);
	}

	/**
	 * Begins game
	 */
	private void start()
	{

	}

}
