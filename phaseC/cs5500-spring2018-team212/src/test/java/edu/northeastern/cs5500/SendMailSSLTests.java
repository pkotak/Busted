package edu.northeastern.cs5500;

import org.junit.Test;

public class SendMailSSLTests {

	@Test
	public void testMailSend() {
		SendMailSSL mailSSL = new SendMailSSL();
		mailSSL.send("^EjHs0R4&wot", "kotak.p@husky.neu.edu", "ATTENTION!","Test project status mail from server.",System.getProperty("user.dir")+"/config.properties");
	}

}
