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

	public void insertMail(String mailaddress, String employeename) throws MailException {
		sendMail(mailaddress, employeename);
	}

	public void sendMail(String to, String employeename) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromAddress);
			message.setTo(to);
			message.setSubject("定期申請の承認について");
			message.setText("こんにちは " + employeename + " さん\n\n"
					+ "定期券代申請の承認結果が出ました。\n\n"
					+ "詳細は以下のURLからログインして、ダッシュボードの\n"
					+ "「定期券承認確認」から確認してください。\n"
					+ "http://localhost:8080/companylogin");

			mailSender.send(message);
			System.out.println("メール送信成功: " + to);
		} catch (MailException e) {
			System.err.println("メール送信エラー: " + e.getMessage());
		}
	}
}
