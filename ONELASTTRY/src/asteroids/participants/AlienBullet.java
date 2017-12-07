package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Path2D;

import asteroids.game.Participant;
import asteroids.destroyers.AlienBulletDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;

public class AlienBullet extends Participant implements AsteroidDestroyer, ShipDestroyer{

	private Shape outline;


	/**
	 * Constructs a bullet to be shot by the alien ship
	 */
	public AlienBullet(double x, double y) 
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
	 * Returns the outline of the alienBullet
	 */
	@Override
	protected Shape getOutline()
	{
		return outline;
	}

	/**
	 * Expires the AlienBullet when it collides with an AlienBulletDestroyer
	 */
	@Override
	public void collidedWith(Participant p) 
	{
		if (p instanceof AlienBulletDestroyer) 
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
		if (payload.equals("expire"))
		{
			Participant.expire(this);
		}

	}
}

