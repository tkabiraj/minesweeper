import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class P3_Kabiraj_Tania_Minesweeper {
	static int[][] grid;
	static int[][] neighbor = new int[21][21];
	Color[][] pic = new Color[21][21];

	//static int row = 1;
	//static int col = 1;
	static int maxRow = 21;
	static int maxCol = 21;
	int time = 0;
	int minesLeft = 10;
	Color color = Color.lightGray;
	MyDrawingPanel drawingPanel;
	JFrame window;
	
	public static void main(String[] arg){
		P3_Kabiraj_Tania_Minesweeper game = new P3_Kabiraj_Tania_Minesweeper();
	}
	
	public P3_Kabiraj_Tania_Minesweeper(){
		window = new JFrame("Minesweeper");
		window.setBounds(100, 100, 445, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawingPanel = new MyDrawingPanel();
		drawingPanel.setBounds(20, 20, 400,400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				drawingPanel.setPixel(i, j);
			}
		}
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		JLabel timePassedLabel = new JLabel("" +time);
		timePassedLabel.setBorder(new TitledBorder("Time Passed"));
		timePassedLabel.setBounds(100, 450, 95, 50);
		//cobutton.addActionListener(this);
		mainPanel.add(timePassedLabel);
		
		JLabel minesLeftLabel = new JLabel("" +minesLeft);
		minesLeftLabel.setBorder(new TitledBorder("Mines Left"));
		minesLeftLabel.setBounds(250, 450, 95, 50);
		//stop.addActionListener(this);
		mainPanel.add(minesLeftLabel);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu gameMenu = new JMenu("Game");
		JMenu optionMenu = new JMenu("Options");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem newGameItem = new JMenuItem("New Game", 'n');
		//openItem.addActionListener(this);
		JMenuItem numMinesItem = new JMenuItem("Set Number of Mines", 's');
		//saveItem.addActionListener(this);
		JMenuItem exitItem = new JMenuItem("Exit", 'e');
		//clearItem.addActionListener(this);
		JMenuItem aboutItem = new JMenuItem("About", 'a');
		JMenuItem howToPlayItem = new JMenuItem("How to Play", 'h');
		
		gameMenu.add(newGameItem);
		optionMenu.add(numMinesItem);
		gameMenu.add(exitItem);
		helpMenu.add(aboutItem);
		helpMenu.add(howToPlayItem);
		
		menuBar.add(gameMenu);
		menuBar.add(optionMenu);
		menuBar.add(helpMenu);
		
		window.setJMenuBar(menuBar);
		
		mainPanel.add(drawingPanel);
		window.getContentPane().add(mainPanel);
		window.setVisible(true);
	}
	private class MyDrawingPanel extends JPanel {

		public String getPixel(int x, int y){
			return "" + pic[y][x].getRed() + " " +  pic[y][x].getGreen() + " "+ pic[y][x].getBlue() + " ";
		}
		
		public void setPixel(int x, int y){
			pic[y][x] = color;
		}
		public void paintComponent(Graphics g) {
			for(int i = 0; i < 20; i++){
				for(int j = 0; j < 20; j++){
					g.setColor(pic[i][j]);
					g.fillRect(20*j, 20*i, 20, 20);
					g.setColor(Color.black);
					g.drawRect(20*j, 20*i, 20, 20);
				}
			}
		}
	}
}
