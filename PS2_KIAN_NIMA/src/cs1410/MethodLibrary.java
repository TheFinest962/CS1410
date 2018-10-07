package cs1410;

import java.util.Date;
import java.util.Scanner;
import static cs1410.MethodLibrary.largestOfFive;
import static cs1410.MethodLibrary.nthRoot;
import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

/**
 * A collection of methods for the second assignment of CS 1410.
 * 
 * @author Nima Kian
 */
public class MethodLibrary
{
    /**
     * You can use this main method for experimenting with your methods if you like, but it is not part of the
     * assignment.
     */
    public static void main (String[] args)
    {
        
    }

    /**
     * Returns the nth root of x, where n is positive. For example, nthRoot(27.0, 3) is 3.0 (the cube root of 27), and
     * nthRoot(64.0, 6) is 2.0. NOTE: A small amount of roundoff error is acceptable.
     * 
     * The number x is required to have a real-valued nth root, and n is required to be positive. If this requirement is
     * violated, the behavior of the method is undefined (it does not matter what it does).
     */
    public static double nthRoot (double x, int n)
    {
        double root = Math.pow(x, 1.0 / n);
        return root;
    }

    /**
     * Reports whether or not c is a vowel ('a', 'e', 'i', 'o', 'u' or the upper-case version). For example,
     * isVowel('a') and isVowel('U') are true; isVowel('x') and isVowel('H') are false.
     */
    public static boolean isVowel (char c)
    {

        return "AEIOUaeiou".indexOf(c) != -1;

    }

    /**
     * Reports whether or not number is a multiple of both factor1 and factor2. For example, isMultipleOf(15, 3, 5) is
     * true and isMutipleOf(27, 3, 4) is false.
     * 
     * Both factor1 and factor2 are required to be non-zero. If this requirement is violated, the behavior of the method
     * is undefined (it does not matter what it does).
     */
    public static boolean isMultipleOf (int number, int factor1, int factor2)
    {
        boolean numMultiple = ((number % factor1) == 0);
        boolean numMultiple2 = ((number % factor2) == 0);
        boolean result = numMultiple && numMultiple2 == true;
        return result;
    }

    /**
     * Returns the string that results from capitalizing the first character of s, which is required to have at least
     * one character. For example, capitalize("hello") is "Hello" and capitalize("Jack") is "Jack".
     * 
     * The string s is required to be non-empty. If this requirement is violated, the behavior of the method is
     * undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The Character.toUpperCase() method will be helpful. The method s.substring() [where s is a
     * String] will also be helpful. Learn more about them before starting!
     */
    public static String capitalize (String s)
    {
        String upperCase = s.substring(0, 1).toUpperCase();

        return upperCase + s.substring(1);
    }

    /**
     * Returns a new string that (1) begins with all the characters of original that come after the first occurrence of
     * target and (2) ends with all the characters of target that come before the first occurrence of pattern. For
     * example, flip("abcdefg", 'd') is "efgabc", flip("ababad", 'b') is "abada", and flip("x", 'x') is "".
     * 
     * The string original is required to contain the character target. If this requirement is violated, the behavior of
     * the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The methods s.indexOf() and s.substring() [where s is a String] will be helpful.
     */
    public static String flip (String original, char pattern)
    {
        int targetCH = original.indexOf(pattern);
        int lengthStr = original.length();
        String firstOccurrence = original.substring(0, targetCH);
        String secondOccurrence = original.substring(targetCH + 1, lengthStr);
        String flippedString = secondOccurrence.concat(firstOccurrence);

        return flippedString;
    }

    /**
     * Returns a new string that is just like s except all of the lower-case vowels ('a', 'e', 'i', 'o', 'u') have been
     * capitalized. For example, capitalizeVowels("hello") is "hEllO", capitalizeVowels("String") is "StrIng", and
     * capitalizeVowels("nth") is "nth".
     * 
     * IMPLEMENTATION HINT: The method s.replace() [where s is a String] will be helpful.
     */
    public static String capitalizeVowels (String s)
    {
        String replace = s.replaceAll("a", "A").replaceAll("e", "E").replaceAll("i", "I").replaceAll("o", "O")
                .replaceAll("u", "U");

        return replace;
    }

