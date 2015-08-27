package com.lqtz.globaldomination.gameplay;

public class CityBuildTask extends CountdownTask
{
	/**
	 * {@code Runnable} that builds a {@code City} once a certain number of
	 * turns has passed
	 *
	 * @param turnsToCity
	 *            the number of turns to wait before building a {@code City}
	 */
	public CityBuildTask(int turnsToCity)
	{
		this.sentry = turnsToCity * 4;
		check();
	}

	@Override
	public void decrease()
	{
		sentry -= 1;
		check();
	}

	@Override
	public void run()
	{}
}
