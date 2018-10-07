package c4;

/**
 * Represents the state of a Connect Four board on which multiple games can be played. The board consists of a
 * rectangular grid of squares in which playing pieces can be placed. Squares are identified by their zero-based row and
 * column numbers, where rows are numbered starting with the bottom row, and columns are numbered by starting from the
 * leftmost column.
 * 
 * Multiple games can be played on a single board. Whenever a new game begins, the board is empty. There are two
 * players, identified by the constants P1 (Player 1) and P2 (Player 2). P1 moves first in the first game, P2 moves
 * first in the second game, and so on in alternating fashion.
 * 
 * A C4Board also keeps track of the outcomes of the games that have been played on it. It tracks the number of wins by
 * P1, the number of wins by P2, and the number of Ties. It does not track games that were abandoned before being
 * completed.
 */
public class C4Board
{

    private int[][] board = null;
    private int Player1Wins = 0;
    private int Player2Wins = 0;
    private int Ties = 0;
    private int currPlayer = 1;
    private boolean gameStatus = true;

    /** The number used to indicate Player 1 */
    public static final int P1 = 1;

    /** The number used to indicate Player 2 */
    public static final int P2 = 2;

    /** The number used to indicate a tie */
    public static final int TIE = 3;

    /**
     * Creates a C4Board with the specified number of rows and columns. There should be no pieces on the board and it
     * should be player 1's turn to move.
     * 
     * However, if either rows or cols is less than four, throws an IllegalArgumentException.
     */
    public C4Board (int rows, int cols)
    {
        if (rows < 4 || cols < 4)
        {

            throw new IllegalArgumentException("You need 4 columns and 4 rows");

        }

        this.board = new int[cols][rows];
    }

    /**
     * Creates a new board via directly loading an int[][]
     * 
     * @param boardData
     */
    public C4Board (int[][] boardData)
    {

        this.board = boardData;

    }

    /**
     * Sets up the board to play a new game, whether or not the current game is complete. All of the pieces should be
     * removed from the board. The player who had the second move in the previous game should have the first move in the
     * new game.
     */
    public void newGame ()
    {

        this.board = new int[this.board.length][this.board[0].length];
        this.gameStatus = true;

    }

    /**
     * Records a move, by the player whose turn it is to move, in the first open square in the specified column. Returns
     * P1 if the move resulted in a win for player 1, returns P2 if the move resulted in a win for player 2, returns TIE
     * if the move resulted in a tie, and returns 0 otherwise.
     * 
     * If the column is full, or if the game is over because a win or a tie has previously occurred, does nothing but
     * return zero.
     * 
     * If a non-existent column is specified, throws an IllegalArgumentException.
     */
    public int moveTo (int col)
    {

        try
        {
            this.board[col] = this.board[col];
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException("CANNOT FIND COLUMN #" + col);
        }

        int nextPosition = checkDropAvailability(col);

        if (nextPosition == -1 || !this.gameStatus)
        {
            return 0;
        }

        this.board[col][nextPosition] = currPlayer;

        if (FourInARow.fourInRow(this.board))
        {

            this.gameStatus = false;

            int winner = currPlayer;

            if (winner == P1)
            {
                Player1Wins++;
            }
            else
            {
                Player2Wins++;
            }

            currPlayer = ((Player1Wins + Player2Wins + Ties) % 2) + 1;

            return winner;

        }
        else if (checkBoardLength())
        {

            this.gameStatus = false;

            Ties++;

            currPlayer = ((Player1Wins + Player2Wins + Ties) % 2) + 1;

            return TIE;

        }
        else
        {

            if (currPlayer == P1)
            {
                currPlayer = P2;
            }
            else
            {
                currPlayer = P1;
            }

            return 0;

        }

    }

    /**
     * Checks the drop availability of 'i' position.
     * 
     * @param col
     * @return
     */
    private int checkDropAvailability (int col)
    {

        for (int i = 0; i < this.board[col].length; i++)
        {

            if (this.board[col][i] == 0)
            {

                return i;

            }

        }

        return -1;

    }

    /**
     * Checks the board length, columns * rows.
     * 
     * @return
     */
    private boolean checkBoardLength ()
    {

        for (int c = 0; c < this.board.length; c++)
        {

            for (int r = 0; r < this.board[0].length; r++)
            {

                if (board[c][r] == 0)
                {

                    return false;

                }

            }

        }

        return true;

    }

    /**
     * Returns an int array that contains the boards initial structure
     * 
     * @return
     */
    public int[][] getBoardStructure ()
    {

        return this.board;

    }

    /**
     * Returns the current game status, i.e. wether or not a current game is in progress
     * 
     * @return
     */
    public boolean getGameStatus ()
    {

        return gameStatus;

    }

    /**
     * Reports the occupant of the board square at the specified row and column. If the row or column doesn't exist,
     * throws an IllegalArgumentException. If the square is unoccupied, returns 0. Otherwise, returns P1 (if the square
     * is occupied by player 1) or P2 (if the square is occupied by player 2).
     */
    public int getOccupant (int row, int col)
    {

        try
        {
            this.board[col][row] = this.board[col][row];
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException("Row " + row + ", column " + col + " does not exist.");
        }

        return this.board[col][row];

    }

    /**
     * Reports whose turn it is to move. Returns P1 (if it is player 1's turn to move), P2 (if it is player 2's turn to
     * move, or 0 (if it is neither player's turn to move because the current game is over because of a win or tie).
     */
    public int getToMove ()
    {
        return currPlayer;
    }

    /**
     * Reports how many games have been won by player 1 since this board was created.
     */
    public int getWinsForP1 ()
    {
        return Player1Wins;
    }

    /**
     * Reports how many games have been won by player 2 since this board was created.
     */
    public int getWinsForP2 ()
    {
        return Player2Wins;
    }

    /**
     * Reports how many Ties have occurred since this board was created.
     */
    public int getTies ()
    {
        return Ties;
    }

}
