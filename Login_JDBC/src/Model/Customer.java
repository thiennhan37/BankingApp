/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Hi
 */
public class Customer extends Account{
    
    public static int number  = 0;
    private double balance;

    public Customer(String fullName, String email, String password, String gender, LocalDate birthDay, boolean isActive, double balance) {
        super(fullName, email, password, gender, birthDay, isActive);
        this.balance = balance;
        this.id = "CTM" + String.format("%3d", ++number);
    }

    @Override
    public String getType() { return "Customer"; }
    @Override
    public double getBalace() { return balance; }
    @Override
    public String getBranch() { return null; }
    
    
}
