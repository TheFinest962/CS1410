package cs1410;

import java.awt.Dialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import cs1410lib.Dialogs;

public class Generator {


public static void main (String[] args) throws IOException {
    int index = 0;
    int level = 0;
    int length = 0;

    while (true)
    {
        try
        {
            if (index == 0)
            {
                String analysisLevel = Dialogs.showInputDialog("Enter desired analysis level.");
                if (analysisLevel == null)
                    return;
                level = Integer.parseInt(analysisLevel);
                if (level < 0)
                {
                    throw new NumberFormatException();
                }
                index++;
            }
            if (index == 1)
            {
                String lengthOfOutput = Dialogs.showInputDialog("Enter desired length of the output text.");
                if (lengthOfOutput == null)
                    return;
                length = Integer.parseInt(lengthOfOutput);
                if (length < 0)
                {
                    throw new NumberFormatException();
                }
                index++;
            }
            if (index == 2)
            {

                File textFile = Dialogs.showOpenFileDialog("Select a text file");

                try (FileInputStream textFile1 = new FileInputStream(textFile);
                        Scanner textScn = new Scanner(textFile1))
                {
                    Dialogs.showMessageDialog(PS5Library.generateText(textScn, level, length));
                    return;
                }
            }
        }

        catch (NumberFormatException e)
        {
        }
        catch (FileNotFoundException e)
        {
        }
        catch (NullPointerException e)
        {
            return;
        }
    }
}
}



            
                
               
