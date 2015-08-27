/*******************************************************************************
 * Global Domination is a strategy game.
 * Copyright (C) 2014, 2015  LQTZ Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
		for (int i = 0; i < getWidth() / utils.images.background.getWidth()
				+ 1; i++)
		{
			for (int j = 0; j < getHeight()
					/ utils.images.background.getHeight() + 1; j++)
			{
				g.drawImage(utils.images.background,
						i * utils.images.background.getWidth(),
						j * utils.images.background.getHeight(), null);
			}
		}
	}
}
