package com.lqtz.globaldomination;

import java.io.IOException;

import com.lqtz.globaldomination.io.Images;

public class Game
{
	public Images images;
	
	public Game()
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
		new GameWindow(this);
	}
}
