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
          test = true; 
      }
    } catch (SQLException e){
        test = false;
    }
    assertTrue("", test);
  }

  @Test
  //UserInput constructor test
  //Checking if inputs properly parse furiture category
  public void testFurnitureCategoryInput() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setFurnitureCategory("Filing");
    //testObj.takeRequest("Filing Medium 1");
    String expected = "Filing";
    String category = testObj.getFurnitureCategory();

    assertTrue("initializing furnitureCategory with takeRequest and testing it with its getter method failed", expected.equals(category));
  }

  @Test 
  //Testing when the input # of items is 5
  public void testFurnitureOrderInputItems() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setItems(5);
    //testObj.takeRequest("Desk Standing 5");
    int expected = 5;
    int item = testObj.getItems();

    assertTrue("initializing UserInput's private data member items with takeRequest and testing it with its getter method failed", expected == item);
  }

  @Test 
  //Testing where input type of furniture is 
  public void testFurnitureOrderInputType() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setFurnitureType("Swing Arm");
    //testObj.takeRequest("Lamp Swing Arm 2";
    String expected = "Swing Arm";
    String type = testObj.getFurnitureType();

    assertTrue("initializing furnitureType with takeRequest with an input that has a space and testing it with its getter method failed", expected.equals(type));
  }

  @Test
  //Testing when the input # of items is -3
  //NOT WORKING RN CAUSE BEING SET WITHOUT USERINPUT BEING CHECKED!!
  public void testNegItems() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setItems(-3);
    //testObj.takeRequest("Desk Standing -3");
    int item = testObj.getItems();
    assertTrue("initializing UserInput's items with a negative number should not update it, stay null", item == 0);
  }

  //Add tests for incorect category/type where furnitureType and furnitureCateogry should be null!!

  @Test
  //testing PriceCalc's calculateThePrice() method
  //Directly setting user input
  //check if the lowest price is returned for the multiple items set
  public void testCheapestPriceOutput() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setFurnitureCategory("Filing");
    testObj.setFurnitureType("Small");
    testObj.setItems(2);
    PriceCalc priceObj = new PriceCalc(testObj);
    priceObj.calculateThePrice();
    int priceCalc = priceObj.getCheapestPrice();
    int expected = 200;
   
    this.itemInfo = priceObj.infoToRestore; //Copy of all info for the ID's found to restore the inventory database after this test

    assertTrue("calling calculateThePrice() with valid inputs does not return the correct calculated price combination", priceCalc == expected);
  }

  @Test
  //testing PriceCalc correctly changes to true if order is fulfilled
  //guarunteed valid UserInput
  //calculateThePrice() should change the default false value to true
  public void testBooleanFulfilled() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setFurnitureCategory("Desk");
    testObj.setFurnitureType("Standing");
    testObj.setItems(1);

    PriceCalc priceObj = new PriceCalc(testObj);
    priceObj.calculateThePrice();
    boolean isTrue = priceObj.fulfilled; //should be true
  
    this.itemInfo = priceObj.infoToRestore; //Copy of all the info from the ID's found to restore the inventory database after this test
    assertTrue("calling calculateThePrice() with valid order form should change the boolean fulfilled PriceCalc data memeber to true", isTrue);
  }

  @Test
   //testing PriceCalc's calculateThePrice() method
  //Directly setting user input
  //check if the ID numbers for the items order is correctly stored 
  public void testIDOfItemsOrdered() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setFurnitureCategory("Lamp");
    testObj.setFurnitureType("Desk");
    testObj.setItems(2);

    PriceCalc priceObj = new PriceCalc(testObj);
    priceObj.calculateThePrice();
    String[] itemCombo = priceObj.getItemCombination();
 
    String[] itemsOrderedExpected = {"L013","L208","L564"};
    boolean test = true;
    for(int i = 0; i < itemCombo.length; i++){
      if(itemsOrderedExpected[i].equals(itemCombo[i])){ //check all ID's in the array match
      } else { test = false; }
    }

    this.itemInfo = priceObj.infoToRestore; //Copy of all the info from the ID's found to restore the inventory database after this test
    assertTrue("calling calculateThePrice() which stores the ID's of the fulfilled order in itemCombination failed", test);
  }

  @Test
   //testing OrderForms's createFile() method
  //Setting random file input
  //check if calculated price, original request and ID numbers of the items ordered correctly read into file
  public void testOrderFormOutput() {
    UserInput testObj = new UserInput("scm","ensf409");
    testObj.setFurnitureCategory("TestCategory");
    testObj.setFurnitureType("TestType");
    testObj.setItems(3);
    String[] testIDs = {"Test1","Test2"};
    OrderForm order = new OrderForm(testObj, 150, testIDs);
    order.createFile("TestFile"); //will create a file called TestFile0.txt
    //Note: will also try to delete a table but since TestCategory is not a valid table will catch error and move on
    boolean test = true;
    
    String[] tempArray = {
      "Furniture Order Form",
      "",
      "Faculty Name:",
      "Contact:",
      "Date:",
      "",
      "Original Request: TestCategory TestType, 3",
      "",
      "Items Ordered",
      "ID: Test1",
      "ID: Test2",
      "",
      "Total Price: $150"
    }; 
    
    try{
      String[] read = readFile("TestFile0.txt"); //reads in the created .txt file using helper utility method below
      for(int i = 0; i < read.length; i++){
        if(tempArray[i].equals(read[i])){ //check all file outputs match hardcoded array
        } else{
          test = false;
        }
      }
    } catch(Exception e){
        test = false;
    }
    
    assertTrue("calling createFile() which creates a formated order in a .txt file using the price, request and ID ordered failed", test);
  }
  
  @Rule
  // Handle System.exit() status
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();
  
  @Test
  public void testFailedDBConnection() throws Exception {
 
    exit.expectSystemExitWithStatus(1);
    System.exit(1);
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
      String[] info = itemInfo[i].split(",[ ]*");

      if(info.length == 6){
        restoreLamp(info[0], info[1], info[2], info[3], Integer.parseInt(info[4]), info[5]);
      } if(info.length == 7){
          if(info[1].equals("Traditional") || info[1].equals("Adjustable") || info[1].equals("Standing")){
            restoreDeskFiling("desk", info[0], info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6]);
          } else{
          restoreDeskFiling("filing", info[0], info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6]);
          }
      } if(info.length == 8){
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

	/* 
	 * Restores items from table "Desk" or "filing"
	*/
	public void restoreDeskFiling(String table,String ID, String type, String legs, String top, String drawer, int price, String manuId) {
		if(table.equals("desk")) {
			try {
        String query= "INSERT INTO DESK(ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
    		PreparedStatement state= dbConnect.prepareStatement(query);

    		state.setString(1, ID);
    		state.setString(2, type);
    		state.setString(3,legs);
    		state.setString(4,top);
    		state.setString(5,drawer);
    		state.setInt(6, price);
    		state.setString(7, manuId);

    		state.executeUpdate();
    		state.close();
		  }   catch(SQLException e) {
			  System.out.println("Error, unable to insert new item into table 'Desk' ");
		  }
		}
		if(table.equals("filing")) {
			try {
				String query= "INSERT INTO FILING(ID, Type, Rails, Drawers, Cabinet, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
	    		PreparedStatement state= dbConnect.prepareStatement(query);

	    		state.setString(1, ID);
	    		state.setString(2, type);
	    		state.setString(3,legs);
	    		state.setString(4,top);
	    		state.setString(5,drawer);
	    		state.setInt(6, price);
	    		state.setString(7, manuId);

	    		state.executeUpdate();
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

        state.executeUpdate();
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

	    		state.executeUpdate();
	    		state.close();
			} catch(SQLException e) {
				System.out.println("Error, unable to insert new item into table 'lamp' ");
			}
	}

}