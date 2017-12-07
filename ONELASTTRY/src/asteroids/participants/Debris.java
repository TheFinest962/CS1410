package asteroids.participants;



import java.awt.Shape;
import java.awt.geom.Path2D;

import asteroids.game.Participant;
import static asteroids.game.Constants.*;


public class Debris extends Participant{
	private Shape outline;

	/**
	 * Construct a single "Debris" object in the shape of a small line at position (x, y).
	 * Has 4 different varieties, which each have different rotations.
	 */
	public Debris(double x, double y, int variety)
	{
		if(variety == 0){
			int i = RANDOM.nextInt(21);
			if(i < 10)
			{
				i+= 10;
			}
	        Path2D.Double poly = new Path2D.Double();
	        poly.moveTo(x, y);
	        poly.lineTo(x, y + i);
	        poly.closePath();

	        outline = poly;
		}
		
		if(variety == 1){
			int i = RANDOM.nextInt(21);
			if(i < 10)
			{
				i+= 10;
			}
	        Path2D.Double poly = new Path2D.Double();
	        poly.moveTo(x, y);
	        poly.lineTo(x + i, y + i);
	        poly.closePath();

	        outline = poly;
		}
		
		if(variety == 2){
			int i = RANDOM.nextInt(21);
			if(i < 10)
			{
				i+= 10;
			}
	        Path2D.Double poly = new Path2D.Double();
	        poly.moveTo(x, y);
	        poly.lineTo(x + i, y);
	        poly.closePath();

	        outline = poly;
		}
		
		if(variety == 3){
			int i = RANDOM.nextInt(21);
			if(i < 10)
			{
				i+= 10;
			}
	        Path2D.Double poly = new Path2D.Double();
	        poly.moveTo(x, y);
	        poly.lineTo(x - i, y + i);
	        poly.closePath();

	        outline = poly;
	        
		}
	}

	/**
	 * Return the Debris outline
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
