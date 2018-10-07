package cs1410;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A CacheList is a collection of Cache objects together with these six constraints:
 * 
 * <ol>
 * <li>A title constraint</li>
 * <li>An owner constraint</li>
 * <li>A minimum difficulty constraint</li>
 * <li>A maximum difficulty constraint</li>
 * <li>A minimum terrain constraint</li>
 * <li>A maximum terrain constraint</li>
 * </ol>
 */
public class CacheList
{
    // The caches being managed by this CacheList. They are arranged in
    // ascending order according to cache title.
    private ArrayList<Cache> allCaches;

    public String titleConstraint = "";
    public String ownerConstraint = "";
    public double minDifficulty = 0.0;
    public double maxDifficulty = 0.0;
    public double minTerrain = 0.0;
    public double maxTerrain = 0.0;

    /**
     * Creates a CacheList from the specified Scanner. Each line of the Scanner should contain the description of a
     * cache in a format suitable for consumption by the Cache constructor. The resulting CacheList should contain one
     * Cache object corresponding to each line of the Scanner.
     * 
     * Sets the initial value of the title and owner constraints to the empty string, sets the minimum difficulty and
     * terrain constraints to 1.0, and sets the maximum difficulty and terrain constraints to 5.0.
     * 
     * Throws an IOException if the Scanner throws an IOException, or an IllegalArgumentException if any of the
     * individual lines are not appropriate for the Cache constructor.
     * 
     * When an IllegalArgumentException e is thrown, e.getMessage() is the number of the line that was being read when
     * the error that triggered the exception was encountered. Lines are numbered beginning with 1.
     */
    public CacheList (Scanner caches) throws IOException
    {
        
        allCaches = new ArrayList<Cache>();
        
        int counter = 0;
        
        while(caches.hasNextLine()) {
            
            counter++;
            
            try {
                allCaches.add(new Cache(caches.nextLine()));
            }
            catch(IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
            
        }

        // Sort the list of caches
        Collections.sort(allCaches, (c1, c2) -> c1.getTitle().compareToIgnoreCase(c2.getTitle()));
        
        
    }

    /**
     * Sets the title constraint to the specified value.
     */
    public void setTitleConstraint (String title)
    {
        this.titleConstraint = title.toLowerCase();
    }

    /**
     * Sets the owner constraint to the specified value.
     */
    public void setOwnerConstraint (String owner)
    {
        this.ownerConstraint = owner.toLowerCase();
    }

    /**
     * Sets the minimum and maximum difficulty constraints to the specified values.
     */
    public void setDifficultyConstraints (double min, double max)
    {
         this.minDifficulty = min;
         this.maxDifficulty = max;
         
    }

    /**
     * Sets the minimum and maximum terrain constraints to the specified values.
     */
    public void setTerrainConstraints (double min, double max)
    {
        this.minTerrain = min;
        this.maxTerrain = max;
    }

    /**
     * Returns a list that contains each cache c from the CacheList so long as c's title contains the title constraint
     * as a substring, c's owner equals the owner constraint (unless the owner constraint is empty), c's difficulty
     * rating is between the minimum and maximum difficulties (inclusive), and c's terrain rating is between the minimum
     * and maximum terrains (inclusive). Both the title constraint and the owner constraint are case insensitive.
     * 
     * The returned list is arranged in ascending order by cache title.
     */
    public ArrayList<Cache> select ()
    {
        
        ArrayList<Cache> caches = new ArrayList<Cache>();
        
        for(Cache cache: this.allCaches) {
             if(cache.getDifficulty() >= this.minDifficulty
             && cache.getDifficulty() <= this.maxDifficulty
             && cache.getTerrain() >= this.minTerrain
             && cache.getTerrain() <= this.maxTerrain
             && cache.getTitle().toLowerCase().contains(this.titleConstraint)
             && cache.getOwner().toLowerCase().contains(this.ownerConstraint)) {
                 
                 caches.add(cache);
             }
             
        }
        return caches;
    }

    /**
     * Returns a list containing all the owners of all of the Cache objects in this CacheList. There are no duplicates.
     * The list is arranged in ascending order.
     */
    public ArrayList<String> getOwners ()
    {
        
        ArrayList<String> owners = new ArrayList<String>();
        
        for (Cache cache: this.allCaches) {
            String owner = cache.getOwner();
            if(!(owners.contains(owner))) {
                owners.add(owner);
            }
        }

        // Sort the list of owners
        Collections.sort(owners, (s1, s2) -> s1.compareToIgnoreCase(s2));
        return owners;
    }
}
