/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DAO.AccountDAO;
import View.FormLogin;
import Model.Account;
/**
 *
 * @author Hi
 */
public class AccountControl {
    private FormLogin formLogin;
    private AccountDAO accountDAO;

    public AccountControl(FormLogin formLogin) {
        this.formLogin = formLogin;
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
}
