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
    public Staff(String id, String fullName, String email, String password, String gender, LocalDate birthDay, boolean active, String branch) {
        super(fullName, email, password, gender, birthDay, active);
        this.branch = branch;
        this.id = id;
    }

    public static int getNumber() {
        return number;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String getType() {return "Staff";}
    
    @Override
    public Long getBalace() {
        return null;
    }
    @Override
    public int getDegree(){ return 1;}
        
    
    
}
