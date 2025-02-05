package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.RicpageRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MailformController {

	private final RicpageRepository ricpageRepository;

	public MailformController(RicpageRepository ricpageRepository) {
		this.ricpageRepository = ricpageRepository;
	}

	@GetMapping("/mailform")
	public String showForm(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");

		if (username == null) {
			return "redirect:/companylogin"; // セッションがない場合ログイン画面へ
		}

		Optional<CommuterpassData> existingData = ricpageRepository.findByUsername(username);
		model.addAttribute("username", username);
		model.addAttribute("commuterpassData", existingData.orElse(new CommuterpassData()));

		return "mailform";
	}
}
