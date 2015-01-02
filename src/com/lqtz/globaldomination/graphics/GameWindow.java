package com.lqtz.globaldomination.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.lqtz.globaldomination.gameplay.Utils;

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
	private JPanel rightPanel; // Right panel
	private JLabel infoBox; // Info box
	private JTextPane infoPanel; // Pane with tile, city, and game info
	private Utils utils;

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

			// Makes full screen
			GraphicsDevice gd = GraphicsEnvironment
					.getLocalGraphicsEnvironment().getDefaultScreenDevice();
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

		setVisible(true);
		pack();
		start();
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
		unitsPane.setBackground(new Color(64, 64, 64, 160));
		unitsPane.setPreferredSize(new Dimension(200,
				utils.resolution.height / 2));
		unitsPane.setEditable(false);
		unitsPane.setFocusable(false);

		eventLogPane = new JTextPane();
		eventLogPane.setBackground(new Color(64, 64, 64, 160));
		eventLogPane.setPreferredSize(new Dimension(200,
				utils.resolution.height / 2));
		eventLogPane.setEditable(false);
		eventLogPane.setFocusable(false);

		leftPanel.add(unitsPane, BorderLayout.NORTH);
		leftPanel.add(eventLogPane, BorderLayout.SOUTH);

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
		buttonsPane = new JPanel();
		buttonsPane.setBackground(new Color(50, 50, 50, 210));
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
			buttons[i] = new JButton(buttonText[i]);
			buttons[i].setFont(utils.fonts.sourcesans
					.deriveFont(Font.PLAIN, 16));
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

		// Add Containers to the main right components (only the info panel)
		rightPanel = new JPanel((LayoutManager) new FlowLayout(
				FlowLayout.TRAILING, 0, 0));
		rightPanel.setOpaque(false);
		infoPanel = new JTextPane();
		infoPanel.setBackground(new Color(64, 64, 64, 160));
		infoPanel.setPreferredSize(new Dimension(200, utils.resolution.height));
		infoPanel.setEditable(false);
		infoPanel.setFocusable(false);
		rightPanel
				.setPreferredSize(new Dimension(200, utils.resolution.height));
		rightPanel.add(infoPanel);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}

	/**
	 * Begins game
	 */
	private void start()
	{

	}
}
