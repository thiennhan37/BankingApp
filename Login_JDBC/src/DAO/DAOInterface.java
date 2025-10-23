/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;
import Model.Account;

import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Hi
 *
*/
public interface DAOInterface<T> {
    public int addObject(T t);
    public int deleteObject(T t);
    public int updateObject(T t);
    public boolean checkExistObject(T t);
    public ArrayList<Account> showAll();

}
