package Find;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Main extends JFrame{
	
	JLabel[] choiceLabel;
	ImageIcon penguin, fish, puffle;
	JButton newButton = new JButton();
	Random myRandom = new Random();
	JMenuBar mainMenuBar = new JMenuBar();
	JMenuItem newMenuItem = new JMenuItem("New Game");
	JLabel guesses = new JLabel("Guesses: 0");
	
	int penguinLocation, panelAmt, width, height;
	int counter = 0;
	public static void main (String[] args) {
		new Main().setVisible(true);
	}
	
	public Main () {
		
		getGameOption();
		
		panelAmt = width * height;
		
		penguin = new ImageIcon(this.getClass().getResource("sun_knight.jpg"));
		fish = new ImageIcon(this.getClass().getResource("sun_knight_red.jpg"));
		puffle = new ImageIcon(this.getClass().getResource("sun_knight_green.jpg"));
	
		choiceLabel = new JLabel[panelAmt];
		
		for (int i = 0; i < choiceLabel.length; i++) {
			choiceLabel[i] = new JLabel();
		}
		
		setTitle("Find the Knight!");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}	
		});
		
		setJMenuBar(mainMenuBar);
		
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gridConstraints;
		
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.insets = new Insets(10, 10, 10, 10);
		
		getContentPane().add(guesses, gridConstraints);
		
		penguinLocation = myRandom.nextInt(panelAmt);
		
		int rowSet = 1;
		
		for (int i = 0; i < panelAmt; i++) {
			
			gridConstraints = new GridBagConstraints();
			choiceLabel[i].setPreferredSize(new Dimension(penguin.getIconWidth(), penguin.getIconHeight()));
			choiceLabel[i].setOpaque(true);
			choiceLabel[i].setBackground(Color.GREEN);
			
			if (i % width == 0 && i != 0) {
				rowSet++;
			}
			
			int columnSet = i - ((rowSet - 1) * width);
			
			gridConstraints.gridx = columnSet;
			gridConstraints.gridy = rowSet;
			gridConstraints.insets = new Insets(10, 10, 10, 10);
			
			getContentPane().add(choiceLabel[i], gridConstraints);
			
			choiceLabel[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					labelMouseClicked(e);
				}
			});
		}
	    mainMenuBar.add(newMenuItem);
	    newMenuItem.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	        newButtonActionPerformed(e);
	      }
	    });

	    
	    pack();
	    getContentPane().setBackground(Color.GRAY);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), 860, 950);
	}
	
	private void exitForm(WindowEvent evt) {
		System.exit(0);
	}
	

	private void labelMouseClicked(MouseEvent e) {
		Component clickedComponent = e.getComponent();
		int choice;
		for (choice = 0; choice < panelAmt; choice++) {
			if (clickedComponent == choiceLabel[choice]) {
				break;
			}
		}
		choiceLabel[choice].setBackground(Color.WHITE);
		if (choice == penguinLocation) {
			choiceLabel[choice].setIcon(penguin);
			startOverOption();
		}
		else {
			int rand = myRandom.nextInt(2);
			if (rand == 0)
				choiceLabel[choice].setIcon(fish);
			if (rand == 1)
				choiceLabel[choice].setIcon(puffle);
			counter++;
			guesses.setText("Guesses: " + counter);
			
		}
	}
	
	private void newButtonActionPerformed(ActionEvent e) {
		newGame();
	}
	
	private void newGame () {
		for (int i = 0; i < panelAmt; i++) {
			choiceLabel[i].setIcon(null);
			choiceLabel[i].setBackground(Color.GREEN);
		}
		penguinLocation = myRandom.nextInt(panelAmt);
		counter = 0;
		guesses.setText("Guesses: " + counter);
	}
	
	private void startOverOption() {
		String message = "Congratulations! You found the knight"
				+ " with " + (counter + 1) + " guesses. Would you like to play again?";
		int choice = JOptionPane.showConfirmDialog(null, message);
		if (choice == 0)
			newGame();
		else if (choice == 1)
			System.exit(0);
		else
			System.exit(0);
			
	}
	
	private void getGameOption () {
		width = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter amount of columns (between 1-5):"));
		height = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter amount of rows (between 1-5):"));
	}
}

