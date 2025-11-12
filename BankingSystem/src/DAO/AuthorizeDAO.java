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
import java.time.LocalDate;

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

    public Integer[] staticsForStaff(String staffID, LocalDate time){
        Integer[] result = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Connection c = MyDatabase.getConnection();
        PreparedStatement statement1 = null, statement2 = null;
        ResultSet rs1 = null, rs2 = null;
        try{
            String command1 = "SELECT COUNT(*) AS COUNT1 FROM trans_manage WHERE staffID = ? AND YEAR(time) = ? AND MONTH(time) = ?;";
            String command2 = "SELECT COUNT(*) AS COUNT2 FROM account_manage WHERE staffID = ? AND YEAR(time) = ? AND MONTH(time) = ?;";
            for(int i = 4; i >= 0; i--){
                LocalDate tmp = time.minusMonths(i);
                statement1 = c.prepareStatement(command1);
                statement2 = c.prepareStatement(command2);
                
                statement1.setString(1, staffID);
                statement1.setInt(2, tmp.getYear()); statement1.setInt(3, tmp.getMonthValue());
                statement2.setString(1, staffID);
                statement2.setInt(2, tmp.getYear()); statement2.setInt(3, tmp.getMonthValue());
                rs1 = statement1.executeQuery(); rs1.next();
                rs2 = statement2.executeQuery(); rs2.next();
                
                result[(4 - i) * 2] = rs1.getInt("COUNT1");
                result[(4 - i) * 2 + 1] = rs2.getInt("COUNT2");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement1 != null){
                try{statement1.close();} catch(SQLException ex){};
            }
            if(statement2 != null){
                try{statement2.close();} catch(SQLException ex){};
            }
            if(rs1 != null){
                try{rs1.close();} catch(SQLException ex){};
            }
            if(rs2 != null){
                try{rs2.close();} catch(SQLException ex){};
            }
        }
        return result;
    }
}
