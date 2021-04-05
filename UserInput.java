import java.util.Scanner;

/** (!!!!SHOULD WE ADD MORE AUTHORS?)
@author     Agam Aulakh <a
    href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
@version 1.2
@since  1.0

*/


/** This class extends Database Access and handles all of the user input
using a switch statement
*/
public class UserInput extends DatabaseAccess{

    public static String furnitureCategory;
    public static String furnitureType;
    public static int items;

    private boolean initiatedConnection;
    private int requestNum;

    /** this constructor also calls the super constructor
    the database URL is hardcoded for now
    @param the username for the database connection
    @param the password for the database connection
    */
    public UserInput(String myUsername,String myPassword){
        super("jdbc:mysql://localhost/inventory", myUsername, myPassword);
        this.initiatedConnection = true;
        this.requestNum = 1;

    }

    /** this is the main menu method. it first makes sure a connection has
    been made with the database before taking in a request.
    @params nothing
    @return 1 if no connection to DB, 2 if correct request give, 3 if user quits
    @return default is 0 (wrong input given)
    */
    public int displayMenu(){
        if(!initiatedConnection){
            return 1;
        }
        Scanner scanned = new Scanner(System.in);
        boolean continueProg = takeRequest(scanned);
        scanned.close();
        if(initiatedConnection && continueProg){
            if(correctNameOfObject()){
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

    /** this method checks if a correct furniture name was read.
    @params nothing
    @return true if correct, false if incorrect
    */
    public boolean correctNameOfObject(){
        if(furnitureCategory.equals("Chair") || furnitureCategory.equals("Desk")
        || furnitureCategory.equals("Filing") || furnitureCategory.equals("Lamp")
        || furnitureCategory.equals("Manufacturer")){
            return true;
        }
        return false;
    }

    /** this method takes the request: reads from command line using a scanner
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
        items = scanner.nextInt();
        return true;
    }

    /** this method processes the request [ADD]
    @params nothing
    @return nothing
    */
    public void processRequest(){
        PriceCalc calculation = new PriceCalc(getDburl(), getUsername(), getPassword());
        calculation.getTableFromDatabase();
    }

    private static UserInput initalizeConstructor(Scanner scanner){
        return new UserInput(scanner.next(), scanner.next());
    }
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
                    startProgram.processRequest();
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
    }
}
