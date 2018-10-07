package scan;

import static org.junit.Assert.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import org.junit.Test;

public class MyScannerTests
{

    /**
     * Tests the ability of the MyScanner class to return the same .next() results as its Scanner counterpart.
     * 
     * The sample inputs used are the same every time and represent some more common use cases.
     * 
     * Fails if any exceptions are thrown, or if any returned MyScanner.next() values are different than the equivalent Scanner.next() values.
     */
    @Test
    public void nextTest ()
    {
        
        String[] Inputs = { "Once upon a time there\n" + 
                                  "was a ,\n" +
                                  "tiny guy who n" + 
                                  "went to the barn\n" + 
                                  "it got eaten by a spider\n" + 
                                  "and the spider pooped it out.\n" + 
                                  "\n" + 
                                  "This was a sad story,\n" + 
                                  "but to make you feel better\n" + 
                                  "someone killed the spider",
                                  
                                  "6. 1 4 1 59 2 /t/t/t/t     ><0124\\\n" +
                                  "sldkfjb alsdf pge asbdjsdjsdg (*&^% 19" +
                                  "asjdlfjj lkdasjdlkfj aslkdfjl\n" +
                                  "alsjdfkj jalkfsjdlkfj ldkdei \n" +
                                  "asldfje eikdjfkals kdkjfl finaldkd",
                                  
                                  "askd dkfksdf, fask;dfjkasd, s\n" +
                                  "asjdk verklajstikajsd dksadfl \n" +
                                  "sad j haljsdhf shldfhjas dlfj, \n" +
                                  "ahsldfjh asjlkdfhlashfljashdfal\n" +
                                  "ahsldfh asldfhjahsljdfhlahsdflkj\n" +
                                  "shldfh lasdjf lasjdf lashfdj alshdf\n" +
                                  "ahlsdjf ahlsjdfhlajshdflkjhajklsdhflk\n" +
                                  "alshdjfh aslhdfjl eiuuire fdjanrjerkjs\n" +
                                  "as df;jkwjenfjsa jdklf;ie;jfkjdfas;\n" +
                                  "as;dfksjer;ehaflsjdk;f oi;fjdaklsdjf;\n" +
                                  "nfja;sdjrk ;e jkf;asdflajksdf, ",
                                };
        
        for (String input : Inputs)
        {

            Scanner expectedScan = new Scanner(input);
            MyScanner actualScan = new MyScanner(input);

            while (expectedScan.hasNext())
            {

                if (!actualScan.hasNext())
                {
                    fail("YA FAILED");
                }

                assertEquals(expectedScan.next(), actualScan.next());

            }

            expectedScan.close();

        }

    }
    
    /**
     * Tests the ability of the MyScanner class to return the same .next() results as its Scanner counterpart.
     * 
     * The sample input used is random every time, generated using alphanumerics and special (i.e. tabs, newline) characters.
     * 
     * Fails if any exceptions are thrown, or if any returned MyScanner.next() values are different than the equivalent Scanner.next() values.
     */
    @Test
    public void randNextTest ()
    {

        final int LENGTH = 500;
        final String charBank = " abcdefghijklmnopqrstuvwxyz1234567890\n\t\r\b\f\"\'";
        Random randGen = new Random();

        StringBuilder randomSample = new StringBuilder();

        for (int i = 0; i < LENGTH; i++)
        {
            randomSample.append(charBank.charAt(randGen.nextInt(charBank.length())));
        }
        
        Scanner expectedScan = new Scanner(randomSample.toString());
        MyScanner actualScan = new MyScanner(randomSample.toString());

        while (expectedScan.hasNext())
        {

            if (!actualScan.hasNext())
            {
                fail("YA FAILED");
            }
            
            String expected = expectedScan.next();
            String actual = actualScan.next();

            assertEquals(expected, actual);

        }

        expectedScan.close();

    }

    /**
     * Tests MyScanner.next()'s ability to throw a NoSuchElementException if the scanner is out of tokens and .next() is called.
     * 
     * Fails if MyScanner.next() does not throw a NoSuchElementException after all tokens are exhausted.
     */
    @Test(expected = NoSuchElementException.class)
    public void nextExceptionTest ()
    {

        String Input = "1 2 3 4 5 6 7 8";

        MyScanner Scan = new MyScanner(Input);

        while (Scan.hasNext())
        {

            Scan.next();

        }

        Scan.next();

    }
    
    /**
     * Tests MyScanner.hasNextInt()'s ability to determine if the next token in a Scanner, if any, is an integer.
     * Results are compared to that of Scanner.hasNextInt(); if results are ever different, the test fails.
     * 
     * Tests uses a randomly-generated set of 1000 integers ranging from 0 to 100.
     * 
     * The test will fail if MyScanner.hasNextInt()'s returned value differs from Scanner.hasNextInt()'s returned value, if MyScanner.nextInt()'s
     * returned value is different than Scanner.hasNextInt()'s returned value, or if any exceptions are thrown.
     */
    @Test
    public void nextIntTest ()
    {
        StringBuilder Data = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 1000; i++)
        {
            Data.append(rand.nextInt(100));
            Data.append('\t');
        }

        Scanner expectedScan = new Scanner(Data.toString());
        MyScanner actualScan = new MyScanner(Data.toString());

        while (expectedScan.hasNextInt())
        {

            if (!actualScan.hasNextInt())
            {
                fail("Ya Failed");
            }

            assertTrue(expectedScan.nextInt() == actualScan.nextInt());

        }

        expectedScan.close();

    }
    
    /**
     * Tests MyScanner's ability to throw an InputMismatchException in cases where
     * .nextInt() is called, but the next token is not a parsable integer.
     * 
     * Also tests to see if this exception is handled in the same manner as Scanner,
     * i.e. an InputMismatchException does not cause the scanner to go to the next token.
     * 
     * Fails if the exception is not thrown, the exception thrown is not of the right type, 
     * or if the returned MyScanner.nextInt() values do not match the Scanner.nextInt() values.
     */
    @Test
    public void mismatchIntTest ()
    {

        String Data = "a 4 5 6";

        Scanner expectedScan = new Scanner(Data);
        MyScanner actualScan = new MyScanner(Data);

       
        try
        {
            actualScan.nextInt();
            fail("nextInt should have thrown some kind of exception.");
        }
        catch (Exception e)
        {
            if (e.getClass() != InputMismatchException.class)
            {
                fail("nextInt should not have thrown a " + e.getClass().getName() + " exception.");
            }
        }

      
        assertEquals(expectedScan.next(), actualScan.next());

        while (actualScan.hasNextInt())
        {
            assertTrue(expectedScan.nextInt() == actualScan.nextInt());
        }

        expectedScan.close();

    }
    
    /**
     * Tests MyScanner's ability to react to null values in the constructor in the same manner as Scanner.
     * 
     * Fails if the exceptions thrown are different.
     */
    @Test
    public void nullInputTest ()
    {
        String Data = null;
        Exception expectedException = null;
        Exception actualException = null;
        
        try {
            Scanner expectedScanner = new Scanner(Data);
            String test = expectedScanner.next();
            test.trim();
            expectedScanner.close();
        } catch (Exception e) {
            expectedException = e;
        }
        
        try {
            MyScanner actualScanner = new MyScanner(Data);
            String test = actualScanner.next();
            test.trim();
        } catch (Exception e) {
            actualException = e;
        }
        
        assertEquals(expectedException.getClass(), actualException.getClass());
        
    }

}
