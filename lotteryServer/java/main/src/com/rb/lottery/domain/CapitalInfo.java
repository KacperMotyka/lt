package com.rb.lottery.domain;

// default package

import com.rb.lottery.user.User;

/**
 * CapitalInfo entity. @author MyEclipse Persistence Tools
 */

public class CapitalInfo implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 9124471480750836279L;

	private String userid;
	private User user;
	private Double remain;
	private Double award;
	private Double freeze;
	private Double available;
	private Double bet;
	private Double reward;
	private Double alladd;
	private Double allaward;
	private Long points;

	// Constructors

	/** default constructor */
	public CapitalInfo() {
		remain = Double.valueOf(0.0);
		award = Double.valueOf(0.0);
		freeze = Double.valueOf(0.0);
		available = Double.valueOf(0.0);
		bet = Double.valueOf(0.0);
		reward = Double.valueOf(0.0);
		alladd = Double.valueOf(0.0);
		allaward = Double.valueOf(0.0);
		points = Long.valueOf(0);
	}

	/** full constructor */
	public CapitalInfo(String userid, User user, Double remain, Double award,
			Double freeze, Double available, Double bet, Double reward,
			Double alladd, Double allaward, Long points) {
		this.userid = userid;
		this.user = user;
		this.remain = remain;
		this.award = award;
		this.freeze = freeze;
		this.available = available;
		this.bet = bet;
		this.reward = reward;
		this.alladd = alladd;
		this.allaward = allaward;
		this.points = points;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getRemain() {
		return this.remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	public Double getAward() {
		return this.award;
	}

	public void setAward(Double award) {
		this.award = award;
	}

	public Double getFreeze() {
		return this.freeze;
	}

	public void setFreeze(Double freeze) {
		this.freeze = freeze;
	}

	public Double getAvailable() {
		return this.available;
	}

	public void setAvailable(Double available) {
		this.available = available;
	}

	public Double getBet() {
		return this.bet;
	}

	public void setBet(Double bet) {
		this.bet = bet;
	}

	public Double getReward() {
		return this.reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

	public Double getAlladd() {
		return this.alladd;
	}

	public void setAlladd(Double alladd) {
		this.alladd = alladd;
	}

	public Double getAllaward() {
		return this.allaward;
	}

	public void setAllaward(Double allaward) {
		this.allaward = allaward;
	}

	public Long getPoints() {
		return this.points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

}