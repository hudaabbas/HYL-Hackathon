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
    private UserInput programInfo;
    private Connection dbConnect;
    private boolean fulfilled = false;
    private String[] categories; //arms, seat, cushion
	private String[] itemsAvailablePrice; //associated price for an item
	private String[] itemIds; //associate ID for an item
	private String[][] typeAvailable; //Y N Y table
	private int numOfItems = 0;
    private int cheapestPrice;
    private String[] itemCombination;
    private int number;

    public PriceCalc(UserInput furnitureAndDatabase) {
		programInfo = furnitureAndDatabase;
        this.dbConnect = programInfo.database.getDBConnect();
        this.number = programInfo.getItems();
    }

    public int getPrice(){
        return cheapestPrice;
    }

    public void calculateThePrice() { 
        findCategories(programInfo.getFurnitureCategory());

        for(int i = 0; i < numOfItems ; i++){
            for(int j = 0; j < numOfItems ; j++){
                int[] array = {i,j};
                findCombinations(array);
            }
        }

        if(fulfilled){
            OrderForm order= new OrderForm(programInfo, cheapestPrice, itemCombination);
            order.createFile("OrderForm");
        } else {
            UnfulfilledRequest checkClass = new UnfulfilledRequest(programInfo);
            checkClass.print();
        }  
    }

    /** 
     * Stores all the furniture pieces into a string array	
     * */
	public void findCategories(String table) {
		try {
            Statement st= dbConnect.prepareStatement(table);
            ResultSet rs = st.executeQuery("SELECT * FROM " + table);
			ResultSetMetaData rsmd = rs.getMetaData();
			   
			int columnCount = rsmd.getColumnCount()-4; //Gets number of columns besides the 4 constant ones (ID, Type, Price, ManuID)
			categories = new String[columnCount]; 
			for (int i = 1,j=0; i <= rsmd.getColumnCount(); i++) {
                if(!rsmd.getColumnName(i).equals("ID")&&!rsmd.getColumnName(i).equals("Type")
                && !rsmd.getColumnName(i).equals("Price")&&!rsmd.getColumnName(i).equals("ManuID")){
                    categories[j] = rsmd.getColumnName(i);
                    j++;
				}
			}
			   
            getItemsInCategories(table, programInfo.getFurnitureType());
			st.close();
            rs.close();
		} catch(SQLException e) {
            System.out.println("Error, unable to get categories");
            System.exit(1);
		}
	}
	
	
	public void getItemsInCategories(String table, String furnitureType) {
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
            itemIds = new String[numOfItems];
			typeAvailable = new String[numOfItems][categories.length];;

            int i = 0;
            while(rf.next()) {
                if(furnitureType.equals(rf.getString("Type"))) {
                    itemsAvailablePrice[i] = rf.getString("Price");
                    itemIds[i] = rf.getString("ID");
                    for (int j = 0; j < categories.length ; j++){
                        typeAvailable[i][j] = rf.getString(categories[j]);
                    }
                    i++;
                }   
    		}

            st.close();
            rf.close();
            rs.close();
        } catch(SQLException e) {
		   System.out.println("Error, unable to check for furniture availability");
           System.exit(1);
		}	
	
	}

    public boolean comboFound(String[] myitems){
        for(int i = 0; i < myitems.length; i++){
            if(myitems[i].equals("N")){ //not equal to Y
                return false;
            } 
        }
        return true;
    }

    public void findCombinations(int[] indexArr){

        for(int j = 0; j < indexArr.length; j++){
            if(indexArr[j] >= numOfItems)
                return;
        }

        for(int i = 0; i < indexArr.length; i++) {   //same element duplicated
            for(int j = i + 1; j < indexArr.length; j++) {  
                if(indexArr[i] == indexArr[j]){ 
                    return;  
                }
            }  
        }  

        String myitems[] = new String[categories.length];

        for(int def = 0; def < myitems.length; def++){
            myitems[def] = "N";
        }
      
        for(int j = 0; j < indexArr.length; j++){
            for(int category = 0; category < myitems.length; category++){
                if(myitems[category].equals("N")){
                    myitems[category] = typeAvailable[indexArr[j]][category];
                }
            }
        }

        if(comboFound(myitems)){
            number--;

            if(number > 0){
                int[] array = {0,0};
                findCombinations(array);
            }

            System.out.println();
            this.fulfilled = true;
            Integer price = 0;

            for(int i = 0; i < indexArr.length; i++){
                price += Integer.parseInt(itemsAvailablePrice[indexArr[i]]);
            }

            if(this.cheapestPrice == 0){
                this.cheapestPrice = price;
                int j = 0;
                this.itemCombination = new String[indexArr.length];
                for(int i = 0; i < indexArr.length; i++){  
                    this.itemCombination[j] = itemIds[indexArr[i]];
                    j++;
                } 
            } else if(this.cheapestPrice > price){
                this.cheapestPrice = price;
                int j = 0;
                this.itemCombination = new String[indexArr.length];
                for(int i = 0; i < indexArr.length; i++){
                    this.itemCombination[j] = itemIds[indexArr[i]];
                    j++;
                } 
            }   
        }

       /* int[] items= new int[numOfItems];
        for(int i = 0; i < numOfItems ; i++){
            for(int j = 0; j < numOfItems ; j++){
                //items[i] = j;
                int[] array = {i,j};
                findCombinations(array);
            }
        }
        */

    }
	
}

