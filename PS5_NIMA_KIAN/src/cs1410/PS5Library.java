package cs1410;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * A library of methods for implementing the random text generation algorithm described in PS5, Fall 2017.
 * 
 * @author Nima Kian and Joe Zachary
 */
public class PS5Library
{
    /**
     * Demonstrates the use of the generateText method.
     */
    public static void main (String[] args) throws IOException
    {
        // You won't need to use this feature in PS5, but this shows how to include a resource (in this
        // case a text file) as part of a project. I created a package called "books", then put two
        // text files into the package. I was then able to open one of those files as shown below. When
        // I export the project, the resources go along with it.
        /*
         * try (InputStream book = PS5Library.class.getResourceAsStream("/books/PrideAndPrejudice.txt"); Scanner input =
         * new Scanner(book)) { System.out.println(generateText(input, 6, 100)); }
         */
        
        // System.out.println(scannerToString(new Scanner("This\nis a\ntest")));
        // System.out.println(chooseSubstring("abcde", 2, new Random()));
        // System.out.println(getCharsThatFollowPattern("abababa", "aba"));
        // System.out.println(getCharsThatFollowPattern("abcabdabc", "ab"));
        // System.out.println(pickCharThatFollowsPattern("hello", "o", new Random()));
        // System.out.println(pickCharThatFollowsPattern("They are here", "he", new Random()));

    }

    /**
     * Uses all the text in the input to generate and return random text with the specified level and length, using the
     * algorithm described in PS5, CS 1410, Fall 2017.
     * 
     * @throws IllegalArgumentException if level < 0, or length < 0, or there are less than level+1 chars in the input.
     */
    public static String generateText (Scanner input, int level, int length)
    {
        // Validate the parameters
        if (level < 0 || length < 0)
        {
            throw new IllegalArgumentException();
        }

        // Grab all the text from the Scanner and make sure it is long enough.
        String text = scannerToString(input);
        if (level >= text.length())
        {
            throw new IllegalArgumentException();
        }

        // Create a random number generator to pass to the methods that make random choices
        Random rand = new Random();

        // Get the initial pattern.
        String pattern = chooseSubstring(text, level, rand);

        // Build up the final result one character at a time. We use a StringBuilder because
        // it is more efficient than using a String when doing long sequences of appends.
        StringBuilder result = new StringBuilder();
        while (result.length() < length)
        {
            try
            {
                // Pick at random a character that follows the pattern in the text and append it
                // to the result. If there is no such character (which can happen if the pattern
                // occurs only once, at the very end of text), the method we're calling will throw
                // a NoSuchElementException, which is caught below.
                char newChar = pickCharThatFollowsPattern(text, pattern, rand);
                result.append(newChar);

                // Update the pattern by removing its first character and adding on the new
                // character. The length of the pattern remains the same. If the pattern is
                // the empty string, though, it never changes.)
                if (pattern.length() > 0)
                {
                    pattern = pattern.substring(1) + newChar;
                }
            }
            catch (NoSuchElementException e)
            {
                // It is possible to get stuck if the pattern occurs only once in the text and
                // that occurrence is at the very end of the text. In this case, we pick a new
                // seed and keep going.
                pattern = chooseSubstring(text, level, rand);
            }
        }

        // Return the string we've accumulated.
        return result.toString();
    }

    /**
     * Takes a scanner as a param and returns a String. The returned string consists of all characters in scanner
     * including the \n. The last line will always end with a newLine. e.g. scannerToString("This\nis a\ntest") will
     * return "This\nis a\ntest\n".
     */

    public static String scannerToString (Scanner scan)
    {
        String result = "";

        while (scan.hasNext())
        {
            String input = scan.nextLine();

            StringBuffer buffer = new StringBuffer(input);

            String buffed = buffer.toString();

            result = result + buffed + "\n";
        }

        return result;
    }

    /**
     * Uses random number generator to return a random substring of a certain length in a string. e.g.
     * chooseSubstring("abcde", 4, new Random()) will return "abcd" or "bcde" e.g. chooseSubstring("abcde", 2, new
     * Random()) will return "ab", "bc", "de", "cd", etc.
     * 
     * @param text
     * @param length
     * @param rand
     * @return
     */
    public static String chooseSubstring (String text, int length, Random rand)
    {

        int randomNum = rand.nextInt(text.length() - length + 1);
        String result = text.substring(randomNum, randomNum + length);
        return result;
    }

    /**
     * Takes a string text and string pattern as parameters and returns an arrayList<Characters> Finds the index at
     * which the end of the pattern in a string is and prints the character after the pattern e.g.
     * getCharsThatFollowPattern("abcabdabc", "ab") will return [c, d, c]
     * 
     * @param text
     * @param pattern
     * @return
     */
    public static ArrayList<Character> getCharsThatFollowPattern (String text, String pattern)
    {

        ArrayList<Character> character = new ArrayList<Character>();

        char c;
        int lastIndex = 0;
        int patLength = pattern.length();

        while (text.length() >= lastIndex + pattern.length())
        {
            int newIndex = text.indexOf(pattern, lastIndex);
            if (newIndex == -1 || newIndex + patLength >= text.length())
            {
                return character;
            }
            c = text.charAt(newIndex + patLength);
            character.add(c);
            lastIndex = newIndex + 1;
        }

        return character;
    }

    /**
     * So this basically just does exactly the same thing as getCharsThatFollow except its random. 
     * @param text
     * @param pattern
     * @param rand
     * @return
     */
    public static char pickCharThatFollowsPattern (String text, String pattern, Random rand)
    {

        ArrayList<Character> character = new ArrayList<Character>();
        char random = ' ';

        character = getCharsThatFollowPattern(text, pattern);

        if (character.size() == 0)
        {
            throw new NoSuchElementException();

        }

        random = character.get(rand.nextInt(character.size()));

        return random;

    }

}
