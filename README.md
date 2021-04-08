# Introduction
Huda Abbas 30087827 huda.abbas@ucalgary.ca
Agam Aulakh agampreet.aulakh@ucalgary.ca
Nuha Shaikh nuha.shaikh1@ucalgary.ca
Melanie Nguyen melanie.nguyen1@ucalgary.ca

# ENSF409 Final Project
Our application takes in a furniture category, type, and the number of items requested. Calculates and outputs the cheapest option for creating the requested pieces of furniture

## Usage
To compile the program use this command:
    javac -cp .:lib/mysql-connector-java-8.0.23.jar:. edu/ucalgary/ensf409/DatabaseAccess.java edu/ucalgary/ensf409/UserInput.java edu/ucalgary/ensf409/UnfulfilledRequest.java edu/ucalgary/ensf409/UpdateInventory.java edu/ucalgary/ensf409/OrderForm.java edu/ucalgary/ensf409/PriceCalc.java
    
To run the program use this command and follow the prompts in the terminal to enter your command line arguments:
    java -cp .:lib/mysql-connector-java-8.0.23.jar:. edu.ucalgary.ensf409.UserInput

## Notes
You can make multiple requests in one run of the program and "quit" once you are done. Once the program is cancelled the inventory database will be reset to reflect the original given database and all the deleted items will be restored.

## Testing
To compile the tests after compiling the program (see above) use this command:
    javac -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/system-rules-1.19.0.jar edu/ucalgary/ensf409/UserInputTest.java

To run the tests use this command:
    java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/system-rules-1.19.0.jar org.junit.runner.JUnitCore edu.ucalgary.ensf409.UserInputTest
