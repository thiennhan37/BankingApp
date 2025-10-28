/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Control.AccountControl;
import Control.TransactionControl;
import Model.Account;
import Process.SendMail;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Hi
 */
public class FormCustomer extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormCustomer.class.getName());

    /**
     * Creates new form FormCustomer
     */
    private TransactionControl transController;
    private AccountControl accController;
    private Account myAccount;
    private String myEmail;
    private CardLayout cardLayout3;
    private boolean isOpenEye3 = true;
    private ImageIcon openEyeImage = new javax.swing.ImageIcon(getClass().getResource("/MyImage/seenEye.png"));
    private ImageIcon closeEyeImage = new javax.swing.ImageIcon(getClass().getResource("/MyImage/hideEye.png"));
    private ImageIcon logoutStatic = new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutStatic.png"));
    private ImageIcon logoutDynamic = new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutDynamic1.gif"));
    
    private Color color1 = new Color(255,255,255), 
            color2 = new Color(171, 245, 232),
            color3 = new Color(89, 222, 198);
    public FormCustomer(String myEmail) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.myEmail = myEmail;
        accController = new AccountControl(this);
        transController = new TransactionControl();
        initComponents();
        myAccount = accController.getAccountByEmail(myEmail);
        resetAccountInfo();
        txtPassword3.setEchoChar((char) 0); 
        cardLayout3 = (CardLayout) pnlCard.getLayout();
        
        settingGUIComponent();
        setMouseList(); 
        setAmountText(txtTransAmount, new JButton[]{btSATrans1, btSATrans2, btSATrans3});
        setTextForNumber(txtTransTo); 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
         
    } 
    private void settingGUIComponent(){
        // btHistory.putClientProperty(FlatClientProperties.STYLE,"arc:0; ");
        btSaveInfo3.putClientProperty(FlatClientProperties.STYLE,"arc:25; ");
        btChangePass3.putClientProperty(FlatClientProperties.STYLE,"arc:25; ");
        btTransfer3.putClientProperty(FlatClientProperties.STYLE,"arc:25; ");
        
        lblEmail3.putClientProperty("FlatLaf.style", "arc:20; background:#F0F8FF;");
        fieldDay3.putClientProperty("FlatLaf.style", "borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        fieldMonth3.putClientProperty("FlatLaf.style", "borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        fieldYear3.putClientProperty("FlatLaf.style", "borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        
        
        JComponent[] arr = {txtFullName3, txtPassword3, txtConfirmPass3, txtTransTo, txtTransAmount, txtTransDescription, 
                           btSATrans1, btSATrans2, btSATrans3};
        for(JComponent x : arr){
           x.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        
    }   
    
    private void setMouseList(){
        JButton[] arr = {btHome, btHistory, btTrans, btWithDraw, btDeposit, btStatics, btLogout};
        
        for(int i = 0; i < arr.length; i++){
            JButton x = arr[i];
            x.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if(x.hasFocus()) return;
                    x.setBackground(color2);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(x.hasFocus()) return;
                    x.setBackground(color1); 
                }
                
            });
            x.addFocusListener(new FocusListener(){
                @Override
                public void focusGained(FocusEvent e) {
                    x.setBackground(color3); 
                }

                @Override
                public void focusLost(FocusEvent e) { 
                    x.setBackground(color1);
                }
                
            });
        }
    }
    private void resetAccountInfo(){
        txtFullName3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        txtPassword3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        txtConfirmPass3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        lblFullName3.setText(myAccount.getFullName());
        lblAccNumber3.setText(myAccount.getId());
        lblBalance3.setText(myAccount.getBalace().toString());
        lblEmail3.setText("   " + myAccount.getEmail());
        txtFullName3.setText(myAccount.getFullName());
        fieldDay3.setSelectedIndex(myAccount.getBirthDay().getDayOfMonth() - 1);
        fieldMonth3.setSelectedIndex(myAccount.getBirthDay().getMonthValue() - 1);
        fieldYear3.setSelectedIndex(myAccount.getBirthDay().getYear() - 1900);
        if(myAccount.getGender().equals("Female")){
            btFemale3.setSelected(true);
        }
        txtPassword3.setText("");
        txtConfirmPass3.setText(""); 
        lblWarnSavePass3.setText(""); 
    }
    private void setTextForNumber(JFormattedTextField txtFormat){
        NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setGroupingUsed(false); 
        NumberFormatter formatter = new NumberFormatter(numFormat); 
        formatter.setValueClass(Long.class); // kiểu dữ liệu 
        formatter.setAllowsInvalid(false); // không cho nhập ký tự sai 
        formatter.setMinimum(0L); // không cho nhập số âm 
        formatter.setMaximum(999999L); 
        txtFormat.setFormatterFactory(new DefaultFormatterFactory(formatter)); 
        
        txtFormat.addKeyListener(new java.awt.event.KeyAdapter() { 
            @Override 
            public void keyPressed(java.awt.event.KeyEvent e) { 
                if ((e.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE 
                        || e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE) 
                        && txtFormat.getText().trim().length() <= 1) 
                     txtFormat.setValue(null);
            }
        });
    }
    private void setAmountText(JFormattedTextField txtFormat, JButton[] arr){
        NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setGroupingUsed(true);
        Long mx = 10_000_000_000L;
        NumberFormatter formatter = new NumberFormatter(numFormat);
        formatter.setValueClass(Long.class);    // kiểu dữ liệu
        formatter.setAllowsInvalid(false);      // không cho nhập ký tự sai
        formatter.setMinimum(0L);               // không cho nhập số âm
        formatter.setMaximum(mx);
        
        txtFormat.setFormatterFactory(new DefaultFormatterFactory(formatter)); 
        txtFormat.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if ((e.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE ||
                     e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE) &&
                    txtFormat.getText().replace(",", "").trim().length() <= 1) {
                    txtFormat.setValue(null);  // cho phép xóa hết
                }
            }
        });
        txtFormat.getDocument().addDocumentListener(new DocumentListener() {
            private boolean isFormatting = false;

            private void updateSuggestionsAndFormat() {
                if (isFormatting) return;
                isFormatting = true;
                SwingUtilities.invokeLater(() -> {
                    try {
                        String text = txtFormat.getText().replace(",", "").trim();

                        // Nếu rỗng thì xóa gợi ý
                        if (text.isEmpty() || !text.matches("\\d+")) {
                            for (JButton b : arr) b.setText("");
                            isFormatting = false;
                            return;
                        }

                        long money = Long.parseLong(text);

                        // Cập nhật gợi ý
                        long[] factors = {100, 1000, 10000};
                        for (int i = 0; i < arr.length; i++) {
                            if (money * factors[i] <= mx)
                                arr[i].setText(numFormat.format(money * factors[i]));
                            else
                                arr[i].setText("");
                        }

                        // Cập nhật lại text có format
                        String formatted = numFormat.format(money);
                        txtFormat.getCaretPosition();
                        txtFormat.setText(formatted);
                        txtFormat.setCaretPosition(txtFormat.getText().length());
                    } catch (Exception ignored) {
                    } finally {
                        isFormatting = false;
                    }
                });
            }

            @Override public void insertUpdate(DocumentEvent e) { updateSuggestionsAndFormat(); }
            @Override public void removeUpdate(DocumentEvent e) { updateSuggestionsAndFormat(); }
            @Override public void changedUpdate(DocumentEvent e) {}
        });

        for(JButton x : arr){
            x.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    
                    String text = x.getText().replace(",", "").trim();
                    if(text.isEmpty()) return;
                    txtFormat.setValue(Long.valueOf(text));
                }
            });
        }
    }
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GenderGroup3 = new javax.swing.ButtonGroup();
        pnlTop = new javax.swing.JPanel();
        btReset3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblAccNumber3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblBalance3 = new javax.swing.JLabel();
        lblFullName3 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btHome = new javax.swing.JButton();
        btHistory = new javax.swing.JButton();
        btTrans = new javax.swing.JButton();
        btWithDraw = new javax.swing.JButton();
        btDeposit = new javax.swing.JButton();
        btStatics = new javax.swing.JButton();
        lblProfile = new javax.swing.JLabel();
        btSetingPF = new javax.swing.JButton();
        lblLineRow1 = new javax.swing.JLabel();
        pnlLogout = new javax.swing.JPanel();
        btLogout = new javax.swing.JButton();
        lblLineCol2 = new javax.swing.JLabel();
        lblLineCol1 = new javax.swing.JLabel();
        lblLineRow2 = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        pnlCardHome = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pnlCardProfile = new javax.swing.JPanel();
        pnlSubProfile1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtFullName3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        fieldDay3 = new javax.swing.JComboBox<>();
        fieldMonth3 = new javax.swing.JComboBox<>();
        fieldYear3 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        btMale3 = new javax.swing.JRadioButton();
        btFemale3 = new javax.swing.JRadioButton();
        btSaveInfo3 = new javax.swing.JButton();
        lblEmail3 = new javax.swing.JLabel();
        pnlSubProfile2 = new javax.swing.JPanel();
        btEyePass3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtPassword3 = new javax.swing.JPasswordField();
        txtConfirmPass3 = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        btChangePass3 = new javax.swing.JButton();
        lblWarnSavePass3 = new javax.swing.JLabel();
        pnlCardHistory = new javax.swing.JPanel();
        pnlCardTransfer = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblToName = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTransAmount = new javax.swing.JFormattedTextField();
        btSATrans1 = new javax.swing.JButton();
        btSATrans2 = new javax.swing.JButton();
        btSATrans3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtTransDescription = new javax.swing.JTextField();
        txtTransTo = new javax.swing.JFormattedTextField();
        btTransfer3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        pnlTop.setBackground(new java.awt.Color(153, 255, 255));

        btReset3.setBackground(new java.awt.Color(153, 255, 255));
        btReset3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/reset.png"))); // NOI18N
        btReset3.setBorderPainted(false);
        btReset3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReset3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Account Number");

        lblAccNumber3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAccNumber3.setForeground(new java.awt.Color(255, 255, 255));
        lblAccNumber3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Balance");

        lblBalance3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBalance3.setForeground(new java.awt.Color(255, 255, 255));
        lblBalance3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblFullName3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFullName3.setForeground(new java.awt.Color(153, 153, 153));
        lblFullName3.setText("NGUYEN THIEN NHAN");

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAccNumber3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBalance3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
                    .addComponent(lblFullName3, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btReset3)
                .addGap(16, 16, 16))
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btReset3))
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblFullName3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lblAccNumber3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBalance3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenu.setLayout(new java.awt.GridLayout(0, 1));

        btHome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/home.png"))); // NOI18N
        btHome.setText("  Home   ");
        btHome.setBorderPainted(false);
        btHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHomeActionPerformed(evt);
            }
        });
        pnlMenu.add(btHome);

        btHistory.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btHistory.setForeground(new java.awt.Color(51, 51, 51));
        btHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/bill.png"))); // NOI18N
        btHistory.setText("   History   ");
        btHistory.setBorderPainted(false);
        btHistory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btHistoryFocusLost(evt);
            }
        });
        btHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHistoryActionPerformed(evt);
            }
        });
        pnlMenu.add(btHistory);

        btTrans.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btTrans.setForeground(new java.awt.Color(51, 51, 51));
        btTrans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/trans.png"))); // NOI18N
        btTrans.setText(" Transfer ");
        btTrans.setBorderPainted(false);
        btTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTransActionPerformed(evt);
            }
        });
        pnlMenu.add(btTrans);

        btWithDraw.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btWithDraw.setForeground(new java.awt.Color(51, 51, 51));
        btWithDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/withdraw.png"))); // NOI18N
        btWithDraw.setText("  Withdraw");
        btWithDraw.setBorderPainted(false);
        btWithDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWithDrawActionPerformed(evt);
            }
        });
        pnlMenu.add(btWithDraw);

        btDeposit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDeposit.setForeground(new java.awt.Color(51, 51, 51));
        btDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/deposit.png"))); // NOI18N
        btDeposit.setText(" Deposit ");
        btDeposit.setBorderPainted(false);
        pnlMenu.add(btDeposit);

        btStatics.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btStatics.setForeground(new java.awt.Color(51, 51, 51));
        btStatics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/statics.png"))); // NOI18N
        btStatics.setText("  Statics  ");
        btStatics.setBorderPainted(false);
        pnlMenu.add(btStatics);

        lblProfile.setBackground(new java.awt.Color(153, 255, 255));
        lblProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/profile.png"))); // NOI18N
        lblProfile.setOpaque(true);

        btSetingPF.setBackground(new java.awt.Color(153, 255, 255));
        btSetingPF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btSetingPF.setForeground(new java.awt.Color(51, 51, 51));
        btSetingPF.setText("Setting Profile");
        btSetingPF.setBorder(null);
        btSetingPF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btSetingPFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btSetingPFMouseExited(evt);
            }
        });
        btSetingPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetingPFActionPerformed(evt);
            }
        });

        lblLineRow1.setBackground(new java.awt.Color(0, 0, 0));
        lblLineRow1.setOpaque(true);

        pnlLogout.setBackground(new java.awt.Color(255, 255, 255));

        btLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutStatic.png"))); // NOI18N
        btLogout.setText("   Logout");
        btLogout.setBorderPainted(false);
        btLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btLogoutMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlLogoutLayout = new javax.swing.GroupLayout(pnlLogout);
        pnlLogout.setLayout(pnlLogoutLayout);
        pnlLogoutLayout.setHorizontalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLogoutLayout.setVerticalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoutLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblLineCol2.setBackground(new java.awt.Color(0, 0, 0));
        lblLineCol2.setOpaque(true);

        lblLineCol1.setBackground(new java.awt.Color(0, 0, 0));
        lblLineCol1.setOpaque(true);

        lblLineRow2.setBackground(new java.awt.Color(0, 0, 0));
        lblLineRow2.setOpaque(true);

        pnlCard.setLayout(new java.awt.CardLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/Giám đốc nhân sự 093 456 789 xinchao@trangwebhay.vn.gif"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/ChucTet.png"))); // NOI18N

        javax.swing.GroupLayout pnlCardHomeLayout = new javax.swing.GroupLayout(pnlCardHome);
        pnlCardHome.setLayout(pnlCardHomeLayout);
        pnlCardHomeLayout.setHorizontalGroup(
            pnlCardHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardHomeLayout.createSequentialGroup()
                .addGroup(pnlCardHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardHomeLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardHomeLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlCardHomeLayout.setVerticalGroup(
            pnlCardHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardHomeLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlCard.add(pnlCardHome, "Home");

        pnlCardProfile.setName(""); // NOI18N

        pnlSubProfile1.setBackground(new java.awt.Color(255, 255, 255));
        pnlSubProfile1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(153, 153, 255))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(178, 137, 145));
        jLabel7.setText("Fullname");

        txtFullName3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFullName3FocusLost(evt);
            }
        });
        txtFullName3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFullName3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(178, 137, 145));
        jLabel8.setText("Email");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(178, 137, 145));
        jLabel11.setText("Day");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(178, 137, 145));
        jLabel12.setText("Month");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(178, 137, 145));
        jLabel13.setText("Year");

        fieldDay3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        fieldDay3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldDay3FocusLost(evt);
            }
        });

        fieldMonth3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldMonth3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldMonth3FocusLost(evt);
            }
        });

        fieldYear3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldYear3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldYear3FocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(178, 137, 145));
        jLabel14.setText("Gender");

        GenderGroup3.add(btMale3);
        btMale3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMale3.setForeground(new java.awt.Color(178, 137, 145));
        btMale3.setSelected(true);
        btMale3.setText("Male");

        GenderGroup3.add(btFemale3);
        btFemale3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFemale3.setForeground(new java.awt.Color(178, 137, 145));
        btFemale3.setText("Female");

        btSaveInfo3.setBackground(new java.awt.Color(153, 255, 255));
        btSaveInfo3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btSaveInfo3.setForeground(new java.awt.Color(178, 137, 145));
        btSaveInfo3.setText("Save ");
        btSaveInfo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveInfo3ActionPerformed(evt);
            }
        });

        lblEmail3.setBackground(new java.awt.Color(255, 255, 255));
        lblEmail3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmail3.setForeground(new java.awt.Color(178, 137, 145));
        lblEmail3.setToolTipText("");
        lblEmail3.setOpaque(true);

        javax.swing.GroupLayout pnlSubProfile1Layout = new javax.swing.GroupLayout(pnlSubProfile1);
        pnlSubProfile1.setLayout(pnlSubProfile1Layout);
        pnlSubProfile1Layout.setHorizontalGroup(
            pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                        .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtFullName3, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(240, 240, 240))
                            .addComponent(lblEmail3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                        .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldDay3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldMonth3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btSaveInfo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldYear3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btMale3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btFemale3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(116, 116, 116))
                            .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        pnlSubProfile1Layout.setVerticalGroup(
            pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubProfile1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFullName3, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(lblEmail3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldDay3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldMonth3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldYear3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btMale3)
                    .addComponent(btFemale3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btSaveInfo3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        fieldYear3.setSelectedIndex(105);

        pnlSubProfile2.setBackground(new java.awt.Color(255, 255, 255));
        pnlSubProfile2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(153, 153, 255))); // NOI18N

        btEyePass3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/seenEye.png"))); // NOI18N
        btEyePass3.setContentAreaFilled(false);
        btEyePass3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEyePass3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(178, 137, 145));
        jLabel9.setText("New Password");

        txtPassword3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword3FocusLost(evt);
            }
        });

        txtConfirmPass3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConfirmPass3FocusLost(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(178, 137, 145));
        jLabel10.setText("Confirm New Password");

        btChangePass3.setBackground(new java.awt.Color(153, 255, 255));
        btChangePass3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btChangePass3.setForeground(new java.awt.Color(178, 137, 145));
        btChangePass3.setText("Change Password");
        btChangePass3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangePass3ActionPerformed(evt);
            }
        });

        lblWarnSavePass3.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout pnlSubProfile2Layout = new javax.swing.GroupLayout(pnlSubProfile2);
        pnlSubProfile2.setLayout(pnlSubProfile2Layout);
        pnlSubProfile2Layout.setHorizontalGroup(
            pnlSubProfile2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubProfile2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubProfile2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(pnlSubProfile2Layout.createSequentialGroup()
                        .addComponent(txtPassword3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEyePass3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addGroup(pnlSubProfile2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btChangePass3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblWarnSavePass3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtConfirmPass3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        pnlSubProfile2Layout.setVerticalGroup(
            pnlSubProfile2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubProfile2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubProfile2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btEyePass3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmPass3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnSavePass3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btChangePass3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlCardProfileLayout = new javax.swing.GroupLayout(pnlCardProfile);
        pnlCardProfile.setLayout(pnlCardProfileLayout);
        pnlCardProfileLayout.setHorizontalGroup(
            pnlCardProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSubProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSubProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        pnlCardProfileLayout.setVerticalGroup(
            pnlCardProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardProfileLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlSubProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSubProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pnlCard.add(pnlCardProfile, "Profile");

        javax.swing.GroupLayout pnlCardHistoryLayout = new javax.swing.GroupLayout(pnlCardHistory);
        pnlCardHistory.setLayout(pnlCardHistoryLayout);
        pnlCardHistoryLayout.setHorizontalGroup(
            pnlCardHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );
        pnlCardHistoryLayout.setVerticalGroup(
            pnlCardHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardHistory, "History");

        pnlCardTransfer.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardTransfer.setName(""); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(178, 137, 145));
        jLabel5.setText("Transfer to");

        lblToName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblToName.setForeground(new java.awt.Color(153, 153, 153));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(178, 137, 145));
        jLabel15.setText("Amount");

        txtTransAmount.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTransAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransAmountActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(178, 137, 145));
        jLabel16.setText("Transaction Description");

        txtTransDescription.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtTransTo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTransTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTransToFocusLost(evt);
            }
        });

        btTransfer3.setBackground(new java.awt.Color(153, 255, 255));
        btTransfer3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btTransfer3.setForeground(new java.awt.Color(178, 137, 145));
        btTransfer3.setText("Transfer");
        btTransfer3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTransfer3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCardTransferLayout = new javax.swing.GroupLayout(pnlCardTransfer);
        pnlCardTransfer.setLayout(pnlCardTransferLayout);
        pnlCardTransferLayout.setHorizontalGroup(
            pnlCardTransferLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTransferLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlCardTransferLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblToName, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCardTransferLayout.createSequentialGroup()
                        .addComponent(btSATrans1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSATrans2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSATrans3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTransAmount)
                    .addComponent(txtTransDescription)
                    .addComponent(txtTransTo)
                    .addComponent(btTransfer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(217, Short.MAX_VALUE))
        );
        pnlCardTransferLayout.setVerticalGroup(
            pnlCardTransferLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardTransferLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel5)
                .addGap(7, 7, 7)
                .addComponent(txtTransTo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblToName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTransAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardTransferLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSATrans2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSATrans3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSATrans1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTransDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btTransfer3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        pnlCard.add(pnlCardTransfer, "Transfer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLineRow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlLogout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProfile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btSetingPF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLineRow2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLineCol1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLineCol2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(btSetingPF))
                    .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLineCol1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(lblLineRow1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblLineRow2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblLineCol2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHistoryActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "History");
    }//GEN-LAST:event_btHistoryActionPerformed

    private void btTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTransActionPerformed
        // TODO add your handling code here:
        txtTransTo.setValue(null);
        txtTransAmount.setValue(null);
        txtTransDescription.setText(myAccount.getFullName() + " chuyen tien");
        lblToName.setText(""); 
        cardLayout3.show(pnlCard, "Transfer");
    }//GEN-LAST:event_btTransActionPerformed

    private void btWithDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWithDrawActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btWithDrawActionPerformed

    private void btHistoryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btHistoryFocusLost
        // TODO add your handling code here:
        btHistory.setBackground(color1); 
    }//GEN-LAST:event_btHistoryFocusLost

    private void btLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLogoutMouseEntered
        // TODO add your handling code here:
        btLogout.setIcon(logoutDynamic);
    }//GEN-LAST:event_btLogoutMouseEntered

    private void btLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLogoutMouseExited
        // TODO add your handling code here:
        btLogout.setIcon(logoutStatic);
    }//GEN-LAST:event_btLogoutMouseExited

    private void txtFullName3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullName3FocusLost
        // TODO add your handling code here:
        txtFullName3.setText(txtFullName3.getText().trim().toUpperCase());
        if(txtFullName3.getText().isEmpty()){
            txtFullName3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtFullName3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtFullName3FocusLost

    private void fieldDay3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldDay3FocusLost
        // TODO add your handling code here:
        String ss = fieldDay3.getSelectedItem().toString() + "/"
        + fieldMonth3.getSelectedItem().toString() + "/"
        + fieldYear3.getSelectedItem().toString();
        LocalDate date = LocalDate.parse(ss, DateTimeFormatter.ofPattern("d/M/yyyy"));
        fieldDay3.setSelectedIndex(date.getDayOfMonth() - 1);
        fieldMonth3.setSelectedIndex(date.getMonthValue() - 1);
        fieldYear3.setSelectedIndex(date.getYear() - 1900);
    }//GEN-LAST:event_fieldDay3FocusLost

    private void fieldMonth3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldMonth3FocusLost
        // TODO add your handling code here:
        String ss = fieldDay3.getSelectedItem().toString() + "/"
        + fieldMonth3.getSelectedItem().toString() + "/"
        + fieldYear3.getSelectedItem().toString();
        LocalDate date = LocalDate.parse(ss, DateTimeFormatter.ofPattern("d/M/yyyy"));
        fieldDay3.setSelectedIndex(date.getDayOfMonth() - 1);
        fieldMonth3.setSelectedIndex(date.getMonthValue() - 1);
        fieldYear3.setSelectedIndex(date.getYear() - 1900);
    }//GEN-LAST:event_fieldMonth3FocusLost

    private void fieldYear3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldYear3FocusLost
        // TODO add your handling code here:
        String ss = fieldDay3.getSelectedItem().toString() + "/"
        + fieldMonth3.getSelectedItem().toString() + "/"
        + fieldYear3.getSelectedItem().toString();
        LocalDate date = LocalDate.parse(ss, DateTimeFormatter.ofPattern("d/M/yyyy"));
        fieldDay3.setSelectedIndex(date.getDayOfMonth() - 1);
        fieldMonth3.setSelectedIndex(date.getMonthValue() - 1);
        fieldYear3.setSelectedIndex(date.getYear() - 1900);
    }//GEN-LAST:event_fieldYear3FocusLost

    private void btEyePass3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEyePass3ActionPerformed
        // TODO add your handling code here:
        if(isOpenEye3){
            txtPassword3.setEchoChar('•');
            btEyePass3.setIcon(closeEyeImage);
            isOpenEye3 = false;
        }
        else{
            txtPassword3.setEchoChar((char) 0);
            btEyePass3.setIcon(openEyeImage);
            isOpenEye3 = true;
        }
    }//GEN-LAST:event_btEyePass3ActionPerformed

    private void txtPassword3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword3FocusLost
        // TODO add your handling code here:
        txtPassword3.setText(txtPassword3.getText().trim());
        if(txtPassword3.getText().isEmpty()){
            txtPassword3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtPassword3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtPassword3FocusLost

    private void txtConfirmPass3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConfirmPass3FocusLost
        // TODO add your handling code here:
        txtConfirmPass3.setText(txtConfirmPass3.getText().trim());
        if(txtConfirmPass3.getText().isEmpty()){
            txtConfirmPass3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtConfirmPass3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtConfirmPass3FocusLost

    private void btReset3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReset3ActionPerformed
        // TODO add your handling code here:
        myAccount = accController.getAccountByEmail(myEmail);
        resetAccountInfo();
    }//GEN-LAST:event_btReset3ActionPerformed

    private void txtFullName3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFullName3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFullName3ActionPerformed

    private void btSaveInfo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveInfo3ActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to save these changes?", 
                "Confirm Save Information", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(choice == 0){
            myAccount.setFullName(txtFullName3.getText().toUpperCase());
            String date = fieldDay3.getSelectedItem().toString() + "/"
                    + fieldMonth3.getSelectedItem().toString() + "/"
                    + fieldYear3.getSelectedItem().toString();
            myAccount.setBirthDay(LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy")));  
            if(btMale3.isSelected()) myAccount.setGender("Male");
            else myAccount.setGender("Female");
            
            accController.updateObjectInfor(myAccount);
            resetAccountInfo();
        }
        
    }//GEN-LAST:event_btSaveInfo3ActionPerformed

    private void btChangePass3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangePass3ActionPerformed
        // TODO add your handling code here:
        lblWarnSavePass3.setText(""); 
        String oldPass = myAccount.getPassword();
        String newPass = txtPassword3.getText().trim();
        String confirmNewPass = txtConfirmPass3.getText().trim();
        if(newPass.isEmpty() || confirmNewPass.isEmpty()) return;
        if(newPass.length() < 6){
            lblWarnSavePass3.setText("Password must be at least 6 characters long");
            return;
        }
        if(newPass.equals(oldPass)){
            lblWarnSavePass3.setText("New password cannot be the same as the old password");
            return;
        }
        if(!newPass.equals(confirmNewPass)){
            lblWarnSavePass3.setText("Password does not match");
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to change password?", 
                "Confirm Save Password", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(choice == 0){
            if(new OTPDialog(this, myEmail, "xác nhận đổi mật khẩu").isMatch()){
                myAccount.setPassword(newPass); 
                accController.updateObjectPass(myAccount);
                JOptionPane.showMessageDialog(this, "Password changed successfully", "", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }//GEN-LAST:event_btChangePass3ActionPerformed

    private void btHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHomeActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "Home");
    }//GEN-LAST:event_btHomeActionPerformed

    private void btSetingPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetingPFActionPerformed
        // TODO add your handling code here:
        resetAccountInfo();
        cardLayout3.show(pnlCard, "Profile");
    }//GEN-LAST:event_btSetingPFActionPerformed

    private void btSetingPFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSetingPFMouseExited
        // TODO add your handling code here:
        btSetingPF.setBackground(new Color(153,255,255));
    }//GEN-LAST:event_btSetingPFMouseExited

    private void btSetingPFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSetingPFMouseEntered
        // TODO add your handling code here:
        btSetingPF.setBackground(new Color(51,255,255));
    }//GEN-LAST:event_btSetingPFMouseEntered

    private void txtTransAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransAmountActionPerformed

    private void btTransfer3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTransfer3ActionPerformed
        // TODO add your handling code here:
        if(txtTransTo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter the account number!", 
                    "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Account toAccount = accController.getAccountByID(txtTransTo.getText(), "Customer");
        if(toAccount == null || toAccount.getId().equals(myAccount.getId())){ 
            JOptionPane.showMessageDialog(this, "Account is not valid!", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(txtTransAmount.getValue() == null || Long.valueOf(txtTransAmount.getValue().toString()) < 1000){
            JOptionPane.showMessageDialog(this, "Amount is not valid!", 
                    "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long amount = Long.valueOf(txtTransAmount.getValue().toString());
        if(amount > myAccount.getBalace()){
            JOptionPane.showMessageDialog(this, "Your account balance is too low for this transaction!", 
                    "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(txtTransDescription.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter the transfer description!", 
                    "", JOptionPane.WARNING_MESSAGE);
        }
        String passInput = JOptionPane.showInputDialog(this,
                                    "Please enter your password to confirm the transfer",
                                                    "", JOptionPane.QUESTION_MESSAGE);
        if(passInput == null || !passInput.equals(myAccount.getPassword())){
            JOptionPane.showMessageDialog(this, "Your password is incorrect!", 
                    "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = transController.Transfer(myAccount.getId(), toAccount.getId(), 
                amount, "TRANSFER", txtTransDescription.getText());
        if(result == 1){
            
            JOptionPane.showMessageDialog(this, "thanh cong", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(result == -1){
            JOptionPane.showMessageDialog(this, "dang xu li", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(this, "that bai", "", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btTransfer3ActionPerformed

    private void txtTransToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTransToFocusLost
        // TODO add your handling code here:
        Account toAccount = accController.getAccountByID(txtTransTo.getText(), "Customer");
        if(toAccount != null){
            lblToName.setText("  " + toAccount.getFullName());
        }       
    }//GEN-LAST:event_txtTransToFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        String email = "nhanprovip37@gmail.com";
        
        java.awt.EventQueue.invokeLater(() -> new FormCustomer(email).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GenderGroup3;
    private javax.swing.JButton btChangePass3;
    private javax.swing.JButton btDeposit;
    private javax.swing.JButton btEyePass3;
    private javax.swing.JRadioButton btFemale3;
    private javax.swing.JButton btHistory;
    private javax.swing.JButton btHome;
    private javax.swing.JButton btLogout;
    private javax.swing.JRadioButton btMale3;
    private javax.swing.JButton btReset3;
    private javax.swing.JButton btSATrans1;
    private javax.swing.JButton btSATrans2;
    private javax.swing.JButton btSATrans3;
    private javax.swing.JButton btSaveInfo3;
    private javax.swing.JButton btSetingPF;
    private javax.swing.JButton btStatics;
    private javax.swing.JButton btTrans;
    private javax.swing.JButton btTransfer3;
    private javax.swing.JButton btWithDraw;
    private javax.swing.JComboBox<String> fieldDay3;
    private javax.swing.JComboBox<String> fieldMonth3;
    private javax.swing.JComboBox<String> fieldYear3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblAccNumber3;
    private javax.swing.JLabel lblBalance3;
    private javax.swing.JLabel lblEmail3;
    private javax.swing.JLabel lblFullName3;
    private javax.swing.JLabel lblLineCol1;
    private javax.swing.JLabel lblLineCol2;
    private javax.swing.JLabel lblLineRow1;
    private javax.swing.JLabel lblLineRow2;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblToName;
    private javax.swing.JLabel lblWarnSavePass3;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlCardHistory;
    private javax.swing.JPanel pnlCardHome;
    private javax.swing.JPanel pnlCardProfile;
    private javax.swing.JPanel pnlCardTransfer;
    private javax.swing.JPanel pnlLogout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlSubProfile1;
    private javax.swing.JPanel pnlSubProfile2;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPasswordField txtConfirmPass3;
    private javax.swing.JTextField txtFullName3;
    private javax.swing.JPasswordField txtPassword3;
    private javax.swing.JFormattedTextField txtTransAmount;
    private javax.swing.JTextField txtTransDescription;
    private javax.swing.JFormattedTextField txtTransTo;
    // End of variables declaration//GEN-END:variables
}
