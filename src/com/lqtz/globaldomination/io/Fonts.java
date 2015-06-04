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
package com.lqtz.globaldomination.io;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class Fonts
{
	/**
	 * A body text font
	 */
	public Font droid;
	/**
	 * A header text font
	 */
	public Font goudy;
	/**
	 * A body text font
	 */
	public Font sourcesans;

	/**
	 * Load fonts
	 *
	 * @throws FontFormatException
	 *             error loading fonts
	 * @throws IOException
	 *             error loading fonts
	 */
	public Fonts() throws FontFormatException, IOException
	{
		droid = Font.createFont(Font.TRUETYPE_FONT, getClass()
				.getResourceAsStream("/fonts/droid_sans_mono.ttf"));
		goudy = Font.createFont(Font.TRUETYPE_FONT, getClass()
				.getResourceAsStream("/fonts/goudy_bookletter_1911.otf"));
		sourcesans = Font.createFont(Font.TRUETYPE_FONT, getClass()
				.getResourceAsStream("/fonts/source_sans_pro.otf"));
	}
}
