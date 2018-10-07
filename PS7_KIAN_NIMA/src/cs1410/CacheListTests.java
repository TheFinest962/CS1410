package cs1410;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;

public class CacheListTests
{
    private CacheList cacheList = null;
    
    /**
     * Makes sure cacheList is able to run the list
     * @throws IOException
     */
    @Test
    public void testCacheList () throws IOException
    {
        Cache[] expectedCaches = {
                new Cache("GC2AETF\tLeaving the zone\tbeautiful143 & Strokin\t3\t1\tN40 41.375\tW111 52.963"),
                new Cache("GC1QDAP\tA Tough Nut to Crack\tSharktooth\t1\t2\tN40 41.060\tW111 55.098"),
                new Cache("GCMXVE\tPokemon Center\tRenaissance Man\t2\t1.5\tN40 38.170\tW111 51.602"),
                new Cache("GC2Z05B\tUpsidaisy Cache\tKingK66\t1\t1\tN40 38.458\tW111 54.812"),
                new Cache("GC23KAQ\tCupcakes!\tAD0OR\t2\t1.5\tN40 43.941\tW111 51.567"),
                new Cache("GC18YDB\tSLC Degrees of Confluence #04 3S3E\ttreponema\t3\t1.5\tN40 45.773\tW111 53.054") };
        int tallier = 0;

        
        try
        {
            cacheList = new CacheList(new Scanner(new File("caches.txt")));
            
        }
        catch (FileNotFoundException e)
        {
            fail("Cache file could not be found");
        }

        
        for (Cache actualCache : cacheList.select())
        {
            String actualCode = actualCache.getGcCode();

            for (Cache expectedCache : expectedCaches)
            {
                if (expectedCache.getGcCode().equals(actualCode))
                {
                    tallier++;
                    break;
                }
            }
        }
    }
        
    /**
     * Tests the ability to return lists
     */
    public void testSort() {
     
        try
        {
            cacheList = new CacheList(new Scanner(new File("caches.txt")));
            
        }
        catch (FileNotFoundException e)
        {
            fail("Cache file could not be found.");
        }
        catch (IOException e)
        {
            fail("Cache scanner had an IO error.");
        }

        cacheList.setTitleConstraint("Holladay");
        cacheList.setDifficultyConstraints(1.0, 3.0);

        Cache[] expectedCaches = { 
                new Cache("GC2793D\tHolladay\tTester\t2\t1.5\tN40 45.494\tW111 50.196"),
                new Cache("GC09YDG\tHolladay is my home\tLOLZAS\t3\t1.5\tN40 45.621\tW111 52.788"),
                new Cache("GC1PHP0\tGCHollday in the making\tDERPKK\t1.5\t1.5\tN40 40.249\tW111 56.449"),
                new Cache("GC8175\tI live in Holladay\tHarryPotter\t1.5\t1\tN40 45.775\tW111 52.913"),
                new Cache("GC1JP6R\tSTHOLLADAY\tLordVoldemort\t1\t1\tN40 40.171\tW111 56.364") };
        int tallier = 0;

        
        for (Cache actualCache : cacheList.select())
        {
            String actualCode = actualCache.getGcCode();

            for (Cache expectedCache : expectedCaches)
            {
                if (expectedCache.getGcCode().equals(actualCode))
                {
                    tallier++;
                    break;
                }
            }
        }

        assertTrue(tallier == expectedCaches.length);

    }

    @Test
    public void testSort2() {
        
     
        try
        {
            cacheList = new CacheList(new Scanner(new File("caches.txt")));
            
        }
        catch (FileNotFoundException e)
        {
            fail("Cache file could not be found.");
        }
        catch (IOException e)
        {
            fail("Cache scanner had an IO error.");
        }

        cacheList.setDifficultyConstraints(5, 5);
        cacheList.setTerrainConstraints(5, 5);
        cacheList.setOwnerConstraint("0");

        Cache[] expectedCaches = {
                new Cache("GC1CRBP\tTTYLESISTA\tCasualtyZer0\t5\t5\tN40 44.14w\tW111 49.242"),
                new Cache("GC39834\tGameBOYCOLOR\tJ0sh\t5\t5\tN40 42.033\tW111 47.650"),
                new Cache("GC1D075\tTheFrozenHearth\tAar0n\t5\t5\tN40 44.734\tW111 55.092") };
        int tallier = 0;

        
        for (Cache actualCache : cacheList.select())
        {
            String actualCode = actualCache.getGcCode();

            for (Cache expectedCache : expectedCaches)
            {
                if (expectedCache.getGcCode().equals(actualCode))
                {
                    tallier++;
                    break;
                }
            }
        }

        assertTrue(tallier == expectedCaches.length);

    }
    
    /**
     * Tests the ability to get owners and generate a list of all owners
     */
    @Test
    public void testOwners() {
     
        try
        {
            cacheList = new CacheList(new Scanner(new File("caches.txt")));
            
        }
        catch (FileNotFoundException e)
        {
            fail("FileNotFoundException");
        }
        catch (IOException e)
        {
            fail("IOException");
        }

        ArrayList<String> repetitionList = new ArrayList<String>();
        ArrayList<String> ownersList = cacheList.getOwners();

        for (String owner : ownersList)
        {
            if (repetitionList.contains(owner)) {
                fail("The test failed because there was a duplicate owner");
            }
            
            repetitionList.add(owner);
        }
    }
}

