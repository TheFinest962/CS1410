package cs1410;

import static cs1410.MethodLibrary.containsAll;
import static cs1410.MethodLibrary.convertToPigLatin;
import static cs1410.MethodLibrary.getLifeStage;
import static cs1410.MethodLibrary.hailstones;
import static cs1410.MethodLibrary.makeLine;
import static cs1410.MethodLibrary.makeRectangle;
import static cs1410.MethodLibrary.sameTokens;
import static cs1410.MethodLibrary.sumOfRoots;
import static cs1410.MethodLibrary.toPigLatin;
import java.util.Scanner;

/**
 * A collection of methods for the third assignment of CS 1410.
 * 
 * @author Your name goes here
 */
public class MethodLibrary
{
    

    /**
     * You can use this main method for experimenting with your methods if you like, but it is not part of the
     * assignment.
     */
    public static void main (String[] args)
    {
        System.out.println(hailstones(1));
    }

    /**
     * Returns the life stage of a person, given his or her age. The possible return values are "baby" (age is less than
     * 2), "child" (age is between 2 and 12 inclusive), "teen" (age is between 13 and 17 inclusive), "adult" (age is
     * between 18 and 64 inclusive), and "senior" (age is greater than 64).
     * 
     * Examples: getLifeStage(25) is "adult" and getLifeStage(0) is "baby".
     * 
     * IMPLEMENTATION NOTE: Use a 5-way conditional
     * 
     * @param age Must be non-negative
     */
    public static String getLifeStage (int age)
    {
        String stage;
        if (age >= 0 && age < 2) {
            stage = "baby";
        }
        else if (age >= 2 && age < 13) {
            stage = "child";
        }
        else if (age >= 13 && age < 18) {
            stage = "teen";
        }
        else if (age >= 18 && age <= 64) {
            stage = "adult";
        }
        else {
            stage = "senior";
        }
        
        return stage;
    }
    

    /**
     * Returns the index within s of the first vowel ('a', 'e', 'i', 'o', 'u' or an upper-case version) that occurs in
     * s. If there is no vowel in s, returns -1.
     * 
     * Examples: firstVowelIndex("Apple") is 0, firstVowelIndex("hello") is 1, firstVowelIndex("slope") is 2,
     * firstVowelIndex("strength") is 4, and firstVowelIndex("xyzzy") is -1.
     * 
     * IMPLEMENTATION NOTE: This method is already completely implemented. There is no need for you to change anything.
     */
    public static int firstVowelIndex (String s)
    {
        int i = 0;
        while (i < s.length())
        {
            if ("aeiouAEIOU".indexOf(s.charAt(i)) >= 0)
            {
                return i;
            }
            i = i + 1;
        }
        return -1;
    }

    /**
     * Returns the result of converting s to "Pig Latin". Convert a string s to Pig Latin by using the following rules:
     * 
     * (1) If s contains no vowels, do nothing to it.
     * 
     * (2) Otherwise, if s starts with a vowel, append "way" to the end.
     * 
     * (3) Otherwise, move everything up to (but not including) the first vowel to the end and add "ay".
     * 
     * For example, "hello" converts to "ellohay", "small" converts to "allsmay", "eat" converts to "eatway", and "nth"
     * converts to "nth".
     * 
     * IMPLEMENTATION NOTE: This will require a three-way conditional that extracts pieces of Strings and recombines
     * them into new Strings. For full credit (and an easier implementation), use the firstVowelIndex method provided
     * above in your method's implementation. You will also find the methods s.substring() and s.charAt() (where s is a
     * String), as well as the + operator that concatenates Strings, very useful.
     */
    public static String toPigLatin (String s)
    {
        
        String start = "";
        String end = "";
        int firstVowel = 0;
        String appended;
        
        if(firstVowelIndex(s) == 0) {
            firstVowel = 0;
            end = end + "way";
        }
        if(firstVowelIndex(s) > 0 ) {
            firstVowel = firstVowelIndex(s);
            end = end + "ay";
        }
        
            start = s.substring(firstVowel, s.length());
            String change = s.substring(0, firstVowel);
            
            return appended = start + change + end;
            

    }
    
        

       
    /**
     * Returns the result of converting each token from words into Pig Latin and then appending the results, with each
     * converted word followed by a single space character. A token is a sequence of letters separated from other tokens
     * by white space. If there are no tokens, returns the empty string.
     * 
     * Examples: "" converts to "" and "This is a test" converts to "isThay isway away esttay ".
     * 
     * IMPLEMENTATION NOTE: Use a Scanner to split the string into individual tokens and use an accumulation loop to
     * consume the tokens. Make use of your toPigLatin() method.
     * 
     * @param words Must consist of only letters and white space
     */
    public static String convertToPigLatin (String words)
    {
        Scanner scn = new Scanner(words);
        String converted = "";
        String start = "";
        String end = "";
        String appended = "";
        int firstVowel = 0;
        
        while (scn.hasNext()) {
        String token = scn.next();
        
        
        
        converted += toPigLatin(token) + " ";
        }
        
        return converted;
    }

        
    

    
    /**
     * Returns the sum of the nth roots of each double x in numbers, where numbers consists of zero or more double
     * tokens (separated by white space) and n is positive. A small around of roundoff error is to be expected.
     * 
     * Examples: sumOfRoots("1 4 9 16", 2) is 10 and sumOfRoots("") is 0.
     * 
     * @param numbers Must consist of only double literals and white space
     * @param n must be positive
     */
    public static double sumOfRoots (String numbers, int n)
    {
        double sum = 0;
        Scanner scn = new Scanner (numbers); 
            
            while (scn.hasNext())
               {
                 String token = scn.next();
              
                 double roots = Double.parseDouble(token);
                 
                 double sqrt = Math.pow(roots, (1.0 / n));
                 
                 sum += sqrt;
                 
               }
        
            return sum;
               
      
    }
                    
                 
                 
                  

