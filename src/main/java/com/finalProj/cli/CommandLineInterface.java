package main.java.com.finalProj.cli;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

import main.java.com.finalProj.db.DatabaseConnection;

public class CommandLineInterface {
// Connect to your database.
// Replace server name, username, and password with your credentials
    public static void main() throws SQLException {

        //scanner to read input
        Scanner myObj = new Scanner(System.in);
        String custEmail, custName;
        String deptSelection;

        // Enter username and email and press Enter
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please enter your email: ");
        custEmail = myObj.nextLine();
        System.out.println("Please enter your name: ");
        custName = myObj.nextLine();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");

        // check that name and email are in database
        checkUserInDB(custName, custEmail, myObj);

        // print out menu and ask for input on what to do next
        printMenu();
        System.out.println("What would you like to do?");
        deptSelection = myObj.nextLine();
        findCategory(deptSelection);

        myObj.close();
    }

    private static void printMenu() {
        ResultSet resultSet = null;

        try(Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement();) {
            String getCategories = "SELECT name FROM Categories";

            resultSet = statement.executeQuery(getCategories);

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            while(resultSet.next()) {
                System.out.println(resultSet.getString(0));
            }

            System.out.println("Promotions");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void findCategory(String catInput) {
        ResultSet resultSet = null;

        try(Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement();) {
            String findCatSql = 
                "SELECT name, description, price, quantity FROM Products p JOIN Categories c ON c.category_id = p.category_id WHERE c.name = " + catInput;

            resultSet = statement.executeQuery(findCatSql);

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            while(resultSet.next()) {
                System.out.println(resultSet.getString(0));
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // check that the username and email match user table, 
    // if they don't, ask if they want to enter their info into the system
    // if Y then create user, if N then continue as 'Guest'
    private static void checkUserInDB(String custName, String custEmail, Scanner myObj) {
        try (Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement();) {

            String checkCustNamesql = "SELECT * FROM User WHERE name = '" + custName + "' AND email = '" + custEmail + "'";
            ResultSet resultSet = statement.executeQuery(checkCustNamesql);

            if (resultSet.next()) {
                // User exists
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Welcome, " + custName);
            } else {
                // User does not exist
                System.out.println("Name and Email do not match any existing user.");
                System.out.println("Would you like to create an account? (Y/N)");

                // Code to handle user input here (e.g., using Scanner)
                String userInput = myObj.nextLine();

                if (userInput.equalsIgnoreCase("Y")) {
                    // Code to create user account goes here
                    System.out.println("Creating new User Account...");
                    System.out.println("Please enter your address: ");
                    String custAddr = myObj.nextLine();
                    String insertSql = "INSERT INTO customers (name, email, address) " + " values (?, ?, ?); " ;

                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                        insertStmt.setString(1, custName);
                        insertStmt.setString(2, custEmail);
                        insertStmt.setString(3, custAddr);
                
                        int rowsAffected = insertStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("User account created successfully!");
                        } else {
                            System.out.println("Failed to create user account.");
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }

                } else {
                    // Continue as guest
                    System.out.println("Continuing as guest...");
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
