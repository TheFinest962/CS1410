package c4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static c4.C4Board.*;
import static c4.C4Display.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Implements a Connect 4 game with a GUI interface.
 * 
 * @author Joe Zachary
 */
@SuppressWarnings("serial")
public class C4Display extends JFrame implements ActionListener
{
    // Some formatting constants
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    public final static int ROWS = 6;
    public final static int COLS = 7;
    public final static Color BACKGROUND_COLOR = Color.GRAY;
    public final static Color P1_COLOR = new Color(176, 0, 0);
    public final static String P1_COLOR_NAME = "Red";
    public final static Color P2_COLOR = Color.YELLOW;
    public final static String P2_COLOR_NAME = "Yellow";
    public final static Color BOARD_COLOR = Color.BLUE;
    public final static Color TIE_COLOR = Color.WHITE;
    public final static int BORDER = 5;
    public final static int FONT_SIZE = 20;

    /** The "smarts" of the game **/
    private C4Board model;

    /** The circle that illuminates when it is player 1's turn to play **/
    private MoveIndicator p1Indicator;

    /** The circle that illuminates when it is player 2's turn to play **/
    private MoveIndicator p2Indicator;

    /** The number of games that player 1 has won **/
    private JLabel p1Wins;

    /** The number of games that player 2 has won **/
    private JLabel p2Wins;

    /** The number of ties there have been **/
    private JLabel ties;

    /** The portion of the GUI that contains the playing surface **/
    private Board board;

    /**
     * Creates and initializes the game.
     */
    public C4Display ()
    {
        // Set the title that appears at the top of the window
        setTitle("CS 1410 Connect Four");

        // Cause the application to end when the windows is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give the window its initial dimensions
        setSize(WIDTH, HEIGHT);

        // The root panel contains everything
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // The center portion contains the playing board
        model = new C4Board(ROWS, COLS);
        board = new Board(model, this);
        root.add(board, "Center");

        // The top portion contains the scores
        JPanel scores = new JPanel();
        scores.setLayout(new BorderLayout());
        root.add(scores, "North");

        // Score and indicator for the first player
        JPanel p1 = new JPanel();
        p1.setBackground(BACKGROUND_COLOR);
        p1Wins = new JLabel("Wins: 0");
        p1Wins.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        p1Wins.setForeground(P1_COLOR);
        p1Wins.setBorder(new EmptyBorder(0, BORDER, 0, BORDER));
        p1.add(p1Wins);
        p1Indicator = new MoveIndicator();
        p1.add(p1Indicator);
        scores.add(p1, "West");

        // Ties
        JPanel tiePanel = new JPanel();
        tiePanel.setBackground(BACKGROUND_COLOR);
        ties = new JLabel("Ties: 0");
        ties.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        ties.setForeground(TIE_COLOR);
        tiePanel.add(ties);
        scores.add(tiePanel, "Center");

        // Score and indicator for the second player
        JPanel p2 = new JPanel();
        p2.setBackground(BACKGROUND_COLOR);
        p2Indicator = new MoveIndicator();
        p2.add(p2Indicator);
        p2Wins = new JLabel("Wins: 0");
        p2Wins.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        p2Wins.setForeground(P2_COLOR);
        p2Wins.setBorder(new EmptyBorder(0, BORDER, 0, BORDER));
        p2.add(p2Wins);
        scores.add(p2, "East");

        // The bottom portion contains the New Game button
        JButton newGame = new JButton("New Game");
        newGame.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        newGame.setForeground(TIE_COLOR);
        newGame.setBackground(BACKGROUND_COLOR);
        newGame.addActionListener(this);
        root.add(newGame, "South");

        // Refresh the display and we're ready
        board.refresh();
        setVisible(true);
    }

    /**
     * Sets the indicators when it is player 1's turn to move.
     */
    public void setP1ToMove ()
    {
        p1Indicator.setColor(P1_COLOR);
        p2Indicator.setColor(BACKGROUND_COLOR);
    }

    /**
     * Sets the indicators when it is player 2's turn to move.
     */
    public void setP2ToMove ()
    {
        p2Indicator.setColor(P2_COLOR);
        p1Indicator.setColor(BACKGROUND_COLOR);
    }

    /**
     * Clears both indicators when the game is over.
     */
    public void clearToMove ()
    {
        p1Indicator.setColor(BACKGROUND_COLOR);
        p2Indicator.setColor(BACKGROUND_COLOR);
    }

    /**
     * Sets the label that displays player 1's win count
     */
    public void setWinsForP1 (int n)
    {
        p1Wins.setText("Wins: " + n);

    }

    /**
     * Sets the label that displays player 2's win count
     */
    public void setWinsForP2 (int n)
    {
        p2Wins.setText("Wins: " + n);
    }

