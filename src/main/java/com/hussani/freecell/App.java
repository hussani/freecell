package com.hussani.freecell;

public class App 
{
    public static void main( String[] args )
    {
        boolean exit = false;
        String input;

        while (!exit) {

            System.out.println("Welcome to Freecell!");
            System.out.println("Please select an option:");
            input = System.console().readLine();

            System.out.println("You choose " + input);
            if (input.equals("exit")) {
                exit = true;
            }
        }
    }
}
