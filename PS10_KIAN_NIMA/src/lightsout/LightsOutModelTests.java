package lightsout;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit tests for LightsOutModel
 * 
 */
public class LightsOutModelTests
{

    @Test
    public void testLightsOutModelTest ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        
        boolean light;
        light = model.isActive(0, 0);
        model.clickLight(0, 0);
        assertEquals(!light, model.isActive(0, 0));
        model.toggleLight(0, 0);
        assertEquals(light, model.isActive(0, 0));
        assertEquals(light, model.getBoard()[0][0]);
        
        assertEquals(false, model.isManualSetupMode());
        model.setManualSetupMode(true);
        assertEquals(true, model.isManualSetupMode());
        model.setManualSetupMode(false);
        assertEquals(false, model.isManualSetupMode());
        
        assertEquals(0, model.getScore());
        model.newGame();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorIllegalArgumentException ()
    {
        new LightsOutModel(0, 5);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testClickLightLessThanZeroIllegalArgumentException ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        model.clickLight(0, -1);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testClickLightTooLargeIllegalArgumentException ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        model.clickLight(5, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testToggleLightLessThanZeroIllegalArgumentException ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        model.toggleLight(-1, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testToggleLightTooLargeIllegalArgumentException ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        model.toggleLight(0, 5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIsActiveLessThanZeroIllegalArgumentException ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        model.isActive(-1, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testIsActiveTooLargeIllegalArgumentException ()
    {
        LightsOutModel model = new LightsOutModel(5, 5);
        model.isActive(0, 5);
    }
}
