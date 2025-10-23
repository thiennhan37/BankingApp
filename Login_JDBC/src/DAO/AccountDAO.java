/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.MyDatabase;
import Model.Account;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Hi
 */

public class AccountDAO implements DAOInterface<Account>{

    public static AccountDAO getInstance(){
        return new AccountDAO();
    }
    
    @Override
    public int addObject(Account ac) {
        Connection c = MyDatabase.getConnection();
        int result = 0;
        try{
            String command = "insert into accounts " +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = c.prepareStatement(command);
            
            statement.setString(1, ac.getId());
            statement.setString(2, ac.getFullName());
            statement.setString(3, ac.getEmail());
            statement.setString(4, ac.getPassword());
            statement.setString(5, ac.getGender());
            statement.setDate(6, java.sql.Date.valueOf(ac.getBirthDay()));
            statement.setBoolean(7, ac.isActive());
            statement.setString(8, ac.getType());
            statement.setDouble(9, ac.getBalace());
            statement.setString(10, ac.getBranch());
            result = statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(c);
        return result;
    }
    
    @Override
    public boolean checkExistObject(Account ac){
        Connection connect = MyDatabase.getConnection();
        boolean result  = false;
        try{
            String command = "SELECT 1 FROM accounts WHERE email = ?";
            PreparedStatement statement = connect.prepareStatement(command);
            statement.setString(1, ac.getEmail());
            result = statement.executeQuery().next();
        }
        catch(SQLException e){
            e.printStackTrace(); 
        }
        MyDatabase.closeConnection(connect);
        return result;
    }
    public boolean checkValidAccount(String email, String password){
        Connection connect = MyDatabase.getConnection();
        boolean result  = false;
        try{ 
            String command = "SELECT 1 FROM accounts WHERE email = ? AND password = ?";
            PreparedStatement statement = connect.prepareStatement(command);
            statement.setString(1, email);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        }
        catch(SQLException e){
            e.printStackTrace(); 
        }
        MyDatabase.closeConnection(connect);
        return result;
    }
    @Override
    public int deleteObject(Account ac) {
        return 0;
    }

    @Override
    public int updateObject(Account ac) {
        Connection connect = MyDatabase.getConnection();
        int result = 0;
        return result;
    }

    @Override
    public ArrayList<Account> showAll() {
        Connection connect = MyDatabase.getConnection();
        ArrayList<Account> resultArray = new ArrayList<>();
        try{
            String command = "select * from accounts";
            PreparedStatement state = connect.prepareStatement(command);
            ResultSet result = state.executeQuery(command);
            while(result.next()){
                String username = result.getString("username");
                String password = result.getString("password");
                int age = result.getInt("age");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(connect);
        return resultArray;
    }

    
}
