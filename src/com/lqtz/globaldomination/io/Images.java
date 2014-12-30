package com.lqtz.globaldomination.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Loads images
 * 
 * @author Gitdropcode
 * 
 */
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
	 * Load images
	 * 
	 * @throws IOException
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

	}
}
