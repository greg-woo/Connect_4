
/*
Created by: GREG WOO
Program: Simulate a game of Connect4
*/

public class Configuration {

	public int[][] board;
	public int[] available;
	boolean spaceLeft;

	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}

	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}

	public void addDisk (int index, int player){

		this.board[index][this.available[index]] = player;
		this.available[index] += 1;

	}

	public boolean isWinning (int lastColumnPlayed, int player){

		int row = this.available[lastColumnPlayed] - 1;
		// check vertical WIN
		// prevents null pointer exceptions
		if(this.available[lastColumnPlayed] >= 4) {
			if(this.board[lastColumnPlayed][row] == player) {
				if(this.board[lastColumnPlayed][row - 1] == player) {
					if(this.board[lastColumnPlayed][row - 2] == player) {
						if(this.board[lastColumnPlayed][row - 3] == player) {
							return true;
						}
					}
				}
			}
		}

		// check horizontal WIN
		int counter1 = 0;
		boolean isPrevious1 = false;

		// checking each column locking the row number
		for(int i = 0; i < 7; i++) {

			if(this.board[i][row] == player) {
				// avoiding null pointers
				if (i != 0) {
					isPrevious1 = this.board[i - 1][row] == player;
				}

				if(counter1 == 0) {
					counter1 = 1;
				} else {

					if(isPrevious1) {
						counter1++;
					} else {

						counter1 = 0;

					}

					if(counter1 == 4) {

						return true;
					}
				}
			}
		}

		// checking diagonal (en gros check all diagonal positions)
		// si last placed token had coordinates 4,2
		// then check until row is 0 and row is 5 || column reaches end
		// --> check 2,0 / 3,1 / |4,2| / 5,3 / 6,4

		int counter2 = 0;
		boolean isPrevious2 = false;
		int[] diagonalPosition = new int[7];

		int counter3 = 0;
		boolean isPrevious3 = false;
		int[] diagonalPosition3 = new int[7];

		for(int i = 0; i < 7; i++) {
			//DIAG QUI MONTE DE LA GAUCHE VERS LA DROITE
			diagonalPosition[i] = row - (lastColumnPlayed - i);

			//DIAG QUI DESCEND DE LA GAUCHE VERS LA DROITE
			diagonalPosition3[i] = row - i + lastColumnPlayed;

			if(diagonalPosition[i] <= 5 && diagonalPosition[i] >= 0) {

				if(this.board[i][diagonalPosition[i]] == player) {

					// avoiding null pointers
					if (i != 0 && diagonalPosition[i] != 0) {
						isPrevious2 = this.board[i-1][diagonalPosition[i-1]] == player;
					}

					if(counter2 == 0) {
						counter2 = 1;
					} else {

						if(isPrevious2) {
							counter2++;
						}

						if(counter2 == 4) {

							return true;
						}
					}
				} else {

					counter2 = 0;

				}

			}

			if (diagonalPosition3[i] <= 5 && diagonalPosition3[i] >= 0) {

				if (this.board[i][diagonalPosition3[i]] == player) {

					if (i != 0 && diagonalPosition3[i] != 5) {
						isPrevious3 = this.board[i-1][diagonalPosition3[i-1]] == player;
					}

					if(counter3 == 0) {
						counter3 = 1;
					} else {

						if(isPrevious3) {
							counter3++;
						}
						if(counter3 == 4) {

							return true;
						}
					}
				} else {

					counter3 = 0;

				}
			}
		}



		return false;
	}

	public void removeDisk (int index, int player){

		if(this.board[index][this.available[index] - 1] == player) {

			this.available[index] -= 1;
			this.board[index][this.available[index]] = 0;

		} else {

			System.out.println("MISTAKE IN REMOVE DISK!!!");

		}


	}

	public int canWinNextRound (int player){

		for(int i = 0; i < 7; i++) {

			if(this.available[i] < 6){
				this.addDisk(i,player);

				if(this.isWinning(i,player)) {
					this.removeDisk(i,player);
					return i;

				} else {
					this.removeDisk(i,player);
				}
			}

		}
		return -1;
	}

	public int canWinTwoTurns (int player){

		// First we want to know who is the second player
		int player2 = 0;

		for (int i = 0; i < 7; i++){
			for (int j = 0; j < 6; j++){

				if(this.board[i][j] != player && this.board[i][j] != 0) {

					player2 = this.board[i][j];
				}
			}
		}

		// Tracking if second player can win next turn
		if(this.canWinNextRound(player2) != -1) {
			//System.out.println("OPPONENT WAS GOING TO WIN ANYWAYS NEXT TURN");
			return -1;

		}

		int counter = 0;
		int temp;
		int lastResult = -1;
		int possiblePlays4Player2 = 0;

		// Here we want to go through every column and add the player to the column
		// we want to make sure there is available space
		// then we want to go through every column again and add the second player
		// again making sure there is some space available
		// We have to set up a counter so that if canWinNextRound returns always
		// the same output for a fixed i (and so for all k) then we return the
		// result of canWinNextRound

		for (int i = 0; i < 7; i++){

			counter = 0;
			if(this.available[i] < 6) {

				this.addDisk(i,player);

				possiblePlays4Player2 = 0;
				for (int j = 0; j < 7; j++){
					if(this.available[j] < 6){
						possiblePlays4Player2 ++;
					}
				}

				for (int k = 0; k < 7; k++){
					if(this.available[k] < 6){

						this.addDisk(k,player2);

						temp = this.canWinNextRound(player);

						if(temp != -1) {
							// initialize last result
							if(counter == 0) {

								lastResult = temp;
								counter++;

							} else if (lastResult == temp) {

								counter ++;

							} else {

								counter = 0;
							}

							if(counter >=  possiblePlays4Player2) {
								this.removeDisk(k,player2);
								this.removeDisk(i,player);

								return temp;
							}
						}
						this.removeDisk(k,player2);
					}
				}

				this.removeDisk(i,player);

			}
		}

		return -1;
	}

}
