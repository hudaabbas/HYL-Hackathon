# Introduction
Huda Abbas 30087827 huda.abbas@ucalgary.ca
Agam Aulakh 30090445 agampreet.aulakh@ucalgary.ca
Nuha Shaikh nuha.shaikh1@ucalgary.ca
Melanie Nguyen 30085836 melanie.nguyen1@ucalgary.ca

# ENSF409 Final Project
Our application takes in a furniture category, type, and the number of items requested. Calculates and outputs the cheapest option for creating the requested pieces of furniture

## Usage
** FOR WINDOWS SYSTEM REPLACE : IN ALL COMMANDS WITH ; **
To compile the program use this command:
    javac -cp .:lib/mysql-connector-java-8.0.23.jar:. edu/ucalgary/ensf409/DatabaseAccess.java edu/ucalgary/ensf409/UserInput.java edu/ucalgary/ensf409/UnfulfilledRequest.java edu/ucalgary/ensf409/UpdateInventory.java edu/ucalgary/ensf409/OrderForm.java edu/ucalgary/ensf409/PriceCalc.java

To run the program use this command and follow the prompts in the terminal to enter your command line arguments:
    java -cp .:lib/mysql-connector-java-8.0.23.jar:. edu.ucalgary.ensf409.UserInput

## Program Notes
You can make multiple requests in one run of the program and "quit" once you are done. With multiple sucesfull requests multiple different OrderForm .txt files will be created matching the request number. Even after the program is cancelled and run again the inventory database will not be reset so all the previously ordered items will be deleted from the table. 

## Testing
To compile the tests after compiling the program (see above under Usage) use this command:
    javac -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/system-rules-1.19.0.jar:lib/mysql-connector-java-8.0.23.jar:. edu/ucalgary/ensf409/UserInputTest.java

To run the tests use this command:
    java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/system-rules-1.19.0.jar:lib/mysql-connector-java-8.0.23.jar:. org.junit.runner.JUnitCore edu.ucalgary.ensf409.UserInputTest

## Testing Notes
Everytime a test is run the inventory database will be reset to reflect the original given database and all the deleted items will be restored.
