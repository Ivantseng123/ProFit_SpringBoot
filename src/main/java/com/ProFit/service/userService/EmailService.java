package com.ProFit.service.userService;

import java.util.Collection;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async //非同步發送郵件
	public void sendSimpleHtml(Collection<String> receivers, String subject, String content) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(receivers.toArray(new String[0]));
			helper.setSubject(subject);
			helper.setText(content, true);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
//	@Async
//	public void sendMixedHtml(Collection<String> receivers, String subject, String htmlContent, Collection<DataSource> attachments) {
//	    try {
//	        // 設置收件人和主旨
//	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
//	        helper.setTo(receivers.toArray(new String[0]));
//	        helper.setSubject(subject);
//
//	        // 郵件內容由多個部份組成
//	        Multipart mimeMultipart = new MimeMultipart();
//	        mimeMessage.setContent(mimeMultipart);
//
//	        // 添加內文部份
//	        MimeBodyPart contentBodyPart = new MimeBodyPart();
//	        contentBodyPart.setContent(htmlContent, "text/html;charset=utf-8");
//	        mimeMultipart.addBodyPart(contentBodyPart);
//
//	        // 添加附件們
//	        for (DataSource source : attachments) {
//	            MimeBodyPart sourceBodyPart = new MimeBodyPart();
//	            
//	            // 用於一般附件
//	            sourceBodyPart.setFileName(source.getName());
//	            sourceBodyPart.setDataHandler(new DataHandler(source));
//	            
//	            // 用於內嵌圖片
//	            sourceBodyPart.setContentID("<" + source.getName() + ">");
//
//	            // 添加一個附件
//	            mimeMultipart.addBodyPart(sourceBodyPart);
//	        }
//
//	        // 儲存變更並發送郵件
//	        mimeMessage.saveChanges();
//	        javaMailSender.send(mimeMessage);
//	    } catch (MessagingException e) {
//	        throw new RuntimeException(e);
//	    }
//	}
	
//	@Async
//	public void sendEmail(SimpleMailMessage email) {
//		javaMailSender.send(email);
//	}

}
