package com.lastfarewells.backend.config;

import java.security.GeneralSecurityException;
import java.util.Properties;

import org.eclipse.angus.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailSenderConfig {
	
	
	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private String port;
	
	
	@Value("${spring.mail.protocol}")
	private String protocol;

	
	
	 @Bean
	    public JavaMailSender getJavaMailSender() {
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost(host);
	        mailSender.setPort(Integer.parseInt(port));

	        mailSender.setUsername(username);
	        mailSender.setPassword(password);

	        Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.transport.protocol", protocol);
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.starttls.required", "true");
	        props.put("mail.smtp.ssl.trust", host);
	        //props.put("mail.debug", "true");
	        MailSSLSocketFactory sf;
			try {
				sf = new MailSSLSocketFactory();
				 sf.setTrustAllHosts(true);
			        props.put("mail.smtp.ssl.socketFactory", sf);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       

	        return mailSender;
	    }
}
