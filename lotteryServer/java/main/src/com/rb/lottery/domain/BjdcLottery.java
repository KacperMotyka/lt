/**
 * @文件名称: Bjdc.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-2 下午12:41:01
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import com.rb.lottery.server.common.SystemConstants;

/**
 * @类功能说明: POJO-北京单场
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-2 下午12:41:01
 * @版本: 1.0.0
 */

public class BjdcLottery extends Lottery implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -4222979113552903333L;

	/**
	 * @类名: Bjdc.java
	 * @描述: TODO
	 */
	public BjdcLottery() {
		super();
		this.type = 2;
	}

	/**
	 * @类名: Bjdc.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 * @param bet
	 */
	public BjdcLottery(String qihao, String bet) {
		super();
		this.type = 2;
		this.qihao = qihao;
		this.bet = bet;
	}

	/**
	 * @类名: Bjdc.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 */
	public BjdcLottery(int type, String qihao) {
		super();
		this.type = type;
		this.qihao = qihao;
		this.bet = SystemConstants.EMPTY_STRING;
	}

}
