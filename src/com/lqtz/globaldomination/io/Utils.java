package com.lqtz.globaldomination.io;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.lqtz.globaldomination.gameplay.Game;

public class Utils
{
	/**
	 * Images to load
	 */
	public Images images;

	/**
	 * Fonts to load
	 */
	public Fonts fonts;

	/**
	 * Random number generator
	 */
	public Random random;

	/**
	 * Whether or not to display the game in full-screen
	 */
	public boolean fullScreen = true;

	/**
	 * Screen resolution
	 */
	public Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * {@code Game} object to be added once the {@code Game} is instantiated
	 */
	public Game game = null;

	/**
	 * Length and width of the board
	 */
	public final int DIM = 5;

	/**
	 * {@code Color}s of the {@code GameWindow} buttons
	 */
	public final HashMap<String, Color> buttonColors;

	/**
	 * Load resources
	 */
	public Utils()
	{
		random = new Random();

		// Import images
		try
		{
			images = new Images();
		}
		catch (IOException e)
		{
			System.err.println("Images not found");
			e.printStackTrace();
		}

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

		// Inactive button colors
		buttonColors = new HashMap<String, Color>();
		buttonColors.put("Move", new Color(39, 78, 19));
		buttonColors.put("Settle", new Color(116, 27, 71));
		buttonColors.put("Attack", new Color(153, 0, 0));
		buttonColors.put("Next", new Color(127, 127, 127));
		buttonColors.put("Pause", Color.BLACK);
	}
}
