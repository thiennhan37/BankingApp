/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Control.AccountControl;
import Control.StaffControl;
import Control.TransactionControl;
import Model.Account;
import Model.Transaction;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Hi
 */
public class FormStaff extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormStaff.class.getName());

    /**
     * Creates new form FormCustomer
     */
    private TransactionControl transController;
    private AccountControl accController;
    private StaffControl stfController;
    private Account myAccount;
    private String myEmail;
    private CardLayout cardLayout3;
    private boolean isOpenEye3 = true;
    private ImageIcon openEyeImage = new javax.swing.ImageIcon(getClass().getResource("/MyImage/seenEye.png"));
    private ImageIcon closeEyeImage = new javax.swing.ImageIcon(getClass().getResource("/MyImage/hideEye.png"));
    private ImageIcon logoutStatic = new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutStatic.png"));
    private ImageIcon logoutDynamic = new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutDynamic1.gif"));
    private DefaultTableModel historyTableModel;
    private NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));;
    private Color color1 = new Color(255,255,255), 
            color2 = new Color(171, 245, 232),
            color3 = new Color(89, 222, 198);
    public FormStaff(String myEmail) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.myEmail = myEmail;
        accController = new AccountControl(this);
        transController = new TransactionControl();
        stfController = new StaffControl();
        initComponents();
        
        myAccount = accController.getAccountByEmail(myEmail);
        resetAccountInfo();
        txtPassword3.setEchoChar((char) 0); 
        cardLayout3 = (CardLayout) pnlCard.getLayout();
        historyTableModel = (DefaultTableModel) tblAuthorize.getModel();
        
        settingGUIComponent();
        setMouseList(); 

        this.setResizable(false);
        this.setLocationRelativeTo(null);
         
    } 
    private void settingGUIComponent(){
        JComponent[] btArr = {btSaveInfo3, btChangePass3};
        for(JComponent x : btArr){
           x.putClientProperty(FlatClientProperties.STYLE,"arc:25;");
        }
        
        lblEmail3.putClientProperty("FlatLaf.style", "arc:20; background:#F0F8FF;");
        
        JComponent[] fieldTime = {fieldDay3, fieldMonth3, fieldYear3, fieldBeginMonth, fieldBeginYear,
                    fieldEndMonth, fieldEndYear, fieldType};
        for(JComponent x : fieldTime){
           x.putClientProperty("FlatLaf.style", "borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        
        
        JComponent[] arr = {txtFullName3, txtPassword3, txtConfirmPass3};
        for(JComponent x : arr){
           x.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        
//        tblHistory.putClientProperty("FlatLaf.style", ""
//            + "rowHeight: 28;"
//            + "showHorizontalLines:true;"
//            + "showVerticalLines:true;"
//            + "selectionBackground:#E0F7FA;"
//            + "selectionForeground:#004D40;"
//            + "gridColor:#B0BEC5;"
//            + "font: 14 Roboto;"
//        );
        JTableHeader header = tblAuthorize.getTableHeader();
        header.putClientProperty("FlatLaf.style", ""
                + "background: #99FFFF;"
                + "foreground: #37474F;"

        );
        TableColumnModel columnModel = tblAuthorize.getColumnModel();
        columnModel.getColumn(5).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(70);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(40);
        columnModel.getColumn(0).setPreferredWidth(40);



        header.setReorderingAllowed(false); // không cho kéo đổi thứ tự cột
        header.setResizingAllowed(true); // có thể cho resize nếu muốn
        
    }   
    
    private void setMouseList(){
        JButton[] arr = {btHome, btAuthorize, btFind, btMoney, btProfile, btStatics, btLogout};
        
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
        if(myAccount.getType().equals("Staff")) lblBranch.setText(myAccount.getBranch());
        lblFullName3.setText(myAccount.getFullName());
        lblAccNumber3.setText(myAccount.getId());
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
    private void resetAccount(){
        myAccount = accController.getAccountByEmail(myEmail);
        resetAccountInfo();
    }
    private void loadHistoryTable(){
        
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
        NumberFormat numFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
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
                    txtFormat.getText().replace(".", "").trim().length() <= 1) {
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
                        String text = txtFormat.getText().replace(".", "").trim();

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
                    
                    String text = x.getText().replace(".", "").trim();
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
        jLabel19 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlTop = new javax.swing.JPanel();
        btReset3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblAccNumber3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblBranch = new javax.swing.JLabel();
        lblFullName3 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btHome = new javax.swing.JButton();
        btProfile = new javax.swing.JButton();
        btFind = new javax.swing.JButton();
        btMoney = new javax.swing.JButton();
        btAuthorize = new javax.swing.JButton();
        btStatics = new javax.swing.JButton();
        lblProfile = new javax.swing.JLabel();
        lblLineRow1 = new javax.swing.JLabel();
        pnlLogout = new javax.swing.JPanel();
        btLogout = new javax.swing.JButton();
        lblLineCol2 = new javax.swing.JLabel();
        lblLineCol1 = new javax.swing.JLabel();
        lblLineRow2 = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        pnlCardStatics = new javax.swing.JPanel();
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
        pnlCardMoney = new javax.swing.JPanel();
        pnlCardFind = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHistory1 = new javax.swing.JTable();
        fieldEndYear1 = new javax.swing.JComboBox<>();
        fieldEndMonth1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        fieldBeginYear1 = new javax.swing.JComboBox<>();
        fieldBeginMonth1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        fieldType1 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        btFindHistory1 = new javax.swing.JButton();
        fieldStatus1 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        pnlCardAuthorize = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAuthorize = new javax.swing.JTable();
        fieldEndYear = new javax.swing.JComboBox<>();
        fieldEndMonth = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        fieldBeginYear = new javax.swing.JComboBox<>();
        fieldBeginMonth = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        fieldType = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        btFindHistory = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSenderID = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtReceiverID = new javax.swing.JFormattedTextField();
        btConfirm = new javax.swing.JButton();
        btReject = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        jLabel19.setText("jLabel19");

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
        lblAccNumber3.setText("STF999");
        lblAccNumber3.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Branch");

        lblBranch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBranch.setForeground(new java.awt.Color(255, 255, 255));
        lblBranch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBranch.setText("Quang Ngai Province");

        lblFullName3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFullName3.setForeground(new java.awt.Color(153, 153, 153));
        lblFullName3.setText("THON TU SON");

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAccNumber3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblFullName3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAccNumber3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBranch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

        btProfile.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btProfile.setForeground(new java.awt.Color(51, 51, 51));
        btProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/Information.png"))); // NOI18N
        btProfile.setText("  Profile");
        btProfile.setBorderPainted(false);
        btProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProfileActionPerformed(evt);
            }
        });
        pnlMenu.add(btProfile);

        btFind.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFind.setForeground(new java.awt.Color(51, 51, 51));
        btFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/transfer.png"))); // NOI18N
        btFind.setText("Find");
        btFind.setBorderPainted(false);
        btFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindActionPerformed(evt);
            }
        });
        pnlMenu.add(btFind);

        btMoney.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMoney.setForeground(new java.awt.Color(51, 51, 51));
        btMoney.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/money.png"))); // NOI18N
        btMoney.setText("  Money");
        btMoney.setBorderPainted(false);
        btMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMoneyActionPerformed(evt);
            }
        });
        pnlMenu.add(btMoney);

        btAuthorize.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAuthorize.setForeground(new java.awt.Color(51, 51, 51));
        btAuthorize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/bill.png"))); // NOI18N
        btAuthorize.setText("  Authorize");
        btAuthorize.setBorderPainted(false);
        btAuthorize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btAuthorizeFocusLost(evt);
            }
        });
        btAuthorize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAuthorizeActionPerformed(evt);
            }
        });
        pnlMenu.add(btAuthorize);

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

        javax.swing.GroupLayout pnlCardStaticsLayout = new javax.swing.GroupLayout(pnlCardStatics);
        pnlCardStatics.setLayout(pnlCardStaticsLayout);
        pnlCardStaticsLayout.setHorizontalGroup(
            pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );
        pnlCardStaticsLayout.setVerticalGroup(
            pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardStatics, "card7");

        pnlCardHome.setAlignmentX(0.0F);

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
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
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

        javax.swing.GroupLayout pnlCardMoneyLayout = new javax.swing.GroupLayout(pnlCardMoney);
        pnlCardMoney.setLayout(pnlCardMoneyLayout);
        pnlCardMoneyLayout.setHorizontalGroup(
            pnlCardMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );
        pnlCardMoneyLayout.setVerticalGroup(
            pnlCardMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardMoney, "cardMoney");

        tblHistory1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Amount", "Type", "Status", "Time", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHistory1.setGridColor(new java.awt.Color(102, 102, 102));
        tblHistory1.setRowHeight(30);
        tblHistory1.setSelectionBackground(new java.awt.Color(153, 255, 153));
        tblHistory1.setSelectionForeground(new java.awt.Color(0, 0, 153));
        tblHistory1.setShowGrid(true);
        jScrollPane2.setViewportView(tblHistory1);

        fieldEndYear1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldEndYear1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndYear1FocusLost(evt);
            }
        });

        fieldEndMonth1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldEndMonth1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndMonth1FocusLost(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(178, 137, 145));
        jLabel27.setText("End");

        fieldBeginYear1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldBeginYear1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginYear1FocusLost(evt);
            }
        });

        fieldBeginMonth1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldBeginMonth1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginMonth1FocusLost(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(178, 137, 145));
        jLabel28.setText("Begin");

        fieldType1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "TRANSFER", "WITHDRAW", "DEPOSIT" }));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(178, 137, 145));
        jLabel29.setText("Type");

        btFindHistory1.setBackground(new java.awt.Color(153, 255, 255));
        btFindHistory1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btFindHistory1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/Find.png"))); // NOI18N
        btFindHistory1.setText(" Find");
        btFindHistory1.setFocusable(false);
        btFindHistory1.setOpaque(true);
        btFindHistory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindHistory1ActionPerformed(evt);
            }
        });

        fieldStatus1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "SUCCESSFUL", "PENDING", "FAILED" }));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(178, 137, 145));
        jLabel30.setText("Status");

        javax.swing.GroupLayout pnlCardFindLayout = new javax.swing.GroupLayout(pnlCardFind);
        pnlCardFind.setLayout(pnlCardFindLayout);
        pnlCardFindLayout.setHorizontalGroup(
            pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardFindLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(pnlCardFindLayout.createSequentialGroup()
                        .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCardFindLayout.createSequentialGroup()
                                .addComponent(fieldBeginMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldBeginYear1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCardFindLayout.createSequentialGroup()
                                .addComponent(fieldEndMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldEndYear1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldType1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardFindLayout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlCardFindLayout.createSequentialGroup()
                                .addComponent(fieldStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btFindHistory1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnlCardFindLayout.setVerticalGroup(
            pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardFindLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldEndYear1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldEndMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBeginYear1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBeginMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldType1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btFindHistory1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        fieldYear3.setSelectedIndex(105);
        fieldYear3.setSelectedIndex(105);

        pnlCard.add(pnlCardFind, "Find");

        tblAuthorize.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TransID", "SenderID", "ReceiverID", "Amount", "Type", "Status", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAuthorize.setGridColor(new java.awt.Color(102, 102, 102));
        tblAuthorize.setRowHeight(30);
        tblAuthorize.setSelectionBackground(new java.awt.Color(153, 255, 153));
        tblAuthorize.setSelectionForeground(new java.awt.Color(0, 0, 153));
        tblAuthorize.setShowGrid(true);
        jScrollPane1.setViewportView(tblAuthorize);

        fieldEndYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldEndYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndYearFocusLost(evt);
            }
        });

        fieldEndMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldEndMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndMonthFocusLost(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(178, 137, 145));
        jLabel23.setText("End");

        fieldBeginYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldBeginYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginYearFocusLost(evt);
            }
        });

        fieldBeginMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldBeginMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginMonthFocusLost(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(178, 137, 145));
        jLabel24.setText("Begin");

        fieldType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "TRANSFER", "WITHDRAW", "DEPOSIT" }));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(178, 137, 145));
        jLabel25.setText("Type");

        btFindHistory.setBackground(new java.awt.Color(153, 255, 255));
        btFindHistory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btFindHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/Find.png"))); // NOI18N
        btFindHistory.setText("Find");
        btFindHistory.setFocusable(false);
        btFindHistory.setOpaque(true);
        btFindHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindHistoryActionPerformed(evt);
            }
        });

        jLabel5.setText("SenderID");

        jLabel15.setText("ReceiverID");

        btConfirm.setBackground(new java.awt.Color(153, 255, 255));
        btConfirm.setText("Confirm");
        btConfirm.setToolTipText("");
        btConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmActionPerformed(evt);
            }
        });

        btReject.setBackground(new java.awt.Color(153, 255, 255));
        btReject.setText("Reject");

        javax.swing.GroupLayout pnlCardAuthorizeLayout = new javax.swing.GroupLayout(pnlCardAuthorize);
        pnlCardAuthorize.setLayout(pnlCardAuthorizeLayout);
        pnlCardAuthorizeLayout.setHorizontalGroup(
            pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                        .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addComponent(fieldBeginMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldBeginYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addComponent(fieldEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtReceiverID, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSenderID))
                                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                                .addComponent(fieldType, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(btFindHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(btConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btReject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        pnlCardAuthorizeLayout.setVerticalGroup(
            pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardAuthorizeLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldBeginYear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBeginMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btFindHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenderID, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btReject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtReceiverID)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        fieldYear3.setSelectedIndex(105);
        fieldYear3.setSelectedIndex(105);

        pnlCard.add(pnlCardAuthorize, "Authorize");

        jLabel6.setBackground(new java.awt.Color(153, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("9999@gmail.com");
        jLabel6.setOpaque(true);

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLineRow2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLineCol1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLineCol2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblLineRow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLineCol1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)
                        .addComponent(lblLineRow1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void btAuthorizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAuthorizeActionPerformed
        // TODO add your handling code here:
        LocalDate now = LocalDate.now();
        fieldBeginMonth.setSelectedIndex(0); fieldBeginYear.setSelectedIndex(2005 - 1900);
        fieldEndMonth.setSelectedIndex(now.getMonthValue() - 1); fieldEndYear.setSelectedIndex(now.getYear() - 1900);
        
        cardLayout3.show(pnlCard, "Authorize");
    }//GEN-LAST:event_btAuthorizeActionPerformed

    private void btFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "Find");
    }//GEN-LAST:event_btFindActionPerformed

    private void btMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMoneyActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "cardMoney");
    }//GEN-LAST:event_btMoneyActionPerformed

    private void btAuthorizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btAuthorizeFocusLost
        // TODO add your handling code here:
        btAuthorize.setBackground(color1); 
    }//GEN-LAST:event_btAuthorizeFocusLost

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
        resetAccount();
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

    private void btProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProfileActionPerformed
        // TODO add your handling code here:
        resetAccountInfo();
        cardLayout3.show(pnlCard, "Profile");
    }//GEN-LAST:event_btProfileActionPerformed

    private void fieldBeginMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginMonthFocusLost

    private void fieldBeginYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginYearFocusLost

    private void fieldEndYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndYearFocusLost

    private void fieldEndMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndMonthFocusLost

    private void btFindHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindHistoryActionPerformed
        // TODO add your handling code here:
        historyTableModel.setRowCount(0); 
        String senderID = null, receiverID = null;
        if(!txtSenderID.getText().isBlank()) senderID = txtSenderID.getText().trim();
        if(!txtReceiverID.getText().isBlank())  receiverID = txtReceiverID.getText().trim();
