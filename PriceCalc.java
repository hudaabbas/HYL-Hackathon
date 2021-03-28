package edu.ucalgary.ensf409;

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
	
	
	
}
