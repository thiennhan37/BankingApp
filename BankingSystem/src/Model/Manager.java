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
public class Manager extends Account{
    private String branch;

    public Manager(String id, String fullName, String email, String password, String gender, LocalDate birthDay, boolean active, String branch) {
        super(fullName, email, password, gender, birthDay, active);
        this.branch = branch;
        this.id = id;
    }

    @Override
    public String getType() { return "Manager"; }

    @Override
    public Long getBalace() {return null;}

    @Override
    public String getBranch() {return branch;}

    @Override
    public int getDegree() {return 2;}
}
