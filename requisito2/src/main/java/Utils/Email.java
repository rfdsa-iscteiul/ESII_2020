package Utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;  
import javax.mail.internet.*;   

public class Email {
	
	private final static String HOST="smtp.gmail.com";  
	private final static String USER="sid.museu.info@gmail.com"; 
	private final static String PASSWORD="sid_2020"; 
	/**
	 * Send email using gmail host smtp service.
	 * FROM "sid.museu.info@gmail.com" account
	 * TO email field in config.ini file 
	 * @param subject - subject of the email 
	 * @param text - message of the email
	 */
	public static void sendEmail( String subject, String text){  
		String to = null;
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/main/resources/config.ini"));
			to = p.getProperty("email");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();  
		props.put("mail.smtp.host", HOST);  
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(USER ,PASSWORD);  
			}  
		}); 
		props.put("mail.smtp.starttls.enable", "true");
		try {  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(USER));  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			message.setSubject(subject);  
			message.setText(text);
			Transport.send(message);   
		} catch (MessagingException e) {
			e.printStackTrace();
			System.err.println("FAILED to send email to " + to + " with message: " + text);
		}  
	}  

}
