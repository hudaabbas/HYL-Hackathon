//package edu.ucalgary.ensf409

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class UserInputTest {
    
    @Test
    // Constructor created with 2 arguments
  public void testConstructor() {
    UserInput testObj = new UserInput("huda","ensf409");
    Boolean value = testObj.takeRequest();
    
    assertTrue("Test exist", value);
  }
}
