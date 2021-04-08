/**
@author Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.5
@since 1.0
*/

package edu.ucalgary.ensf409;



import java.sql.*;

public class UpdateInventory extends DatabaseAccess {
	
// 
    private String[] itemInfo;
	UpdateInventory(String DBURL, String USERNAME, String PASSWORD) {
		super(DBURL, USERNAME, PASSWORD);
	}


	private String[] itemsOrdered;
	
	//Setter
	public void setItemsOrdered(String[] items) {
		this.itemsOrdered=items;
	}
	
	//Getter
	public String[] getItemsOrdered() {
		return this.itemsOrdered;
	}
	

	/*
	 * Deletes all the items in the list
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
	
	
	/*
	 * Gets all the information of all the furniture that will be removed from inventory
	 * Stores each row's info as a string in an array
	 */
	public void getInventory(String table) {
		itemInfo= new String[itemsOrdered.length];
		int column=0;
		try {
			for(int i=0;i<itemsOrdered.length;i++) {
				Statement stment= getDBConnect().createStatement();
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
	    			System.out.println("Item information is: "+sb);
	    		}
			  stment.close();
			}
		   }
		   catch(SQLException e) {
			   System.out.println("Error, unable to get item row information");
		   }
	}

	
	
	/*
	 * Restores inventory for items in table "Chair"
	 */
	public void restoreInventory(String table, String ID, String type, String l, String arm, 
			String seat, String cushion, int price, String manuId ) {
		if(table.equals("chair")) {
			try {
			String query= "INSERT INTO chair(ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID) VALUES (?,?,?,?,?,?,?,?)";
    		PreparedStatement state= getDBConnect().prepareStatement(query);
    		state.setString(1,ID);
    		state.setString(2, type);
    		state.setString(3,l);
    		state.setString(4,arm);
    		state.setString(5,seat);
    		state.setString(6, cushion);
    		state.setInt(7, price);
    		state.setString(8,manuId);
    		
    		int rows= state.executeUpdate();
    		System.out.println("Rows updated: "+rows);
    		state.close();
		}
		catch(SQLException e) {
			   System.out.println("Error, unable to insert new item into table 'Chair' ");
		   }
			
		}
	}


		
/*
 * Restores items from table "Desk" or "filing"
 */
	
	public void restoreItem(String table,String ID, String type, String legs, String top, String drawer,
			int price, String manuId) {
		
		if(table.equals("desk")) {
			try {
			String query= "INSERT INTO desk(ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
    		PreparedStatement state= getDBConnect().prepareStatement(query);
    		
    		state.setString(1, ID);
    		state.setString(2, type);
    		state.setString(3,legs);
    		state.setString(4,top);
    		state.setString(5,drawer);
    		state.setInt(6, price);
    		state.setString(7, manuId);
		
    		int rows= state.executeUpdate();
    		System.out.println("Rows updated: "+rows);
    		state.close();
		}
		catch(SQLException e) {
			   System.out.println("Error, unable to insert new item into table 'Desk' ");
		   }
			
		}
		
		
		if(table.equals("filing")) {
			try {
				String query= "INSERT INTO filing(ID, Type, Rails, Drawers, Cabinet, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
	    		PreparedStatement state= getDBConnect().prepareStatement(query);
	    		
	    		state.setString(1, ID);
	    		state.setString(2, type);
	    		state.setString(3,legs);
	    		state.setString(4,top);
	    		state.setString(5,drawer);
	    		state.setInt(6, price);
	    		state.setString(7, manuId);
			
	    		int rows= state.executeUpdate();
	    		System.out.println("Rows updated: "+rows);
	    		state.close();
			}
			catch(SQLException e) {
				   System.out.println("Error, unable to insert new item into table 'filing' ");
			   }
		}
	
	
	}
	
	
	/*
	 * Restores inventory for items from table "lamp"
	 */
	
	public void restoreInventory(String table, String ID, String type, String base, String bulb, int price, String manuId) {
		if(table.contentEquals("lamp")) {
			try {
				String query= "INSERT INTO lamp(ID, Type, Base, Bulb, Price, ManuID) VALUES (?,?,?,?,?,?)";
	    		PreparedStatement state= getDBConnect().prepareStatement(query);
	    		
	    		state.setString(1, ID);
	    		state.setString(2, type);
	    		state.setString(3,base);
	    		state.setString(4,bulb);
	    		state.setInt(5, price);
	    		state.setString(6, manuId);
	    		
	    		int rows= state.executeUpdate();
	    		System.out.println("Rows updated: "+rows);
	    		state.close();
			}
			catch(SQLException e) {
				   System.out.println("Error, unable to insert new item into table 'lamp' ");
			   }
		}
	}
	
	
	
}

//public final String DBURL; //store the database url information
//public final String USERNAME; //store the user's account username
//public final String PASSWORD; //store the user's account password
//
//private Connection dbConnect;
//// private ResultSet results;
//
///**
//* Constructor that sets the url, username, password- doesn't use database
//* @param args
//*/
//
//public UpdateInventory(String url, String username, String password) {
//	this.DBURL=url;
//	this.USERNAME=username;
//	this.PASSWORD=password;
//}
//
///**
//* Creates a connection to the database using the url, username, and password that was initialized
//* by the constructor   
//*/
// 
// public void initializeConnection() {
// 	try {
// 		dbConnect= DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
// 	}
// 	catch(SQLException e) {
// 		System.out.println("Error, unable to form connection");
// 	}
// }

