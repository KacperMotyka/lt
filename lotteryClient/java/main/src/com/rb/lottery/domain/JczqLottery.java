/**
 * @文件名称: Jczq.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-2 下午12:42:10
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: POJO-竞彩足球
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-2 下午12:42:10
 * @版本: 1.0.0
 */

public class JczqLottery extends Lottery implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -9047274170162651537L;

	public JczqLottery() {
		super();
		this.type = 3;
	}

	public JczqLottery(String qihao, String bet) {
		super();
		this.type = 3;
		this.qihao = qihao;
		this.bet = bet;
	}

	/**
	 * @类名: Jczq.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 */
	public JczqLottery(int type, String qihao) {
		super();
		this.type = type;
		this.qihao = qihao;
		this.bet = SystemConstants.EMPTY_STRING;
	}
}
