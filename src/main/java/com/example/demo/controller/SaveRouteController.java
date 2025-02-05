package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.RicpageRepository;
import com.example.demo.util.RouteSaveRequest;

import jakarta.servlet.http.HttpSession;

@RestController
public class SaveRouteController {

	@Autowired
	private RicpageRepository ricpageRepository;

	@PostMapping("/saveRoute")
	@ResponseBody
	public ResponseEntity<String> saveRoute(@RequestBody RouteSaveRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");

		if (username == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("セッションがありません");
		}
		System.out.println("ログイン中のユーザー: " + username);
		Optional<CommuterpassData> commuterPassOpt = ricpageRepository.findByUsername(username);
		if (!commuterPassOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ユーザーのデータが見つかりません");
		}

		CommuterpassData commuterPass = commuterPassOpt.get();
		commuterPass.setArrival_station(request.getArrival_station());
		commuterPass.setNearest_station(request.getNearest_station());
		commuterPass.setTravelTimeTransportation(request.getTravelTimeTransportation());
		commuterPass.setTravelTimeWalk(request.getTravelTimeWalk());
		commuterPass.setTransferCount(request.getTransferCount());
		commuterPass.setFare(request.getFare());
		commuterPass.setTeiki1(request.getTeiki1());
		commuterPass.setTeiki3(request.getTeiki3());
		commuterPass.setTeiki6(request.getTeiki6());
		commuterPass.setRouteInfo(request.getRouteInfo());

		ricpageRepository.save(commuterPass);

		return ResponseEntity.ok("経路情報を保存しました");
	}
}
