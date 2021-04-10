/**
@author Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.5
@since 1.0
*/

package edu.ucalgary.ensf409;
import java.sql.*;

/*
UpdateInventory is a class which removes the items ordered for the furniture from the inventory database
*/

public class UpdateInventory {
	private Connection dbConnect;
	private String[] itemsOrdered;
	int rowsUpdated; //returns number of updated rows

	/** This constructor sets the database and items ordered from the PriceCalc class
    @params Connection dbConnect which had the database connection
	@params String array of items ordered
    */
	UpdateInventory(Connection dbConnect,String[] items) {
		this.dbConnect = dbConnect;
		this.itemsOrdered = items;
	}

	UpdateInventory(UserInput test,String[] IDs ){
		this.dbConnect= test.getDBConnect();
		this.itemsOrdered = IDs;
	}
	
	
	public int getRowsUpdated() {
		return rowsUpdated;
	}
	
	
	/** 
	 * This method deletes all the items in the list that match the items ordered
     * @params String of the furniture category which is the name of the database table
     * @return nothing
    */
	public void removeItem(String table) throws IllegalArgumentException {
		String[] itemInfo= new String[itemsOrdered.length];  //Stores each row's info as a string in an array
		boolean items=true;
		try {
			for(int i=0;i<itemsOrdered.length;i++) {
				Statement stment= dbConnect.createStatement();
				ResultSet results= stment.executeQuery("SELECT *FROM "+table);
    		
//    		Checks whether ID is already registered in database
				while(results.next()) {
					if(!itemsOrdered[i].equals(results.getString("ID"))) {
						items=false;
					}
				}
    		
				if(items==false) {
					throw new IllegalArgumentException();
				}
			}
		}
		catch(SQLException e) {
			System.out.println("Error, unable to delete ID");
		} 


		try {
			 //now deletes the funiture items after its been save
			for(int i=0;i<itemsOrdered.length;i++) {
			   String query= "DELETE FROM "+table+" WHERE ID = ?";
			   PreparedStatement st = dbConnect.prepareStatement(query);
			   
			   st.setString(1, itemsOrdered[i]);
			   rowsUpdated+=st.executeUpdate();  
			   st.close();
			}
			
		} catch(SQLException e) {
			System.out.println("Error, unable to delete ID");
		} 
		

	}

}
