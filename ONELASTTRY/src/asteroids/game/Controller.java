package asteroids.game;

import asteroids.participants.*;
import sounds.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Iterator;

import static asteroids.game.Constants.*;

/**
 * Controls a game of Asteroids.
 */
public class Controller implements KeyListener, ActionListener {
	// The state of all the Participants
	private ParticipantState pstate;

	// The ship (if one is active) or null (otherwise)
	private Ship ship;

	// When this timer goes off, it is time to refresh the animation
	private Timer refreshTimer;

	// The time at which a transition to a new stage of the game should be made.
	// A transition is scheduled a few seconds in the future to give the user
	// time to see what has happened before doing something like going to a new
	// level or resetting the current level.
	private long transitionTime;

	// Number of lives left
	private int lives;

	private Alien alien;

	// The game display
	private Display display;

	private int turnLeft;
	private int turnRight;
	private int accelerate;
	private int shoot;
	private int numScore;
	private int numLevel;
	private int highScore;
	private double speed;
	private Bullet b;
	private AlienBullet ab;
	private boolean flame;

	// Keeps track of the number of times the lives have been updated in the
	// enhanced version
	private int numLiveUpdated;

	// Keeps track of whether or not the game is in enhanced mode
	private boolean enhanced = false;

	// Represent audio clips for enhanced mode
	private Clip clipFire;
	private Clip clipShoot;
	private Clip clipAlienDestroyed;
	private Clip clipShip;
	private Clip clipAsteroid;
	private Clip clipAlienShip;

	// Represent AudioInputStreams for enhanced mode
	private AudioInputStream streamFire;
	private AudioInputStream streamShoot;
	private AudioInputStream streamAlienDestroyed;
	private AudioInputStream streamShip;
	private AudioInputStream streamAsteroid;
	private AudioInputStream streamAlienShip;

	/**
	 * Constructs a controller to coordinate the game and screen
	 */
	public Controller(int n) {
		// Enhanced Mode
		if (n > 0) {
			enhanced = true;

			// keeps track of the number of times that the user has been given a new life
			// for reaching
			// a scoring threshold
			numLiveUpdated = 0;

			// Initializes/opens the sound files for enhanced mode

		}

		initializeSounds();

		// Initialize member variables for the new Controller object
		highScore = 0;
		flame = false;
		turnLeft = 0;
		turnRight = 0;
		accelerate = 0;
		shoot = 0;
		speed = 3;

		// Record the game and screen objects
		display = new Display(this);
		display.setVisible(true);

		// Initialize the ParticipantState
		pstate = new ParticipantState();

		// Set up the refresh timer.
		refreshTimer = new Timer(FRAME_INTERVAL, this);

		// Clear the transitionTime
		transitionTime = Long.MAX_VALUE;

		// Bring up the splash screen and start the refresh timer
		splashScreen();
		refreshTimer.start();
	}

	/**
	 * Returns true if the game mode is in enhanced mode, and returns false
	 * otherwise
	 */
	public boolean isEnhancedMode() {
		return enhanced;
	}

	/**
	 * Returns the ship, or null if there isn't one
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Configures the game screen to display the splash screen
	 */
	private void splashScreen() {
		// Clear the screen, reset the level, and display the legend
		clear();
		display.setLegend("Asteroids");

		// Place four asteroids near the corners of the screen.
		placeAsteroids();
	}

	/**
	 * The game is over. Displays a message to that effect.
	 */
	private void finalScreen() {
		display.setLegend(GAME_OVER);
		display.removeKeyListener(this);
	}

	/**
	 * Place a new ship in the center of the screen. Remove any existing ship first.
	 */
	private void placeShip() {
		// Expire the ship ship
		Participant.expire(ship);

		clipShip.stop();

		// Create a new ship
		ship = new Ship(SIZE / 2, SIZE / 2, -Math.PI / 2, this);
		addParticipant(ship);
		display.setLegend("");
	}

