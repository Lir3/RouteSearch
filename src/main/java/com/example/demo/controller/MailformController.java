package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@PostMapping("/mailform/submit")
	public String submitApproval(
			HttpSession session,
			@RequestParam(value = "select_approve", required = false, defaultValue = "yes") String selectApprove,
			@RequestParam(value = "reason", required = false) String reason,
			Model model) {

		String username = (String) session.getAttribute("username");

		if (username == null) {
			return "redirect:/companylogin";
		}

		// データベースから取得
		Optional<CommuterpassData> existingData = ricpageRepository.findByUsername(username);

		if (existingData.isPresent()) {
			CommuterpassData commuterpassData = existingData.get();

			// `〇` or `×` に変換して保存
			commuterpassData.setSelect_approve("yes".equals(selectApprove) ? "〇" : "×");

			// 理由がある場合のみ保存
			if (reason != null && !reason.trim().isEmpty()) {
				commuterpassData.setApproval_reason(reason);
			}

			try {
				ricpageRepository.save(commuterpassData);
			} catch (Exception e) {
				model.addAttribute("errorMessage", "データの保存に失敗しました: " + e.getMessage());
				return "mailform"; // エラー時は同じページに戻る
			}

		} else {
			model.addAttribute("errorMessage", "データが見つかりませんでした。");
			return "mailform";
		}

		return "redirect:/generaldashboard"; // 成功時のリダイレクト
	}
}
