package com.lqtz.globaldomination.gameplay;

import java.io.Serializable;

/**
 *
 * Enum for what nationality an object can be.
 *
 * @author Gandalf
 *
 */
public enum Nationality implements Serializable
{
	RED, GREEN, BLUE, YELLOW, NEUTRAL;

	@Override
	public String toString()
	{
		switch (this)
		{
			case RED:
				return "Red";
			case GREEN:
				return "Green";
			case BLUE:
				return "Blue";
			case YELLOW:
				return "Yellow";
			case NEUTRAL:
				return "Neutral";
			default:
				return "";
		}
	}
}
