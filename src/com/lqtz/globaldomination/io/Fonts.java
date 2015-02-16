package com.lqtz.globaldomination.io;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 *
 * Loads fonts
 *
 * @author Gitdropcode
 *
 */
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
	 * @throws IOException
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
