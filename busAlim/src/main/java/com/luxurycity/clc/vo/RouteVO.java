package com.luxurycity.clc.vo;
import com.luxurycity.clc.util.*;

public class RouteVO {
	private int route_id, route_cd, st_sta_id, ed_sta_id, peek_alloc, npeek_alloc, district_cd, station_id, str_order;
	private String route_nm, up_first_time, up_last_time, down_first_time, down_last_time, region, station_nm, mobile_no, direction, route_tp, st_sta_nm, ed_sta_nm, keyword;
	private double loc_x, loc_y;
	private PageUtil page;
	
	
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
	public int getSt_sta_id() {
		return st_sta_id;
	}
	public void setSt_sta_id(int st_sta_id) {
		this.st_sta_id = st_sta_id;
	}
	public int getEd_sta_id() {
		return ed_sta_id;
	}
	public void setEd_sta_id(int ed_sta_id) {
		this.ed_sta_id = ed_sta_id;
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
	public int getPeek_alloc() {
		return peek_alloc;
	}
	public void setPeek_alloc(int peek_alloc) {
		this.peek_alloc = peek_alloc;
	}
	public int getNpeek_alloc() {
		return npeek_alloc;
	}
	public void setNpeek_alloc(int npeek_alloc) {
		this.npeek_alloc = npeek_alloc;
	}
	public int getDistrict_cd() {
		return district_cd;
	}
	public void setDistrict_cd(int district_cd) {
		this.district_cd = district_cd;
	}
	public int getStation_id() {
		return station_id;
	}
	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}
	public int getStr_order() {
		return str_order;
	}
	public void setStr_order(int str_order) {
		this.str_order = str_order;
	}
	public String getRoute_nm() {
		return route_nm;
	}
	public void setRoute_nm(String route_nm) {
		this.route_nm = route_nm;
	}
	public String getUp_first_time() {
		return up_first_time;
	}
	public void setUp_first_time(String up_first_time) {
		this.up_first_time = up_first_time;
	}
	public String getUp_last_time() {
		return up_last_time;
	}
	public void setUp_last_time(String up_last_time) {
		this.up_last_time = up_last_time;
	}
	public String getDown_first_time() {
		return down_first_time;
	}
	public void setDown_first_time(String down_first_time) {
		this.down_first_time = down_first_time;
	}
	public String getDown_last_time() {
		return down_last_time;
	}
	public void setDown_last_time(String down_last_time) {
		this.down_last_time = down_last_time;
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getRoute_tp() {
		return route_tp;
	}
	public void setRoute_tp(String route_tp) {
		this.route_tp = route_tp;
	}
	@Override
	public String toString() {
		return "RouteVO [route_id=" + route_id + ", route_cd=" + route_cd + ", st_sta_id=" + st_sta_id + ", ed_sta_id="
				+ ed_sta_id + ", peek_alloc=" + peek_alloc + ", npeek_alloc=" + npeek_alloc + ", district_cd="
				+ district_cd + ", station_id=" + station_id + ", str_order=" + str_order + ", route_nm=" + route_nm
				+ ", up_first_time=" + up_first_time + ", up_last_time=" + up_last_time + ", down_first_time="
				+ down_first_time + ", down_last_time=" + down_last_time + ", region=" + region + ", station_nm="
				+ station_nm + ", mobile_no=" + mobile_no + ", direction=" + direction + ", route_tp=" + route_tp
				+ ", st_sta_nm=" + st_sta_nm + ", ed_sta_nm=" + ed_sta_nm + ", keyword=" + keyword + ", page=" + page
				+ "]";
	}
	
}
