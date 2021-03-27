import java.util.Scanner;

/** (!!!!SHOULD WE ADD MORE AUTHORS?)
@author     Agam Aulakh <a
    href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
@version 1.2
@since  1.0

*/


/** This class extends

*/
//               [add] extends DatabaseAccess
public class UserInput{

    private String furnitureCategory;
    private String furnitureType;
    private int items;

    private boolean initiatedConnection;
    private int requestNum;

    public UserInput(){
        this.initiatedConnection = false;
    }

    public int displayMenu(){
        if(!initiatedConnection){
            this.initiatedConnection = makeNewConnection();
            return 1;
        }
        if(initiatedConnection){
            if(!quitOrContinue()){
                return 7;
            }
            showMenu();
            if(furnitureCategory.equals("Chair")){
                this.requestNum++;
                return 2;
            }
            if(furnitureCategory.equals("Desk")){
                this.requestNum++;
                return 3;
            }
            if(furnitureCategory.equals("Filing")){
                this.requestNum++;
                return 4;
            }
            if(furnitureCategory.equals("Lamp")){
                this.requestNum++;
                return 5;
            }
            if(furnitureCategory.equals("Manufacturer")){
                this.requestNum++;
                return 6;
            }
        }
        return 8; // weird name was added to the furniture category
    }

    public void showMenu(){
        System.out.println("\n * * * Taking Request #"+ requestNum + " * * * \n");
        System.out.println("please enter your request in the form of [Object] [Type] [#]"+
        "separated by spaces and with no brackets");
        System.out.println("example: Chair Mesh 1\n");
        Scanner scanner = new Scanner(System.in);
        this.furnitureCategory = scanner.next();
        this.furnitureType = scanner.next();
        this.items = scanner.nextInt();
    }
    public boolean quitOrContinue(){
        System.out.println("\nDo you want to continue? [y/n]\n");
        Scanner scanner = new Scanner(System.in);
        String recieved = scanner.next();
        if(recieved.equals("y")){
            return true;
        }
        if(recieved.equals("n")){
            return false;
        }
        System.out.println("invalid input, please try again");
        return quitOrContinue();
    }

    public boolean makeNewConnection(){
        System.out.println("\n * * * Lets Connect to Data Base! * * * \n");
        System.out.println("please enter your database url username and password"+
        "separated by spaces");
        System.out.println("example: jdbc:mysql://localhost/competition jaywhypee ensf409\n");
        Scanner scanner = new Scanner(System.in);
        String dbURL = scanner.next();
        String username = scanner.next();
        String password = scanner.next();
        DatabaseAccess newDBAcess = new DatabaseAccess(dbURL, username, password);
        if(newDBAcess.initializeConnection()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        UserInput startProgram = new UserInput();
        startProgram.displayMenu();
        boolean endProgram = true;
        while(true){
            switch(startProgram.displayMenu()){
                case 1:
                    //finished displaying header, call the switch again
                    break;
                case 2:
                    //processChairRequest();
                    break;
                case 3:
                    //processDeskRequest();
                    break;
                case 4:
                    //processFilingRequest();
                    break;
                case 5:
                    //processLampRequest();
                    break;
                case 6:
                    //processManuRequest();
                    break;
                case 7:
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
