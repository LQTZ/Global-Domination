package com.lqtz.globaldomination.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.lqtz.globaldomination.io.Utils;

public class ClickableTextPane extends JTextPane implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private Utils utils;

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
				utils.game.selectUnit(utils.game.selectedTile.soldiers
						.get(soldIndex));
			}
		}
		else
		{
			utils.game.selectUnit(utils.game.selectedTile.settlers
					.get(settIndex));
		}
		
		utils.game.updateWindow();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	/**
	 * Check using the icon if a style is in an array of styles.
	 * 
	 * @param a the style
	 * @param aList the array of styles
	 * @return whether the style icon is in the array of styles
	 */
	private boolean styleImageIn(AttributeSet a, AttributeSet[] aList)
	{
		Object iconAttr = StyleConstants.IconAttribute;

		for (AttributeSet compare : aList)
		{
			if (a.getAttribute(iconAttr) != null
					&& a.getAttribute(iconAttr).equals(
							compare.getAttribute(iconAttr)))
			{
				return true;
			}
		}
		return false;
	}
}
