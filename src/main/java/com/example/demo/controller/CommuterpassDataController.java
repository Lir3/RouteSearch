package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.RicdataRepository;

@Controller
public class CommuterpassDataController {

	private final RicdataRepository commuterpassDataRepository;

	public CommuterpassDataController(RicdataRepository commuterpassDataRepository) {
		this.commuterpassDataRepository = commuterpassDataRepository;
	}

	@GetMapping("/commuterpassdata")
	public String showCommuterPassList(Model model) {
		// データベースから全データを取得
		List<CommuterpassData> commuterpassDataList = commuterpassDataRepository.findAll();

		// デバッグログを追加（取得したデータを表示）
		//		System.out.println("取得したデータの件数: " + commuterpassDataList.size());
		//		for (CommuterpassData data : commuterpassDataList) {
		//			System.out.println(
		//					"データ: " + data.getUsername() + ", " + data.getEmployeename() + ", " + data.getMailaddress());
		//		}

		// Thymeleaf にデータを渡す
		model.addAttribute("commuterpassdata", commuterpassDataList);

		return "commuterpassdata"; // commuterpassdata.html を表示
	}
}
