package cs1410;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Methods in support of PS6.
 * 
 * @author Nima Kian and Joe Zachary
 */
public class GraphingMethods
{
    /**
     * Constant used to request a max operation
     */
    public final static int MAX = 0;

    /**
     * Constant used to request a min operation
     */
    public final static int MIN = 1;

    /**
     * Constant used to request a sum operation
     */
    public final static int SUM = 2;

    /**
     * Constant used to request an average operation
     */
    public final static int AVG = 3;

    /**
     * The dataSource must consist of one or more lines. If there is not at least one line, the method throws an
     * IllegalArgumentException whose message explains what is wrong.
     * 
     * Each line must consist of some text (a key), followed by a tab character, followed by a double literal (a value),
     * followed by a newline.
     * 
     * If any lines are encountered that don't meet this criteria, the method throws an IllegalArgumentException whose
     * message explains what is wrong.
     * 
     * Otherwise, the map returned by the method (here called categoryMap) must have all of these properties:
     * 
     * (1) The set of keys contained by categoryMap must be the same as the set of keys that occur in the Scanner
     * 
     * (2) The list valueMap.get(key) must contain exactly the same numbers that appear as values on lines in the
     * Scanner that begin with key. The values must occur in the list in the same order as they appear in the Scanner.
     * 
     * For example, if the Scanner contains
     * 
     * <pre>
     * Utah        10
     * Nevada       3
     * Utah         2
     * California  14
     * Arizona     21
     * Utah         2
     * California   7
     * California   6
     * Nevada      11
     * California   1
     * </pre>
     * 
     * (where the spaces in each line are intended to be a single tab), then this map should be returned:
     * 
     * <pre>
     *  Arizona    {21}
     *  California {14, 7, 6, 1} 
     *  Nevada     {3, 11}
     *  Utah       {10, 2, 2}
     * </pre>
     */
    public static TreeMap<String, ArrayList<Double>> readTable (Scanner dataSource)
    {

        TreeMap<String, ArrayList<Double>> map = new TreeMap<>();

        int countLine = 0;

        while (dataSource.hasNextLine())
        {

            countLine++;
            String key = "";
            Double value = 0.0;

            try
            {

                String[] entry = dataSource.nextLine().split("\t");
                key = entry[0];
                value = Double.parseDouble(entry[1]);

            }

            catch (NumberFormatException e)
            {

                throw new IllegalArgumentException(countLine + " is not formatted correctly.");

            }

            catch (ArrayIndexOutOfBoundsException e)
            {

                throw new IllegalArgumentException(countLine + " is not formatted correctly.");
            }

            if (!(map.containsKey(key)))
            {

                map.put(key, new ArrayList<Double>());

            }

            map.get(key).add(value);

        }

        return map;
    }

