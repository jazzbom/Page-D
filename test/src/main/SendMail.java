package main;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




public class SendMail {
	final String username = "dbzloaded@gmail.com";
	final String password = "Q_4QeQ-PBB2qh6uT2ceUeQ";
	
public void sendMail(String subject, String msg)
{
	

	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.mandrillapp.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getInstance(props,
	  new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	  });

	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("dbzloaded@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse("dbzloaded@gmail.com"));
		message.setSubject(subject);
		message.setText(msg);

		Transport.send(message);

		System.out.println("Mail sent!");

	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}
}
	
	
}
