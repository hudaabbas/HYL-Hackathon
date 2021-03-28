//package edu.ucalgary.ensf409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 @author Nuha Shaikh <a href="mailto:nuha.shaikh1@ucalgary.ca">nuha.shaikh1@ucalgary.ca</a>
 @version 1.0
 @since 1.0
  * DatabaseAccess.java accesses and updates the SQL inventory database. It has data members DBURL to specify
  * which database is being accessed, USERNAME to specific username of user, PASSWORD and dbConnect
  * to establish a connection to the database.
 **/
public class DatabaseAccess {
    public final String DBURL; //store the database url information, no setters
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
    public static Connection dbConnect; //connection data member to establish connection to interact with database

    DatabaseAccess(String DBURL, String USERNAME, String PASSWORD){
        /**
         * Constructor for DatabaseAccess, not using database.
         */
        /**
         @param args //command line argument for accessing inventory database, requires user input of password, dburl and username
         */
        this.DBURL = DBURL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        initializeConnection();
    }
    public String getDburl() {
        /**
         * Getter method for data member DBURL, does not use database.
         */
        return this.DBURL;
    }

    public String getUsername() {
        /**
         * Getter method for data member USERNAME, does not use database.
         */
        return this.USERNAME;
    }

    public String getPassword() {
        /**
         * Getter method for data member PASSWORD, does not use database.
         */
        return this.PASSWORD;
    }

    public Connection getDBConnect(){
        return this.dbConnect;
    }

    public boolean initializeConnection() {
        /**
         * initializeConnection creates a connection with the SQL database inventory.
         */
        try { //need to have try and catch in the case the program fails to make connection with database
            dbConnect = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close () {
        /**
         * close is a method that closes the Connection when all methods are
         * run and executed.
         */
        try {
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
