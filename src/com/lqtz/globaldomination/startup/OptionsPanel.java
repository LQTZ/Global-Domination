package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lqtz.globaldomination.gameplay.Game;

public class OptionsPanel extends JPanel implements MouseListener,
		MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	private int selected = -1;
	private Font labelFont;
	private Game game;
	private JFrame frame;

	public OptionsPanel(Game game, JFrame frame)
	{
		this.game = game;
		this.frame = frame;
		labelFont = game.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

		setPreferredSize(new Dimension(frame.getWidth(), 100));
		setOpaque(false);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(192, 192, 192));
		g.setFont(labelFont);
		g.drawString("back", frame.getWidth() - 100, 65);
		if (selected != -1)
		{
			g.setColor(new Color(240, 192, 48));
			g.fillPolygon(new int[] {frame.getWidth() - 115,
					frame.getWidth() - 115, frame.getWidth() - 105}, new int[] {
					60, 40, 50}, 3);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		switch (selected)
		{
			case -1: // Invalid button push
			{
				break;
			}
			case 0: // Welcome
			{
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));
				new Welcome(game);
				break;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Rectangle itemRect = new Rectangle(getWidth() - 100, 35, 100, 30);
		if (itemRect.contains(e.getPoint()))
		{
			selected = 0;
		}
		else
		{
			selected = -1;
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	@Override
	public void mouseDragged(MouseEvent e)
	{}
}
