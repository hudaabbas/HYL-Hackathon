/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  3.0
*/

package edu.ucalgary.ensf409;
import java.sql.*;

/*
 PriceCalc is a class which trys to find the cheapest combination of furniture items and stores their ID number
*/
public class PriceCalc {
    private UserInput programInfo;
    private Connection dbConnect;
    private boolean fulfilled = false; //determine whether an order can be fullfiled or not
    private String[] categories; //holds the furnitrure pieces for the specifc category
	private String[] itemsAvailablePrice; //holds associated price of all the items with the desired furniture type & category
	private String[] itemIds; //holds associated ID of all the items with the desired furniture type & category
	private String[][] typeAvailable; //2D array of the desired furniture piece Y and N boolean values for our desired furniture type
	private int numOfItems = 0; //number of items in the database which match the requested type and category
    private int cheapestPrice;
    private String[] itemCombination; //combo of all the ID's
    private int number; //number of furniture items user has requested

    /** This constructor uses the information from UserInput class to intialize some of its private data members
    @params UserInput programInfo for database connection and order request info
    */
    public PriceCalc(UserInput furnitureAndDatabase) {
		programInfo = furnitureAndDatabase;
        this.dbConnect = programInfo.database.getDBConnect();
        this.number = programInfo.getItems();
    }

    /**
     * Getter method for cheapestPrice
     * @params nothing
     * @return integer value of the cheapest price
    */
    public int getCheapestPrice(){
        return this.cheapestPrice;
    }

    /**
     * Getter method for itemCombination
     * @params nothing
     * @return String[] of the ID values of items ordered
    */
    public String[] getItemCombination(){
        return this.itemCombination;
    }

    /**
     * Helper function which calls all the remaining methods, sets up our recursive price calculation class,
     * Determines whether an order form can be made or if the request is unfulfilled and the UnfulfilledRequest class should be called 
     * @params nothing
     * @return nothing
    */
    public void calculateThePrice() { 
        findCategories(programInfo.getFurnitureCategory());

        int[] array = new int[numOfItems]; //sets an array to be used as an index with values from 0 to the number of possible items
        for(int i = 0; i < numOfItems; i++){
            array[i] = i;
        }

        int n = 2;
        while( n <= numOfItems){ 
            int[] data = new int[n]; //different sized combinations of arrays starting from 2 which is {0,0} until the greatest size
            allCombinations(array, data, 0, array.length-1, 0, n); //recursively goes through all index 
            n++;
        }  
        
        if(fulfilled){ //if order sucesfull create your order .txt file
            OrderForm order= new OrderForm(programInfo, cheapestPrice, itemCombination);
            order.createFile("OrderForm");
        } else { //if unsucessfull go to the class which outputs possible manufactures and print to terminal
            UnfulfilledRequest checkClass = new UnfulfilledRequest(programInfo);
            checkClass.print();
        }  
    }

