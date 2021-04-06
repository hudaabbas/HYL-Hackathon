/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  3.0
*/

//package edu.ucalgary.ensf409;
import java.io.*;

/*
OrderForm is a class which extends PriceCalc and produces a formated order in a .txt file
*/

public class OrderForm extends PriceCalc{
    private String totalPrice;
    private String[] itemsOrdered;
    private String originalRequest;
    private UserInput program;

    /** This constructor extends PriceCalc to intialize private data members
    @params UserInput programInfo for super constructor
    */
    public OrderForm(UserInput programInfo){
        super(programInfo);
        this.program = programInfo;
        this.originalRequest =  programInfo.getFurnitureCategory() + " " + programInfo.getFurnitureType() + ", " + String.valueOf(programInfo.getItems()); 
        this.totalPrice = "$" + String.valueOf(cheapestPrice);
        this.itemsOrdered = itemCombination;
    }

    /** This method creates a .txt file of the furniture order form, then calls class to update the inventory
     * @params String of the file name to create
     * @return nothing
    */
    public void createFile(String fileName) {
        
        BufferedWriter outputStream = null;
        try{
          outputStream = new BufferedWriter(new FileWriter(fileName + String.valueOf(program.requestNum - 1) + ".txt"));
         
          outputStream.write("Furniture Order Form\n");
          outputStream.write("\n");

          outputStream.write("Faculty Name:\n");
          outputStream.write("Contact:\n");
          outputStream.write("Date:\n");

          outputStream.write("\n");
          outputStream.write("Original Request: " + originalRequest);
      
          outputStream.write("\n");
          outputStream.newLine();
          outputStream.write("Items Ordered\n");
          for(int i = 0; i<itemsOrdered.length; i++){
            outputStream.write("ID: " + itemsOrdered[i]);
            outputStream.newLine();
          }
          outputStream.newLine();
          outputStream.write("Total Price: " + totalPrice);
          outputStream.flush();

        } catch(FileNotFoundException e){
            System.out.println("File not found when trying to write to " + fileName + ".txt");
        } catch(IOException e){
            System.out.println("I/O exception when trying to write to " + fileName + ".txt");
            e.printStackTrace();
        } finally {
          if (outputStream != null){
            try{
              outputStream.close();
            } catch (IOException e){
              System.out.println("Couldn't close file" + fileName + ".txt" );
            }
          }
        }
    
        UpdateInventory update = new UpdateInventory(program.database.getDBConnect(), itemsOrdered);
        update.removeItem(program.getFurnitureCategory());

      }
}

