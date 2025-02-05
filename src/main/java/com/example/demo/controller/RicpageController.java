package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.RicpageRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class RicpageController {

	private final RicpageRepository ricpageRepository;

	public RicpageController(RicpageRepository ricpageRepository) {
		this.ricpageRepository = ricpageRepository;
	}

	@GetMapping("/ricpage")
	public String showForm(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");

		if (username == null) {
			return "redirect:/companylogin"; // セッションがない場合ログイン画面へ
		}

		Optional<CommuterpassData> existingData = ricpageRepository.findByUsername(username);
		model.addAttribute("username", username);
		model.addAttribute("commuterpassData", existingData.orElse(new CommuterpassData()));

		return "ricpage";
	}

	@PostMapping("/ricpage/save")
	public String saveCommuterpassData(
			@ModelAttribute CommuterpassData commuterpassData,
			HttpSession session) {

		String username = (String) session.getAttribute("username");
		if (username == null) {
			return "redirect:/companylogin";
		}

		Optional<CommuterpassData> existingData = ricpageRepository.findByUsername(username);

		CommuterpassData data = existingData.orElse(new CommuterpassData()); // データがなければ新規作成
		data.setUsername(username); // 必ず username をセットする
		data.setEmployeename(commuterpassData.getEmployeename());
		data.setKana_name(commuterpassData.getKana_name());
		data.setAddress(commuterpassData.getAddress());
		data.setMailaddress(commuterpassData.getMailaddress());
		data.setNearest_station(commuterpassData.getNearest_station());
		data.setArrival_station(commuterpassData.getArrival_station());
		data.setBic_move_distance(commuterpassData.getBic_move_distance());
		data.setBic_move_time(commuterpassData.getBic_move_time());
		data.setBus_arrival_station(commuterpassData.getBus_arrival_station());
		data.setBus_departure_station(commuterpassData.getBus_departure_station());

		// トグルボタンの値を〇/×に変換（nullチェックを追加）
		data.setUse_bicycle(commuterpassData.getUse_bicycle() != null ? "〇" : "×");
		data.setUse_bus(commuterpassData.getUse_bus() != null ? "〇" : "×");

		ricpageRepository.save(data);

		return "redirect:/generaldashboard";
	}

}