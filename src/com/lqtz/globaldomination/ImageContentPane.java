package com.lqtz.globaldomination;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

import com.lqtz.globaldomination.io.Images;

/**
 * Content pane with image background
 * 
 * @author Daniel Zhu
 *
 */
public class ImageContentPane extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Images images;

	public ImageContentPane()
	{
		try
		{
			images = new Images();
		}
		catch (IOException e)
		{
			System.err.println("Images not found");
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		for (int i = 0; i < getWidth() / images.background.getWidth() + 1; i++)
		{
			for (int j = 0; j < getHeight() / images.background.getHeight() + 1; j++)
			{
				g.drawImage(images.background, 0, 0, null);
			}
		}
	}
}
