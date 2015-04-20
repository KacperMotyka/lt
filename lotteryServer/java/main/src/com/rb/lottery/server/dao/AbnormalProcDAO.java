/**
 * @文件名称: AbnormalProcDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-19 上午11:25:41
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import org.apache.log4j.Logger;


/**
 * @类功能说明: 非正常结束场次状态更新
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-3-19 上午11:25:41
 * @版本:       1.0.0
 */

public class AbnormalProcDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(AbnormalProcDAO.class);
	
	private static final String ABNORMAL_PROC_NAME = "UPDATE_ABNORMAL";

	/**
	 * @方法说明: 更新非正常结束场次状态,
	 * 				比赛开始12小时后状态为未结束且无开奖号码的为非正常结束场次
	 * @参数:     
	 * @return    void
	 * @throws
	 */
	public void doAbnormalProc() {
		log.info("开始处理非正常结束场次...");
		doProc(ABNORMAL_PROC_NAME);
		log.info("非正常结束场次处理完成.");
	}
}
