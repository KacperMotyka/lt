/**
 * @文件名称: SocketManager.java
 * @类路径:   com.rb.lottery.server.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-28 上午10:03:12
 * @版本:     1.0.0
 */
package com.rb.lottery.server.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;

import com.rb.lottery.domain.BetForm;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.domain.TradeInfo;
import com.rb.lottery.protocol.LtPackage;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.dao.BetInfoDAO;
import com.rb.lottery.server.dao.BjdcDAO;
import com.rb.lottery.server.dao.HibernateSessionFactory;
import com.rb.lottery.server.dao.JczqDAO;
import com.rb.lottery.server.dao.SfcDAO;
import com.rb.lottery.server.dao.UserDAO;
import com.rb.lottery.server.manager.SocketManager;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.user.User;
import com.rb.lottery.user.UserInfo;
import com.rb.lottery.user.UserSfQa;
import com.rb.lottery.server.util.Log;

/**
 * @类功能说明: 服务器端请求接收响应处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-28 上午10:03:12
 * @版本: 1.0.0
 */

public class SocketManager {

	private static final Logger log = Logger.getLogger(SocketManager.class);

	private static SocketManager socketManager = null;

	public static SocketManager getInstance() {
		if (socketManager == null) {
			socketManager = new SocketManager();
		}
		return socketManager;
	}

	/**
	 * @类名: SocketManager.java
	 * @描述: TODO
	 */
	private SocketManager() {
	}

