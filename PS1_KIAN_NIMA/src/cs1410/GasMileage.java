package cs1410;

import cs1410lib.Dialogs;

/**
 * The purpose of the class is to create a program that will take in values do some calculations and print out the new
 * calculations
 * 
 * @author nimakian
 **/

public class GasMileage
{
    public static void carInfo(String carType, String milesDriven, String priceOfGas, String gallonsToFill) {
        carType = Dialogs.showInputDialog("What is the type of car? (Year, Model, Car)");
        milesDriven = Dialogs.showInputDialog("Miles Driven since last refill?");
        int miles = Integer.parseInt(milesDriven);
        priceOfGas = Dialogs.showInputDialog("Price of gas per gallon?");
        double gasPrice = Double.parseDouble(priceOfGas);
        gallonsToFill = Dialogs.showInputDialog("How many gallons does it take to fill the tank?");
        double tankSize = Double.parseDouble(gallonsToFill);
        double costToFill = gasPrice * tankSize;
        double milesPerGallon = miles / tankSize;
        double costPerMile = costToFill / miles;
        String formattedNumber1 = String.format("$%.2f", costToFill);
        String formattedNumber2 = String.format("$%.2f", costPerMile);
        String formattedNumber3 = String.format("%.2f", milesPerGallon);
        Dialogs.showMessageDialog(
                carType + "\nCost to fill tank: " + formattedNumber1 + "\nMiles per gallon since last fill-up: "
                        + formattedNumber3 + "\nGas cost per mile since last fill-up: " + formattedNumber2);
        System.out.println(
                carType + "\nCost to fill tank: " + formattedNumber1 + "\nMiles per gallon since last fill-up: "
                        + formattedNumber3 + "\nGas cost per mile since last fill-up: " + formattedNumber2);
        return;
    }
    
    public static void main (String[] args) {
        
        carInfo(null, null, null, null);
    }
    /**
     * This main method creates variables that stores information given by the user. The information is then used for
     * calculations which are then printed back out to the user.
     * 
     * @param args
     */
    /*public static void main (String[] args)
    {

        String carType = Dialogs.showInputDialog("What is the type of car? (Year, Model, Car)");
        String mileageDriven = Dialogs.showInputDialog("Miles Driven since last refill?");
        String priceOfGas = Dialogs.showInputDialog("Price of gas per gallon?");
        String gallonsToFill = Dialogs.showInputDialog("How many gallons does it take to fill the tank?");

        int miles = Integer.parseInt(mileageDriven);
        double price = Double.parseDouble(priceOfGas);
        double tank = Double.parseDouble(gallonsToFill);
        double costToFill = price * tank;
        double milesPerGallon = miles / tank;
        double costPerMile = costToFill / miles;

        String formattedNumber1 = String.format("$%.2f", costToFill);
        String formattedNumber2 = String.format("$%.2f", costPerMile);
        Dialogs.showMessageDialog(
                carType + "\nCost to fill tank: " + formattedNumber1 + "\nMiles per gallon since last fill-up: "
                        + milesPerGallon + "\nGas cost per mile since last fill-up: " + formattedNumber2);
        System.out.println(
                carType + "\nCost to fill tank: " + formattedNumber1 + "\nMiles per gallon since last fill-up: "
                        + milesPerGallon + "\nGas cost per mile since last fill-up: " + formattedNumber2);
    }*/

}
