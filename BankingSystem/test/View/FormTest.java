/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package View;

import Control.AuthorizeControl;
import Control.StaffControl;
import Control.TransactionControl;
import DAO.AccountDAO;
import DAO.TransactionDAO;
import Model.Account;
import Model.Customer;
import Model.Staff;
import Model.Transaction;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hi
 */
public class FormTest {
    
    public FormTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class FormCustomer.
     */
    AccountDAO acDAO = AccountDAO.getInstance();
    TransactionDAO transDAO = TransactionDAO.getInstance();
    TransactionControl transController = new TransactionControl();
    AuthorizeControl authController = new AuthorizeControl();
    StaffControl stfController = new StaffControl();
    public boolean makeDeposit(String customerID){
        Long amount = 10000L;
        int result = transController.Transfer(null, customerID, 
                amount, "DEPOSIT", "TEST");
        return (result == -1);
    }

    @Test
    public void testTransfer() {
        String senderID = "999007", receiverID = "999009";
        long senderBal = acDAO.getObjectByID(senderID).getBalace();
        long receiverBal = acDAO.getObjectByID(receiverID).getBalace();
        Long amount = 10000L;
        transController.Transfer(senderID,  receiverID, amount, "TRANSFER", "TEST");
        long newSenderBal = acDAO.getObjectByID(senderID).getBalace();
        long newReceiverBal = acDAO.getObjectByID(receiverID).getBalace();
        assertEquals(senderBal - amount, newSenderBal);
        assertEquals(receiverBal + amount, newReceiverBal);
    }

    @Test
    public void testWithdraw(){
        String customerID = "999007";
        long bal = acDAO.getObjectByID(customerID).getBalace();
        Long amount = 10000L;
        transController.Transfer(customerID,  null, amount, "WITHDRAW", "TEST");
        long newBal = acDAO.getObjectByID(customerID).getBalace();
        assertEquals(bal - amount, newBal);
    }
    
