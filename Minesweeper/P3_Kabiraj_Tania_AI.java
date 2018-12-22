import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class P3_Kabiraj_Tania_AI implements KeyListener{
	final int GUESS_MODE = 0;
	final int FLAG_MODE = 1;
	final int REVEAL_MODE = 2;
	int state = GUESS_MODE;
	P3_Kabiraj_Tania_MinesweeperController controller;
	char[][] coverGrid;
	Random r = new Random();
	int freeMines;
	int rows;
	int cols;

	public P3_Kabiraj_Tania_AI(P3_Kabiraj_Tania_MinesweeperController c){
		controller = c;
		coverGrid = c.model.covergrid;
		rows = controller.model.numRows;
		cols = controller.model.numCols;
	}
	
	public void automaticPlay(){
		if(state == GUESS_MODE){
			covertwenty();
		}else if(state == FLAG_MODE){
			flagAll();
		}else if(state == REVEAL_MODE){
			revealAll();
		}
		controller.gameOver();
	}
	
	public void revealAll(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++) {
				if(coverGrid[i][j] == '1' || coverGrid[i][j] == '2' || coverGrid[i][j] == '3'
					|| coverGrid[i][j] == '4' || coverGrid[i][j] == '5' || coverGrid[i][j] == '6'
					|| coverGrid[i][j] == '7' || coverGrid[i][j] == '8'){
					revealNeighbors(i,j,"" + coverGrid[i][j]);
				}
			}
		}
		controller.view.setPic();
		state= FLAG_MODE;
	}
	
	public void revealNeighbors(int row, int col, String str){
		int count = 0;
		int num = Integer.parseInt(str);
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0){}
				else{
					if((row + i >= 0 && row + i < rows) && (col + j >= 0 && col + j < cols)){ 
						if(coverGrid[row + i][col + j] == 'F'){
							count++;
						}
					}
				}
			}			
		}
//		if(num==1)
//			System.out.println(count);
		if(count == num){
			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					if((row + i >= 0 && row + i < rows) && (col + j >= 0 && col + j < cols)){ 
						if(coverGrid[row+i][col+j] == '-'){
							controller.model.covergrid[row+i][col+j] = controller.model.grid[row+i][col+j];
							
						}
					}
				}
			}
		}
	}
	
	public void flagAll(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++) {
				if(coverGrid[i][j] == '1' || coverGrid[i][j] == '2' || coverGrid[i][j] == '3'
					|| coverGrid[i][j] == '4' || coverGrid[i][j] == '5' || coverGrid[i][j] == '6'
					|| coverGrid[i][j] == '7' || coverGrid[i][j] == '8'){
					flagNeighbors(i,j, "" + coverGrid[i][j]);
				}
			}
		}
		controller.view.setPic();
		state = REVEAL_MODE;
	}
	
	public void flagNeighbors(int row, int col, String str){
		int count = 0;
		int flagcount = 1;
		int num = Integer.parseInt(str);
		boolean flagged = false;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0){}
				else{
					if((row + i >= 0 && row + i < rows) && (col + j >= 0 && col + j < cols)){ 
						if(coverGrid[row + i][col + j] == '-'){
							count++;
						}
						else if(coverGrid[row + i][col + j] == 'F'){
							flagcount++;
//							if(flagcount == num)
								flagged = true;
//							if(str.equals("2"))
//								System.out.println(flagcount);
						}
					}
				}
			}
		}
		if(!flagged && count == num){
			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					if(i == 0 && j == 0){}
					else{
						if((row + i >= 0 && row + i < rows) && (col + j >= 0 && col + j < cols) 
							&& coverGrid[row + i][col + j] == '-'){
							controller.model.covergrid[row+i][col+j] = 'F';
							controller.minesLeft--;
							controller.view.minesLeftLabel.setText("" +controller.minesLeft);
						}
					}
				}
			}
		}
	}
	
	public void covertwenty(){
		freeMines = cols* rows-controller.model.bomb;
		int xpos;
		int ypos;
		do{
			xpos = r.nextInt(cols);
			ypos = r.nextInt(rows);
		}while(coverGrid[ypos][xpos] == controller.model.EMPTY);
		controller.model.checkBlank(ypos, xpos);
		controller.view.setPic();
		int count = 0;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(coverGrid[ypos][xpos] == controller.model.EMPTY){
					count++;
				}
			}
		}
		if(cols* rows - count < .2*freeMines){
			state = FLAG_MODE;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A){
			automaticPlay();
			controller.useAI = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}

