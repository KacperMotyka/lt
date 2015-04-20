/**
 * @文件名称: SfcKj.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-6 下午12:50:43
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.rb.lottery.server.common.SystemConstants;

/**
 * @类功能说明: POJO-胜负彩开奖
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-6 下午12:50:43
 * @版本: 1.0.0
 */

public class SfcKj implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -7856200244997024964L;
	
	private String qihao; // 开奖期号
	private String date; // 开奖日期
	private String deadline;// 兑奖截至日期

	LinkedHashMap<String, String> data; // 开奖号码
	private String teams;
	private String kjNumber;
	private int sfc14Sales; // 胜负彩14场销量
	private int sfcr9Sales; // 胜负彩R9销量
	private int cumulation; // 胜负彩滚存
	private int firstBets; // 一等奖注数
	private int secondBets; // 二等奖注数
	private int r9Bets; // 任9注数
	private int firstPrize; // 一等奖奖金
	private int secondPrize;// 二等奖奖金
	private int r9Prize; // 任9奖金

	public SfcKj() {
		this.qihao = SystemConstants.EMPTY_STRING;
		this.date = SystemConstants.EMPTY_STRING;
		this.deadline = SystemConstants.EMPTY_STRING;
		data = new LinkedHashMap<String, String>();
	}

	public SfcKj(String qihao) {
		this.qihao = qihao;
		this.date = SystemConstants.EMPTY_STRING;
		this.deadline = SystemConstants.EMPTY_STRING;
		data = new LinkedHashMap<String, String>();
	}

	/**
	 * @类名: SfcKj.java
	 * @描述: TODO
	 * @param qihao
	 * @param date
	 * @param deadline
	 * @param data
	 * @param sfc14Sales
	 * @param sfcr9Sales
	 * @param cumulation
	 * @param firstBets
	 * @param secondBets
	 * @param r9Bets
	 * @param firstPrize
	 * @param secondPrize
	 * @param r9Prize
	 */
	public SfcKj(String qihao, String date, String deadline,
			LinkedHashMap<String, String> data, int sfc14Sales, int sfcr9Sales,
			int cumulation, int firstBets, int secondBets, int r9Bets,
			int firstPrize, int secondPrize, int r9Prize) {
		super();
		this.qihao = qihao;
		this.date = date;
		this.deadline = deadline;
		this.data = data;
		this.sfc14Sales = sfc14Sales;
		this.sfcr9Sales = sfcr9Sales;
		this.cumulation = cumulation;
		this.firstBets = firstBets;
		this.secondBets = secondBets;
		this.r9Bets = r9Bets;
		this.firstPrize = firstPrize;
		this.secondPrize = secondPrize;
		this.r9Prize = r9Prize;
	}

	/**
	 * @return qihao
	 */
	public String getQihao() {
		return qihao;
	}

	/**
	 * @param qihao
	 *            qihao
	 */
	public void setQihao(String qihao) {
		this.qihao = qihao;
	}

	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return deadline
	 */
	public String getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline
	 *            deadline
	 */
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return data
	 */
	public LinkedHashMap<String, String> getData() {
		return data;
	}

	/**
	 * @param data
	 *            data
	 */
	public void setData(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	public void addKjhm(String team, String kjhm) {
		data.put(team, kjhm);
	}

	/**
	 * @return sfc14Sales
	 */
	public int getSfc14Sales() {
		return sfc14Sales;
	}

	/**
	 * @param sfc14Sales
	 *            sfc14Sales
	 */
	public void setSfc14Sales(int sfc14Sales) {
		this.sfc14Sales = sfc14Sales;
	}

	/**
	 * @return sfcr9Sales
	 */
	public int getSfcr9Sales() {
		return sfcr9Sales;
	}

	/**
	 * @param sfcr9Sales
	 *            sfcr9Sales
	 */
	public void setSfcr9Sales(int sfcr9Sales) {
		this.sfcr9Sales = sfcr9Sales;
	}

	/**
	 * @return cumulation
	 */
	public int getCumulation() {
		return cumulation;
	}

	/**
	 * @param cumulation
	 *            cumulation
	 */
	public void setCumulation(int cumulation) {
		this.cumulation = cumulation;
	}

	/**
	 * @return firstBets
	 */
	public int getFirstBets() {
		return firstBets;
	}

	/**
	 * @param firstBets
	 *            firstBets
	 */
	public void setFirstBets(int firstBets) {
		this.firstBets = firstBets;
	}

	/**
	 * @return secondBets
	 */
	public int getSecondBets() {
		return secondBets;
	}

	/**
	 * @param secondBets
	 *            secondBets
	 */
	public void setSecondBets(int secondBets) {
		this.secondBets = secondBets;
	}

	/**
	 * @return r9Bets
	 */
	public int getR9Bets() {
		return r9Bets;
	}

	/**
	 * @param r9Bets
	 *            r9Bets
	 */
	public void setR9Bets(int r9Bets) {
		this.r9Bets = r9Bets;
	}

	/**
	 * @return firstPrize
	 */
	public int getFirstPrize() {
		return firstPrize;
	}

	/**
	 * @param firstPrize
	 *            firstPrize
	 */
	public void setFirstPrize(int firstPrize) {
		this.firstPrize = firstPrize;
	}

	/**
	 * @return secondPrize
	 */
	public int getSecondPrize() {
		return secondPrize;
	}

	/**
	 * @param secondPrize
	 *            secondPrize
	 */
	public void setSecondPrize(int secondPrize) {
		this.secondPrize = secondPrize;
	}

	/**
	 * @return r9Prize
	 */
	public int getR9Prize() {
		return r9Prize;
	}

	/**
	 * @param r9Prize
	 *            r9Prize
	 */
	public void setR9Prize(int r9Prize) {
		this.r9Prize = r9Prize;
	}

	/**
	 * @方法说明: 获取比赛队伍信息
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getTeams() {
		String kjTeam = SystemConstants.EMPTY_STRING;
		Iterator kit = data.keySet().iterator();
		int index = 0;
		while (kit.hasNext()) {
			String team = (String) kit.next();
			if (index > 0) {
				kjTeam += SystemConstants.SLASH;
			}
			kjTeam += team;
			index++;
		}
		teams = kjTeam;
		return teams;
	}

	/**
	 * @param teams
	 *            teams
	 */
	public void setTeams(String teams) {
		this.teams = teams;
	}

	/**
	 * @方法说明: 获取开奖号码
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getKjNumber() {
		String kjNum = SystemConstants.EMPTY_STRING;
		Iterator eit = data.entrySet().iterator();
		int index = 0;
		while (eit.hasNext()) {
			Entry entry = (Entry) eit.next();
			String value = (String) entry.getValue();
			if (index > 0) {
				kjNum += SystemConstants.BLANK_STRING;
			}
			kjNum += value;
			index++;
		}
		this.kjNumber = kjNum;
		return kjNumber;
	}

	/**
	 * @param kjNumber
	 *            kjNumber
	 */
	public void setKjNumber(String kjNumber) {
		this.kjNumber = kjNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qihao == null) ? 0 : qihao.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SfcKj other = (SfcKj) obj;
		if (qihao == null) {
			if (other.qihao != null)
				return false;
		} else if (!qihao.equals(other.qihao))
			return false;
		return true;
	}
}
