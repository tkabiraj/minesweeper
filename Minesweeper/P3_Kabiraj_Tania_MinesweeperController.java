import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;


public class P3_Kabiraj_Tania_MinesweeperController {
	P3_Kabiraj_Tania_MinesweeperModel model = new P3_Kabiraj_Tania_MinesweeperModel();
	P3_Kabiraj_Tania_MinesweeperView view;
	P3_Kabiraj_Tania_AI AI;
	int minesLeft = model.bomb;
	char[][] grid = model.grid;
	int delay = 0;
	javax.swing.Timer timer = new javax.swing.Timer(delay, null);
	String messege;
	boolean useAI = false;
	public P3_Kabiraj_Tania_MinesweeperController(){
		Scanner in = new Scanner(System.in);
		model.makeGrid();
		timer.start();
		
		timer.setActionCommand("Running");
		AI = new P3_Kabiraj_Tania_AI(this);
		view = new P3_Kabiraj_Tania_MinesweeperView(model.covergrid, AI);
		view.minesLeftLabel.setText("" + minesLeft);
		//view.printGrid(model.grid);
		view.addButtonListeners(new CustomActionListener());
		this.addButtonListeners(new CustomActionListener());
		view.addCustomMouseListener(new CustomMouseListener());
	}
	public void gameOver(){
   	 if((model.isWon() && minesLeft ==0) || model.bombHit){
   		 if(model.bombHit){
   			 messege = "You lost :(";
   		 }else if(useAI){
   			 messege = "Congrats you won with the help of AI!";
   		 }else if(!useAI){
   			 messege = "Congrats you won by yourself!:)";
   		 }
				int playAgain = JOptionPane.showConfirmDialog(null,messege+ " Do you want to play again?");
	    		 if(playAgain == JOptionPane.YES_OPTION){
	    			 model.grid = new char[model.numRows][model.numCols];
	    			 model.makeGrid();
	 				 //view.printGrid(model.grid);
	 				 view.setPic();
	 				 view.time = 0;
	 				 model.bombHit = false;
	 				 minesLeft = model.bomb;
	 				 view.minesLeftLabel.setText("" + minesLeft);
	 				 AI.state = AI.GUESS_MODE;
	 				 useAI = false;
	    		 }else if(playAgain == JOptionPane.NO_OPTION){
	    			 view.window.dispose();
	    		 }
			}
    }
	
	public void addButtonListeners(CustomActionListener a) {
		timer.addActionListener(a);
	}
	private class CustomActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Running")){
				view.time++;
				if(view.time % 10000 == 0){
					view.timePassedLabel.setText("" +view.time/10000);
				}
			}
			if(e.getSource() == view.exitItem){
				view.window.dispose();
			}
			if(e.getSource() == view.AI){
				AI.automaticPlay();
			}
			if(e.getSource() == view.newGameItem){
				model.grid = new char[model.numRows][model.numCols];
				model.makeGrid();
				//view.printGrid(model.grid);
				view.setPic();
				view.time = 0;
				AI.state = AI.GUESS_MODE;
				minesLeft = model.bomb;
				view.minesLeftLabel.setText("" + minesLeft);
				useAI = false;
			}
			if(e.getSource() == view.numMinesItem){
				do{
					model.bomb = Integer.parseInt(JOptionPane.showInputDialog("How many mines?"));
				}while(model.bomb < 0 || model.bomb > 400);
				model.grid = new char[model.numRows][model.numCols];
				model.makeGrid();
				//view.printGrid(model.grid);
				view.setPic();
				view.time = 0;
				minesLeft = model.bomb;
				view.minesLeftLabel.setText("" + minesLeft);
				AI.state = AI.GUESS_MODE;
				useAI = false;
			}
			if(e.getSource() == view.howToPlayItem){
				JEditorPane helpContent = null;
				try {
					helpContent = new JEditorPane(new URL("file:HowToPlayMinesweeper.html"));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JScrollPane helpPane = new JScrollPane(helpContent);
				helpPane.setMinimumSize(new Dimension(500,1000));
				JOptionPane.showMessageDialog(null, helpPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);
			}
			if(e.getSource() == view.aboutItem){
				JEditorPane aboutContent = null;
				try {
					aboutContent = new JEditorPane(new URL("file:AboutMinesweeper.html"));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JScrollPane aboutPane = new JScrollPane(aboutContent);
				JOptionPane.showMessageDialog(null, aboutPane, "About Minesweeper", JOptionPane.PLAIN_MESSAGE, null);
			}
		}
	}
	private class CustomMouseListener implements MouseListener {
	     @Override
	     public void mouseClicked(MouseEvent e) {
	    	 int xpos = ((e.getX()-20) - (e.getX()%20))/20;
	    	 int ypos = ((e.getY() -20)- (e.getY()%20))/20;
	    	 view.minesLeft = minesLeft;
	    	if(e.getButton() == MouseEvent.BUTTON1){
	    		if(e.getX() >=20 && e.getY() >= 20 && e.getX() <= 420 && e.getY() <= 420){
	 				model.checkBlank(ypos,xpos);
	 				view.setPic();
	    		}
	    	}
	 		if(e.getButton() == MouseEvent.BUTTON3){
	 			if(e.getX() >= 20 && e.getY() >= 20 && e.getX() <= 420 && e.getY() <= 420){	
	 				if(model.covergrid[ypos][xpos] == model.COVER){
	 					model.covergrid[ypos][xpos] = model.FLAG;
	 					minesLeft--;
	 				}else if(model.covergrid[ypos][xpos] == model.FLAG){
	 					model.covergrid[ypos][xpos] = '?';
	 					minesLeft++;
	 				}else if(model.covergrid[ypos][xpos] == '?'){
	 					model.covergrid[ypos][xpos] = model.COVER;
	 				}
	 				
	 				view.minesLeftLabel.setText("" +minesLeft);
	 				view.coverGrid = model.covergrid;
	 				view.setPic();
	 			}
	 		}
	 		gameOver();
	     }
	   

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}
}