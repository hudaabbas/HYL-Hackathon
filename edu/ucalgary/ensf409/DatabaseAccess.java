/** 
@author Agam Aulakh <a href="mailto:agampreet.aulakh@ucalgary.ca">agampreet.aulakh@ucalgary.ca </a>
Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
Huda Abbas <a href="mailto:huda.abbas@ucalgary.ca">huda.abbas@ucalgary.ca</a>
Melanie Nguyen <a href= "mailto:melanie.nguyen1@ucalgary.ca">melanie.nguyen@ucalgary.ca</a>
@version 2.2
@since  2.0
  * DatabaseAccess.java accesses and updates the SQL inventory database. It has data members DBURL to specify
  * which database is being accessed, USERNAME to specific username of user, PASSWORD and dbConnect
  * to establish a connection to the database.
*/

package edu.ucalgary.ensf409;
import java.sql.*;

public class DatabaseAccess {
    private final String DBURL; //store the database url information, no setters
    private final String USERNAME; //store the user's account username
    private final String PASSWORD; //store the user's account password
    private static Connection dbConnect; //connection data member to establish connection to interact with database

    /**
     * Constructor for DatabaseAccess, sets username, dburl and password
     * @params the database url for database connection
     * @params the username for the database connection
     * @params the password for the database connection 
     * 
    */
    public DatabaseAccess(String DBURL, String USERNAME, String PASSWORD){
        this.DBURL = DBURL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    /**
     * Getter method for data member database url
     * @params nothing
     * @return String value of DBURL
    */
    public String getDburl() {
        return this.DBURL;
    }

    /**
     * Getter method for data member USERNAME
     * @params nothing
     * @return String value of USERNAME
    */
    public String getUsername() {
        return this.USERNAME;
    }

    /**
     * Getter method for data member PASSWORD
     * @params nothing
     * @return String value of PASSWORD
    */
    public String getPassword() {
        return this.PASSWORD;
    }

    /**
     * Getter method for database connection 
     * @params nothing
     * @return Connection value of dbConnect
    */
    public Connection getDBConnect(){
        return dbConnect;
    }

    /**
     * initializeConnection creates a connection with the SQL database inventory.
     * @params nothing
     * @return true if connection succesful, false if database connection fails
    */
    public boolean initializeConnection() {
        try { //need to have try and catch in the case the program fails to make connection with database
            dbConnect = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * close is a method that closes the Connection when all methods are run and executed.
     * @params nothing
     * @return nothing
    */
    public void close() {
        try {
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
