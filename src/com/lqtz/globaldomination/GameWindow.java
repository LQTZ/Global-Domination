package com.lqtz.globaldomination;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

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
	private JTextPane eventLogPane; // Event log pane
	private JPanel centerPanel; // Panel with map pane, action buttons pane, and
								// combat info pane
	private GameScreen mapPane; // Map pane
	private JPanel controlPane; // Pane with buttons pane and combat odds pane
	private JPanel buttonsPane; // Pane with action buttons
	private JButton[] buttons; // Action buttons themselves
	private JLabel combatOddsPane; // Pane with combat odds info
	private JTextPane infoPanel; // Pane with tile, city, and game info

	public GameWindow()
	{
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
		controlPane = new JPanel(new BorderLayout());

		// Creates buttons and add them to the buttonsPane
		buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttons = new JButton[5];
		for (int i = 0; i < 5; i++)
		{
			buttons[i] = new JButton(); // Create new button
			buttonsPane.add(buttons[i]); // Add button
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT); // Align
																	// button
			buttons[i].setAlignmentY(Component.CENTER_ALIGNMENT); //
		}
		buttonsPane.setPreferredSize(new Dimension(getWidth() - 400, 300));

		// Set up the buttons panel with GridBagLayout
		// TODO Make buttons panel distributed across the entire center panel
		controlPane.setLayout(new GridBagLayout()); // Change controlPane layout
													// setter to GridBagLayout
		GridBagConstraints controlPanelFormattingConstraints = new GridBagConstraints(); // Object
																							// to
																							// hold
																							// GridBagLayout
																							// constraints
		controlPanelFormattingConstraints.fill = GridBagConstraints.HORIZONTAL;
		controlPanelFormattingConstraints.anchor = GridBagConstraints.NORTH;
		controlPanelFormattingConstraints.gridx = 3; // Centered (aligned at
														// column 3)
		controlPanelFormattingConstraints.gridwidth = 5; // 5 columns wide
		controlPanelFormattingConstraints.gridy = 1; // First row
		controlPane.add(buttonsPane, controlPanelFormattingConstraints); // Add
																			// the
																			// buttons
																			// pane

		// Set up the combat odds label and pane to go below the action buttons
		// TODO Make combat odds panel distributed across the entire center
		// panel
		combatOddsPane = new JLabel();
		combatOddsPane.setPreferredSize(new Dimension(getWidth() - 400, 50));
		controlPanelFormattingConstraints.anchor = GridBagConstraints.SOUTH;
		controlPanelFormattingConstraints.gridx = 3; // Centered (aligned at
														// column 3)
		controlPanelFormattingConstraints.gridwidth = 5; // 5 columns wide
		controlPanelFormattingConstraints.gridy = 2; // First row
		controlPane.add(combatOddsPane, controlPanelFormattingConstraints); // Add
																			// the
																			// combat
																			// odds
																			// pane

		// Add Containers to the main center panel
		centerPanel.add(mapPane, BorderLayout.NORTH);
		centerPanel.add(controlPane, BorderLayout.SOUTH);

		// Add Containers to the main right components (only the info panel)
		infoPanel = new JTextPane();
		infoPanel.setPreferredSize(new Dimension(200, getHeight()));

		// Outline panels with color (to be removed)
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Main
																			// panels
																			// black
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.black)); //
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black)); //
		mapPane.setBorder(BorderFactory.createLineBorder(Color.blue)); // Map
																		// blue
		unitsPane.setBorder(BorderFactory.createLineBorder(Color.cyan)); // Units
																			// pane
																			// cyan
		eventLogPane.setBorder(BorderFactory.createLineBorder(Color.pink)); // Event
																			// log
																			// pane
																			// pink
		controlPane.setBorder(BorderFactory.createLineBorder(Color.blue)); // Control
																			// pane
																			// blue
		buttonsPane.setBorder(BorderFactory.createLineBorder(Color.cyan)); // Buttons
																			// pane
																			// cyan
		combatOddsPane.setBorder(BorderFactory.createLineBorder(Color.green)); // Combat
																				// odds
																				// pane
																				// green

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
