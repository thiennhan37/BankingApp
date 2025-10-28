/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.MyDatabase;
import Model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Hi
 */
public class TransactionDAO {
    public static TransactionDAO getInstance(){
        return new TransactionDAO();
    }
    public int countTransaction(){
        Connection c = MyDatabase.getConnection();
        int result = 0;
        try{
            String command = "SELECT COUNT(*) AS tong FROM transactions";
            PreparedStatement statement = c.prepareStatement(command);
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
    public boolean addTransaction(Transaction trans){
        Connection c = MyDatabase.getConnection();
        int result = 0;
        try{
            String command = "INSERT INTO transactions "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement statement = c.prepareStatement(command);
            statement.setString(1, trans.getTransID()); 
            statement.setString(2, trans.getSenderID());
            statement.setString(3, trans.getReceiverID());
            statement.setLong(4, trans.getAmount());
            statement.setString(5, trans.getType());
            statement.setString(6, trans.getStatus());
            if(trans.getSendTime() != null) statement.setTimestamp(7, Timestamp.valueOf(trans.getSendTime())); 
            else statement.setTimestamp(7,null);
            if(trans.getReceiveTime() != null) statement.setTimestamp(8, Timestamp.valueOf(trans.getReceiveTime()));
            else statement.setTimestamp(8, null);
            statement.setString(9, trans.getDescription());
            
            result = statement.executeUpdate();
            // System.out.println(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(c);
        return result > 0;
    }
    
    public boolean updateReceiveTime(String transID, LocalDateTime receiveTime){
        Connection c = MyDatabase.getConnection();
        int result = 0;
        try{
            String command = "UPDATE transactions "
                    + "SET receiveTime = ? WHERE transID = ?";
            PreparedStatement statement = c.prepareStatement(command);
            statement.setTimestamp(1, Timestamp.valueOf(receiveTime)); 
            statement.setString(2, transID); 
            
            result = statement.executeUpdate();
            // System.out.println(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(c);
        return result > 0;
    }

    public boolean updateStatus(String transID, String status){
        Connection c = MyDatabase.getConnection();
        int result = 0;
        try{
            String command = "UPDATE transactions "
                    + "SET status = ? WHERE transID = ?";
            PreparedStatement statement = c.prepareStatement(command);
            statement.setString(1, status); 
            statement.setString(2, transID); 
            
            result = statement.executeUpdate();
            // System.out.println(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        MyDatabase.closeConnection(c);
        return result > 0;
    }


}
