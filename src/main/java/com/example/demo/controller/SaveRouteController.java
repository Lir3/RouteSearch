package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<String> saveRoute(@RequestBody RouteSaveRequest request,@ModelAttribute CommuterpassData commuterpassData, HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("セッションがありません");
        }

        System.out.println("ログイン中のユーザー: " + username);
        Optional<CommuterpassData> commuterPassOpt = ricpageRepository.findByUsername(username);
        
        CommuterpassData commuterPass;
        
     // address が NULL または空文字の場合にデフォルト値を設定
        if (commuterpassData.getAddress() == null || commuterpassData.getAddress().trim().isEmpty()) {
        	commuterpassData.setAddress("");
        }
        
		if (commuterpassData.getEmployeename() == null || commuterpassData.getEmployeename().trim().isEmpty()) {
			commuterpassData.setEmployeename("");
		}
		
		if (commuterpassData.getKana_name() == null || commuterpassData.getKana_name().trim().isEmpty()) {
			commuterpassData.setKana_name("");
        }
		
		if (commuterpassData.getMailaddress() == null || commuterpassData.getMailaddress().trim().isEmpty()) {
            commuterpassData.setMailaddress("");
        }
        if (commuterPassOpt.isPresent()) {
            commuterPass = commuterPassOpt.get();
            System.out.println("既存のデータを更新します");
        } else {
            commuterPass = new CommuterpassData();
            commuterPass.setUsername(username); // 新しいデータにユーザー名を設定
            System.out.println("新しいデータを作成します");
        }

        // データをセット
        commuterPass.setUsername(username);
        commuterPass.setAddress(commuterpassData.getAddress());
        commuterPass.setEmployeename(commuterpassData.getEmployeename());
        commuterPass.setKana_name(commuterpassData.getKana_name());
        commuterPass.setMailaddress(commuterpassData.getMailaddress());
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

        // データを保存（新規 or 更新）
        ricpageRepository.save(commuterPass);

        return ResponseEntity.ok("経路情報を保存しました");
    }
}