///*
// * Adds the furniture that was deleted back into the inventory
// */
//public void restoreInventory(String table) {
//	if(table.equals("chair")) {
//		int i=0;
//		while(i<itemInfo.length) {
//		try {
//		String query= "INSERT INTO chair(ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID) VALUES (?,?,?,?,?,?,?,?)";
//		PreparedStatement state= getDBConnect().prepareStatement(query);
//		state.setString(1,itemInfo[i].substring(b, endIndex) );
//		
//	}
//	catch(SQLException e) {
//		   System.out.println("Error, unable to get item row information");
//	   }
//		
//	}
//	}
//}
//public void restoreInventory(String table, String ID, String type, String rails, String drawers,
//String cabinet, int price, String manuId) {
//if(table.equals("filing")) {
//try {
//String query= "INSERT INTO desk(ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
//PreparedStatement state= getDBConnect().prepareStatement(query);
//
//state.setString(1, ID);
//state.setString(2, type);
//state.setString(3,rails);
//state.setString(4,drawers);
//state.setString(5,cabinet);
//state.setInt(6, price);
//state.setString(7, manuId);
//
//int rows= state.executeUpdate();
//System.out.println("Rows updated: "+rows);
//state.close();
//}
//catch(SQLException e) {
//   System.out.println("Error, unable to insert new item into table 'Desk' ");
//}
//
//}
//
//}
//import java.sql.*;
//
//public class UpdateInventory extends DatabaseAccess {
//	private String[] itemsOrdered;
//	UpdateInventory(String DBURL, String USERNAME, String PASSWORD) {
//		super(DBURL, USERNAME, PASSWORD);
//	}
//
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//        DatabaseAccess myJDBC = new DatabaseAccess("jdbc:mysql://localhost:3306/inventory","mel409","ensf409");
//        myJDBC.initializeConnection();
//        UpdateInventory update=new UpdateInventory("jdbc:mysql://localhost:3306/inventory","mel409","ensf409");
//        String[] items= {"C0914", "C1148"};
//        
//        update.setItemsOrdered(items);
//        update.removeItem("filing");
//	}
//	
//	
//	//Setter
//	public void setItemsOrdered(String[] items) {
//		this.itemsOrdered=items;
//	}
//	
//	//Getter
//	public String[] getItemsOrdered() {
//		return this.itemsOrdered;
//	}
//	
//
//	/*
//	 * Deletes all the items in the list
//	 */
//	public void removeItem(String table) {
//		try {
//			for(int i=0;i<itemsOrdered.length;i++) {
//			   String query= "DELETE FROM "+table+" WHERE ID = ?";
//			   PreparedStatement st= getDbConnect().prepareStatement(query);
//			   
//			   st.setString(1, itemsOrdered[i]);
//			
//			   int rows=st.executeUpdate();
//			   System.out.println("Rows updated: "+rows);
//			   
//			   st.close();
//			}
//		   }
//		   catch(SQLException e) {
//			   System.out.println("Error, unable to delete teacher");
//		   }
//	}
//}
	
	
//	/**
//	 * Updates database and changes all categories for a selected item to N
//	 */
//	public void changeToN(String[] itemsOrdered, String table) {
//		String[] categories=null;
//		
//		//Gets all the column names 
//		try {
//			   Statement st= dbConnect.prepareStatement(table);
//			   ResultSet rs = st.executeQuery("SELECT * FROM "+table);
//			   
//			   ResultSetMetaData rsmd = rs.getMetaData();
//			   int columnCount = rsmd.getColumnCount()-4;
//			   for (int i = 1,j=0; i <= columnCount; i++,j++ ) {
//				   if(!rsmd.getColumnName(i).equals("ID")&&!rsmd.getColumnName(i).equals("Type")
//					   &&!rsmd.getColumnName(i).equals("Price")&&!rsmd.getColumnName(i).equals("MaunID")){
//					  categories[j] = rsmd.getColumnName(i);
//					}
//			   }
//			   st.close();
//		   }
//		   catch(SQLException e) {
//			   System.out.println("Error, unable to delete teacher");
//		   }
//		
//		//Updates the table and sets all categories to N for items in the array
//		 try {
//			 for(int i=0;i<itemsOrdered.length;i++) {
//				 int j=0;
//				 while(j<categories.length) {
//			   String query= "UPDATE "+table+"SET ?='N' WHERE ID = ?";
//			   PreparedStatement st= dbConnect.prepareStatement(query);
//			   
//			   st.setString(1, categories[j]);
//			   st.setString(2, itemsOrdered[i]);
//			   
//			   int rows=st.executeUpdate();
//			   System.out.println("Rows updated: "+rows);
//				 } 
//			 }
//			 
//			 //Should close it but the IDE is being picky about brackets so add it in later
//			//  st.close();
//		   } 
//		   catch(SQLException e) {
//			   System.out.println("Error, unable to delete teacher");
//		   }
//	}
//
//}
