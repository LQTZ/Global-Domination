package com.lqtz.globaldomination.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Loads images
 * 
 * @author Gitdropcode
 *
 */
public class Images
{
	public BufferedImage background;
	
	public Images() throws IOException
	{
		background = ImageIO.read(getClass().getResourceAsStream("/images/background.png"));
	}
}
