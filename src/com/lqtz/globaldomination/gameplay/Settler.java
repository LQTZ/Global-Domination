package com.lqtz.globaldomination.gameplay;

import com.lqtz.globaldomination.graphics.Tile;
import com.lqtz.globaldomination.io.Utils;

public class Settler extends Unit
{
	private static final long serialVersionUID = 1L;

	/**
	 * Whether or not the {@code Settler} is building a {@code City}
	 */
	public boolean isBuilding = false;

	/**
	 * Number of turns it takes for the {@code Settler} to build a {@code City}
	 */
	public int turnsToCity;

	/**
	 * {@code CountdownTask} that builds the {@code City}
	 */
	public CountdownTask cityBuilder;

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
		unitType = UnitType.SETTLER;
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
				maxHealthPoints = 4;
				defendPower = 4;
				maxMoveDistance = 1;
				turnsToCity = 3;
				break;
			}
			case 2:
			{
				maxHealthPoints = 5;
				defendPower = 7;
				maxMoveDistance = 2;
				turnsToCity = 3;
				break;
			}
			case 3:
			{
				maxHealthPoints = 8;
				defendPower = 10;
				maxMoveDistance = 3;
				turnsToCity = 2;
				break;
			}
			case 4:
			{
				maxHealthPoints = 10;
				defendPower = 20;
				maxMoveDistance = 3;
				turnsToCity = 2;
				break;
			}
			case 5:
			{
				maxHealthPoints = 20;
				defendPower = 30;
				maxMoveDistance = 4;
				turnsToCity = 1;
				break;
			}
		}
	}

	/**
	 * Move to a certain {@code Tile}
	 * 
	 * @param toTile
	 *            {@code Tile} to move to
	 * @return Whether or not {@code move()} was legal (-4 if the
	 *         {@code Settler} is on a {@code City}, -3 if the {@code Settler}
	 *         is building, -2 if the {@code Settler} has maxed out moves for
	 *         the turn, -1 if the {@code Tile}s are not adjacent, and 0 if
	 *         {@code move()} successful)
	 */
	@Override
	public int move(Tile toTile)
	{
		// Check if tile is not adjacent
		if ((Math.abs(tile.xCoord - toTile.xCoord) > 1)
				|| (Math.abs(tile.yCoord - toTile.yCoord) > 1)
				|| (Math.abs(tile.xCoord - toTile.xCoord) == 1)
				&& (tile.yCoord - toTile.yCoord == tile.xCoord - toTile.xCoord))
		{
			return -1;
		}

		// Check if unit has maxed out moves for the turn
		if (movesLeft <= 0)
		{
			return -2;
		}

		// Check if building city
		if (isBuilding)
		{
			return -3;
		}

		if (tile.soldiers.size() + tile.settlers.size() == 1
				&& tile.city == null)
		{
			tile.nat = Nationality.NEUTRAL;
		}

		utils.gw.eventLog("A " + this + " was moved from " + tile + " to "
				+ toTile + ".");

		// Toggle tiles
		tile.settlers.remove(this);
		tile = toTile;
		tile.settlers.add(this);

		// Decrement movesLeft
		movesLeft--;

		return 0;
	}

	/**
	 * Create a {@code City} object on the {@code Tile} the {@code Settler} is
	 * on
	 * 
	 * @return Error status (-1: {@code isBuilding}, 0: successful)
	 */
	public int buildCity()
	{
		// Check if building
		if (isBuilding)
		{
			return -1;
		}

		// Check if on city
		if (tile.city != null)
		{
			return -2;
		}

		isBuilding = true;

		utils.gw.eventLog("A " + this + " initiated city building on " + tile
				+ ".\nThis will take " + turnsToCity + " moves.");
		cityBuilder = new CountdownTask(turnsToCity)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void run()
			{
				stopGrowing();
				nation.addCity(tile);
				utils.game.gw.eventLog("A new " + nation.nationality
						+ " city was built on " + tile + ".");
				delete();
			}
		};
		utils.game.countdownTasks.add(cityBuilder);
		return 0;
	}

	/**
	 * Finish building the {@code City}
	 */
	public void stopGrowing()
	{
		isBuilding = false;
		cityBuilder = null;
	}

	@Override
	public void delete()
	{
		super.delete();
		tile.settlers.remove(this);
		utils.game.countdownTasks.remove(cityBuilder);
	}
}
