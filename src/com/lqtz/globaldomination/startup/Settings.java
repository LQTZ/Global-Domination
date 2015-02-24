package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;

import com.lqtz.globaldomination.io.Utils;

public class Settings extends BasicScreen implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private JPanel bodyPanel;
	private JPanel resolution;
	private JLabel resText;
	private JComboBox<String> resSelect;
	private JTextArea resNote;
	private String resNoteTxt = " NOTE: Using a 1024 x 768 resolution is not "
			+ "recommended, as the graphics begin to degrade. However, the game"
			+ " is still playable.";
	private JPanel fullScreen;
	private JLabel fsText;
	private JCheckBox fsSelect;

	private SettingsPanel footPanel;

	private Font labelFont;

	private Dimension[] dims = {new Dimension(1024, 768),
			new Dimension(1152, 864), new Dimension(1280, 720),
			new Dimension(1280, 768), new Dimension(1280, 800),
			new Dimension(1200, 1024), new Dimension(1366, 768),
			new Dimension(1440, 900), new Dimension(1440, 1050),
			new Dimension(1600, 900), new Dimension(1680, 1050),
			new Dimension(1600, 1200), new Dimension(1920, 1080),
			new Dimension(1920, 1200), new Dimension(2048, 1536),
			new Dimension(2560, 1440)};
	private ArrayList<Dimension> possdims = new ArrayList<Dimension>();
	private String[] dimstrs;

	/**
	 * Settings screen
	 * 
	 * @param utils
	 *            GD {@code Utils} utility
	 */
	public Settings(Utils utils)
	{
		super("Settings", utils);
		labelFont = utils.fonts.sourcesans.deriveFont(Font.PLAIN, 36);
		Dimension currdim = Toolkit.getDefaultToolkit().getScreenSize();
		for (Dimension dim : dims)
		{
			if (dim.width <= currdim.width && dim.height <= currdim.height)
			{
				possdims.add(dim);
			}
		}
		if (!possdims.contains(currdim))
		{
			possdims.add(currdim);
		}
		Collections.reverse(possdims);

		dimstrs = new String[possdims.size()];
		for (int i = 0; i < dimstrs.length; i++)
		{
			dimstrs[i] = possdims.get(i).width + " x " + possdims.get(i).height;
		}

		createWindow();
	}

	@Override
	protected JComponent createBody()
	{
		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		bodyPanel.setOpaque(false);

		resolution = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
		resolution.setOpaque(false);
		resText = new JLabel("Resolution");
		resText.setFont(labelFont);
		resText.setForeground(Color.WHITE);
		resSelect = new JComboBox<String>(dimstrs);
		resSelect.setFont(utils.fonts.sourcesans.deriveFont(Font.PLAIN, 24));
		resSelect.setEnabled(!utils.fullScreen);
		if (utils.fullScreen)
		{
			resSelect.setSelectedIndex(0);
		}
		else
		{
			resSelect.setSelectedItem(utils.resolution.width + " x "
					+ utils.resolution.height);
		}
		resolution.add(resText);
		resolution.add(resSelect);

		resNote = new JTextArea(resNoteTxt);
		resNote.setFont(labelFont);
		resNote.setForeground(Color.WHITE);
		resNote.setOpaque(false);
		resNote.setLineWrap(true);
		resNote.setWrapStyleWord(true);
		resNote.setEditable(false);
		resNote.setFocusable(false);

		fullScreen = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
		fullScreen.setOpaque(false);
		fsText = new JLabel("Full Screen?");
		fsText.setFont(labelFont);
		fsText.setForeground(Color.WHITE);
		fsSelect = new JCheckBox();
		fsSelect.setOpaque(false);
		fsSelect.setSelected(utils.fullScreen);
		fsSelect.addActionListener(this);
		fullScreen.add(fsText);
		fullScreen.add(fsSelect);

		bodyPanel.add(resolution);
		bodyPanel.add(resNote);
		bodyPanel.add(Box.createVerticalStrut(25));
		bodyPanel.add(fullScreen);

		// Disable expanding
		resolution.setPreferredSize(new Dimension(utils.resolution.width,
				resolution.getPreferredSize().height));
		resolution.setMaximumSize(resolution.getPreferredSize());
		fullScreen.setPreferredSize(new Dimension(utils.resolution.width,
				fullScreen.getPreferredSize().height));
		fullScreen.setMaximumSize(fullScreen.getPreferredSize());

		// resNote must have positive size for modelToView to work
		resNote.setSize(new Dimension(utils.resolution.width, 36));
		Rectangle limRect = null;
		try
		{
			// Calculates position of last char
			limRect = resNote.modelToView(resNoteTxt.length() - 1);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		// Sets height so that it fits
		resNote.setPreferredSize(new Dimension(utils.resolution.width,
				limRect.height + limRect.y));
		resNote.setMaximumSize(resNote.getPreferredSize());

		return bodyPanel;
	}

	@Override
	protected JComponent createFoot()
	{
		footPanel = new SettingsPanel(utils, this);
		return footPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) // Full screen checked
	{
		resSelect.setEnabled(!fsSelect.isSelected());
		resSelect.setSelectedIndex(0);
	}

	private class SettingsPanel extends JPanel implements MouseInputListener
	{
		private static final long serialVersionUID = 1L;
		private int selected = -1;
		private Font labelFont;
		private Utils utils;
		private JFrame frame;

		private SettingsPanel(Utils utils, JFrame frame)
		{
			this.utils = utils;
			this.frame = frame;
			labelFont = utils.fonts.sourcesans.deriveFont(Font.PLAIN, 30);

			setPreferredSize(new Dimension(utils.resolution.width, 100));
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
			g.drawString("apply", utils.resolution.width - 300, 65);
			g.drawString("back", utils.resolution.width - 100, 65);
			g.setColor(new Color(240, 192, 48));
			switch (selected)
			{
				case 0:
				{
					g.fillPolygon(new int[] {utils.resolution.width - 115,
							utils.resolution.width - 115,
							utils.resolution.width - 105}, new int[] {60, 40,
							50}, 3);
					break;
				}
				case 1:
				{
					g.fillPolygon(new int[] {utils.resolution.width - 315,
							utils.resolution.width - 315,
							utils.resolution.width - 305}, new int[] {60, 40,
							50}, 3);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			switch (selected)
			{
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
				case 1: // Apply
				{
					utils.fullScreen = fsSelect.isSelected();
					utils.resolution = possdims.get(Arrays.asList(dimstrs)
							.indexOf(resSelect.getSelectedItem()));
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.dispatchEvent(new WindowEvent(frame,
							WindowEvent.WINDOW_CLOSING));
					new Settings(utils);
					break;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			Rectangle itemRect0 = new Rectangle(utils.resolution.width - 100,
					35, 100, 30);
			Rectangle itemRect1 = new Rectangle(utils.resolution.width - 300,
					35, 100, 30);
			if (itemRect0.contains(e.getPoint()))
			{
				selected = 0;
			}
			else if (itemRect1.contains(e.getPoint()))
			{
				selected = 1;
			}
			else
			{
				selected = -1;
			}
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e)
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
}
