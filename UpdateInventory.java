/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  3.0
*/

//package edu.ucalgary.ensf409;
import java.sql.*;

/*
UpdateInventory is a class which removes items ordered for the furniture database
*/

public class UpdateInventory {
	private Connection dbConnect;
	private String[] itemsOrdered;

	/** This constructor sets the database and items ordered from the PriceCalc class
    @params Connection dbConnect which had the database connection
	@params String array of items ordered
    */
	UpdateInventory(Connection dbConnect,String[] items) {
		this.dbConnect = dbConnect;
		this.itemsOrdered = items;
	}

	/** 
	 * This method deletes all the items in the list that match the items ordered
     * @params String of the furniture category which is the name of the database table
     * @return nothing
    */
	public void removeItem(String table) {
		try {
			for(int i=0;i<itemsOrdered.length;i++) {
			   String query= "DELETE FROM "+table+" WHERE ID = ?";
			   PreparedStatement st = dbConnect.prepareStatement(query);
			   
			   st.setString(1, itemsOrdered[i]);
			
			   st.executeUpdate();
			   //System.out.println("Rows updated: "+rows);
			   
			   st.close();
			}
		   }
		   catch(SQLException e) {
			   System.out.println("Error, unable to delete table");
		   }
	}
}

// 	/**
// 	 * Updates database and changes all categories for a selected item to N
// 	 */
// 	public void changeToN(String[] itemsOrdered, String table) {
// 		String[] categories=null;
		
// 		//Gets all the column names 
// 		try {
// 			   Statement st= dbConnect.prepareStatement(table);
// 			   ResultSet rs = st.executeQuery("SELECT * FROM "+table);
			   
// 			   ResultSetMetaData rsmd = rs.getMetaData();
// 			   int columnCount = rsmd.getColumnCount()-4;
// 			   for (int i = 1,j=0; i <= columnCount; i++,j++ ) {
// 				   if(!rsmd.getColumnName(i).equals("ID")&&!rsmd.getColumnName(i).equals("Type")
// 					   &&!rsmd.getColumnName(i).equals("Price")&&!rsmd.getColumnName(i).equals("MaunID")){
// 					  categories[j] = rsmd.getColumnName(i);
// 					}
// 			   }
// 			   st.close();
// 		   }
// 		   catch(SQLException e) {
// 			   System.out.println("Error, unable to get categories");
// 		   }
		
// 		//Updates the table and sets all categories to N for items in the array
// 		 try {
// 			 for(int i=0;i<itemsOrdered.length;i++) {
// 				 int j=0;
// 				 while(j<categories.length) {
// 			   String query= "UPDATE "+table+"SET ?='N' WHERE ID = ?";
// 			   PreparedStatement st= dbConnect.prepareStatement(query);
			   
// 			   st.setString(1, categories[j]);
// 			   st.setString(2, itemsOrdered[i]);
			   
// 			   int rows=st.executeUpdate();
// 			   System.out.println("Rows updated: "+rows);
// 				 } 
// 			 }
			 
// 			 //Should close it but the IDE is being picky about brackets so add it in later
// 			//  st.close();
// 		   } 
// 		   catch(SQLException e) {
// 			   System.out.println("Error, unable to update item status");
// 		   }
// 	}

// }
