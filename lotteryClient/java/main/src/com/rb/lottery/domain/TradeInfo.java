package com.rb.lottery.domain;

// default package

import com.rb.lottery.user.User;

import java.util.Date;

/**
 * TradeInfo entity. @author MyEclipse Persistence Tools
 */

public class TradeInfo implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 3818218082469154581L;

	private String tid;
	private BetForm betForm;
	private User user;
	private Long inout;
	private Long type;
	private Date time;
	private Double amount;
	private Double remain;
	private Long status;
	private String remark;

	// Constructors

	/** default constructor */
	public TradeInfo() {
		time = new Date();
	}

	/** minimal constructor */
	public TradeInfo(String tid, User user, Long inout, Long type,
			Double amount, Double remain, Long status) {
		this.tid = tid;
		this.user = user;
		this.inout = inout;
		this.type = type;
		this.amount = amount;
		this.remain = remain;
		this.status = status;
	}

	/** full constructor */
	public TradeInfo(String tid, BetForm betForm, User user, Long inout,
			Long type, Date time, Double amount, Double remain, Long status,
			String remark) {
		this.tid = tid;
		this.betForm = betForm;
		this.user = user;
		this.inout = inout;
		this.type = type;
		this.time = time;
		this.amount = amount;
		this.remain = remain;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public BetForm getBetForm() {
		return this.betForm;
	}

	public void setBetForm(BetForm betForm) {
		this.betForm = betForm;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getInout() {
		return this.inout;
	}

	public void setInout(Long inout) {
		this.inout = inout;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRemain() {
		return this.remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}