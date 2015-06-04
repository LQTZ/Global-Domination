package com.lqtz.globaldomination.io;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.lqtz.globaldomination.gameplay.Game;
import com.lqtz.globaldomination.gameplay.Nationality;
import com.lqtz.globaldomination.graphics.GameWindow;

/**
 * This file is part of Global Domination.
 *
 * Global Domination is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Global Domination is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Global Domination. If not, see <http://www.gnu.org/licenses/>.
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
	 * {@code GameWindow} to play the game in
	 */
	public GameWindow gw = null;

	/**
	 * Length and width of the board
	 */
	public final int DIM = 5;

	/**
	 * {@code Color}s of the {@code GameWindow} buttons
	 */
	public final HashMap<String, Color> buttonColors;

	/**
	 * {@code Color}s of the info boxes
	 */
	public final HashMap<Nationality, Color> infoBoxColors;

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

		// Button colors
		buttonColors = new HashMap<String, Color>();
		buttonColors.put("Move", new Color(39, 78, 19));
		buttonColors.put("Settle", new Color(116, 27, 71));
		buttonColors.put("Attack", new Color(153, 0, 0));
		buttonColors.put("Grow", new Color(0, 96, 0));
		buttonColors.put("Next", new Color(127, 127, 127));
		buttonColors.put("Exit", Color.BLACK);
		buttonColors.put("Save", new Color(0, 0, 180));

		infoBoxColors = new HashMap<Nationality, Color>();
		infoBoxColors.put(Nationality.RED, new Color(255, 0, 0, 100));
		infoBoxColors.put(Nationality.GREEN, new Color(0, 255, 0, 100));
		infoBoxColors.put(Nationality.YELLOW, new Color(191, 191, 0, 100));
		infoBoxColors.put(Nationality.BLUE, new Color(0, 0, 255, 100));
	}

	/**
	 * Serializes {@code Game} object.
	 *
	 * @return whether successful
	 */
	public boolean serializeGame()
	{
		JFileChooser fc = new JFileChooser();
		GDMFilter ff = new GDMFilter();
		fc.addChoosableFileFilter(ff);
		fc.setFileFilter(ff);
		int returnVal = fc.showSaveDialog(gw);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				String n = fc.getSelectedFile().getAbsolutePath() + ".gdm";
				FileOutputStream fos = new FileOutputStream(n);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(game);
				oos.close();
				fos.close();
				gw.eventLog("Game saved.");
				return true;
			}
			catch (Exception e)
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * Deserializes {@code Game} object.
	 *
	 * @return {@code Game} object or {@code null} if cancelled
	 * @throws IOException
	 *             if game file is corrupted
	 */
	public Game deserializeGame() throws IOException
	{
		JFileChooser fc = new JFileChooser();
		GDMFilter ff = new GDMFilter();
		fc.addChoosableFileFilter(ff);
		fc.setFileFilter(ff);
		int returnVal = fc.showOpenDialog(gw);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				FileInputStream fis = new FileInputStream(fc.getSelectedFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				game = (Game) ois.readObject();
				game.onDeserialization(this, gw);
				ois.close();
				fis.close();
				return game;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IOException("Corrupted or outdated file.");
			}
		}
		return null;
	}

	/**
	 * Filters only {@code .gdm} files and directories.
	 *
	 * @author Daniel
	 *
	 */
	private class GDMFilter extends FileFilter
	{
		@Override
		public boolean accept(File f)
		{
			if (f.isDirectory())
			{
				return true;
			}
			if (extension(f).equals("gdm"))
			{
				return true;
			}
			return false;
		}

		private String extension(File f)
		{
			String n = f.getName();
			int i = n.lastIndexOf('.') + 1;
			if (i > 0 && i < n.length())
			{
				return n.substring(i).toLowerCase();
			}
			else
			{
				return "";
			}
		}

		@Override
		public String getDescription()
		{
			return "Global Domination Files";
		}
	}
}
