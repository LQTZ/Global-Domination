package com.lqtz.globaldomination.startup;

import javax.swing.JFrame;

import com.lqtz.globaldomination.GameWindow;

/**
 * Welcome screen
 */
public class Welcome extends JFrame
{
	private static final long serialVersionUID = 1L;

	// for the moment, goes straight to game, will fix later
	public static void main(String[] args)
	{
		new GameWindow();
	}
}
