package com.lqtz.globaldomination.gameplay;

public abstract class CountdownTask implements Runnable
{
	private int moves;
	
	public CountdownTask(int moves)
	{
		this.moves = moves;
		if (moves == 0)
		{
			run();
		}
	}
	
	public void decrease()
	{
		moves--;
		if (moves == 0)
		{
			run();
		}
	}
	
	@Override
	public abstract void run();
}
