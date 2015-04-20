/**
 * @文件名称: DateUpdateTimerTask.java
 * @类路径:   com.rb.lottery.server.timer
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-16 下午05:20:28
 * @版本:     1.0.0
 */
package com.rb.lottery.server.timer;

import java.io.File;
import java.util.TimerTask;

import com.rb.lottery.server.common.FilePathConstants;
import com.rb.lottery.server.common.HtmlTagConstants;
import com.rb.lottery.server.common.RegexConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.manager.SystemManager;
import com.rb.lottery.server.thread.BjdcDataUpdateThread;
import com.rb.lottery.server.thread.DataUpdateThread;
import com.rb.lottery.server.thread.JczqDataUpdateThread;
import com.rb.lottery.server.thread.SfcDataUpdateThread;
import com.rb.lottery.server.thread.SfcKjDataUpdateThread;
import com.rb.lottery.server.thread.AbnormalProcThread;
import com.rb.lottery.server.thread.LotteryKjThread;
import com.rb.lottery.system.SysConfig;

/**
 * @类功能说明: 数据自动更新任务
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-16 下午05:20:28
 * @版本: 1.0.0
 */

public class DateUpdateTimerTask extends TimerTask {

	private static final String UPDATA_THREAD_GROUP = "数据更新线程组";

	/**
	 * @throws LotteryException
	 * @方法说明: 自动数据更新
	 * @参数:
	 * @return void
	 * @throws
	 */
	private static void autoUpdate() throws LotteryException {
		try {
			String sfcSite = SysConfig.getInstance().getSfcSite();
			String sfcKjSite = SysConfig.getInstance().getSfcKjSite();
			String bjdcSite = SysConfig.getInstance().getBjdcSite();
			String jczqSite = SysConfig.getInstance().getJczqSite();
			String sfcQihao = new String(SysConfig.getInstance().getSfcQihao());
			String bjdcQihao = new String(SysConfig.getInstance()
					.getBjdcQihao());
			String jczqPeriod = new String(SysConfig.getInstance()
					.getJczqPeriod());
			int sfcQh = 0;
			if (sfcQihao.matches(RegexConstants.INTEGER_REGEX)) {
				sfcQh = Integer.parseInt(sfcQihao);
			} else {
				throw (new LotteryException(12));
			}
			int bjdcQh = 0;
			if (bjdcQihao.matches(RegexConstants.INTEGER_REGEX)) {
				bjdcQh = Integer.parseInt(bjdcQihao);
			} else {
				throw (new LotteryException(12));
			}
			ThreadGroup group = new ThreadGroup(UPDATA_THREAD_GROUP);
			DataUpdateThread sfcTread = new SfcDataUpdateThread(group, sfcSite,
					sfcQh);
			DataUpdateThread sfcKjTread = new SfcKjDataUpdateThread(group,
					sfcKjSite, sfcQh);
			DataUpdateThread bjdcTread = new BjdcDataUpdateThread(group,
					bjdcSite, bjdcQh);
			DataUpdateThread jczqTread = new JczqDataUpdateThread(group,
					jczqSite, jczqPeriod);
			sfcTread.start();
			sfcKjTread.start();
			bjdcTread.start();
			jczqTread.start();
			// 主线程挂起
			while (sfcTread.isAlive() || sfcKjTread.isAlive()
					|| bjdcTread.isAlive() || jczqTread.isAlive()) {
				// Do nothing
			}
			// State sfcState = sfcTread.getState();
			// 更新系统配置文件
			File sysConFile = new File(FilePathConstants.SYSTEM_CONFIG_FILE);
			// 胜负彩更新
			String updateSfcQihao = SysConfig.getInstance().getSfcQihao();
			SystemManager.getInstance().updateSystemConfig(sysConFile,
					SystemConstants.CURRENT_SFC_QIHAO, updateSfcQihao);
			String lastYear = SysConfig.getInstance().getLastSfcYear()
					+ SystemConstants.EMPTY_STRING;
			String lastSfcCount = SysConfig.getInstance().getLastSfcCount()
					+ SystemConstants.EMPTY_STRING;
			SystemManager.getInstance().updateSystemConfig(sysConFile,
					SystemConstants.LAST_SFC_COUNT, HtmlTagConstants.XML_VALUE,
					lastYear, lastSfcCount);
			// 北京单场更新
			String updateBjdcQihao = SysConfig.getInstance().getBjdcQihao();
			SystemManager.getInstance().updateSystemConfig(sysConFile,
					SystemConstants.CURRENT_BJDC_QIHAO, updateBjdcQihao);
			String lastMonth = SysConfig.getInstance().getLastBjdcMonth()
					+ SystemConstants.EMPTY_STRING;
			String lastBjdcCount = SysConfig.getInstance().getLastBjdcCount()
					+ SystemConstants.EMPTY_STRING;
			SystemManager.getInstance().updateSystemConfig(sysConFile,
					SystemConstants.LAST_BJDC_COUNT,
					HtmlTagConstants.XML_VALUE, lastMonth, lastBjdcCount);
			// 竞彩足球更新
			String updateJczqPeriod = SysConfig.getInstance().getJczqPeriod();
			SystemManager.getInstance().updateSystemConfig(sysConFile,
					SystemConstants.CURRENT_JCZQ_PERIOD, updateJczqPeriod);
		} catch (LotteryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 非正常结束场次处理
	 * @参数:
	 * @return void
	 * @throws
	 */
	private static void doAbnormalProc() throws LotteryException {

		// 开奖线程启动
		AbnormalProcThread abTread = new AbnormalProcThread();
		abTread.start();
		// 主线程挂起
		while (abTread.isAlive()) {
			// Do nothing
		}
	}

	/**
	 * @throws LotteryException
	 * @方法说明:自动开奖
	 * @参数:
	 * @return void
	 * @throws
	 */
	private static void autoKj() throws LotteryException {
		// 开奖线程启动
		LotteryKjThread kjTread = new LotteryKjThread();
		kjTread.start();
		// 主线程挂起
		while (kjTread.isAlive()) {
			// Do nothing
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		try {
			autoUpdate();
			doAbnormalProc();
			autoKj();
		} catch (LotteryException e) {
			e.printStackTrace();
		}
	}

}
