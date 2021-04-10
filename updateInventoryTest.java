/**
@author Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.5
@since 1.0
*/

package edu.ucalgary.ensf409;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class updateInventoryTest {

	@Test
//	Tests removeItem method 
//	Checks number of rows that were updated
	public void testRemoveItem(){
		 
		
		UserInput testObj = new UserInput("scm","ensf409");
		 String[] Ids= {"D1030","D2746"};

		 UpdateInventory testInventory= new UpdateInventory(testObj,Ids);
		 testInventory.removeItem("desk");
		 assertEquals(2,testInventory.getRowsUpdated());
		 
		restockDatabase r=new restockDatabase("jdbc:mysql://localhost:3306/inventory","scm","ensf409");
		r.restoreItem("desk", "D1030", "Adjustable", "N", "Y", "N", 150,"002");
		r.restoreItem("desk", "D2746", "Adjustable", "Y", "N", "Y", 250,"004");
	}
	


	@Test  (expected= IllegalArgumentException.class)
//	Expects an error to be thrown if an item doesn't exist in the database
	public void testRemoveItems() {
		 UserInput testObj = new UserInput("scm","ensf409");
		 String[] Ids= {"D1031","D2749","D1339"};

		 UpdateInventory testInventory= new UpdateInventory(testObj,Ids);
		 testInventory.removeItem("desk");
	}
	
}
