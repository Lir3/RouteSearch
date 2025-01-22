package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/campanylogin")
	public String showLoginForm(Model model) {
		// エラーメッセージを初期表示で渡さない
		return "campanylogin"; 
	}

	@PostMapping("/campanylogin")
	public String processLogin(@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model) {
		String role = loginService.authenticateUser(username, password);
		if (role != null) {
			// ロールによって画面を切り替え
			if ("admin".equals(role)) {
				return "redirect:/admindashboard";
			} else if ("general".equals(role)) {
				return "redirect:/generaldashboard";
			}
		}
		// 認証失敗時のみエラーメッセージを追加
		model.addAttribute("error", "ユーザーIDまたはパスワードが間違っています");
		return "campanylogin";
	}
}
