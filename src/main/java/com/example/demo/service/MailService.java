package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromAddress;

	public void insertMail(String mailaddress, String employeename, String rootselect) throws MailException {
		sendMail(mailaddress, employeename, rootselect);
	}

	public void sendMail(String to, String employeename, String root) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromAddress);
			message.setTo(to);
			message.setSubject("定期申請の承認について");
			message.setText("こんにちは " + employeename + " さん\n\n"
					+ "以下のルートで承認されました: " + root + "\n\n"
					+ "詳細は以下のURLを確認してください。\n"
					+ "http://localhost:8080/aspage");

			mailSender.send(message);
			System.out.println("メール送信成功: " + to);
		} catch (MailException e) {
			System.err.println("メール送信エラー: " + e.getMessage());
		}
	}
}
