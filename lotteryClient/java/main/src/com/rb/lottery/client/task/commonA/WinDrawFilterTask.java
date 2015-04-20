/**
 * @文件名称: WinDrawFilterTask.java
 * @类路径:   com.rb.lottery.task.commonA
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-13 上午09:35:00
 * @版本:     1.0.0
 */
package com.rb.lottery.client.task.commonA;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.system.SystemCache;
import com.rb.threadpool.Task;

/**
 * @类功能说明:胜平连场过滤 单注中 "3"和"1" 任意组合在一起的最大连续场次数（在该连续场次中必须包含"3"和"1"）
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-13 上午09:35:00
 * @版本: 1.0.0
 */

public class WinDrawFilterTask extends Task {

	private static final Logger log = Logger.getLogger(WinDrawFilterTask.class);

	private float least;
	private float most;
	private boolean fatal;

	public WinDrawFilterTask() {
		least = 0;
		most = 14;
		this.fatal = false;
	}

	public WinDrawFilterTask(float least, float most, boolean ft) {
		this.least = least;
		this.most = most;
		this.fatal = ft;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.threadPool.Task#info()
	 */
	@Override
	public String info() {
		String taskInfo = "胜平连场过滤任务处理";
		return taskInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.threadPool.Task#needExecuteImmediate()
	 */
	@Override
	protected boolean needExecuteImmediate() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.threadPool.Task#taskCore()
	 */
	@Override
	public Task[] taskCore() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.threadPool.Task#useDb()
	 */
	@Override
	protected boolean useDb() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.threadPool.Task#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Map<String, List<String>> singleBets = SystemCache.getBasket(
				SystemCache.currentBasketId).getSingleBets();
		Iterator it = singleBets.keySet().iterator();
		int all = 0;
		int drop = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			List<String> bets = singleBets.get(key);
			if (bets == null) {
				continue;
			}
			Iterator vit = bets.iterator();
			while (vit.hasNext()) {
				Object obj = vit.next();
				if (obj == null) {
					continue;
				}
				all++;
				String bet = (String) obj;
				int count = SystemFunctions.getCombConCount(bet,
						SystemConstants.THREE, SystemConstants.ONE);
				if (count > most || count < least) {
					vit.remove();
					drop++;
				}
			}
			if (bets.size() == 0) {
				it.remove();
			}
		}
		log.debug("总注数: " + all);
		log.debug("滤除注数: " + drop);
		log.debug("剩余注数: " + (all - drop));
	}

}
