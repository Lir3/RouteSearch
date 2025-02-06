package com.example.demo.service;

import java.io.File;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.CommuterpassData;
import com.example.demo.repository.CommuterpassDataRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CommuterpassDataService {

    private final CommuterpassDataRepository commuterpassDataRepository;

    public CommuterpassDataService(CommuterpassDataRepository commuterpassDataRepository) {
        this.commuterpassDataRepository = commuterpassDataRepository;
    }

    /** ユーザーのデータを取得（Optional型を返す） */
    public Optional<CommuterpassData> getCommuterpassDataOptional(String username) {
        return commuterpassDataRepository.findByUsername(username);
    }

    /** ユーザーのデータを取得 */
    public CommuterpassData getCommuterpassData(String username) {
        return commuterpassDataRepository.findByUsername(username).orElse(new CommuterpassData());
    }

    /** ユーザーのデータを保存（bicroutemap用：nullまたは値が変わった場合のみ更新） */
    @Transactional
    public void saveCommuterpassDataFrom(String username, CommuterpassData commuterpassData) {
        Optional<CommuterpassData> existingDataOpt = commuterpassDataRepository.findByUsername(username);

        if (existingDataOpt.isPresent()) {
            CommuterpassData data = existingDataOpt.get();

            // 新しい値が null でなく、かつ異なる場合のみ更新
            if (commuterpassData.getEmployeename() != null && !commuterpassData.getEmployeename().equals(data.getEmployeename())) {
                data.setEmployeename(commuterpassData.getEmployeename());
            }
            if (commuterpassData.getKana_name() != null && !commuterpassData.getKana_name().equals(data.getKana_name())) {
                data.setKana_name(commuterpassData.getKana_name());
            }
            if (commuterpassData.getAddress() != null && !commuterpassData.getAddress().equals(data.getAddress())) {
                data.setAddress(commuterpassData.getAddress());
            }
            if (commuterpassData.getMailaddress() != null && !commuterpassData.getMailaddress().equals(data.getMailaddress())) {
                data.setMailaddress(commuterpassData.getMailaddress());
            }
            if (commuterpassData.getNearest_station() != null && !commuterpassData.getNearest_station().equals(data.getNearest_station())) {
                data.setNearest_station(commuterpassData.getNearest_station());
            }
            if (commuterpassData.getArrival_station() != null && !commuterpassData.getArrival_station().equals(data.getArrival_station())) {
                data.setArrival_station(commuterpassData.getArrival_station());
            }
            if (commuterpassData.getBus_arrival_station() != null && !commuterpassData.getBus_arrival_station().equals(data.getBus_arrival_station())) {
                data.setBus_arrival_station(commuterpassData.getBus_arrival_station());
            }
            if (commuterpassData.getBus_departure_station() != null && !commuterpassData.getBus_departure_station().equals(data.getBus_departure_station())) {
                data.setBus_departure_station(commuterpassData.getBus_departure_station());
            }
            if (commuterpassData.getBic_move_distance() != null && !commuterpassData.getBic_move_distance().equals(data.getBic_move_distance())) {
                data.setBic_move_distance(commuterpassData.getBic_move_distance());
            }
            if (commuterpassData.getBic_move_time() != null && !commuterpassData.getBic_move_time().equals(data.getBic_move_time())) {
                data.setBic_move_time(commuterpassData.getBic_move_time());
            }
            if (commuterpassData.getFilePath() != null && !commuterpassData.getFilePath().equals(data.getFilePath())) {
                data.setFilePath(commuterpassData.getFilePath());
            }

            // 支給額の計算
            if (commuterpassData.getBic_move_distance() != null) {
                // "Km" または "km" を削除し、数値に変換
                String distanceStr = commuterpassData.getBic_move_distance().replaceAll("[^0-9.]", "");
                try {
                    double distance = Double.parseDouble(distanceStr);
                    if (distance >= 2 && distance < 10) {
                        data.setSupportAmount(4200); // 2km以上10km未満なら4200円
                    } else {
                        data.setSupportAmount(0); // 他の条件の場合は0円
                    }
                } catch (NumberFormatException e) {
                    // 数値に変換できない場合のエラーハンドリング
                    System.out.println("距離のフォーマットエラー: " + commuterpassData.getBic_move_distance());
                    data.setSupportAmount(0); // 変換できない場合は支給額を0円にする
                }
            }


            commuterpassDataRepository.save(data);
        }
    }

    @PostConstruct
    public void init() {
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // サーバーのuploadsディレクトリを作成
        }
    }
}