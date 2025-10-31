/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;
import Model.Account;

import java.util.ArrayList;
/**
 *
 * @author Hi
 *
*/
public interface DAOInterface<T> {
    public int addObject(T t);
    public int deleteObject(T t);
    public void updateObjectInfor(T t);
    public void updateObjectPass(T t);
            
    public int countObjects(String type);
    public T getObjectByEmail(String email);
    public T getObjectByID(String id);
    public boolean checkExistObject(String email);
    public ArrayList<Account> showAll();
    public boolean updateActiveObject(String id, boolean active);
}
