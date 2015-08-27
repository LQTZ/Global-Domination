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
package com.lqtz.globaldomination.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.lqtz.globaldomination.gameplay.Settler;
import com.lqtz.globaldomination.gameplay.Soldier;
import com.lqtz.globaldomination.io.Utils;

public class ClickableTextPane extends JTextPane implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private Utils utils;

	/**
	 * Pane with clickable buttons to go in the {@code GameWindow}
	 *
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public ClickableTextPane(Utils utils)
	{
		this.utils = utils;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	@Override
	public void mousePressed(MouseEvent e)
	{
		StyledDocument doc = getStyledDocument();

		int soldIndex = -1;
		int settIndex = -1;
		// Whether currently in a gap between a pointer and the unit image
		boolean inGap = false;
		int location = viewToModel(e.getPoint());

		// Tell if selection is beyond last unit
		if (location == doc.getLength())
		{
			utils.game.selectUnit(null);
			utils.game.updateWindow();
			return;
		}

		for (int i = 0; i < (location + 1); i++)
		{
			AttributeSet sty = doc.getCharacterElement(i).getAttributes();
			if (styleImageIn(sty, utils.game.gw.soldierImages))
			{
				soldIndex++;
				inGap = false;
			}
			else if (styleImageIn(sty, utils.game.gw.settlerImages))
			{
				settIndex++;
				inGap = false;
			}
			else if (styleImageIn(sty,
					new AttributeSet[] {utils.game.gw.pointer}))
			{
				inGap = true;
			}
		}

		settIndex += (inGap ? 1 : 0);
		soldIndex += (inGap ? 1 : 0);

		if (settIndex == -1)
		{
			if (soldIndex == -1)
			{
				utils.game.selectUnit(null);
			}
			else
			{
				Soldier clickedSoldier = utils.game.selectedTile.soldiers
						.get(soldIndex);
				// Make sure unit belongs to nation whose turn it is
				if (clickedSoldier.nation.nationality == utils.game.turnNationality)
				{
					utils.game.selectUnit(clickedSoldier);
				}
				else
				{
					utils.game.selectUnit(null);
				}
			}
		}
		else
		{
			Settler clickedSettler = utils.game.selectedTile.settlers
					.get(settIndex);
			// Make sure unit belongs to nation whose turn it is
			if (clickedSettler.nation.nationality == utils.game.turnNationality)
				utils.game.selectUnit(clickedSettler);
		}

		utils.game.updateWindow();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	/**
	 * Check using the icon if a style is in an array of styles.
	 *
	 * @param a
	 *            the style
	 * @param aList
	 *            the array of styles
	 * @return whether the style icon is in the array of styles
	 */
	private boolean styleImageIn(AttributeSet a, AttributeSet[] aList)
	{
		Object iconAttr = StyleConstants.IconAttribute;

		for (AttributeSet compare : aList)
		{
			if (a.getAttribute(iconAttr) != null && a.getAttribute(iconAttr)
					.equals(compare.getAttribute(iconAttr)))
			{
				return true;
			}
		}
		return false;
	}
}
