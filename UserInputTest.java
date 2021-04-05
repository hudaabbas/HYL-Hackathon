//package edu.ucalgary.ensf409

import static org.junit.Assert.*;
import org.junit.*;
//import java.util.*;
//import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class UserInputTest {
    
    @Test
    // Constructor created with 2 arguments
  public void testConstructor() {
    UserInput testObj = new UserInput("huda","ensf409");
    //String args[] = {"Chair","Mesh","1"};
    int test = testObj.processRequest();
    int priceCalc = 150;
   // String[] itemsOrdered = {"C0942,C9890"};
    assertTrue("Cheapest price is wrong", priceCalc == test);
  }
}
