package com.luxurycity.clc.vo;

import java.util.Arrays;
import java.lang.Comparable;
import com.luxurycity.clc.util.*;

public class StationVO {

	private int station_id, district_cd, route_id, route_cd, next_station_id, start_id, end_id, waittime, str_order, cnt, str_order_cnt, distance;
	private double loc_x, loc_y;
	private String region, station_nm, transfer_nm,mobile_no, route_nm, keyword, route_tp, st_sta_nm,ed_sta_nm, next_station_nm, direction, start_nm, end_nm, staregion;
	private PageUtil page;
	private String[] route_list;
	
	


	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}

	
	public int getStr_order_cnt() {
		return str_order_cnt;
	}
	public void setStr_order_cnt(int str_order_cnt) {
		this.str_order_cnt = str_order_cnt;
	}
	public String getStaregion() {
		return staregion;
	}
	public void setStaregion(String staregion) {
		this.staregion = staregion;
	}

	public String getTransfer_nm() {
		return transfer_nm;
	}
	public void setTransfer_nm(String transfer_nm) {
		this.transfer_nm = transfer_nm;
	}
	public String[] getRoute_list() {
		return route_list;
	}
	public void setRoute_list(String[] route_list) {
		this.route_list = route_list;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getWaittime() {
		return waittime;
	}
	public void setWaittime(int waittime) {
		this.waittime = waittime;
	}
	
	public int getStr_order() {
		return str_order;
	}
	public void setStr_order(int str_order) {
		this.str_order = str_order;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getNext_station_id() {
		return next_station_id;
	}
	public void setNext_station_id(int next_station_id) {
		this.next_station_id = next_station_id;
	}
	public String getNext_station_nm() {
		return next_station_nm;
	}
	public void setNext_station_nm(String next_station_nm) {
		this.next_station_nm = next_station_nm;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public PageUtil getPage() {
		return page;
	}
	public void setPage(PageUtil page) {
		this.page = page;
	}
	public int getStation_id() {
		return station_id;
	}
	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}
	
	public String getRoute_tp() {
		return route_tp;
	}
	public void setRoute_tp(String route_tp) {
		this.route_tp = route_tp;
	}
	public String getSt_sta_nm() {
		return st_sta_nm;
	}
	public void setSt_sta_nm(String st_sta_nm) {
		this.st_sta_nm = st_sta_nm;
	}
	public String getEd_sta_nm() {
		return ed_sta_nm;
	}
	public void setEd_sta_nm(String ed_sta_nm) {
		this.ed_sta_nm = ed_sta_nm;
	}
	public int getDistrict_cd() {
		return district_cd;
	}
	public void setDistrict_cd(int district_cd) {
		this.district_cd = district_cd;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public int getRoute_cd() {
		return route_cd;
	}
	public void setRoute_cd(int route_cd) {
		this.route_cd = route_cd;
	}
	public double getLoc_x() {
		return loc_x;
	}
	public void setLoc_x(double loc_x) {
		this.loc_x = loc_x;
	}
	public double getLoc_y() {
		return loc_y;
	}
	public void setLoc_y(double loc_y) {
		this.loc_y = loc_y;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getStation_nm() {
		return station_nm;
	}
	public void setStation_nm(String station_nm) {
		this.station_nm = station_nm;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getRoute_nm() {
		return route_nm;
	}
	public void setRoute_nm(String route_nm) {
		this.route_nm = route_nm;
	}
	public int getStart_id() {
		return start_id;
	}
	public void setStart_id(int start_id) {
		this.start_id = start_id;
	}
	public int getEnd_id() {
		return end_id;
	}
	public void setEnd_id(int end_id) {
		this.end_id = end_id;
	}
	public String getStart_nm() {
		return start_nm;
	}
	public void setStart_nm(String start_nm) {
		this.start_nm = start_nm;
	}
	public String getEnd_nm() {
		return end_nm;
	}
	public void setEnd_nm(String end_nm) {
		this.end_nm = end_nm;
	}
	
}