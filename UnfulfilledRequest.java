import java.sql.*;
/** (!!!!SHOULD WE ADD MORE AUTHORS?)
@author     Agam Aulakh <a
    href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
@version
@since

*/
//
public class UnfulfilledRequest extends DatabaseAccess{

    private String[] manufacturers = new String[5];
    private int numOfManufacturers;
    private ResultSet results;

    public UnfulfilledRequest(String username, String password){
        super("jdbc:mysql://localhost/inventory", username, password);
        super.initializeConnection();
        // Whatever makes an object of this class must send the username password??
        // is there anyway we could use the same connection from before?
        // otherwise, we will also have to close the connection after calling
        // UnfulfilledRequest.print();
        this.manufacturers = searchDBManufacture();
    }

    /** This method goes through the database and looks for a furniture object with the
    given type.
    @params nothing
    @return the manufacturers' ID in a string array
    */
    public String[] findIDForPrint(){
        String [] manuID = new String[5];
        int count = 0;
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM "+ UserInput.furnitureCategory);
            while(results.next()){
                if(results.getString("Type").equals(UserInput.furnitureType)){
                    manuID[count] = results.getString("ManuID");
                    count++;
                }
            }
            myStmt.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return manuID;
    }

    /** This method goes through the database and looks for the name of
    manufacturers based on the IDs found by findIDForPRint()
    @params nothing
    @return the manufacturers name in a string array
    */
    public String [] searchDBManufacture(){
        String [] manuFound = new String[5]; // ASSUMING THERE ARE 5 MAX (like in the DB)
        String [] manuID = findIDForPrint();
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM Manufacturer");
            while(results.next()){
                for(int i = 0; i< manuID.length; i++){
                    if(results.getString("ManuID").equals(manuID[i])){
                        manuFound[i] = results.getString("Name");
                        numOfManufacturers++;
                    }
                }
            }
            myStmt.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return manuFound;
    }

    /** This method prints the output statement using the manufacturer names
    that are appropraite for each object type.
    @params nothing
    @return nothing
    */
    public void print(){
        System.out.print("\nOrder cannot be fulfilled based on current inventory."+
        " Suggested manufacturers are ");
        if(manufacturers[0].length() != 0){
            System.out.print(manufacturers[0]);
        }
        for(int i = 1; i< numOfManufacturers; i++){
            System.out.print(", "+ manufacturers[i]);
        }
        System.out.print(".\n");
    }

}
