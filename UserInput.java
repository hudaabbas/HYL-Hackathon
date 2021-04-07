/**
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  2.0
*/

//package edu.ucalgary.ensf409;
import java.util.Scanner;
import java.util.*;

/** UserInput is the main class which extends Database Access and handles all of the user input
using a switch statement
*/
public class UserInput {
    private String furnitureCategory;
    private String furnitureType;
    private int items;
    public int requestNum;
    public DatabaseAccess database;
    private boolean initiatedConnection;

    private String [] chairTypes = {"Task", "Mesh", "Kneeling", "Ergonomic", "Executive"};
    private String [] lampTypes = {"Desk", "Swing Arm", "Study"};
    private String [] deskTypes = {"Traditional", "Adjustable", "Standing"};
    private String [] filingTypes = {"Small", "Medium", "Large"};

    /** This constructor calls the Database constructor to initialize the connection
    @params the username for the database connection
    @params the password for the database connection
    */
    public UserInput(String myUsername,String myPassword){
        database = new DatabaseAccess("jdbc:mysql://localhost/inventory", myUsername, myPassword);
        this.initiatedConnection = database.initializeConnection();
        this.requestNum = 1;
    }

    /**
     * Getter method for data member furnitureCategory
     * @params nothing
     * @return String value of category of furniture
    */
    public String getFurnitureCategory() {
        return this.furnitureCategory;
    }

    /**
     * Getter method for data member furnitureType
     * @params nothing
     * @return String value of type of furniture
    */
    public String getFurnitureType() {
        return this.furnitureType;
    }

    /**
     * Getter method for item number
     * @params nothing
     * @return int value of items
    */
    public int getItems() {
        return this.items;
    }

    /** This is the menu method. It first makes sure a connection has
    been made with the database before taking in a request.
    @params nothing
    @return 1 if no connection to DB, 2 if correct request give, 3 if user quits
    @return default is 0 (wrong input given, none of the above)
    */
    public int displayMenu(){
        if(!initiatedConnection){
            return 1;
        }
        Scanner scanned = new Scanner(System.in);
        boolean continueProg = takeRequest(scanned);
        if(initiatedConnection && continueProg){
            if(correctNameOfObject() && correctTypeOfObject() && items>0){
                this.requestNum++;
                return 2;
            }
        }
        if(!continueProg){
            return 3;
        }
        System.out.println("wrong input given");
        return 0; // weird name was added to the furniture category
    }

    /** This method checks if a correct furniture name was read.
    @params nothing
    @return true if correct, false if incorrect
    */
    public boolean correctNameOfObject(){
        if(furnitureCategory.equals("Chair") || furnitureCategory.equals("Desk")
        || furnitureCategory.equals("Filing") || furnitureCategory.equals("Lamp")){
            return true;
        }
        return false;
    }

    /** This method checks if a correct furniture type was read, depending on furniture kind
    @params nothing
    @return true if correct, false if incorrect
    */
    public boolean correctTypeOfObject(){
        if(furnitureCategory.equals("Chair")){
            Set<String> checkForType = new HashSet<String>(Arrays.asList(chairTypes));
    	    return checkForType.contains(furnitureType);
        }
        else if (furnitureCategory.equals("Desk")){
            Set<String> checkForType = new HashSet<String>(Arrays.asList(deskTypes));
    	    return checkForType.contains(furnitureType);
        }
        else if(furnitureCategory.equals("Filing")){
            Set<String> checkForType = new HashSet<String>(Arrays.asList(filingTypes));
    	    return checkForType.contains(furnitureType);
        }
        else if (furnitureCategory.equals("Lamp")){
            Set<String> checkForType = new HashSet<String>(Arrays.asList(lampTypes));
    	    return checkForType.contains(furnitureType);
        }
        return false;
    }

    /** This method takes the request by reading from the command line using a scanner
    @params nothing
    @return default is true, false if user wants to quit
    */
    private boolean takeRequest(Scanner scanner){
        System.out.println("\n * * * Taking Request #"+ requestNum + " * * * \n");
        System.out.println("Please enter your furniture request in the form of 'Category' 'Type' '#'"+
        " with the first letter capitalized, separated by spaces and with no quotations");
        System.out.println("example: Chair Mesh 1");
        System.out.println("or write Quit to terminate program\n");

        furnitureCategory = scanner.next();
        if(furnitureCategory.equals("Quit") || furnitureCategory.equals("quit")){
            return false;
        }
        furnitureType = scanner.next();
        String possibleItems = scanner.next();
        boolean onlyThree = false;
        if(possibleItems.charAt(0) <= 57 && possibleItems.charAt(0) >= 48){
            items = Integer.parseInt(possibleItems);
            return true;
        }
        if(possibleItems.charAt(0) == '-'){
            items = -10;
            return true;
        }
        furnitureType+=" ";
        furnitureType+=possibleItems;
        items = Integer.parseInt(scanner.next());

        return true;
    }

    /** This method processes the request by calling the PriceCalc class to calculate the lowest price
    @params UserInput class input
    @return returns the lowest price combo calculated
    */
    public static int processRequest(UserInput program){
        PriceCalc calculation = new PriceCalc(program);
        calculation.calculateThePrice();
        return calculation.getPrice();
    }

    /** This method initiliazes the database by using the command line and UserInput constructor
    @params scanner of the command line user input
    @return returns the new instantiation of the UserInput class using the command line
    */
    private static UserInput initalizeConstructor(Scanner scanner){
        return new UserInput(scanner.next(), scanner.next());
    }

    /** This accepts different tests for the program and calls the resulting classes to fulfill a users furniture request
     * Instructions for running and using this program is in README.md
    @params args command line arguemnts (not in use, optional)
    @return returns the new instantiation of the UserInput class using the command line
    */
    public static void main(String[] args) {
        System.out.println("\n * * * Lets Connect to the INVENTORY Database! * * * \n");
        System.out.println("Please enter your database username and password"+
        " separated by spaces");
        System.out.println("example: myname ensf409\n");
        UserInput startProgram = initalizeConstructor(new Scanner(System.in));
        boolean endProgram = true;
        while(true){
            switch(startProgram.displayMenu()){
                case 1:
                    System.out.println("\nNo Connection To Database");
                    System.out.println("please run the program again");
                    System.exit(1);
                    break;
                case 2:
                    processRequest(startProgram);
                    break;
                case 3:
                    System.out.println("\nProgram terminated!\n");
                    endProgram = false;
                    break;
                default:
                    System.out.println("\n~ Input was not valid, let's try again! ~.\n");
            }
        if(endProgram == false) break;
        }

        startProgram.database.close(); //close database connection at end
    }
}
