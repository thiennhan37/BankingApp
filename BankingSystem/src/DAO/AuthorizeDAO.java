/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.TransactionManage;
import Database.MyDatabase;
import Model.AccountManage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class AuthorizeDAO {
    public static AuthorizeDAO getInstance(){
        return new AuthorizeDAO();
    }
    public boolean addAccAuthorize(String staffID, String accountID, String decision){
        Connection c = MyDatabase.getConnection();
        int result = 0;
        PreparedStatement statement = null;
        try{
            String command = "INSERT INTO account_manage " +
                    " values (?, ?, ?, ?)";
            statement = c.prepareStatement(command);
            
            statement.setString(1, accountID);
            statement.setString(2, staffID);
            statement.setString(3, decision);
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            result = statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement != null){
                try {statement.close();} catch(SQLException ex){}
            }
        }
        MyDatabase.closeConnection(c);
        return result > 0;
    }
    
    public boolean addTransAuthorize(String staffID, String transID, String decision){
        Connection c = MyDatabase.getConnection();
        int result = 0;
        PreparedStatement statement = null;
        try{
            String command = "INSERT INTO trans_manage " +
                    " values (?, ?, ?, ?)";
            statement = c.prepareStatement(command);
            
            statement.setString(1, transID);
            statement.setString(2, staffID);
            statement.setString(3, decision);
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            result = statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement != null){
                try {statement.close();} catch(SQLException ex){}
            }
        }
        MyDatabase.closeConnection(c);
        return result > 0;
    }

    public List<TransactionManage> filterTransManage(String transID, String staffID, int year, int month){
        List<TransactionManage> result = new ArrayList<>();
        Connection c = MyDatabase.getConnection();
        PreparedStatement statement = null; 
        ResultSet rs = null;
        try{
            String command = "SELECT * FROM trans_manage WHERE " +
                    "transID LIKE ? AND staffID LIKE ? " +
                    "AND YEAR(time) = ? AND MONTH(time) = ?";
            statement = c.prepareStatement(command);
            statement.setString(1, "%" + transID + "%");
            statement.setString(2, "%" + staffID + "%");
            statement.setInt(3, year);
            statement.setInt(4, month);
            rs = statement.executeQuery();
            TransactionManage x;
            while(rs.next()){
                x = new TransactionManage(rs.getString("transID"), rs.getString("staffID"), 
                        rs.getString("decision"), rs.getTimestamp("time").toLocalDateTime());
                result.add(x);
            }
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
        return result;
    }

    public List<AccountManage> filterAccManage(String accountID, String staffID, int year, int month){
        List<AccountManage> result = new ArrayList<>();
        Connection c = MyDatabase.getConnection();
        PreparedStatement statement = null; 
        ResultSet rs = null;
        try{
            String command = "SELECT * FROM account_manage WHERE " +
                    "accountID LIKE ? AND staffID LIKE ? " +
                    "AND YEAR(time) = ? AND MONTH(time) = ?";
            statement = c.prepareStatement(command);
            statement.setString(1, "%" + accountID + "%");
            statement.setString(2, "%" + staffID + "%");
            statement.setInt(3, year);
            statement.setInt(4, month);
            rs = statement.executeQuery();
            AccountManage x;
            while(rs.next()){
                x = new AccountManage(rs.getString("accountID"), rs.getString("staffID"), 
                        rs.getString("decision"), rs.getTimestamp("time").toLocalDateTime());
                result.add(x);
            }
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
        return result;
    }

}
