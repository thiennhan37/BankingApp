/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Process;

/**
 *
 * @author Hi
 * toi uu hien thi nhanh khi register
 * đơ UI khi bấm resend
 */
import Control.AccountControl;
import Model.Account;
import DAO.AccountDAO;
import DAO.TransactionDAO;
import Model.Customer;
import Model.Transaction;
import View.FormTransaction;
import View.OTPDialog;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import View.OTPDialog;
import com.formdev.flatlaf.FlatDarculaLaf;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class testAccount {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        JFrame x = new JFrame("nhan");
//        JOptionPane.showInputDialog(x, "thong bao", "", JOptionPane.CLOSED_OPTION);
//        System.exit(0); 
        //x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        UIManager.put("OptionPane.background", new Color(178,137,145));
//        UIManager.put("RootPane.background", new Color(178,137,145));
//        UIManager.put("OptionPane.messageForeground", Color.DARK_GRAY);
//        // UIManager.put("RootPane.messageFont", new Font("Segoe UI", Font.BOLD, 25));
//        // UIManager.put("RootPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
//        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.BOLD, 14));
//        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
//        UIManager.put("Button.arc", 15); // bo tròn nút OK/Cancel
//        UIManager.put("Button.background", Color.WHITE); // màu xanh Google
//        UIManager.put("Button.foreground", Color.BLACK);

//        JFrame x = new JFrame();  
//        x.setLocationRelativeTo(null);
//        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        x.setVisible(true);

//        JOptionPane.showMessageDialog(null, "Activate account failed!", 
//                    "", JOptionPane.ERROR_MESSAGE);
        Transaction x = TransactionDAO.getInstance().filterTransaction("999007", LocalDateTime.now().minusMonths(1),
                LocalDateTime.now(), "ALL", "ALL").get(0);
        new FormTransaction(x.getAmount(), x.getSenderID(), x.getReceiverID(),  x.getDescription(), x.getSendTime());
    }
}
