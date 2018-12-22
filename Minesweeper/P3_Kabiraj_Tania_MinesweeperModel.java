import java.util.Random;

public class P3_Kabiraj_Tania_MinesweeperModel {
	static int numRows = 20;
	static int numCols = 20;
	static char[][] grid = new char[numRows][numCols];
	static char[][] covergrid = new char[numRows][numCols];
	int bomb = 20;
	boolean bombHit = false;
	boolean gridCovered = false;
	boolean wantToPlay = true;
	
	final char BOMB = '*';
	final char EMPTY = ' ';
	final char FLAG = 'F';
	final char COVER = '-';
	
	public boolean isWon(){		
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(covergrid[i][j] == '-' || covergrid[i][j] == '?'){
					return false;
				}
			}
		}
		return true;
	}
	
	public void checkBlank(int row, int col){
		if(grid[row][col] != '0' || covergrid[row][col] == EMPTY || covergrid[row][col] == FLAG){
			if(covergrid[row][col] == FLAG){
				covergrid[row][col] = FLAG;
			}
			else if(covergrid[row][col] != EMPTY){
				covergrid[row][col] = grid[row][col];
			}
			if(grid[row][col] == BOMB){
				bombHit = true;
			}
		}else{
			covergrid[row][col] = EMPTY;
			inBounds(row, col);
		}
	}
	
	public void inBounds(int row, int col){
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0){}
				else{
					if((row + i >= 0 && row + i < numRows) && (col + j >= 0 && col + j < numCols)){
						checkBlank(row+i, col+j);
					}
				}
			}
		}
	}
	
	public void makeGrid(){
		Random r = new Random();
		int nexti;
		int nextj;
		for(int i = 0; i < bomb; i++){
			do{
				nexti = r.nextInt(numRows);
				nextj = r.nextInt(numCols);
			}while(grid[nexti][nextj] == BOMB);
			grid[nexti][nextj] = BOMB;
		}
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j] != BOMB){
					grid[i][j] = checkNeighbor(i,j);
					covergrid[i][j] = COVER;
				}else{
					covergrid[i][j] = COVER;
				}
			}
		}				
	}
	public char checkNeighbor(int row, int col){
		int counter = 0;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0){}
				else{
					if((row + i > -1 && row + i < numRows) && (col + j > -1 && col + j < numCols) && grid[row + i][col + j] == BOMB){
						counter++;
					}
				}
			}
		}
		return (char)(48+counter);
	}

}
