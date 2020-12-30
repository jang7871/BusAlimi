package com.luxurycity.clc.vo;

import java.sql.Date;
import java.sql.Time;
import java.text.*;

public class FriendVO {

	private int frno, cnt;
	private String id, frid, adddate, afile;
	private Date addDate;
	private Time addTime;
	public int getFrno() {
		return frno;
	}
	public void setFrno(int frno) {
		this.frno = frno;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrid() {
		return frid;
	}
	public void setFrid(String frid) {
		this.frid = frid;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public void setAdddate() {
		SimpleDateFormat form1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat form2 = new SimpleDateFormat("HH:mm:ss");
		adddate = form1.format(addDate)+ " " + form2.format(addTime);		
	}
	public String getAfile() {
		return afile;
	}
	public void setAfile(String afile) {
		this.afile = afile;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Time getAddTime() {
		return addTime;
	}
	public void setAddTime(Time addTime) {
		this.addTime = addTime;
		setAdddate();
	}
	@Override
	public String toString() {
		return "FriendVO [frno=" + frno + ", cnt=" + cnt + ", id=" + id + ", frid=" + frid + ", adddate=" + adddate
				+ ", afile=" + afile + ", addDate=" + addDate + ", addTime=" + addTime + "]";
	}
	
	
}
