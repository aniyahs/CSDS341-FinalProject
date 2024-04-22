 package main.java.com.finalProj;

import java.sql.SQLException;

import main.java.com.finalProj.cli.CommandLineInterface;

 public class Main {
 
    public static void main(String[] args) {
        try {
            CommandLineInterface.main();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 }