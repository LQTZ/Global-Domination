package com.lqtz.globaldomination.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextPane;

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
		utils.game.gw.eventLog(e.getPoint().toString());
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{}
}
