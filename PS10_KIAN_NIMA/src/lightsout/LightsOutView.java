package lightsout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Game Interface
 *
 */
@SuppressWarnings("serial")
public class LightsOutView extends JFrame implements ActionListener
{

    
    private final static int WIDTH = 700;
    private final static int HEIGHT = 750;
    private static float[] colorF = Color.RGBtoHSB(0, 0, 0, new float[3]);
    public final static int ROWS = 5;
    public final static int COLS = 5;
    public final static Color FOREGROUND_COLOR = Color.LIGHT_GRAY;
    public final static Color BACKGROUND_COLOR = Color.DARK_GRAY;
    public final static Color BOARD_COLOR = Color.getHSBColor(colorF[0], colorF[1], colorF[2]);
    
    public final static Color INACTIVE_LIGHT = Color.LIGHT_GRAY;
    public final static Color ACTIVE_LIGHT = Color.YELLOW;
    public final static Color PRIMARY_TEXT_COLOR = Color.WHITE;
    public final static Color SECONDARY_TEXT_COLOR = Color.LIGHT_GRAY;
    public final static int BORDER = 5;
    public final static int FONT_SIZE = 20;

    /** Writes the player's score to the GUI **/
    private JLabel score;

    /** Writes the hint to the GUI **/
    private JLabel hint;

    /** Starts a new game **/
    private JButton newGame;

    /** Enters level mode **/
    private JButton level;

    /** Enters Manual Setup **/
    private JButton manualSetup;

    /** The intelligence of the game **/
    private LightsOutModel model;

    /** The interactive portion of the GUI **/
    private Board board;

    /**
     * Creates the LightsOutView GUI
     */
    public LightsOutView ()
    {
        
        setTitle("Lights Out");

        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

       
        model = new LightsOutModel(ROWS, COLS);
        board = new Board(model, this);
        root.add(board, BorderLayout.CENTER);

        
        JPanel scores = new JPanel(new BorderLayout());
        scores.setBackground(BACKGROUND_COLOR);
        scores.setForeground(FOREGROUND_COLOR);
        root.add(scores, BorderLayout.NORTH);

        
        JPanel scoreP = new JPanel();
        scoreP.setBackground(BACKGROUND_COLOR);
        score = new JLabel("Score: 0");
        score.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        score.setForeground(PRIMARY_TEXT_COLOR);
        score.setBorder(new EmptyBorder(0, BORDER, 0, BORDER));
        scoreP.add(score);
        scores.add(score, BorderLayout.WEST);

        
        JPanel hintP = new JPanel();
        hintP.setBackground(BACKGROUND_COLOR);
        hint = new JLabel("GOAL - Turn off all of the lights");
        hint.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        hint.setForeground(SECONDARY_TEXT_COLOR);
        hint.setBorder(new EmptyBorder(0, BORDER, 0, BORDER));
        hintP.add(hint);
        scores.add(hintP, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        root.add(buttonPanel, BorderLayout.SOUTH);

        
        newGame = new JButton("Random Game");
        newGame.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        newGame.setForeground(PRIMARY_TEXT_COLOR);
        newGame.setBackground(BACKGROUND_COLOR);
        newGame.addActionListener(this);
        buttonPanel.add(newGame, 0, 0);

        
        level = new JButton("Start Level 1");
        level.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        level.setForeground(PRIMARY_TEXT_COLOR);
        level.setBackground(BACKGROUND_COLOR);
        level.addActionListener(this);
        buttonPanel.add(level, 0, 1);

        
        manualSetup = new JButton("Enter Manual Setup");
        manualSetup.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        manualSetup.setForeground(PRIMARY_TEXT_COLOR);
        manualSetup.setBackground(BACKGROUND_COLOR);
        manualSetup.addActionListener(this);
        buttonPanel.add(manualSetup, 0, 2);

        
        board.refresh();
        setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        
        JButton source = (JButton) e.getSource();

        
        if (source.getText().contains("Manual Setup"))
        {
            if (source.getText().equals("Enter Manual Setup"))
            {
                model.setLevelMode(false);
                model.setManualSetupMode(true);
                source.setText("Exit Manual Setup");
                setHintLabel("MANUAL SETUP - Click to toggle a light!");
                setLevelText("Start Level " + model.getLevel());
            }
            else
            {
                model.setLevelMode(false);
                model.setManualSetupMode(false);
                source.setText("Enter Manual Setup");
                setHintLabel("GOAL - Turn off all of the lights");
                setLevelText("Start Level " + model.getLevel());
            }
        }
        else if (source.getText().contains("Random Game"))
        {
            model.newGame();
            board.refresh();
            manualSetup.setText("Enter Manual Setup");
            setHintLabel("GOAL - Turn off all of the lights");
            setLevelText("Start Level " + model.getLevel());
        }
        else if (source.getText().contains("Level"))
        {
            if (source.getText().contains("Start"))
            {
                model.setLevelMode(true);
                model.generateLevel();
                board.refresh();
                manualSetup.setText("Enter Manual Setup");
                setHintLabel("Level " + model.getLevel());
                setLevelText("Restart Level " + model.getLevel());
            }
            else
            {
                model.generateLevel();
                board.refresh();
                manualSetup.setText("Enter Manual Setup");
                setHintLabel("Level " + model.getLevel());
                setLevelText("Restart Level " + model.getLevel());
            }
        }
    }

    /**
     * Sets the score JLabel in the gui to score.
     */
    public void setScoreLabel (int score)
    {
        this.score.setText("Score: " + score);
    }

    /**
     * Sets the hint JLabel in the gui to text.
     */
    public void setHintLabel (String text)
    {
        hint.setText(text);
    }

    /**
     * Sets the level JButton's text to text.
     */
    public void setLevelText (String text)
    {
        level.setText(text);
    }
}

/**
 * Generates the primary game board
 */
@SuppressWarnings("serial")
class Board extends JPanel implements MouseListener
{

