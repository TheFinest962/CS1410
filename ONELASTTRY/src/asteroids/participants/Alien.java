package asteroids.participants;


import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import static asteroids.game.Constants.*;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;
import asteroids.destroyers.AlienDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.ShipDestroyer;

public class Alien extends Participant implements AsteroidDestroyer,
		ShipDestroyer, BulletDestroyer {

	// The outline of the ship
	private Shape outline;

	// Game controller
	private Controller controller;
	private double alienSpeed;
	private int size;
	private boolean movingLeft;

	/**
	 * Create an alien Spaceship at position (x, y) with the inputted direction, speed, 
	 * and size( 0 = small, 1 = big)
	 */
	public Alien(int x, int y, double direction, double speed, int size, Controller controller) 
	{
		
		// Constructs a ship at the specified coordinates
		// that is pointed in the given direction.
		this.controller = controller;
		setPosition(x, y);
		setRotation(direction);
		this.size = size;
		
		//Enforce the speed limit
		if(speed > SPEED_LIMIT)
		{
			speed = SPEED_LIMIT;
		}
		alienSpeed = speed;
		
		//If the alien is moving left (negative speed) assigns true to the movingLeft variable
		if(alienSpeed <= 0)
		{
			movingLeft = true;
		}
		else
		{
			movingLeft = false;
		}
		
		//Draw the Alien Ship
		Path2D.Double poly = new Path2D.Double();
		poly.moveTo(20, -3);
		poly.lineTo(40, -3);
		poly.lineTo(40, 5);
		poly.lineTo(20, 5);
		poly.closePath();
		poly.moveTo(20, 5);
		poly.lineTo(5, 15);
		poly.lineTo(55, 15);
		poly.lineTo(40, 5);
		poly.closePath();
		poly.moveTo(55, 15);
		poly.lineTo(45, 25);
		poly.lineTo(15, 25);
		poly.lineTo(5, 15);
		poly.closePath();
        
		outline = poly;
		
		//Scale the ship to the appropriate size
		double scale = ALIENSHIP_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));
        
        //Set the velocity with the given speed and direction
        setVelocity(alienSpeed, direction);		
	}

	/**
	 * Returns the size of the Alien Ship
	 */
	public int getSize()
	{
		return size;
	}
	/**
	 * Return the outline of the alien ship
	 */
	@Override
	protected Shape getOutline() {
		return outline;
	}
	
	 /**
     * Returns the x-coordinate of the point on the screen where the alien ship's nose
     * is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(55, 15);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the y-coordinate of the point on the screen where the alien ship's nose
     * is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(55, 15);
        transformPoint(point);
        return point.getY();
    }

	/**
	 * When a Alien collides with a AlienKiller, it expires
	 */
	@Override
	public void collidedWith(Participant p) {
		if (p instanceof AlienDestroyer) {

			//Tell the controller the alien was destroyed
			controller.alienDestroyed();
		}
	}
	
	/**
	 * This method is invoked when a ParticipantCountdownTimer completes its
	 * countdown.
	 */
	@Override
	public void countdownComplete(Object payload) 
	{
		if(payload.equals("expire"))
		{
			Participant.expire(this);
		}
		if(payload.equals("create"))
		{
	    	
			controller.addParticipant(this);
			new ParticipantCountdownTimer(this, "shoot", 1000);
			new ParticipantCountdownTimer(this, "move", 1000);
		}
		
		if(payload.equals("shoot"))
		{
			if(this != null){
				controller.placeAlienBullet();

				new ParticipantCountdownTimer(this, "shoot", 1000);
			}
		}
		if(payload.equals("move"))
		{
			int d = RANDOM.nextInt(3);
			double direction = Math.PI;
			
			//Alien moving left
				if(movingLeft == true){
					
					if(d == 0){
						direction = Math.PI;
					}
					else if(d == 1){
						direction = Math.PI - 1;
					}
					else{
						direction = Math.PI + 1;
					}
				}
				
				//Alien moving right
				else{
				
				if(d == 0){
					direction = 0;
				}
				else if(d == 1){
					direction = 1;
				}
				else{
					direction = -1;
				}
				}
				
				//Time moved
				int random = RANDOM.nextInt(1000);
				if(random < 500){
					random += 500;
				}
				
		
			this.setDirection(direction);
			new ParticipantCountdownTimer(this, "move", random);
		}
	}
}
