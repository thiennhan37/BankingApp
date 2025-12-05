/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.AccountDAO;
import View.FormLogin;
import Model.Account;
import View.FormCustomer;
import View.FormStaff;
/**
 *
 * @author Hi
 */
public class AccountControl {
    private FormLogin formLogin;
    private AccountDAO accountDAO;
    private FormCustomer formCustomer;
    private FormStaff formStaff;
    public AccountControl(){
        this.accountDAO = AccountDAO.getInstance();
    }
    public AccountControl(FormLogin formLogin) {
        this.formLogin = formLogin;
        this.accountDAO = AccountDAO.getInstance();
    }
    public AccountControl(FormCustomer formCustomer) {
        this.formCustomer = formCustomer;
        this.accountDAO = AccountDAO.getInstance();
    }
    public AccountControl(FormStaff formStaff) {
        this.formStaff = formStaff;
        this.accountDAO = AccountDAO.getInstance();
    }
    public boolean checkExistedAccount(String email){
        return accountDAO.checkExistObject(email);
    }
    public boolean checkValidAccount(String email, String password){ 
        return accountDAO.checkValidAccount(email, password);
    }
    public int addAccount(Account ac){
        return accountDAO.addObject(ac);
    }
    public Account getAccountByEmail(String email){
        return accountDAO.getObjectByEmail(email) ; 
    }
    public Account getAccountByID(String id){
        return accountDAO.getObjectByID(id);
    } 
    public void updateObjectInfor(Account ac){
        accountDAO.updateObjectInfor(ac);
    }
    public void updateObjectPass(Account ac){
        accountDAO.updateObjectPass(ac); 
    }
    
    public boolean updateObjectActive(String id, boolean active){
        return accountDAO.updateActiveObject(id, active);
    }
}
