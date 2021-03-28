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
    public boolean fulfilled = false;
    public int cheapestPrice;
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
            results = myStmt.executeQuery("SELECT * FROM " + UserInput.furnitureCategory + "WHERE Type = " + UserInput.furnitureType);

            while(results.next()){
                if(results.getString("Type").equals(UserInput.furnitureType)){
                    furnitures.add(results.getString("ID")); 
                    furnitureTypePrice.add(results.getString("Price"));
                } 
            }
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

            results.close();
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
      
    }

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
    }

    


}
