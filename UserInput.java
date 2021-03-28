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
        boolean continueProg = takeRequest();
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
    public boolean takeRequest(){
        System.out.println("\n * * * Taking Request #"+ requestNum + " * * * \n");
        System.out.println("please enter your request in the form of [Object] [Type] [#]"+
        " separated by spaces and with no brackets");
        System.out.println("example: Chair Mesh 1");
        System.out.println("or write Quit to terminate program\n");
        Scanner scanner = new Scanner(System.in);
        furnitureCategory = scanner.next();
        if(furnitureCategory.equals("Quit") || furnitureCategory.equals("quit")){
            scanner.close();
            return false;
        }
        furnitureType = scanner.next();
        items = scanner.nextInt();
        scanner.close();
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

    public static void main(String[] args) {
        System.out.println("\n * * * Lets Connect to Data Base! * * * \n");
        System.out.println("please enter your database username and password"+
        " separated by spaces");
        System.out.println("example: myname ensf409\n");
        Scanner scanner = new Scanner(System.in);
        UserInput startProgram = new UserInput(scanner.next(), scanner.next());
        scanner.close();
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
