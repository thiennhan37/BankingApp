/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.TransactionDAO;
import java.time.LocalDateTime;

/**
 *
 * @author Hi
 */
public class Transaction implements Comparable<Transaction>{
    public static int number = TransactionDAO.getInstance().countTransaction();
    private String transID, senderID, receiverID;
    private Long amount;
    private String type;
    private LocalDateTime sendTime, receiveTime;
    private String status, description;

    public Transaction() {
    }

    public Transaction(String senderID, String receiverID, Long amount, String type, String status, LocalDateTime sendTime, LocalDateTime receiveTime, String description, String transID) {
        this.transID = transID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.amount = amount;
        this.type = type;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
        this.status = status;
        this.description = description;
    }

    public Transaction(String senderID, String receiverID, Long amount, String type, String status, LocalDateTime sendTime, LocalDateTime receiveTime, String description) {
        this.transID = "TRANS" + String.format("%03d", (++number)); 
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.amount = amount;
        this.type = type;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
        this.status = status;
        this.description = description;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        Transaction.number = number;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Transaction o) {
        return -transID.compareTo(o.transID);
                
    }
    
    

    
    
}
