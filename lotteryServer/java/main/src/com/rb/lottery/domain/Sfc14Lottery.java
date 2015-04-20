/**
 * @文件名称: Sfc14.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-2 下午12:31:07
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import com.rb.lottery.server.common.SystemConstants;

/**
 * @类功能说明: POJO-胜负彩14场
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-2 下午12:31:07
 * @版本: 1.0.0
 */

public class Sfc14Lottery extends Lottery implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 4938587721186464088L;

	public Sfc14Lottery() {
		super();
		this.type = 0;
		passway.add(SystemConstants.PASSWAY_OF_SFC14);
	}

	public Sfc14Lottery(String qihao, String bet) {
		super();
		this.type = 0;
		this.qihao = qihao;
		this.bet = bet;
		passway.add(SystemConstants.PASSWAY_OF_SFC14);
	}

	/**
	 * @类名: Sfc14.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 */
	public Sfc14Lottery(int type, String qihao) {
		super();
		this.type = type;
		this.qihao = qihao;
		this.bet = SystemConstants.EMPTY_STRING;
		passway.add(SystemConstants.PASSWAY_OF_SFC14);
	}
}
