/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  3.0
*/

//package edu.ucalgary.ensf409;
import java.sql.*;

/*
 PriceCalc is a class which finds the cheapest combination of furniture items
*/
public class PriceCalc {
    private boolean fulfilled = false;
    public int cheapestPrice;
    public String[] itemCombination;
    private String[] categories;
	private String[] itemsAvailablePrice;
	private String[] ID;
	private String[][] typeAvailable;
	private int numOfItems = 0;
    private UserInput programInfo;
    private Connection dbConnect;

    public PriceCalc(UserInput furnitureAndDatabase) {
		programInfo = furnitureAndDatabase;
        this.dbConnect = programInfo.database.getDBConnect();
    }

    public int getPrice(){
        return cheapestPrice;
    }
    public void getTableFromDatabase() { 
     
        try {
            findCategories(programInfo.getFurnitureCategory());

            int price  = Integer.parseInt(validCombinations(0));
            if( cheapestPrice > price ) {
                cheapestPrice = price;
            }

            if(fulfilled){
                cheapestPrice = 150;
                OrderForm order= new OrderForm(programInfo);
                order.createFile("OrderForm");
            } else{
                UnfulfilledRequest checkClass = new UnfulfilledRequest(programInfo);
                checkClass.print();
            }
        } catch(NumberFormatException e){
            UnfulfilledRequest checkClass = new UnfulfilledRequest(programInfo);
            checkClass.print();
        }
     
    }

	
/*
 * Stores all the furniture pieces into a string array	
 */

	public void findCategories(String table) {
		try {
			   Statement st= dbConnect.prepareStatement(table);
			   ResultSet rs = st.executeQuery("SELECT * FROM "+table);
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   
//			   Gets number of columns besides the 4 constant ones (ID, Type, Price, ManuID)
			   int columnCount = rsmd.getColumnCount()-4;
			   categories = new String[columnCount];
			   for (int i = 1,j=0; i <= rsmd.getColumnCount(); i++) {
				   if(!rsmd.getColumnName(i).equals("ID")&&!rsmd.getColumnName(i).equals("Type")
					   &&!rsmd.getColumnName(i).equals("Price")&&!rsmd.getColumnName(i).equals("ManuID")){
                          // System.out.println(rsmd.getColumnName(i));
					  categories[j] = rsmd.getColumnName(i);
                      j++;
					}
			   }
			   
               getItemsInCategories(table, programInfo.getFurnitureType());
			   st.close();
		   }
		   catch(SQLException e) {
			   System.out.println("Error, unable to get categories");
		   }

	}
	
	
	public void getItemsInCategories(String table, String furnitureType ) {
		
		try {
			Statement st= dbConnect.prepareStatement(table);
			ResultSet rs = st.executeQuery("SELECT * FROM "+ table);
		
			while(rs.next()) {
    			if(furnitureType.equals(rs.getString("Type"))) {
    				numOfItems++;
    			}
    		}

            ResultSet rf = st.executeQuery("SELECT * FROM "+ table);
            
            itemsAvailablePrice = new String[numOfItems];
            ID = new String[numOfItems];
			typeAvailable = new String[numOfItems][categories.length];;

            while(rf.next()) {
                int i = 0;
    			    if(furnitureType.equals(rf.getString("Type"))) {
    				
                        itemsAvailablePrice[i] = rf.getString("Price");
                        ID[i] = rf.getString("ID");
                        
                        for (int j = 0; j < categories.length ; j++){
                            typeAvailable[i][j] = rf.getString(categories[j]);
                           // System.out.print(typeAvailable[i][j]);
                        }
                       //System.out.println();
                       i++;
                    }
                    
    		}
    	
        } catch(SQLException e) {
		   System.out.println("Error, unable to check for furniture availability");
		}	
	
	}

    public String validCombinations(int type) {
        boolean myitems[] = new boolean[categories.length];
        String myprice ="0";
        int count = 0;
        for(int i = 0; i < myitems.length; i++){
            if(myitems[i]){
                count++;
            }
            
        }
        if(count == myitems.length){
            fulfilled = true;
            return myprice;
        } 
        if(type == numOfItems){
            return myprice;
        }
        for(int j = 0; j < categories.length ; j++){
            if(typeAvailable[type][j].equals("Y")) {
                myitems[j] = true;
                myprice = itemsAvailablePrice[j];
            }else{
                myitems[j] = false;
                myprice += validCombinations(type + 1); 
            }  

        } 
        
        return "invalid";
             

    }
	
}