    /**
     * If categoryMap is of size zero, throws an IllegalArgumentException whose message explains what is wrong.
     * 
     * Else if any of the values in the category map is an empty set, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * Else if any of the numbers in the categoryMap is not positive, throws an IllegalAgumentException whose message
     * explains what is wrong.
     * 
     * Else if operation is anything other than SUM, AVG, MAX, or MIN, throws an IllegalArgumentException whose message
     * explains what is wrong.
     *
     * Else, returns a TreeMap<String, Double> (here called summaryMap) such that:
     * 
     * (1) The sets of keys contained by categoryMap and summaryMap are the same
     * 
     * (2) For all keys, summaryMap.get(key) is the result of combining the numbers contained in the set
     * categoryMap.get(key) using the specified operation. If the operation is MAX, "combining" means finding the
     * largest of the numbers. If the operation is MIN, "combining" means finding the smallest of the numbers. If the
     * operation is SUM, combining means summing the numbers. If the operation is AVG, combining means averaging the
     * numbers.
     * 
     * For example, suppose the categoryMap maps like this:
     * 
     * <pre>
     *  Arizona    {21
     *  California {14, 7, 6, 1} 
     *  Nevada     {3, 11}
     *  Utah       {10, 2, 2}
     * </pre>
     * 
     * and the operation is SUM. The map that is returned must map like this:
     * 
     * <pre>
     *  Arizona    21
     *  California 28 
     *  Nevada     14
     *  Utah       14
     * </pre>
     */
    public static TreeMap<String, Double> prepareGraph (TreeMap<String, ArrayList<Double>> categoryMap, int operation)
    {

        if (categoryMap.size() <= 0)
        {

            throw new IllegalArgumentException("Size of category map must be greater than 0");
        }

        else if (operation < 0 || operation > 3)
        {

            throw new IllegalArgumentException(operation + " does not exist.");
        }

        else
        {

            for (String key : categoryMap.keySet())
            {

                if (categoryMap.get(key).size() <= 0)
                {

                    throw new IllegalArgumentException("Category Map is empty: " + key);
                }

                for (double value : categoryMap.get(key))
                {

                    if (value < 0)
                    {

                        throw new IllegalArgumentException(key + " contains negative value " + value);

                    }

                }

            }

        }

        TreeMap<String, Double> summaryMap = new TreeMap<>();

        if (operation == GraphingMethods.SUM)
        {

            summaryMap = sumCategoryMap(categoryMap);

        }

        else if (operation == GraphingMethods.AVG)
        {

            summaryMap = avgCategoryMap(categoryMap);
        }

        else if (operation == GraphingMethods.MIN)
        {

            summaryMap = minCategoryMap(categoryMap);

        }

        else if (operation == GraphingMethods.MAX)
        {

            summaryMap = maxCategoryMap(categoryMap);

        }

        return summaryMap;

    }

    /**
     * If colorMap is empty, throws an IllegalArgumentException.
     * 
     * If there is a key in colorMap that does not occur in summaryMap, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * If any of the numbers in the summaryMap is non-positive, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * Otherwise, displays on g the subset of the data contained in summaryMap that has a key that appears in colorMap
     * with either a pie chart (if usePieChart is true) or a bar graph (otherwise), using the colors in colorMap.
     * 
     * Let SUM be the sum of all the values in summaryMap whose keys also appear in colorMap, let KEY be a key in
     * colorMap, let VALUE be the value to which KEY maps in summaryMap, and let COLOR be the color to which KEY maps in
     * colorMap. The area of KEY's slice (in a pie chart) and the length of KEY's bar (in a bar graph) must be
     * proportional to VALUE/SUM. The slice/bar should be labeled with both KEY and VALUE, and it should be colored with
     * COLOR.
     * 
     * For example, suppose summaryMap has this mapping:
     * 
     * <pre>
     *  Arizona    21
     *  California 28 
     *  Nevada     14
     *  Utah       14
     * </pre>
     * 
     * and colorMap has this mapping:
     * 
     * <pre>
     *  California Color.GREEN
     *  Nevada     Color.BLUE
     *  Utah       Color.RED
     * </pre>
     * 
     * Since Arizona does not appear as a key in colorMap, Arizona's entry in summaryMap is ignored.
     * 
     * In a pie chart Utah and Nevada should each have a quarter of the pie and California should have half. In a bar
     * graph, California's line should be twice as long as Utah's and Nevada's. Utah's slice/bar should be red, Nevada's
     * blue, and California's green.
     * 
     * The method should display the pie chart or bar graph by drawing on the g parameter. The example code below draws
     * both a pie chart and a bar graph for the situation described above.
     */
    public static void drawGraph (Graphics g, TreeMap<String, Double> summaryMap, TreeMap<String, Color> colorMap,
            boolean usePieChart)
    {

        final int TOP = 10;
        final int LEFT = 10;
        final int WIDTH = 10;
        final int DIAMETER = 300;

        Object[] keys = summaryMap.keySet().toArray();

        for (Object key : keys)
        {

            if (!(colorMap.containsKey(key)))
            {

                summaryMap.remove(key);

            }
        }

        TreeMap<String, Double> percentages = assignPercentages(summaryMap);

        if (usePieChart)
        {

            int degreesInCircle = 360;
            int counter = 0;

            for (String key : summaryMap.keySet())
            {

                g.setColor(colorMap.get(key));

                g.fillArc(LEFT, TOP, DIAMETER, DIAMETER, 0, degreesInCircle);
                degreesInCircle -= (int) (360.0 * percentages.get(key));

                g.fillRect(LEFT + DIAMETER + 2 * WIDTH, TOP + (WIDTH * 2) * (counter), WIDTH, WIDTH);
                g.setColor(Color.black);
                g.drawString(key + ' ' + String.format("%4.2f", summaryMap.get(key)), LEFT + DIAMETER + 4 * WIDTH,
                        TOP + WIDTH + (WIDTH * 2) * (counter));

                counter++;

            }
        }

        else
        {

            int counter = 0;

            for (String key : summaryMap.keySet())
            {

                int bar = (int) (DIAMETER * percentages.get(key));

                g.setColor(colorMap.get(key));

                g.fillRect(LEFT + DIAMETER - bar, TOP + counter * 10 * WIDTH / 3, bar, WIDTH * 2);

                g.setColor(Color.black);
                g.drawString(key + ' ' + String.format("%4.2f", summaryMap.get(key)), 2 * LEFT + DIAMETER,
                        5 * TOP / 2 + counter * 10 * WIDTH / 3);

                counter++;
            }

        }
    }