	/**
	 * Places 3 debris objects and sets them all to expire slightly apart from each
	 * other
	 */
	private void placeDebris(double x, double y) {

		// Place three Debris objects
		int variety = RANDOM.nextInt(4);
		Debris d = new Debris(x, y, variety);
		d.setVelocity(RANDOM.nextInt(16) / 10 + 1, 20);

		variety = RANDOM.nextInt(4);
		Debris d2 = new Debris(x + 5, y + 5, variety);
		d2.setVelocity(RANDOM.nextInt(16) / 10 + 1, 10);

		variety = RANDOM.nextInt(4);
		Debris d3 = new Debris(x - 5, y - 5, variety);
		d3.setVelocity(RANDOM.nextInt(16) / 10 + 1, -20);

		// Add the Debris objects as participants
		addParticipant(d);
		addParticipant(d2);
		addParticipant(d3);

		// Expire the three debris objects after 2 seconds
		new ParticipantCountdownTimer(d, 2000);
		new ParticipantCountdownTimer(d2, 2100);
		new ParticipantCountdownTimer(d3, 2200);

		// Place a dust object at the site of the debris
		placeDust(x, y);
		display.setLegend("");

	}

	/**
	 * Places 4 dust objects at the given x and y coordinates
	 */
	public void placeDust(double x, double y) {

		// Place 4 dust objects, and add them as participants
		Dust du = new Dust(x, y);
		Dust du2 = new Dust(x + 5, y);
		Dust du3 = new Dust(x + 5, y + 5);
		Dust du4 = new Dust(x, y + 5);
		addParticipant(du);
		addParticipant(du2);
		addParticipant(du3);
		addParticipant(du4);

		// Set the velocities of the 4 dust objects
		du.setVelocity(RANDOM.nextInt(16) / 10 + 1, 20);
		du2.setVelocity(RANDOM.nextInt(16) / 10 + 1, 10);
		du3.setVelocity(RANDOM.nextInt(16) / 10 + 1, -20);
		du4.setVelocity(RANDOM.nextInt(16) / 10 + 1, -5);

		// Expire the 4 dust objects
		new ParticipantCountdownTimer(du, 2000);
		new ParticipantCountdownTimer(du2, 2100);
		new ParticipantCountdownTimer(du3, 2200);
		new ParticipantCountdownTimer(du4, 2300);

		display.setLegend("");

	}

	/**
	 * Returns true if the Flame should be displayed and false if otherwise
	 */
	public boolean getFlame() {
		return flame;
	}

	/**
	 * Place a bullet object at the given x and y coordinates
	 */
	private void placeBullet(double x, double y) {
		b = new Bullet(x, y);
		addParticipant(b);
		b.setVelocity(BULLET_SPEED, ship.getRotation());
		new ParticipantCountdownTimer(b, "expire", BULLET_DURATION);
	}

	/**
	 * Places four asteroids near the corners of the screen. Gives them random
	 * velocities and rotations.
	 */
	private void placeAsteroids() {
		addParticipant(new Asteroid(0, 2, EDGE_OFFSET, EDGE_OFFSET, speed, this));
		addParticipant(new Asteroid(1, 2, SIZE - EDGE_OFFSET, EDGE_OFFSET, 3, this));
		addParticipant(new Asteroid(2, 2, EDGE_OFFSET, SIZE - EDGE_OFFSET, 3, this));
		addParticipant(new Asteroid(3, 2, SIZE - EDGE_OFFSET, SIZE - EDGE_OFFSET, 3, this));
	}

	/**
	 * Clears the screen so that nothing is displayed
	 */
	private void clear() {
		pstate.clear();
		display.setLegend("");
		ship = null;
	}

	/**
	 * Sets things up and begins a new game.
	 */
	private void initialScreen() {
		// Clear the screen
		clear();

		// Place four asteroids
		placeAsteroids();

		// Place the ship
		placeShip();

		// Reset statistics
		lives = 3;
		display.setLives(lives);
		numLevel = 1;
		display.setLevel(numLevel);
		display.setScore(0);
		speed = 3;

		// Start listening to events
		display.removeKeyListener(this);
		display.addKeyListener(this);

		// Give focus to the game screen
		display.requestFocusInWindow();
	}

