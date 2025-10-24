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
public class Staff extends Account{
    public static int number = AccountDAO.getInstance().countObjects("Staff");
    private String branch;
    
    public Staff(String fullName, String email, String password, String gender, LocalDate birthDay, boolean active, String branch) {
        super(fullName, email, password, gender, birthDay, active);
        this.branch = branch;
        this.id = "STF" + String.format("%03d", ++number);
    }
    
    @Override
    public String getType() {
        return "Staff";
    }
    @Override
    public Double getBalace() {
        return null;
    }
    @Override
    public String getBranch() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
