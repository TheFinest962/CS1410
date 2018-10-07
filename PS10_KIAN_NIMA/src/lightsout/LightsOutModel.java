package lightsout;

import java.util.HashMap;
import java.util.Random;

/**
 * The Game Logic
 * 
 */
public class LightsOutModel
{
    /** Integer value for a win **/
    public final static int WIN_VALUE = 1;
    
    /** Keeps track of the lights out board **/
    boolean[][] board;
    
    /** Stores all of the levels **/
    Level levelList;

    /** The number of rows in the board. The uppermost row is position 0. **/
    private final int ROWS;

    /** The number of columns in the board. The leftmost column is position 0. **/
    private final int COLS;
    
    /** Keeps track of the player's score **/
    private int score;
    
    /** Keeps track of the player's level **/
    private int level;

    /** Keeps track of whether manual setup mode is enabled or not **/
    private boolean isManualSetupMode;
    
    /** If level mode is enabled **/
    private boolean isLevelMode;

    /** Keeps track of whether the game is active or not. This will be false if a player has just won a game. **/
    private boolean isActive;

    /**
     * Initializes the LightsOutModel Class and creates a game board with the specified number of rows.
     * 
     * If rows or cols is <= 0, throws IllegalArgumentException.
     */
    public LightsOutModel (int rows, int cols)
    {
        if (rows <= 0 || cols <= 0)
        {
            throw new IllegalArgumentException("The number of rows and cols must be greater than 0");
        }
        this.ROWS = rows;
        this.COLS = cols;

        board = new boolean[rows][cols];
        levelList = new Level();
        isManualSetupMode = false;
        isLevelMode = false;
        isActive = false;
        score = 0;
        level = 1;

        newGame();
    }

    /**
     * Starts a new game. The previous board will be cleared, a new board will be randomly generated, and the score will
     * not be iterated.
     */
    public void newGame ()
    {
       
        Random random = new Random();

        
        int presses = random.nextInt(10) + 20;

        
        isActive = true;
        isLevelMode = false;
        isManualSetupMode = false;
        board = levelList.getLevel(0);

        
        for (int i = 0; i < presses; i++)
        {
            
            int r = random.nextInt(ROWS);
            int c = random.nextInt(COLS);
            clickLight(r, c);
        }
    }
    
