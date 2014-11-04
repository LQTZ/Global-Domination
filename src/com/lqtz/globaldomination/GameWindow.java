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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.lqtz.globaldomination.graphics.GameScreen;
import com.lqtz.globaldomination.io.Colors;
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
		addComponents();
		setVisible(true);

		start();
	}

	/**
	 * Adds components to frame
	 */
	private void addComponents()
	{
		// Left components
		leftPanel = new JPanel(new BorderLayout());
		unitsPane = new JTextPane();
		unitsPane.setPreferredSize(new Dimension(200, getHeight() / 2));
		eventLogPane = new JTextPane();
		eventLogPane.setPreferredSize(new Dimension(200, getHeight() / 2));
		leftPanel.add(unitsPane, BorderLayout.NORTH);
		leftPanel.add(eventLogPane, BorderLayout.SOUTH);

		// Center components
		centerPanel = new JPanel(new BorderLayout());
		mapPane = new GameScreen();
		mapPane.setPreferredSize(new Dimension(getWidth() - 400,
				getHeight() - 152));
		mapPane.addTiles(getWidth() - 400, getHeight() - 152);
		controlPane = new JPanel(new BorderLayout());

		// Creates buttons and add them to the buttonsPane
		buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttons = new JButton[5];
		String[] buttonText = new String[] {"Move Unit", "Settle",
				"Upgrade City", "Upgrade Unit", "Attack"};
		Color[] buttonColor = new Color[] {Colors.moveUnitButtonColor,
				Colors.settleButtonColor, Colors.upgradeCityButtonColor,
				Colors.upgradeUnitButtonColor, Colors.attackButtonColor};
		buttonsPane.add(Box.createHorizontalGlue());
		for (int i = 0; i < 5; i++)
		{
			buttons[i] = new JButton(buttonText[i]); // Create new button
			buttons[i].setFont(fonts.sourcesans.deriveFont(Font.PLAIN, BUTTON_FONT_SIZE));	// Set font
			buttonsPane.add(buttons[i]); // Add button
			
			// Spacing
			buttonsPane.add(Box.createHorizontalGlue());
			buttons[i].setMargin(new Insets(5, 5, 5, 5));
			buttons[i].setMinimumSize(new Dimension(120, 60));
			buttons[i].setMaximumSize(new Dimension(120, 60));
			buttons[i].setPreferredSize(new Dimension(120, 60));
			
			// Colors of buttons
			buttons[i].setBackground(buttonColor[i]);	// Button color
			buttons[i].setForeground(Color.black);		// Text color
			buttons[i].setOpaque(true);
		}
		buttonsPane.setPreferredSize(new Dimension(getWidth() - 400, 100));

		// Set up the combat odds label and pane to go below the action buttons
		combatOddsPane = new JLabel("Blah Blah", SwingConstants.CENTER);
		combatOddsPane.setPreferredSize(new Dimension(getWidth() - 400, 50));
		combatOddsPane.setFont(fonts.sourcesans.deriveFont(Font.PLAIN, 20));
		combatOddsPane.setText("blah");
		controlPane.add(buttonsPane, BorderLayout.NORTH);
		controlPane.add(combatOddsPane, BorderLayout.SOUTH); // Add

		// Add Containers to the main center panel
		centerPanel.add(mapPane, BorderLayout.NORTH);
		centerPanel.add(controlPane, BorderLayout.SOUTH);

		// Add Containers to the main right components (only the info panel)
		infoPanel = new JTextPane();
		infoPanel.setPreferredSize(new Dimension(200, getHeight()));

		controlPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Add the main panels
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.EAST);
	}

	/**
	 * Begins game
	 */
	private void start()
	{

	}
}
