/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class SendMail {
    public static String sendVerificationCode(String toEmail) {
        // 1. Sinh mã 6 chữ số ngẫu nhiên
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(999999));

        // 2. Thông tin email gửi
        final String fromEmail = "nhan88thien@gmail.com"; // Gmail gửi
        final String password = "byvp zruk fhzo gvtu";      // App Password (16 ký tự)

        // 3. Cấu hình SMTP server của Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true"); // xác thực 2 bước 
        props.put("mail.smtp.starttls.enable", "true");

        // 4. Tạo phiên gửi mail
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // 5. Soạn nội dung email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Banking App")); // tên hiển thị
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã xác nhận từ Banking App");
            message.setText("Xin chào,\n\nMã xác nhận của bạn là: " + code + 
                            "\nMã này có hiệu lực trong 5 phút.\n\nTrân trọng,\nBanking App Team");

            // 6. Gửi mail
            Transport.send(message);
            System.out.println("Đã gửi email đến: " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return code;
    }

}
