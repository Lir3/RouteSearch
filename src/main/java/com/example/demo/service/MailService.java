package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.form.MailForm;
import com.example.demo.model.Mail;


@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
    private String fromAddress;

	//入力項目をDBに保存
	public void insertMail(MailForm mailForm) {
		Mail mail = new Mail();

		// メールを送信
		sendVerificationEmail(mailForm.getEmail());
		mail.setEmail(mailForm.getEmail());
	}

	//メール送信内容
	private void sendVerificationEmail(String to) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromAddress);
			message.setTo(to);
			message.setSubject("定期申請の承認について");
			message.setText("以下のurlを開いてください\n\n"
					+ "http://localhost:8080/aspage"
					+ "\n\n"
					+ "このメールに覚えのない場合は、メールを削除してください。");
			mailSender.send(message);
		}
}