    /**
     * Reports whether s1 and s2 end with the same n characters. For example, sameEnding("abcde" "xde", 2) is true and
     * sameEnding("abcde", "xde", 3) is false.
     * 
     * The value of n is required to be non-negative, and the two strings must each contain at least n characters. If
     * this requirement is violated, the behavior of the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The methods s.length() and s.substring() [where s is a String] will be helpful.
     */
    public static boolean sameEnding (String s1, String s2, int n)
    {
        int s1Length = s1.length();
        int s2Length = s2.length();
        String firstString = s1.substring(s1Length - n, s1Length);
        String secondString = s2.substring(s2Length - n, s2Length);

        if (firstString.equals(secondString))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the value of the largest of the five int literals, separated by white space, that make up the integerList
     * parameter. For example, largestOfFive(" 15 28 -99 62 44 ") is 62.
     * 
     * The string integerList is required to consist of exactly five int literals surrounded by white space. If this
     * requirement is violated, the behavior of the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The class java.util.Scanner will be helpful. Use the one-argument constructor that takes a
     * String as a parameter and the nextInt() method.
     */
    public static int largestOfFive (String integerList)
    {  
        Scanner scan = new Scanner(integerList);
        int firstInt = scan.nextInt();
        int secondInt = scan.nextInt();
        int thirdInt = scan.nextInt();
        int fourthInt = scan.nextInt();
        int fifthInt = scan.nextInt();
        
        int firstTest = Math.max(firstInt, secondInt);
        int secondTest = Math.max(firstTest, thirdInt);
        int thirdTest = Math.max(secondTest, fourthInt);
        int fourthTest = Math.max(thirdTest, fifthInt);
        
        int max = fourthTest;
        
        scan.close();
        return max;
        
       
    }
    

    /**
     * Reports whether or not date1 comes earlier in time than date2. For example, isEarlierThan("12-01-2015",
     * "02-15-2017") is true but isEarlierThan("10-11-2016", "10-11-2016") and isEarlierThan("09-09-1967", "02-15-1933")
     * is false.
     * 
     * The two parameters must be of the form MM-DD-YYYY where YYYY is a year, MM is the two-digit number of a month, DD
     * is a two-digit number of a day, and the entire date is valid. If this requirement is violated, the behavior of
     * the method is undefined (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: Turn this into a String comparison problem.
     * 
     */

    public static boolean isEarlierThan (String date1, String date2)
    {

        String month1 = date1.substring(0, 2);
        String month2 = date2.substring(0, 2);

        String day1 = date1.substring(3, 5);
        String day2 = date2.substring(3, 5);

        String year1 = date1.substring(6, 10);
        String year2 = date2.substring(6, 10);

        date1 = year1 + month1 + day1; // + month1 + day1
        date2 = year2 + month2 + day2; // + month2 + day2

        if (date1.compareTo(date2) < 0)
        {
            return true;
        }
        else if (date1.compareTo(date2) > 0)
        {
            return false;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the integer numeral that represents the sum of the integer numerals integer1 and integer2. For example,
     * addNumerals("125", "64") is "189". The method works for numerals of any length, including numerals that consist
     * of hundreds or thousands of digits and are far too big to parse as ints or longs.
     * 
     * The two parameters must both be valid integer numerals. That is, both must consist of one or more digits
     * optionally preceded by a + and - sign. If this requirement is violated, the behavior of the method is undefined
     * (it does not matter what it does).
     * 
     * IMPLEMENTATION HINT: The class java.math.BigInteger will be helpful. Use the one-argument constructor that takes
     * a String as a parameter, the add method(), and the toString() method.
     */
    public static String addNumerals (String integer1, String integer2)
    {

        BigInteger bigInt, bigInt2, sum;
        bigInt = new BigInteger(integer1);
        bigInt2 = new BigInteger(integer2);
        sum = bigInt.add(bigInt2);
        String result = String.valueOf(sum);

        return result;
    }
}
