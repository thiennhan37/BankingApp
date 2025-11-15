/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Control.AccountControl;
import Control.AuthorizeControl;
import Control.StaffControl;
import Control.TransactionControl;
import DAO.TransactionDAO;
import Model.Account;
import Model.AccountManage;
import Model.Customer;
import Model.Staff;
import Model.Transaction;
import Model.TransactionManage;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import javax.swing.text.AbstractDocument;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
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
    private AuthorizeControl authController;
    private Account myAccount;
    private String myEmail;
    private CardLayout cardLayout3;
    private boolean isOpenEye3 = true;
    private ImageIcon openEyeImage = new javax.swing.ImageIcon(getClass().getResource("/MyImage/seenEye.png"));
    private ImageIcon closeEyeImage = new javax.swing.ImageIcon(getClass().getResource("/MyImage/hideEye.png"));
    private ImageIcon logoutStatic = new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutStatic.png"));
    private ImageIcon logoutDynamic = new javax.swing.ImageIcon(getClass().getResource("/MyImage/logoutDynamic1.gif"));
    private ImageIcon FemaleAdmin = new javax.swing.ImageIcon(getClass().getResource("/MyImage/FemaleAdmin.png"));
    private ImageIcon MaleAdmin = new javax.swing.ImageIcon(getClass().getResource("/MyImage/MaleAdmin.png"));
    private DefaultTableModel authorizeTableModel, searchCusTBMD, searchTransTBMD, manageAccTBMD, manageTransTBMD, staffInfoTBMD;
    private NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));;
    private DateTimeFormatter fm2Y = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
    private DateTimeFormatter fm4Y = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private DateTimeFormatter fmForBirth = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Locale myLocale = new Locale("vi", "VN");
    private Color color1 = new Color(255,255,255), 
            color2 = new Color(171, 245, 232),
            color3 = new Color(89, 222, 198);
    private DefaultPieDataset datasetPie1, datasetPie2;
    private DefaultCategoryDataset datasetCategory, datasetCategoryStaff;
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
        authController = new AuthorizeControl();
        initComponents();
        resetAccount();
        if(myAccount instanceof Staff){ 
            btStaff.setEnabled(false); btManage.setEnabled(false);
        }
        if(myAccount.getGender().equals("Female")){
            lblProfile.setIcon(FemaleAdmin);
        }
        txtPassword3.setEchoChar((char) 0); 
        cardLayout3 = (CardLayout) pnlCard.getLayout();
        authorizeTableModel = (DefaultTableModel) tblAuthorize.getModel();
        searchCusTBMD = (DefaultTableModel) tblCustomerS.getModel();
        searchTransTBMD = (DefaultTableModel) tblTransactionS.getModel();
        manageTransTBMD = (DefaultTableModel) tblManageTrans.getModel();
        manageAccTBMD = (DefaultTableModel) tblManageAcc.getModel();
        staffInfoTBMD = (DefaultTableModel) tblStaffInfo.getModel();
        ((AbstractDocument)txtFullName3.getDocument()).setDocumentFilter(new AlphabetFilter());
        ((AbstractDocument)txtFullName2.getDocument()).setDocumentFilter(new AlphabetFilter()); 
        settingGUIComponent();
        setMouseList(); 
        setRestrict();
        setManageFunction(); setTransManage(); setAccountManage();
        addForStat(); btAllTime.doClick(); 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        autoReset();
        this.setVisible(true);
    } 
    
    private Timer myTime;
    private void autoReset(){
        myTime = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetByTimer();
            }
        });
        myTime.start();
    }
    private void resetByTimer(){
        myAccount = accController.getAccountByEmail(myEmail);
        if(!myAccount.isActive()){
            myTime.stop();
            this.dispose();
            new FormLogin();
        }
    }
    private void settingGUIComponent(){
        JComponent[] btArr = {btSaveInfo3, btChangePass3};
        for(JComponent x : btArr){
           x.putClientProperty(FlatClientProperties.STYLE,"arc:25;");
        }
        JComponent[] btTime = {bt1Week, bt1Month, bt6Month, bt1Year, btAllTime};
        for(JComponent x : btTime){
           x.putClientProperty(FlatClientProperties.STYLE,"arc:20;borderColor:#B28991;");
        }
        
        lblEmail3.putClientProperty("FlatLaf.style", "arc:20; background:#F0F8FF;");
        
        JComponent[] fieldTime = {fieldDay3, fieldMonth3, fieldYear3, fieldBeginMonthA, fieldBeginYearA,
                    fieldEndMonthA, fieldEndYearA, fieldTypeA, fieldTypeS, fieldStatusS,
                     fieldBeginMonthS, fieldBeginYearS, fieldEndMonthS, fieldEndYearS, 
                    fieldMonthManage1, fieldMonthManage2, fieldYearManage1, fieldYearManage2};
        for(JComponent x : fieldTime){
           x.putClientProperty("FlatLaf.style", "borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        
        
        JComponent[] txtArr = {txtFullName3, txtPassword3, txtConfirmPass3, 
            txtBranch2, txtEmail2, txtPassword2, txtConfirmPass2, txtFullName2, 
            txtTransManage, txtAccManage, txtStaffManage1, txtStaffManage2, 
            txtSenderID, txtReceiverID,txtCusSearch, txtStaffInStaff};
        for(JComponent x : txtArr){
           x.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }

        JTable[] tblArr = {tblAuthorize, tblCustomerS, tblTransactionS, tblManageAcc, tblManageTrans};
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
        transCLMD.getColumn(6).setPreferredWidth(100);
        
        datasetPie1 = new DefaultPieDataset();
        datasetPie2 = new DefaultPieDataset();
        setPieChart(datasetPie1, pnlPie1, "Transaction Amount By Type");
        setPieChart(datasetPie2, pnlPie2, "Transaction Quantity By Type");
        
        datasetCategory = new DefaultCategoryDataset();
        setCategoryChart();
        datasetCategoryStaff = new DefaultCategoryDataset();
        setCategoryChartForStaff();
        
        TableColumnModel transManageCLMD = tblManageTrans.getColumnModel();
        transManageCLMD.getColumn(0).setPreferredWidth(30);
        transManageCLMD.getColumn(1).setPreferredWidth(30);
        transManageCLMD.getColumn(2).setPreferredWidth(40);
        transManageCLMD.getColumn(3).setPreferredWidth(60);
        
        TableColumnModel accManageCLMD = tblManageAcc.getColumnModel();
        accManageCLMD.getColumn(0).setPreferredWidth(30);
        accManageCLMD.getColumn(1).setPreferredWidth(30);
        accManageCLMD.getColumn(2).setPreferredWidth(40);
        accManageCLMD.getColumn(3).setPreferredWidth(60);
        
        TableColumnModel staffInfoCLMD = tblStaffInfo.getColumnModel();
        staffInfoCLMD.getColumn(0).setPreferredWidth(60);
        staffInfoCLMD.getColumn(1).setPreferredWidth(120);
        staffInfoCLMD.getColumn(2).setPreferredWidth(100);
        staffInfoCLMD.getColumn(3).setPreferredWidth(50);
        staffInfoCLMD.getColumn(4).setPreferredWidth(60);
    }   
    private void setRestrict(){
        JFormattedTextField[] arr = {txtSenderID, txtReceiverID, txtCusSearch};
        for(JFormattedTextField x : arr){
            setTextForNumber(x);
        }
    } 
            
    private void setMouseList(){
        JButton[] arr = {btStaff, btAuthorize, btSearch, btManage, btProfile, btStatics, btLogout};
        
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
            lblProfile.setIcon(FemaleAdmin); 
        }
        else{
            btMale3.setSelected(true);
            lblProfile.setIcon(MaleAdmin); 
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
        fieldMonthManage1.setSelectedIndex(now.getMonthValue() - 1); fieldYearManage1.setSelectedIndex(now.getYear() - 2010);
        fieldMonthManage2.setSelectedIndex(now.getMonthValue() - 1); fieldYearManage2.setSelectedIndex(now.getYear() - 2010);
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
    
    private void setPieChart(DefaultPieDataset dataset, JPanel pnlPie, String title){
        dataset.setValue("Transfer", 1);
        dataset.setValue("Withdraw", 1);
        dataset.setValue("Deposit", 1);
        
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, true);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(new Font("Times New Roman", Font.BOLD, 14));
        }
        chart.getTitle().setFont(new Font("Times New Roman", Font.BOLD, 18)); 
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Transfer", new Color(255, 255, 102));
        plot.setSectionPaint("Withdraw", new Color(102, 255, 102));
        plot.setSectionPaint("Deposit", new Color(255, 102, 153));

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}"    // {0}=tên, {1}=giá trị, {2}=tỉ lệ %
        ));
        plot.setToolTipGenerator(new StandardPieToolTipGenerator(
            "{0} = {1}", myLocale    // {0}=tên, {1}=giá trị, {2}=tỉ lệ %
        ));
        plot.setSimpleLabels(true);

        plot.setLabelFont(new Font("Calibri", Font.BOLD, 14));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(new Color(204, 255, 255));
        plot.setInteriorGap(0.01);
        
        plot.setLabelBackgroundPaint(null);      // bỏ màu nền
        plot.setLabelOutlinePaint(null);         // bỏ viền chữ nhật
        plot.setLabelShadowPaint(null);
        
        ChartPanel chartPnl = new ChartPanel(chart);
        chartPnl.setDisplayToolTips(true);
        chartPnl.setMouseWheelEnabled(true); 
        
        
        pnlPie.removeAll(); 
        pnlPie.add(chartPnl, BorderLayout.CENTER);
        pnlPie.validate();
    }
    private LocalDateTime beginCusSta= null;
    private void addForStat(){
        JButton[] btArr = {bt1Week, bt1Month, bt6Month, bt1Year, btAllTime};
        
        for(JButton x : btArr){
            x.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch(x.getActionCommand()){
                        case("1 Week") ->{
                            beginCusSta = LocalDateTime.now().minusDays(7);
                        }
                        case("1 Month") ->{
                            beginCusSta = LocalDateTime.now().minusMonths(1);
                        }
                        case("6 Month") ->{
                            beginCusSta = LocalDateTime.now().minusMonths(6);
                        }
                        case("1 Year") ->{
                            beginCusSta = LocalDateTime.now().minusYears(1);
                        } 
                        default ->{
                            beginCusSta= null;
                        }
                    }
                    Long[] result = TransactionDAO.getInstance().staticsPieForCustomer(null, beginCusSta);
                    if(datasetPie1 != null) datasetPie1.clear(); 
                    if(datasetPie2 != null) datasetPie2.clear();
                    datasetPie1.setValue("Transfer", result[0]);
                    datasetPie1.setValue("Withdraw", result[2]);
                    datasetPie1.setValue("Deposit", result[4]);
                    
                    datasetPie2.setValue("Transfer", result[1]);
                    datasetPie2.setValue("Withdraw", result[3]);
                    datasetPie2.setValue("Deposit", result[5]);
                    
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
    
    private void setCategoryChart(){ 

        LinkedHashMap<String, Long> lst = TransactionDAO.getInstance().staticsPremierCus();
        for(String id : lst.keySet()){
            datasetCategory.addValue(lst.get(id), "Balance", id); 
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Premier Customers by Account Holdings", // Tiêu đề
                "Customer ID",                            // Trục X
                "Total Balance(VND)",                    // Trục Y
                datasetCategory,
                PlotOrientation.HORIZONTAL,
                true, true, false
        );
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        
        NumberFormat vnFormat = NumberFormat.getInstance(myLocale);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(vnFormat);
        // Giao diện ban đầu
        plot.setBackgroundPaint(new Color(204,255,255));
        plot.setRangeGridlinePaint(Color.GRAY);
        
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(0, 153, 255));
        plot.getDomainAxis().setCategoryMargin(0.4);
        
        renderer.setDefaultToolTipGenerator(
            new StandardCategoryToolTipGenerator("{2}", NumberFormat.getInstance(myLocale))
        );
        
        chart.getTitle().setFont(new Font("Times New Roman", Font.BOLD, 18)); 
        chart.getLegend().setItemFont(new Font("Times New Roman", Font.BOLD, 14)); 
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(350, 500));
        
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 13));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 13));
        renderer.setDefaultItemLabelsVisible(true);
        
        pnlCategoryStats.add(chartPanel, BorderLayout.CENTER);
        
    }
    private void updateCategoryChartBalance(){
        LinkedHashMap<String, Long> lst = TransactionDAO.getInstance().staticsPremierCus();
        datasetCategory.clear();
        for(String id : lst.keySet()){
            datasetCategory.addValue(lst.get(id), "Balance", id); 
        }
    }
            
    private void setCategoryChartForStaff(){ 
        LocalDate now = LocalDate.now();
        for(int i = 4; i >= 0; i--){
            LocalDate x = now.minusMonths(i);
            // System.out.println(x.getMonthValue() + " " + x.getYear());
            datasetCategoryStaff.addValue(0, "Transactions", x.getMonthValue()+ "/" + x.getYear());
            datasetCategoryStaff.addValue(0, "Accounts", x.getMonthValue()+ "/" + x.getYear());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Transactions and Accounts Processed", // Tiêu đề
                "Date",                            // Trục X
                "Number Of Process",                    // Trục Y
                datasetCategoryStaff,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        
        NumberFormat vnFormat = NumberFormat.getInstance(myLocale);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(vnFormat);
        // Giao diện ban đầu
        plot.setBackgroundPaint(new Color(204,255,255));
        plot.setRangeGridlinePaint(Color.GRAY);
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(231, 76, 60));  // Trans
        renderer.setSeriesPaint(1, new Color(52, 73, 94)); // Accounts
        
       
        plot.getDomainAxis().setCategoryMargin(0.4);
        renderer.setItemMargin(0.0); 
        
        renderer.setDefaultToolTipGenerator(
            new StandardCategoryToolTipGenerator("({0}, {1}) = {2} ", NumberFormat.getInstance(myLocale))
        );
        
        chart.getTitle().setFont(new Font("Times New Roman", Font.BOLD, 18)); 
        chart.getLegend().setItemFont(new Font("Times New Roman", Font.BOLD, 14)); 
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(350, 500));
        
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 13));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 13));
        renderer.setDefaultItemLabelsVisible(true);
        
        pnlCategoryStaff.add(chartPanel, BorderLayout.CENTER);
        
    }
    private void setDataChartForStaff(String staffID){
        LocalDate now = LocalDate.now();
        Integer[] result = authController.staticsForStaff(staffID, now);
        // System.out.println(Arrays.toString(result)); 
        for(int i = 4; i >= 0; i--){
            LocalDate x = now.minusMonths(i);
            // System.out.println(x.getMonthValue() + " " + x.getYear());
            datasetCategoryStaff.setValue(result[(4 - i) * 2], "Transactions", x.getMonthValue()+ "/" + x.getYear());
            datasetCategoryStaff.setValue(result[(4 - i) * 2 + 1], "Accounts", x.getMonthValue()+ "/" + x.getYear());
        }
    }
    
    private void setTransManage(){
        int month = Integer.parseInt(fieldMonthManage1.getSelectedItem().toString());
        int year = Integer.parseInt(fieldYearManage1.getSelectedItem().toString());
        String transID = txtTransManage.getText();
        String staffID = txtStaffManage1.getText();
        List<TransactionManage> lst = authController.filterTransManage(transID, staffID, year, month);
        manageTransTBMD.setRowCount(0); 
        for(TransactionManage x : lst){
            manageTransTBMD.addRow(new Object[]{x.getTransactionID(), x.getStaffID(), x.getDecision(), x.getTime().format(fm4Y)}); 
        }
    }
    private void setAccountManage(){
        int month = Integer.parseInt(fieldMonthManage2.getSelectedItem().toString());
        int year = Integer.parseInt(fieldYearManage2.getSelectedItem().toString());
        String accountID = txtAccManage.getText();
        String staffID = txtStaffManage2.getText();
        List<AccountManage> lst = authController.filterAccManage(accountID, staffID, year, month);
        manageAccTBMD.setRowCount(0); 
        for(AccountManage x : lst){
            manageAccTBMD.addRow(new Object[]{x.getAccountID(), x.getStaffID(), x.getDecision(), x.getTime().format(fm4Y)}); 
        }
    }
    private void setManageFunction(){
        JTextField[] arrText1 = {txtStaffManage1, txtTransManage};
        for(JTextField x : arrText1){
            x.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    setTransManage();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    setTransManage();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    
                }
            });
        }
        JComboBox[] arrComboBox1 = {fieldMonthManage1, fieldYearManage1};
        for(JComboBox x : arrComboBox1){
            x.addActionListener((ActionEvent e) -> {
                setTransManage();
            });
        }
        JTextField[] arrText2 = {txtStaffManage2, txtAccManage};
        for(JTextField x : arrText2){
            x.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    setAccountManage();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    setAccountManage();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    
                }
            });
        }
        JComboBox[] arrComboBox2 = {fieldMonthManage2, fieldYearManage2};
        for(JComboBox x : arrComboBox2){
            x.addActionListener((ActionEvent e) -> {
                setAccountManage();
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
        GenderGroupRegister = new javax.swing.ButtonGroup();
        pnlTop = new javax.swing.JPanel();
        btReset3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblPositon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblBranch = new javax.swing.JLabel();
        lblFullName3 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btProfile = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
        btAuthorize = new javax.swing.JButton();
        btStatics = new javax.swing.JButton();
        btStaff = new javax.swing.JButton();
        btManage = new javax.swing.JButton();
        lblProfile = new javax.swing.JLabel();
        lblLineRow1 = new javax.swing.JLabel();
        pnlLogout = new javax.swing.JPanel();
        btLogout = new javax.swing.JButton();
        lblLineCol2 = new javax.swing.JLabel();
        lblLineCol1 = new javax.swing.JLabel();
        lblLineRow2 = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        pnlCardStatics = new javax.swing.JPanel();
        bt1Week = new javax.swing.JButton();
        pnlPie1 = new javax.swing.JPanel();
        pnlPie2 = new javax.swing.JPanel();
        bt1Month = new javax.swing.JButton();
        bt6Month = new javax.swing.JButton();
        bt1Year = new javax.swing.JButton();
        btAllTime = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        pnlCategoryStats = new javax.swing.JPanel();
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
        pnlCardStaff1 = new javax.swing.JPanel();
        btAddStaffAcc = new javax.swing.JButton();
        btFindStaff = new javax.swing.JButton();
        txtStaffInStaff = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblWarnStaff = new javax.swing.JLabel();
        btActivateStaff = new javax.swing.JButton();
        btDeactivateStaff = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStaffInfo = new javax.swing.JTable();
        pnlCategoryStaff = new javax.swing.JPanel();
        pnlCardStaff2 = new javax.swing.JPanel();
        pnlRegister = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtFullName2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtEmail2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        jLabel22 = new javax.swing.JLabel();
        txtConfirmPass2 = new javax.swing.JPasswordField();
        btEyePass2 = new javax.swing.JButton();
        btRegister2 = new javax.swing.JButton();
        fieldDay2 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        fieldMonth2 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        fieldYear2 = new javax.swing.JComboBox<>();
        btMale2 = new javax.swing.JRadioButton();
        btFemale2 = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        lblWarn2 = new javax.swing.JLabel();
        btExit2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtBranch2 = new javax.swing.JTextField();
        pnlCardManage = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblManageTrans = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        txtTransManage = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtStaffManage1 = new javax.swing.JTextField();
        fieldMonthManage1 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        fieldYearManage1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblManageAcc = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtStaffManage2 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        fieldMonthManage2 = new javax.swing.JComboBox<>();
        fieldYearManage2 = new javax.swing.JComboBox<>();
        txtAccManage = new javax.swing.JTextField();
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
        txtCusSearch = new javax.swing.JFormattedTextField();
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
        btStatics.setText("   Statics  ");
        btStatics.setBorderPainted(false);
        btStatics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStaticsActionPerformed(evt);
            }
        });
        pnlMenu.add(btStatics);

        btStaff.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/staff.png"))); // NOI18N
        btStaff.setText("   Staff");
        btStaff.setBorderPainted(false);
        btStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStaffActionPerformed(evt);
            }
        });
        pnlMenu.add(btStaff);

        btManage.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btManage.setForeground(new java.awt.Color(51, 51, 51));
        btManage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/icons8-manage-50.png"))); // NOI18N
        btManage.setText(" Manage");
        btManage.setBorderPainted(false);
        btManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btManageActionPerformed(evt);
            }
        });
        pnlMenu.add(btManage);

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
        btLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLogoutLayout = new javax.swing.GroupLayout(pnlLogout);
        pnlLogout.setLayout(pnlLogoutLayout);
        pnlLogoutLayout.setHorizontalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLogoutLayout.setVerticalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoutLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btLogout)
                .addGap(36, 36, 36))
        );

        lblLineCol2.setBackground(new java.awt.Color(0, 0, 0));
        lblLineCol2.setOpaque(true);

        lblLineCol1.setBackground(new java.awt.Color(0, 0, 0));
        lblLineCol1.setOpaque(true);

        lblLineRow2.setBackground(new java.awt.Color(0, 0, 0));
        lblLineRow2.setOpaque(true);

        pnlCard.setLayout(new java.awt.CardLayout());

        bt1Week.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt1Week.setForeground(new java.awt.Color(102, 102, 102));
        bt1Week.setText("1 Week");

        pnlPie1.setLayout(new java.awt.BorderLayout());

        pnlPie2.setLayout(new java.awt.BorderLayout());

        bt1Month.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt1Month.setForeground(new java.awt.Color(102, 102, 102));
        bt1Month.setText("1 Month");

        bt6Month.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt6Month.setForeground(new java.awt.Color(102, 102, 102));
        bt6Month.setText("6 Month");

        bt1Year.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt1Year.setForeground(new java.awt.Color(102, 102, 102));
        bt1Year.setText("1 Year");

        btAllTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAllTime.setForeground(new java.awt.Color(102, 102, 102));
        btAllTime.setText("ALL");

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));

        pnlCategoryStats.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlCardStaticsLayout = new javax.swing.GroupLayout(pnlCardStatics);
        pnlCardStatics.setLayout(pnlCardStaticsLayout);
        pnlCardStaticsLayout.setHorizontalGroup(
            pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                .addGroup(pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(bt1Week)
                        .addGap(18, 18, 18)
                        .addComponent(bt1Month)
                        .addGap(18, 18, 18)
                        .addComponent(bt6Month)
                        .addGap(18, 18, 18)
                        .addComponent(bt1Year)
                        .addGap(18, 18, 18)
                        .addComponent(btAllTime)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                                .addComponent(pnlCategoryStats, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 15, Short.MAX_VALUE))
                            .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                                .addComponent(pnlPie1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlPie2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))))
                .addContainerGap())
        );
        pnlCardStaticsLayout.setVerticalGroup(
            pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardStaticsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt1Month, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt1Week, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(bt6Month, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt1Year, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAllTime, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCardStaticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPie2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPie1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlCategoryStats, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCard.add(pnlCardStatics, "statics");

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

        pnlCardStaff1.setAlignmentX(0.0F);

        btAddStaffAcc.setBackground(new java.awt.Color(153, 255, 204));
        btAddStaffAcc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAddStaffAcc.setText("Add Staff Account");
        btAddStaffAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddStaffAccActionPerformed(evt);
            }
        });

        btFindStaff.setBackground(new java.awt.Color(153, 255, 255));
        btFindStaff.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFindStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/find.png"))); // NOI18N
        btFindStaff.setText("Find");
        btFindStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindStaffActionPerformed(evt);
            }
        });

        txtStaffInStaff.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStaffInStaffFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(178, 137, 145));
        jLabel4.setText("Staff ID");

        lblWarnStaff.setForeground(new java.awt.Color(255, 51, 51));

        btActivateStaff.setBackground(new java.awt.Color(51, 255, 51));
        btActivateStaff.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btActivateStaff.setText("Activate");
        btActivateStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btActivateStaffActionPerformed(evt);
            }
        });

        btDeactivateStaff.setBackground(new java.awt.Color(255, 51, 51));
        btDeactivateStaff.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDeactivateStaff.setText("Deactivate");
        btDeactivateStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeactivateStaffActionPerformed(evt);
            }
        });

        tblStaffInfo.setBackground(new java.awt.Color(204, 204, 204));
        tblStaffInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Full Name", "Email", "Branch", "Gender", "Activate"
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
        tblStaffInfo.setEnabled(false);
        tblStaffInfo.setRowHeight(30);
        tblStaffInfo.setShowGrid(true);
        jScrollPane3.setViewportView(tblStaffInfo);

        pnlCategoryStaff.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlCardStaff1Layout = new javax.swing.GroupLayout(pnlCardStaff1);
        pnlCardStaff1.setLayout(pnlCardStaff1Layout);
        pnlCardStaff1Layout.setHorizontalGroup(
            pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardStaff1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCardStaff1Layout.createSequentialGroup()
                        .addGroup(pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStaffInStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(lblWarnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btFindStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btActivateStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btDeactivateStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btAddStaffAcc))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                    .addComponent(pnlCategoryStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnlCardStaff1Layout.setVerticalGroup(
            pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardStaff1Layout.createSequentialGroup()
                .addGroup(pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardStaff1Layout.createSequentialGroup()
                        .addGroup(pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCardStaff1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtStaffInStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardStaff1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btActivateStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(lblWarnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(pnlCardStaff1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btFindStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btDeactivateStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlCardStaff1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btAddStaffAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCategoryStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pnlCard.add(pnlCardStaff1, "Staff1");

        pnlRegister.setBackground(new java.awt.Color(153, 255, 204));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(178, 137, 145));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("REGISTER");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(178, 137, 145));
        jLabel18.setText("Fullname");

        txtFullName2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFullName2FocusLost(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(178, 137, 145));
        jLabel20.setText("Email");

        txtEmail2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmail2FocusLost(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(178, 137, 145));
        jLabel21.setText("Password");

        txtPassword2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword2FocusLost(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(178, 137, 145));
        jLabel22.setText("Confirm Password");

        txtConfirmPass2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConfirmPass2FocusLost(evt);
            }
        });

        btEyePass2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/hideEye.png"))); // NOI18N
        btEyePass2.setContentAreaFilled(false);
        btEyePass2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEyePass2ActionPerformed(evt);
            }
        });

        btRegister2.setBackground(new java.awt.Color(153, 255, 255));
        btRegister2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btRegister2.setForeground(new java.awt.Color(178, 137, 145));
        btRegister2.setText("Register");
        btRegister2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegister2ActionPerformed(evt);
            }
        });

        fieldDay2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        fieldDay2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldDay2FocusLost(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(178, 137, 145));
        jLabel26.setText("Day");

        fieldMonth2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        fieldMonth2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldMonth2FocusLost(evt);
            }
        });

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(178, 137, 145));
        jLabel31.setText("Month");

        jLabel32.setBackground(new java.awt.Color(255, 255, 255));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(178, 137, 145));
        jLabel32.setText("Year");

        fieldYear2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1900", "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        fieldYear2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldYear2FocusLost(evt);
            }
        });

        GenderGroupRegister.add(btMale2);
        btMale2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMale2.setForeground(new java.awt.Color(178, 137, 145));
        btMale2.setSelected(true);
        btMale2.setText("Male");

        GenderGroupRegister.add(btFemale2);
        btFemale2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFemale2.setForeground(new java.awt.Color(178, 137, 145));
        btFemale2.setText("Female");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(178, 137, 145));
        jLabel33.setText("Gender");

        lblWarn2.setForeground(new java.awt.Color(255, 51, 51));

        btExit2.setBackground(new java.awt.Color(153, 255, 255));
        btExit2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btExit2.setForeground(new java.awt.Color(178, 137, 145));
        btExit2.setText("Exit");
        btExit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExit2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(178, 137, 145));
        jLabel2.setText("Branch");

        txtBranch2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBranch2FocusLost(evt);
            }
        });

        javax.swing.GroupLayout pnlRegisterLayout = new javax.swing.GroupLayout(pnlRegister);
        pnlRegister.setLayout(pnlRegisterLayout);
        pnlRegisterLayout.setHorizontalGroup(
            pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlRegisterLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegisterLayout.createSequentialGroup()
                        .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEyePass2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel21)
                    .addGroup(pnlRegisterLayout.createSequentialGroup()
                        .addComponent(btMale2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btFemale2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20)
                    .addComponent(jLabel18)
                    .addComponent(txtFullName2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtBranch2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtEmail2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRegisterLayout.createSequentialGroup()
                            .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fieldDay2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(44, 44, 44)
                            .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fieldMonth2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                            .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fieldYear2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel2)
                    .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtConfirmPass2)
                        .addGroup(pnlRegisterLayout.createSequentialGroup()
                            .addComponent(btExit2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                            .addComponent(btRegister2))
                        .addComponent(lblWarn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnlRegisterLayout.setVerticalGroup(
            pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFullName2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBranch2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldDay2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldMonth2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldYear2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btMale2)
                    .addComponent(btFemale2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEyePass2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarn2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRegister2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExit2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fieldYear2.setSelectedIndex(105);

        javax.swing.GroupLayout pnlCardStaff2Layout = new javax.swing.GroupLayout(pnlCardStaff2);
        pnlCardStaff2.setLayout(pnlCardStaff2Layout);
        pnlCardStaff2Layout.setHorizontalGroup(
            pnlCardStaff2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardStaff2Layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(pnlRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );
        pnlCardStaff2Layout.setVerticalGroup(
            pnlCardStaff2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardStaff2, "Staff2");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Transaction", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(153, 153, 255))); // NOI18N

        tblManageTrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trans ID", "Staff ID", "Decision", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblManageTrans.setGridColor(new java.awt.Color(102, 102, 102));
        tblManageTrans.setRowHeight(30);
        tblManageTrans.setSelectionBackground(new java.awt.Color(153, 255, 153));
        tblManageTrans.setSelectionForeground(new java.awt.Color(0, 0, 153));
        tblManageTrans.setShowGrid(true);
        tblManageTrans.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblManageTransMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblManageTrans);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(178, 137, 145));
        jLabel35.setText("Transaction ID");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(178, 137, 145));
        jLabel36.setText("Staff ID");

        fieldMonthManage1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(178, 137, 145));
        jLabel37.setText("Time");

        fieldYearManage1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", " " }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTransManage)
                    .addComponent(txtStaffManage1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fieldMonthManage1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(fieldYearManage1, 0, 1, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTransManage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStaffManage1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldMonthManage1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldYearManage1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 66, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Account", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(153, 153, 255))); // NOI18N

        tblManageAcc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account ID", "Staff ID", "Decision", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblManageAcc.setGridColor(new java.awt.Color(102, 102, 102));
        tblManageAcc.setRowHeight(30);
        tblManageAcc.setSelectionBackground(new java.awt.Color(153, 255, 153));
        tblManageAcc.setSelectionForeground(new java.awt.Color(0, 0, 153));
        tblManageAcc.setShowGrid(true);
        jScrollPane6.setViewportView(tblManageAcc);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(178, 137, 145));
        jLabel38.setText("Account ID");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(178, 137, 145));
        jLabel39.setText("Staff ID");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(178, 137, 145));
        jLabel40.setText("Time");

        fieldMonthManage2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        fieldYearManage2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", " " }));
        fieldYearManage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldYearManage2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStaffManage2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fieldMonthManage2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(fieldYearManage2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtAccManage))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAccManage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStaffManage2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldMonthManage2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldYearManage2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlCardManageLayout = new javax.swing.GroupLayout(pnlCardManage);
        pnlCardManage.setLayout(pnlCardManageLayout);
        pnlCardManageLayout.setHorizontalGroup(
            pnlCardManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        pnlCardManageLayout.setVerticalGroup(
            pnlCardManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardManageLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCard.add(pnlCardManage, "Manage");

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
        btFindS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/find.png"))); // NOI18N
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
                            .addComponent(txtCusSearch)
                            .addComponent(btActivate, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
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
                    .addComponent(txtCusSearch))
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
        btFindA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImage/find.png"))); // NOI18N
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
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
                .addComponent(lblLineCol1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLineRow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLineCol2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlTop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void btManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btManageActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "Manage");
    }//GEN-LAST:event_btManageActionPerformed

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
        if(txtFullName3.getText().isEmpty()){
            txtFullName3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        else{
            txtFullName3.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        String name = txtFullName3.getText().trim().toUpperCase().replaceAll("\\s+", " ");
        txtFullName3.setText(name);
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

    private void btStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStaffActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "Staff1");
    }//GEN-LAST:event_btStaffActionPerformed

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
        Account customerSearch = accController.getAccountByID(txtCusSearch.getText());
        if(customerSearch == null){
            if(txtCusSearch.getText().isBlank()) lblWarnS.setText("Invalid CustomerID");
            else lblWarnS.setText("CustomerID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnS.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        if(customerSearch.isActive()){
            btActivate.setEnabled(false); btDeactivate.setEnabled(true);
        }
        else{
            btDeactivate.setEnabled(false); btActivate.setEnabled(true); 
        }
        btFindS.grabFocus();
        searchCusTBMD.addRow(new Object[]{customerSearch.getFullName(), customerSearch.getEmail(), 
            customerSearch.getGender(), customerSearch.getBirthDay().format(fmForBirth), 
            String.valueOf(customerSearch.isActive()).toUpperCase()});
        
        List<Transaction> lst = transController.filterTransactions(txtCusSearch.getText(), 
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
                authController.addTransAuthorize(myAccount.getId(), transID, "CONFIRM");
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
                authController.addTransAuthorize(myAccount.getId(), transID, "REJECT");
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
        updateCategoryChartBalance();
        cardLayout3.show(pnlCard, "statics");
    }//GEN-LAST:event_btStaticsActionPerformed

    private void btActivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btActivateActionPerformed
        // TODO add your handling code here:
        Account acc = accController.getAccountByID(txtCusSearch.getText());
        if(acc == null || !(acc instanceof Customer)){
            if(txtCusSearch.getText().isBlank()) lblWarnS.setText("Invalid CustomerID");
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
                authController.addAccAuthorize(myAccount.getId(), acc.getId(), "ACTIVATE");
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
        Account acc = accController.getAccountByID(txtCusSearch.getText());
        if(acc == null || acc.getDegree() >= myAccount.getDegree()){
            if(txtCusSearch.getText().isBlank()) lblWarnS.setText("Invalid CustomerID");
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
                authController.addAccAuthorize(myAccount.getId(), acc.getId(), "DEACTIVATE");
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

    private void resetRegister2(){
        JTextField[] arr = {txtFullName2, txtEmail2, txtBranch2, txtPassword2, txtConfirmPass2};
        for(JTextField x : arr){
            x.setText("");
            x.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            
        } 
        lblWarn2.setText(""); 
        txtPassword2.setEchoChar('•');
        btEyePass2.setIcon(closeEyeImage);
        isOpenEye2 = false;
        fieldDay2.setSelectedIndex(0); fieldMonth2.setSelectedIndex(0); fieldYear2.setSelectedIndex(105);
        btMale2.setSelected(true); 
    }
    private void txtFullName2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullName2FocusLost
        // TODO add your handling code here:
        if(txtFullName2.getText().isEmpty()){
            txtFullName2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        else{
            txtFullName2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        
        String name = txtFullName2.getText().trim().toUpperCase().replaceAll("\\s+", " ");
        txtFullName2.setText(name);
    }//GEN-LAST:event_txtFullName2FocusLost

    private void txtEmail2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmail2FocusLost
        // TODO add your handling code here:
        txtEmail2.setText(txtEmail2.getText().trim());
        if(txtEmail2.getText().isEmpty()){
            txtEmail2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtEmail2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtEmail2FocusLost

    private void txtPassword2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword2FocusLost
        // TODO add your handling code here:
        txtPassword2.setText(txtPassword2.getText().trim());
        if(txtPassword2.getText().isEmpty()){
            txtPassword2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtPassword2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtPassword2FocusLost

    private void txtConfirmPass2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConfirmPass2FocusLost
        // TODO add your handling code here:
        txtConfirmPass2.setText(txtConfirmPass2.getText().trim());
        if(txtConfirmPass2.getText().isEmpty()){
            txtConfirmPass2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtConfirmPass2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtConfirmPass2FocusLost

    private boolean isOpenEye2 = false;
    private void btEyePass2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEyePass2ActionPerformed
        // TODO add your handling code here:
        if(isOpenEye2){
            txtPassword2.setEchoChar('•');
            btEyePass2.setIcon(closeEyeImage);
            isOpenEye2 = false;
        }
        else{
            txtPassword2.setEchoChar((char) 0);
            btEyePass2.setIcon(openEyeImage);
            isOpenEye2 = true;
        }
    }//GEN-LAST:event_btEyePass2ActionPerformed

    private void btRegister2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegister2ActionPerformed
        // TODO add your handling code here:
        lblWarn2.setText("");
        String pass = txtPassword2.getText().trim(), confirmPass = txtConfirmPass2.getText().trim();
        String fullName = txtFullName2.getText().trim(), email = txtEmail2.getText().trim();
        String gender = "Male"; if(btFemale2.isSelected()) gender = "Female";
        String branch = txtBranch2.getText();
        String day = fieldDay2.getSelectedItem().toString() + "/"
        + fieldMonth2.getSelectedItem().toString() + "/"
        + fieldYear2.getSelectedItem().toString();
        LocalDate birthDay = LocalDate.parse(day, DateTimeFormatter.ofPattern("d/M/yyyy"));
        String emailRegex = "^[A-Za-z0-9]+([._%+\\-][A-Za-z0-9]+)*@"
        + "[A-Za-z0-9](?:[A-Za-z0-9\\-]{0,61}[A-Za-z0-9])?"
        + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9\\-]{0,61}[A-Za-z0-9])?)+$";
        if(fullName.isEmpty()){
            txtFullName2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        if(email.isEmpty()){
            txtEmail2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        if(!email.matches(emailRegex)){
            lblWarn2.setText("Invalid email address");
            return;
        }
        if(branch.isEmpty()){
            txtBranch2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        if(pass.length() < 6){
            lblWarn2.setText("Password must be at least 6 characters long");
            return;
        }
        if(pass.isEmpty()){
            txtPassword2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        if(confirmPass.isEmpty()){
            txtConfirmPass2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
            return;
        }
        if(!pass.equals(confirmPass)){
            lblWarn2.setText("Password does not match");
            return;
        }
        Account x = accController.getAccountByEmail(email);
        if(x != null){
            lblWarn2.setText("Account already exists");
            return;
        }
        if(!new OTPDialog(this, myAccount.getEmail(), "xác thực tài khoản").isMatch()){
            return;
        }
        Account ac = new Staff(fullName, email, pass, gender, birthDay, true , branch);
        if(accController.addAccount(ac) > 0){
            resetRegister2();
            JOptionPane.showMessageDialog(this, "Register completely", "", JOptionPane.INFORMATION_MESSAGE);
        }
        cardLayout3.show(pnlCard, "Staff1");
    }//GEN-LAST:event_btRegister2ActionPerformed

    private void fieldDay2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldDay2FocusLost
        // TODO add your handling code here:
        String ss = fieldDay2.getSelectedItem().toString() + "/"
        + fieldMonth2.getSelectedItem().toString() + "/"
        + fieldYear2.getSelectedItem().toString();
        LocalDate date = LocalDate.parse(ss, DateTimeFormatter.ofPattern("d/M/yyyy"));
        fieldDay2.setSelectedIndex(date.getDayOfMonth() - 1);
        fieldMonth2.setSelectedIndex(date.getMonthValue() - 1);
        fieldYear2.setSelectedIndex(date.getYear() - 1900);
    }//GEN-LAST:event_fieldDay2FocusLost

    private void fieldMonth2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldMonth2FocusLost
        // TODO add your handling code here:
        String ss = fieldDay2.getSelectedItem().toString() + "/"
        + fieldMonth2.getSelectedItem().toString() + "/"
        + fieldYear2.getSelectedItem().toString();
        LocalDate date = LocalDate.parse(ss, DateTimeFormatter.ofPattern("d/M/yyyy"));
        fieldDay2.setSelectedIndex(date.getDayOfMonth() - 1);
        fieldMonth2.setSelectedIndex(date.getMonthValue() - 1);
        fieldYear2.setSelectedIndex(date.getYear() - 1900);
    }//GEN-LAST:event_fieldMonth2FocusLost

    private void fieldYear2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldYear2FocusLost
        // TODO add your handling code here:
        String ss = fieldDay2.getSelectedItem().toString() + "/"
        + fieldMonth2.getSelectedItem().toString() + "/"
        + fieldYear2.getSelectedItem().toString();
        LocalDate date = LocalDate.parse(ss, DateTimeFormatter.ofPattern("d/M/yyyy"));
        fieldDay2.setSelectedIndex(date.getDayOfMonth() - 1);
        fieldMonth2.setSelectedIndex(date.getMonthValue() - 1);
        fieldYear2.setSelectedIndex(date.getYear() - 1900);
    }//GEN-LAST:event_fieldYear2FocusLost

    private void btExit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExit2ActionPerformed
        // TODO add your handling code here:
        cardLayout3.show(pnlCard, "Staff1");
    }//GEN-LAST:event_btExit2ActionPerformed

    private void btAddStaffAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddStaffAccActionPerformed
        // TODO add your handling code here:
        resetRegister2();
        cardLayout3.show(pnlCard, "Staff2");
    }//GEN-LAST:event_btAddStaffAccActionPerformed

    private void txtBranch2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBranch2FocusLost
        // TODO add your handling code here:
        txtBranch2.setText(txtBranch2.getText().trim());
        if(txtBranch2.getText().isEmpty()){
            txtBranch2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#FF3333; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
        else{
            txtBranch2.putClientProperty("FlatLaf.style", "arc:20; borderColor:#B28991; focusedBorderColor:#99FFFF; background:#F0F8FF;");
        }
    }//GEN-LAST:event_txtBranch2FocusLost

    private void fieldYearManage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldYearManage2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldYearManage2ActionPerformed

    private void tblManageTransMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblManageTransMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2 && !evt.isConsumed()){
            int row = tblManageTrans.rowAtPoint(evt.getPoint());
            if(row != -1){
                String transID = tblManageTrans.getValueAt(row, 0).toString();
                // System.out.println(transID);
                String decision = tblManageTrans.getValueAt(row, 2).toString();
                new FormTransaction(transID, decision);
            }
            evt.consume();
        }
    }//GEN-LAST:event_tblManageTransMouseClicked

    private void btLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLogoutActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", 
                "", JOptionPane.YES_NO_OPTION);
        if(choice != 0) return;
        this.dispose();
        new FormLogin();
    }//GEN-LAST:event_btLogoutActionPerformed

    private void btFindStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindStaffActionPerformed
        // TODO add your handling code here:
        staffInfoTBMD.setRowCount(0);
        setDataChartForStaff(txtStaffInStaff.getText()); 
        Account stf = accController.getAccountByID(txtStaffInStaff.getText());
        if(stf == null || stf instanceof Customer){
            if(txtStaffInStaff.getText().isBlank()) lblWarnStaff.setText("Invalid StaffID");
            else lblWarnStaff.setText("StaffID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnStaff.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        if(stf.isActive()){
            btActivateStaff.setEnabled(false); btDeactivateStaff.setEnabled(true);
        }
        else{
            btDeactivateStaff.setEnabled(false); btActivateStaff.setEnabled(true); 
        }
        btFindStaff.grabFocus();
        staffInfoTBMD.addRow(new Object[]{stf.getFullName(), stf.getEmail(), stf.getBranch(), stf.getGender(), (""+ stf.isActive()).toUpperCase()} );
        

    }//GEN-LAST:event_btFindStaffActionPerformed

    private void txtStaffInStaffFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStaffInStaffFocusLost
        // TODO add your handling code here: 
        txtStaffInStaff.setText(txtStaffInStaff.getText().toUpperCase());
    }//GEN-LAST:event_txtStaffInStaffFocusLost

    private void btActivateStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btActivateStaffActionPerformed
        // TODO add your handling code here:
        btFindStaff.doClick();
        Account acc = accController.getAccountByID(txtStaffInStaff.getText());
        if(acc == null || acc instanceof Customer){
            if(txtStaffInStaff.getText().isBlank()) lblWarnStaff.setText("Invalid StaffID");
            else lblWarnStaff.setText("StaffID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnStaff.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        if(acc.getDegree() >= myAccount.getDegree()){
            JOptionPane.showMessageDialog(this, "You can not have permission!", 
                        "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(acc.isActive()) return;
        int choice = JOptionPane.showConfirmDialog(this, "Activate account " + acc.getId() + "?", 
                                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(accController.updateObjectActive(acc.getId(), true)){
                authController.addAccAuthorize(myAccount.getId(), acc.getId(), "ACTIVATE");
                JOptionPane.showMessageDialog(this, "Activate account successfully!", 
                        "", JOptionPane.INFORMATION_MESSAGE);
                btFindStaff.doClick();
            }
            else{
                JOptionPane.showMessageDialog(this, "Activate account failed!", 
                        "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btActivateStaffActionPerformed

    private void btDeactivateStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeactivateStaffActionPerformed
        // TODO add your handling code here:
        btFindStaff.doClick();
        Account acc = accController.getAccountByID(txtStaffInStaff.getText());
        if(acc == null || acc instanceof Customer){
            if(txtStaffInStaff.getText().isBlank()) lblWarnStaff.setText("Invalid StaffID");
            else lblWarnStaff.setText("StaffID is not found");
            Timer clearWarning = new Timer(2000, e -> lblWarnStaff.setText(""));
            clearWarning.setRepeats(false);
            clearWarning.start();
            return;
        }
        if(acc.getDegree() >= myAccount.getDegree()){
            JOptionPane.showMessageDialog(this, "You can not have permission!", 
                        "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!acc.isActive()) return;
        int choice = JOptionPane.showConfirmDialog(this, "Deactivate account " + acc.getId() + "?", 
                                    "", JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            if(accController.updateObjectActive(acc.getId(), false)){
                authController.addAccAuthorize(myAccount.getId(), acc.getId(), "DEACTIVATE");
                JOptionPane.showMessageDialog(this, "Deactivate account successfully!", 
                        "", JOptionPane.INFORMATION_MESSAGE);
                btFindStaff.doClick();
            }
            else{
                JOptionPane.showMessageDialog(this, "Deactivate account failed!", 
                        "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btDeactivateStaffActionPerformed

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
    private javax.swing.ButtonGroup GenderGroupRegister;
    private javax.swing.JButton bt1Month;
    private javax.swing.JButton bt1Week;
    private javax.swing.JButton bt1Year;
    private javax.swing.JButton bt6Month;
    private javax.swing.JButton btActivate;
    private javax.swing.JButton btActivateStaff;
    private javax.swing.JButton btAddStaffAcc;
    private javax.swing.JButton btAllTime;
    private javax.swing.JButton btAuthorize;
    private javax.swing.JButton btChangePass3;
    private javax.swing.JButton btConfirm;
    private javax.swing.JButton btDeactivate;
    private javax.swing.JButton btDeactivateStaff;
    private javax.swing.JButton btExit2;
    private javax.swing.JButton btEyePass2;
    private javax.swing.JButton btEyePass3;
    private javax.swing.JRadioButton btFemale2;
    private javax.swing.JRadioButton btFemale3;
    private javax.swing.JButton btFindA;
    private javax.swing.JButton btFindS;
    private javax.swing.JButton btFindStaff;
    private javax.swing.JButton btLogout;
    private javax.swing.JRadioButton btMale2;
    private javax.swing.JRadioButton btMale3;
    private javax.swing.JButton btManage;
    private javax.swing.JButton btProfile;
    private javax.swing.JButton btRegister2;
    private javax.swing.JButton btReject;
    private javax.swing.JButton btReset3;
    private javax.swing.JButton btSaveInfo3;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btStaff;
    private javax.swing.JButton btStatics;
    private javax.swing.JComboBox<String> fieldBeginMonthA;
    private javax.swing.JComboBox<String> fieldBeginMonthS;
    private javax.swing.JComboBox<String> fieldBeginYearA;
    private javax.swing.JComboBox<String> fieldBeginYearS;
    private javax.swing.JComboBox<String> fieldDay2;
    private javax.swing.JComboBox<String> fieldDay3;
    private javax.swing.JComboBox<String> fieldEndMonthA;
    private javax.swing.JComboBox<String> fieldEndMonthS;
    private javax.swing.JComboBox<String> fieldEndYearA;
    private javax.swing.JComboBox<String> fieldEndYearS;
    private javax.swing.JComboBox<String> fieldMonth2;
    private javax.swing.JComboBox<String> fieldMonth3;
    private javax.swing.JComboBox<String> fieldMonthManage1;
    private javax.swing.JComboBox<String> fieldMonthManage2;
    private javax.swing.JComboBox<String> fieldStatusS;
    private javax.swing.JComboBox<String> fieldTypeA;
    private javax.swing.JComboBox<String> fieldTypeS;
    private javax.swing.JComboBox<String> fieldYear2;
    private javax.swing.JComboBox<String> fieldYear3;
    private javax.swing.JComboBox<String> fieldYearManage1;
    private javax.swing.JComboBox<String> fieldYearManage2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
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
    private javax.swing.JLabel lblWarn2;
    private javax.swing.JLabel lblWarnS;
    private javax.swing.JLabel lblWarnSavePass3;
    private javax.swing.JLabel lblWarnStaff;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlCardAuthorize;
    private javax.swing.JPanel pnlCardManage;
    private javax.swing.JPanel pnlCardProfile;
    private javax.swing.JPanel pnlCardSearch;
    private javax.swing.JPanel pnlCardStaff1;
    private javax.swing.JPanel pnlCardStaff2;
    private javax.swing.JPanel pnlCardStatics;
    private javax.swing.JPanel pnlCategoryStaff;
    private javax.swing.JPanel pnlCategoryStats;
    private javax.swing.JPanel pnlLogout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlPie1;
    private javax.swing.JPanel pnlPie2;
    private javax.swing.JPanel pnlRegister;
    private javax.swing.JPanel pnlSubProfile1;
    private javax.swing.JPanel pnlSubProfile2;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tblAuthorize;
    private javax.swing.JTable tblCustomerS;
    private javax.swing.JTable tblManageAcc;
    private javax.swing.JTable tblManageTrans;
    private javax.swing.JTable tblStaffInfo;
    private javax.swing.JTable tblTransactionS;
    private javax.swing.JTextField txtAccManage;
    private javax.swing.JTextField txtBranch2;
    private javax.swing.JPasswordField txtConfirmPass2;
    private javax.swing.JPasswordField txtConfirmPass3;
    private javax.swing.JFormattedTextField txtCusSearch;
    private javax.swing.JTextField txtEmail2;
    private javax.swing.JTextField txtFullName2;
    private javax.swing.JTextField txtFullName3;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JPasswordField txtPassword3;
    private javax.swing.JFormattedTextField txtReceiverID;
    private javax.swing.JFormattedTextField txtSenderID;
    private javax.swing.JTextField txtStaffInStaff;
    private javax.swing.JTextField txtStaffManage1;
    private javax.swing.JTextField txtStaffManage2;
    private javax.swing.JTextField txtTransManage;
    // End of variables declaration//GEN-END:variables
}
