package com.luxurycity.clc.vo;

import java.sql.*;
import java.text.SimpleDateFormat;

public class MessageVO {
	private int msgno, cnt;
	private String id, reid, name, frname, afile, message, adate, msgcheck;
	private Date addDate;
	private Time addTime;
	public int getMsgno() {
		return msgno;
	}
	public void setMsgno(int msgno) {
		this.msgno = msgno;
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
	public String getReid() {
		return reid;
	}
	public void setReid(String reid) {
		this.reid = reid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFrname() {
		return frname;
	}
	public void setFrname(String frname) {
		this.frname = frname;
	}
	public String getAfile() {
		return afile;
	}
	public void setAfile(String afile) {
		this.afile = afile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAdate() {
		return adate;
	}
	public void setAdate(String adate) {
		this.adate = adate;
	}
	public void setAdate() {
		SimpleDateFormat form1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat form2 = new SimpleDateFormat("HH:mm:ss");
		adate = form1.format(addDate) + " " + form2.format(addTime);
	}
	public String getMsgcheck() {
		return msgcheck;
	}
	public void setMsgceck(String msgcheck) {
		this.msgcheck = msgcheck;
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
		setAdate();
	}
	
}
