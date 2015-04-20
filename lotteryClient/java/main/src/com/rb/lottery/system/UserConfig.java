/**
 * @文件名称: UserConfig.java
 * @类路径:   com.rb.lottery.system
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-1 上午09:52:36
 * @版本:     1.0.0
 */
package com.rb.lottery.system;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 用户配置类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-1 上午09:52:36
 * @版本: 1.0.0
 */

public class UserConfig {

	private static UserConfig userconf = null;

	private int tzlx;
	private String qihao;
	private String bet;

	private UserConfig() {
		tzlx = -1;
		bet = SystemConstants.EMPTY_STRING;
	}

	public static UserConfig getInstance() {
		if (userconf == null) {
			userconf = new UserConfig();
		}
		return userconf;
	}

	/**
	 * @return tzlx
	 */
	public int getTzlx() {
		return tzlx;
	}

	/**
	 * @param tzlx
	 *            tzlx
	 */
	public void setTzlx(int tzlx) {
		this.tzlx = tzlx;
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
	 * @return bet
	 */
	public String getBet() {
		return bet;
	}

	/**
	 * @param bet
	 *            bet
	 */
	public void setBet(String bet) {
		this.bet = bet;
	}
}
