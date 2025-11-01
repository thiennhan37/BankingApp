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
import Control.*;
import DAO.*;
import Model.*;
import View.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import View.OTPDialog;
import java.time.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

class PieChartExample extends JFrame {
    public PieChartExample() {
        // 1️⃣ Tạo dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Transfer", 45);
        dataset.setValue("Withdraw", 30);
        dataset.setValue("Deposit", 25);

        // 2️⃣ Tạo chart
        JFreeChart chart = ChartFactory.createPieChart(
            "Statics Transactions", // tiêu đề
            dataset,              // dữ liệu
            true, true, false     // có legend, tooltips, URLs
        );

        // 3️⃣ Hiển thị chart trên JFrame
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        setTitle("My Chart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
public class testAccount {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        JOptionPane.showMessageDialog(null, "Activate account failed!", 
//                    "", JOptionPane.ERROR_MESSAGE);
//        Transaction x = TransactionDAO.getInstance().filterTransaction("999007", LocalDateTime.now().minusMonths(1),
//                LocalDateTime.now(), "ALL", "ALL").get(0);
//        new FormTransaction(x.getSenderID(), x.getReceiverID(), x.getAmount(),  x.getDescription(), x.getSendTime());
        new PieChartExample();
        
        

    }
}
