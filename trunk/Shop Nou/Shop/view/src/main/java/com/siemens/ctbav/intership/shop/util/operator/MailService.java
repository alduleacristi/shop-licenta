package com.siemens.ctbav.intership.shop.util.operator;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {

	static final String username = "shop4j@yahoo.com",
			password = "Licenta1";
	static String smtpServ = "smtp.mail.yahoo.com";

	public static void sendLink(String to, String subject, String mess,
			boolean isHtml) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpServ);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		
		if (isHtml) {
			System.out.println("trimit html");
			MimeMessage message= new MimeMessage(session);
			message.setContent(mess, "text/html; charset=utf-8");
			message.saveChanges();
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			Transport.send(message);
		}
		else{
		Message message2 = new MimeMessage(session);
		message2.setFrom(new InternetAddress(username));
		message2.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
		message2.setSubject(subject);
		message2.setText(mess);
		Transport.send(message2);
		}

		

		System.out.println("Done");

	}
}
