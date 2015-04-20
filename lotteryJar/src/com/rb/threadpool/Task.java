/**
 * @文件名称: Task.java
 * @类路径:   com.rb.threadpool
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-24 下午01:31:34
 * @版本:     1.0.0
 */
package com.rb.threadpool;

import java.util.Date;

/**
 * @类功能说明: 所有任务类接口
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-24 下午01:31:34
 * @版本: 1.0.0
 */

public abstract class Task implements Runnable {

	// private static Logger logger = Logger.getLogger(Task.class);
	/* 产生时间 */
	private Date generateTime = null;
	/* 提交执行时间 */
	private Date submitTime = null;
	/* 开始执行时间 */
	private Date beginExceuteTime = null;
	/* 执行完成时间 */
	private Date finishTime = null;

	private long taskId;

	public Task() {
		this.generateTime = new Date();
	}

	/**
	 * 任务执行入口
	 * 
	 * 相关执行代码
	 * 
	 * beginTransaction();
	 * 
	 * 执行过程中可能产生新的任务 subtask = taskCore();
	 * 
	 * commitTransaction();
	 * 
	 * 增加新产生的任务 ThreadPool.getInstance().batchAddTask(taskCore());
	 */
	public abstract void run();

	/**
	 * 所有任务的核心 所以特别的业务逻辑执行之处
	 * 
	 * @throws Exception
	 */
	public abstract Task[] taskCore() throws Exception;

	/**
	 * 是否用到数据库
	 * 
	 * @return
	 */
	protected abstract boolean useDb();

	/**
	 * 是否需要立即执行
	 * 
	 * @return
	 */
	protected abstract boolean needExecuteImmediate();

	/**
	 * 任务信息
	 * 
	 * @return String
	 */
	public abstract String info();

	public Date getGenerateTime() {
		return generateTime;
	}

	public Date getBeginExceuteTime() {
		return beginExceuteTime;
	}

	public void setBeginExceuteTime(Date beginExceuteTime) {
		this.beginExceuteTime = beginExceuteTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

}
