/**
 * @文件名称: UserManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-02-21 下午05:23:08
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import com.rb.lottery.client.UI.dialog.ChangePwdDialog;
import com.rb.lottery.client.UI.dialog.CheckCptDialog;
import com.rb.lottery.client.UI.dialog.CryptoguardDialog;
import com.rb.lottery.client.UI.dialog.LoginDialog;
import com.rb.lottery.client.UI.dialog.PayPasswordDialog;
import com.rb.lottery.client.UI.dialog.RechargeDialog;
import com.rb.lottery.client.UI.dialog.RegisterDialog;
import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.html.BetFormHtmlPage;
import com.rb.lottery.client.html.BetDetailHtmlPage;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.util.BareBonesBrowserLaunch;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetForm;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.domain.TradeInfo;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.user.User;
import com.rb.lottery.user.UserInfo;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明: 用户操作处理类
 * @类修改者:robin
 * @修改日期:2012-03-02
 * @修改说明:添加投注处理
 * @作者: robin
 * @创建时间: 2012-02-21 下午05:23:08
 * @版本: 1.0.0
 */

public class UserManager {

	private static final Logger log = Logger.getLogger(UserManager.class);

	private static UserManager userManager = null;

	private UserManager() {
	}

	public static UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	/**
	 * @方法说明: 注册
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void register() {
		RegisterDialog registerDialog = RegisterDialog.getInstance();
		registerDialog.setVisible(true);
	}

	/**
	 * @方法说明: 登入
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void login() {
		LoginDialog loginDialog = LoginDialog.getInstance();
		loginDialog.setVisible(true);
	}

	/**
	 * @方法说明: 修改密码
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void changePassword() {
		if (!SystemCache.isLogin) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN,
					MessageConstants.INFOMATION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		ChangePwdDialog chgpwdDialog = ChangePwdDialog.getInstance();
		chgpwdDialog.setVisible(true);
	}

	/**
	 * @方法说明: 安全设置-密保问题设置
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void cryptoguardSetting() {
		if (!SystemCache.isLogin) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN,
					MessageConstants.INFOMATION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		// 密保验证
		List<UserSfQa> safeqas = SystemProcessor.getRondomSafeQas();
		if (safeqas != null && safeqas.size() > 0) {
			CheckCptDialog ccptDialog = CheckCptDialog.getInstance(safeqas,
					SystemConstants.NEXT_CHANGECPD);
			ccptDialog.setVisible(true);
		} else {
			CryptoguardDialog cpdDialog = CryptoguardDialog.getInstance();
			cpdDialog.setVisible(true);
		}
	}

	/**
	 * @方法说明: 安全设置-支付密码设置
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void payPasswordSetting() {
		if (!SystemCache.isLogin) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN,
					MessageConstants.INFOMATION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		// 密保验证
		List<UserSfQa> safeqas = SystemProcessor.getRondomSafeQas();
		if (safeqas != null && safeqas.size() > 0) {
			CheckCptDialog ccptDialog = CheckCptDialog.getInstance(safeqas,
					SystemConstants.NEXT_CHANGEPAYPWD);
			ccptDialog.setVisible(true);
		} else {
			PayPasswordDialog ppdDialog = PayPasswordDialog.getInstance();
			ppdDialog.setVisible(true);
		}
	}

	/**
	 * @方法说明:充值
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void recharge() {
		if (!SystemCache.isLogin) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN,
					MessageConstants.INFOMATION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		RechargeDialog rechargeDialog = RechargeDialog.getInstance();
		rechargeDialog.setVisible(true);
	}

	/**
	 * @方法说明: 开通会员
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void tovip() {
		// TODO Auto-generated method stub

	}

	/**
	 * @方法说明: 退出
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void logout() {
		ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
		int isOK = JOptionPane.showConfirmDialog(null,
				MessageConstants.LOGOUT_CONFIRM, MessageConstants.INFOMATION,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				icon);
		if (isOK == 0) // YES
		{
			MainFrame.getInstance().logout();
			boolean logout = SocketManager.getInstance().logout(
					SystemCache.currentUser);
			if (!logout) {
				// TODO
			}
			SystemCache.isLogin = false;
			SystemCache.currentUser = null;
			JOptionPane.showMessageDialog(null, "注销成功");
		}
	}

	/**
	 * @方法说明: 用户投注
	 * @参数: @param basket
	 * @return void
	 * @throws
	 */
	public void doBet(long btType) {
		SocketManager som = SocketManager.getInstance();
		BetBasket basket = SystemCache.getCurrentBasket();
		Lottery lt = SystemCache.getCurrentLottery();
		User user = SystemCache.currentUser;
		BetForm betInfo = new BetForm(); // 投注订单
		TradeInfo tradeInfo = new TradeInfo(); // 交易记录
		CapitalInfo capInfo = som.getCapitalInfoByUserId(user.getUserid()); // 资产明细
		String uuid = UUID.randomUUID().toString();
		betInfo.setBid(uuid);
		betInfo.setUser(user);
		betInfo.setBtime(new Date());
		betInfo.setLtType(Long.valueOf(lt.getType()));
		betInfo.setBtType(btType);
		betInfo.setUpload(Long.valueOf(1));
		String qihao = lt.getQihao();
		qihao = qihao.replaceAll("-", ""); // 处理竞彩足球
		betInfo.setQihao(qihao);
		betInfo.setMids(lt.getLotteryMatchIds());
		betInfo.setNumbers(lt.getLotteryNumbers());
		int betCount = basket.getSingleBetCount();
		int mul = lt.getMulti();
		long money = SystemEnvironment.getInstance().money_per_bet * betCount
				* mul;
		betInfo.setBets(Long.valueOf(betCount));
		betInfo.setMulti(Long.valueOf(mul));
		betInfo.setMoney(money);
		betInfo.setPattern(lt.getPassway().toString());
		boolean isValid = SystemProcessor.isValidBet(SystemCache
				.getCurrentLottery());
		boolean isEnough = (SystemCache.availableMoney >= money);
		// 投注订单有效，会生成交易记录（交易失败或成功）
		if (isValid) {
			betInfo.setValid(Long.valueOf(1));
			tradeInfo.setUser(user);
			tradeInfo.setInout(Long.valueOf(1)); // 支出
			tradeInfo.setType(Long.valueOf(11)); // 代购
			tradeInfo.setAmount(money * 1.0);
			tradeInfo.setRemain(SystemCache.availableMoney); // 交易后余额
			// 免费模式，直接模拟下单出票
			if (0 == SystemCache.fee_mode) {
				log.info("CLIENT_MODE = 0");
				// 余额充足
				if (isEnough) {
					double remain = capInfo.getRemain() - money;
					double availbale = SystemCache.availableMoney - money;
					betInfo.setStatus(Long.valueOf(3)); // 已出票
					betInfo.setCtime(new Date());
					tradeInfo.setRemain(remain);
					tradeInfo.setStatus(Long.valueOf(1));
					capInfo.setRemain(remain);
					capInfo.setAvailable(availbale);
					capInfo.setBet(capInfo.getBet() + money); // 累计投注金额
					log.info("投注成功");
				}
				// 余额不足
				else {
					betInfo.setStatus(Long.valueOf(0)); // 下单失败，生成交易失败记录
					tradeInfo.setStatus(Long.valueOf(2));
					tradeInfo.setRemark("余额不足");
					log.info("投注失败,余额不足");
				}
			}
			// 实际投注模式
			else {
				log.info("fee_mode = 1");
				// 余额充足
				if (isEnough) {
					// 出票中,冻结资金
					betInfo.setStatus(Long.valueOf(2));
					double freeze = capInfo.getFreeze() + money;
					double available = SystemCache.availableMoney - money;
					tradeInfo.setStatus(Long.valueOf(0));
					tradeInfo.setRemark("出票中");
					capInfo.setFreeze(freeze);
					capInfo.setAvailable(available);
					// 出票中投注资金被冻结，出票后资金减少，添加一条交易记录
					// TODO 出票接口
				}
				// 余额不足
				else {
					betInfo.setStatus(Long.valueOf(0)); // 下单失败，生成交易失败记录
					tradeInfo.setStatus(Long.valueOf(2));
					tradeInfo.setRemark("余额不足");
				}
			}
		}
		// 投注订单无效，不产生交易记录
		else {
			betInfo.setValid(Long.valueOf(0));
			betInfo.setStatus(Long.valueOf(0));
		}
		// 保存投注信息
		tradeInfo.setBetForm(betInfo);
		boolean isSuccess = som.addBetInfo(betInfo, tradeInfo, capInfo);
		if (isSuccess) {
			if (isValid) {
				SystemCache.availableMoney -= money;
				JOptionPane.showMessageDialog(null, "投注成功!\n可用余额："
						+ SystemCache.availableMoney);
				log.info("投注成功! 可用余额：" + SystemCache.availableMoney);
			} else {
				JOptionPane.showMessageDialog(null, "方案无效,超过投注截至时间!\n可用余额："
						+ SystemCache.availableMoney);
			}
		} else {
			JOptionPane.showMessageDialog(null, "系统错误,投注失败");
			log.info("系统错误,投注失败");
		}
	}
	
