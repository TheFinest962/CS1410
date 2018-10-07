package cs1410;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import org.junit.Test;

public class MyTestCases
{

    @Test
    public void testReadTable()
    {

        String input = "Nevada\t20" + "\nUtah\t40" + "\nCalifornia\t80" + "\nArizona\t10" + "\nIdaho\t40" + "\nNevada\t120" + "\nCalifornia\t20";

        Scanner scan = new Scanner(input);

        TreeMap<String, ArrayList<Double>> expected = new TreeMap<String, ArrayList<Double>>();

        expected.put("Nevada", new ArrayList<Double>());
        expected.get("Nevada").add(20.0);
        expected.get("Nevada").add(120.0);

        expected.put("Utah", new ArrayList<Double>());
        expected.get("Utah").add(40.0);

        expected.put("California", new ArrayList<Double>());
        expected.get("California").add(80.0);
        expected.get("California").add(20.0);

        expected.put("Arizona", new ArrayList<Double>());
        expected.get("Arizona").add(10.0);

        expected.put("Idaho", new ArrayList<Double>());
        expected.get("Idaho").add(40.0);

        TreeMap<String, ArrayList<Double>> actual = GraphingMethods.readTable(scan);

        assertEquals(expected, actual);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTableNoDouble() {
        
        String input = "Nevada\t10" + "\nUtah\t20" + "\nCalifornia\t30" + "\nArizona\t40" + "\nIdaho\t50" + "\nWyoming\t";
        
        Scanner scan = new Scanner(input);
        
        GraphingMethods.readTable(scan);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testReadTableExtraString() {
        
        String input = "Nevada\tNevada" + "\nUtah\t20" + "\nCalifornia\t30" + "\nArizona\t40" + "\nIdaho\t50" + "\nWyoming\t60";
        
        Scanner scan = new Scanner(input);
        
        GraphingMethods.readTable(scan);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testReadTableNoTab() {
        
        String input =  "Nevada\t10" + "\nUtah\t20" + "\nCalifornia\t30" + "\nArizona\t40" + "\nIdaho\t50" + "\nWyoming 60";
        
        Scanner scan = new Scanner(input);
        
        GraphingMethods.readTable(scan);
    }
    
    @Test
    public void testPrepareGraph() {
        
        String input =   "Nevada\t20" + "\nNevada\t80" + "\nUtah\t20" + "\nUtah\t40" + "\nCalifornia\t40"  + "\nCalifornia\t80"
        + "\nIdaho\t50" + "\nIdaho\t150" + "\nWyoming\t50" + "\nNevada\t170"; 
        
        Scanner scan = new Scanner(input);
        
        TreeMap<String, ArrayList<Double>> testMaps = GraphingMethods.readTable(scan);
        
        TreeMap<String, Double> expectedMin = new TreeMap<>();
        expectedMin.put("Nevada", 20.0);
        expectedMin.put("Utah", 20.0);
        expectedMin.put("California", 40.0);
        expectedMin.put("Idaho", 50.0);
        expectedMin.put("Wyoming", 50.0);
        TreeMap<String, Double> actualMin = GraphingMethods.prepareGraph(testMaps, GraphingMethods.MIN);
        assertEquals(expectedMin, actualMin);
        
        
        TreeMap<String, Double> expectedMax = new TreeMap<>();
        expectedMax.put("Nevada", 170.0);
        expectedMax.put("Utah", 40.0);
        expectedMax.put("California", 80.0);
        expectedMax.put("Idaho", 150.0);
        expectedMax.put("Wyoming", 50.0);
        TreeMap<String, Double> actualMax = GraphingMethods.prepareGraph(testMaps, GraphingMethods.MAX);
        assertEquals(expectedMax, actualMax);
        
        
        TreeMap<String, Double> expectedSum = new TreeMap<>();
        expectedSum.put("Nevada", 270.0);
        expectedSum.put("Utah", 60.0);
        expectedSum.put("California", 120.0);
        expectedSum.put("Idaho", 200.0);
        expectedSum.put("Wyoming", 50.0);
        TreeMap<String, Double> actualSum = GraphingMethods.prepareGraph(testMaps, GraphingMethods.SUM);
        assertEquals(expectedSum, actualSum);
        
        
        TreeMap<String, Double> expectedAvg = new TreeMap<>();
        expectedAvg.put("Nevada", 90.0);
        expectedAvg.put("Utah", 30.0);
        expectedAvg.put("California", 60.0);
        expectedAvg.put("Idaho", 100.0);
        expectedAvg.put("Wyoming", 50.0);
        TreeMap<String, Double> actualAvg = GraphingMethods.prepareGraph(testMaps, GraphingMethods.AVG);
        assertEquals(expectedAvg, actualAvg);
        
    }
       
    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraphEMPTY() {
        
        TreeMap<String, ArrayList<Double>> testMaps = new TreeMap<>();
        GraphingMethods.prepareGraph(testMaps, 1); 
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraphNEGATIVE() {
        
        String input =   "Nevada\t-20" + "\nNevada\t80" + "\nUtah\t20" + "\nUtah\t-40" + "\nCalifornia\t40"  + "\nCalifornia\t80"
                + "\nIdaho\t50" + "\nIdaho\t150" + "\nWyoming\t50" + "\nNevada\t-170"; 
        
        Scanner scan = new Scanner(input);
        
        TreeMap<String, ArrayList<Double>> testMaps = GraphingMethods.readTable(scan);
        
        GraphingMethods.prepareGraph(testMaps, 3);
        
    }
    
    
 }
    

