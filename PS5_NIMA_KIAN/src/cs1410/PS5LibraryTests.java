package cs1410;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import org.junit.Test;

public class PS5LibraryTests
{
    /**
     * This checks that the last line of the result ends with a newline, even if the last line in the scanner didn't.
     */
    @Test
    public void testScannerToString1 ()
    {
        assertEquals("This\nis a\ntest\n", PS5Library.scannerToString(new Scanner("This\nis a\ntest")));
        
    }
    @Test
    public void testScannerToString() {
        assertEquals("This is a test\n", PS5Library.scannerToString(new Scanner("This is a test")));
    }

    /**
     * This illustrates how to test that an exception is thrown when one is supposed to be thrown. If
     * pickCharThatFollowsPattern doesn't thrown the right kind of exception, the test will fail.
     */
    
    @Test
    public void testPickCharThatFollowsPattern1() {
        PS5Library.pickCharThatFollowsPattern("They are here", "he", new Random());
    }
    @Test(expected = NoSuchElementException.class)
    public void testPickCharThatFollowsPattern ()
    {
        PS5Library.pickCharThatFollowsPattern("hello", "o", new Random());
    }

    /**
     * This illustrates a way to do tests of methods that have a randomized behavior. When we ask for a randomly chosen
     * substring of length 4 of "abcde", about half the time we should get "abcd" and about half the time we should get
     * "bcde". So we call chooseSubstring 1000 times and count how many times we get each possibility. (If we get
     * anything else back, we immediately fail the test case.) Then we assert that we got each "about" half the time. It
     * is possible for a correct implementation to fail this test if we get extremely unlucky, but that is extremely
     * unlikely.
     */
    @Test
    public void testChooseSubstring ()
    {
        Random rand = new Random();
        int abcd = 0;
        int bcde = 0;

        for (int i = 0; i < 1000; i++)
        {
            String substring = PS5Library.chooseSubstring("abcde", 4, rand);
            if (substring.equals("abcd"))
            {
                abcd++;
            }
            else if (substring.equals("bcde"))
            {
                bcde++;
            }
            else
            {
                fail();
            }
        }

        assertTrue(400 <= abcd && abcd <= 600 && 400 <= bcde && bcde <= 600);
    }

    @Test
    public void testChooseSubstring1 ()
    {
        Random rand = new Random();
        int lmfa = 0;
        int mfao = 0;

        for (int i = 0; i < 1000; i++)
        {
            String substring = PS5Library.chooseSubstring("lmfao", 4, rand);
            if (substring.equals("lmfa"))
            {
                lmfa++;
            }
            else if (substring.equals("mfao"))
            {
                mfao++;
            }
            else
            {
                fail();
            }
        }

        assertTrue(400 <= lmfa && lmfa <= 600 && 400 <= mfao && mfao <= 600);
    }
    /**
     * This illustrates how to make assertions about ArrayLists.
     */
    @Test
    public void testGetCharsThatFollowPattern ()
    {
        ArrayList<Character> list = new ArrayList<Character>();
        list.add('b');
        list.add('b');
        assertEquals(list, PS5Library.getCharsThatFollowPattern("abababa", "aba"));
    }
    @Test
    public void testGetCharsThatFollowPattern1() {
        ArrayList<Character> list = new ArrayList<Character>();
        list.add('c');
        list.add('d');
        list.add('c');
        assertEquals(list, PS5Library.getCharsThatFollowPattern("abcabdabc", "ab"));
    }
}
