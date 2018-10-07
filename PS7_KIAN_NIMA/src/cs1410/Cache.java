package cs1410;

/**
 * Represents a variety of information about a geocache. A geocache has a title, an owner, a difficulty rating, a
 * terrain rating, a GC code, a latitude, and a longitude.
 */
public class Cache
{
    public String GC_code = "";
    public String title = "";
    public String owner = "";
    public double difficulty = 0.0;
    public double terrainRating = 0.0;
    public String latitude = "";
    public String longitude = "";
   

    /**
     * Creates a Cache from a string that consists of these seven cache attributes: the GC code, the title, the owner,
     * the difficulty rating, the terrain rating, the latitude, and the longitude, in that order, separated by single
     * TAB ('\t') characters.
     * 
     * If any of the following problems are present, throws an IllegalArgumentException:
     * <ul>
     * <li>Fewer than seven attributes</li>
     * <li>More than seven attributes</li>
     * <li>A GC code that is anything other than "GC" followed by one or more upper-case letters and/or digits</li>
     * <li>A difficulty or terrain rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5,
     * or 5.</li>
     * <li>A title, owner, latitude, or longitude that consists only of white space</li>
     */
    public Cache (String attributes)
    {
        String[] cacheAttributes = attributes.split("\t");
        
        if(cacheAttributes.length != 7) {
            
            throw new IllegalArgumentException();
        }
        
        this.GC_code = cacheAttributes[0];
        if(!this.GC_code.matches("GC[A-Z0-9]+")) {
            
        throw new IllegalArgumentException();
        }
        
        this.title = checkEntryString(cacheAttributes[1]);
        this.owner = checkEntryString(cacheAttributes[2]);
        
        this.difficulty = parseToRating(cacheAttributes[3]);
        this.terrainRating = parseToRating(cacheAttributes[4]);
        
        this.latitude = checkEntryString(cacheAttributes[5]);
        this.longitude = checkEntryString(cacheAttributes[6]);
        
        
    }
    
    
    /**
     * Converts this cache to a string
     */
    public String toString ()
    {
        return getTitle() + " by " + getOwner();
    }

    /**
     * Returns the owner of this cache
     */
    public String getOwner ()
    {
        
        return this.owner;
    }

    /**
     * Returns the title of this cache
     */
    public String getTitle ()
    {
        
        return this.title;
    }

    /**
     * Returns the difficulty rating of this cache
     */
    public double getDifficulty ()
    {
        
        return this.difficulty;
    }

    /**
     * Returns the terrain rating of this cache
     */
    public double getTerrain ()
    {
        
        return this.terrainRating;
    }

    /**
     * Returns the GC code of this cache
     */
    public String getGcCode ()
    {
        
        return this.GC_code;
    }

    /**
     * Returns the latitude of this cache
     */
    public String getLatitude ()
    {
        
        return this.latitude;
    }

    /**
     * Returns the longitude of this cache
     */
    public String getLongitude ()
    {
        
        return this.longitude;
    }
    
    /**
     * Parses the entered string into a double rating
     * Will throw an exception if it cant be parsed to a double
     * 
     * @param rating
     * @return
     * @throws IllegalArgumentException
     */
    public double parseToRating(String string) throws IllegalArgumentException {
        
        final double[] Ratings = {1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5};
        double parsedRating = 0.0;
        
        try {
            parsedRating = Double.parseDouble(string);
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        
        for(double rating: Ratings) {
            if(parsedRating == rating) {
                return rating;
            }
        }
        throw new IllegalArgumentException();
    }
    
    public String checkEntryString(String string) throws IllegalArgumentException {
        
        if(string.isEmpty()) {
            throw new IllegalArgumentException();
        }
        
        return string;
    }
    
}
