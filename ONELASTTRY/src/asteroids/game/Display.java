package asteroids.game;

import javax.swing.*;
import java.awt.*;

import static asteroids.game.Constants.START_LABEL;
import static asteroids.game.Constants.TITLE;

/**
 * Defines the top-level appearance of an Asteroids game.
 */
@SuppressWarnings("serial")
public class Display extends JFrame
{
    // The area where the action takes place
    private Screen screen;
    private JLabel level = new JLabel("");
    private JLabel lives = new JLabel("");
    private JLabel score = new JLabel("");
    private JLabel highScore = new JLabel("");

    /**
     * Lays out the game and creates the controller
     */
    public Display (Controller controller)
    {
        // Title at the top
        setTitle(TITLE);

        // Default behavior on closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main playing area and the controller
        screen = new Screen(controller);
        
        // This panel contains the screen to prevent the screen from being
        // resized
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridBagLayout());
        screenPanel.add(screen);

        // This panel contains buttons and labels
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(1,2));

        // The button that starts the game
        JButton startGame = new JButton(START_LABEL);
        JPanel start = new JPanel();
        start.add(startGame);
        controls.add(start);
        
        //The labels that keep track of the level, lives remaining, and score
        JPanel labels = new JPanel();
        labels.add(level);
        labels.add(lives);
        labels.add(score);
        
        //Add a highScore label in enhanced mode
        if(controller.isEnhancedMode() == true){
        	labels.add(highScore);
        }
        
        //Add the labels to the panel
        controls.add(labels);

        // Organize everything
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(screenPanel, "Center");
        mainPanel.add(controls, "North");
        setContentPane(mainPanel);
        pack();

        // Connect the controller to the start button
        startGame.addActionListener(controller);
    }
    
    /**
     * Called when it is time to update the screen display. This is what drives
     * the animation.
     */
    public void refresh ()
    {
        screen.repaint();
    }
    
    /**
     * Sets the large legend
     */
    public void setLegend (String s)
    {
        screen.setLegend(s);
    }
    
    /**
     * Updates the highScore label
     */
    public void setHighScore(int n){
    	highScore.setText("Highscore: " + n);
    }
    
    /**
     * Updates the Level display at the top of the screen
     */
    public void setLevel(int n)
    {
    	level.setText("Level: " + n);
    }
    
    /**
     * Updates the number of lives remaining display at the top of the screen
     */
    public void setLives(int i)
    {
    	lives.setText("Lives: " + i);
    }
    
    /**
     * Updates the Score display at the top of the screen
     */
    public void setScore(int s)
    {
    	score.setText("Score: " + s);
    }

}
