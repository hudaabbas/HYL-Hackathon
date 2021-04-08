/**
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  2.0
*/

package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
//import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/** UserInputTest is a class that tests the UserInput class and its methods
 * Tests must be run against the expected given INVENTORY database to work
*/

public class UserInputTest{

  private String[] itemInfo;
  @Test
  // to confirm initializtion/close methods are all working
  public void testDBConnect() {
    DatabaseAccess testObj = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
    boolean test = testObj.initializeConnection();
    try{
      testObj.close();
      if(test == testObj.getDBConnect().isClosed()){
          test = false; //shouldnt this be =true?
      }
    } catch (SQLException e){
        test = false;
    }

    assertTrue("Furniture category is wrong", test);
  }

  @Test
  // Constructor created with 2 arguments
  public void testFurnitureOrderInputCategory() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    String expected = "Chair";
    String category = testObj.getFurnitureCategory();

    assertTrue("Furniture category is wrong", expected.equals(category));
  }

  @Test //Testing where input # of items is 5
  public void testFurnitureOrderInputItems() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    int expected = 5;
    int item = testObj.getItems();

    assertTrue("Number of furniture items is wrong", expected == item);
  }

  @Test //Testing where input type of furniture is MESH, category should be chair
  public void testFurnitureOrderInputType() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    String expected = "Mesh";
    String type = testObj.getFurnitureType();

    assertTrue("Furniture type is wrong", expected.equals(type));
  }

  @Test
  public void testCheapestPriceOutput() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    PriceCalc priceObj = new PriceCalc(testObj);
    priceObj.calculateThePrice();
    int priceCalc = priceObj.getCheapestPrice();
    int  expected = 150;
    assertTrue("Cheapest price is wrong", priceCalc == expected);
  }

  @Test
  public void testItemsOrdered() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    PriceCalc priceObj = new PriceCalc(testObj);
    priceObj.calculateThePrice();
    String[] itemCombo = priceObj.getItemCombination();
    String[] itemsOrderedExpected = {"C0942,C9890"};
    boolean test = true;
    for(int i = 0; i < itemCombo.length; i++){
      if(itemsOrderedExpected[i] != itemCombo[i]){
        test = false;
      }
    }
    assertTrue("Item combo is wrong", test);
  }

  @Test
  public void testNegItems() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    int item = testObj.getItems();

    assertTrue("Item combo is wrong", item == 0);
  }

  //need to add more BOUNDARY CASES and EXCEPTION HANDLING!! also above dont work

  /*
	 * Gets all the information of all the furniture that will be removed from inventory
	 * Stores each row's info as a string in an array
	 */
	public void getInventory(String table) {
		itemInfo= new String[itemsOrdered.length];
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
