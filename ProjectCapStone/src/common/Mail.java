package common;



import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Mail {
	
	
  
  public boolean sendMail(String address, String message) {
	  
	  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	  
	  // Get a Properties object
	  
	     Properties props = System.getProperties();
	     props.setProperty("mail.smtp.host", "smtp.gmail.com");
	     props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	     props.setProperty("mail.smtp.socketFactory.fallback", "false");
	     props.setProperty("mail.smtp.port", "465");
	     props.setProperty("mail.smtp.socketFactory.port", "465");
	     props.put("mail.smtp.auth", "true");
	     props.put("mail.debug", "true");
	     props.put("mail.store.protocol", "pop3");
	     props.put("mail.transport.protocol", "smtp");
	     final String username = "projectidp2021@gmail.com";//
	     final String password = "xohsy9-cYpmem-myggum";
	     
	     try{
	    	 
	       Session session = Session.getDefaultInstance(props, 
	                           new Authenticator(){
	                              protected PasswordAuthentication getPasswordAuthentication() {
	                                 return new PasswordAuthentication(username, password);
	                              }});

	    // -- Create a new message --
	       Message msg = new MimeMessage(session);

	    // -- Set the FROM and TO fields --
	       msg.setFrom(new InternetAddress("projectidp2021@gmail.com"));
	       msg.setRecipients(Message.RecipientType.TO, 
	                         InternetAddress.parse(address,false));
	       msg.setSubject("Upcoming Vaccination Appointment Reminder");
	       msg.setText(message);
	       msg.setSentDate(new Date());
	       Transport.send(msg);
	       System.out.println("Message sent.");
	       
	       return true;
	       
	     }catch (MessagingException e){ 
	       System.out.println("Erreur d'envoi, cause: " + e);
	       return false;
	     }
}



  
}