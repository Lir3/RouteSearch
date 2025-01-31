package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.LoginInfo;
import com.example.demo.repository.LoginInfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompanyLoginController {

	private final LoginInfoRepository loginInfoRepository;

	public CompanyLoginController(LoginInfoRepository loginInfoRepository) {
		this.loginInfoRepository = loginInfoRepository;
	}

	@GetMapping("/companylogin")
	public String companyloginPage() {
		return "companylogin";
	}

	@PostMapping("/companylogin")
	public String login(@RequestParam String username, // パラメータ名を変更
			@RequestParam String password,
			HttpSession session,
			Model model) {
		Optional<LoginInfo> loginInfo = loginInfoRepository.findByusername(username); // メソッド名を変更

		if (loginInfo.isPresent() && loginInfo.get().getPassword().equals(password)) {
			session.setAttribute("username", username); // セッションに username を保存
			if ("ADMIN".equalsIgnoreCase(loginInfo.get().getRole())) {
				return "redirect:/admindashboard";
			} else {
				return "redirect:/generaldashboard";
			}
		} else {
			model.addAttribute("error", "ユーザー名またはパスワードが間違っています。");
			return "companylogin"; // エラーを表示してログインページに戻る
		}
	}
}