/**
 * @文件名称: AbnormalProcThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-19 上午11:23:10
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import com.rb.lottery.server.dao.AbnormalProcDAO;


/**
 * @类功能说明: 非正常场次处理线程
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-3-19 上午11:23:10
 * @版本:       1.0.0
 */

public class AbnormalProcThread extends Thread {

	private static final String ABNORMALPROC_THREAD = "非正常结束场次处理线程";
	
	public AbnormalProcThread() {
		super();
		this.setName(ABNORMALPROC_THREAD);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		AbnormalProcDAO dao = new AbnormalProcDAO();
		dao.doAbnormalProc();
	}
}
