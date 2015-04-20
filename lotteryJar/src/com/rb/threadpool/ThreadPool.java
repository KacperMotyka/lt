/**
 * @文件名称: ThreadPool.java
 * @类路径:   com.rb.lottery.analysis.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-24 下午01:26:57
 * @版本:     1.0.0
 */
package com.rb.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @类功能说明: 线程池/创建线程池，销毁线程池，添加新任务
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-24 下午01:26:57
 * @版本: 1.0.0
 */

public final class ThreadPool {

	private static Logger logger = Logger.getLogger(ThreadPool.class);
	private static Logger taskLogger = Logger.getLogger("TaskLogger");

	private static boolean debug = taskLogger.isDebugEnabled();
	// private static boolean debug = taskLogger.isInfoEnabled();
	/* 单例 */
	private static ThreadPool instance = ThreadPool.getInstance();

	public static final int SYSTEM_BUSY_TASK_COUNT = 150;
	/* 默认池中线程数 */
	public static int worker_num = 5;
	/* 已经处理的任务数 */
	private static int taskCounter = 0;

	private static boolean systemIsBusy = false;

	private static List<Task> taskQueue = Collections
			.synchronizedList(new LinkedList<Task>());
	/* 池中的所有线程 */
	public List<PoolWorker> workers;

	private ThreadPool() {
		worker_num = 5;
		workers = new ArrayList<PoolWorker>(worker_num);
		for (int i = 0; i < worker_num; i++) {
			workers.add(new PoolWorker(i));
		}
	}

	private ThreadPool(int pool_worker_num) {
		worker_num = pool_worker_num;
		workers = new ArrayList<PoolWorker>(worker_num);
		for (int i = 0; i < worker_num; i++) {
			workers.add(new PoolWorker(i));
		}
	}

	public static synchronized ThreadPool getInstance() {
		if (instance == null)
			return new ThreadPool();
		return instance;
	}

	/**
	 * 增加新的任务 每增加一个新任务，都要唤醒任务队列
	 * 
	 * @param newTask
	 */
	public void addTask(Task newTask) {
		synchronized (taskQueue) {
			newTask.setTaskId(++taskCounter);
			newTask.setSubmitTime(new Date());
			taskQueue.add(newTask);
			/* 唤醒队列, 开始执行 */
			taskQueue.notifyAll();
		}
		logger.info("Submit Task<" + newTask.getTaskId() + ">: "
				+ newTask.info());
	}

	/**
	 * 批量增加新任务
	 * 
	 * @param taskes
	 */
	public void addBatchTask(Task[] taskes) {
		if (taskes == null || taskes.length == 0) {
			return;
		}
		synchronized (taskQueue) {
			for (int i = 0; i < taskes.length; i++) {
				if (taskes[i] == null) {
					continue;
				}
				taskes[i].setTaskId(++taskCounter);
				taskes[i].setSubmitTime(new Date());
				taskQueue.add(taskes[i]);
			}
			/* 唤醒队列, 开始执行 */
			taskQueue.notifyAll();
		}
		for (int i = 0; i < taskes.length; i++) {
			if (taskes[i] == null) {
				continue;
			}
			logger.info("Submit Task<" + taskes[i].getTaskId() + ">: "
					+ taskes[i].info());
		}
	}

	/**
	 * 线程池是否工作
	 * 
	 * @return
	 */
	public boolean isWorking() {
		if (workers == null || workers.size() == 0) {
			return false;
		}
		boolean isAllWaiting = true;
		if (taskQueue.isEmpty()) {
			for (PoolWorker wk : workers) {
				isAllWaiting = isAllWaiting && wk.isWaiting();
			}
		}
		return !isAllWaiting;
	}

	/**
	 * 线程池是否工作繁忙
	 * 
	 * @return
	 */
	public boolean isSystemBusy() {
		if (workers == null || workers.size() == 0) {
			return true;
		}
		boolean hasWaiting = false;
		if (!taskQueue.isEmpty()) {
			for (PoolWorker wk : workers) {
				hasWaiting = hasWaiting || wk.isWaiting();
			}
		}
		systemIsBusy = !hasWaiting;
		return systemIsBusy;
	}

	/**
	 * 线程池工作繁忙时，添加工作线程
	 * 
	 * @return
	 */
	public void addWorker() {
		workers.add(new PoolWorker(worker_num));
		worker_num++;
	}

	public void addWorker(int workerCount) {
		int end = worker_num + workerCount;
		for (int i = worker_num; i < end; i++) {
			workers.add(new PoolWorker(i));
		}
		worker_num += workerCount;
	}

	/**
	 * 线程池信息
	 * 
	 * @return
	 */
	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nTask Queue Size:" + taskQueue.size());
		for (int i = 0; i < worker_num; i++) {
			sb.append("\nWorker " + i + " is "
					+ ((workers.get(i).isWaiting()) ? "Waiting." : "Running."));
		}
		return sb.toString();
	}

	/**
	 * 销毁线程池
	 */
	public synchronized void destroy() {
		for (int i = 0; i < worker_num; i++) {
			workers.get(i).stopWorker();
			workers.remove(i);
		}
		taskQueue.clear();
	}

	/**
	 * 池中工作线程
	 * 
	 */
	private class PoolWorker extends Thread {
		private int index = -1;
		/* 该工作线程是否有效 */
		private boolean isRunning = true;
		/* 该工作线程是否可以执行新任务 */
		private boolean isWaiting = true;

		public PoolWorker(int index) {
			this.index = index;
			start();
		}

		public void stopWorker() {
			this.isRunning = false;
		}

		public boolean isWaiting() {
			return this.isWaiting;
		}

		/**
		 * 循环执行任务 这也许是线程池的关键所在
		 */
		public void run() {
			while (isRunning) {
				Task r = null;
				synchronized (taskQueue) {
					while (taskQueue.isEmpty()) {
						try {
							/* 任务队列为空，则等待有新任务加入从而被唤醒 */
							taskQueue.wait(20);
						} catch (InterruptedException ie) {
							logger.error(ie);
						}
					}
					/* 取出任务执行 */
					r = (Task) taskQueue.remove(0);
				}
				if (r != null) {
					isWaiting = false;
					try {
						if (debug) {
							r.setBeginExceuteTime(new Date());
							taskLogger.debug("Worker<" + index
									+ "> start execute Task<" + r.getTaskId()
									+ ">");
							if (r.getBeginExceuteTime().getTime()
									- r.getSubmitTime().getTime() > 1000)
								taskLogger.debug("longer waiting time. "
										+ r.info()
										+ ",<"
										+ index
										+ ">,time:"
										+ (r.getFinishTime().getTime() - r
												.getBeginExceuteTime()
												.getTime()));
						}
						/* 该任务是否需要立即执行 */
						if (r.needExecuteImmediate()) {
							new Thread(r).start();
						} else {
							r.run();
						}
						if (debug) {
							r.setFinishTime(new Date());
							taskLogger.debug("Worker<" + index
									+ "> finish task<" + r.getTaskId() + ">");
							if (r.getFinishTime().getTime()
									- r.getBeginExceuteTime().getTime() > 1000)
								taskLogger.debug("longer execution time. "
										+ r.info()
										+ ",<"
										+ index
										+ ">,time:"
										+ (r.getFinishTime().getTime() - r
												.getBeginExceuteTime()
												.getTime()));
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
					}
					isWaiting = true;
					r = null;
				}
			}
		}
	}

}
