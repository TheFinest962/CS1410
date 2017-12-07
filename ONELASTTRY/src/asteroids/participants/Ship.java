package asteroids.participants;

import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.destroyers.AlienBulletDestroyer;
import asteroids.destroyers.AlienDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import static asteroids.game.Constants.SHIP_ACCELERATION;
import static asteroids.game.Constants.SHIP_FRICTION;

/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer, AlienDestroyer, AlienBulletDestroyer
{
    // The outline of the ship
    private Shape outline;
    private Shape outline2;

    // Game controller
    private Controller controller;
    
    // Constructs a ship at the specified coordinates
    // that is pointed in the given direction.
    
    public Ship (int x, int y, double direction, Controller controller)
    {
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);

        //Ship with the flame
	    Path2D.Double poly = new Path2D.Double();
	   	poly.moveTo(20, 0);
	    poly.lineTo(-20, 12);
        poly.lineTo(-13, 10);
        poly.lineTo(-13, -5);
	    poly.lineTo(-24, 0);
        poly.lineTo(-13, 5);
        poly.lineTo(-13, -10);
	    poly.lineTo(-20, -12);
        poly.closePath();
	
	    outline = poly;

	    //Ship without the flame
        Path2D.Double poly2 = new Path2D.Double();
  	    poly2.moveTo(20, 0);
  	    poly2.lineTo(-20, 12);
  	    poly2.lineTo(-13, 10);
  	    poly2.lineTo(-13, -10);
        poly2.lineTo(-20, -12);
        poly2.closePath();
  	    outline2 = poly2;

    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose
     * is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the y-coordinate of the point on the screen where the ship's nose
     * is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }
    
    /**
     * Returns the x-coordinate of the point on the screen where the ship's tail
     * is located.
     */
    public double getXTail ()
    {
        Point2D.Double point = new Point2D.Double(-13, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the y-coordinate of the point on the screen where the ship's tail
     * is located.
     */
    public double getYTail ()
    {
        Point2D.Double point = new Point2D.Double(-13, 0);
        transformPoint(point);
        return point.getY();
    }

    /**
     * Return the ship's outline
     */
    @Override
    protected Shape getOutline ()
    {
    	//Return the ship with the flame
        if(controller.getFlame() == true)
        {
        	 return outline;
        }
        //Return the ship outline without the flame
        else
        {
        	 return outline2;
        }
       
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        applyFriction(SHIP_FRICTION);
        super.move();
    }

    /**
     * Turns right by Pi/16 radians
     */
    public void turnRight ()
    {
        rotate(Math.PI / 16);
    }

    /**
     * Turns left by Pi/16 radians
     */
    public void turnLeft ()
    {
        rotate(-Math.PI / 16);
    }
    
    /**
     * Accelerates by SHIP_ACCELERATION
     */
    public void accelerate ()
    {
        accelerate(SHIP_ACCELERATION);

    }
    
    /**
     * When a Ship collides with a ShipKiller, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer)
        {
            // Expire the ship from the game
            Participant.expire(this);
            
            // Tell the controller the ship was destroyed
            controller.shipDestroyed();
        }
    }
    
    /**
     * This method is invoked when a ParticipantCountdownTimer completes
     * its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
    	if(payload.equals("asteroid")){
    		controller.getClipAsteroid().setFramePosition(0);
    	}
    }
}
