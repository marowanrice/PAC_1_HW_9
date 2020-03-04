// Rowan Rice
// November 13, 2019
// World.java

import java.util.Scanner;

/* World is a class that holds two 2D arrays that represent worlds
of organisms and empty cells.  One 2D array holds the current generation
of organisms; the other holds the previous generation

A world can be used to Play the Game of Life.
*/

public class World
{
	public static final int M_ROWS = 25;
	public static final int N_COLS = 75;
	public static final char ORGANISM = 'X';
	public static final char EMPTY_CELL = '.';
	
	private char [][] currGeneration;
	private char [][] prevGeneration;
	private int generation;
	private int totalRows;
	private int totalCols;

	public static final int ABOVE_ROW = -1;
	public static final int LEFT_COL = -1;
	public static final int BELOW_ROW = 1;
	public static final int RIGHT_COL = 1;
	public static final int SAME_ROW = 0;
	public static final int SAME_COL = 0;

	World(char [][] board)
	{
		generation = 0;
		totalRows = M_ROWS + 2;
		totalCols = N_COLS + 2;

		// create board with border of 1 cell per end of row/col
		currGeneration = new char[totalRows][totalCols];

		// loop to fill 1st border row
		for (int i = 0; i < totalCols; i++)
		{
			currGeneration[0][i] = EMPTY_CELL;
		}

		// loop that brings in board
		for (int row = 1; row <= M_ROWS; row++)
		{
			currGeneration[row][0] = EMPTY_CELL; // 1st border col

			for (int col = 1; col <= N_COLS; col++)
			{
				currGeneration[row][col] = board[row-1][col-1];
			}

			currGeneration[row][N_COLS + 1] = EMPTY_CELL; // last border col
		}

		// loop to fill last border row
		for (int i = 0; i < totalCols; i++)
		{
			currGeneration[M_ROWS + 1][i] = EMPTY_CELL;
		}

		prevGeneration = copyGeneration(currGeneration, totalRows, totalCols);
		printGeneration(currGeneration);
	}

	// gives a copy of the current generation to the caller
	public char[][] getCurrGeneration()
	{
		char[][] genArray = copyGeneration(currGeneration, totalRows, totalCols);

		return genArray;
	}
	
	// this is a non-void method in the project specification that takes in a 
	// world represented by a 2D array and the coordinates of a single cell 
	// within that 2D array
	// it returns the number of neighbors of that cell
	public int getNumNeighbors(char [][] world, int x, int y)
	{
		int neighbors = 0;

		// top left diagonal
		neighbors += cellResidents(world[x + ABOVE_ROW][y + LEFT_COL]);
		// top
		neighbors += cellResidents(world[x + ABOVE_ROW][y + SAME_COL]);
		// top right diagonal
		neighbors += cellResidents(world[x + ABOVE_ROW][y + RIGHT_COL]);
		// left
		neighbors += cellResidents(world[x + SAME_ROW][y + LEFT_COL]);
		// right
		neighbors += cellResidents(world[x + SAME_ROW][y + RIGHT_COL]);
		// below left diagonal
		neighbors += cellResidents(world[x + BELOW_ROW][y + LEFT_COL]);
		// below
		neighbors += cellResidents(world[x + BELOW_ROW][y + SAME_COL]);
		// below right diagonal
		neighbors += cellResidents(world[x + BELOW_ROW][y + RIGHT_COL]);

		return neighbors;

	}

	// specification said there should be a non-void method
	// that takes in a generation represented by a 2D array and 
	// returns a boolean that determines whether the array is empty (all '.')
	public static boolean isEmpty(char [][] gen)
	{
		int numOrganisms = 0;

		for (int row = 0; row < gen.length; row++)
		{
			for (int col = 0; col < gen[row].length; col++)
			{
				if(gen[row][col] == ORGANISM)
				{
					numOrganisms++;
				}
			}
		}

		return (numOrganisms == 0);
	}

	/* GENERATE takes the current generation and tries to make a new generation
		RETURNS TRUE if the new generation is created
		RETURNS FALSE if new generation is same as the last generation
	*/
	public boolean generate()
	{
		char[][] newGeneration = new char[totalRows][totalCols];
		int numNeighbors = 0;
		boolean isCellVacant = false;

		// loop to fill 1st border row
		for (int i = 0; i < totalCols; i++)
		{
			newGeneration[0][i] = EMPTY_CELL;
		}

		// loop that brings in board
		for (int row = 1; row <= M_ROWS; row++)
		{
			newGeneration[row][0] = EMPTY_CELL; // 1st border col

			for (int col = 1; col <= N_COLS; col++)
			{
				// for every cell, check if vacant or occupied:
				numNeighbors = getNumNeighbors(currGeneration, row, col);
				isCellVacant = (cellResidents(currGeneration[row][col]) == 0);

				if (isCellVacant)
				{
					// check number of neighbors
					if (numNeighbors == 3)
					{
						newGeneration[row][col] = ORGANISM; // gets a resident
					}

					else
					{
						newGeneration[row][col] = EMPTY_CELL; // stays vacant
					}
				}

				// cell not vacant
				else
				{
					// check number of neighbors
					if ((numNeighbors == 2) || (numNeighbors == 3))
					{
						newGeneration[row][col] = ORGANISM; // stays alive
					}

					else
					{
						newGeneration[row][col] = EMPTY_CELL; // organism dies
					}
				}
			}

			newGeneration[row][N_COLS + 1] = EMPTY_CELL; // last border col
		}

		// loop to fill last border row
		for (int i = 0; i < totalCols; i++)
		{
			newGeneration[M_ROWS + 1][i] = EMPTY_CELL;
		}

		boolean generated;

		// check to see if new generation is the same as the last generation
		if (compareGenerations(newGeneration, currGeneration))
		{
			System.out.println("Terminated - Same Generation as Previous");
			generated = false;
			return generated;
		}

		else
		{
			System.out.println("New Generation was created");
			prevGeneration = currGeneration;
			currGeneration = newGeneration;
			generation++;
			generated = true;
		}

		// print generation to the screen
		printGeneration(newGeneration);
		
		return generated;
	}

	// Prints the generation to the screen without the border
	private void printGeneration(char[][] gen)
	{
		System.out.println("Generation " + generation + ":");

		for (int row = 1; row <= M_ROWS; row++)
		{
			for (int col = 1; col <= N_COLS; col++)
			{
				System.out.print(gen[row][col]);
			}
			System.out.println();
		}
	}

	// returns 1 if cell is occupied
	private int cellResidents(char cell)
	{
		if (cell == EMPTY_CELL)
		{
			return 0;
		}

		return 1;
	}

	// compares two generations to see if they're the same
	// return true if they are the same
	private boolean compareGenerations(char[][] original, char[][] copy)
	{
		int numSameCells = 0;
		int numCells = 0;

		for (int r = 0; r < totalRows; r++)
		{
			for (int c = 0; c < totalCols; c++)
			{
				if (copy[r][c] == original[r][c])
				{
					numSameCells++;
				}
				numCells++;
			}
		}
		return (numSameCells == numCells);
	}

	// sends back a copy of the generation passed to it 
	private char[][] copyGeneration(char [][] gen1, int rows, int cols)
	{
		char[][] newGen = new char[rows][cols];

		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				newGen[r][c] = gen1[r][c];
			}
		}

		return newGen;
	} 
}