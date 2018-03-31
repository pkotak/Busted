package edu.northeastern.cs5500;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Paarth
 *
 */
public class SendMailSSL implements ISendMailSSL {
	static final Logger LOGGER = Logger.getLogger(SendMailSSL.class.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(String password, String to, String sub, String msg, String filepath) {
		// Get properties object
		Properties props = getProperties(filepath);
		// get Session
		Session session = getSession(props);
		// compose message
		composeMessage(session, to, sub, msg);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Properties getProperties(String filepath) {
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(filepath);
			props.load(input);
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			return props;
		} catch (FileNotFoundException e) {
			LOGGER.info(e.toString());
			return null;
		} catch (IOException e) {
			LOGGER.info(e.toString());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Session getSession(final Properties props) {
		return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("from"), props.getProperty("pass"));
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void composeMessage(Session session, String to, String sub, String msg) {
		try {
			MimeMessage message = new MimeMessage(session);
			InternetAddress[] myToList = InternetAddress.parse(to);
//			InternetAddress[] myCcList = InternetAddress
//					.parse("murugesan.si@husky.neu.edu, xuan.k@husky.neu.edu, karwa.a@husky.neu.edu");
			  InternetAddress[] myCcList = InternetAddress.parse("paarthkotak@gmail.com");
			 
			message.addRecipients(Message.RecipientType.TO, myToList);
			message.addRecipients(Message.RecipientType.CC, myCcList);
			message.setSubject(sub);
			message.setText(msg);
			// send message
			Transport.send(message);
			LOGGER.info("message sent successfully");
		} catch (MessagingException e) {
			LOGGER.info(e.toString());
		}
	}
}