	/**
	 * Adds a new Participant
	 */
	public void addParticipant(Participant p) {
		pstate.addParticipant(p);
	}

	/**
	 * The ship has been destroyed
	 */
	public void shipDestroyed() {
		placeDebris(ship.getX(), ship.getY());

		// Null out the ship
		ship = null;

		openShipSound();

		// Decrement lives
		lives--;
		display.setLives(lives);

		// Since the ship was destroyed, schedule a transition
		scheduleTransition(END_DELAY);
	}

	/**
	 * An asteroid of the given size has been destroyed
	 * 
	 * @throws InterruptedException
	 */
	public void asteroidDestroyed(int size) {
		// Update score based on asteroid size
		numScore += ASTEROID_SCORE[size];
		display.setScore(numScore);

		// If all the asteroids are gone, schedule a transition
		if (pstate.countAsteroids() == 0) {
			scheduleTransition(END_DELAY);
		}

		openAsteroidSound();

	}

	/**
	 * The alien has been destroyed
	 */
	public void alienDestroyed() {
		// Place 6 debris objects where the alien was located
		placeDebris(alien.getX(), alien.getY());
		placeDebris(alien.getX() + 1, alien.getY() + 1);

		// Update the score
		numScore += ALIENSHIP_SCORE[alien.getSize()];
		display.setScore(numScore);

		// Expire the alien from the game
		Participant.expire(alien);

		// Open the Alien destroyed sound in enhanced mode

		openAlienDestroyedSound();

		// Schedule a transition if all aliens have been destroyed
		if (pstate.countAliens() == 0) {
			scheduleTransition(END_DELAY);
		}
	}

	/**
	 * Places an Alien ship of the given size (size 1 == large alien, size 0 ==
	 * small alien)
	 */
	public void placeAlien(int size) {

		if (pstate.countAliens() == 0) {
			// create a random int with a value of either 1 or -1
			int a = -1;
			int j = RANDOM.nextInt(2);
			if (j == 0) {
				a = -1;

			} else {
				a = 1;

			}

			// Create a random value for the starting y position of the alien
			int i = RANDOM.nextInt(SIZE);

			
			// Create the alien
			alien = new Alien(0, i, 0, a * 6, size, this);
			new ParticipantCountdownTimer(alien, "create", ALIEN_DELAY);
		}
	}

	/**
	 * Places an Alien ship of the given size (size 1 == large alien, size 0 ==
	 * small alien)
	 */
	public void placeAlienBullet() {
		if (alien != null) {
			// If the alien is large fires a bullet in a random direction
			if (alien.getSize() == 1 && ship != null) {

				// Create the alien bullet
				ab = new AlienBullet(alien.getX(), alien.getY());

				// give the alien bullet a random direction and the bullet speed velocity,
				// then add as participant
				double r = (2 * Math.PI) * RANDOM.nextDouble();
				ab.setVelocity(BULLET_SPEED, r);
				addParticipant(ab);

				// Sets the alien bullet to expire when it reaches the bullet duration time
				// limit
				new ParticipantCountdownTimer(ab, "expire", BULLET_DURATION);
			}

			// If the alien is small fires a bullet aimed directly at the ship
			if (alien.getSize() == 0 && ship != null) {
				ab = new AlienBullet(alien.getX(), alien.getY());
				ab.setSpeed(BULLET_SPEED);

				// Aims the alien bullet directly at the ship
				double A = ship.getY() - alien.getY();
				double B = ship.getX() - alien.getX();
				double angle = Math.atan2(A, B);
				ab.setVelocity(BULLET_SPEED, angle);

				// Add the alien bullet as a participant and then set it to expire when it
				// reaches the bullet duration time limit
				addParticipant(ab);
				new ParticipantCountdownTimer(ab, "expire", BULLET_DURATION);
			}
		}

	}

