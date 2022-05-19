package com.oda.component.mail;

import com.oda.dto.MailSendDto;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Random;

public class MailSend {
    public Integer sendMail(MailSendDto mailSendDto) throws EmailException {
        //generate 6 digit pincode
        Random random = new Random();

        Integer pc = random.nextInt(999999);
        //get only 6 digit
        String pinCode = String.format("%06d",pc);

        //mail send process
        String username = "onlineassistance400@gmail.com";
        String password = "izfxybivpuotfhjq";

        Email email1 = new SimpleEmail();
        email1.setHostName("smtp.googlemail.com");
        email1.setSmtpPort(465);
        email1.setAuthentication(username,password);
        email1.setSSLOnConnect(true);
        email1.setFrom("onlineassistance400@gmail.com");
        email1.setSubject("Verification");
        email1.setMsg("Hey "+mailSendDto.getUserName()+",\n"+mailSendDto.getMessage()+"\nPinCode: "+pinCode);
        email1.addTo(mailSendDto.getEmail());
        email1.send();
        System.out.println("Mail send successfully");
        return Integer.valueOf(pinCode);
    }

    public void sendConfirmMail(MailSendDto mailSendDto) throws EmailException {
        //mail send process
        String username = "onlineassistance400@gmail.com";
        String password = "izfxybivpuotfhjq";

        Email email1 = new SimpleEmail();
        email1.setHostName("smtp.googlemail.com");
        email1.setSmtpPort(465);
        email1.setAuthentication(username, password);
        email1.setSSLOnConnect(true);
        email1.setFrom("onlineassistance400@gmail.com");
        email1.setSubject("Verification");
        email1.setMsg("Hey " + mailSendDto.getUserName() + ",\n" + mailSendDto.getMessage());
        email1.addTo(mailSendDto.getEmail());
        email1.send();
        System.out.println("Mail send successfully");
    }
}