    /**
     * Creates the current level.
     * 
     * If level is greater than the quantity of available levels the last level will be created.
     */
    public void generateLevel ()
    {
        boolean[][] newBoard = levelList.getLevel(level);
        
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                board[i][j] = newBoard[i][j];
            }
        }
        
        isActive = true;
        isLevelMode = true;
        isManualSetupMode = false;
    }

    /**
     * Toggles the light located in the board at the specified row and column, as well as the light to the right, left,
     * above, and below the selected light. If the light is on it will be turned off. If the light is off it will be
     * turned on.
     * 
     * If row or col is greater than the existing number of rows or columns, throws IllegalArgumentException. If row or
     * col is <= 0, throws IllegalArgumentException.
     */
    public int clickLight (int row, int col)
    {
        if (row < 0 || col < 0 || row >= ROWS || col >= COLS)
        {
            throw new IllegalArgumentException("Invalid row or col");
        }

        
        if (isManualSetupMode)
        {
            toggleLight(row, col);
            return 0;
        }
        
        
        if (!isActive)
        {
            return 0;
        }

        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                
                if ((Math.abs(i) + Math.abs(j)) > 1)
                {
                    continue;
                }

                try
                {
                    toggleLight(row + i, col + j);
                }
                catch (IllegalArgumentException e)
                {
                }
            }
        }

        
        if (checkForWin() && !isManualSetupMode)
        {
            isActive = false;
            score++;
            
            if (isLevelMode)
            {
                if (level == levelList.levelListSize())
                {
                    level = 1;
                }
                else
                {
                    level++;
                    generateLevel();
                }
            }
            
            return WIN_VALUE;
        }
        
        return 0;
    }

    /**
     * Toggles the single light at the specified row and column.
     * 
     * If row or col is greater than the existing number of rows or columns, throws IllegalArgumentException. If row or
     * col is <= 0, throws IllegalArgumentException.
     */
    public void toggleLight (int row, int col)
    {
        if (row < 0 || col < 0 || row >= ROWS || col >= COLS)
        {
            throw new IllegalArgumentException("Invalid row or col");
        }

        
        board[row][col] = !(board[row][col]);
    }

    /**
     * Returns whether the light at row and col is turned on or off.
     * 
     * If row or col is greater than the existing number of rows or columns, throws IllegalArgumentException. If row or
     * col is <= 0, throws IllegalArgumentException.
     */
    public boolean isActive (int row, int col)
    {
        if (row < 0 || col < 0 || row >= ROWS || col >= COLS)
        {
            throw new IllegalArgumentException("Invalid row or col");
        }

        return board[row][col];
    }

    /**
     * Returns the lights out board.
     */
    public boolean[][] getBoard ()
    {
        return board;
    }

    /**
     * Enables manual setup mode if toggle is true, otherwise manual setup mode will be disabled.
     */
    public void setManualSetupMode (boolean toggle)
    {
        isManualSetupMode = toggle;
        isActive = !toggle;
    }

    /**
     * Returns true if the game is in manual setup mode, otherwise it returns false.
     */
    public boolean isManualSetupMode ()
    {
        return isManualSetupMode;
    }
    
    /**
     * Enables level mode if toggle is true, otherwise level mode will be disabled.
     */
    public void setLevelMode (boolean toggle)
    {
        isLevelMode = toggle;
        
        if (toggle)
        {
            generateLevel();
        }
    }
    
    /**
     * Returns the level
     */
    public int getLevel ()
    {
        return level;
    }
    
    /**
     * Returns whether level mode is enabled
     */
    public boolean isLevelMode ()
    {
        return isLevelMode;
    }
    
    /**
     * Returns the player's score
     */
    public int getScore ()
    {
        return score;
    }

    /**
     * Returns true of all of the lights are turned off, otherwise returns false.
     */
    private boolean checkForWin ()
    {
        
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                
                if (board[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }
}

class Level
{
    
    /** Stores all of the levels **/
    HashMap<Integer, boolean[][]> levelList;
    
    public Level ()
    {
        levelList = new HashMap<>();
        generateLevels();
    }
    
    public void generateLevels()
    {
        boolean[][] l0 = {
                {false, false, false, false, false}, 
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };
        levelList.put(0, l0);
        
        boolean[][] l1 = {
                {true, true, false, true, true}, 
                {true, false, true, false, true},
                {false, true, true, true, false},
                {true, false, true, false, true},
                {true, true, false, true, true}
        };
        levelList.put(1, l1);
        
        boolean[][] l2 = {
                {false, false, true, true, false}, 
                {false, true, true, true, true},
                {false, true, true, true, true},
                {false, false, true, true, false},
                {false, false, false, false, false}
        };
        levelList.put(2, l2);
            
        boolean[][] l3 = {
                {false, false, false, true, true}, 
                {false, false, false, true, true},
                {true, true, true, false, false},
                {false, false, false, true, true},
                {false, false, false, true, true}
        };
        levelList.put(3, l3);
                
        boolean[][] l4 = {
                {false, false, false, false, false}, 
                {true, true, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false}
        };
        levelList.put(4, l4);
        
        boolean[][] l5 = {
                {false, false, true, true, true}, 
                {false, false, true, true, true},
                {false, false, true, true, true},
                {false, false, true, true, true},
                {false, false, true, true, true}
        };
        levelList.put(5, l5);
        
        boolean[][] l6 = {
                {false, false, false, false, false}, 
                {false, true, true, true, false},
                {false, true, true, true, false},
                {false, true, true, true, false},
                {false, false, false, false, false}
        };
        levelList.put(6, l6);
        
        boolean[][] l7 = {
                {false, false, false, false, false}, 
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };
        levelList.put(7, l7);
        
        boolean[][] l8 = {
                {true, false, false, false, false}, 
                {false, true, false, false, false},
                {false, false, true, true, true},
                {false, true, false, false, false},
                {true, false, false, false, false}
        };
        levelList.put(8, l8);
        
        boolean[][] l9 = {
                {true, false, false, false, true}, 
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {true, false, false, false, true}
        };
        levelList.put(9, l9);
    }
    
    /**
     * Returns the board for the level.
     * 
     * If level is greater than levelList.size() the final level is returned.
     * If level is less than 0, throw IllegalArgumentException
     */
    public boolean[][] getLevel (int level)
    {
        if (level < 0)
        {
            throw new IllegalArgumentException("level must be greater than or equal to 1");
        }
        else if (!levelList.containsKey(level))
        {
            return levelList.get(levelList.size() - 1).clone();
        }
        
        return levelList.get(level).clone();
    }
    
    /**
     * Returns the size of levelList.
     */
    public int levelListSize ()
    {
        return levelList.size();
    }
}
