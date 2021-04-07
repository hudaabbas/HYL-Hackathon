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

    public String[] getItemCombination(){
        return itemCombination;
    }

    public void calculateThePrice() { 
        findCategories(programInfo.getFurnitureCategory());

        int[] array = new int[numOfItems];
        for(int i = 0; i < numOfItems; i++){
            array[i] = i;
        }

        int n = 2;
        while( n <= numOfItems){
            int[] data = new int[n];
            allCombinations(array, data, 0, array.length-1, 0, n);
            n++;
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

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed 
    */
    public void allCombinations(int arr[], int data[], int start,
                                int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r) {
            findCombinations(data);
            return;
        }
 
        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            allCombinations(arr, data, i+1, end, index+1, r);
        }

    }

    public void findCombinations(int[] indexArr){
        for(int j = 0; j < indexArr.length; j++){
            if(indexArr[j] >= numOfItems)
                return;
        }

        for(int i = 0; i < indexArr.length; i++) {   
            for(int j = i + 1; j < indexArr.length; j++) {  
                if(indexArr[i] == indexArr[j]){  //same element duplicated
                    return;  
                }
            }  
        }  

        String myitems[][] = new String[number][categories.length];

        for(int num = 0; num < number; num++){
            for(int cat = 0; cat < myitems[0].length; cat++){
                myitems[num][cat] = "N";
            }
        }
      
        for(int j = 0; j < indexArr.length; j++){
            for(int category = 0; category < myitems[0].length; category++){
                for(int num = 0; num < number; num++){ //go down the category to fulfill other orders
                    if(myitems[num][category].equals("N")){
                        myitems[num][category] = typeAvailable[indexArr[j]][category];
                        break;
                    }
                }
            }
        }

        if(comboFound(myitems)){
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
       
    }

    public boolean comboFound(String[][] myitems){
        for(int items = 0; items < number; items++){
            for(int i = 0; i < myitems[0].length; i++){
                if(myitems[items][i].equals("N")){ //not equal to Y
                    return false;
                } 
            }
        }
        return true;
    }
	
}