package lightsout;

import javax.swing.SwingUtilities;

/**
 * Launches Game
 * 
 */
public class LightsOut
{

    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new LightsOutView());
    }

}