    /**
     * Stores all the furniture pieces into a String array (private data member categories[])
     * called from calculateThePrice	
     * @params String table which is the inputed furniture category
     * @return nothing
    */
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
                    categories[j] = rsmd.getColumnName(i); //holds all the furniture pieces for a given category
                    j++;
				}
			}
			   
            getItemsInCategories(table, programInfo.getFurnitureType()); //passes in the furniture category and type
			st.close();
            rs.close();
		} catch(SQLException e) {
            System.out.println("Error, unable to get categories");
            System.exit(1);
		}
	}
	
	 /**
     * Stores all the furniture items, IDs, and prices into seperate private data memebers for our given furnityre type
     * called from findCategories	
     * @params String table is the furniture category, String furniture type is the type of furniture user requested
     * @return nothing
    */
	public void getItemsInCategories(String table, String furnitureType) {
		try {
			Statement st= dbConnect.prepareStatement(table);
			ResultSet rs = st.executeQuery("SELECT * FROM "+ table);
		
			while(rs.next()) {
    			if(furnitureType.equals(rs.getString("Type"))) {
    				numOfItems++; //number of items in database that match user furniture type
    			}
    		}

            ResultSet rf = st.executeQuery("SELECT * FROM "+ table);
            itemsAvailablePrice = new String[numOfItems];
            itemIds = new String[numOfItems];
			typeAvailable = new String[numOfItems][categories.length];

            int i = 0;
            while(rf.next()) {
                if(furnitureType.equals(rf.getString("Type"))) { //all char same i index for a given item
                    itemsAvailablePrice[i] = rf.getString("Price"); //stores the price of the given item 
                    itemIds[i] = rf.getString("ID"); //stores ID number of the given item
                    for (int j = 0; j < categories.length ; j++){
                        typeAvailable[i][j] = rf.getString(categories[j]); //stores boolean value of all the furniture pieces of the given item
                    }
                    i++;
                }   
    		}

            st.close(); //close all statements and ResultSet objects
            rf.close();
            rs.close();
        } catch(SQLException e) {
		   System.out.println("Error, unable to check for furniture availability");
           System.exit(1);
		}	
	
	}

    /**
     * Onces all the necessary database info is stored in local arrays this method is called from calculateThePrice()
     * Recursively checks through all the different index combinations
     * @params arr[] is the input int array of all the indexes
     * @params data[] is a temporary array to store current combination
     * @params start & end are the staring and ending indexes in arr[], set at 0 (initally) and arr.length-1
     * @params the current index in data[]
     * @params size of the combination to be printed, size of data[] array
     * @return nothing
    */
    public void allCombinations(int arr[], int data[], int start, int end, int index, int r) {  
        if (index == r) { // current combination is ready now to be checked
            checkCombination(data);
            return; //base case
        }

        //The condition "end-i+1 >= r-index" makes sure index will make a combination with remaining elements at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++) 
        {
            data[index] = arr[i]; //will replace the index with all possible elements 
            allCombinations(arr, data, i+1, end, index+1, r); //recusively goes through all indexes and different starts of the arr[] array
        }

    }

    /**
     * Checks through our stored using the given indexArr and sees if that index combination fulfills the order
     * If sucesfull private vairable fulfilled is changed, the price and itemsOrdered are also updated if its cheaper then the previously fulfilled combo
     * @params indexArr int array of all the possible index position combinations  
     * @return nothing
    */
    public void checkCombination(int[] indexArr){
        for(int j = 0; j < indexArr.length; j++){
            if(indexArr[j] >= numOfItems)  //if index value greater than number of items exit
                return;
        }

        for(int i = 0; i < indexArr.length; i++) {   
            for(int j = i + 1; j < indexArr.length; j++) {  
                if(indexArr[i] == indexArr[j]){  //if the same index is duplicated exit, can't use same element twice (won't happen with our combos but safety net)
                    return;  
                }
            }  
        }  

        String myitems[][] = new String[number][categories.length]; //creates an empty 2D array the size of the furniture pieces and # of items requested to know whether the combo is sucesfful

        for(int num = 0; num < number; num++){
            for(int cat = 0; cat < myitems[0].length; cat++){
                myitems[num][cat] = "N"; //default nothing has been added all furniture items are N
            }
        }
      
        for(int j = 0; j < indexArr.length; j++){
            for(int category = 0; category < myitems[0].length; category++){
                for(int num = 0; num < number; num++){ //go down the category to fulfill other orders if ones before already Y for that furniture piece
                    if(myitems[num][category].equals("N")){
                        myitems[num][category] = typeAvailable[indexArr[j]][category]; //copies in the Y and N values at the given index to our items array to see if can fill up the whole thing with Y
                        break;
                    }
                }
            }
        }

        if(comboFound(myitems)){ //if our array is all Y, sucesfull combo!
            this.fulfilled = true;
            Integer price = 0;

            for(int i = 0; i < indexArr.length; i++){
                price += Integer.parseInt(itemsAvailablePrice[indexArr[i]]); //set temp price variable as addition of all prices at inputed index
            }

            if(this.cheapestPrice == 0){ //no other combo has been sucesfull previously
                this.cheapestPrice = price; //set private variable cheapestPrice to temporary price
                int j = 0;
                this.itemCombination = new String[indexArr.length];
                for(int i = 0; i < indexArr.length; i++){  
                    this.itemCombination[j] = itemIds[indexArr[i]]; //set itemCombination array to the IDs at inputed index
                    j++;
                } 
            } else if(this.cheapestPrice > price){ //another combo was sucesfull but our price is cheaper
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

    /**
     * Checks if our temporary array has all Y's if so then a sucesfull combo has been found for entire order
     * @params String[][] myitems is 2D array that holds the Y and N values of a specfic index combination to try and attempt to fill it all with Y
     * @return boolean value, true if all Y's, false otehrwise
    */
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