    /**
     * Reports whether or not every character in source occurs at least once in target.
     * 
     * Examples: containsAll("abc", "abracadabra") is true, and containsAll("def", "Defect") is false.
     * 
     * IMPLEMENTATION NOTE: Write this as an accumulation loop. Don't try to write a doubly-nested loop!
     */
    public static boolean containsAll (String source, String target)
    {

        boolean contains = true;

        for (int i = 0; i < source.length(); i++)
        {

            if (!target.contains(String.valueOf(source.charAt(i))))
            {
                contains = false;
            }
        }

        return contains;

    }
                 
               
    
            
    /**
     * Returns a String of length width that begins and ends with the character edge and contains width -2 copies of the
     * character inner in between. The method does not print anything.
     * 
     * Example makeLine('+', '-', 8) is "+------+".
     * 
     * IMPLEMENTATION NOTE: Use an accumulation loop
     * 
     * @params width must be >= 2
     */
    public static String makeLine (char edge, char inner, int width)
    {
        String line = "";
        for (int i = 2; i < width; i++) {
            
            line = line + inner;
            
        }
        return edge + line + edge;
    }

    /**
     * Returns a string which, when printed out, will be a rectangle shaped something like this:
     * 
     * <pre>
     * +----------+
     * |          |
     * |          |
     * |          |
     * |          |
     * |          |
     * +----------+
     * </pre>
     * 
     * The returned string should consist of height lines, each ending with a newline. In addition to the newline, the
     * first and last lines should begin and end with '+' and should contain width-2 '-' symbols. In addition to the
     * newline, the other lines should begin and end with '|' and should contain width-2 spaces. The method does not
     * print anything.
     * 
     * IMPLEMENTATION NOTE: For full credit (and for easier implementation), make use of the makeLine method in your
     * implementation of makeRectangle. Use an accumulation loop.
     * 
     * @param height must be >= 2
     * @param width must be >= 2
     */
    public static String makeRectangle (int height, int width)
    {
        
        String TopandBotRectangle = makeLine('+','-',width) + "\n";
        String middleRect = "";
        int i;
        
        for(i = 0; i < height - 2; i++) {
            
            middleRect += makeLine('|',' ', width) + "\n";
        }
        
        middleRect += makeLine('+', '-', width) + "\n";
    
        return TopandBotRectangle + middleRect;
    
    }
        

        
      
    

    /**
     * Returns the integer that follows n in a Hailstone sequence. If n is 1, returns 1. Otherwise, returns either n/2
     * (if n is even) or 3n+1 (if n is odd).
     * 
     * Examples: nextHailstone(1) is 1, nextHailstone(7) is 22, and nextHailstone(6) is 3,
     * 
     * IMPLEMENTATION NOTE: This one will require a three-way conditional
     * 
     * @param n must be positive
     */
    public static int nextHailstone (int n)
    {
        int result = 0;
        
        if(n == 1) {
            result = 1;
        }
        else if(n % 2 == 0) {
            result = n/2;
        } 
        else {
            result = (3 * n) + 1;
        }
        return result;
    }


    /**
     * Returns a string consisting of a Hailstone sequence beginning with the positive integer n and ending with 1. The
     * string should consist of a sequence of numerals, with each numeral followed by a single space. When a numeral m
     * (other than 1) appears in the sequence, it should be followed by nextHailstone(m).
     * 
     * Examples: nextHailstone(1) is "1 " and nextHailstone(5) is "5 16 8 4 2 1 ".
     * 
     * IMPLEMENTATION NOTE: Make use of your nextHailstone method. Use an accumulation loop, but with a bit of a twist.
     * 
     * @param n must be positive
     */
    public static String hailstones (int n)
    {
            String result = "";
            int calculation = 1;
            System.out.print(n + " ");
            while (n > 1)
            {
        
              if (n % 2 == 0)
              {
                 
                 n /= 2;
                  
               }
               else
               {
                 
                 n = (n*3) + 1;
                   
               }
               calculation++;
        
         
         String num = Integer.toString(n);
         result += (num + " ");
                 
               
        }
            return result;
    }
       
    

    /**
     * Reports whether or not s1 and s2 contain exactly the same tokens in the same order.
     * 
     * Examples: <br>
     * sameTokens("this is a test", " this is a test ") is true <br>
     * sameTokens("", "") is true <br>
     * sameTokens("hello there", "hello there Joe") is false <br>
     * sameTokens("abc def", "def abc") is false <br>
     * sameTokens("a", "A") is false
     *
     * IMPLEMENTATION NOTE: You can write this as an accumulation loop with a twist.  Be aware
     * of the implications of the short-circuited evaluation of &&.
     */
    public static boolean sameTokens (String s1, String s2)
    {

        Scanner scan1 = new Scanner(s1);
        Scanner scan2 = new Scanner(s2);

        while (scan1.hasNext() || scan2.hasNext())
        {
            if ((!scan1.hasNext() && scan2.hasNext()) || (!scan2.hasNext() && scan1.hasNext()))
            {
                return false;
            }
            else
            {
                String token1 = scan1.next();
                String token2 = scan2.next();

                if (!token1.equals(token2))
                {
                    return false;
                }
            }
        }

        return true;
    }

}
