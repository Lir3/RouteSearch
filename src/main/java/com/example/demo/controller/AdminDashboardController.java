package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminDashboardController {

	@GetMapping("/admindashboard")
	public String adminDashboard(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username"); // セッションから username を取得
		model.addAttribute("username", username);
		return "admindashboard";
	}
}
