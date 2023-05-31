package game2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	private int[][] board;
	private int score;
	
	public Game() {
		board = new int[4][4];
		score = 0;
		initBoard();
	}
	
//	display menu Option
	
	public void displayMenuOption() {
		System.out.println("Enter 0 to Move Left");
		System.out.println("Enter 1 to Move Right");
		System.out.println("Enter 2 to Move Up");
		System.out.println("Enter 3 to Move Down");
		System.out.println("Enter 4 to Move Restart");
		System.out.println("Enter 5 to Move Quit");     
	}
	
	//show menu logic
	
	public void showMenu() {
		int input = 7; 
		while(input !=5) {   // till unless Quit option is not selected, I will show menu
			System.out.println("Game Menu");
			displayMenuOption();
			Scanner sc = new Scanner(System.in);
			System.out.println("Please Enter Your Input: ");
			input = sc.nextInt(); 
			if(input < 0 || input > 5)  {  //check the input from game menu, if it is valid or not
				System.out.println("Please enter a valid input from the given options");
				displayMenuOption();
			}else { 	//once confirmed that value is valid, we will call function for each option
				if(input == 0) {
					moveLeft(); 
				} else if(input == 1) {
					moveRight();
				} else if(input == 2) {
					moveUp();
				} else if(input == 3) {
					moveDown();
				} else if(input == 4) {
					restart();
					displayBoard();
				} else if(input == 5) {
					System.out.println("Thank you for playing. You may close the game.");
					break;
				}
				
				addRandomTile(); 		//once we have moved our tile, we must add next tile to the game, which should be moved in next step of game
				displayBoard();			//Once a step is complete, since we are not using GUI, we will declare that this step is completed through a text.
				System.out.println("Score: " + getScore()); //update and print our score
				
				if(isGameOver()) {		//before moving to next loop, we will check if game is over or not
					System.out.println("Game Over.");
					break;
				}
			}
		}
	}
	
	
	public void initBoard() {
		//Add two random tiles
		addRandomTile();
		addRandomTile();
	}
	
	private void addRandomTile() {
		//Find all empty cells
		List<int[]> emptyCells = new ArrayList<>();
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				if (board[i][j] == 0) {
					emptyCells.add(new int[] {i,j});
				}
			}
		}
		//if there are no empty cells, return
		if(emptyCells.isEmpty()) {
			return;
		}
		//pick a random empty cell and add a tile with 2 or 4 to it
		int[] cell = emptyCells.get((int)(Math.random()*emptyCells.size()));
		board[cell[0]][cell[1]] = (Math.random()<0.9) ? 2 : 4;
	}
	
	//moving functions
	
	//move
	
	public boolean moveUp() {
		boolean moved = false;
		for(int j=0; j<4;j++) {  //row
			int lastMergedRow = -1;
			for(int i = 0; i<4; i++) { //column
				if (board[i][j] != 0) {
					int k=i;
					while(k>0 && board[k-1][j] == 0) {
						board[k-1][j] = board[k][j];
						board[k][j] = 0;
						k--;
						moved = true;
					}
					if (k>0 && board[k-1][j] == board[k][j] && lastMergedRow != k-1) {
						board[k-1][j] *= 2;
						addScore(board[k-1][j]);
						board[k][j] =0;
						lastMergedRow= k-1;
						moved = true;
					}
				}
			}
			
		}
		return moved;
	}
	
	public boolean moveDown() {
		boolean moved = false;
		for(int j=0; j<4;j++) {  //row
			int lastMergedRow = -1;
			for(int i = 3; i>=0; i--) { //column
				if (board[i][j] != 0) {
					int k=i;
					while(k<3 && board[k+1][j] == 0) {
						board[k+1][j] = board[k][j];
						board[k][j] = 0;
						k++;
						moved = true;
					}
					if (k<3 && board[k+1][j] == board[k][j] && lastMergedRow != k+1) {
						board[k+1][j] *= 2;
						addScore(board[k+1][j]);
						board[k][j] =0;
						lastMergedRow= k+1;
						moved = true;
					}
				}
			}
			
		}
		return moved;
	}
	
	
	public boolean moveLeft() {
		boolean moved = false;
		for(int i=0; i<4;i++) {  //column
			int lastMergedCol = -1;
			for(int j = 0; j<4; j++) { //row
				if (board[i][j] != 0) {
					int k=j;
					while(k>0 && board[i][k-1] == 0) {
						board[i][k-1] = board[i][k];
						board[i][k] = 0;
						k--;
						moved = true;
					}
					if (k>0 && board[i][k-1] == board[i][k] && lastMergedCol != k-1) {
						board[i][k-1] *= 2;
						addScore(board[i][k-1]);
						board[i][k] =0;
						lastMergedCol= k-1;
						moved = true;
					}
				}
			}
			
		}
		return moved;
	}
	
	public boolean moveRight() {
		boolean moved = false;
		for(int i=0; i<4;i++) {  //column
			int lastMergedCol = -1;
			for(int j = 3; j>=0; j--) { //row
				if (board[i][j] != 0) {
					int k=j;
					while(k<3 && board[i][k-1] == 0) {
						board[i][k+1] = board[i][k];
						board[i][k] = 0;
						k++;
						moved = true;
					}
					if (k<3 && board[i][k-1] == board[i][k] && lastMergedCol != k+1) {
						board[i][k+1] *= 2;
						addScore(board[i][k+1]);
						board[i][k] =0;
						lastMergedCol= k+1;
						moved = true;
					}
				}
			}
			
		}
		return moved;
	}
	
	
	//if tiles merge, update score 
	
	public void addScore(int increment) {
		score += increment;
	}
	

	
	//isGameOver
	public boolean isGameOver() {
		
		
		//Check if cells are empty or not
		
		for(int i=0;i<4;i++) { // scan every row
			for(int j=0;j<4;j++) { // scan every column inside row 
				
				if(board[i][j] == 0) {   //board[i][j]==0 means empty cell
					return false;     //if any empty cell is there, false is returned
				}
				
			}
		}
		
		//check if adjacent cells have the same value or not : no
		for(int i=0;i<4;i++) { //row
			for(int j=0;j<4;j++) { //column
				
				if(i<3 && board[i][j] == board[i+1][j]) { //i=0 i=1 i=2 adjacent cell in row is same or not
					return false;   //any adjacent is same, it will return false
				}
				if(i<3 && board[i][j] == board[i][j+1]) {//i=0 i=1 i=2 adjacent cell in column is same or not
					return false;   //any adjacent is same, it will return false
				}
			}
			
		}
	
		//if we have not returned any false, then game is over
		return true;
	}
	//DisplayBoARD
	public void displayBoard() {
		System.out.println("----");
		for(int i=0;i<4;i++) { // scan every row
			for(int j=0;j<4;j++) { // scan every column inside row 
				
				System.out.print("|");
				
				if(board[i][j] == 0) {
					System.out.print("   ");
				} else {
					System.out.printf("%4d", board[i][j]);
				}
				System.out.println("|");
				System.out.println("----");
			}
		}
	}
	//GetScore
	public int getScore() {
		return score;
	}
	//restart
	public void restart() {
		//reset the game  board
		score = 0;
		board = new int[4][4];
		initBoard();
	}
	//quit
	public void quit() {
		//quit the game
	
	}
	
}
