package com.alqsoft.entity;

import java.io.Serializable;
import java.util.Date;

import org.alqframework.orm.hibernate.IdEntity;

public class DoctorServiceTime extends IdEntity implements Serializable {
	private static final long serialVersionUID = 8294097370913362900L;

	// 医生可咨询星期
	private String monday;
	private String Tuesday;
	private String Wednesday;
	private String Thursday;
	private String Friday;
	private String Saturday;
	private String Sunday;
	// 可咨询开始时间
	private String can_consult_starttime;
	// 可咨询结束时间
	private String can_consult_endtime;
	
	private String canConsultStarttime;
	// 可咨询结束时间
	private String canConsultEndtime;
	private Long doctor_id;

	public String getMonday() {
		return monday;
	}

	public void setMonday(String monday) {
		this.monday = monday;
	}

	public String getTuesday() {
		return Tuesday;
	}

	public void setTuesday(String tuesday) {
		Tuesday = tuesday;
	}

	public String getWednesday() {
		return Wednesday;
	}

	public void setWednesday(String wednesday) {
		Wednesday = wednesday;
	}

	public String getThursday() {
		return Thursday;
	}

	public void setThursday(String thursday) {
		Thursday = thursday;
	}

	public String getFriday() {
		return Friday;
	}

	public void setFriday(String friday) {
		Friday = friday;
	}

	public String getSaturday() {
		return Saturday;
	}

	public void setSaturday(String saturday) {
		Saturday = saturday;
	}

	public String getSunday() {
		return Sunday;
	}

	public void setSunday(String sunday) {
		Sunday = sunday;
	}

	public String getCan_consult_starttime() {
		return can_consult_starttime;
	}

	public void setCan_consult_starttime(String can_consult_starttime) {
		this.can_consult_starttime = can_consult_starttime;
	}

	public String getCan_consult_endtime() {
		return can_consult_endtime;
	}

	public void setCan_consult_endtime(String can_consult_endtime) {
		this.can_consult_endtime = can_consult_endtime;
	}

	public Long getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Long doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getCanConsultStarttime() {
		return canConsultStarttime;
	}

	public void setCanConsultStarttime(String canConsultStarttime) {
		this.canConsultStarttime = canConsultStarttime;
	}

	public String getCanConsultEndtime() {
		return canConsultEndtime;
	}

	public void setCanConsultEndtime(String canConsultEndtime) {
		this.canConsultEndtime = canConsultEndtime;
	}

}
