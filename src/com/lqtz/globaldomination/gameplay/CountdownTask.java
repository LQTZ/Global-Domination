package com.lqtz.globaldomination.gameplay;

public abstract class CountdownTask implements Runnable
{
	private int moves;
	public boolean hasRun = false;

	public CountdownTask(int moves)
	{
		this.moves = moves * 4;
		if (moves == 0)
		{
			run();
			hasRun = true;
		}
	}

	public void decrease()
	{
		moves--;
		if (moves == 0)
		{
			run();
			hasRun = true;
		}
	}

	@Override
	public abstract void run();
}
