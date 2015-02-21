package com.lqtz.globaldomination.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images
{
	/**
	 * Background image
	 */
	public BufferedImage background;

	/**
	 * City icon
	 */
	public BufferedImage city;

	/**
	 * Productivity icon
	 */
	public BufferedImage productivity;

	/**
	 * Revenue icon
	 */
	public BufferedImage revenue;

	/**
	 * General military icon (for tiles)
	 */
	public BufferedImage soldier;

	/**
	 * General settler icon (for tiles)
	 */
	public BufferedImage settler;

	/**
	 * Soldier icons
	 */
	public BufferedImage[] soldiers;

	/**
	 * Settler icons
	 */
	public BufferedImage[] settlers;

	public BufferedImage pointer;

	/**
	 * Load images
	 * 
	 * @throws IOException
	 *             error loading images
	 */
	public Images() throws IOException
	{
		background = ImageIO.read(getClass().getResourceAsStream(
				"/images/background.png"));
		city = ImageIO.read(getClass().getResourceAsStream("/images/city.png"));
		productivity = ImageIO.read(getClass().getResourceAsStream(
				"/images/productivity.png"));
		revenue = ImageIO.read(getClass().getResourceAsStream(
				"/images/revenue.png"));
		soldier = ImageIO.read(getClass().getResourceAsStream(
				"/images/soldier.png"));
		settler = ImageIO.read(getClass().getResourceAsStream(
				"/images/settler.png"));
		soldiers = new BufferedImage[10];
		for (int i = 0; i < soldiers.length; i++)
		{
			soldiers[i] = ImageIO.read(getClass().getResourceAsStream(
					"/images/soldier/level" + (i + 1) + ".png"));
		}

		settlers = new BufferedImage[5];
		for (int i = 0; i < settlers.length; i++)
		{
			settlers[i] = ImageIO.read(getClass().getResourceAsStream(
					"/images/settler/level" + (i + 1) + ".png"));
		}

		pointer = ImageIO.read(getClass().getResourceAsStream(
				"/images/pointer.png"));
	}
}
