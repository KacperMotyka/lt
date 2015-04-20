/**
 * @文件名称: SocketManager.java
 * @类路径:   com.rb.lottery.client.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-28 上午09:46:51
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.rb.lottery.client.socket.Sender;
import com.rb.lottery.domain.BetForm;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.LoginFlag;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.domain.TradeInfo;
import com.rb.lottery.protocol.LtPackage;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.user.User;
import com.rb.lottery.user.UserInfo;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明: 客户端请求发送响应处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-28 上午09:46:51
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
	 * @方法说明: 初试化环境
	 * @参数:
	 * @return void
	 * @throws
	 */
	public boolean getEnvironment() {
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getEnvironment", null);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			SystemEnvironment env = (SystemEnvironment) received
					.getParameter("env");
			SystemEnvironment.init(env);
			log.info("客户端系统环境初始化成功");
			SysConfig sysconf = (SysConfig) received.getParameter("conf");
			SysConfig.init(sysconf);
			log.info("客户端业务环境初始化成功");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @方法说明: 服务器连接测试
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean testServerConnection() {
		boolean isServerConnect = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("testServerConnection", null);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			isServerConnect = (Boolean) received.getParameter("connection");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isServerConnect;
	}

	/**
	 * @方法说明: 服务器数据库连接测试
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean testDatabaseConnection() {
		boolean isDatabaseConnect = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("testDatabaseConnection", null);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			isDatabaseConnect = (Boolean) received.getParameter("connection");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isDatabaseConnect;
	}

	/**
	 * @方法说明: 从服务器端获取投注比赛数据
	 * @参数: @return
	 * @return Map<String,List<BetMatch>>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List<BetMatch>> getGUIDataFromServer(int type,
			String qihao) {
		Map<String, List<BetMatch>> data = null;
		try {
			boolean serverConn = testServerConnection();
			if (serverConn) {
				Sender sender = new Sender();
				LtPackage sendPack = new LtPackage("getBetData", null);
				sendPack.addParameter("type", type);
				sendPack.addParameter("qihao", qihao);
				LtPackage received = sender.doPost(sendPack);
				if (null == received) {
					return null;
				}
				data = (Map<String, List<BetMatch>>) received
						.getParameter("data");
				log.info("服务器端读取数据成功");
			} else {
				log.info("连接服务器失败");
				JOptionPane.showMessageDialog(null, "连接服务器失败,请检查网络连接!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		IOManager iom = IOManager.getInstance();
		// 保存进文件
		if (type < 2) {
			iom.updateSfcFile(data.get("sfc"));
		} else if (2 == type) {
			int qh = Integer.parseInt(qihao);
			iom.updateBjdcFiles(qh, data);
		} else if (3 == type) {
			iom.updateJczqFiles(data);
		}
		return data;
	}

	/**
	 * @方法说明: 注册
	 * @参数: @param user
	 * @参数: @param userInfo
	 * @return void
	 * @throws
	 */
	public boolean register(User user, UserInfo userInfo) {
		boolean isSuccess = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("register", null);
			sendPack.addParameter("user", user);
			sendPack.addParameter("userInfo", userInfo);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			isSuccess = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	/**
	 * @方法说明: 用户是否存在
	 * @参数: @param username
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean isUserExist(String username) {
		boolean isExist = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("isUserExist", null);
			sendPack.addParameter("username", username);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			isExist = (Boolean) received.getParameter("isUserExist");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isExist;
	}

	/**
	 * @方法说明: 获取用户
	 * @参数: @param username
	 * @参数: @param password
	 * @参数: @return
	 * @return User
	 * @throws
	 */
	public User doLogin(String username, String password, LoginFlag loginFlag) {
		User user = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("login", null);
			sendPack.addParameter("username", username);
			sendPack.addParameter("password", password);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				loginFlag.setLoginFlag(3);
				return null;
			}
			user = (User) received.getParameter("user");
			Integer login = (Integer) received.getParameter("loginFlag");
			loginFlag.setLoginFlag(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * @方法说明: 获取帐户信息
	 * @参数: @param userid
	 * @参数: @return
	 * @return CapitalInfo
	 * @throws
	 */
	public CapitalInfo getCapitalInfoByUserId(String userid) {
		CapitalInfo cap = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getCapitalInfo", null);
			sendPack.addParameter("userid", userid);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return null;
			}
			cap = (CapitalInfo) received.getParameter("capitalInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cap;
	}

	/**
	 * @方法说明: 修改帐户密码
	 * @参数: @param user
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean changePassword(User user) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("changePassword", null);
			sendPack.addParameter("user", user);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 密保验证
	 * @参数: @param sfqas
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean checkSfQa(List<UserSfQa> sfqas) {
		boolean isPass = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("checkSfQa", null);
			sendPack.addParameter("sfqas", sfqas);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			isPass = (Boolean) received.getParameter("isPass");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isPass;
	}

	/**
	 * @方法说明: 修改用户密保问题
	 * @参数: @param qas
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean updateUserSfQa(List<UserSfQa> qas) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("updateUserSfQa", null);
			sendPack
					.addParameter("userid", SystemCache.currentUser.getUserid());
			sendPack.addParameter("sfqas", qas);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 修改支付密码
	 * @参数: @param user
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean changePayPassword(User user) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("changePayPassword", null);
			sendPack.addParameter("user", user);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 更新帐户余额明细
	 * @参数: @param cap
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean updateCapitalInfo(CapitalInfo cap) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("updateCapitalInfo", null);
			sendPack.addParameter("capitalInfo", cap);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 添加交易记录
	 * @参数: @param trade
	 * @return void
	 * @throws
	 */
	public boolean addTradeInfo(TradeInfo trade) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("addTradeInfo", null);
			sendPack.addParameter("tradeInfo", trade);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 获取该用户的投注订单号
	 * @参数: @param userid
	 * @参数: @return
	 * @return List<String>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BetForm> getBetInfoByUserId(String userid) {
		List<BetForm> bids = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getBetInfo", null);
			sendPack.addParameter("userid", userid);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return null;
			}
			bids = (List<BetForm>) received.getParameter("betInfos");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bids;
	}

	/**
	 * @方法说明: 根据期号获取14场胜负彩信息
	 * @参数: @param qihao
	 * @参数: @return
	 * @return List<SfcMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<SfcMatch> getSfcMatchByQh(String qihao) {
		List<SfcMatch> matches = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getSfcMatchByQh", null);
			sendPack.addParameter("qihao", qihao);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return null;
			}
			matches = (List<SfcMatch>) received.getParameter("sfcmatches");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return matches;
	}

	/**
	 * @方法说明: 根据期号和比赛id获取北京单场信息
	 * @参数: @param qihao
	 * @参数: @param mids
	 * @参数: @return
	 * @return List<BjdcMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BjdcMatch> getBjdcMatchByQhIds(String qihao, String[] mids) {
		List<BjdcMatch> matches = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getBjdcMatchByQhIds", null);
			sendPack.addParameter("qihao", qihao);
			sendPack.addParameter("mids", mids);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return null;
			}
			matches = (List<BjdcMatch>) received.getParameter("bjdcmatches");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return matches;
	}

	/**
	 * @方法说明: 根据期号和比赛id获取竞彩足球信息
	 * @参数: @param qihao
	 * @参数: @param mids
	 * @参数: @return
	 * @return List<JczqMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<JczqMatch> getJczqMatchByQhIds(String qihao, String[] mids) {
		List<JczqMatch> matches = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getJczqMatchByQhIds", null);
			sendPack.addParameter("qihao", qihao);
			sendPack.addParameter("mids", mids);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return null;
			}
			matches = (List<JczqMatch>) received.getParameter("jczqmatches");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return matches;
	}

	/**
	 * @方法说明: 注销
	 * @参数: @param currentUser
	 * @return void
	 * @throws
	 */
	public boolean logout(User user) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("logout", null);
			sendPack.addParameter("user", user);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 添加投注记录
	 * @参数: @param betInfo
	 * @return void
	 * @throws
	 */
	public boolean addBetInfo(BetForm betInfo, TradeInfo tradeInfo,
			CapitalInfo capInfo) {
		boolean success = false;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("addBetInfo", null);
			sendPack.addParameter("betInfo", betInfo);
			sendPack.addParameter("tradeInfo", tradeInfo);
			sendPack.addParameter("capitalInfo", capInfo);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return false;
			}
			success = (Boolean) received.getParameter("isSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}

	/**
	 * @方法说明: 获取用户信息
	 * @参数: @param userid
	 * @参数: @return
	 * @return UserInfo
	 * @throws
	 */
	public UserInfo getUserInfoById(String userid) {
		UserInfo userInfo = null;
		try {
			Sender sender = new Sender();
			LtPackage sendPack = new LtPackage("getUserInfo", null);
			sendPack.addParameter("userid", userid);
			LtPackage received = sender.doPost(sendPack);
			if (null == received) {
				return null;
			}
			userInfo = (UserInfo) received.getParameter("userInfo");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return userInfo;
	}

}
