/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  2.0
*/

//package edu.ucalgary.ensf409;
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
    
  @Test
  // to confirm initializtion/close methods are all working
  public void testDBConnect() {
    DatabaseAccess testObj = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
    boolean test = testObj.initializeConnection();
    try{
      testObj.close();
      if(test == testObj.getDBConnect().isClosed()){
          test = false;
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

  @Test
  public void testFurnitureOrderInputItems() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    int expected = 5;
    int item = testObj.getItems();
   
    assertTrue("Number of furniture items is wrong", expected == item);
  }

  @Test
  public void testFurnitureOrderInputType() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    String expected = "Chair";
    String type = testObj.getFurnitureType();
   
    assertTrue("Furniture type is wrong", expected.equals(type));
  }

  public void testCheapestPriceOutput() {
    UserInput testObj = new UserInput("scm","ensf409");
    Scanner args = new Scanner(System.in);
    testObj.takeRequest(args);
    PriceCalc priceObj = new PriceCalc(testObj);
    priceObj.calculateThePrice();
    int priceCalc = priceObj.getPrice();
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

}
