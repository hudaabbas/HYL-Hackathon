import java.sql.*;
import java.util.*;
/** (!!!!SHOULD WE ADD MORE AUTHORS?)
@author     Agam Aulakh <a
    href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
@version 1.2
@since  1.0

*/

//package edu.ucalgary.ensf409;

public class AltPriceCalc extends DatabaseAccess{
    public boolean fulfilled = true;
    public static int cheapestPrice;
    public String[] itemCombination;
	private int numberOfElements;
    public ArrayList<ArrayList<String>> furnitures = new ArrayList<ArrayList<String>>();
    //public ArrayList<String> furnitureTypePrice = new ArrayList<String>();
    public ResultSet results;
	UserInput programInfo;

    public AltPriceCalc(String DBURL, String USERNAME, String PASSWORD,UserInput furnitureAndDatabase) {
		super(DBURL, USERNAME, PASSWORD);
		this.programInfo = furnitureAndDatabase; 
    }


	// overloaded: for Lamp
	public ArrayList<String> makeArrayList(int price, String id, String type, String boolOne,
	String boolTwo){
		ArrayList<String> returnList = new ArrayList<String>();
		returnList.add(String.valueOf(price));
		returnList.add(id);
		returnList.add(type);
		returnList.add(boolOne);
		returnList.add(boolTwo);
		return returnList;
	}

	// for desk and filing
	public ArrayList<String> makeArrayList(int price, String id, String type, String boolOne,
	String boolTwo, String boolThree){
		ArrayList<String> returnList = new ArrayList<String>();
		returnList.add(String.valueOf(price));
		returnList.add(id);
		returnList.add(type);
		returnList.add(boolOne);
		returnList.add(boolTwo);
		returnList.add(boolThree);
		return returnList;
	}


	// for chair
	public ArrayList<String> makeArrayList(int price, String id, String type, String boolOne,
	String boolTwo, String boolThree, String boolFour){
		ArrayList<String> returnList = new ArrayList<String>();
		returnList.add(String.valueOf(price));
		returnList.add(id);
		returnList.add(type);
		returnList.add(boolOne);
		returnList.add(boolTwo);
		returnList.add(boolThree);
		returnList.add(boolFour);
		return returnList;
	}


    public void getTableFromDatabase() {

        try {
            Statement myStmt = getDBConnect().createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + programInfo.getFurnitureCategory());

            while(results.next()){
                if(results.getString("Type").equals(programInfo.getFurnitureType())){
					if(programInfo.getFurnitureCategory().equals("Lamp")){
						int price = results.getInt("Price");
						String id = results.getString("ID");
						String type = results.getString("Type");
						String boolOne = results.getString("Base");
						String boolTwo = results.getString("Bulb");
						furnitures.add(makeArrayList(price, id, type, boolOne, boolTwo));
						numberOfElements++;
					}
					if(programInfo.getFurnitureCategory().equals("Desk")){
						int price = results.getInt("Price");
						String id = results.getString("ID");
						String type = results.getString("Type");
						String boolOne = results.getString("Legs");
						String boolTwo = results.getString("Top");
						String boolThree = results.getString("Drawer");
						furnitures.add(makeArrayList(price, id, type, boolOne, boolTwo, boolThree));
						numberOfElements++;
					}
					if(programInfo.getFurnitureCategory().equals("Filing")){
						int price = results.getInt("Price");
						String id = results.getString("ID");
						String type = results.getString("Type");
						String boolOne = results.getString("Rails");
						String boolTwo = results.getString("Drawers");
						String boolThree = results.getString("Cabinet");
						furnitures.add(makeArrayList(price, id, type, boolOne, boolTwo, boolThree));
						numberOfElements++;
					}
					if(programInfo.getFurnitureCategory().equals("Chair")){
						int price = results.getInt("Price");
						String id = results.getString("ID");
						String type = results.getString("Type");
						String boolOne = results.getString("Legs");
						String boolTwo = results.getString("Arms");
						String boolThree = results.getString("Seat");
						String boolFour = results.getString("Cushion");
						furnitures.add(makeArrayList(price, id, type, boolOne, boolTwo, boolThree, boolFour));
						numberOfElements++;
					}
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
			// THIS IS WHERE THE IF STATEMENT WAS

            results.close();
            myStmt.close();
			findCheapest();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void sortTheArrayList(){
		int sortTheNumbers = numberOfElements - 1;
		int a, b;
		ArrayList<String> temp;
		for (a = 0; a < sortTheNumbers; ++a) {
		    for (b = 0; b < sortTheNumbers; ++b) {
				int first = Integer.parseInt(furnitures.get(b).get(0));
				int second = Integer.parseInt(furnitures.get(b+1).get(0));
		        if (first < second) {
		            temp = furnitures.get(b);
		            furnitures.set(b, furnitures.get(b+1));
		            furnitures.set(b+1, temp);
		        }
		    }
		}
	}


	public boolean checkIfAllYes(String [] boolValues){
		for(int i = 0; i<4;i++){
			if(boolValues[i] == "N"){
                return false;
            }
		}
		return true;
	}
	// CHAIR
	private ArrayList<String> usedID = new ArrayList<String>();
	private int a = 0;
	private int lowestPrice = 101010001;

    //need to make this recursive
	public void findCheapest(){
		int sortTheNumbers = numberOfElements - 1;
		sortTheArrayList();
		String [] boolValues = {"N","N","N","N"};
		int counter = 0;
		for (a = 0; a < sortTheNumbers; ++a) {
			if(furnitures.get(a).get(3).equals("Y")){
				boolValues[0] = furnitures.get(a).get(3);
				counter++;
			}
			if(furnitures.get(a).get(3).equals("Y")){
				boolValues[1] = furnitures.get(a).get(4);
				counter++;
			}
			if(furnitures.get(a).get(3).equals("Y")){
				boolValues[2] = furnitures.get(a).get(5);
				counter++;
			}
			if(furnitures.get(a).get(3).equals("Y")){
				boolValues[3] = furnitures.get(a).get(6);
				counter++;
			}
			if (counter >=1){
                usedID.add(furnitures.get(a).get(0));
            }
		}
		if(checkIfAllYes(boolValues)){
			int newPrice = 0;
			for(int i = 0; i< usedID.size(); i++){
				newPrice += Integer.parseInt(usedID.get(i));
			}
			if (lowestPrice>newPrice){
				lowestPrice = newPrice;
			}
		}
	}

}
