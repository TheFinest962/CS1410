package cs1410;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;

public class CacheTests
{
    /**
     * Tests the methods of the cache class to return the right value.
     */
    @Test
    public void methodValueTest() {
        
        Cache cachetest = new Cache("GCABC" + "\tGolden Necklace" + "\tCasualtyZer0" + "\t1.5" + "\t1" + "\tN40 7.8096" + "\tW111 28.7616");
        
        assertEquals("GCABC", cachetest.getGcCode());
        assertEquals("Golden Necklace", cachetest.getTitle());
        assertEquals("CasualtyZer0", cachetest.getOwner());
        assertTrue(cachetest.getDifficulty() == 1.5);
        assertTrue(cachetest.getTerrain() == 1);
        assertEquals(cachetest.getLatitude(), "N40 7.8096");
        assertEquals(cachetest.getLongitude(), "W111 28.7616");
           
    }
    
    /**
     * Tests to make sure that every entry is a valid cache object
     */
    @Test
    public void methodEntryTest() {
        
        final String CACHE_NAME = "caches.txt";
        
        Scanner scan = null;
        
        try {
            
            scan = new Scanner(new File(CACHE_NAME));
           
        }
        catch(FileNotFoundException e) {
            throw new RuntimeException("File not Found");
        }
         
        while(scan.hasNextLine()) {
            Cache cache = new Cache(scan.nextLine());
            
            cache.equals(null);
        }
        scan.close();
    }
    
    /**
     * Tests the ability to identify incorrect GC Codes
     */
    @Test
    public void GCCodeExceptionTest() {
        
        String[] invalidCodes = { // List of invalid codes
                "GC123h",
                "",
                "GcABCDE",
                "404 ERROR dignity not found",
                "GC ABCDE",
                "\n",
                "GC" };
        int counter = 0; // Count of how many codes threw exceptions

        for (int i = 0; i < invalidCodes.length; i++)
        {

            try
            {
                Cache cachetest = new Cache(
                        invalidCodes[i] 
                        + "\tGolden Necklace" 
                        + "\tCasualty Zer0" 
                        + "\t1.5" 
                        + "\t1"
                        + "\tN40 7.8096" 
                        + "\tW111 28.7616");
                cachetest.equals(null);

            }
            catch (IllegalArgumentException e)
            {
                counter++;
            }

        }

        assertTrue(counter == invalidCodes.length);

    }
    
    /**
     * Tests the ability to check correct GC Codes
     */
    @Test
    public void GCCodeTest() {
    String[] correctCodes = {
            "GC356",
            "GC6ABC",
            "GCABCDE12345",
            "GCX1X2X3X4X5"};
    
    for(int num = 0; num < correctCodes.length; num++) {
        Cache cachetest = new Cache("GCABC" 
                + "\tGolden Necklace" 
                + "\tCasualtyZer0" 
                + "\t1.5" 
                + "\t1" 
                + "\tN40 7.8096" 
                + "\tW111 28.7616");
        cachetest.equals(null);
    }
    }
}

