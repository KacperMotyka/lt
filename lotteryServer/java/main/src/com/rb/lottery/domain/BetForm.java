package com.rb.lottery.domain;

import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.user.User;

import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * BetForm entity. @author MyEclipse Persistence Tools
 */

public class BetForm implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 8200859818210076014L;

	private String bid; // 投注订单号
	private User user;
	private Date btime; // 下单时间
	private Date ctime; // 出票时间
	private Date dtime; // 开奖时间
	private Long status; // 订单状态
	private Long ltType; // 彩票类型
	private Long btType; // 投注类型
	private Long upload; // 方案是否上传
	private Long valid; // 方案是否有效
	private String qihao; // 期号
	private String mids; // 投注所有比赛ID号（','隔开）
	private String numbers; // 投注所有比赛的复式号码（','隔开）
	private Blob sinnumbers; // 投注所有比赛的单式号码（','隔开）
	private String SPs; // 开奖SP
	private Long bets; // 投注注数
	private Long multi; // 投注倍数
	private Long money; // 投注金额
	private String kjnumbers; // 开奖号码
	private String pattern; // 过关方式
	private Long winflag; // 中奖标志
	private Long rewardflag; // 派奖状态
	private Long rewardBets; // 中奖注数(14场胜负彩为一等奖注数)
	private Long reward2Bets;	// 14场胜负彩二等奖注数
	private Double bfreward; // 税前奖金
	private Double afreward; // 税后奖金

	private Set ltTradeInfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public BetForm() {
		winflag = Long.valueOf(-1);
		rewardflag = Long.valueOf(0);
		rewardBets = Long.valueOf(0);
		reward2Bets = Long.valueOf(0);
		bfreward = Double.valueOf(0.0);
		afreward = Double.valueOf(0.0);
	}

	/** minimal constructor */
	public BetForm(String bid, User user, Long status, Long ltType,
			Long btType, Long upload, Long valid, String qihao, String mids,
			String pattern, Long winflag, Long rewardflag) {
		this.bid = bid;
		this.user = user;
		this.status = status;
		this.ltType = ltType;
		this.btType = btType;
		this.upload = upload;
		this.valid = valid;
		this.qihao = qihao;
		this.mids = mids;
		this.pattern = pattern;
		this.winflag = winflag;
		this.rewardflag = rewardflag;
	}

	/** full constructor */
	public BetForm(String bid, User user, Date btime, Date ctime, Date dtime,
			Long status, Long ltType, Long btType, Long upload, Long valid,
			String qihao, String mids, String numbers, String kjnumbers,
			String pattern, Long winflag, Long rewardflag, Long rewardBets,
			Double bfreward, Double afreward, Set ltTradeInfos) {
		this.bid = bid;
		this.user = user;
		this.btime = btime;
		this.ctime = ctime;
		this.dtime = dtime;
		this.status = status;
		this.ltType = ltType;
		this.btType = btType;
		this.upload = upload;
		this.valid = valid;
		this.qihao = qihao;
		this.mids = mids;
		this.numbers = numbers;
		this.kjnumbers = kjnumbers;
		this.pattern = pattern;
		this.winflag = winflag;
		this.rewardflag = rewardflag;
		this.rewardBets = rewardBets;
		this.bfreward = bfreward;
		this.afreward = afreward;
		this.ltTradeInfos = ltTradeInfos;
	}

	// Property accessors

	public String getBid() {
		return this.bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBtime() {
		return this.btime;
	}

	public void setBtime(Date btime) {
		this.btime = btime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getDtime() {
		return this.dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getLtType() {
		return this.ltType;
	}

	public void setLtType(Long ltType) {
		this.ltType = ltType;
	}

	public Long getBtType() {
		return this.btType;
	}

	public void setBtType(Long btType) {
		this.btType = btType;
	}

	public Long getUpload() {
		return this.upload;
	}

	public void setUpload(Long upload) {
		this.upload = upload;
	}

	public Long getValid() {
		return this.valid;
	}

	public void setValid(Long valid) {
		this.valid = valid;
	}

	public String getQihao() {
		return this.qihao;
	}

	public void setQihao(String qihao) {
		this.qihao = qihao;
	}

	public String getMids() {
		return this.mids;
	}

	public void setMids(String mids) {
		this.mids = mids;
	}

	/**
	 * @return numbers
	 */
	public String getNumbers() {
		return numbers;
	}

	/**
	 * @param numbers
	 *            numbers
	 */
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	/**
	 * @return sinnumbers
	 */
	public Blob getSinnumbers() {
		return sinnumbers;
	}

	/**
	 * @param sinnumbers sinnumbers
	 */
	public void setSinnumbers(Blob sinnumbers) {
		this.sinnumbers = sinnumbers;
	}

	/**
	 * @return sPs
	 */
	public String getSPs() {
		return SPs;
	}

	/**
	 * @param sPs
	 *            sPs
	 */
	public void setSPs(String sPs) {
		SPs = sPs;
	}

	/**
	 * @return bets
	 */
	public Long getBets() {
		return bets;
	}

	/**
	 * @param bets
	 *            bets
	 */
	public void setBets(Long bets) {
		this.bets = bets;
	}

	/**
	 * @return multi
	 */
	public Long getMulti() {
		return multi;
	}

	/**
	 * @param multi
	 *            multi
	 */
	public void setMulti(Long multi) {
		this.multi = multi;
	}

	/**
	 * @return money
	 */
	public Long getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            money
	 */
	public void setMoney(Long money) {
		this.money = money;
	}

	/**
	 * @return kjnumbers
	 */
	public String getKjnumbers() {
		return kjnumbers;
	}

	/**
	 * @param kjnumbers
	 *            kjnumbers
	 */
	public void setKjnumbers(String kjnumbers) {
		this.kjnumbers = kjnumbers;
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Long getWinflag() {
		return this.winflag;
	}

	public void setWinflag(Long winflag) {
		this.winflag = winflag;
	}

	public Long getRewardflag() {
		return this.rewardflag;
	}

	public void setRewardflag(Long rewardflag) {
		this.rewardflag = rewardflag;
	}

	public Long getRewardBets() {
		return this.rewardBets;
	}

	public void setRewardBets(Long rewardBets) {
		this.rewardBets = rewardBets;
	}

	/**
	 * @return reward2Bets
	 */
	public Long getReward2Bets() {
		return reward2Bets;
	}

	/**
	 * @param reward2Bets reward2Bets
	 */
	public void setReward2Bets(Long reward2Bets) {
		this.reward2Bets = reward2Bets;
	}

	public Double getBfreward() {
		return this.bfreward;
	}

	public void setBfreward(Double bfreward) {
		this.bfreward = bfreward;
	}

	public Double getAfreward() {
		return this.afreward;
	}

	public void setAfreward(Double afreward) {
		this.afreward = afreward;
	}

	public Set getLtTradeInfos() {
		return this.ltTradeInfos;
	}

	public void setLtTradeInfos(Set ltTradeInfos) {
		this.ltTradeInfos = ltTradeInfos;
	}

	/**
	 * @方法说明: 获取开奖场次号码
	 * @参数: @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String, String> getKjMidNum() {
		Map<String, String> kj = new HashMap<String, String>();
		String[] m = mids.trim().split(SystemConstants.COMMA);
		for (int i = 0; i < m.length; i++) {
			kj.put(m[i], kjnumbers.charAt(i) + "");
		}
		return kj;
	}

	/**
	 * @方法说明: 获取对应场次开奖SP
	 * @参数: @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String, Double> getMidSps() {
		Map<String, Double> sps = new HashMap<String, Double>();
		String[] m = mids.trim().split(SystemConstants.COMMA);
		String[] sp = SPs.trim().split(SystemConstants.COMMA);
		for (int i = 0; i < m.length; i++) {
			sps.put(m[i], Double.valueOf(sp[i]));
		}
		return sps;
	}
}