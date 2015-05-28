package com.lqtz.globaldomination.gameplay;

public abstract class CountdownTask implements Runnable
{
	private int moves;

	/**
	 * Whether or not it has finished its job
	 */
	public boolean hasRun = false;

	/**
	 * Runnable that waits a certain number of turns then executes its
	 * {@code run()} method (to be overridden)
	 *
	 * @param moves
	 *            Number of moves to wait before executing {@code run()}
	 */
	public CountdownTask(int moves)
	{
		this.moves = moves * 4;
		if (moves == 0)
		{
			run();
			hasRun = true;
		}
	}

	/**
	 * Decreases the number of moves left by one (called every new turn)
	 */
	public void decrease()
	{
		moves--;
		if (!hasRun && moves == 0)
		{
			run();
			hasRun = true;
		}
	}

	@Override
	public abstract void run();
}
