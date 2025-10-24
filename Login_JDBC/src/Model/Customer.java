/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccountDAO;
import java.time.LocalDate;

/**
 *
 * @author Hi
 */ 
public class Customer extends Account{
    
    public static int number  = AccountDAO.getInstance().countObjects("Customer");
    private Double balance;

    public Customer(String fullName, String email, String password, String gender, LocalDate birthDay, boolean isActive, Double balance) {
        super(fullName, email, password, gender, birthDay, isActive);
        this.balance = balance;
        this.id = "999" + String.format("%03d", ++number);
    }
    @Override
    public String getType() { return "Customer"; }
    @Override
    public Double getBalace() { return balance; }
    @Override
    public String getBranch() { return null; }
    
    
}
