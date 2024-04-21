 package main.java.com.finalProj.cli;

// This also shows "connect.setAutoCommit(false);" in a line of code. Comment the "connect.commit():" and show
// that the database is not updated without it. The reason to have auto committing set to false if several statements
// should succeed before committing all changes to the database. That is, for transaction control. However, often
// this type of transaction control is handled in stored procedures within the database itself. But, we are not
// always given options of where to handle transaction control so knowing both ways (within the dbms and within
// code) is helpful.
//
// Edit the connectionUrl in the line below that currently reads "jdbc:sqlserver://cxp-sql-02\\abc123;" and replace
// abc123 with your network id and the server "cxp-sql-02" with the server you were given in an email from the TA.
// The server for Fall 2023 is either cxp-sql-02 or cxp-sql-03

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
public class CommandLineInterface {
// Connect to your database.
// Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl =
            "jdbc:sqlserver://cxp-sql-02\\abc123;"  // TODO: Change this line to mathc our server
            + "database=OnlineOrders;"
            + "user=dbuser;"
            + "password=csds341143sdsc;"
            + "encrypt=true;"
            + "trustServerCertificate=true;"
            + "loginTimeout=20;";   // dunno if this is right

        //scanner to read input
        Scanner myObj = new Scanner(System.in);
        String custEmail, custPW;
        float inpBudget;

        // Enter username and password and press Enter
        System.out.println("Please enter your email: ");
        custEmail = myObj.nextLine();
        System.out.println("Please enter your password: ");
        custPW = myObj.nextLine();

        // check that the username and pw match User table, if they don't ask if they want to enter their information into the system
        // if Y then create user, if N then continue as 'Guest'

        System.out.println("Enter budget max of 12 digits with the last two following the decimal point then hit enter. ");
        inpBudget = myObj.nextFloat();
        myObj.close();

        String insertSql = "INSERT INTO department (dept_name, building, budget) " + " values (?, ?, ?); " ;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement prepsInsert = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
                prepsInsert.setString(1, inpDeptName);
                prepsInsert.setString(2, inpBuilding);
                prepsInsert.setFloat(3,inpBudget);
                connection.setAutoCommit(false);
                prepsInsert.execute();
                // Retrieve the generated key from the insert. None in this example.
                resultSet = prepsInsert.getGeneratedKeys();
                // Print the ID of the inserted row. Again, will be null because no keys auto gen

                while (resultSet.next()) {
                    System.out.println("Generated: " + resultSet.getString(1));
                }
                connection.commit();
            }
         // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printTable(String table) {

    }

    private static void printColFromTable(String column, String table) {

    }
}