	/**
	 * @方法说明: 处理请求
	 * @参数: @param pack
	 * @参数: @return
	 * @return LtPackage
	 * @throws
	 */
	public LtPackage response(LtPackage reqPack) {
		LtPackage resPack = new LtPackage();
		resPack.setDestIP(reqPack.getSrcIP());
		resPack.setDestPort(reqPack.getSrcPort());
		String cmd = reqPack.getName();
		log.info("接收[" + cmd + "]请求.");
		resPack.setName(cmd);
		if (cmd.equalsIgnoreCase("getEnvironment")) {
			doGetEnvironment(resPack);
		} else if (cmd.equalsIgnoreCase("testDatabaseConnection")) {
			doTestDatabaseConnection(resPack);
		} else if (cmd.equalsIgnoreCase("testServerConnection")) {
			doTestServerConnection(resPack);
		} else if (cmd.equalsIgnoreCase("getBetData")) {
			doGetBetData(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("register")) {
			doRegister(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("isUserExist")) {
			doIsUserExist(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("login")) {
			doLogin(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("getCapitalInfo")) {
			doGetCapitalInfo(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("changePassword")) {
			doChangePassword(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("checkSfQa")) {
			doCheckSfQa(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("updateUserSfQa")) {
			doUpdateUserSfQa(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("changePayPassword")) {
			doChangePayPassword(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("updateCapitalInfo")) {
			doUpdateCapitalInfo(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("addTradeInfo")) {
			doAddTradeInfo(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("getBetInfo")) {
			doGetBetInfo(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("getSfcMatchByQh")) {
			doGetSfcMatchByQh(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("getBjdcMatchByQhIds")) {
			doGetBjdcMatchByQhIds(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("getJczqMatchByQhIds")) {
			doGetJczqMatchByQhIds(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("addBetInfo")) {
			doAddBetInfo(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("getUserInfo")) {
			doGetUserInfo(reqPack, resPack);
		} else if (cmd.equalsIgnoreCase("logout")) {
			doLogout(reqPack, resPack);
		}
		return resPack;
	}

	/**
	 * @方法说明: 获取用户信息
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetUserInfo(LtPackage reqPack, LtPackage resPack) {
		UserInfo userInfo = null;
		try {
			String userid = (String) reqPack.getParameter("userid");
			UserDAO udao = new UserDAO();
			userInfo = udao.getUserInfoById(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resPack.addParameter("userInfo", userInfo);
	}

	/**
	 * @方法说明: 添加投注交易记录
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doAddBetInfo(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			BetForm betInfo = (BetForm) reqPack.getParameter("betInfo");
			TradeInfo tradeInfo = (TradeInfo) reqPack.getParameter("tradeInfo");
			CapitalInfo capInfo = (CapitalInfo) reqPack
					.getParameter("capitalInfo");
			UserDAO udao = new UserDAO();
			isSuccess = udao.addBetInfo(betInfo);
			Log myLog = new Log();
			myLog.setType(SystemConstants.LOG_TYPE_BET);
			myLog.setUserid(betInfo.getUser().getUserid());
			myLog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			myLog.setIp(reqPack.getSrcIP());
			if (isSuccess) {
				if (1 == betInfo.getValid()) {
					boolean doTrade = true;
					// 手动模式
					if (0 == SystemCache.trade_mode) {
						doTrade = udao.addTradeInfo(tradeInfo);
					}
					if (doTrade) {
						boolean udCap = true;
						if (0 == SystemCache.trade_mode) {
							udCap = udao.updateCapitalInfo(capInfo);
							if (!udCap) {
								Log clog = new Log();
								clog.setKeepdays(SystemEnvironment
										.getInstance().getLog_keep_days());
								clog.setIp(reqPack.getSrcIP());
								clog.setType(SystemConstants.LOG_TYPE_OTHER);
								clog.setUserid(SystemConstants.BLANK_STRING);
								clog.setDescription("系统错误,有交易记录生成但更新资产明细失败");
								log.info("系统错误,有交易记录生成但更新资产明细失败");
								clog.setSuccess(SystemConstants.LOG_FAIL);
								SystemProcessor.logDAO.addLog(clog);
								isSuccess = false;
							}
						}
					} else {
						Log tlog = new Log();
						tlog.setKeepdays(SystemEnvironment.getInstance()
								.getLog_keep_days());
						tlog.setIp(reqPack.getSrcIP());
						tlog.setType(SystemConstants.LOG_TYPE_OTHER);
						tlog.setUserid(SystemConstants.BLANK_STRING);
						tlog.setDescription("系统错误,有投注记录生成但无交易记录生成");
						log.info("系统错误,有投注记录生成但无交易记录生成");
						tlog.setSuccess(SystemConstants.LOG_FAIL);
						SystemProcessor.logDAO.addLog(tlog);
						isSuccess = false;
					}
				}
				myLog.setSuccess(SystemConstants.LOG_SUCCESS);
				myLog.setDescription("投注成功");
			} else {
				myLog.setSuccess(SystemConstants.LOG_FAIL);
				myLog.setDescription("系统错误,投注失败");
			}
			SystemProcessor.logDAO.addLog(myLog);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 注销
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doLogout(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			User user = (User) reqPack.getParameter("user");
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_RECHARGE);
			mylog.setUserid(user.getUserid());
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			mylog.setSuccess(SystemConstants.LOG_SUCCESS);
			mylog.setDescription(" 注销成功");
			isSuccess = true;
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 根据期号和比赛id获取竞彩足球信息
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetJczqMatchByQhIds(LtPackage reqPack, LtPackage resPack) {
		List<JczqMatch> matches = null;
		try {
			String qihao = (String) reqPack.getParameter("qihao");
			String[] mids = (String[]) reqPack.getParameter("mids");
			JczqDAO jdao = new JczqDAO();
			matches = jdao.getJczqMatchesByQhIds(qihao, mids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resPack.addParameter("jczqmatches", matches);
	}

	/**
	 * @方法说明: 根据期号和比赛id获取北京单场信息
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetBjdcMatchByQhIds(LtPackage reqPack, LtPackage resPack) {
		List<BjdcMatch> matches = null;
		try {
			String qihao = (String) reqPack.getParameter("qihao");
			String[] mids = (String[]) reqPack.getParameter("mids");
			BjdcDAO bdao = new BjdcDAO();
			matches = bdao.getBjdcMatchesByQhIds(qihao, mids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resPack.addParameter("bjdcmatches", matches);
	}

	/**
	 * @方法说明:根据期号获取14场胜负彩信息
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetSfcMatchByQh(LtPackage reqPack, LtPackage resPack) {
		List<SfcMatch> matches = null;
		try {
			String qihao = (String) reqPack.getParameter("qihao");
			SfcDAO sdao = new SfcDAO();
			matches = sdao.getSfcMatchesByQh(qihao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resPack.addParameter("sfcmatches", matches);
	}

	/**
	 * @方法说明: 获取用户的投注订单
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetBetInfo(LtPackage reqPack, LtPackage resPack) {
		List<BetForm> betInfos = null;
		try {
			String userid = (String) reqPack.getParameter("userid");
			BetInfoDAO bdao = new BetInfoDAO();
			betInfos = bdao.getBetFormsByUserId(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resPack.addParameter("betInfos", betInfos);
	}

	/**
	 * @方法说明: 添加交易记录
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doAddTradeInfo(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			TradeInfo trade = (TradeInfo) reqPack.getParameter("tradeInfo");
			UserDAO udao = new UserDAO();
			isSuccess = udao.addTradeInfo(trade);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 更新帐户余额明细
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doUpdateCapitalInfo(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			CapitalInfo cap = (CapitalInfo) reqPack.getParameter("capitalInfo");
			UserDAO udao = new UserDAO();
			isSuccess = udao.updateCapitalInfo(cap);
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_RECHARGE);
			mylog.setUserid(cap.getUserid());
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			if (isSuccess) {
				mylog.setSuccess(SystemConstants.LOG_SUCCESS);
				mylog.setDescription("充值" + cap.getAward() + "元成功");
			} else {
				mylog.setSuccess(SystemConstants.LOG_FAIL);
				mylog.setDescription("修改支付密码失败");
			}
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 修改支付密码
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doChangePayPassword(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			User user = (User) reqPack.getParameter("user");
			UserDAO udao = new UserDAO();
			isSuccess = udao.updateUser(user);
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_SAFESETTING);
			mylog.setUserid(user.getUserid());
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			if (isSuccess) {
				mylog.setSuccess(SystemConstants.LOG_SUCCESS);
				mylog.setDescription("修改支付密码成功");
			} else {
				mylog.setSuccess(SystemConstants.LOG_FAIL);
				mylog.setDescription("修改支付密码失败");
			}
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明:修改用户密保问题
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void doUpdateUserSfQa(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			List<UserSfQa> qas = (List<UserSfQa>) reqPack.getParameter("sfqas");
			String userid = (String) reqPack.getParameter("userid");
			UserDAO udao = new UserDAO();
			isSuccess = udao.updateUserSfQa(qas);
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_SAFESETTING);
			mylog.setUserid(userid);
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			if (isSuccess) {
				mylog.setSuccess(SystemConstants.LOG_SUCCESS);
				mylog.setDescription("密保问题设置成功");
			} else {
				mylog.setSuccess(SystemConstants.LOG_FAIL);
				mylog.setDescription("密保问题设置失败");
			}
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 密保验证
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void doCheckSfQa(LtPackage reqPack, LtPackage resPack) {
		Boolean isPass = false;
		try {
			List<UserSfQa> sfqas = (List<UserSfQa>) reqPack
					.getParameter("sfqas");
			UserDAO udao = new UserDAO();
			isPass = udao.checkSfQa(sfqas);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isPass = false;
		}
		resPack.addParameter("isPass", isPass);
	}

	/**
	 * @方法说明: 修改帐户密码
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doChangePassword(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			User user = (User) reqPack.getParameter("user");
			UserDAO udao = new UserDAO();
			isSuccess = udao.updateUser(user);
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_SAFESETTING);
			mylog.setUserid(user.getUserid());
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			if (isSuccess) {
				mylog.setSuccess(SystemConstants.LOG_SUCCESS);
				mylog.setDescription("修改帐户密码成功");
			} else {
				mylog.setSuccess(SystemConstants.LOG_FAIL);
				mylog.setDescription("修改帐户密码失败");
			}
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 获取帐户信息
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetCapitalInfo(LtPackage reqPack, LtPackage resPack) {
		CapitalInfo cap = null;
		try {
			String userid = (String) reqPack.getParameter("userid");
			UserDAO udao = new UserDAO();
			cap = udao.getCapitalInfoByUserId(userid);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
		}
		resPack.addParameter("capitalInfo", cap);
	}

	/**
	 * @方法说明:获取用户
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doLogin(LtPackage reqPack, LtPackage resPack) {
		User user = null;
		int loginFlag = 0;
		try {
			String username = (String) reqPack.getParameter("username");
			String password = (String) reqPack.getParameter("password");
			UserDAO udao = new UserDAO();
			user = udao.getUserByName(username);
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_LOGIN);
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			// user exist
			if (null != user) {
				mylog.setUserid(user.getUserid());
				String userPassword = user.getPassword();
				if (password.equals(userPassword)) { // success
					mylog.setSuccess(SystemConstants.LOG_SUCCESS);
					mylog.setDescription("登录成功");
					loginFlag = 0;
				} else { // wrong password
					mylog.setSuccess(SystemConstants.LOG_FAIL);
					mylog.setDescription("密码错误");
					loginFlag = 1;
				}
			}
			// user isn't exist
			else {
				mylog.setUserid(" ");
				mylog.setSuccess(SystemConstants.LOG_FAIL);
				mylog.setDescription("用户不存在");
				loginFlag = 2;
			}
			mylog.setTime(new Date());
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
		}
		resPack.addParameter("loginFlag", loginFlag);
		resPack.addParameter("user", user);
	}

	/**
	 * @方法说明: 用户是否存在
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doIsUserExist(LtPackage reqPack, LtPackage resPack) {
		Boolean isExit = false;
		try {
			String username = (String) reqPack.getParameter("username");
			UserDAO udao = new UserDAO();
			isExit = udao.isExistUserName(username);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isExit = false;
		}
		resPack.addParameter("isUserExist", isExit);
	}

	/**
	 * @方法说明: 注册
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doRegister(LtPackage reqPack, LtPackage resPack) {
		Boolean isSuccess = false;
		try {
			User user = (User) reqPack.getParameter("user");
			UserInfo userInfo = (UserInfo) reqPack.getParameter("userInfo");
			UserDAO udao = new UserDAO();
			isSuccess = udao.register(user, userInfo);
			Log mylog = new Log();
			mylog.setType(SystemConstants.LOG_TYPE_REGISTER);
			mylog.setIp(reqPack.getSrcIP());
			mylog.setKeepdays(SystemEnvironment.getInstance()
					.getLog_keep_days());
			if (isSuccess) {
				mylog.setUserid(user.getUserid());
				mylog.setSuccess(SystemConstants.LOG_SUCCESS);
				mylog.setDescription("注册成功");
			} else {
				mylog.setUserid(" ");
				mylog.setSuccess(SystemConstants.LOG_FAIL);
				mylog.setDescription("注册失败");
			}
			SystemProcessor.logDAO.addLog(mylog);
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			isSuccess = false;
		}
		resPack.addParameter("isSuccess", isSuccess);
	}

	/**
	 * @方法说明: 获取投注比赛数据
	 * @参数: @param reqPack
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetBetData(LtPackage reqPack, LtPackage resPack) {
		Map<String, List<BetMatch>> matches = null;
		try {
			int type = (Integer) reqPack.getParameter("type");
			String qihao = (String) reqPack.getParameter("qihao");
			matches = IOManager.getInstance().getBetDate(type, qihao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resPack.addParameter("data", matches);
	}

	/**
	 * @方法说明: 服务器连接测试
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doTestServerConnection(LtPackage resPack) {
		// 连接成功
		Boolean conn = true;
		resPack.addParameter("connection", conn);
	}

	/**
	 * @方法说明: 服务器数据库连接测试
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doTestDatabaseConnection(LtPackage resPack) {
		Boolean conn = false;
		try {
			conn = HibernateSessionFactory.testDatabaseConnection();
		} catch (GenericJDBCException ge) {
			ge.printStackTrace();
			conn = false;
		}
		resPack.addParameter("connection", conn);
	}

	/**
	 * @方法说明: 获取环境
	 * @参数: @param resPack
	 * @return void
	 * @throws
	 */
	private void doGetEnvironment(LtPackage resPack) {
		try {
			resPack.addParameter("env", SystemEnvironment.getInstance().copy());
			resPack.addParameter("conf", SysConfig.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
