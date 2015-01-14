package com.lqtz.globaldomination.io;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Random;

/**
 * 
 * Object for loading res
 * 
 * @author Gandalf
 * 
 */
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
	public Random random;

	public boolean fullScreen = true;
	public Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();

	public final int DIM = 5;

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
	}
}
