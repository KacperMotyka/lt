/**
 * @文件名称: FilterCriterion.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-24 下午04:03:36
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import com.rb.lottery.client.task.commonA.BreakFilterTask;
import com.rb.lottery.client.task.commonA.ContinueFilterTask;
import com.rb.lottery.client.task.commonA.DrawConFilterTask;
import com.rb.lottery.client.task.commonA.DrawFilterTask;
import com.rb.lottery.client.task.commonA.DrawLossFilterTask;
import com.rb.lottery.client.task.commonA.DstSumFilterTask;
import com.rb.lottery.client.task.commonA.LossConFilterTask;
import com.rb.lottery.client.task.commonA.LossFilterTask;
import com.rb.lottery.client.task.commonA.NbrCrsFilterTask;
import com.rb.lottery.client.task.commonA.PosCrsFilterTask;
import com.rb.lottery.client.task.commonA.ScoreFilterTask;
import com.rb.lottery.client.task.commonA.WinConFilterTask;
import com.rb.lottery.client.task.commonA.WinDrawFilterTask;
import com.rb.lottery.client.task.commonA.WinFilterTask;
import com.rb.lottery.client.task.commonA.WinLossFilterTask;
import com.rb.lottery.client.task.commonB.ExpectFilterTask;
import com.rb.lottery.client.task.commonB.FirstOddsFilterTask;
import com.rb.lottery.client.task.commonB.OddsCrsFilterTask;
import com.rb.lottery.client.task.commonB.OddsSumFilterTask;
import com.rb.lottery.client.task.commonB.ProbCrsFilterTask;
import com.rb.lottery.client.task.commonB.ProbSumFilterTask;
import com.rb.lottery.client.task.commonB.RangeRankFilterTask;
import com.rb.lottery.client.task.commonB.RewardFilterTask;
import com.rb.lottery.client.task.commonB.SecondOddsFilterTask;
import com.rb.lottery.client.task.commonB.ThirdOddsFilterTask;
import com.rb.threadpool.Task;

/**
 * @类功能说明: 过滤条件基类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-24 下午04:03:36
 * @版本: 1.0.0
 */

public class FilterCriterion implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 4979311113315630256L;
	
	private FilterType type;
	// 操作类型0(x≤least) 1(least≤x≤most) 2(x≥most)
	private int option;
	private float least;
	private float most;
	// 容错
	private boolean ft;

	public FilterCriterion(FilterType type, float least, float most) {
		this.type = type;
		this.least = least;
		this.most = most;
		ft = false;
	}

	public FilterCriterion(FilterType type, float least, float most, boolean ft) {
		this.type = type;
		this.least = least;
		this.most = most;
		this.ft = ft;
	}

	/**
	 * @return type
	 */
	public FilterType getType() {
		return type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(FilterType type) {
		this.type = type;
	}

	/**
	 * @return option
	 */
	public int getOption() {
		return option;
	}

	/**
	 * @param option
	 *            option
	 */
	public void setOption(int option) {
		this.option = option;
	}

	/**
	 * @return least
	 */
	public float getLeast() {
		return least;
	}

	/**
	 * @param least
	 *            least
	 */
	public void setLeast(float least) {
		this.least = least;
	}

	/**
	 * @return most
	 */
	public float getMost() {
		return most;
	}

	/**
	 * @param most
	 *            most
	 */
	public void setMost(float most) {
		this.most = most;
	}

	/**
	 * @return ft
	 */
	public boolean isFaultTolerant() {
		return ft;
	}

	/**
	 * @param ft
	 *            ft
	 */
	public void setFaultTolerant(boolean ft) {
		this.ft = ft;
	}

	public Task generateFilterTask() {
		if (type == FilterType.胜场数) {
			return new WinFilterTask(least, most, ft);
		}
		if (type == FilterType.平场数) {
			return new DrawFilterTask(least, most, ft);
		}
		if (type == FilterType.负场数) {
			return new LossFilterTask(least, most, ft);
		}
		if (type == FilterType.积分和) {
			return new ScoreFilterTask(least, most, ft);
		}
		if (type == FilterType.断点数) {
			return new BreakFilterTask(least, most, ft);
		}
		if (type == FilterType.连号个数) {
			return new ContinueFilterTask(least, most, ft);
		}
		if (type == FilterType.主场连胜) {
			return new WinConFilterTask(least, most, ft);
		}
		if (type == FilterType.主场连平) {
			return new DrawConFilterTask(least, most, ft);
		}
		if (type == FilterType.主场连负) {
			return new LossConFilterTask(least, most, ft);
		}
		if (type == FilterType.胜平连号) {
			return new WinDrawFilterTask(least, most, ft);
		}
		if (type == FilterType.胜负连号) {
			return new WinLossFilterTask(least, most, ft);
		}
		if (type == FilterType.平负连号) {
			return new DrawLossFilterTask(least, most, ft);
		}
		if (type == FilterType.邻号间距和) {
			return new DstSumFilterTask(least, most, ft);
		}
		if (type == FilterType.邻号乘积和) {
			return new NbrCrsFilterTask(least, most, ft);
		}
		if (type == FilterType.号码位积和) {
			return new PosCrsFilterTask(least, most, ft);
		}
		if (type == FilterType.第一赔率) {
			return new FirstOddsFilterTask(least, most, ft);
		}
		if (type == FilterType.第二赔率) {
			return new SecondOddsFilterTask(least, most, ft);
		}
		if (type == FilterType.第三赔率) {
			return new ThirdOddsFilterTask(least, most, ft);
		}
		if (type == FilterType.区间排名) {
			return new RangeRankFilterTask(least, most, ft);
		}
		if (type == FilterType.概率积) {
			return new ProbCrsFilterTask(least, most, ft);
		}
		if (type == FilterType.值博率) {
			return new ExpectFilterTask(least, most, ft);
		}
		if (type == FilterType.概率和) {
			return new ProbSumFilterTask(least, most, ft);
		}
		if (type == FilterType.赔率和) {
			return new OddsSumFilterTask(least, most, ft);
		}
		if (type == FilterType.赔率积) {
			return new OddsCrsFilterTask(least, most, ft);
		}
		if (type == FilterType.奖金指数) {
			return new RewardFilterTask(least, most, ft);
		}
		return null;
	}
}