    /**
     * Takes all values within category maps and returns the minimum value.
     * 
     * @param categoryMap
     * @return
     */
    public static TreeMap<String, Double> minCategoryMap (TreeMap<String, ArrayList<Double>> categoryMap)
    {

        TreeMap<String, Double> result = new TreeMap<>();

        for (String key : categoryMap.keySet())
        {

            double min = categoryMap.get(key).get(0);

            for (double value : categoryMap.get(key))
            {

                if (value < min)
                {

                    min = value;

                }

            }

            result.put(key, min);

        }

        return result;

    }

    /**
     * Takes all values within category maps and finds the maximum value.
     * 
     * @param categoryMap
     * @return
     */
    public static TreeMap<String, Double> maxCategoryMap (TreeMap<String, ArrayList<Double>> categoryMap)
    {

        TreeMap<String, Double> result = new TreeMap<>();

        for (String key : categoryMap.keySet())
        {

            double max = categoryMap.get(key).get(0);

            for (double value : categoryMap.get(key))
            {

                if (value > max)
                {

                    max = value;

                }

            }

            result.put(key, max);

        }

        return result;

    }

    /**
     * Sums up all values in category maps and then returns them.
     * 
     * @param categoryMap
     * @return
     */
    public static TreeMap<String, Double> sumCategoryMap (TreeMap<String, ArrayList<Double>> categoryMap)
    {

        TreeMap<String, Double> result = new TreeMap<>();

        for (String key : categoryMap.keySet())
        {

            double sum = 0.0;

            for (double value : categoryMap.get(key))
            {

                sum += value;

            }

            result.put(key, sum);

        }

        return result;

    }

    /**
     * Takes all values in category map and gets the average then returns that.
     * 
     * @param categoryMap
     * @return
     */
    public static TreeMap<String, Double> avgCategoryMap (TreeMap<String, ArrayList<Double>> categoryMap)
    {

        TreeMap<String, Double> result = new TreeMap<>();

        for (String key : categoryMap.keySet())
        {

            double sum = 0.0;

            for (double value : categoryMap.get(key))
            {

                sum += value;

            }

            double avg = sum / categoryMap.get(key).size();

            result.put(key, avg);

        }

        return result;

    }

    /**
     * Takes summary maps and assigns percentages to each sum
     * 
     * @param summaryMap
     * @return
     */
    public static TreeMap<String, Double> assignPercentages (TreeMap<String, Double> summaryMap)
    {

        TreeMap<String, Double> result = new TreeMap<>();

        double sum = 0.0;
        for (String key : summaryMap.keySet())
        {

            sum += summaryMap.get(key);

        }

        for (String key : summaryMap.keySet())
        {

            result.put(key, summaryMap.get(key) / sum);

        }

        return result;

    }
}
