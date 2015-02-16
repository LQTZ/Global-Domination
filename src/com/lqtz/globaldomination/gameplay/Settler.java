package com.lqtz.globaldomination.gameplay;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Settler extends Unit
{
	private static final long serialVersionUID = 1L;

	/**
	 * {@code Settler} {@code Unit}
	 *
	 * @param nation
	 *            {@code Nation} the {@code Settler} belongs to
	 * @param level
	 *            {@code level} of the {@code Settler} to grow
	 * @param xCoord
	 *            initial x-coordinate to put the {@code Settler} on
	 * @param yCoord
	 *            initial y-coordinate to put the {@code Settler} on
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public Settler(Nation nation, int level, int xCoord, int yCoord, Utils utils)
	{
		super(nation, level, xCoord, yCoord, utils);
		utils.game.tiles[xCoord][yCoord].settlers.add(this);
	}

	@Override
	protected void assignByLevel()
	{
		// Note: switch is used instead of simply writing a function to generate
		// the values since it allows for greater flexibility (e.g.
		// maxMoveDistance can flat out at 3 and level 5 can be
		// disproportionately more OP than previous levels

		// TODO make the constants realistic (current values of maxHealthPoints
		// and defendPower were arbitrarily chosen)
		switch (level)
		{
			case 1:
			{
				maxHealthPoints = 2;
				defendPower = 1;
				maxMoveDistance = 1;
			}
			case 2:
			{
				maxHealthPoints = 4;
				defendPower = 3;
				maxMoveDistance = 2;
			}
			case 3:
			{
				maxHealthPoints = 6;
				defendPower = 5;
				maxMoveDistance = 3;
			}
			case 4:
			{
				maxHealthPoints = 8;
				defendPower = 7;
				maxMoveDistance = 3;
			}
			case 5:
			{
				maxHealthPoints = 10;
				maxMoveDistance = 3;
			}
		}
	}

	/**
	 * Move to a certain {@code Tile}
	 *
	 * @param toTile
	 *            {@code Tile} to move to
	 * @return Whether or not {@code move()} was legal (-2 if the
	 *         {@code Settler} has maxed out moves for the turn, -1 if the
	 *         {@code Tile}s are not adjacent, and 0 if {@code move()}
	 *         successful)
	 */
	@Override
	public int move(Tile toTile)
	{
		// Check if unit has maxed out moves for the turn
		if (movesLeft <= 0)
			return -2;

		// Make sure tile is not adjacent
		else if ((Math.abs(tile.xCoord - toTile.xCoord) > 1)
				|| (Math.abs(tile.xCoord - toTile.xCoord) > 1))
			return -1;

		// Delete the old one
		tile.settlers.remove(this);
		nation.units.remove(this);

		// Add new one
		tile = toTile;
		tile.settlers.add(this);

		return 0;
	}

	/**
	 * Create a {@code City} object on the {@code Tile} the {@code Settler} is
	 * on
	 *
	 * @return number of turns it will take to build the {@code City}
	 */
	public int buildCity()
	{
		// TODO create method once City class exists
		return 0;
	}
}
