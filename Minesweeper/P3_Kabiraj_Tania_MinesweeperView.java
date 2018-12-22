import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class P3_Kabiraj_Tania_MinesweeperView {
	MyDrawingPanel drawingPanel;
	JPanel mainPanel;
	JFrame window;
	JMenuItem numMinesItem;
	JMenuItem newGameItem;
	JMenuItem exitItem;
	JMenuItem aboutItem;
	JMenuItem howToPlayItem;
	JMenuItem AI;
	JLabel minesLeftLabel;
	JLabel timePassedLabel;
	boolean coverWholeGrid = true;
	boolean newGame = false;
	int time = 0;
	int minesLeft = 20;
	char[][] coverGrid;
	BufferedImage blank;
	BufferedImage num_0;
	BufferedImage num_1;
	BufferedImage num_2;
	BufferedImage num_3;
	BufferedImage num_4;
	BufferedImage num_5;
	BufferedImage num_6;
	BufferedImage num_7;
	BufferedImage num_8;
	BufferedImage bomb_revealed;
	BufferedImage bomb_flagged;
	BufferedImage bomb_question;
	
	//boolean[][] alreadyCovered = new boolean[20][20];
	//BufferedImage[][] pic = new BufferedImage[20][20];
	
	public P3_Kabiraj_Tania_MinesweeperView(char[][] ncoverGrid, P3_Kabiraj_Tania_AI AIi){
		
		coverGrid = ncoverGrid;
		//printGrid(coverGrid);
		window = new JFrame("Minesweeper");
		window.setBounds(100, 100, 445, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addKeyListener(AIi);
		
		drawingPanel = new MyDrawingPanel();
		//Graphics g = drawingPanel.getGraphics();
		drawingPanel.setBounds(20, 20, 400,400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());
		//drawingPanel.paintComponent(g);
		
		try{
			blank = ImageIO.read(new URL("file:images/blank.gif"));
			num_0 = ImageIO.read(new URL("file:images/num_0.gif"));
			num_1 = ImageIO.read(new URL("file:images/num_1.gif"));
			num_2 = ImageIO.read(new URL("file:images/num_2.gif"));
			num_3 = ImageIO.read(new URL("file:images/num_3.gif"));
			num_4 = ImageIO.read(new URL("file:images/num_4.gif"));
			num_5 = ImageIO.read(new URL("file:images/num_5.gif"));
			num_6 = ImageIO.read(new URL("file:images/num_6.gif"));
			num_7 = ImageIO.read(new URL("file:images/num_7.gif"));
			num_8 = ImageIO.read(new URL("file:images/num_8.gif"));
			bomb_revealed = ImageIO.read(new URL("file:images/bomb_revealed.gif"));
			bomb_flagged = ImageIO.read(new URL("file:images/bomb_flagged.gif"));
			bomb_question = ImageIO.read(new URL("file:images/bomb_question.gif"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		timePassedLabel = new JLabel("" +time);
		timePassedLabel.setBorder(new TitledBorder("Time Passed"));
		timePassedLabel.setBounds(100, 450, 95, 50);
		mainPanel.add(timePassedLabel);
		
		minesLeftLabel = new JLabel("" +minesLeft);
		minesLeftLabel.setBorder(new TitledBorder("Mines Left"));
		minesLeftLabel.setBounds(250, 450, 95, 50);
		mainPanel.add(minesLeftLabel);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu gameMenu = new JMenu("Game");
		JMenu optionMenu = new JMenu("Options");
		JMenu helpMenu = new JMenu("Help");
		
		newGameItem = new JMenuItem("New Game", 'n');
		numMinesItem = new JMenuItem("Set Number of Mines", 's');
		AI = new JMenuItem("Help from Computer AI", 'a');
		exitItem = new JMenuItem("Exit", 'e');
		aboutItem = new JMenuItem("About", 'b');
		howToPlayItem = new JMenuItem("How to Play", 'h');
		
		gameMenu.add(newGameItem);
		optionMenu.add(numMinesItem);
		optionMenu.add(AI);
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
	public void addCustomMouseListener(MouseListener m) {
	     mainPanel.addMouseListener(m);
	}
	public void addButtonListeners(ActionListener a) {
		 newGameItem.addActionListener(a);
		 numMinesItem.addActionListener(a);
		 exitItem.addActionListener(a);
		 aboutItem.addActionListener(a);
		 howToPlayItem.addActionListener(a);
		 AI.addActionListener(a);
	}
	
	private class MyDrawingPanel extends JPanel {

		static final long serialVersionUID = 1234567890L;
		
		public BufferedImage setPixel(int x, int y){
			if(coverGrid[y][x] == '-'){
				return blank;
			}
			else if(coverGrid[y][x] == ' '){
				return num_0;
			}
			else if(coverGrid[y][x] == '1'){
				return num_1;
			}
			else if(coverGrid[y][x] == '2'){
				return num_2;
			}
			else if(coverGrid[y][x] == '3'){
				return num_3;
			}
			else if(coverGrid[y][x] == '4'){
				return num_4;
			}
			else if(coverGrid[y][x] == '5'){
				return num_5;
			}
			else if(coverGrid[y][x] == '6'){
				return num_6;
			}
			else if(coverGrid[y][x] == '7'){
				return num_7;
			}
			else if(coverGrid[y][x] == '8'){
				return num_8;
			}
			else if(coverGrid[y][x] == '*'){
				return bomb_revealed;
			}
			else if(coverGrid[y][x] == 'F'){
				return bomb_flagged;
			}
			else if(coverGrid[y][x] == '?'){
				return bomb_question;
			}
				System.out.println("null");
				return null;
		}
		
		public void paintComponent(Graphics g) {
			for(int i = 0; i < 20; i++){
				for(int j = 0; j < 20; j++){
					g.drawImage(setPixel(j,i), 20*j, 20*i, 20, 20, null);
				}
			}
		}
	}
//	public void printGrid(char[][] type){
//		for(int i = 0; i < type.length; i ++){
//			for(int j = 0; j < type.length; j++){
//				System.out.print(type[i][j]);
//			}
//			System.out.println();
//		}
//	}
	
	public void setPic(){
		drawingPanel.repaint();
	}
}
