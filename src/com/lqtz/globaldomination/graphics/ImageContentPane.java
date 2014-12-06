package com.lqtz.globaldomination.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.lqtz.globaldomination.io.Game;

/**
 * Content pane with image background
 * 
 * @author Gitdropcode
 * 
 */
public class ImageContentPane extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Game game;

	/**
	 * Content pane for the GD UI (the background)
	 * 
	 * @param game
	 *            game object for loading res
	 */
	public ImageContentPane(Game game)
	{
		this.game = game;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < getWidth() / game.images.background.getWidth() + 1; i++)
		{
			for (int j = 0; j < getHeight()
					/ game.images.background.getHeight() + 1; j++)
			{
				g.drawImage(game.images.background,
						i * game.images.background.getWidth(), j
								* game.images.background.getHeight(), null);
			}
		}
	}
}
