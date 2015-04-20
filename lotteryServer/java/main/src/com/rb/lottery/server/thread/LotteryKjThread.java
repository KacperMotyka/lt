/**
 * @文件名称: LotteryKjThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-5 下午04:55:07
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import com.rb.lottery.server.dao.KjDAO;


/**
 * @类功能说明: 开奖线程
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-3-5 下午04:55:07
 * @版本:       1.0.0
 */

public class LotteryKjThread extends Thread {

	private static final String KJ_THREAD = "自动开奖线程";
	
	public LotteryKjThread() {
		super();
		this.setName(KJ_THREAD);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		KjDAO kjDao = new KjDAO();
		kjDao.doAutoKj();
	}
}
