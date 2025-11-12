/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Process.SendMail;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Hi
 */
public class OTPDialog extends JDialog{
    private JTextField otpField;
    private JButton resendButton, okButton;
    private String serverOTP;
    private boolean match = false;
    private Timer ttl;
    public OTPDialog(JFrame par, String toEmail, String typeMess) {
        super(par, "Verify OTP", true);
        setSize(400, 150);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(par);
        
        Font myFont = new Font("Segoe UI", Font.BOLD, 14);
        otpField = new JTextField("", 10);
        otpField.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        otpField.setFont(myFont);
        
        resendButton = new JButton("Resend OTP");
        resendButton.setFont(myFont);
        resendButton.putClientProperty("FlatLaf.style", "arc:20; foreground:#B28991 ;background:#99FFFF;");
        
        okButton = new JButton("OK");
        okButton.setFont(myFont);
        okButton.putClientProperty("FlatLaf.style", "arc:20; foreground:#B28991 ;background:#99FFFF;");
        
        JLabel label = new JLabel("Please enter the OTP sent to your email");
        label.setFont(myFont);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(label);
        inputPanel.add(otpField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(resendButton);
        buttonPanel.add(okButton);

        
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        
        
        serverOTP = SendMail.sendVerificationCode(toEmail, typeMess);
        ttl = new Timer(5 * 60 * 1000, lbd -> serverOTP = null);
        ttl.setRepeats(false);
        ttl.start();
        
        okButton.addActionListener(e -> {
            String OTP = otpField.getText().trim();
            if(serverOTP != null && OTP.equals(serverOTP)){
                match = true;
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Wrong OTP. Please check and try again", "",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        
        resendButton.addActionListener(e -> {
            resendButton.setEnabled(false);
            serverOTP = SendMail.sendVerificationCode(toEmail, typeMess);
            if(ttl != null) ttl.stop();
            ttl = new Timer(5 * 60 * 1000, lbd -> serverOTP = null);
            ttl.setRepeats(false);
            ttl.start();
            
            Timer tt = new Timer(5000, lbd -> resendButton.setEnabled(true));
            tt.setRepeats(false);
            tt.start();
            
        });
        setVisible(true);
    }

    public boolean isMatch() {
        return match;
    }

    
}
