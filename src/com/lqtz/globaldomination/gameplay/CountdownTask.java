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
package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

public abstract class CountdownTask implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * The variable that controls when the {@code CountdownTask} will execute
	 * {@code run}
	 */
	public int sentry;

	/**
	 * The value of sentry that the {@code CountdownTask} will execute
	 * {@code run} for
	 */
	public int exit;

	/**
	 * Whether or not the {@code CountdownTask} has {@code run} yet
	 */
	public boolean hasRun = false;

	/**
	 * Decreases the sentry
	 */
	public abstract void decrease();

	/**
	 * Executes the code
	 */
	public abstract void run();

	/**
	 * Check if {@code sentry} has been depleted, and if so, {@code run}
	 */
	public void check()
	{
		if (!hasRun && sentry <= 0)
		{
			run();
			hasRun = true;
		}
	}
}