    @Test
    public void testDeposit(){
        String customerID = "999009";
        boolean result = makeDeposit(customerID);
        assertTrue(result);
    }
    @Test
    public void testChangeCustomerInfo(){
        String chars = "      ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] genders = {"Male", "Female"};
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 20; i++){
            sb.append(chars.charAt(rd.nextInt(chars.length())));
        }
        String customerID = "999009";
        String name = sb.toString().trim();
        String gender = genders[rd.nextInt(2)];
        String textDate = String.valueOf(rd.nextInt(31) + 1) + "/" + 
                String.valueOf(rd.nextInt(12) + 1) + "/" + String.valueOf(rd.nextInt(106) + 1900);
        LocalDate date = LocalDate.parse(textDate, DateTimeFormatter.ofPattern("d/M/yyyy"));
        Account test = new Customer(customerID, name, "", "", gender, date, true, 0L);
        acDAO.updateObjectInfor(test);
        Account expected = acDAO.getObjectByID(customerID) ;
        assertEquals(test.getId(), expected.getId());
        assertEquals(test.getFullName(), expected.getFullName());
        assertEquals(test.getGender(), expected.getGender());
        assertEquals(test.getBirthDay(), expected.getBirthDay());
    }
    @Test
    public void testChangeCustomerPass(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,@";
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 20; i++){
            sb.append(chars.charAt(rd.nextInt(chars.length())));
        }
        String customerID = "999009";
        String password = sb.toString().trim();
        // Customer(String id, fullName, email, String password, String gender, LocalDate birthDay, boolean isActive, Long balance)
        Account test = new Customer(customerID, "", "", password, "", null, true, 0L);
        acDAO.updateObjectPass(test);
        Account curAcc = acDAO.getObjectByID(customerID) ;
        assertEquals(password, curAcc.getPassword());

    }
    @Test
    public void testDeactiveCustomerAcc(){
        String customerID = "999009";
        acDAO.updateActiveObject(customerID, false);
        Account x = acDAO.getObjectByID(customerID);
        assertTrue(!x.isActive()); 
    }
    @Test
    public void testActiveCustomerAcc(){
        String customerID = "999009";
        acDAO.updateActiveObject(customerID, true);
        Account x = acDAO.getObjectByID(customerID);
        assertTrue(x.isActive()); 
    }

    @Test
    public void testChangeStaffInfo(){
        String chars = "      ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] genders = {"Male", "Female"};
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 20; i++){
            sb.append(chars.charAt(rd.nextInt(chars.length())));
        }
        String staffID = "STF003";
        String name = sb.toString().trim();
        String gender = genders[rd.nextInt(2)];
        String textDate = String.valueOf(rd.nextInt(31) + 1) + "/" + 
                String.valueOf(rd.nextInt(12) + 1) + "/" + String.valueOf(rd.nextInt(106) + 1900);
        LocalDate date = LocalDate.parse(textDate, DateTimeFormatter.ofPattern("d/M/yyyy"));
        Account test = new Staff(staffID, name, "", "", gender, date, true, "TEST");
        acDAO.updateObjectInfor(test);
        Account expected = acDAO.getObjectByID(staffID) ;
        assertEquals(test.getId(), expected.getId());
        assertEquals(test.getFullName(), expected.getFullName());
        assertEquals(test.getGender(), expected.getGender());
        assertEquals(test.getBirthDay(), expected.getBirthDay());
    }
    @Test
    public void testChangeStaffPass(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,@";
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 20; i++){
            sb.append(chars.charAt(rd.nextInt(chars.length())));
        }
        String staffID = "STF003";
        String password = sb.toString().trim();
        // Customer(String id, fullName, email, String password, String gender, LocalDate birthDay, boolean isActive, Long balance)
        Account test = new Staff(staffID, "", "", password, "", null, true, "TEST");
        acDAO.updateObjectPass(test);
        Account curAcc = acDAO.getObjectByID(staffID) ;
        assertEquals(password, curAcc.getPassword());

    }
    @Test
    public void testDeactiveStaffAcc(){
        String staffID = "STF003";
        acDAO.updateActiveObject(staffID, false);
        Account x = acDAO.getObjectByID(staffID);
        assertTrue(!x.isActive()); 
    }
    @Test
    public void testActiveStaffAcc(){
        String staffID = "STF003";
        acDAO.updateActiveObject(staffID, true);
        Account x = acDAO.getObjectByID(staffID);
        assertTrue(x.isActive()); 
    }
    @Test
    public void testConfirmTransaction(){
        String customerID = "999009", staffID = "STF003";
        assertTrue("Can not make Deposit", makeDeposit(customerID));
        LocalDate now = LocalDate.now();
        List<Transaction> lst = transController.filterTransactions(customerID,1, 1900, 
                now.getMonthValue(), now.getYear(), "ALL", "ALL");
        if(lst.size() <= 0){
            fail("Transaction List is empty");
        }
        Transaction lastTrans = lst.get(lst.size() - 1);
        
        String transID = lastTrans.getTransID();
        String senderID = lastTrans.getSenderID(), receiverID = lastTrans.getReceiverID();
        String type = lastTrans.getType(); Long amount = lastTrans.getAmount();
        if(!type.equals("DEPOSIT")){
            fail("Transaction type is not 'DEPOSIT'");
        }
        if(!stfController.confirmTransaction(transID, senderID, receiverID, type, amount)){
            fail("Failed Confirm Transaction");
        }
        if(!authController.addTransAuthorize(staffID, transID, "CONFIRM")){
            fail("Failed Add Confirmed Transaction");
        }
    }

    @Test
    public void testRejectTransaction(){
        String customerID = "999009", staffID = "STF003";
        assertTrue("Can not make Deposit", makeDeposit(customerID));
        LocalDate now = LocalDate.now();
        List<Transaction> lst = transController.filterTransactions(customerID,1, 1900, 
                now.getMonthValue(), now.getYear(), "ALL", "ALL");
        if(lst.size() <= 0){
            fail("Transaction List is empty");
        }
        Transaction lastTrans = lst.get(lst.size() - 1);
        String transID = lastTrans.getTransID();
        String type = lastTrans.getType();
        if(!type.equals("DEPOSIT")){
            fail("Transaction type is not 'DEPOSIT'");
        }
        if(!transDAO.updateStatus(transID, "FAILED")){
            fail("Failed Reject Transaction");
        }
        if(!authController.addTransAuthorize(staffID, transID, "REJECT")){
            fail("Failed Add Rejected Transaction");
        }
    }

    
}
