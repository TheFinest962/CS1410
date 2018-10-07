package scan;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MyScanner
{

        private String delimiters = "\t \n\r\f";
        private String input = "";

        /**
         * Initializes a scanner with the given string as an input.
         * 
         * @param Input String
         */
        public MyScanner (String input)
        {
            this.input = input;
        }

        /**
         * Determines whether or not the scanner's input has another token.
         * 
         * @return True if another token is found, false if not.
         */
        public boolean hasNext ()
        {
            Pattern p = Pattern.compile("[^" + delimiters + "]");
            Matcher m = p.matcher(input);

            return (m.find());
        }

        /**
         * Returns the next token in the scanner.
         * 
         * @return Next scanner token
         * @throws NoSuchElementException if the scanner does not have another token
         */
        public String next ()
        {

            if (!hasNext())
            {
                throw new NoSuchElementException("Scanner doesnt have token");
            }

            int[] token = retrieveNextTokenIndices();

            String result = input.substring(token[0], token[1]);
            input = input.substring(token[1]);

            return result;

        }

        /**
         * Determines whether or not the next token in the scanner, if any, is an integer.
         * 
         * @return True if the next token is an integer, false if the next token is not an integer or if there is not next token.
         */
        public boolean hasNextInt ()
        {

            if (!hasNext())
            {

                return false;

            }
            else
            {

                int[] token = retrieveNextTokenIndices();

                String result = input.substring(token[0], token[1]);

                try
                {
                    Integer.parseInt(result);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }

            }

        }

        /**
         * If the next token in the scanner is an integer, returns that integer.
         * 
         * @return The next int in the scanner
         * @throws NoSuchElementException if the scanner is empty
         * @throws InputMismatchException if the next token is not an integer.
         */
        public int nextInt ()
        {

            if (!hasNext())
            {
                throw new NoSuchElementException("MyScanner does not have a next token");
            }
            else if (!hasNextInt())
            {
                throw new InputMismatchException("Next token is not an int.");
            }

            int[] token = retrieveNextTokenIndices();

            int result = Integer.parseInt(input.substring((token)[0], token[1]));
            input = input.substring(token[1]);

            return result;
        }

        /**
         * Retrieves the beginning and ending indices of the next token in the scanner.
         * 
         * @return
         */
        private int[] retrieveNextTokenIndices ()
        {

            int[] result = new int[2];

            int currentIndex = 0;

            
            while (currentIndex < input.length() && delimiters.contains(Character.toString(input.charAt(currentIndex))))
            {
                currentIndex++;
            }

            result[0] = currentIndex;

            
            while (currentIndex < input.length() && !delimiters.contains(Character.toString(input.charAt(currentIndex))))
            {
                currentIndex++;
            }

            result[1] = currentIndex++;

            return result;

        }

    }

