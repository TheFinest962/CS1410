package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Path2D;

import asteroids.game.Participant;
import asteroids.destroyers.AlienDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;

public class Bullet extends Participant implements AsteroidDestroyer, AlienDestroyer{

	private Shape outline;

	/**
	 * Create a Bullet object at the given x and y coordinates
	 */
	public Bullet(double x, double y)
	{
		Path2D.Double poly = new Path2D.Double();
		poly.moveTo(x, y);
		poly.lineTo(x, y + 1);
		poly.lineTo(x+1, y + 1);
		poly.lineTo(x+1, y);
		poly.closePath();

		outline = poly;
	}

	/**
	 * Return the Bullet's outline
	 */
	@Override
	protected Shape getOutline() 
	{
		return outline;
	}

	/**
	 * Expire the Bullet when it collides with a BulletDestroyer
	 */
	@Override
	public void collidedWith(Participant p) 
	{
		if (p instanceof BulletDestroyer) 
		{
			Participant.expire(this);
		}
	}

	/**
	 * This method is invoked when a ParticipantCountdownTimer completes its
	 * countdown.
	 */
	@Override
	public void countdownComplete(Object payload) 
	{
		Participant.expire(this);
	}

}
