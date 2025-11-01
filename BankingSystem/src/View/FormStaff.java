/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Control.AccountControl;
import Control.StaffControl;
import Control.TransactionControl;
import DAO.TransactionDAO;
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
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.Collections;
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
    private ImageIcon FemaleAdmin = new javax.swing.ImageIcon(getClass().getResource("/MyImage/FemaleAdmin.png"));
    private DefaultTableModel authorizeTableModel, searchCusTBMD, searchTransTBMD;
    private NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));;
    private DateTimeFormatter fm2Y = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    private DateTimeFormatter fm4Y = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private DateTimeFormatter fmForBirth = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
        resetAccount();
        if(myAccount.getGender().equals("Female")) lblProfile.setIcon(FemaleAdmin);
        txtPassword3.setEchoChar((char) 0); 
        cardLayout3 = (CardLayout) pnlCard.getLayout();
        authorizeTableModel = (DefaultTableModel) tblAuthorize.getModel();
        searchCusTBMD = (DefaultTableModel) tblCustomerS.getModel();
        searchTransTBMD = (DefaultTableModel) tblTransactionS.getModel();
        settingGUIComponent();
        setMouseList(); 
        setRestrict();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    } 
    private void settingGUIComponent(){
        JComponent[] btArr = {btSaveInfo3, btChangePass3};
        for(JComponent x : btArr){
           x.putClientProperty(FlatClientProperties.STYLE,"arc:25;");
        }
        
        lblEmail3.putClientProperty("FlatLaf.style", "arc:20; background:#F0F8FF;");
        
        JComponent[] fieldTime = {fieldDay3, fieldMonth3, fieldYear3, fieldBeginMonthA, fieldBeginYearA,
                    fieldEndMonthA, fieldEndYearA, fieldTypeA, txtSenderID, txtReceiverID,fieldTypeS, fieldStatusS,
                     fieldBeginMonthS, fieldBeginYearS, fieldEndMonthS, fieldEndYearS,txtCustomerID};
        for(JComponent x : fieldTime){
           x.putClientProperty("FlatLaf.style", "borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        
        
        JComponent[] txtArr = {txtFullName3, txtPassword3, txtConfirmPass3};
        for(JComponent x : txtArr){
           x.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }

        JTable[] tblArr = {tblAuthorize, tblCustomerS, tblTransactionS};
        for(JTable x : tblArr){
            JTableHeader header = x.getTableHeader();
            header.putClientProperty("FlatLaf.style", ""
                    + "background: #99FFFF;"
                    + "foreground: #37474F;"

            );
            header.setReorderingAllowed(false); // không cho kéo đổi thứ tự cột
            header.setResizingAllowed(true); // có thể cho resize nếu muốn
        }
        
        TableColumnModel columnModel = tblAuthorize.getColumnModel();
        columnModel.getColumn(5).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(70);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(40);
        columnModel.getColumn(0).setPreferredWidth(40);
        
        TableColumnModel customerCLMD = tblCustomerS.getColumnModel();
        customerCLMD.getColumn(0).setPreferredWidth(150);
        customerCLMD.getColumn(1).setPreferredWidth(180);
        
        TableColumnModel transCLMD = tblTransactionS.getColumnModel();
        transCLMD.getColumn(0).setPreferredWidth(60);
        transCLMD.getColumn(1).setPreferredWidth(60);
        transCLMD.getColumn(2).setPreferredWidth(60);
        transCLMD.getColumn(3).setPreferredWidth(90);
        transCLMD.getColumn(6).setPreferredWidth(90);
        
    }   
    private void setRestrict(){
        JFormattedTextField[] arr = {txtSenderID, txtReceiverID, txtCustomerID};
        for(JFormattedTextField x : arr){
            setTextForNumber(x);
        }
    } 
            
    private void setMouseList(){
        JButton[] arr = {btHome, btAuthorize, btSearch, btMoney, btProfile, btStatics, btLogout};
        
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
        if(!myAccount.getType().equals("Customer")) lblBranch.setText(myAccount.getBranch());
        lblFullName3.setText(myAccount.getFullName());
        lblPositon.setText(myAccount.getType().toUpperCase());
        lblMemberID.setText("ID: " + myAccount.getId()); 
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
        
        LocalDate now = LocalDate.now();
        fieldBeginMonthA.setSelectedIndex(0); fieldBeginYearA.setSelectedIndex(2005 - 1900);
        fieldEndMonthA.setSelectedIndex(now.getMonthValue() - 1); fieldEndYearA.setSelectedIndex(now.getYear() - 1900);
        fieldBeginMonthS.setSelectedIndex(0); fieldBeginYearS.setSelectedIndex(2005 - 1900);
        fieldEndMonthS.setSelectedIndex(now.getMonthValue() - 1); fieldEndYearS.setSelectedIndex(now.getYear() - 1900);
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
        lblPositon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblBranch = new javax.swing.JLabel();
        lblFullName3 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btHome = new javax.swing.JButton();
        btProfile = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
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
        pnlCardSearch = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTransactionS = new javax.swing.JTable();
        fieldEndYearS = new javax.swing.JComboBox<>();
        fieldEndMonthS = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        fieldBeginYearS = new javax.swing.JComboBox<>();
        fieldBeginMonthS = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        fieldTypeS = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        btFindS = new javax.swing.JButton();
        fieldStatusS = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCustomerS = new javax.swing.JTable();
        txtCustomerID = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        btActivate = new javax.swing.JButton();
        btDeactivate = new javax.swing.JButton();
        lblWarnS = new javax.swing.JLabel();
        pnlCardAuthorize = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAuthorize = new javax.swing.JTable();
        fieldEndYearA = new javax.swing.JComboBox<>();
        fieldEndMonthA = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        fieldBeginYearA = new javax.swing.JComboBox<>();
        fieldBeginMonthA = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        fieldTypeA = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        btFindA = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSenderID = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtReceiverID = new javax.swing.JFormattedTextField();
        btConfirm = new javax.swing.JButton();
        btReject = new javax.swing.JButton();
        pnlCardStatics = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblMemberID = new javax.swing.JLabel();
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
        jLabel1.setText("Position");

        lblPositon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPositon.setForeground(new java.awt.Color(255, 255, 255));
        lblPositon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPositon.setText("STAFF");
        lblPositon.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Branch");

        lblBranch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBranch.setForeground(new java.awt.Color(255, 255, 255));
        lblBranch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBranch.setText("Branch Name");

        lblFullName3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFullName3.setForeground(new java.awt.Color(153, 153, 153));
        lblFullName3.setText("STAFF NAME");

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFullName3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(33, 33, 33)
                                .addComponent(lblPositon, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
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
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblPositon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
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
        btProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/information.png"))); // NOI18N
        btProfile.setText("  Profile");
        btProfile.setBorderPainted(false);
        btProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProfileActionPerformed(evt);
            }
        });
        pnlMenu.add(btProfile);

        btSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btSearch.setForeground(new java.awt.Color(51, 51, 51));
        btSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/search.png"))); // NOI18N
        btSearch.setText("  Search");
        btSearch.setBorderPainted(false);
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });
        pnlMenu.add(btSearch);

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
        btAuthorize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/authorize.png"))); // NOI18N
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
        btStatics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/chart.png"))); // NOI18N
        btStatics.setText("  Statics  ");
        btStatics.setBorderPainted(false);
        btStatics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStaticsActionPerformed(evt);
            }
        });
        pnlMenu.add(btStatics);

        lblProfile.setBackground(new java.awt.Color(153, 255, 255));
        lblProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/MaleAdmin.png"))); // NOI18N
        lblProfile.setOpaque(true);

        lblLineRow1.setBackground(new java.awt.Color(0, 0, 0));
        lblLineRow1.setOpaque(true);

        pnlLogout.setBackground(new java.awt.Color(255, 255, 255));

        btLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutStatic.png"))); // NOI18N
        btLogout.setText("  Logout");
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
            .addGroup(pnlLogoutLayout.createSequentialGroup()
                .addComponent(btLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLogoutLayout.setVerticalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoutLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btLogout)
                .addGap(43, 43, 43))
        );

        lblLineCol2.setBackground(new java.awt.Color(0, 0, 0));
        lblLineCol2.setOpaque(true);

        lblLineCol1.setBackground(new java.awt.Color(0, 0, 0));
        lblLineCol1.setOpaque(true);

        lblLineRow2.setBackground(new java.awt.Color(0, 0, 0));
        lblLineRow2.setOpaque(true);

        pnlCard.setLayout(new java.awt.CardLayout());

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCardProfileLayout.setVerticalGroup(
            pnlCardProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardProfileLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlSubProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSubProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pnlCard.add(pnlCardProfile, "Profile");

        javax.swing.GroupLayout pnlCardMoneyLayout = new javax.swing.GroupLayout(pnlCardMoney);
        pnlCardMoney.setLayout(pnlCardMoneyLayout);
        pnlCardMoneyLayout.setHorizontalGroup(
            pnlCardMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );
        pnlCardMoneyLayout.setVerticalGroup(
            pnlCardMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardMoney, "cardMoney");

        tblTransactionS.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTransactionS.setGridColor(new java.awt.Color(102, 102, 102));
        tblTransactionS.setRowHeight(30);
        tblTransactionS.setSelectionBackground(new java.awt.Color(153, 255, 153));
        tblTransactionS.setSelectionForeground(new java.awt.Color(0, 0, 153));
        tblTransactionS.setShowGrid(true);
        jScrollPane2.setViewportView(tblTransactionS);

        fieldEndYearS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldEndYearS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndYearSFocusLost(evt);
            }
        });

        fieldEndMonthS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldEndMonthS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndMonthSFocusLost(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(178, 137, 145));
        jLabel27.setText("End");

        fieldBeginYearS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldBeginYearS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginYearSFocusLost(evt);
            }
        });

        fieldBeginMonthS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldBeginMonthS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginMonthSFocusLost(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(178, 137, 145));
        jLabel28.setText("Begin");

        fieldTypeS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "TRANSFER", "WITHDRAW", "DEPOSIT" }));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(178, 137, 145));
        jLabel29.setText("Type");

        btFindS.setBackground(new java.awt.Color(153, 255, 255));
        btFindS.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btFindS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/Find.png"))); // NOI18N
        btFindS.setText(" Find");
        btFindS.setFocusPainted(false);
        btFindS.setFocusable(false);
        btFindS.setOpaque(true);
        btFindS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindSActionPerformed(evt);
            }
        });

        fieldStatusS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "SUCCESSFUL", "PENDING", "FAILED" }));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(178, 137, 145));
        jLabel30.setText("Status");

        tblCustomerS.setBackground(new java.awt.Color(204, 204, 204));
        tblCustomerS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FullName", "Email", "Gender", "BirthDay", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCustomerS.setEnabled(false);
        tblCustomerS.setRowHeight(30);
        tblCustomerS.setShowGrid(true);
        jScrollPane4.setViewportView(tblCustomerS);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(178, 137, 145));
        jLabel16.setText("CustomerID");

        btActivate.setBackground(new java.awt.Color(51, 255, 51));
        btActivate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btActivate.setText("Activate");
        btActivate.setFocusPainted(false);
        btActivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btActivateActionPerformed(evt);
            }
        });

        btDeactivate.setBackground(new java.awt.Color(255, 51, 51));
        btDeactivate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDeactivate.setText("Deactivate");
        btDeactivate.setFocusPainted(false);
        btDeactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeactivateActionPerformed(evt);
            }
        });

        lblWarnS.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout pnlCardSearchLayout = new javax.swing.GroupLayout(pnlCardSearch);
        pnlCardSearch.setLayout(pnlCardSearchLayout);
        pnlCardSearchLayout.setHorizontalGroup(
            pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(pnlCardSearchLayout.createSequentialGroup()
                        .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCardSearchLayout.createSequentialGroup()
                                .addComponent(fieldBeginMonthS, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldBeginYearS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlCardSearchLayout.createSequentialGroup()
                                .addComponent(fieldEndMonthS, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldEndYearS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardSearchLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldStatusS, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlCardSearchLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardSearchLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldTypeS, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardSearchLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCustomerID)
                            .addComponent(btActivate, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(lblWarnS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btDeactivate, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(btFindS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        pnlCardSearchLayout.setVerticalGroup(
            pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardSearchLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldBeginYearS)
                    .addComponent(fieldTypeS)
                    .addGroup(pnlCardSearchLayout.createSequentialGroup()
                        .addComponent(fieldBeginMonthS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardSearchLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btFindS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCustomerID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30)
                    .addComponent(lblWarnS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldEndMonthS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCardSearchLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlCardSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btDeactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldEndYearS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldStatusS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btActivate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
        );

        fieldYear3.setSelectedIndex(105);
        fieldYear3.setSelectedIndex(105);

        pnlCard.add(pnlCardSearch, "Search");

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

        fieldEndYearA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldEndYearA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndYearAFocusLost(evt);
            }
        });

        fieldEndMonthA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldEndMonthA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldEndMonthAFocusLost(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(178, 137, 145));
        jLabel23.setText("End");

        fieldBeginYearA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldBeginYearA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginYearAFocusLost(evt);
            }
        });

        fieldBeginMonthA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldBeginMonthA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldBeginMonthAFocusLost(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(178, 137, 145));
        jLabel24.setText("Begin");

        fieldTypeA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "TRANSFER", "WITHDRAW", "DEPOSIT" }));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(178, 137, 145));
        jLabel25.setText("Type");

        btFindA.setBackground(new java.awt.Color(153, 255, 255));
        btFindA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFindA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/Find.png"))); // NOI18N
        btFindA.setText("Find");
        btFindA.setFocusPainted(false);
        btFindA.setOpaque(true);
        btFindA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindAActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(178, 137, 145));
        jLabel5.setText("SenderID");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(178, 137, 145));
        jLabel15.setText("ReceiverID");

        btConfirm.setBackground(new java.awt.Color(153, 255, 255));
        btConfirm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btConfirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/check_123.png"))); // NOI18N
        btConfirm.setText("Confirm");
        btConfirm.setToolTipText("");
        btConfirm.setFocusPainted(false);
        btConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmActionPerformed(evt);
            }
        });

        btReject.setBackground(new java.awt.Color(153, 255, 255));
        btReject.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btReject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/reject321.png"))); // NOI18N
        btReject.setText("Reject");
        btReject.setFocusPainted(false);
        btReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRejectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCardAuthorizeLayout = new javax.swing.GroupLayout(pnlCardAuthorize);
        pnlCardAuthorize.setLayout(pnlCardAuthorizeLayout);
        pnlCardAuthorizeLayout.setHorizontalGroup(
            pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                        .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addComponent(fieldBeginMonthA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldBeginYearA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addComponent(fieldEndMonthA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(fieldEndYearA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtSenderID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtReceiverID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldTypeA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btFindA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btReject, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16))
                            .addGroup(pnlCardAuthorizeLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                    .addComponent(fieldBeginYearA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBeginMonthA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldTypeA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenderID, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btFindA, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCardAuthorizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldEndMonthA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldEndYearA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btReject, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReceiverID))
                .addGap(18, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        fieldYear3.setSelectedIndex(105);
        fieldYear3.setSelectedIndex(105);

        pnlCard.add(pnlCardAuthorize, "Authorize");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout pnlCardStaticsLayout = new javax.swing.GroupLayout(pnlCardStatics);
        pnlCardStatics.setLayout(pnlCardStaticsLayout);
        pnlCardStaticsLayout.setHorizontalGroup(
            pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        pnlCardStaticsLayout.setVerticalGroup(
            pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );

        pnlCard.add(pnlCardStatics, "card7");

        lblMemberID.setBackground(new java.awt.Color(153, 255, 255));
        lblMemberID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMemberID.setForeground(new java.awt.Color(153, 153, 153));
        lblMemberID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMemberID.setText("ID: STF999");
        lblMemberID.setOpaque(true);

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblLineRow2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMemberID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLineCol1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLineRow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLineCol2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(lblMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLineCol1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addComponent(lblLineRow1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblLineCol2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblLineRow2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAuthorizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAuthorizeActionPerformed
        // TODO add your handling code here:
        // txtSenderID.setValue(null); txtReceiverID.setValue(null);
        btFindA.doClick();
        cardLayout3.show(pnlCard, "Authorize");
    }//GEN-LAST:event_btAuthorizeActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        
        
        cardLayout3.show(pnlCard, "Search");
    }//GEN-LAST:event_btSearchActionPerformed

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

    private void fieldBeginMonthAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginMonthAFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginMonthAFocusLost

    private void fieldBeginYearAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginYearAFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginYearAFocusLost

    private void fieldEndYearAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndYearAFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndYearAFocusLost

    private void fieldEndMonthAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndMonthAFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndMonthAFocusLost

    private void btFindAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindAActionPerformed
        // TODO add your handling code here:
        authorizeTableModel.setRowCount(0); 
        String senderID = null, receiverID = null;
        if(!txtSenderID.getText().isBlank()) senderID = txtSenderID.getText().trim();
        if(!txtReceiverID.getText().isBlank())  receiverID = txtReceiverID.getText().trim();
//        if(senderID == null) System.out.println(1);
//        if(receiverID == null) System.out.println(2);
        List<Transaction> lst = stfController.filterTransForAuthorize(senderID, receiverID,
                fieldBeginMonthA.getSelectedIndex() + 1, fieldBeginYearA.getSelectedIndex() + 1900,
                fieldEndMonthA.getSelectedIndex() + 1, fieldEndYearA.getSelectedIndex() + 1900, 
                fieldTypeA.getSelectedItem().toString());
        
                
        for(Transaction x : lst){
            LocalDateTime time = x.getSendTime();
            String timeShow = null;
            if(time != null) timeShow = time.format(fm2Y);
            authorizeTableModel.addRow(new Object[]{x.getTransID(), x.getSenderID(), x.getReceiverID(), numberFormat.format(x.getAmount()), 
                x.getType(), x.getStatus(), timeShow}); 
        }
    }//GEN-LAST:event_btFindAActionPerformed

    private void fieldEndYearSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndYearSFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndYearSFocusLost

    private void fieldEndMonthSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldEndMonthSFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEndMonthSFocusLost

    private void fieldBeginYearSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginYearSFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginYearSFocusLost

    private void fieldBeginMonthSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldBeginMonthSFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBeginMonthSFocusLost

    private void btFindSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindSActionPerformed
        // TODO add your handling code here:
        searchCusTBMD.setRowCount(0);
        searchTransTBMD.setRowCount(0);
        Account customerSearch = accController.getAccountByID(txtCustomerID.getText());
        if(customerSearch == null){
            if(txtCustomerID.getText().isBlank()) lblWarnS.setText("Invalid CustomerID");
            else lblWarnS.setText("CustomerID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnS.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        
        searchCusTBMD.addRow(new Object[]{customerSearch.getFullName(), customerSearch.getEmail(), 
            customerSearch.getGender(), customerSearch.getBirthDay().format(fmForBirth), 
            String.valueOf(customerSearch.isActive()).toUpperCase()});
        
        List<Transaction> lst = transController.filterTransactions(txtCustomerID.getText(), 
                fieldBeginMonthS.getSelectedIndex() + 1, fieldBeginYearS.getSelectedIndex() + 1900,
                fieldEndMonthS.getSelectedIndex() + 1, fieldEndYearS.getSelectedIndex() + 1900, 
                fieldTypeS.getSelectedItem().toString(), fieldStatusS.getSelectedItem().toString());

        Collections.sort(lst);
        for(Transaction x : lst){
            String c;
            LocalDateTime time;
            if(x.getSenderID() == null){ c = "+"; time = x.getReceiveTime();}
            else if(x.getReceiverID() == null){ c = "-"; time = x.getSendTime();}
            else{
                if(x.getSenderID().equals(customerSearch.getId())){ c = "-"; time = x.getSendTime();}
                else{ c = "+"; time = x.getReceiveTime();}
            }
            if(!x.getStatus().equals("SUCCESSFUL")) c = "";
            String timeShow;
            if(time == null) timeShow = null;
            else timeShow = time.format(fm2Y);
            searchTransTBMD.addRow(new Object[]{x.getTransID(), x.getSenderID(), x.getReceiverID(), 
                c + numberFormat.format(x.getAmount()), 
                x.getType(), x.getStatus(), timeShow}); 
        }
    }//GEN-LAST:event_btFindSActionPerformed

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
        int choice = JOptionPane.showConfirmDialog(this, "CONFIRM this transaction - " + transID + "?", 
                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(stfController.confirmTransaction(transID, senderID, receiverID, type, amount)){
                JOptionPane.showMessageDialog(this, "Confirm Successfully!", "", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Confirm Failed!", "", JOptionPane.WARNING_MESSAGE);
            }
        }
        btFindA.doClick();
    }//GEN-LAST:event_btConfirmActionPerformed

    private void btRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRejectActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblAuthorize.getSelectedRow();
        if(selectedRow == -1) return;
        String transID = tblAuthorize.getValueAt(selectedRow, 0).toString();
        int choice = JOptionPane.showConfirmDialog(this, "REJECT this transaction - " + transID + "?", 
                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(TransactionDAO.getInstance().updateStatus(transID, "FAILED")){
                JOptionPane.showMessageDialog(this, "Reject Successfully!", "", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Reject Failed!", "", JOptionPane.WARNING_MESSAGE);
            }
        }
        btFindA.doClick();
    }//GEN-LAST:event_btRejectActionPerformed

    private void btStaticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStaticsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btStaticsActionPerformed

    private void btActivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btActivateActionPerformed
        // TODO add your handling code here:
        Account acc = accController.getAccountByID(txtCustomerID.getText());
        if(acc == null || acc.getDegree() >= myAccount.getDegree()){
            if(txtCustomerID.getText().isBlank()) lblWarnS.setText("Invalid CustomerID");
            else lblWarnS.setText("CustomerID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnS.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        btFindS.doClick();
        if(acc.isActive()) return;
        int choice = JOptionPane.showConfirmDialog(this, "Activate account " + acc.getId() + "?", 
                                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(accController.updateObjectActive(acc.getId(), true)){
                JOptionPane.showMessageDialog(this, "Activate account successfully!", 
                        "", JOptionPane.INFORMATION_MESSAGE);
                btFindS.doClick();
            }
            else{
                JOptionPane.showMessageDialog(this, "Activate account failed!", 
                        "", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_btActivateActionPerformed

    private void btDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeactivateActionPerformed
        // TODO add your handling code here:
        Account acc = accController.getAccountByID(txtCustomerID.getText());
        if(acc == null || acc.getDegree() >= myAccount.getDegree()){
            if(txtCustomerID.getText().isBlank()) lblWarnS.setText("Invalid CustomerID");
            else lblWarnS.setText("CustomerID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnS.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        btFindS.doClick();
        if(!acc.isActive()) return;
        int choice = JOptionPane.showConfirmDialog(this, "Deactivate account " + acc.getId() + "?", 
                                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(accController.updateObjectActive(acc.getId(), false)){
                JOptionPane.showMessageDialog(this, "Deactivate account successfully!", 
                        "", JOptionPane.INFORMATION_MESSAGE);
                btFindS.doClick();
            }
            else{
                JOptionPane.showMessageDialog(this, "Deactivate account failed!", 
                        "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btDeactivateActionPerformed

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
    private javax.swing.JButton btActivate;
    private javax.swing.JButton btAuthorize;
    private javax.swing.JButton btChangePass3;
    private javax.swing.JButton btConfirm;
    private javax.swing.JButton btDeactivate;
    private javax.swing.JButton btEyePass3;
    private javax.swing.JRadioButton btFemale3;
    private javax.swing.JButton btFindA;
    private javax.swing.JButton btFindS;
    private javax.swing.JButton btHome;
    private javax.swing.JButton btLogout;
    private javax.swing.JRadioButton btMale3;
    private javax.swing.JButton btMoney;
    private javax.swing.JButton btProfile;
    private javax.swing.JButton btReject;
    private javax.swing.JButton btReset3;
    private javax.swing.JButton btSaveInfo3;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btStatics;
    private javax.swing.JComboBox<String> fieldBeginMonthA;
    private javax.swing.JComboBox<String> fieldBeginMonthS;
    private javax.swing.JComboBox<String> fieldBeginYearA;
    private javax.swing.JComboBox<String> fieldBeginYearS;
    private javax.swing.JComboBox<String> fieldDay3;
    private javax.swing.JComboBox<String> fieldEndMonthA;
    private javax.swing.JComboBox<String> fieldEndMonthS;
    private javax.swing.JComboBox<String> fieldEndYearA;
    private javax.swing.JComboBox<String> fieldEndYearS;
    private javax.swing.JComboBox<String> fieldMonth3;
    private javax.swing.JComboBox<String> fieldStatusS;
    private javax.swing.JComboBox<String> fieldTypeA;
    private javax.swing.JComboBox<String> fieldTypeS;
    private javax.swing.JComboBox<String> fieldYear3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBranch;
    private javax.swing.JLabel lblEmail3;
    private javax.swing.JLabel lblFullName3;
    private javax.swing.JLabel lblLineCol1;
    private javax.swing.JLabel lblLineCol2;
    private javax.swing.JLabel lblLineRow1;
    private javax.swing.JLabel lblLineRow2;
    private javax.swing.JLabel lblMemberID;
    private javax.swing.JLabel lblPositon;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblWarnS;
    private javax.swing.JLabel lblWarnSavePass3;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlCardAuthorize;
    private javax.swing.JPanel pnlCardHome;
    private javax.swing.JPanel pnlCardMoney;
    private javax.swing.JPanel pnlCardProfile;
    private javax.swing.JPanel pnlCardSearch;
    private javax.swing.JPanel pnlCardStatics;
    private javax.swing.JPanel pnlLogout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlSubProfile1;
    private javax.swing.JPanel pnlSubProfile2;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tblAuthorize;
    private javax.swing.JTable tblCustomerS;
    private javax.swing.JTable tblTransactionS;
    private javax.swing.JPasswordField txtConfirmPass3;
    private javax.swing.JFormattedTextField txtCustomerID;
    private javax.swing.JTextField txtFullName3;
    private javax.swing.JPasswordField txtPassword3;
    private javax.swing.JFormattedTextField txtReceiverID;
    private javax.swing.JFormattedTextField txtSenderID;
    // End of variables declaration//GEN-END:variables
}
