package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

public class UnitGrowTask extends CountdownTask
		implements Runnable, Serializable
{
	/**
	 * {@code City} the {@code Unit} is being grown in
	 */
	public City city;

	/**
	 * {@code Runnable} that grows a {@code Unit} once it receives enough
	 *
	 * @param city
	 *            {@code City} to grow the {@code Unit} in
	 * @param productivityCost
	 *            number of {@code tileProductivity} points needed to grow the
	 *            {@code Unit}
	 */
	public UnitGrowTask(City city, int productivityCost)
	{
		this.city = city;
		this.sentry = productivityCost * 4;
		check();
	}

	@Override
	public void decrease()
	{
		sentry -= city.tile.tileProductivity;
		check();
	}

	@Override
	public void run()
	{}
}