//        if(senderID == null) System.out.println(1);
//        if(receiverID == null) System.out.println(2);
        List<Transaction> lst = stfController.filterTransForAuthorize(senderID, receiverID,
                fieldBeginMonth.getSelectedIndex() + 1, fieldBeginYear.getSelectedIndex() + 1900,
                fieldEndMonth.getSelectedIndex() + 1, fieldEndYear.getSelectedIndex() + 1900, 
                fieldType.getSelectedItem().toString());
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm");
                
        for(Transaction x : lst){
            LocalDateTime time = x.getSendTime();
            String timeShow = null;
            if(time != null) timeShow = time.format(fm);
            historyTableModel.addRow(new Object[]{x.getTransID(), x.getSenderID(), x.getReceiverID(), numberFormat.format(x.getAmount()), 
                x.getType(), x.getStatus(), timeShow}); 
        }
    }//GEN-LAST:event_btFindHistoryActionPerformed

    private void fieldEndYear1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndYear1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndYear1FocusLost

    private void fieldEndMonth1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndMonth1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndMonth1FocusLost

    private void fieldBeginYear1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginYear1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginYear1FocusLost

    private void fieldBeginMonth1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginMonth1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginMonth1FocusLost

    private void btFindHistory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindHistory1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btFindHistory1ActionPerformed

    private void btConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblAuthorize.getSelectedRow();
        if(selectedRow == -1) return;
        String transID = tblAuthorize.getValueAt(selectedRow, 0).toString();
        String senderID = null;
        if(tblAuthorize.getValueAt(selectedRow, 1) != null) senderID = tblAuthorize.getValueAt(selectedRow, 1).toString();
        String receiverID = tblAuthorize.getValueAt(selectedRow, 2).toString();
        Long amount = Long.valueOf(tblAuthorize.getValueAt(selectedRow, 3).toString().replace(".", ""));
        String type = tblAuthorize.getValueAt(selectedRow, 4).toString();
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to approve this transaction - " + transID + "?", 
                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(stfController.confirmTransaction(transID, senderID, receiverID, type, amount)){
                JOptionPane.showMessageDialog(this, "Confirm Successfully!", "", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Confirm Failed!", "", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btConfirmActionPerformed

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
        String email = "thiennhan11a1@gmail.com";
        
        java.awt.EventQueue.invokeLater(() -> new FormStaff(email).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GenderGroup3;
    private javax.swing.JButton btAuthorize;
    private javax.swing.JButton btChangePass3;
    private javax.swing.JButton btConfirm;
    private javax.swing.JButton btEyePass3;
    private javax.swing.JRadioButton btFemale3;
    private javax.swing.JButton btFind;
    private javax.swing.JButton btFindHistory;
    private javax.swing.JButton btFindHistory1;
    private javax.swing.JButton btHome;
    private javax.swing.JButton btLogout;
    private javax.swing.JRadioButton btMale3;
    private javax.swing.JButton btMoney;
    private javax.swing.JButton btProfile;
    private javax.swing.JButton btReject;
    private javax.swing.JButton btReset3;
    private javax.swing.JButton btSaveInfo3;
    private javax.swing.JButton btStatics;
    private javax.swing.JComboBox<String> fieldBeginMonth;
    private javax.swing.JComboBox<String> fieldBeginMonth1;
    private javax.swing.JComboBox<String> fieldBeginYear;
    private javax.swing.JComboBox<String> fieldBeginYear1;
    private javax.swing.JComboBox<String> fieldDay3;
    private javax.swing.JComboBox<String> fieldEndMonth;
    private javax.swing.JComboBox<String> fieldEndMonth1;
    private javax.swing.JComboBox<String> fieldEndYear;
    private javax.swing.JComboBox<String> fieldEndYear1;
    private javax.swing.JComboBox<String> fieldMonth3;
    private javax.swing.JComboBox<String> fieldStatus1;
    private javax.swing.JComboBox<String> fieldType;
    private javax.swing.JComboBox<String> fieldType1;
    private javax.swing.JComboBox<String> fieldYear3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAccNumber3;
    private javax.swing.JLabel lblBranch;
    private javax.swing.JLabel lblEmail3;
    private javax.swing.JLabel lblFullName3;
    private javax.swing.JLabel lblLineCol1;
    private javax.swing.JLabel lblLineCol2;
    private javax.swing.JLabel lblLineRow1;
    private javax.swing.JLabel lblLineRow2;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblWarnSavePass3;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlCardAuthorize;
    private javax.swing.JPanel pnlCardFind;
    private javax.swing.JPanel pnlCardHome;
    private javax.swing.JPanel pnlCardMoney;
    private javax.swing.JPanel pnlCardProfile;
    private javax.swing.JPanel pnlCardStatics;
    private javax.swing.JPanel pnlLogout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlSubProfile1;
    private javax.swing.JPanel pnlSubProfile2;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tblAuthorize;
    private javax.swing.JTable tblHistory1;
    private javax.swing.JPasswordField txtConfirmPass3;
    private javax.swing.JTextField txtFullName3;
    private javax.swing.JPasswordField txtPassword3;
    private javax.swing.JFormattedTextField txtReceiverID;
    private javax.swing.JFormattedTextField txtSenderID;
    // End of variables declaration//GEN-END:variables
}
