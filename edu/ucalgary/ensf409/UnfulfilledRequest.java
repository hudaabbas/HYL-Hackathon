/**
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  2.0
*/

package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.ArrayList;

/*
 UnfulfilledRequest prints a list of manufuactures to the terminal
 when the furniture combination is not possible
*/
public class UnfulfilledRequest{
    private ArrayList<String> manufacturers = new ArrayList<String>();
    private int numOfManufacturers;
    private ResultSet results;
    private UserInput program;

    /** This constructor sets the UserInput data member and calls method to search the manufactures list
    @params UserInput programinfo with database and furniture info in its datamembers
    */
    public UnfulfilledRequest(UserInput programInfo){
        this.program = programInfo;
        this.manufacturers = searchDBManufacture();
    }

     /**
     * Getter method for manufactureres
     * @params nothing
     * @return ArrayList<String> of the manufactures for the users request
    */
    public ArrayList<String> getManufacturers(){
        return this.manufacturers;
    }

    /** This method goes through the database and looks for the name of
    manufacturers based on the IDs found by makeIDArrayLists().
    This method is used by the constructor and it initializes the array list
    field.
    @params nothing
    @return the manufacturers name in a string array
    */
    public ArrayList<String> searchDBManufacture(){
        ArrayList<String> manuFound = new ArrayList<String>();
        ArrayList<String> manuID = makeIDArrayLists();
        try{
            Statement myStmt = program.database.getDBConnect().createStatement();
            results = myStmt.executeQuery("SELECT * FROM Manufacturer");
            while(results.next()){
                for(int i = 0; i< manuID.size(); i++){
                    if(results.getString("ManuID").equals(manuID.get(i))){
                        manuFound.add(results.getString("Name"));
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

    /** This method is hardcoded to return the IDs of manufacturers depending on
    given furniture category (not type!).
    We chose to hardcode the information because some methods delete entries
    in the database, and searching for an ID based on currently available
    entries will result in errors.
    Although this makes a new array list every time it is called, it is much
    faster than going through the database itself.
    @params nothing
    @return the manufacturers' ID in a string array
    */
    public ArrayList<String> makeIDArrayLists(){
        ArrayList<String> manuID = new ArrayList<String>();
        String category = program.getFurnitureCategory();

        manuID.add("002");
        manuID.add("004");
        manuID.add("005");
        if(category.equals("Chair")){
            manuID.add("003");
        }
        else if(category.equals("Desk")){
            manuID.add("001");
        }
        return manuID;
    }

    /** This method prints the output statement using the manufacturer names
    that are appropriate for each object type.
    @params nothing
    @return nothing
    */
    public void print(){
        System.out.print("\nOrder cannot be fulfilled based on current inventory."+
        " Suggested manufacturers are ");
        if(manufacturers.get(0).length() != 0){
            System.out.print(manufacturers.get(0));
        }
        for(int i = 1; i< numOfManufacturers; i++){
            System.out.print(", "+ manufacturers.get(i));
        }
        System.out.print(".\n");
    }

}
