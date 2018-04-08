//package assignment4Game;

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
		// ADD YOUR CODE HERE
		//System.out.println(this.available[index]);

		// L'un ou l'autre
		//////////////////////////////////////////////////////////
		// for(int i = 0; i < 6; i++) {
		//
		// 	if(this.board[index][i] == 0) {
		//
		// 		this.board[index][i] = player;
		// 		this.available[index] = i + 1;
		// 		break;
		// 	}
		// }
		//////////////////////////////////////////////////////////

		// System.out.println("INDEX");
		// System.out.println(index);
		// System.out.println(this.available[index]);


		this.board[index][this.available[index]] = player;
		this.available[index] += 1;


	}

	public boolean isWinning (int lastColumnPlayed, int player){
		// ADD YOUR CODE HERE


		int row = this.available[lastColumnPlayed] - 1;
		// check vertical WIN
		// prevents null pointer exceptions
		// ECRIRE EN FOR LOOP
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
					}

					if(counter1 == 4) {
						return true;
					}
				}
			}
		}

		/////////////////////////////////////

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
			// System.out.println("POS1 " + diagonalPosition[i]);
			// System.out.println("POS2 " + diagonalPosition3[i]);

			//System.out.println("I " + i + " DIAGO POS " + diagonalPosition[i]);

			if(diagonalPosition[i] <= 5 && diagonalPosition[i] >= 0) {

				if(this.board[i][diagonalPosition[i]] == player) {
					//System.out.println("SURVIVED " + i);
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
				}

			}

			if (diagonalPosition3[i] <= 5 && diagonalPosition3[i] >= 0) {
					//System.out.println("RENTRE!!! " + diagonalPosition3[i]);
				if (this.board[i][diagonalPosition3[i]] == player) {
					//System.out.println("SURVIVED " + i);

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
				}

			}
			//System.out.println("SURVIVED " + i);
			//System.out.println(diagonalPosition[i]);



		}



		return false; // DON'T FORGET TO CHANGE THE RETURN
	}

	public void removeDisk (int index, int player){

		this.board[index][this.available[index] - 1] = 0;
		this.available[index] -= 1;

	}


	// MAKE SURE THAT IT RETURNS THE SMALLEST COLUMN
	public int canWinNextRound (int player){
		// ADD YOUR CODE HERE

		int addChecker = 0;

		for(int i = 0; i < 7; i++) {

			if(this.available[i] < 5){
				this.addDisk(i,player);
				addChecker = 1;
			}

			if(this.isWinning(i,player)) {

				if (addChecker == 1) {

					this.removeDisk(i,player);
					return i;

				}


			} else {

				if (addChecker == 1) {

					this.removeDisk(i,player);

				}
			}
		}

		return -1;

	}

	public int canWinTwoTurns (int player){
		// ADD YOUR CODE HERE

		//Code canWinTwoTurns.
		//It takes as argument the player p whose turn it is and returns i
		//such that if player p places its disk in column i,
		//whatever the other player does on next round,
		//player p can win when it’s his turn again.
		//Note that this requires that
		//the other player does not win in between

		// First we want to know who is the second player
		int player2 = 0;

		for (int i = 0; i < 7; i++){
			for (int j = 0; j < 6; j++){

				if(this.board[i][j] != player && this.board[i][j] != 0) {

					player2 = this.board[i][j];

				}
			}
		}

		//System.out.println("PLAYER2 " + player2);
		// Tracking if second player can win next turn
		if(this.canWinNextRound(player2) != -1) {
			System.out.println("OPPONENT WAS GOING TO WIN ANYWAYS NEXT TURN");
			return -1;

		}

		boolean addChecker1 = false;
		boolean addChecker2 = false;
		int counter = 0;
		int temp;
		int lastResult = -1;

		// Here we want to go through every column and add the player to the column
		// we want to make sure there is available space
		// then we want to go through every column again and add the second player
		// again making sure there is some space available
		// We have to set up a counter so that if canWinNextRound returns always
		// the same output for a fixed i (and so for all k) then we return the
		// result of canWinNextRound

		for (int i = 1; i < 7; i++){

			if(this.available[i] < 6) {
				// System.out.println("BOUCLE " + i);
				this.addDisk(i,player);
				//addChecker1 = true;

				for (int k = 0; k < 7; k++){

					if(this.available[k] < 6){

						this.addDisk(k,player2);
						//addChecker2 = true;
						temp = this.canWinNextRound(player);

						// if(i != 18) {
						// 	this.print();
						// 	System.out.println("WHY");
						//
						// }


						if(temp != -1) {
							// initialize last result
							if(counter == 0) {

								lastResult = temp;
								counter++;

							} else if (lastResult == temp) {

								counter ++;

							}

							if(counter > 6) {
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





	//
	// 	////////////////////////////////////////////
	// 	for (int i = 0; i < 7; i++){
	//
	// 		if(this.available[i] < 5){
	// 			this.addDisk(i,player);
	// 			addChecker1 = true;
	// 		}
	//
	// 		for (int k = 0; k < 7; k++){
	//
	// 			if(this.available[i] < 5){
	// 				this.addDisk(k,player2);
	// 				addChecker2 = true;
	// 			}
	//
	// 			if(this.canWinNextRound(player) != -1) {
	//
	// 				// pas sur si je dois faire plus de if
	// 				// regarder a plus d'exemples
	// 				if (addChecker1 && addChecker2) {
	// 					this.removeDisk(k,player2);
	// 					this.removeDisk(i,player);
	// 					return this.canWinNextRound(player);
	//
	// 				} else {
	// 					return this.canWinNextRound(player);
	// 				}
	// 			}
	//
	// 			if(addChecker2) {
	// 				this.removeDisk(k,player2);
	// 			}
	//
	// 		}
	//
	// 		if(addChecker1) {
	// 			this.removeDisk(i,player);
	// 		}
	//
	// 	}
	//
	// 	return -1; // DON'T FORGET TO CHANGE THE RETURN
	// }

}
