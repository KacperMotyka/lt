package com.rb.lottery.server.util;

import java.util.Date;

import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;

/**
 * Log entity. @author MyEclipse Persistence Tools
 */

public class Log implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6090358729124957092L;

	private Long lid;
	private Long keepdays;
	private String userid;
	private String ip;
	private Date time;
	private Long type;
	private Long success;
	private String description;

	// Constructors

	/** default constructor */
	public Log() {
		// keepdays = SystemEnvironment.log_keep_days;
		// need user's id
		// userid = SystemCache.currentUser.getUserid();
		// need user's ip
		// ip = SystemCache.ipAddress;
		// need use Server' time
		time = new Date();
	}

	/** minimal constructor */
	public Log(String userid, Long type, Long success) {
		this.userid = userid;
		this.type = type;
		this.success = success;
		// keepdays = SystemEnvironment.log_keep_days;
		// need user's ip
		// ip = SystemCache.ipAddress;
		// need use Server' time
		time = new Date();
	}

	/** full constructor */
	public Log(Long keepdays, String userid, String ip, Date time, Long type,
			Long success, String description) {
		this.keepdays = keepdays;
		this.userid = userid;
		this.ip = ip;
		this.time = time;
		this.type = type;
		this.success = success;
		this.description = description;
	}

	// Property accessors

	public Long getLid() {
		return this.lid;
	}

	public void setLid(Long lid) {
		this.lid = lid;
	}

	public Long getKeepdays() {
		return this.keepdays;
	}

	public void setKeepdays(Long keepdays) {
		this.keepdays = keepdays;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getSuccess() {
		return this.success;
	}

	public void setSuccess(Long success) {
		this.success = success;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}