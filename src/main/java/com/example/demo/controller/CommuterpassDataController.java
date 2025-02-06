package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.RicdataRepository;
import com.example.demo.service.CommuterpassDataService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommuterpassDataController {

	private final RicdataRepository commuterpassDataRepository;
	private final CommuterpassDataService commuterpassDataService;

	public CommuterpassDataController(RicdataRepository commuterpassDataRepository,
			CommuterpassDataService commuterpassDataService) {
		this.commuterpassDataRepository = commuterpassDataRepository;
		this.commuterpassDataService = commuterpassDataService;
	}

	@GetMapping("/commuterpassdata")
	public String showCommuterPassList(Model model) {
		// データベースから全データを取得
		List<CommuterpassData> commuterpassDataList = commuterpassDataRepository.findAll();

		model.addAttribute("commuterpassdata", commuterpassDataList);

		return "commuterpassdata"; // commuterpassdata.html を表示
	}

	/** フォームの表示 */
	@GetMapping({ "/bicroutemap", "/bicinsurance" })
	public String showForm(HttpSession session, Model model, HttpServletRequest request) {
		String username = (String) session.getAttribute("username");

		if (username == null) {
			return "redirect:/companylogin"; // セッションがない場合ログイン画面へ
		}

		// ユーザーのデータを取得
		CommuterpassData commuterpassData = commuterpassDataService.getCommuterpassData(username);
		model.addAttribute("username", username);
		model.addAttribute("commuterpassData", commuterpassData);

		// リクエストURIを取得
		String requestURI = request.getRequestURI();

		// アクセス元のURLに応じてテンプレートを切り替え
		if (requestURI.contains("bicroutemap")) {
			return "bicroutemap";
		} else {
			return "bicinsurance";
		}
	}

	/** ricpage からのデータ保存 (全項目を更新) */
	@PostMapping("/newsave/save")
	public String saveCommuterpassDataFromRicpage(@ModelAttribute CommuterpassData commuterpassData,
			HttpSession session) {
		String username = (String) session.getAttribute("username");

		if (username == null) {
			return "redirect:/companylogin";
		}

		// ricpage からのデータを保存（全項目更新）
		commuterpassDataService.saveCommuterpassDataFrom(username, commuterpassData);

		return "redirect:/generaldashboard";
	}

	/** bicroutemap からのデータ保存 (nullでない値のみ更新) */
	@PostMapping("/overwritesave/save")
	public String saveCommuterpassData(
			@ModelAttribute CommuterpassData commuterpassData,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {

		String username = (String) session.getAttribute("username");

		if (username == null) {
			return "redirect:/companylogin";
		}

		// ファイルがアップロードされている場合、サーバーに保存
		if (file != null && !file.isEmpty()) {
			System.out.println("ファイルがアップロ-ドされました");
			try {
				// 保存先ディレクトリ（例: /uploads 以下に保存）
				String uploadDir = "uploads/";
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdirs(); // ディレクトリがない場合は作成
				}
				// 読み取り専用に設定
				directory.setWritable(false);
				directory.setExecutable(true, false); // ディレクトリの実行権限を設定
				directory.setReadable(true, false);

				// ファイルの保存
				String fileName = username + "_" + file.getOriginalFilename();
				Path filePath = Path.of(uploadDir, fileName);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// ファイルパスをデータにセット
				commuterpassData.setFilePath(filePath.toString());
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/error"; // エラー時はエラーページへ
			}
		}

		// bicroutemap からのデータを保存（nullでない値のみ更新）
		commuterpassDataService.saveCommuterpassDataFrom(username, commuterpassData);

		return "redirect:/generaldashboard";
	}
}
