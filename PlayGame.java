// Rowan Rice
// November 13, 2019
// PlayGame.java

import java.util.Scanner;
import java.io.*;

public class PlayGame
{
	public static final int M_ROWS = 25;
	public static final int N_COLS = 75;

	public static void main (String [] args)
	{
		char [][] board = new char[M_ROWS][N_COLS];

		// SCAN IN FILE
		Scanner consoleReader = new Scanner(System.in);
		System.out.print ("Which file do you want to open? ");
		String filename = consoleReader.next();
		File file = new File(filename);
		Scanner fileReader = null;

		try 
		{ 
		   fileReader = new Scanner (file);
		}

		catch (Exception e) 
		{   
		   System.out.println("file " + file + " does not exist");
		   System.exit(0);
		}
		
		// FILL IN BOARD
		for (int row = 0; row < M_ROWS; row++)
		{
			String line = fileReader.nextLine();
			//System.out.println(line);
			for (int col = 0; col < N_COLS; col++)
			{
				board[row][col] = line.charAt(col);
			}
		}

		// CREATE WORLD USING BOARD FROM FILE
		World world1 = new World(board);
		boolean continueGame = true;

		// LOOP THROUGH THE GAME WITH THE WORLD
		while (continueGame)
		{
			Scanner read = new Scanner (System.in);
			char nextMove;

			/*  specification said there should be a non-void method
				that takes in a 2D array and returns a boolean that 
				determines whether the array is empty (all '.')
				this method is a static method within the world
				object class, so it needs to have a generation passed
				to it 
			*/
			if (World.isEmpty(world1.getCurrGeneration()))
			{
				System.out.println("Empty World - Game Over");
				continueGame = false;
			}

			else
			{
				System.out.println("Enter 'G' to Generate or 'T' to Terminate: ");
				nextMove = read.next().charAt(0);

				switch (nextMove)
				{
					case ('G'):
						continueGame = world1.generate();
						break;

					case ('T'):
						System.out.println("Terminated - Game Over");
						continueGame = false;
						break;

					default:
						System.out.println("Error: Unknown Command; Try Again");
						break;
				}
			}
		}

		System.out.println("Thanks for playing!");
	}
}