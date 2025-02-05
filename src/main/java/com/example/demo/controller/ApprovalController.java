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
	public String sendMail(@PathVariable("username") String username, @Validated MailForm mailForm,
			BindingResult bindingResult,
			@RequestParam String mailaddress,
			@RequestParam String employeename,
			@RequestParam("rootselect") String rootselectStr,
			Model model) {

		// バリデーションチェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "メールアドレスを正しく入力してください。");
			return "approval";
		}

		try {
			// 3. `rootselect` を `int` に変換
			int rootselect;
			try {
				rootselect = Integer.parseInt(rootselectStr);
			} catch (NumberFormatException e) {
				model.addAttribute("errorMessage", "ルート選択が無効です。");
				return "approval";
			}

			// 4. `username` に対応する `commuterpassdata` を取得し、`rootselect_num` を保存
			Optional<CommuterpassData> dataOpt = ricpageRepository.findByUsername(username);

			if (dataOpt.isPresent()) {
				CommuterpassData data = dataOpt.get();
				data.setRootselect_num(rootselect); // 選択したルートを保存
				ricpageRepository.save(data); // データを保存

				// 5. メール送信
				mailService.insertMail(data.getMailaddress(), data.getEmployeename(), rootselectStr);

				return "redirect:/approval/sendcomp"; // 送信成功時

			} else {
				model.addAttribute("errorMessage", "ユーザー情報が見つかりませんでした。");
				return "approval";
			}

		} catch (Exception e) {
			model.addAttribute("errorMessage", "送信に失敗しました。" + e.getMessage());
			return "approval"; // 送信失敗時
		}
	}

	@GetMapping("/approval/sendcomp")
	public String showSuccessPage() {
		return "sendcomp";
	}
}
