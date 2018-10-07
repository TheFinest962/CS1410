package c4;

import static org.junit.Assert.*;
import org.junit.Test;

public class C4Tests
{

    int[][] testBoard = { { 1, 1, 1, 0, 0, 0 }, { 2, 2, 2, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, };

    /**
     * Tests C4Board's ability to construct itself properly given any rows/columns greater than 4.
     * 
     */
    @Test
    public void boardTest ()
    {

        for (int c = 4; c <= 50; c++)
        {

            for (int r = 4; r <= 50; r++)
            {

                C4Board testBoard = new C4Board(c, r);

                for (int i = 0; i < c; i++)
                {

                    for (int j = 0; r < r; j++)
                    {

                        assertEquals(0, testBoard.getOccupant(i, j));

                    }

                }

            }

        }

    }

    /**
     * Tests ability to load a board
     * 
     */
    @Test
    public void getBoardStructureTest ()
    {

        C4Board Board = new C4Board(testBoard);

        assertArrayEquals(testBoard, Board.getBoardStructure());

    }

    /**
     * Tests ability to throw an error
     * 
     */
    @Test
    public void getBoardStructureExeptionTest ()
    {

        C4Board testBoard = new C4Board(4, 4);

        for (int i = -1; i < 4; i++)
        {

            try
            {
                testBoard = new C4Board(i, 4);
                fail("Column#" + i + "was supposed throw an exception");
            }
            catch (IllegalArgumentException e)
            {
            }

            try
            {
                testBoard = new C4Board(4, i);
                fail("Row#" + i + "was supposed to throw an exception");
            }
            catch (IllegalArgumentException e)
            {
            }

        }

        testBoard.equals(testBoard);

    }

    /**
     * Unit test for C4Board's newGame function.
     * 
     * Fails if the board is not cleared properly after calling newGame(), or throws an exception.
     */
    @Test
    public void newGameTest ()
    {

        int[][] expectedBoard = new int[testBoard.length][testBoard[0].length];

        C4Board Board = new C4Board(testBoard);
        Board.newGame();

        assertArrayEquals(expectedBoard, Board.getBoardStructure());

    }

    /**
     * Test moveTo method
     * 
     * Tests a non-winning move
     */
    @Test
    public void moveToTest1 ()
    {

        int[][] testBoard = { { 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
                { 2, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, };

        C4Board Board = new C4Board(6, 7);

        Board.moveTo(0);
        Board.moveTo(4);

        assertArrayEquals(testBoard, Board.getBoardStructure());

    }

    /**
     * Tests moveTo method
     * 
     * Tests a winning move
     * 
     */
    @Test
    public void moveToTest2 ()
    {

        int[][] testBoard = { { 1, 1, 1, 1, 0, 0 }, { 2, 2, 2, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, };

        C4Board Board = new C4Board(6, 7);

        // These moves will result in Player 1 winning the game
        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(0);
        Board.moveTo(1);
        assertEquals(1, Board.moveTo(0));

        // This is to test moves after the game has ended. Should fail if these moves affect the board in anyway
        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(2);
        Board.moveTo(3);

        assertArrayEquals(testBoard, Board.getBoardStructure());
        assertEquals(1, Board.getWinsForP1());

    }

    /**
     * Tests moveTo method
     * 
     * Tests non-winning moves on an empty col
     *
     */
    @Test
    public void moveToTest3 ()
    {

        int[][] testBoard = { { 1, 2, 1, 2, 1, 2 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0 }, };

        C4Board Board = new C4Board(6, 7);

        Board.moveTo(0);
        Board.moveTo(0);
        Board.moveTo(0);
        Board.moveTo(0);
        Board.moveTo(0);
        Board.moveTo(0);

        Board.moveTo(0);
        Board.moveTo(6);

        assertArrayEquals(testBoard, Board.getBoardStructure());
        assertEquals(2, Board.getToMove());

    }

    /**
     * Tests the ability to detect die
     * 
     */
    @Test
    public void getTieTest ()
    {

        int[][] testBoard = { { 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2 }, { 2, 1, 2, 1, 2, 0 },
                { 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2 }, };

        C4Board Board = new C4Board(testBoard);

        assertEquals(3, Board.moveTo(3));

        assertEquals(1, Board.getTies());

    }

    /**
     * Tests the ability to get who's turn it is
     * 
     */
    @Test
    public void getcurrPlayerTest ()
    {

        C4Board Board = new C4Board(6, 7);

        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(0);
        Board.moveTo(1);
        assertEquals(1, Board.moveTo(0));

        Board.newGame();
        assertEquals(2, Board.getToMove());

        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(0);
        Board.moveTo(1);
        Board.moveTo(0);
        Board.moveTo(1);
        assertEquals(2, Board.moveTo(0));

    }

}