/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

public class MyDatabase {
    public static Connection getConnection(){
        Connection c = null;
        try{
            com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
//            String url = "jdbc:mySQL://localhost:3306/jdbc_test";
//            String username = "root";
//            String password = "NhanThien24";

            String url = "jdbc:mySQL://sql12.freesqldatabase.com:3306/sql12811714";
            String username = "sql12811714";
            String password = "x98IGG27fe";
            c = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e){
            System.out.println("loi roi nhan oi");
            // e.printStackTrace();
        }
        return c;
    }
    public static void closeConnection(Connection c){
        try{
            if(c != null) c.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }  
    public static void printInfo(Connection c){
        try{
            if(c != null){
                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println(mtdt.getDatabaseProductName());
                System.out.println(mtdt.getDatabaseProductVersion());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }    
}