	/**
	 * @方法说明: 查看用户信息
	 * @参数:     
	 * @return    void
	 * @throws
	 */
	public void userInfoCk() {
		if (!SystemCache.isLogin) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN,
					MessageConstants.INFOMATION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		User user = SystemCache.currentUser;
		SocketManager som = SocketManager.getInstance();
		UserInfo userInfo = som.getUserInfoById(user.getUserid());
	}

	/**
	 * @方法说明: 查看生成的投注信息
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void betInfoCk() {
		if (!SystemCache.isLogin) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN,
					MessageConstants.INFOMATION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		User user = SystemCache.currentUser;
		SocketManager som = SocketManager.getInstance();
		List<BetForm> betInfos = som.getBetInfoByUserId(user.getUserid());
		// 生成投注订单信息页面
		BetFormHtmlPage bpage = new BetFormHtmlPage();
		bpage.genBetInfoHtmlPage(betInfos);
		// 生成投注方案信息页面
		BetDetailHtmlPage dpage = new BetDetailHtmlPage();
		dpage.genBetDetailHtmlPage(betInfos);

		String uid = user.getUserid();
		String fileDir = FilePathConstants.HTML_FILE_DIR;
		String fileName = uid + FilePathConstants.HTML_POSTFIX;
		String theFile = fileDir + File.separator + fileName;
		BareBonesBrowserLaunch.openURL(theFile);
	}

}
