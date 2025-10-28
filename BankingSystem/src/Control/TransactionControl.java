/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.AccountDAO;
import DAO.TransactionDAO;
import Database.MyDatabase;
import Model.Transaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author Hi
 */
public class TransactionControl {
    private AccountDAO accDAO = AccountDAO.getInstance();
    private TransactionDAO transDAO = TransactionDAO.getInstance();
    public int Transfer(String senderID, String receiverID, Long amount, String type, String description){
        Connection connect = null;
        String status = "PENDING";
        LocalDateTime sendTime = LocalDateTime.now();
        Transaction trans = new Transaction(senderID, receiverID, amount, type, status, sendTime, null, description); 
        if(!transDAO.addTransaction(trans)) return 0;
        try{
            connect = MyDatabase.getConnection();
            connect.setAutoCommit(false);
            
            if(type.equals("WITHDRAW") || (type.equals("TRANSFER") && amount < 1_000_000_000) ){
                if(!accDAO.updateBalance(connect, senderID, -amount) ) throw new SQLException("Transaction failed"); 
                if(receiverID != null){
                    if(!accDAO.updateBalance(connect, receiverID, amount))
                        throw new SQLException("Transaction failed"); 
                }
                LocalDateTime receiveTime = LocalDateTime.now();
                transDAO.updateReceiveTime(trans.getTransID(), receiveTime);
                transDAO.updateStatus(trans.getTransID(), "SUCCESSFUL");
                // trans.setReceiveTime(receiveTime); 
                // trans.setStatus("SUCCESSFUL"); 
                return 1;
            }
        }
        catch(SQLException e){
            
            transDAO.updateStatus(trans.getTransID(), "FAILED");
        }
        finally{
            if(connect != null) try{connect.setAutoCommit(true);} catch(SQLException ex){}
        }
        return 0;
    }
}
