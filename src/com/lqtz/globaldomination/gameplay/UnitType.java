package com.lqtz.globaldomination.gameplay;

public enum UnitType
{
	SETTLER, SOLDIER;

	@Override
	public String toString()
	{
		switch (this)
		{
			case SETTLER:
				return "Settler";
			case SOLDIER:
				return "Soldier";
		}
		return null;
	}

	public static UnitType fromString(String s)
	{
		switch (s)
		{
			case "Settler":
				return SETTLER;
			case "Soldier":
				return SOLDIER;
			default:
				return null;
		}
	}
}