package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.RicpageRepository;

@Controller
public class RicdataController {

	private final RicpageRepository ricpageRepository;

	public RicdataController(RicpageRepository ricpageRepository) {
		this.ricpageRepository = ricpageRepository;
	}

	@GetMapping("/ricdata/{username}")
	public String showRicdata(@PathVariable("username") String username, Model model) {
		Optional<CommuterpassData> data = ricpageRepository.findByUsername(username);

		if (data.isPresent()) {
			model.addAttribute("commuterpassData", data.get());
			return "ricdata"; // ricdata.html を表示
		} else {
			return "error"; // データが存在しない場合のエラーページ
		}
	}
}
