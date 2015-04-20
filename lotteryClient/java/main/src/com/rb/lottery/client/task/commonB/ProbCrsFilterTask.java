/**
 * @文件名称: ProbCrsFilterTask.java
 * @类路径:   com.rb.lottery.task.commonB
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-15 下午05:18:52
 * @版本:     1.0.0
 */
package com.rb.lottery.client.task.commonB;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.system.SystemCache;
import com.rb.threadpool.Task;

/**
 * @类功能说明:概率积过滤任务
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-15 下午05:18:52
 * @版本: 1.0.0
 */

public class ProbCrsFilterTask extends Task {

	private static final Logger log = Logger.getLogger(ProbCrsFilterTask.class);

	private float least;
	private float most;
	private boolean fatal;

	public ProbCrsFilterTask() {
		least = 0;
		most = 1000000;
		this.fatal = false;
	}

	public ProbCrsFilterTask(float least, float most, boolean ft) {
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
		String taskInfo = "概率积过滤任务处理";
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
				double glj = 1000000.0;
				String bet = (String) obj;
				String[] ids = key.split(SystemConstants.COMMA);
				for (int i = 0; i < bet.length(); i++) {
					BetMatch match = SystemCache.getMatchById(ids[i]);
					double pro = 1.0;
					double winOdds = match.getWinOdds();
					double drawOdds = match.getDrawOdds();
					double lossOdds = match.getLossOdds();
					double rvs = SystemFunctions.getRevSum(winOdds, drawOdds,
							lossOdds);
					if (bet.charAt(i) == '3') {
						pro = 1.0 / winOdds / rvs;
					} else if (bet.charAt(i) == '1') {
						pro = 1.0 / drawOdds / rvs;
					} else if (bet.charAt(i) == '0') {
						pro = 1.0 / lossOdds / rvs;
					}
					glj *= pro;
				}
				if (glj > most || glj < least) {
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
