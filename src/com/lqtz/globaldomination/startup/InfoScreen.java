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
package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

import com.lqtz.globaldomination.io.Utils;

public class InfoScreen extends BasicScreen {
	private static final long serialVersionUID = 1L;
	private JTextArea bodyTextArea;
	private JScrollPane bodyScrollPane;
	private InfoPanel footPanel;
	private String bodyText;

	/**
	 * Read the text for the page
	 *
	 * @param input
	 *            {@code InputStream} of the .txt file with the page's body text
	 * @param titleStr
	 *            title of the page
	 * @param utils
	 *            GD {@code Utils} utility
	 * @throws IOException
	 *             error loading body text
	 */
	public InfoScreen(InputStream input, String titleStr, Utils utils)
			throws IOException {
		this(read(input), titleStr, utils);
	}

	/**
	 * Screen with only a header, a body, and an exit button (e.g. the about
	 * page)
	 *
	 * @param text
	 *            text to display in window
	 * @param titleStr
	 *            text to display in the title
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public InfoScreen(String text, String titleStr, Utils utils) {
		super(titleStr, utils);
		bodyText = text;
		createWindow();
	}

	@Override
	protected JComponent createBody() {
		bodyTextArea = new JTextArea(bodyText);
		bodyTextArea.setBackground(new Color(0, 0, 0, 0));
		bodyTextArea.setForeground(Color.WHITE);
		bodyTextArea.setFont(utils.fonts.sourcesans.deriveFont(Font.PLAIN, 36));
		bodyTextArea.setOpaque(false);
		bodyTextArea.setLineWrap(true);
		bodyTextArea.setWrapStyleWord(true);
		bodyTextArea.setEditable(false);
		bodyTextArea.setFocusable(false);
		bodyScrollPane = new JScrollPane(bodyTextArea);
		bodyScrollPane.setPreferredSize(new Dimension(utils.resolution.width,
				utils.resolution.height - 250));
		bodyScrollPane.setOpaque(false);
		bodyScrollPane.getViewport().setOpaque(false);

		// Removed border
		// TODO beautify scroll bars
		Border nullBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		bodyScrollPane.setBorder(nullBorder);

		return bodyScrollPane;
	}

	@Override
	protected JComponent createFoot() {
		footPanel = new InfoPanel(utils, this);
		return footPanel;
	}

	private static String read(InputStream input) {
		Scanner unmodscan = new Scanner(input);
		Scanner s = unmodscan.useDelimiter("\\A");
		String str = s.next();
		unmodscan.close();
		s.close();
		return str;
	}

	private class InfoPanel extends JPanel implements MouseInputListener {
		private static final long serialVersionUID = 1L;
		private int selected = -1;
		private Font labelFont;
		private Utils utils;
		private JFrame frame;

		private InfoPanel(Utils utils, JFrame frame) {
			this.utils = utils;
			this.frame = frame;
			labelFont = utils.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

			setPreferredSize(new Dimension(utils.resolution.width, 100));
			setOpaque(false);

			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(192, 192, 192));
			g.setFont(labelFont);
			g.drawString("back", utils.resolution.width - 100, 65);
			if (selected != -1) {
				g.setColor(new Color(240, 192, 48));
				g.fillPolygon(new int[] { utils.resolution.width - 115,
						utils.resolution.width - 115,
						utils.resolution.width - 105 },
						new int[] { 60, 40, 50 }, 3);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			switch (selected) {
			case -1: // Invalid button push
			{
				break;
			}
			case 0: // Welcome
			{
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));
				new Welcome(utils);
				break;
			}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Rectangle itemRect = new Rectangle(utils.resolution.width - 100,
					35, 100, 30);
			if (itemRect.contains(e.getPoint())) {
				selected = 0;
			} else {
				selected = -1;
			}
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}
	}
}
