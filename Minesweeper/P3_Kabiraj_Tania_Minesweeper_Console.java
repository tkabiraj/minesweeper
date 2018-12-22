import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Tania Kabiraj
 * Pd. 3
 * 3/5/16
 * 
 * This lab took me about 6 hours.
 * 
 * This lab was extremely difficult especially since I kept getting an error message that says where the error
 * is but doesn't say what the error is. I got everything else to work except that error. What I got to work is
 * making the grid with all the bombs and numbers saying how many bombs surround it. I also got the flag to work
 * and getting revealing to work as along as it isn't a zero surrounded by more than one zero. If I click on one
 * zero, the recursion works perfectly and reveals everything surrounding it. If there is more than one zero 
 * surrounding that zero, then it gives multiple errors without saying what the error is.
 * Here is an example of the error that is given: 
 * at P3_Kabiraj_Tania_Minesweeper_Console.checkBlank(P3_Kabiraj_Tania_Minesweeper_Console.java:130)
 * I soon found out the error was appearing because it was basically going into an infinite loop and so the 
 * memory was going down. I found the error and then once I fixed it everything worked.
 *
 */
public class P3_Kabiraj_Tania_Minesweeper_Console {
	static int gridSize = 11;
	static char[][] grid = new char[gridSize][gridSize];
	static char[][] covergrid = new char[11][11];
	int bomb = 10;
	boolean bombHit = false;
	boolean gridCovered = false;
	boolean wantToPlay = true;
	
	final char BOMB = '*';
	final char EMPTY = ' ';
	final char FLAG = 'F';
	final char COVER = '-';
	
	public static void main(String[] args){
		P3_Kabiraj_Tania_Minesweeper_Console game = new P3_Kabiraj_Tania_Minesweeper_Console();
//		game.makeGrid();
//		game.printGrid(grid);
//		game.printGrid(covergrid);
		game.play();
	}
	
	public void play(){
		Scanner in = new Scanner(System.in);
		do{
			makeGrid();
			printGrid(grid);
			printGrid(covergrid);
			while(!bombHit){
				System.out.println("Would you like to flag a cell or reveal a cell?");
				System.out.println("Enter 'f' or 'r'");
				String cell = in.next();
				System.out.println("Enter row: ");
				int row = in.nextInt() + 1;
				System.out.println("Enter column: ");
				int col = in.nextInt() + 1;
				//System.out.println(grid[row][col]);
				if(cell.equals("f")){
					covergrid[row][col] = FLAG;
				}else if(cell.equals("r")){
					checkBlank(row, col);
				}
				printGrid(covergrid);
				//bombHit = true;
			}
			System.out.println("Sorry, you lose :(");
			System.out.println("Want to play again? (Enter 'y' or 'n')");
			if(in.next().equals("y")){
				wantToPlay = true;
				bombHit = false;
			}else if(in.next().equals("n")){
				wantToPlay = false;
				
			}
		}while(wantToPlay);
	}
	public void checkBlank(int row, int col){
		if(grid[row][col] != '0' || covergrid[row][col] == EMPTY){
			if(covergrid[row][col] != EMPTY){
				covergrid[row][col] = grid[row][col];
			}
			if(grid[row][col] == BOMB){
				bombHit = true;
			}
		}else{
			covergrid[row][col] = EMPTY;
			//printGrid(covergrid);
			inBounds(row, col);
//			if(row > 1 && col > 1){
//				checkBlank(row-1, col-1);
//			}
//			if(row > 1){
//				checkBlank(row-1, col);
//			}
//			if(row > 1 && col < 10){
//				checkBlank(row-1, col +1);
//			}
//			if(col < 10){
//				checkBlank(row, col+1);
//			}
//			if(row < 10 && col < 10){
//				checkBlank(row + 1, col +1);
//			}
//			if(row < 10){
//				checkBlank(row+1, col);
//			}
//			if(row < 10 && col > 1){
//				checkBlank(row +1, col -1);
//			}
//			if(col > 1){
//				checkBlank(row, col -1);
//			}
		}
	}
	
	
	public void inBounds(int row, int col){
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0){}
				else{
					if((row + i > 0 && row + i < gridSize) && (col + j > 0 && col + j < gridSize)){
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
				nexti = r.nextInt(gridSize);
			}while(nexti== 0);
			do{
				nextj = r.nextInt(gridSize);
			}while(nextj== 0);
			grid[nexti][nextj] = BOMB;
		}
		for(int i = 0; i < gridSize; i++){
			for(int j = 0; j < gridSize; j++){
				if(i == 0 && j == 0){
					grid[i][j] = EMPTY;
					covergrid[i][j] = EMPTY;
				}
				else if(i == 0 && j != 0){
					grid[i][j] = (char)(47+j);
					covergrid[i][j] = (char)(47+j);
				}
				else if(j == 0 && i != 0){
					grid[i][j] = (char)(47+i);
					covergrid[i][j] = (char)(47+i);
				}
				else if(grid[i][j] != BOMB){
					grid[i][j] = checkNeighbor(i,j);
					covergrid[i][j] = COVER;
				}else{
					covergrid[i][j] = COVER;
				}
			}
		}				
	}
	public char checkNeighbor(int i, int j){
		int counter = 0;
		if (i > 0 && grid[i - 1][j] == BOMB) {
			counter++;
		}
		if (i < 10) {
			if (grid[i + 1][j] == BOMB) {
				counter++;
			}
		}
		if (j < 10) {
			if (grid[i][j + 1] == BOMB) {
				counter++;
			}
		}
		if (j > 0 &&grid[i][j - 1] == BOMB) {
			counter++;
		}
		if (i > 0 && j > 0 && grid[i - 1][j - 1] == BOMB) {
			counter++;
		}
		if (i > 0 && j < 10) {
			if (grid[i - 1][j + 1] == BOMB) {
				counter++;
			}
		}
		if (j > 0 && i < 10) {
			if (grid[i + 1][j - 1] == BOMB) {
				counter++;
			}
		}

		if (i <10 && j < 10) {
			if (grid[i + 1][j + 1] == BOMB) {
				counter++;
			}
		}
		return (char)(48+counter);
	}
	
	public void printGrid(char[][] type){
		for(int i = 0; i < gridSize; i ++){
			for(int j = 0; j < gridSize; j++){
				System.out.print(type[i][j] + " ");
			}
			System.out.println();
		}
	}
}
