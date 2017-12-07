package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Path2D;

import asteroids.game.Participant;

public class Dust extends Participant
{
	private Shape outline;

	/**
	 * Create a single small "Dust" object in the shape of a very small square
	 */
	public Dust(double x, double y)
	{
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(x, y);
        poly.lineTo(x+2, y);
        poly.closePath();

        outline = poly;
	}

	/**
	 * Return the debris outline
	 */
	@Override
	protected Shape getOutline() 
	{
		return outline;
	}

	@Override
	public void collidedWith(Participant p)
	{

	}
	
    /**
     * This method is invoked when a ParticipantCountdownTimer completes
     * its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    { 
    	Participant.expire(this);
    }

}
