package com.pluralsight.calcengine;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double[] firstVals = {100.0d, 25.0d, 225.0d, 11.0d};
        double[] secondVals = {50.0d, 92.0d, 17.0d, 3.0d};
        char[] opCodes = {'d', 'a', 's', 'm'};
        double[] results = new double[opCodes.length];

        if (args.length == 0) {
            for (int i = 0; i < opCodes.length; i++) {
                results[i] = execute(opCodes[i], firstVals[i], secondVals[i]);
            }
            for (double currentResult : results)
                System.out.println(currentResult);
        } else if (args.length == 1 && args[0].equals("interactive"))
            execInteractively();
        else if (args.length == 3)
            handleCommandLine(args);
        else
            System.out.println("Please provide an operation code and 2 numeric value");
    }

    //takes care of details of getting input from user and breaking those inputs into individual parts
    static void execInteractively() {
        System.out.println("Enter an operation and two numbers:");
        Scanner scanner = new Scanner(System.in); // Will take care of details of getting the input from user
        String userInput = scanner.nextLine(); // Read all input from user until they hit enter key
        String[] parts = userInput.split( " ");
        performOperation(parts);
    }

    // converts those parts from strings into appropriate data types
    private static void performOperation(String[] parts) {
        char opCode = opCodeString(parts[0]);
        if (opCode == 'w')
            handleWhen(parts);
        else {
            double firstVal = valueFromWord(parts[1]);
            double secondVal = valueFromWord(parts[2]);
            double result = execute(opCode, firstVal, secondVal);
            displayResult(opCode, firstVal, secondVal, result);
        }
    }

    private static void handleWhen(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = (long) valueFromWord(parts[2]);
        LocalDate newDate = startDate.plusDays(daysToAdd);
        String output = String.format("%s plus %d days is %s", startDate, daysToAdd, newDate);
        System.out.println(output);
    }

    private static void displayResult(char opCode, double firstVal, double secondVal, double result) {
        char symbol = symbolOpCode(opCode);
 /*       StringBuilder builder = new StringBuilder(20);
        builder.append(firstVal);
        builder.append(" ");
        builder.append(symbol);
        builder.append(" ");
        builder.append(secondVal);
        builder.append(" = ");
        builder.append(result);
        String output = builder.toString();
   */
        String output = String.format("%.3f %c %.3f = %.3f", firstVal, symbol, secondVal, result);
        System.out.println(output);
    }

    private static char symbolOpCode(char opCode) {
        char[] opCodes = {'a', 's', 'm', 'd'};
        char[] symbols = {'+', '-', '*', '/'};
        char symbol = ' ';
        for (int i = 0; i < opCodes.length; i++) {
            if (opCode == opCodes[i]) {
                symbol = symbols[i];
                break;
            }
        }
        return symbol;
    }

    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0); //convert string representation into char representation
        double firstVal = Double.parseDouble(args[1]);  //takes string representation of a number and converts to double
        double secondVal = Double.parseDouble(args[2]);
        double result = execute(opCode, firstVal, secondVal);
        System.out.println(result);
    }


    static double execute(char opCode, double firstVal, double secondVal) {
        double result;
        switch (opCode) {
            case 'a':
                result = firstVal + secondVal;
                break;
            case 's':
                result = firstVal - secondVal;
                break;
            case 'm':
                result = firstVal * secondVal;
                break;
            case 'd':
                result = secondVal != 0 ? firstVal / secondVal : 0.0d;
                break;
            default:
                System.out.println("Invalid opCode: " + opCode);
                result = 0.0d;
                break;
        }
        return result;
    }

    static char opCodeString(String opName) {
        char opCode = opName.charAt(0);
        return opCode;
    }

    static double valueFromWord(String word) {
        String[] numWords = {
                "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        };
        double value = -1d;
        for(int index = 0; index < numWords.length; index++) {
            if (word.equals(numWords[index])) {
                value = index;
                break;
            }
        }
        if (value == -1d)
            value = Double.parseDouble(word);

        return value;
    }
}