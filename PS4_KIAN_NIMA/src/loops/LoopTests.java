package loops;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;

public class LoopTests
{
    @Test
    public void testContainsToken ()
    {
        assertFalse(Loops.containsToken("", "xyz"));
        assertTrue(Loops.containsToken("xyz", "xyz"));
        assertTrue(Loops.containsToken("xyx xyy xyz xya", "xyz"));
        assertFalse(Loops.containsToken("xyx xyy xyZ xya", "xyz"));
    }

    @Test
    public void testFindLineWithToken ()
    {
        assertEquals("", Loops.findLineWithToken(new Scanner(""), "hello"));
        assertEquals("hello world", Loops.findLineWithToken(new Scanner("hello world"), "world"));
        assertEquals("", Loops.findLineWithToken(new Scanner("hello world"), "abc"));
        assertEquals("hello world", Loops.findLineWithToken(new Scanner("hello world"), "world"));
        assertEquals("this is a test",
                Loops.findLineWithToken(new Scanner("hello world\nthis is a test\nthis is another test"), "a"));
    }

    @Test
    public void testIsPalindrome ()
    {
        assertTrue(Loops.isPalindrome(""));
        assertTrue(Loops.isPalindrome("z"));
        assertFalse(Loops.isPalindrome("xy"));
        assertTrue(Loops.isPalindrome("abcddcba"));
        assertFalse(Loops.isPalindrome("abcddbba"));
        assertTrue(Loops.isPalindrome("abcdedcba"));
        assertFalse(Loops.isPalindrome("abcdedbba"));
    }

    @Test
    public void testFindLongestPalindrome ()
    {
        assertEquals("did", Loops.findLongestPalindrome(new Scanner("I did something wrong")));
        assertEquals("", Loops.findLongestPalindrome(new Scanner("This is an apple")));
        assertEquals("peep", Loops.findLongestPalindrome(new Scanner("a bb xyz\nI heard a peep sis")));
    }

    @Test
    public void testFindMostWhitespace ()
    {
        assertEquals(-1, Loops.findMostWhitespace(new Scanner("")));
        assertEquals(2, Loops.findMostWhitespace(new Scanner("a b c")));
        assertEquals(3, Loops.findMostWhitespace(new Scanner("a bb\na bb\t\t\nxyz")));
    }
}
