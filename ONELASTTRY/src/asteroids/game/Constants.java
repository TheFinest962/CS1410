package asteroids.game;

import java.util.Random;

/**
 * Provides constants that govern the game.
 */
public class Constants
{
    /**
     * A shared random number generator for general use.
     */
    public final static Random RANDOM = new Random();

    /**
     * The height and width of the game area.
     */
    public final static int SIZE = 750;

    /**
     * Game title
     */
    public final static String TITLE = "CS 1410 Asteroids";

    /**
     * Label on start game button
     */
    public final static String START_LABEL = "Start Game";

    /**
     * Speed beyond which participants may not accelerate
     */
    public final static double SPEED_LIMIT = 15;

    /**
     * Amount of "friction" that can be applied to ships so that they eventually
     * stop. Should be negative.
     */
    public final static double SHIP_FRICTION = -0.05;

    /**
     * Constant of acceleration of ship. Should be positive.s
     */
    public final static double SHIP_ACCELERATION = .65;

    /**
     * The number of milliseconds between the beginnings of frame refreshes
     */
    public final static int FRAME_INTERVAL = 33;

    /**
     * The number of milliseconds between the end of a life and the display of
     * the next screen.
     */
    public final static int END_DELAY = 2500;

    /**
     * The offset in pixels from the edges of the screen of newly-placed
     * asteroids.
     */
    public final static int EDGE_OFFSET = 100;

    /**
     * The game over message
     */
    public final static String GAME_OVER = "Game Over";

    /**
     * Number of asteroids that must be destroyed to complete a level.
     */
    public final static int ASTEROID_COUNT = 28;

    /**
     * Duration in milliseconds of a bullet before it disappears.
     */
    public final static int BULLET_DURATION = 1000;

    /**
     * Speed, in pixels per frame, of a bullet.
     */
    public final static int BULLET_SPEED = 15;

    /**
     * Maximum number of bullets that can exist at one time.
     */
    public final static int BULLET_LIMIT = 8;

    /**
     * Maximum number of alienBullets that can exist at one time.
     */
    public final static int ALIEN_BULLET_LIMIT = 1;

    /**
     * Scaling factors used for asteroids of size 0, 1, and 2.
     */
    public final static double[] ASTEROID_SCALE = { 0.5, 1.0, 2.0 };

    /**
     * Score earned for asteroids of size 0, 1, and 2.
     */
    public final static int[] ASTEROID_SCORE = { 100, 50, 20 };

    /**
     * Scaling factors used for alien ships of size 0 and 1.
     */
    public final static double[] ALIENSHIP_SCALE = { 0.5, 1.0 };
    
    /**
     * Score earned for alien ships of size 0 and 1.
     */
    public final static int[] ALIENSHIP_SCORE = { 400, 200 };
    
    /**
     * Delay after which an alien ship appears.
     */
    public final static int ALIEN_DELAY = 5000;
}
