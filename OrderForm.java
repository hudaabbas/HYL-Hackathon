/**
 * @author Huda Abbas, Nuha Shaikh, Agam Ak, Melanie <a href="mailto:huda.abbas@ucalgar.ca">huda.abbas@ucalgary.ca</a>
 * @version 1.1
 * @since 1.0
*/

 /*
 OrderForm is a class which produces a formated order in a .txt file
 */

import java.io.*;

public class OrderForm {
    public String totalPrice;
    public String[] itemsOrdered = {"C9080","C83410"};
    public String originalRequest;

    //default constructor
    public OrderForm(){
        this.originalRequest = "standing desk, 3"; //UserInput.furnitureCategory + " " + UserInput.furnitureType + ", " UserInput.items;
        this.totalPrice = "$190"; //PriceCalc.cheapestPrice.toString();
        //this.itemsOrdered = new String[2];
    }

    public static void main(String[] args){
        OrderForm newform = new OrderForm();
        newform.createFile("OrderFormTest");
    }
    public void createFile(String fileName) {
        
        BufferedWriter outputStream = null;
        try{
          outputStream = new BufferedWriter(new FileWriter(fileName + ".txt"));
         
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
    
      }
}