    /** The intelligence of the game **/
    LightsOutModel model;

    /** The game board **/
    LightsOutView view;

    public Board (LightsOutModel model, LightsOutView view)
    {
        this.model = model;
        this.view = view;

        
        setBackground(LightsOutView.BOARD_COLOR);
        setLayout(new GridLayout(LightsOutView.ROWS, LightsOutView.COLS));

        
        for (int i = 0; i < LightsOutView.ROWS; i++)
        {
            for (int j = 0; j < LightsOutView.COLS; j++)
            {
                Light l = new Light(i, j);
                l.addMouseListener(this);
                add(l);
            }
        }
    }

    public void refresh ()
    {
        Component[] lights = getComponents();

        for (Component c : lights)
        {
            Light l = (Light) c;

            if (model.isActive(l.getRow(), l.getCol()))
            {
                l.setColor(LightsOutView.ACTIVE_LIGHT);
            }
            else
            {
                l.setColor(LightsOutView.INACTIVE_LIGHT);
            }
        }

        repaint();
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        Light light = (Light) e.getSource();
        int results = model.clickLight(light.getRow(), light.getCol());
        if (results == LightsOutModel.WIN_VALUE)
        {
            if (model.isLevelMode())
            {
                view.setScoreLabel(model.getScore());
                view.setHintLabel("Level " + model.getLevel());
                view.setLevelText("Restart Level " + model.getLevel());
                JOptionPane.showMessageDialog(this, "You've completed Level " + (model.getLevel() - 1) + "!");
            }
            else
            {
                view.setScoreLabel(model.getScore());
                view.setHintLabel("Click 'Random Game' to start a new game");
                JOptionPane.showMessageDialog(this, "YOU WIN! Click on 'Random Game' to create a new game!");
            }
        }
        refresh();
    }

    @Override
    public void mouseClicked (MouseEvent e)
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

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }
}

/**
 * A single Light on the board
 */
@SuppressWarnings("serial")
class Light extends JPanel
{

    /** The row number of which this light is located. The uppermost row is position 0. **/
    private int row;

    /** The column number of which this light is located. The leftmost column is position 0. **/
    private int col;

    /** The active color of the light **/
    private Color color;

    public Light (int row, int col)
    {
        this.row = row;
        this.col = col;
        color = LightsOutView.INACTIVE_LIGHT;
    }

    public int getCol ()
    {
        return col;
    }

    public int getRow ()
    {
        return row;
    }

    public void setColor (Color color)
    {
        this.color = color;
    }

    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(LightsOutView.BORDER, LightsOutView.BORDER, getWidth() - 2 * LightsOutView.BORDER,
                getHeight() - 2 * LightsOutView.BORDER);
    }
}