    /**
     * Sets the label that displays the tie count
     */
    public void setTies (int n)
    {
        ties.setText("Ties: " + n);
    }

    /**
     * Called when the New Game button is clicked. Tells the model to begin a new game, then refreshes the display.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        model.newGame();
        board.refresh();
    }
}

/**
 * The playing surface of the game.
 */
@SuppressWarnings("serial")
class Board extends JPanel implements MouseListener
{
    /** The "smarts" of the game */
    private C4Board model;

    /** The top level GUI for the game */
    private C4Display display;

    /**
     * Creates the board portion of the GUI.
     */
    public Board (C4Board model, C4Display display)
    {
        // Record the model and the top-level display
        this.model = model;
        this.display = display;

        // Set the background color and the layout
        setBackground(BOARD_COLOR);
        setLayout(new GridLayout(ROWS, COLS));

        // Create and lay out the grid of squares that make up the game.
        for (int i = ROWS - 1; i >= 0; i--)
        {
            for (int j = 0; j < COLS; j++)
            {
                Square s = new Square(i, j);
                s.addMouseListener(this);
                add(s);
            }
        }
    }

    /**
     * Refreshes the display. This should be called whenever something changes in the model.
     */
    public void refresh ()
    {
        // Iterate through all of the squares that make up the grid
        Component[] squares = getComponents();
        for (Component c : squares)
        {
            Square s = (Square) c;

            // Set the color of the squares appropriately
            int status = model.getOccupant(s.getRow(), s.getCol());
            if (status == P1)
            {
                s.setColor(P1_COLOR);
            }
            else if (status == P2)
            {
                s.setColor(P2_COLOR);
            }
            else
            {
                s.setColor(BACKGROUND_COLOR);
            }
        }

        // Set the indicators according to whose move it is
        int toMove = model.getToMove();
        if (toMove == P1)
        {
            display.setP1ToMove();
        }
        else if (toMove == P2)
        {
            display.setP2ToMove();
        }
        else
        {
            display.clearToMove();
        }

        // Update the win and tie counts
        display.setWinsForP1(model.getWinsForP1());
        display.setWinsForP2(model.getWinsForP2());
        display.setTies(model.getTies());

        // Ask that this board be repainted
        repaint();
    }

    /**
     * Called whenever a Square is clicked. Notifies the model that a move has been attempted.
     */
    @Override
    public void mouseClicked (MouseEvent e)
    {
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        Square s = (Square) e.getSource();
        int result = model.moveTo(s.getCol());
        refresh();
        if (result == P1)
        {
            JOptionPane.showMessageDialog(this, P1_COLOR_NAME + " wins!");
        }
        else if (result == P2)
        {
            JOptionPane.showMessageDialog(this, P2_COLOR_NAME + " wins!");
        }
        else if (result == TIE)
        {
            JOptionPane.showMessageDialog(this, "The game is a tie!");
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {
    }

    @Override
    public void mouseExited (MouseEvent e)
    {
    }
}

/**
 * A single square on the board where a move can be made
 */
@SuppressWarnings("serial")
class Square extends JPanel
{
    /** The row within the board of this Square. Rows are numbered from zero; row zero is at the bottom of the board. */
    private int row;

    /** The column within the board of this Square. Columns are numbered from zero; column zero is at the left */
    private int col;

    /** The current Color of this Square */
    private Color color;

    /**
     * Creates a square and records its row and column
     */
    public Square (int row, int col)
    {
        this.row = row;
        this.col = col;
        this.color = BACKGROUND_COLOR;
    }

    /**
     * Returns the row of this Square
     */
    public int getRow ()
    {
        return row;
    }

    /**
     * Returns the column of this Square
     */
    public int getCol ()
    {
        return col;
    }

    /**
     * Sets the color of this square
     */
    public void setColor (Color color)
    {
        this.color = color;
    }

    /**
     * Paints this Square onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        g.fillOval(BORDER, BORDER, getWidth() - 2 * BORDER, getHeight() - 2 * BORDER);
    }
}

/**
 * A move indicator circle for use in the user interface.
 */
@SuppressWarnings("serial")
class MoveIndicator extends JPanel
{
    // The color of this MoveIndicator
    private Color color;

    /**
     * Creates a MoveIndicator
     */
    public MoveIndicator ()
    {
        setPreferredSize(new Dimension(FONT_SIZE, FONT_SIZE));
        this.color = BACKGROUND_COLOR;
    }

    /**
     * Changes the color of this indicator
     */
    public void setColor (Color color)
    {
        this.color = color;
        repaint();
    }

    /**
     * Paints this MoveIndicator onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getHeight(), getHeight());
        g.setColor(color);
        g.fillOval(0, 0, getHeight(), getHeight());
    }
}
