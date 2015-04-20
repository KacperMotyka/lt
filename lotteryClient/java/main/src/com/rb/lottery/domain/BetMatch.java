/**
 * @文件名称: BetMatch.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-2 下午12:44:52
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 比赛投注信息
 * @类修改者:robin
 * @修改日期:2011-12-24
 * @修改说明:添加字段
 * @作者: robin
 * @创建时间: 2011-11-2 下午12:44:52
 * @版本: 1.0.0
 */

public class BetMatch implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -8038752492908438911L;

	protected int type;
	protected CompositeId id;
	protected String competition;
	protected String period;
	protected String time;
	protected String home;
	protected String homeRank;
	protected String visitor;
	protected String vistRank;
	protected double winOdds;
	protected double drawOdds;
	protected double lossOdds;
	protected double winRate;
	protected double drawRate;
	protected double lossRate;
	protected int winRQ;
	protected int drawRQ;
	protected int lossRQ;
	protected String pankou;
	protected double jsWinSP;
	protected double jsDrawSP;
	protected double jsLossSP;
	protected MatchStatus status;
	protected int concede;
	protected int homeGoal;
	protected int vistGoal;
	protected char kjNumber;
	protected double SP;

	/**
	 * @类名: BetMatch.java
	 * @描述: TODO
	 */
	public BetMatch() {
		id = new CompositeId();
		competition = SystemConstants.EMPTY_STRING;
		period = SystemConstants.EMPTY_STRING;
		time = SystemConstants.EMPTY_STRING;
		home = SystemConstants.EMPTY_STRING;
		homeRank = SystemConstants.EMPTY_STRING;
		visitor = SystemConstants.EMPTY_STRING;
		vistRank = SystemConstants.EMPTY_STRING;
		pankou = SystemConstants.EMPTY_STRING;
		kjNumber = '*';
		status = new MatchStatus();
		SP = 1.0;
	}

	/**
	 * @类名: BetMatch.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 * @param id
	 * @param competition
	 * @param period
	 * @param time
	 * @param home
	 * @param homeRank
	 * @param visitor
	 * @param vistRank
	 * @param winOdds
	 * @param drawOdds
	 * @param lossOdds
	 * @param winRate
	 * @param drawRate
	 * @param lossRate
	 * @param status
	 * @param concede
	 * @param homeGoal
	 * @param vistGoal
	 * @param sP
	 */
	public BetMatch(int type, CompositeId id, String competition,
			String period, String time, String home, String homeRank,
			String visitor, String vistRank, double winOdds, double drawOdds,
			double lossOdds, double winRate, double drawRate, double lossRate,
			MatchStatus status, int concede, int homeGoal, int vistGoal,
			double sp) {
		this.type = type;
		this.id = id;
		this.competition = competition;
		this.period = period;
		this.time = time;
		this.home = home;
		this.homeRank = homeRank;
		this.visitor = visitor;
		this.vistRank = vistRank;
		this.winOdds = winOdds;
		this.drawOdds = drawOdds;
		this.lossOdds = lossOdds;
		this.winRate = winRate;
		this.drawRate = drawRate;
		this.lossRate = lossRate;
		this.status = status;
		this.concede = concede;
		this.homeGoal = homeGoal;
		this.vistGoal = vistGoal;
		this.SP = sp;
	}

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return id
	 */
	public CompositeId getId() {
		return id;
	}

	/**
	 * @param id
	 *            id
	 */
	public void setId(CompositeId id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public String getMatchId() {
		return id.getId();
	}

	/**
	 * mid
	 */
	public void setMatchId(String mid) {
		id.setId(mid);
	}

	/**
	 * @param
	 * 
	 */
	public String getQihao() {
		return id.getQihao();
	}

	/**
	 * @param
	 * 
	 */
	public void setQihao(String qihao) {
		id.setQihao(qihao);
	}

	/**
	 * @return competition
	 */
	public String getCompetition() {
		return competition;
	}

	/**
	 * @param competition
	 *            competition
	 */
	public void setCompetition(String competition) {
		this.competition = competition;
	}

	/**
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return home
	 */
	public String getHome() {
		return home;
	}

	/**
	 * @param home
	 *            home
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * @return homeRank
	 */
	public String getHomeRank() {
		return homeRank;
	}

	/**
	 * @param homeRank
	 *            homeRank
	 */
	public void setHomeRank(String homeRank) {
		this.homeRank = homeRank;
	}

	/**
	 * @return visitor
	 */
	public String getVisitor() {
		return visitor;
	}

	/**
	 * @param visitor
	 *            visitor
	 */
	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	/**
	 * @return vistRank
	 */
	public String getVistRank() {
		return vistRank;
	}

	/**
	 * @param vistRank
	 *            vistRank
	 */
	public void setVistRank(String vistRank) {
		this.vistRank = vistRank;
	}

	/**
	 * @return winOdds
	 */
	public double getWinOdds() {
		return winOdds;
	}

	/**
	 * @param winOdds
	 *            winOdds
	 */
	public void setWinOdds(double winOdds) {
		this.winOdds = winOdds;
	}

	/**
	 * @return drawOdds
	 */
	public double getDrawOdds() {
		return drawOdds;
	}

	/**
	 * @param drawOdds
	 *            drawOdds
	 */
	public void setDrawOdds(double drawOdds) {
		this.drawOdds = drawOdds;
	}

	/**
	 * @return lossOdds
	 */
	public double getLossOdds() {
		return lossOdds;
	}

	/**
	 * @param lossOdds
	 *            lossOdds
	 */
	public void setLossOdds(double lossOdds) {
		this.lossOdds = lossOdds;
	}

	/**
	 * @return winRate
	 */
	public double getWinRate() {
		return winRate;
	}

	/**
	 * @param winRate
	 *            winRate
	 */
	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	/**
	 * @return drawRate
	 */
	public double getDrawRate() {
		return drawRate;
	}

	/**
	 * @param drawRate
	 *            drawRate
	 */
	public void setDrawRate(double drawRate) {
		this.drawRate = drawRate;
	}

	/**
	 * @return lossRate
	 */
	public double getLossRate() {
		return lossRate;
	}

	/**
	 * @param lossRate
	 *            lossRate
	 */
	public void setLossRate(double lossRate) {
		this.lossRate = lossRate;
	}

	/**
	 * @return winRQ
	 */
	public int getWinRQ() {
		return winRQ;
	}

	/**
	 * @param winRQ
	 *            winRQ
	 */
	public void setWinRQ(int winRQ) {
		this.winRQ = winRQ;
	}

	/**
	 * @return drawRQ
	 */
	public int getDrawRQ() {
		return drawRQ;
	}

	/**
	 * @param drawRQ
	 *            drawRQ
	 */
	public void setDrawRQ(int drawRQ) {
		this.drawRQ = drawRQ;
	}

	/**
	 * @return lossRQ
	 */
	public int getLossRQ() {
		return lossRQ;
	}

	/**
	 * @param lossRQ
	 *            lossRQ
	 */
	public void setLossRQ(int lossRQ) {
		this.lossRQ = lossRQ;
	}

	/**
	 * @return pankou
	 */
	public String getPankou() {
		return pankou;
	}

	/**
	 * @param pankou
	 *            pankou
	 */
	public void setPankou(String pankou) {
		this.pankou = pankou;
	}

	/**
	 * @return jsWinSP
	 */
	public double getJsWinSP() {
		return jsWinSP;
	}

	/**
	 * @param jsWinSP
	 *            jsWinSP
	 */
	public void setJsWinSP(double jsWinSP) {
		this.jsWinSP = jsWinSP;
	}

	/**
	 * @return jsDrawSP
	 */
	public double getJsDrawSP() {
		return jsDrawSP;
	}

	/**
	 * @param jsDrawSP
	 *            jsDrawSP
	 */
	public void setJsDrawSP(double jsDrawSP) {
		this.jsDrawSP = jsDrawSP;
	}

	/**
	 * @return jsLossSP
	 */
	public double getJsLossSP() {
		return jsLossSP;
	}

	/**
	 * @param jsLossSP
	 *            jsLossSP
	 */
	public void setJsLossSP(double jsLossSP) {
		this.jsLossSP = jsLossSP;
	}

	/**
	 * @return status
	 */
	public MatchStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(MatchStatus status) {
		this.status = status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setMStatus(Long sid) {
		this.status.setSid(sid);
	}

	/**
	 * @return concede
	 */
	public int getConcede() {
		return concede;
	}

	/**
	 * @param concede
	 *            concede
	 */
	public void setConcede(int concede) {
		this.concede = concede;
	}

	/**
	 * @return homeGoal
	 */
	public int getHomeGoal() {
		return homeGoal;
	}

	/**
	 * @param homeGoal
	 *            homeGoal
	 */
	public void setHomeGoal(int homeGoal) {
		this.homeGoal = homeGoal;
	}

	/**
	 * @return vistGoal
	 */
	public int getVistGoal() {
		return vistGoal;
	}

	/**
	 * @param vistGoal
	 *            vistGoal
	 */
	public void setVistGoal(int vistGoal) {
		this.vistGoal = vistGoal;
	}

	/**
	 * @return kjNumber
	 */
	public char getKjNumber() {
		return kjNumber;
	}

	/**
	 * @param kjNumber
	 *            kjNumber
	 */
	public void setKjNumber(char kjNumber) {
		this.kjNumber = kjNumber;
	}

	/**
	 * @方法说明: 生成开奖号码
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public char generateKjNumber() {
		if (this.status.getSid() != 3) {
			return '*';
		}
		int gw = homeGoal + concede - vistGoal;
		if (gw > 0) {
			kjNumber = '3';
		} else if (gw == 0) {
			kjNumber = '1';
		} else {
			kjNumber = '0';
		}
		return kjNumber;
	}

	/**
	 * @方法说明: 生成投注率
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void generateRates() {
		int all = winRQ + drawRQ + lossRQ;
		winRate = winRQ * 1.0 / all;
		drawRate = drawRQ * 1.0 / all;
		lossRate = lossRQ * 1.0 / all;
	}

	/**
	 * @return sP
	 */
	public double getSP() {
		return SP;
	}

	/**
	 * @param sP
	 *            sP
	 */
	public void setSP(double sP) {
		SP = sP;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		JczqMatch other = (JczqMatch) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
}
