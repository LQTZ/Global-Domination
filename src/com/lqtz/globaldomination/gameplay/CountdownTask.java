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

public abstract class CountdownTask implements Runnable, Serializable
{
	private static final long serialVersionUID = 1L;
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
	 *            number of moves to wait before executing {@code run()}
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
