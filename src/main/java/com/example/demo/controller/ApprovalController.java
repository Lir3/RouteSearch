package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.form.MailForm;
import com.example.demo.repository.RicpageRepository;
import com.example.demo.service.MailService;

@Controller
public class ApprovalController {
	private final RicpageRepository ricpageRepository;
	private final MailService mailService;

	@Autowired
	public ApprovalController(RicpageRepository ricpageRepository, MailService mailService) {
		this.ricpageRepository = ricpageRepository;
		this.mailService = mailService;
	}

	@GetMapping("/approval/{username}")
	public String showApproval(@PathVariable("username") String username, Model model) {
		Optional<CommuterpassData> data = ricpageRepository.findByUsername(username);
		if (data.isPresent()) {
			model.addAttribute("commuterpassData", data.get());
			model.addAttribute("mailForm", new MailForm()); // 空のフォームをセット
			return "approval";
		} else {
			return "error";
		}
	}

	@PostMapping("/sendMail")
	public String sendMail(@Validated MailForm mailForm, BindingResult bindingResult,
			@RequestParam String mailaddress,
			@RequestParam String employeename,
			Model model) {

		// バリデーションチェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "メールアドレスを正しく入力してください。");
			return "approval";
		}

		// メール送信
		System.out.println("ここはうまくいってるよ");
		mailService.insertMail(mailaddress, employeename);
		return "redirect:/approval/sendcomp";
	}

	@GetMapping("/approval/sendcomp")
	public String showSuccessPage() {
		return "sendcomp";
	}
}