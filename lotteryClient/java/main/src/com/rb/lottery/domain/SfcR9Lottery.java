/**
 * @文件名称: SfcR9.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-10 下午09:01:42
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-10 下午09:01:42
 * @版本:       1.0.0
 */

public class SfcR9Lottery extends Lottery implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 1662752921385725120L;

	public SfcR9Lottery() {
		super();
		this.type = 1;
		passway.add(SystemConstants.PASSWAY_OF_SFCR9);
	}

	public SfcR9Lottery(String qihao, String bet) {
		super();
		this.type = 1;
		this.qihao = qihao;
		this.bet = bet;
		passway.add(SystemConstants.PASSWAY_OF_SFCR9);
	}

	/**
	 * @类名: SfcR9.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 */
	public SfcR9Lottery(int type, String qihao) {
		super();
		this.type = type;
		this.qihao = qihao;
		this.bet = SystemConstants.EMPTY_STRING;
		passway.add(SystemConstants.PASSWAY_OF_SFCR9);
	}
}
