/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.MyDatabase;
import Model.Account;
import Model.Customer;
import Model.Staff;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
    public boolean checkExistObject(String email){
        Connection connect = MyDatabase.getConnection();
        boolean result  = false;
        try{
            String command = "SELECT 1 FROM accounts WHERE email = ?";
            PreparedStatement statement = connect.prepareStatement(command);
            statement.setString(1, email);
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
    public int countObjects(String type) {
        Connection c = MyDatabase.getConnection();
        int result = 0;
        try{
            String command = "SELECT COUNT(*) AS tong FROM accounts "+
                    "WHERE type = ?";
            PreparedStatement statement = c.prepareStatement(command);
            statement.setString(1, type);
            ResultSet rs = statement.executeQuery(); rs.next();
            result = rs.getInt("tong"); 
            // System.out.println(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(c);
        return result;
    }
    @Override
    public int deleteObject(Account ac) {
        return 0;
    }

    @Override
    public void updateObjectInfor(Account ac) {
        Connection connect = MyDatabase.getConnection();
        try{
            String command = "UPDATE accounts SET fullName = ?, "
                    + "gender = ?, birthDay = ? WHERE id = ?";
            PreparedStatement statement = connect.prepareStatement(command);
            statement.setString(1, ac.getFullName());
            statement.setString(2, ac.getGender());
            statement.setDate(3, java.sql.Date.valueOf(ac.getBirthDay()));
            statement.setString(4, ac.getId());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(connect); 
    }


    @Override
    public Account getObject(String email, String type) {
        Connection connect = MyDatabase.getConnection();
        Account ac  = null; 
        String id, fullName, password, gender;
        LocalDate birthDay;
        boolean active;
        try{
            String command = "SELECT * FROM accounts WHERE email = ?";
            PreparedStatement statement = connect.prepareStatement(command);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery(); rs.next();
            id = rs.getString("id"); 
            fullName = rs.getString("fullName");
            password = rs.getString("password"); gender = rs.getString("gender");
            birthDay = rs.getDate("birthDay").toLocalDate();
            active = rs.getBoolean("active");
            if(type.equals("Customer")){
                Double balance = rs.getDouble("balance");
                ac = new Customer(id, fullName, email, password, gender, birthDay, active, balance);
            }
            else if(type.equals("Staff")){
                String branch = rs.getString("branch");
                ac = new Staff(id, fullName, email, password, gender, birthDay, active, branch);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(connect);
        return ac;
    }

    @Override
    public void updateObjectPass(Account ac) {
        Connection connect = MyDatabase.getConnection();
        try{
            String command = "UPDATE accounts SET password = ? "
                    + "WHERE id = ?";
            PreparedStatement statement = connect.prepareStatement(command);
            statement.setString(1, ac.getPassword());
            statement.setString(2, ac.getId());
            statement.executeUpdate(); 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(connect); 
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
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(connect);
        return resultArray;
    }

    
}
