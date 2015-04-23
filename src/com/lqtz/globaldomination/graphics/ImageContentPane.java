package com.lqtz.globaldomination.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.lqtz.globaldomination.io.Utils;

public class ImageContentPane extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Utils utils;

	/**
	 * Content pane for the GD UI (the background)
	 *
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public ImageContentPane(Utils utils)
	{
		this.utils = utils;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < getWidth() / utils.images.background.getWidth() + 1; i++)
		{
			for (int j = 0; j < getHeight()
					/ utils.images.background.getHeight() + 1; j++)
			{
				g.drawImage(utils.images.background, i
						* utils.images.background.getWidth(), j
						* utils.images.background.getHeight(), null);
			}
		}
	}
}
