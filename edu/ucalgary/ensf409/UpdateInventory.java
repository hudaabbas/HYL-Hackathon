/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 3.2
@since  3.0
*/

package edu.ucalgary.ensf409;
import java.sql.*;

/*
UpdateInventory is a class which removes the items ordered for the furniture from the inventory database
*/

public class UpdateInventory {
	private Connection dbConnect;
	private String[] itemsOrdered;
	private int rowsUpdated; //returns number of updated rows

	/** This constructor sets the database and items ordered from the PriceCalc class
    @params Connection dbConnect which had the database connection
	@params String array of items ordered
    */
	UpdateInventory(Connection dbConnect,String[] items) {
		this.dbConnect = dbConnect;
		this.itemsOrdered = items;
	}

	/**
     * Getter method for rowsUpdated
     * @params nothing
     * @return integer value of the number of rows in the database updated
    */
	public int getRowsUpdated() {
		return rowsUpdated;
	}

	/** 
	 * This method deletes all the items in the list that match the items ordered
     * @params String of the furniture category which is the name of the database table
     * @return nothing
    */
	public String[] removeItem(String table) throws IllegalArgumentException {
		String[] itemInfo= new String[itemsOrdered.length];  //Stores each row's info as a string in an array
		try{
			//Gets all the information of all the furniture before it will be removed from inventory
			for(int i=0;i<itemsOrdered.length;i++) {
				Statement stment= dbConnect.createStatement();
				ResultSet results= stment.executeQuery("SELECT *FROM "+table+" WHERE ID= '"+itemsOrdered[i]+"'");
				
				while(results.next()) {
					StringBuilder sb = new StringBuilder();
					ResultSetMetaData rsmd = results.getMetaData();
					int numberOfColumns = rsmd.getColumnCount();
					for (int j = 1; j<= numberOfColumns; j++) {
						sb.append(results.getString(j));
						if (j < numberOfColumns) {
							sb.append(", ");
						}
					}
					itemInfo[i] = sb.toString();
				}
				stment.close();
				results.close();
			}

			//now deletes the funiture items after its been save
			for(int i=0;i<itemsOrdered.length;i++) {
			   String query= "DELETE FROM "+table+" WHERE ID = ?";
			   PreparedStatement st = dbConnect.prepareStatement(query);
			   
			   st.setString(1, itemsOrdered[i]);
			   rowsUpdated+=st.executeUpdate();  
			   st.close();
			}
			
			if(rowsUpdated == 0){
				throw new IllegalArgumentException("ID not registered in database, nothing updated");
			}
			
		} catch(SQLException e) {
			throw new IllegalArgumentException("unable to delete table");
		} 
		return itemInfo;

	}

}