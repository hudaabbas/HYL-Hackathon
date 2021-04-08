/**
@author Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.5
@since 1.0
*/

package edu.ucalgary.ensf409;



import java.sql.*;

public class UpdateInventory extends DatabaseAccess {
    private String[] itemsOrdered;
	
	//Constructor
	UpdateInventory(String DBURL, String USERNAME, String PASSWORD) {
		super(DBURL, USERNAME, PASSWORD);
	}



	
	//Setter
	public void setItemsOrdered(String[] items) {
		this.itemsOrdered=items;
	}
	
	//Getter
	public String[] getItemsOrdered() {
		return this.itemsOrdered;
	}
	

	/*
	 * Deletes all the items used to fill the order 
	 */
	public void removeItem(String table) {
		try {
			for(int i=0;i<itemsOrdered.length;i++) {
			   String query= "DELETE FROM "+table+" WHERE ID = ?";
			   PreparedStatement st= getDBConnect().prepareStatement(query);
			   
			   st.setString(1, itemsOrdered[i]);
			
			   int rows=st.executeUpdate();
			   System.out.println("Rows updated: "+rows);
			   
			   st.close();
			}
		   }
		   catch(SQLException e) {
			   System.out.println("Error, unable to delete teacher");
		   }
	}
	
	
	
}

