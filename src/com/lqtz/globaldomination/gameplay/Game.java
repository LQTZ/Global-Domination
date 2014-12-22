package com.lqtz.globaldomination.gameplay;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.FileReader;
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

	@SuppressWarnings("javadoc")
	public String aboutText;
	@SuppressWarnings("javadoc")
	public String howToPlayText;
	@SuppressWarnings("javadoc")
	public String creditsText;

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

		// Set text
		// TODO load from file properly (fix problem loading from JAR)
		try
		{
			aboutText = getText("res/text/AboutText.txt");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			howToPlayText = getText("res/text/HowToPlayText.txt");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			creditsText = getText("res/text/CreditsText.txt");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getText(String path) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(path));
		String all = "";
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			all = sb.toString();
		}
		finally
		{
			br.close();
		}
		return all;
	}
}
