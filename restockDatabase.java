/**
@author Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.3
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.sql.*;

public class restockDatabase extends DatabaseAccess {
	
	/*
	 * Constructor
	 */
	restockDatabase(String DBURL, String USERNAME, String PASSWORD) {
		super(DBURL, USERNAME, PASSWORD);
	}


/*
 *  Intended to be used after unit tests are run to insert all the deleted items back into the database
 *  Must include all information for the item as arguments when calling the restoreInventory method
 *  Must be done after each test to ensure that database remains unaltered for next test
 */
	
	
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
