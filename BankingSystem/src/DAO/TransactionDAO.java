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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

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
    public Transaction getTransaction(String transID){
        Connection c = MyDatabase.getConnection();
        PreparedStatement statement = null; ResultSet rs = null;
        Transaction result = null;
        try{
            String command = "SELECT * FROM transactions WHERE transID = ?";
            statement = c.prepareStatement(command);
            statement.setString(1, transID); 
            rs = statement.executeQuery();
            if(rs.next()){
                String senderID = rs.getString("senderID");
                String receiverID = rs.getString("receiverID");
                Long amount = rs.getLong("amount");
                String type = rs.getString("type");
                LocalDateTime sendTime = rs.getTimestamp("sendTime").toLocalDateTime();
                LocalDateTime receiveTime = null;
                if(rs.getTimestamp("receiveTime") != null) 
                    receiveTime = rs.getTimestamp("receiveTime").toLocalDateTime();
                String status = rs.getString("status");
                String description = rs.getString("description");
                result = new Transaction(senderID, receiverID, amount, type, status, sendTime, receiveTime, description, transID);
            }
            // System.out.println(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement != null){
                try{ statement.close();} catch(SQLException ex){}
            }
            if(rs != null){
                try{ rs.close();} catch(SQLException ex){}
            }
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
    
    public List<Transaction> filterTransaction(String id, LocalDateTime beginTime, LocalDateTime endTime, String type, String status){
        Connection c = MyDatabase.getConnection();
        List<Transaction> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            String command, addStatus = "";
            if(status.equals("SUCCESSFUL")) addStatus = " AND status = 'SUCCESSFUL'";
            else if(status.equals("PENDING")) addStatus = " AND status = 'PENDING'";
            else if(status.equals("FAILED")) addStatus = " AND status = 'FAILED'";
            if(type.equals("ALL")){
                command = "SELECT * FROM transactions WHERE "
                + "( (? = senderID AND sendTime >= ? AND sendTime < ?) "
                + "OR (? = receiverID AND ((type = 'DEPOSIT' AND receiveTime IS NULL) OR (receiveTime >= ? AND receiveTime < ?)) ) )";
                command += addStatus;
                
                // System.out.println(command);
                statement = c.prepareStatement(command);
                statement.setString(1, id);
                statement.setTimestamp(2, Timestamp.valueOf(beginTime)); 
                statement.setTimestamp(3, Timestamp.valueOf(endTime));
                statement.setString(4, id);
                statement.setTimestamp(5, Timestamp.valueOf(beginTime));
                statement.setTimestamp(6, Timestamp.valueOf(endTime)); 
            }
            else if(type.equals("TRANSFER")){
                command = "SELECT * FROM transactions WHERE type = ? "
                + "AND ((senderID = ? AND sendTime >= ? AND sendTime < ?) OR (receiverID = ? AND receiveTime >= ? AND receiveTime < ?))";
                command += addStatus;
                
                statement = c.prepareStatement(command);
                statement.setString(1, type);
                statement.setString(2, id);
                statement.setTimestamp(3, Timestamp.valueOf(beginTime)); 
                statement.setTimestamp(4, Timestamp.valueOf(endTime));
                statement.setString(5, id);
                statement.setTimestamp(6, Timestamp.valueOf(beginTime));
                statement.setTimestamp(7, Timestamp.valueOf(endTime)); 
            }
            else if(type.equals("WITHDRAW")){
                command = "SELECT * FROM transactions WHERE type = ? "
                        + "AND (senderID = ? AND sendTime >= ? AND sendTime < ?)";
                command += addStatus;
                
                statement = c.prepareStatement(command);
                statement.setString(1, type);
                statement.setString(2, id);
                statement.setTimestamp(3, Timestamp.valueOf(beginTime)); 
                statement.setTimestamp(4, Timestamp.valueOf(endTime));
            }
            else{
                // System.out.println(type + " " + id);
                command = "SELECT * FROM transactions WHERE type = ? "
                + "AND receiverID = ? AND ((type = 'DEPOSIT' AND receiveTime IS NULL) OR (receiveTime >= ? AND receiveTime < ?))";
                command += addStatus;
                
                // System.out.println(beginTime.toString() + "\n" + endTime.toString());
                statement = c.prepareStatement(command);
                statement.setString(1, type);
                statement.setString(2, id);
                statement.setTimestamp(3, Timestamp.valueOf(beginTime)); 
                statement.setTimestamp(4, Timestamp.valueOf(endTime));
            }
            
            rs = statement.executeQuery();
            while(rs.next()){
                Transaction x = new Transaction();
                x.setTransID(rs.getString("transID")); 
                x.setSenderID(rs.getString("senderID"));
                x.setReceiverID(rs.getString("receiverID"));
                x.setAmount((Long)rs.getLong("amount"));
                x.setType(rs.getString("type"));
                x.setStatus(rs.getString("status")); 
                x.setSendTime(rs.getTimestamp("sendTime").toLocalDateTime());
                if(rs.getTimestamp("receiveTime") != null){
                    x.setReceiveTime(rs.getTimestamp("receiveTime").toLocalDateTime());
                }
                else x.setReceiveTime(null); 
                x.setDescription(rs.getString("description"));
                result.add(x);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{ if(statement != null) statement.close();} catch(SQLException ex){}
            try{ if(rs != null) rs.close();} catch(SQLException ex){}
        }
        MyDatabase.closeConnection(c);
        return result;
    }

    public List<Transaction> filterTransForAuthorize(String senderID, String receiverID, 
            LocalDateTime beginTime, LocalDateTime endTime, String type){
        List<Transaction> result = new ArrayList<>();
        if(type.equals("WITHDRAW")) return result;
        Connection c = MyDatabase.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try{
            String command = "SELECT * FROM transactions "
                    + "WHERE (sendTime >= ? AND sendTime < ?) AND status = 'PENDING' ";
            String add1 = ""; if(senderID != null) add1 = " AND senderID = ? ";
            String add2 = ""; if(receiverID != null) add2 = " AND receiverID = ? ";
            String add3 = ""; 
            if(type.equals("TRANSFER")) add3 = " AND type = 'TRANSFER'";
            else if(type.equals("DEPOSIT")) add3 = " AND type = 'DEPOSIT'";
            command = command + add1 + add2 + add3;
            statement = c.prepareStatement(command);
            int cnt = 3;
            statement.setTimestamp(1, Timestamp.valueOf(beginTime)); 
            statement.setTimestamp(2, Timestamp.valueOf(endTime)); 
            if(senderID != null){
                statement.setString(cnt++, senderID);
            }
            if(receiverID != null){
                statement.setString(cnt++, receiverID);
            }
            
            rs = statement.executeQuery();
            while(rs.next()){
                Transaction x = new Transaction();
                x.setTransID(rs.getString("transID")); 
                x.setSenderID(rs.getString("senderID"));
                x.setReceiverID(rs.getString("receiverID"));
                x.setAmount((Long)rs.getLong("amount"));
                x.setType(rs.getString("type"));
                x.setStatus(rs.getString("status")); 
                x.setSendTime(rs.getTimestamp("sendTime").toLocalDateTime());
                result.add(x);
            }
            // System.out.println(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{ if(statement != null) statement.close();} catch(SQLException ex){}
            try{ if(rs != null) rs.close();} catch(SQLException ex){}
        }
        MyDatabase.closeConnection(c);
        return result;
    }

    public Long[] staticsPieForCustomer(String id, LocalDateTime begin){
        Connection c = MyDatabase.getConnection();
        Long[] result = new Long[]{0L, 0L, 0L, 0L, 0L, 0L};
        PreparedStatement statement = null;
        ResultSet rs = null;
        if(begin == null) begin = LocalDateTime.of(1990, 1, 1, 0, 0, 0);
        try{
            String command;
            if(id != null){
                command = "SELECT type, SUM(amount) AS tong, COUNT(*) AS sl FROM transactions "
                    + "WHERE status = 'SUCCESSFUL' AND "
                    + "( (type = 'TRANSFER' AND senderID = ? AND sendTime > ?) "
                    + "OR (type = 'WITHDRAW' AND senderID = ? AND sendTime > ?) "
                    + "OR (type = 'DEPOSIT' AND receiverID = ? AND receiveTime > ?) )"
                    + "GROUP BY type";
            }
            else{
                command = "SELECT type, SUM(amount) AS tong, COUNT(*) AS sl FROM transactions "
                    + "WHERE status = 'SUCCESSFUL' AND ((sendTime > ?) OR (receiveTime > ?)) "
                    + "GROUP BY type";
            }

            statement = c.prepareStatement(command);
            int cnt = 0;
            if(id != null){
                statement.setString(++cnt, id); 
                statement.setTimestamp(++cnt, Timestamp.valueOf(begin));
                statement.setString(++cnt, id); 
                statement.setTimestamp(++cnt, Timestamp.valueOf(begin));
                statement.setString(++cnt, id); 
                statement.setTimestamp(++cnt, Timestamp.valueOf(begin));
            }
            else{
                statement.setTimestamp(++cnt, Timestamp.valueOf(begin));
                statement.setTimestamp(++cnt, Timestamp.valueOf(begin));
            } 
            rs = statement.executeQuery();
            while(rs.next()){
                String type = rs.getString("type");
                if(type.equals("TRANSFER")){
                    result[0] += rs.getLong("tong");
                    result[1] += rs.getLong("sl");
                }
                else if(type.equals("DEPOSIT")){
                    result[2] += rs.getLong("tong");
                    result[3] += rs.getLong("sl");
                }
                else{
                    result[4] += rs.getLong("tong");
                    result[5] += rs.getLong("sl");
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{ if(statement != null) statement.close();} catch(SQLException ex){}
            try{ if(rs != null) rs.close();} catch(SQLException ex){}
        }
        MyDatabase.closeConnection(c);
        return result;
    }
    
    public List<Long> staticsFor5Month(String id, int year, int month){
        Connection c = MyDatabase.getConnection();
        List<Long> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            String command = "SELECT senderID, receiverID, amount FROM transactions "
                    + "WHERE status = 'SUCCESSFUL' AND ( (? = senderID AND YEAR(sendTime) = ? AND MONTH(sendTime) = ?) "
                    + "OR (? = receiverID AND YEAR(receiveTime) = ? AND MONTH(receiveTime) = ?) )";

            statement = c.prepareStatement(command);
            statement.setString(1, id);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setString(4, id);
            statement.setInt(5, year);
            statement.setInt(6, month);
            Long income = Long.valueOf(0), expense = Long.valueOf(0);  
            rs = statement.executeQuery();
            while(rs.next()){
                String senderID = rs.getString("senderID");
                String receiverID = rs.getString("receiverID");
                Long amount = rs.getLong("amount");
                if(senderID == null){
                    income += amount;
                }
                else{
                    if(senderID.equals(id)) expense += amount;
                    else income += amount;
                }
            }
            result.add(income); result.add(expense);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{ if(statement != null) statement.close();} catch(SQLException ex){}
            try{ if(rs != null) rs.close();} catch(SQLException ex){}
        }
        MyDatabase.closeConnection(c);
        return result;
    }
    
    public LinkedHashMap<String, Long> staticsPremierCus(){
        Connection c = MyDatabase.getConnection();
        LinkedHashMap<String, Long> result = new LinkedHashMap<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            String command = "SELECT ID, balance FROM accounts WHERE type = 'Customer' ORDER BY balance DESC LIMIT 5;";

            statement = c.prepareStatement(command);
            rs = statement.executeQuery();
            while(rs.next()){
                String id = rs.getString("ID");
                Long amount = rs.getLong("balance");
                result.put(id, amount);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{ if(statement != null) statement.close();} catch(SQLException ex){}
            try{ if(rs != null) rs.close();} catch(SQLException ex){}
        }
        MyDatabase.closeConnection(c);
        return result;
    }
}
