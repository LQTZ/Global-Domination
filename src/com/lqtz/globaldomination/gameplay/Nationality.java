package com.lqtz.globaldomination.gameplay;

/**
 * 
 * Enum for what nationality an object can be.
 * 
 * @author Gandalf
 * 
 */
public enum Nationality
{
	RED("Red"), GREEN("Green"), BLUE("Blue"), YELLOW("Yellow"), NEUTRAL(
			"Neutral");

	private final String name;

	private Nationality(String name)
	{
		this.name = name;
	}

	public String toStr()
	{
		return name;
	}
}
