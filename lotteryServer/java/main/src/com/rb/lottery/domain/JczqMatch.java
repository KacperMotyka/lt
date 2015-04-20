/**
 * @文件名称: JczqMatch.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-2 下午12:44:52
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明: 竞彩足球比赛投注信息
 * @类修改者:robin
 * @修改日期:2011-12-24
 * @修改说明:添加字段
 * @作者: robin
 * @创建时间: 2011-11-2 下午12:44:52
 * @版本: 1.0.0
 */

public class JczqMatch extends BetMatch implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 3846656874421900159L;

	/**
	 * @类名: SfcMatch.java
	 * @描述: TODO
	 */
	public JczqMatch() {
		super();
		this.type = 3;
	}

	/**
	 * @类名: SfcMatch.java
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
	public JczqMatch(CompositeId id, String competition, String period,
			String time, String home, String homeRank, String visitor,
			String vistRank, double winOdds, double drawOdds, double lossOdds,
			double winRate, double drawRate, double lossRate,
			MatchStatus status, int concede, int homeGoal, int vistGoal,
			double sp) {
		super(3, id, competition, period, time, home, homeRank, visitor,
				vistRank, winOdds, drawOdds, lossOdds, winRate, drawRate,
				lossRate, status, concede, homeGoal, vistGoal, sp);
	}

	/**
	 * @方法说明: 获取比赛开始时间
	 * @参数: @return
	 * @return Date
	 * @throws
	 */
	public Date getPlayTime() {
		String deadline = this.period + " " + this.time;
		Date date = null;
		try {
			date = SystemEnvironment.getInstance().dateformat.parse(deadline);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			// period是当天的10点到第二天的10点
			if (hour < 10) {
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
