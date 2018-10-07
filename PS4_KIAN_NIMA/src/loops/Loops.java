/**
 * 
 */
package loops;

import java.util.Scanner;

/**
 * @author NimaKian
 * @UNID u1130862
 *
 */
public class Loops
{

    /**
     * Main Method
     * 
     * @param args
     */
    public static void main (String[] args)
    {
        
    }

    /**
     * Takes the string t and compares it against string s. Returns true if string s contains string t and return false
     * otherwise.
     * 
     * @param s
     * @param t
     */

    public static boolean containsToken (String s, String t)
    {
        Scanner scan = new Scanner(t);

        String contained = scan.next();

        boolean result = true;
        if (!s.contains(contained))
        {

            result = false;

        }

        return result;
        
    }
    
    /*
     * takes a Scanner scan and a String t as parameters, and returns a String. 
     * It returns a line from scanner that contains t as a token if the token exists.
     * Returns the empty string (otherwise).
     */
    public static String findLineWithToken (Scanner scan, String t)
    {

        String result = "";
        while (scan.hasNext())
        {
            String contained = scan.nextLine();
            if (containsToken(contained, t))
            {

                return contained;
            }
        }
        return result;
        
    }
    
    /*
     * Takes a string s as input and returns true or false.
     * Returns true if the string is spelled same forwards and backwards.
     * Returns false otherwise.
     */
    public static boolean isPalindrome (String s) {
  
        Boolean result = false;
        String revstring = "";
        
        if(s.length() == 0) {
            result = true;
        }
        
        for (int i = s.length() - 1; i >= 0; --i)
        {
            revstring += s.charAt(i);

        }

        if (revstring.equalsIgnoreCase(s))
        {

            result = true;

        }
        else
        {

            result = false;

        }

        return result;
    }
    
    /*
     * Takes a string from a scanner and returns a string.
     * It returns the longest token of the string that is a Palindrome.
     * If Palindrome doesn't exist in string then an empty string is returned.
     */
    public static String findLongestPalindrome(Scanner scan) {
        
        
        String longestPalindrome = "";
        
        while(scan.hasNext()) {
            
            String enteredString = scan.next();
           
   
                if(isPalindrome(enteredString)) {
                    
                    if(longestPalindrome.length() < enteredString.length()) {
                        
                    longestPalindrome = enteredString;
                    
                    }
                }
        }
        return longestPalindrome;
        
    }
    
    /*
     * Takes a string as input(Scanner as parameter) and returns int. 
     * Counts number of white space in everyLine and returns the amount of WhiteSpace.
     */
    public static int findMostWhitespace(Scanner scan) {
        
    int word = -1;

    while (scan.hasNextLine())
    {
        String counter = scan.nextLine();
        int i = 0;
        int whiteSpaceCounter = 0;

        while (i < counter.length())
        {

            if (Character.isWhitespace(counter.charAt(i)))
            {
                ++whiteSpaceCounter;
            }
            ++i;
        }
        if (whiteSpaceCounter > word)
        {
            word = whiteSpaceCounter;
        }
    }
    return word;
}
}
    

