package com.lqtz.globaldomination.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import com.lqtz.globaldomination.gameplay.Game;

public class Settings extends BasicScreen
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

	private OptionsPanel footPanel;

	private Font labelFont;

	public Settings(Game game)
	{
		super("Settings", game);
		labelFont = game.fonts.sourcesans.deriveFont(Font.PLAIN, 36);
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
		resSelect = new JComboBox<String>();
		resolution.add(resText);
		resolution.add(resSelect);

		resNote = new JTextArea(resNoteTxt);
		resNote.setFont(labelFont);
		resNote.setForeground(Color.WHITE);
		resNote.setOpaque(false);
		resNote.setLineWrap(true);
		resNote.setWrapStyleWord(true);
		resNote.setEditable(false);

		fullScreen = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
		fullScreen.setOpaque(false);
		fsText = new JLabel("Full Screen?");
		fsText.setFont(labelFont);
		fsText.setForeground(Color.WHITE);
		fsSelect = new JCheckBox();
		fsSelect.setOpaque(false);
		fullScreen.add(fsText);
		fullScreen.add(fsSelect);

		bodyPanel.add(resolution);
		bodyPanel.add(resNote);
		bodyPanel.add(Box.createVerticalStrut(25));
		bodyPanel.add(fullScreen);

		// Disable expanding
		resolution.setPreferredSize(new Dimension(getWidth(), resolution
				.getPreferredSize().height));
		resolution.setMaximumSize(resolution.getPreferredSize());
		fullScreen.setPreferredSize(new Dimension(getWidth(), fullScreen
				.getPreferredSize().height));
		fullScreen.setMaximumSize(fullScreen.getPreferredSize());
		
		// resNote must have positive size for modelToView to work
		resNote.setSize(new Dimension(getWidth(), 36));
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
		resNote.setPreferredSize(new Dimension(getWidth(), limRect.height
				+ limRect.y));
		resNote.setMaximumSize(resNote.getPreferredSize());

		return bodyPanel;
	}

	@Override
	protected JComponent createFoot()
	{
		footPanel = new OptionsPanel(game, this);
		return footPanel;
	}
}
