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
import java.awt.event.*;
import java.time.*;
import javax.swing.table.DefaultTableModel;
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
    public static void  createAndShowGUI() {
        JFrame frame = new JFrame("Ví dụ JTable đơn giản");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // ===== DỮ LIỆU MẪU =====
        String[] columnNames = {"ID", "Tên", "Tuổi"};
        Object[][] data = {
            {1, "Nguyễn Văn A", 20},
            {2, "Trần Thị B", 22},
            {3, "Lê Văn C", 19},
            {4, "Phạm Thị D", 21}
        };

        // ===== TẠO MODEL & TABLE =====
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // ===== SỰ KIỆN NHẤN ĐÚP =====
        table.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        Object id = table.getValueAt(row, 0);
                        Object name = table.getValueAt(row, 1);
                        Object age = table.getValueAt(row, 2);
                        JOptionPane.showMessageDialog(frame,
                                "Bạn nhấn đúp vào:\nID: " + id + "\nTên: " + name + "\nTuổi: " + age);
                    }
                }
            }
        });

        frame.setLocationRelativeTo(null); // giữa màn hình
        frame.setVisible(true);
    }
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
        // new PieChartExample();
        // createAndShowGUI();
        Scanner sc = new Scanner(System.in);
//        String s = sc.next();
//        String[] arr = s.split("/");
//        for(String x : arr){
//            System.out.println(x);
//        }
//        System.out.println(s);

    }
}
