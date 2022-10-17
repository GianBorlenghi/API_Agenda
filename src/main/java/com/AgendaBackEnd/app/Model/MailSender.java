package com.AgendaBackEnd.app.Model;

import java.util.ArrayList;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MailSender {

	
public static void sendMail(String to, String subjet, String text) throws AddressException, MessagingException {
		
	
		
		Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.user", "borlenghigian");
	    props.put("mail.smtp.password", "qxdcxutjckutoppw");    
	    props.put("mail.smtp.auth", "true");    
	    props.put("mail.smtp.starttls.enable", "true"); 
	    props.put("mail.smtp.port", "587"); 
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");    

		
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    
	    message.setFrom(new InternetAddress("borlenghigian"));
        message.addRecipients(Message.RecipientType.TO, to); 
        message.setSubject(subjet);
        message.setText(text);
        Transport transport = session.getTransport("smtp");
  
			transport.connect("smtp.gmail.com", "borlenghigian", "qxdcxutjckutoppw");
		
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
	}
	
	public static String newPassword(String newPassword) {
		String text = "Hello, "
				+ "\nyour new password will be: "+newPassword+"\n"
						+ "\nWe recommend changing it immediately\n"
						+"\nRegards,\n"
						+"\nToDoList team.";
		return text;
}
	
	public static String TaskLists(String tasks) throws JsonMappingException, JsonProcessingException {
		
        ObjectMapper mapper = new ObjectMapper();
        
        Task[] tasksList= mapper.readValue(tasks, Task[].class);
        
        String t = "";
        
        for(Task task : tasksList) {
        
        	t+=task.getTask_name()+","; 
        }	
        StringBuilder sb = new StringBuilder(t);
       
        
		String text = "Hello, "
				+ "\nYour task(s) :"+sb.deleteCharAt(t.length() - 1 )+"\n"
						+ "\nThe previous tasks are about to finish. If you have already done them, please mark them as finished.\n";
		return text;
}
	
}
