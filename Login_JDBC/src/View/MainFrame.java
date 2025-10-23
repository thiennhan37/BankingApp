/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.CardLayout;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Hi
 */
public class MainFrame extends JFrame{
    public TreeMap<String, String> listAccount = new TreeMap<>();
    public JPanel mainPanel;
    public MainFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainPanel = new JPanel(new CardLayout());
        PanelLogin panelLogin = new PanelLogin(this);
        PanelSignup panelSignup = new PanelSignup(this);
        mainPanel.add(panelLogin, "Login");
        mainPanel.add(panelSignup, "Signup");
        
        
        this.add(mainPanel);
        // this.addAccount("thiennhan11a1@gmail.com", "nhanthien");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public boolean checkAccount(String email, String password){
        if(listAccount.containsKey(email) && listAccount.get(email).equals(password)) return true;
        return false;    
    }
    public boolean isExistAccount(String email){
        return listAccount.containsKey(email);
    }
    public void addAccount(String email, String password){
        listAccount.put(email, password);
    }

}
