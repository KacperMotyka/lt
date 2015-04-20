/**
 * @文件名称: RewardFilterTask.java
 * @类路径:   com.rb.lottery.task.commonB
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-16 下午02:34:21
 * @版本:     1.0.0
 */
package com.rb.lottery.client.task.commonB;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rb.lottery.system.SystemCache;
import com.rb.threadpool.Task;

/**
 * @类功能说明:奖金指数过滤任务
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-16 下午02:34:21
 * @版本: 1.0.0
 */

public class RewardFilterTask extends Task {

	private static final Logger log = Logger.getLogger(RewardFilterTask.class);

	private float least;
	private float most;
	private boolean fatal;

	public RewardFilterTask() {
		least = 0;
		most = 5000000;
		this.fatal = false;
	}

	public RewardFilterTask(float least, float most, boolean ft) {
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
		String taskInfo = "奖金指数过滤任务处理";
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
				it.remove();
				continue;
			}
			Iterator vit = bets.iterator();
			while (vit.hasNext()) {
				Object obj = vit.next();
				if (obj == null) {
					continue;
				}
				all++;
				double jjzs = 0.0;
				// TODO calculate reward
				if (jjzs > most || jjzs < least) {
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
