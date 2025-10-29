/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.AccountDAO;
import DAO.TransactionDAO;
import Database.MyDatabase;
import Model.Transaction;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
/**
 *
 * @author Hi
 */
public class StaffControl {
    private TransactionDAO transDAO = TransactionDAO.getInstance();
    private AccountDAO accDAO = AccountDAO.getInstance();
    public boolean confirmTransaction(String transID, String senderID, String receiverID, String type, Long amount){
        Connection c = MyDatabase.getConnection();
        try{
            c.setAutoCommit(false); 
            if(type.equals("TRANSFER")){
                if(!accDAO.updateBalance(c, senderID, -amount)) throw new Exception("Cannot update sender balance");
                if(!accDAO.updateBalance(c, receiverID, amount)) throw new Exception("Cannot update receiver balance");
                if(!transDAO.updateStatus(transID, "SUCCESSFUL")) throw new Exception("Cannot update status");
                transDAO.updateReceiveTime(transID, LocalDateTime.now());
            }
            else if(type.equals("DEPOSIT")){
                if(!accDAO.updateBalance(c, receiverID, amount)) throw new Exception("Cannot update receiver balance");
                if(!transDAO.updateStatus(transID, "SUCCESSFUL")) throw new Exception("Cannot update status");
                transDAO.updateReceiveTime(transID, LocalDateTime.now());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        finally{
            if(c != null){
                try{c.setAutoCommit(true);} catch(SQLException e){}
            }
        }
        MyDatabase.closeConnection(c);
        return true;
    }
    
    public List<Transaction> filterTransForAuthorize(String senderID, String receiverID, 
            int beginMonth, int beginYear, int endMonth, int endYear, String type){
        LocalDateTime beginTime = LocalDateTime.of(beginYear, beginMonth, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(endYear, endMonth, 1, 0, 0, 0).plusMonths(1); 
        return transDAO.filterTransForAuthorize(senderID, receiverID, beginTime, endTime, type);
    }
}
