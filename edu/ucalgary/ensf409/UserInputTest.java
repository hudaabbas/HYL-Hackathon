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
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/** UserInputTest is a class that tests all the classes in our program and their methods
 * Tests must be run against the expected given INVENTORY database to work
*/

public class UserInputTest{
  private String[] itemInfo;
  private Connection dbConnect;

  @Test
  //DatabaseAccess Test to confirm initializtion/close methods are all working
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
  //UserInput constructor test
  //Checking if inputs properly parse furiture category
  public void testFurnitureOrderInputCategory() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    String expected = "Chair";
    String category = testObj.getFurnitureCategory();

    assertTrue("initializing furnitureCategory with takeRequest and testing it with its getter method faile", expected.equals(category));
  }

  @Test 
  //Testing when the input # of items is 5
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
    OrderForm order = new OrderForm(testObj, priceObj.getCheapestPrice(), itemCombo);
    this.itemInfo = order.storedItemInfo;
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

  @Rule
  // Handle System.exit() status
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  public void testFailedDBConnection() throws Exception {
 
    exit.expectSystemExitWithStatus(1);

  }

  /** Pre- and Post-test processes
   * 
  */

  @After
  public void end() {
    try{
      this.dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/inventory","scm","ensf409");
      if(itemInfo != null){
        restoreAllData();
      }
    } catch(SQLException e){
      System.out.println("Unsucesfull dbconection when doing post-test processes");
      System.exit(1);
    }
  }


  /**  
   * Utility methods to perform common routines 
  */

  //restore all data removed from the directory
  public void restoreAllData() {
    for(int i = 0; i < itemInfo.length ; i++){
      String[] info = itemInfo[i].split("\\s*,\\s*");
      if(info.length == 6){
        restoreLamp(info[0], info[1], info[2], info[3], Integer.parseInt(info[4]), info[5]);
      } if(info.length == 7){
        if(info[1].equals("Traditional") || info[1].equals("Adjustable") || info[1].equals("Standing")){
          restoreDeskFiling("desk", info[0], info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6]);
        } else{
        restoreDeskFiling("chair", info[0], info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6]);
        }
      }if(info.length == 8){
        restoreChair(info[0], info[1], info[2], info[3], info[4], info[5], Integer.parseInt(info[6]), info[7]);
      }
    }
  }


  // Read in a specified file, given path+filename
  public String[] readFile(String fileAndPath) throws Exception {
    BufferedReader file = new BufferedReader(new FileReader(fileAndPath));
    String tmp = new String();
    ArrayList<String> contents = new ArrayList<String>();

    while ((tmp = file.readLine()) != null) {
      contents.add(tmp);
    }

    file.close();
    return contents.toArray(new String[contents.size()]);
  }

  public String[] writeTestInputData() throws Exception {
    // Create some data and write it to the file
    String[] cRossetti = {
      "Apples and quinces,",
      "Lemons and oranges,",
      "Plump unpeck’d cherries,",
      "Melons and raspberries,",
      "Bloom-down-cheek’d peaches,",
      "Swart-headed mulberries,"
    }; 
    return cRossetti;
 }
	/* 
	 * Restores items from table "Desk" or "filing"
	*/
	public void restoreDeskFiling(String table,String ID, String type, String legs, String top, String drawer, int price, String manuId) {
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
		  }   catch(SQLException e) {
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
			} catch(SQLException e) {
				System.out.println("Error, unable to insert new item into table 'filing' ");
			}
		}
	}

  /**
  * Restores inventory for items in table "Chair"
	*/
	public void restoreChair(String ID, String type, String l, String arm, String seat, String cushion, int price, String manuId ) {
    try {
        String query= "INSERT INTO CHAIR(ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID) VALUES (?,?,?,?,?,?,?,?)";
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
    } catch(SQLException e) {
      System.out.println("Error, unable to insert new item into table 'Chair' ");
    }
  
  }

	/*
	 * Restores inventory for items from table "lamp"
	 */
	public void restoreLamp(String ID, String type, String base, String bulb, int price, String manuId) {
			try {
				String query= "INSERT INTO LAMP(ID, Type, Base, Bulb, Price, ManuID) VALUES (?,?,?,?,?,?)";
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
			} catch(SQLException e) {
				System.out.println("Error, unable to insert new item into table 'lamp' ");
			}
	}

}