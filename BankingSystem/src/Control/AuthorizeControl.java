/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.AuthorizeDAO;
import Model.AccountManage;
import Model.TransactionManage;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Hi
 */
public class AuthorizeControl {
    private AuthorizeDAO authorizeDAO = AuthorizeDAO.getInstance();
    public boolean addAccAuthorize(String staffID, String accountID, String decision){
        return authorizeDAO.addAccAuthorize(staffID, accountID, decision);
    }
    public boolean addTransAuthorize(String staffID, String transID, String decision){
        return authorizeDAO.addTransAuthorize(staffID, transID, decision);
    }
    public List<TransactionManage> filterTransManage(String transID, String staffID, int year, int month){
        return authorizeDAO.filterTransManage(transID, staffID, year, month);
    }
    
    public List<AccountManage> filterAccManage(String transID, String staffID, int year, int month){
        return authorizeDAO.filterAccManage(transID, staffID, year, month);
    }
    public Integer[] staticsForStaff(String staffID, LocalDate time){
        return authorizeDAO.staticsForStaff(staffID, time);
    }
}
