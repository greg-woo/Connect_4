
/*
Created by: GREG WOO
Program: Simulate a game of Connect4
*/

import java.io.*;

public class Game {

	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;

		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;

		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}

	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){

		c.print();

		// We want to ask for the move until the move provided has space
		// if so then we add the player to the position on the board
		// if placed, print grid and print also warning if the other player can win next turn

		try{


			boolean didWeAdd = false;

			while(didWeAdd == false) {

				int columnChosen = Integer.parseInt(keyboard.readLine());

				if(columnChosen > 6 || columnChosen < 0) {

					System.out.println("COLUMN INPUTED IS NOT VALID");

				} else if (c.available[columnChosen] < 6) {

					didWeAdd = true;
					return columnChosen;

				}
			}
		} catch (Exception e) {

			System.out.println("EXCEPTION CAUGHT");

		}

		return -1;
	}

	public static int firstMovePlayer1 (){
		return 3;
	}

	public static int movePlayer1 (int columnPlayed2, Configuration c){

		// First we want to know who is the int that represents the ai player
		int playerAI = 0;
		int playerHUMAN = c.board[columnPlayed2][(c.available[columnPlayed2] - 1)];

		for (int i = 0; i < 7; i++){
			for (int j = 0; j < 6; j++){

				if(c.board[i][j] != playerHUMAN && c.board[i][j] != 0) {

					playerAI = c.board[i][j];

				}
			}
		}

		int nextMove = c.canWinNextRound(playerAI);
		// If bot as a winning move he plays it
		if(nextMove >= 0 && nextMove <= 6) {

			return nextMove;

		}

		//if there is a move that can make him win in 2 rounds he plays it
		nextMove = c.canWinTwoTurns(playerAI);

		if(nextMove >= 0 && nextMove <= 6) {

			return nextMove;

		} else {
			// if none of these are available, he puts his disk above the other player

			if(c.available[columnPlayed2] <= 5) {

				return columnPlayed2;

			} else {

				for (int i = 1; i < 7; i++) {

					int nextColumnLeft = columnPlayed2 - i;
					int nextColumnRight = columnPlayed2 + i;

					if(nextColumnLeft >= 0 && nextColumnLeft <= 6) {

						if(c.available[nextColumnLeft] <= 5) {

							return nextColumnLeft;

						}
					}
					 if (nextColumnRight >= 0 && nextColumnRight <= 6) {

						if(c.available[nextColumnRight] <= 5) {

							return nextColumnRight;

						}
					}
				}
			}

		}
			return -1;
	}

	}
