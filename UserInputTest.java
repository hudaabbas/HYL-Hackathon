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
//import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/** UserInputTest is a class that tests the UserInput class and its methods
*/

public class UserInputTest{
    
  @Test
  // Constructor created with 2 arguments
  public void testConstructor() {
    UserInput testObj = new UserInput("huda","ensf409");
    //String args[] = {"Chair","Mesh","1"};
    int test = UserInput.processRequest(testObj);
    int priceCalc = 150;
   // String[] itemsOrdered = {"C0942,C9890"};
    assertTrue("Cheapest price is wrong", priceCalc == test);
  }

}
