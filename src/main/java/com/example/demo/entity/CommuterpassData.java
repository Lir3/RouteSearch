package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "commuterpassdata")
public class CommuterpassData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String employeename;
	private String kana_name;
	private String address;
	private String mailaddress;
	private String nearest_station;
	private String arrival_station;
	private String use_bicycle;
	private int bic_move_distance;
	private int bic_move_time;
	private String use_bus;
	private String bus_arrival_station;
	private String bus_departure_station;
	private int rootselect_num;

	//getter,setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getKana_name() {
		return kana_name;
	}

	public void setKana_name(String kana_name) {
		this.kana_name = kana_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMailaddress() {
		return mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public String getNearest_station() {
		return nearest_station;
	}

	public void setNearest_station(String nearest_station) {
		this.nearest_station = nearest_station;
	}

	public String getArrival_station() {
		return arrival_station;
	}

	public void setArrival_station(String arrival_station) {
		this.arrival_station = arrival_station;
	}

	public String getUse_bicycle() {
		return use_bicycle;
	}

	public void setUse_bicycle(String use_bicycle) {
		this.use_bicycle = use_bicycle;
	}

	public int getBic_move_distance() {
		return bic_move_distance;
	}

	public void setBic_move_distance(int bic_move_distance) {
		this.bic_move_distance = bic_move_distance;
	}

	public int getBic_move_time() {
		return bic_move_time;
	}

	public void setBic_move_time(int bic_move_time) {
		this.bic_move_time = bic_move_time;
	}

	public String getUse_bus() {
		return use_bus;
	}

	public void setUse_bus(String use_bus) {
		this.use_bus = use_bus;
	}

	public String getBus_arrival_station() {
		return bus_arrival_station;
	}

	public void setBus_arrival_station(String bus_arrival_station) {
		this.bus_arrival_station = bus_arrival_station;
	}

	public String getBus_departure_station() {
		return bus_departure_station;
	}

	public void setBus_departure_station(String bus_departure_station) {
		this.bus_departure_station = bus_departure_station;
	}

	public int getRootselect_num() {
		return rootselect_num;
	}

	public void setRootselect_num(int rootselect_num) {
		this.rootselect_num = rootselect_num;
	}

}