	/**
	 * Return the alienBullet
	 */
	public AlienBullet getAlienBullet() {
		return ab;
	}

	/**
	 * Schedules a transition m msecs in the future
	 */
	private void scheduleTransition(int m) {
		transitionTime = System.currentTimeMillis() + m;
	}

	/**
	 * This method will be invoked because of button presses and timer events.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// The start button has been pressed. Stop whatever we're doing
		// and bring up the initial screen
		if (e.getSource() instanceof JButton) {
			// Initialize the speed, numScore, and numLevel values at the beginning of a new
			// game
			speed = 3;
			numScore = 0;
			numLevel = 1;
			display.setLevel(numLevel);
			initialScreen();

			// Update the highScore variable
			if (numScore > highScore) {
				highScore = numScore;
			}

			// Update the highScore Label in enhanced mode
			if (enhanced == true) {
				display.setHighScore(highScore);
			}
		}

		// Time to refresh the screen and deal with keyboard input
		else if (e.getSource() == refreshTimer) {
			if (turnLeft == 1 && ship != null) {
				ship.turnLeft();
			}
			if (turnRight == 1 && ship != null) {
				ship.turnRight();
			}
			if (accelerate == 1 && ship != null) {
				ship.accelerate();
				flame = !flame;

				try {
					if (clipFire.isOpen()) {
						clipFire.start();
					}

					if (!(clipFire.isActive())) {
						/*
						 * clip.stop(); clip.flush();
						 */
						clipFire.setFramePosition(0);
					}
				} catch (NullPointerException g) {
				}
			}
		}

		// Update lives in enhanced mode
		updateLives();

		// It may be time to make a game transition
		performTransition();

		// Move the participants to their new locations
		pstate.moveParticipants();

		// Refresh screen
		display.refresh();

	}

	/**
	 * Returns an iterator over the active participants
	 */
	public Iterator<Participant> getParticipants() {
		return pstate.getParticipants();
	}

	/**
	 * If the transition time has been reached, transition to a new state
	 */
	private void performTransition() {
		// Do something only if the time has been reached
		if (transitionTime <= System.currentTimeMillis()) {
			// Clear the transition time
			transitionTime = Long.MAX_VALUE;

			// If there are no lives left, the game is over. Show the final
			// screen.
			if (lives <= 0) {
				// Updates the highScore
				if (numScore > highScore) {
					highScore = numScore;
				}

				// Updates the HighScore label in enhanced mode
				if (enhanced == true) {
					display.setHighScore(highScore);
				}
				// Brings up the final screen
				finalScreen();
				// JOptionPane.showMessageDialog(null, numLiveUpdated);
			}

			// If the ship was destroyed, place a new one and continue
			else if (ship == null) {
				placeShip();
			}
			if (pstate.countAsteroids() == 0) {

				// increase the level and speed markers
				numLevel += 1;
				speed += 0.5;

				// Update the level display
				display.setLevel(numLevel);
				Participant.expire(alien);
				placeAsteroids();

			}

			// Place a new alien when one has been destroyed
			if (pstate.countAliens() == 0 && ship != null) {
				if (numLevel == 2) {
					placeAlien(1);

				} else if (numLevel >= 3) {
					placeAlien(0);

				}

				clipAlienDestroyed.stop();

			}

		}
	}

	/**
	 * If a key of interest is pressed, record that it is down.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && ship != null) {
			turnLeft = 1;
		}
		if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && ship != null) {
			turnRight = 1;
		}
		if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && ship != null) {
			accelerate = 1;

		}
		if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S) && ship != null) {
			shoot = 1;
			if (shoot == 1 && ship != null && pstate.countBullets() < BULLET_LIMIT) {
				placeBullet(ship.getXNose(), ship.getYNose());
			}

			if (clipShoot.isOpen()) {
				clipShoot.start();
			}

			if (!(clipShoot.isActive())) {
				/*
				 * clip.stop(); clip.flush();
				 */
				clipShoot.setFramePosition(0);
			}

		}
	}

	/**
	 * Ignore these events.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * When the key of interest is released, record that it has been released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)) {
			turnLeft = 0;
		}
		if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)) {
			turnRight = 0;
		}
		if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)) {
			accelerate = 0;
			flame = false;

			clipFire.stop();

		}
		if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S)) {
			shoot = 0;

			try {
				clipShoot.stop();
			} catch (NullPointerException f) {
			}
		}
	}

	/**
	 * Updates the number of lives in enhanced mode, giving the user 1 an extra life
	 * for reaching the 4000, 8000, and 12000 point thresholds.
	 */
	public void updateLives() {

		if (enhanced == true && (numScore >= 4000 && numLiveUpdated == 0) || (numScore >= 8000 && numLiveUpdated == 1)
				|| (numScore >= 12000 && numLiveUpdated == 2)) {
			lives += 1;
			numLiveUpdated += 1;
		}

	}

	/**
	 * Open and initialize the clips to be used for sounds in enhanced mode
	 */
	public void initializeSounds() {
		try {
			sound a = new sound();

			// Fire sound effect
			clipFire = a.getSound();
			streamFire = a.getStream();

			// Shooting sound effect
			clipShoot = a.getSound2();
			streamShoot = a.getStream2();

			// Sound when an alien is destroyed
			clipAlienDestroyed = a.getSoundAlienDestroyed();
			streamAlienDestroyed = a.getStreamAlienDestroyed();

			// Sound when a ship has been destroyed
			clipShip = a.getSoundShip();
			streamShip = a.getStreamShip();

			// Sound when an asteroid has been destroyed
			clipAsteroid = a.getSoundAsteroid();
			streamAsteroid = a.getStreamAsteroid();

			// Sound when AlienShip appears
			clipAlienShip = a.getClipAlienShip();
			streamAlienShip = a.getStreamAlienShip();

			// Open the sound clips
			clipFire.open(streamFire);
			clipShoot.open(streamShoot);
			clipAlienDestroyed.open(streamAlienDestroyed);
			clipShip.open(streamShip);
			clipAsteroid.open(streamAsteroid);
			clipAlienShip.open(streamAlienShip);
		} catch (NullPointerException e) {
		} catch (LineUnavailableException f) {
		} catch (UnsupportedAudioFileException f) {
		} catch (IOException f) {
		}

	}

	/**
	 * Start the alienDestroyed sound
	 */
	public void openAlienDestroyedSound() {
		if (clipAlienDestroyed.isOpen()) {
			clipAlienDestroyed.start();
		}

		if (!(clipAlienDestroyed.isActive())) {
			/*
			 * clip.stop(); clip.flush();
			 */
			clipAlienDestroyed.setFramePosition(0);
		}

	}

	/**
	 * Start the shipSound
	 */
	public void openShipSound() {
		if (clipShip.isOpen()) {
			clipShip.start();
		}

		if (!(clipShip.isActive())) {
			/*
			 * clip.stop(); clip.flush();
			 */
			clipShip.setFramePosition(0);
		}

	}
	
	/**
	 * Start the asteroidDestroyed sound
	 */
	public void openAsteroidSound() {
		sound a;
		try {
			a = new sound();
			Clip asteroidSound = a.getSoundAsteroid();
			asteroidSound.open(streamAsteroid);
			if (clipAsteroid.isOpen()) {
				clipAsteroid.start();
			}

			if (!(clipAsteroid.isActive())) {
				/*
				 * clip.stop(); clip.flush();
				 */
				clipAsteroid.setFramePosition(0);
			}
		} catch (LineUnavailableException e) {
		} catch (UnsupportedAudioFileException e) {
		} catch (IOException e) {
		}

	}

	/**
	 * Return the asteroid destroyed sound
	 */
	public Clip getClipAsteroid() {
		return clipAsteroid;
	}
}
