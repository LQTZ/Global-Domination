package com.lqtz.globaldomination.gameplay;

import java.awt.FontFormatException;
import java.io.IOException;

import com.lqtz.globaldomination.io.Fonts;
import com.lqtz.globaldomination.io.Images;

/**
 * 
 * Object for loading res
 * 
 * @author Gandalf
 * 
 */
public class Game
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
	 * Load resources
	 */
	public Game()
	{
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
	}
}
