package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.*;

import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.destroyers.AlienDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.ShipDestroyer;
import static asteroids.game.Constants.*;

/**
 * Represents asteroids
 */
public class Asteroid extends Participant implements ShipDestroyer, BulletDestroyer, AlienDestroyer
{
    // The size of the asteroid (0 = small, 1 = medium, 2 = large)
    private int size;

    // The outline of the asteroid
    private Shape outline;

    // The game controller
    private Controller controller;
    
    //Asteroid Speed
    private double astSpeed;

    /**
     * Throws an IllegalArgumentException if size or variety is out of range.
     * 
     * Creates an asteroid of the specified variety (0 through 3) and size (0 =
     * small, 1 = medium, 2 = large) and positions it at the provided
     * coordinates with a random rotation. Its velocity has the given speed but
     * is in a random direction.
     */
    public Asteroid (int variety, int size, double x, double y, double speed, Controller controller)
    {
        // Make sure size and variety are valid
        if (size < 0 || size > 2)
        {
            throw new IllegalArgumentException("Invalid asteroid size: " + size);
        }
        else if (variety < 0 || variety > 3)
        {
            throw new IllegalArgumentException();
        }
        
        //Give different speeds to asteroids of different sizes
        if(size == 2){
        	astSpeed = speed;
        }
        if(size == 1){
        	astSpeed = speed*1.2;
        }
        if(size == 0){
        	astSpeed = speed*1.4;
        }

        // Create the asteroid
        this.controller = controller;
        this.size = size;
        setPosition(x, y);
        setVelocity(astSpeed, RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI * RANDOM.nextDouble());
        createAsteroidOutline(variety, size);
    }

    /**
     * Return the outline of the asteroid
     */
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Creates the outline of the asteroid based on its variety and size.
     */
    private void createAsteroidOutline (int variety, int size)
    {
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();

        // Fill out according to variety
        if (variety == 0)
        {
            poly.moveTo(0, -30);
            poly.lineTo(28, -15);
            poly.lineTo(20, 20);
            poly.lineTo(4, 8);
            poly.lineTo(-1, 30);
            poly.lineTo(-12, 15);
            poly.lineTo(-5, 2);
            poly.lineTo(-25, 7);
            poly.lineTo(-10, -25);
            poly.closePath();
        }
        else if (variety == 1)
        {
            poly.moveTo(10, -28);
            poly.lineTo(7, -16);
            poly.lineTo(30, -9);
            poly.lineTo(30, 9);
            poly.lineTo(10, 13);
            poly.lineTo(5, 30);
            poly.lineTo(-8, 28);
            poly.lineTo(-6, 6);
            poly.lineTo(-27, 12);
            poly.lineTo(-30, -11);
            poly.lineTo(-6, -15);
            poly.lineTo(-6, -28);
            poly.closePath();
        }
        else if (variety == 2)
        {
            poly.moveTo(10, -30);
            poly.lineTo(30, 0);
            poly.lineTo(15, 30);
            poly.lineTo(0, 15);
            poly.lineTo(-15, 30);
            poly.lineTo(-30, 0);
            poly.lineTo(-10, -30);
            poly.closePath();
        }
        else
        {
            poly.moveTo(30, -18);
            poly.lineTo(5, 5);
            poly.lineTo(30, 15);
            poly.lineTo(15, 30);
            poly.lineTo(0, 25);
            poly.lineTo(-15, 30);
            poly.lineTo(-25, 8);
            poly.lineTo(-10, -25);
            poly.lineTo(0, -30);
            poly.lineTo(10, -30);
            poly.closePath();
        }

        // Scale to the desired size
        double scale = ASTEROID_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        // Save the outline
        outline = poly;
    }

    /**
     * Returns the size of the asteroid
     */
    public int getSize ()
    {
        return size;
    }

    /**
     * When an Asteroid collides with an AsteroidDestroyer, it expires.
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AsteroidDestroyer)
        {
            
            // Expire the asteroid
            Participant.expire(this);
            
            // Create two smaller asteroids. Put them at the same position
            // as the one that was just destroyed and give them a random
            // direction.
            int size = getSize() - 1;
            if (size >= 0)
            {
            	controller.addParticipant(new Asteroid(RANDOM.nextInt(4), size, getX(), getY(), astSpeed, controller));
                controller.addParticipant(new Asteroid(RANDOM.nextInt(4), size, getX(), getY(), astSpeed, controller));
            }
            controller.placeDust(this.getX(), this.getY());
            
            // Inform the controller
            controller.asteroidDestroyed(size+1);
        }
    }
}
