package com.lqtz.globaldomination.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lqtz.globaldomination.graphics.ImageContentPane;
import com.lqtz.globaldomination.io.Game;

/**
 * 
 * Class for about page
 * 
 * @author Gandalf
 * 
 */
public class InfoScreen extends JFrame
{
	private static final long serialVersionUID = 1L;

	private String fileNameStr;
	private String titleStr;
	private Game game;

	private JPanel titlePanel;
	private JLabel titleLabel;
	private JPanel bodyPanel;
	private JLabel bodyLabel;
	private JPanel backPanel;
	private JButton backButton;

	/**
	 * Text to display in body (read from file)
	 */
	private String bodyText;

	/**
	 * Screen with only a header, a body, and an exit button (i.e. the about
	 * page)
	 * 
	 * @param fileNameStr
	 *            Name of the txt file with the text to display in the body (do
	 *            not include path)
	 * @param titleStr
	 *            Text to display in the title
	 * @param game
	 *            Game object for loading res
	 */
	public InfoScreen(String fileNameStr, String titleStr, Game game)
	{
		this.fileNameStr = fileNameStr;
		this.titleStr = titleStr;
		this.game = game;

		try
		{
			this.bodyText = readText();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Removes buttons
		setUndecorated(true);

		// Makes full screen
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		if (gd.isFullScreenSupported())
		{
			gd.setFullScreenWindow(this);
		}
		else
		{
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}

		// Setup screen attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Ends the program when closed
		setTitle("Global Domination");
		setContentPane(new ImageContentPane());
		addComponents();

		setVisible(true);

		start();
	}

	private void addComponents()
	{
		setLayout(new BorderLayout());

		// Draw title
		titlePanel = new JPanel();
		titlePanel.setOpaque(false);
		titleLabel = new JLabel(titleStr);
		titleLabel.setPreferredSize(new Dimension(600, 200));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(0, 0, 0, 0));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(game.fonts.goudy.deriveFont(Font.PLAIN, 128));
		titleLabel.setOpaque(false);
		titlePanel.add(titleLabel);
		add(titlePanel, BorderLayout.PAGE_START);

		// Draw title
		bodyPanel = new JPanel();
		bodyPanel.setOpaque(false);
		bodyLabel = new JLabel("<html><p>" + bodyText + "</p></html>");
		bodyLabel
				.setPreferredSize(new Dimension((int) (Toolkit
						.getDefaultToolkit().getScreenSize().getWidth()),
						(int) (Toolkit.getDefaultToolkit().getScreenSize()
								.getHeight() - 400)));
		bodyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bodyLabel.setBackground(new Color(0, 0, 0, 0));
		bodyLabel.setForeground(Color.WHITE);
		bodyLabel.setFont(game.fonts.goudy.deriveFont(Font.PLAIN, 36));
		bodyLabel.setOpaque(false);
		bodyPanel.add(bodyLabel);
		add(bodyPanel, BorderLayout.LINE_START);

		// Add button
		// TODO make pretty and like the storyboard
		backPanel = new JPanel();
		backPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		backPanel.setOpaque(false);
		backButton = new JButton("exit");
		backButton.setBackground(new Color(0, 0, 0, 0));
		backButton.setForeground(Color.YELLOW);
		backButton.setFont(game.fonts.sourcesans.deriveFont(Font.PLAIN, 32));
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.setOpaque(false);

		// Add exit screen action listener to button
		backButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				goBack();
			}
		});

		backPanel.add(backButton);
		add(backPanel, BorderLayout.SOUTH);
	}

	/**
	 * Closes window
	 */
	public void goBack()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Gets the text to display on the about screen from file
	 * 
	 * @return text in the AboutText.txt file
	 * @throws IOException
	 */
	private String readText() throws IOException
	{
		// Get a BufferedReader to read text file from the res/text/ dir
		BufferedReader br = new BufferedReader(new FileReader("res/text/"
				+ fileNameStr));
		String fileRepStr = "";	// String to hold the content of the text file
		
		try
		{
			// Initialize the StringBuilder and read lines to it
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				line = br.readLine();
			}
			
			// Convert the StringBuilder to string
			fileRepStr = sb.toString();
		}
		finally
		{
			br.close();		// Close the txt file
		}
		return fileRepStr;		// Return the string rep of the text file
	}

	private void start()
	{

	}
}
