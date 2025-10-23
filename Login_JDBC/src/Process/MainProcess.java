/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Process;

import Database.MyDatabase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainProcess {

    public static void main(String args[]) throws SQLException {
        Connection c = MyDatabase.getConnection();
        // MyDatabase.printInfo(c);
                
        try {
            Statement statement = c.createStatement();
            String sqlCommand = "Insert into accounts "
                    + "values ('nhan37@gmail.com', 'nhan37')";
            int check = statement.executeUpdate(sqlCommand);
            System.out.println(check);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        MyDatabase.closeConnection(c);
    }
}
