/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  3.0
*/

package edu.ucalgary.ensf409;
import java.sql.*;

/*
UpdateInventory is a class which removes items ordered for the furniture database
*/

public class UpdateInventory {
	private Connection dbConnect;
	private String[] itemsOrdered;
	private String[] itemInfo;

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
			getInventory(table); //store all before deleting
			for(int i=0;i<itemsOrdered.length;i++) {
			   String query= "DELETE FROM "+table+" WHERE ID = ?";
			   PreparedStatement st = dbConnect.prepareStatement(query);
			   
			   st.setString(1, itemsOrdered[i]);
			
			   st.executeUpdate();
			   //System.out.println("Rows updated: "+rows);
			   
			   st.close();
			}
		} catch(SQLException e) {
			System.out.println("Error, unable to delete table");
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
    		PreparedStatement state= dbConnect.prepareStatement(query);
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

	/** Restores items from table "Desk" or "filing"
	 * 
	*/
	public void restoreItem(String table,String ID, String type, String legs, String top, String drawer, int price, String manuId) {
		if(table.equals("desk")) {
			try {
			String query= "INSERT INTO desk(ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
    		PreparedStatement state= dbConnect.prepareStatement(query);

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
	    		PreparedStatement state= dbConnect.prepareStatement(query);

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
	    		PreparedStatement state= dbConnect.prepareStatement(query);

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
