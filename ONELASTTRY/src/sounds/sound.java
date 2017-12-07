package sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Provides the sounds to be used in enhanced mode
 */
public class sound 
{

	private static Mixer mixer;
	
	//The five audio clips used in enhanced mode
	private Clip clip;
    private Clip clip2;
    private Clip clipAlienDestroyed;
    private Clip clipShip;
    private Clip clipAsteroid;
    private Clip clipAlienShip;

    //The variables that represent the AudioInputStreams
    private AudioInputStream stream;
    private AudioInputStream stream2;
    private AudioInputStream streamAlienDestroyed;
    private AudioInputStream streamShip;
    private AudioInputStream streamAsteroid;
    private AudioInputStream streamAlienShip;
	
	public  sound () throws LineUnavailableException, UnsupportedAudioFileException, IOException 
	{
	Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
	mixer = AudioSystem.getMixer(mixInfos[0]);

	
	DataLine.Info dataline = new DataLine.Info(Clip.class, null);
	
	clip = (Clip) mixer.getLine(dataline);
	clip2 = (Clip) mixer.getLine(dataline);
	clipAlienDestroyed = (Clip) mixer.getLine(dataline);
	clipShip = (Clip) mixer.getLine(dataline);
	clipAsteroid = (Clip) mixer.getLine(dataline);
	clipAlienShip = (Clip) mixer.getLine(dataline);
	
	//Fire Sound
	URL soundURL = sound.class.getResource("/sounds/thrust.wav");
	 stream = AudioSystem.getAudioInputStream(soundURL);
	
	 //Ship shooting sound
	 URL soundURL2 = sound.class.getResource("/sounds/fire.wav");
	 stream2 = AudioSystem.getAudioInputStream(soundURL2);
	 
	 //Alien destroyed sound
	 URL soundURL3 = sound.class.getResource("/sounds/bangAlienShip.wav");
	 streamAlienDestroyed = AudioSystem.getAudioInputStream(soundURL3);
	
	//Ship destroyed sound
	 URL soundURL4 = sound.class.getResource("/sounds/bangShip.wav");
	 streamShip = AudioSystem.getAudioInputStream(soundURL4);
		 
	//Ship destroyed sound
	 URL soundURL5 = sound.class.getResource("/sounds/bangLarge.wav");
	 streamAsteroid = AudioSystem.getAudioInputStream(soundURL5);
	 
	 //AlienShip Sound
	 URL soundURL6 = sound.class.getResource("/sounds/saucerBig.wav");
	 streamAlienShip = AudioSystem.getAudioInputStream(soundURL6);
	}
	
	/**
	 * Returns the flame clip
	 */
	public Clip getSound()
	{	
		return clip;	
	}
	
	/**
	 * Returns the shooting clip
	 */
	public Clip getSound2()
	{	
		return clip2;	
	}
	
	/**
	 * Returns the alienDestroyed clip
	 */
	public Clip getSoundAlienDestroyed()
	{	
		return clipAlienDestroyed;	
	}
	
	/**
	 * Returns the shipDestroyed clip
	 */
	public Clip getSoundShip()
	{	
		return clipShip;	
	}
	
	/**
	 * Returns the asteroidDestroyed clip
	 */
	public Clip getSoundAsteroid()
	{	
		return clipAsteroid;	
	}

	/**
	 * Returns the flame stream
	 */
	public AudioInputStream getStream () 
	{
		return stream;
	}
	
	/**
	 * Returns the shooting stream
	 */
	public AudioInputStream getStream2 () 
	{
		return stream2;
	}
	
	/**
	 * Returns the alienDestroyed stream
	 */
	public AudioInputStream getStreamAlienDestroyed () 
	{
		return streamAlienDestroyed;
	}

	/**
	 * Returns the shipDestroyed stream
	 */
	public AudioInputStream getStreamShip () 
	{
		return streamShip;
	}
	
	/**
	 * Returns the asteroidDestroyed stream
	 */
	public AudioInputStream getStreamAsteroid () 
	{
		return streamAsteroid;
	}
	
	/**
	 * Returns the alienShip Clip
	 * @return
	 */
	public Clip getClipAlienShip () 
	{
		return clipAlienShip;
	}
	
	/**
	 * Returns the alienShip stream
	 * @return
	 */
	public AudioInputStream getStreamAlienShip () 
	{
		return streamAlienShip;
	}
}
