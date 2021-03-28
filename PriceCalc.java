
/**
@author Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
@version 2.5
@since 1.0
*/

/*
 PriceCalc is a class which produces a formated order in a .txt file
 */

//package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;

public class PriceCalc extends DatabaseAccess{
    public boolean fulfilled = true;
    public static int cheapestPrice;
    public String[] itemCombination;
    public ArrayList<String> furnitures = new ArrayList<String>();
    public ArrayList<String> furnitureTypePrice = new ArrayList<String>();
    public ResultSet results;

    public PriceCalc(String DBURL, String USERNAME, String PASSWORD) {
		super(DBURL, USERNAME, PASSWORD);
    }

    public void getTableFromDatabase() { 
     
        try {                    
            Statement myStmt = getDBConnect().createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + UserInput.furnitureCategory);

            while(results.next()){
                if(results.getString("Type").equals(UserInput.furnitureType)){
                    furnitures.add(results.getString("ID")); 
                    furnitureTypePrice.add(results.getString("Price"));
                } 
            }
            /*
            if(UserInput.furnitureCategory == "Chair"){
                returnCheapestForChair();
            } 
            else if(UserInput.furnitureCategory == "Desk"){
                returnCheapestForDesk();
            }else if(UserInput.furnitureCategory == "Filing"){
                returnCheapestForFiling();
            }else{
                returnCheapestForLamp();
            }
            */

            if(fulfilled){
                cheapestPrice = 400;
                OrderForm order= new OrderForm(getDburl(),getUsername(),getPassword());
                order.createFile("testing");
            } else{
                UnfulfilledRequest checkClass = new UnfulfilledRequest(getUsername(),getPassword());
                checkClass.print();
                checkClass.close();
            }

            results.close();
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
      
    }
    /*
    public void returnCheapestForChair() { 
        String legs;
        String arms;
        String seat;
        String cushion;
        String type;
        try {  
            if( results.getString("Leg").equals("Y")){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnCheapestForLamp() { 
        boolean base;
        boolean bulb;
        int price = 0; 
        try {  
            while(results.next()){
                if( results.getString("Base").equals("Y")) {
                    if(Integer.parseInt(results.getString("Price")) < price){
                        base = true;
                        price = Integer.parseInt(results.getString("Price"));
                    }
                    if(results.getString("Bulb").equals("Y")){
                        bulb = true;
                    } else{
                        bulb = false;
                    }
                } else{
                    base = false;
                    if( results.getString("Bulb").equals("Y")){
                        if(Integer.parseInt(results.getString("Price")) < price){
                            bulb = true;
                            price = Integer.parseInt(results.getString("Price"));
                        } else{
                            bulb = false;
                        }
                    }
                }
                
            }
         
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    public void returnCheapestForDesk() {
        String legs;
        String drawer;
        String top;
         
        try {  
            if( results.getString("Leg").equals("Y")){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnCheapestForFiling() { 
        String rails;
        String drawers;
        String cabinet;
        try {  
            if( results.getString("Leg").equals("Y")){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    


}

/*package edu.ucalgary.ensf409;

import java.sql.*;

public class PriceCalc extends DatabaseAccess{
	private String[] categories;
	private String[] itemsAvailable;
	private String[] ID;
	private String[] typeAvailable;
	private int numOfItems=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public PriceCalc(String URL, String USERNAME,String PASSWORD) {
		super(URL,USERNAME,PASSWORD);
	}
	
/*
 * Stores all the furniture pieces into a string array	
 */
/*
	public void findCategories(String table) {
		try {
			   Statement st= getDbConnect().prepareStatement(table);
			   ResultSet rs = st.executeQuery("SELECT * FROM "+table);
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   
//			   Gets number of columns besides the 4 constant ones (ID, Type, Price, ManuID)
			   int columnCount = rsmd.getColumnCount()-4;
			   
			   for (int i = 1,j=0; i <= columnCount; i++,j++ ) {
				   if(!rsmd.getColumnName(i).equals("ID")&&!rsmd.getColumnName(i).equals("Type")
					   &&!rsmd.getColumnName(i).equals("Price")&&!rsmd.getColumnName(i).equals("MaunID")){
					  categories[j] = rsmd.getColumnName(i);
					}
			   }
			   typeAvailable=new String[categories.length];
			   st.close();
		   }
		   catch(SQLException e) {
			   System.out.println("Error, unable to get categories");
		   }

	}
	
	
	public void getItemsInCategories(String table, String furnitureType ) {
		
		try {
			Statement st= getDbConnect().prepareStatement(table);
			ResultSet rs = st.executeQuery("SELECT * FROM "+table);
		
			while(rs.next()) {
    			if(furnitureType.equals(rs.getString("Type"))) {
    				numOfItems++;
    			}
    		}
	}
		catch(SQLException e) {
		   System.out.println("Error, unable to check for furniture availability");
		  }
		
		
	
	}
	
	
	
}*/

