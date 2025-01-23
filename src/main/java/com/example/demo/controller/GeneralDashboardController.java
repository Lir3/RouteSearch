package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralDashboardController {

	@GetMapping("/generaldashboard")
	public String generalDashboard(HttpSession session, Model model) {
		String userid = (String) session.getAttribute("userid"); // セッションから userid を取得
		model.addAttribute("userid", userid);
		return "generaldashboard";
	}
}