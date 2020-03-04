# PAC_1_HW_9
game of life

GRADE: 10/10

The Game of Life
Created by Michael Overton & Samuel Marateck

Description

Consider a two dimensional array with M rows and N columns. This represents the world in
which some organisms live. Each of the M by N cells in this array is either occupied (if an
organism lives there) or is vacant. No more than one organism can live in any one cell at
any time. Each cell, except those on the boundaries (the edge of the world), has exactly
eight neighboring cells (above, below, left, right, and four diagonals). The cells on the
boundaries have less than eight neighboring cells.

Initially, there is a given population of organisms occupying certain of the cells. At each
succeeding generation, the organisms reproduce and die as follows:

● Each organism of the current generation survives to the next generation if, and only
if, it has 2 or 3 neighbors (a neighbor is an organism that lives in a neighboring cell).
Otherwise, it dies and its cell becomes empty in the next generation. Note: it dies if
it is “lonely” or “overcrowded”.

● Each vacant cell in the current generation becomes occupied by a new organism in
the next generation if, and only if, it has exactly 3 neighbors. Otherwise, it remains
vacant in the next generation.

Write a program to play this Game of Life. Your program should read the initial world from
a file (see below) and repeatedly generate new generations, prompting the user each time
to see if he or she wants to see the next generation or terminate the program. Also, the
program should terminate automatically if the world becomes empty, displaying a message
accordingly (this will happen for life3.dat ).

USER: choose which file to run (.dat)
