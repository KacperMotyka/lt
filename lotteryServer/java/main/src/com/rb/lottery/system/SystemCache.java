/**
 * @文件名称: SystemCache.java
 * @类路径:   com.rb.lottery.system
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午01:02:06
 * @版本:     1.0.0
 */
package com.rb.lottery.system;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.cache.BetBasketCache;
import com.rb.lottery.server.cache.DataCache;
import com.rb.lottery.server.cache.LotteryCache;
import com.rb.lottery.server.server.ServerThread;
import com.rb.lottery.user.User;

/**
 * @类功能说明: 系统缓存
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 下午01:02:06
 * @版本: 1.0.0
 */

public class SystemCache {

	private static SystemCache systemCache = null;

	public static String current_version = null;
	public static ServerThread server = null;
	public static Timer updateTimer = null;
	
	public static int fee_mode;
	public static int save_mode;
	public static int trade_mode;

	public static DataCache dataCache;
	public static LotteryCache lotteryCache;
	public static BetBasketCache basketCache;
	public static int currentType = -1;
	public static String currentQihao = SystemConstants.EMPTY_STRING;
	public static String currentLotteryId = SystemConstants.EMPTY_STRING;
	public static String currentBasketId = SystemConstants.EMPTY_STRING;

	public static String free_memory = SystemConstants.EMPTY_STRING;

	private SystemCache() {
		dataCache = new DataCache();
		lotteryCache = new LotteryCache();
		basketCache = new BetBasketCache();
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static void init() {
		if (systemCache == null) {
			systemCache = new SystemCache();
		} else {
			clear();
		}
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private static void clear() {
		dataCache.clear();
		lotteryCache.clear();
		basketCache.clear();
	}

	/**
	 * @方法说明:
	 * @参数: @param map
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static void initDataCache(Map<String, List<BetMatch>> data) {
		dataCache.setData(data);
		currentQihao = dataCache.getQihao();
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static void initLotteryCache() {
		lotteryCache.clear();
		doLotteryInit(null);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static void initLottery() {
		doLotteryInit(null);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static void initLottery(Lottery lottery) {
		doLotteryInit(lottery);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private static void doLotteryInit(Lottery lottery) {
		lotteryCache.add(lottery);
		currentLotteryId = lottery.getLotteryId();
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static void initBasketCache() {
		basketCache.clear();
		doBasketInit(null);
	}

	/**
	 * @方法说明: 初试化新的号码篮
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static void addBasket(BetBasket basket) {
		String basketId = basket.getId();
		if (basketCache.contains(basketId)) {
			int isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.BASKET_EXIST_INFO,
					MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (isOK == 0) {
				basketCache.removeByKey(basketId);
				doBasketInit(basket);
			}
		} else {
			doBasketInit(basket);
		}

	}

	/**
	 * @方法说明: 初始化当前号码篮
	 * @参数:BetBasket basket
	 * @return void
	 * @throws
	 */
	public static void initBasket(BetBasket basket) {
		String basketId = basket.getId();
		if (basketCache.contains(basketId)) {
			basketCache.removeByKey(basketId);
		} else {
			basketCache.removeByKey(currentBasketId);
		}
		doBasketInit(basket);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private static void doBasketInit(BetBasket basket) {
		basketCache.add(basket);
		currentBasketId = basket.getId();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String getBetStatu() {
		String status = lotteryCache.getBetStatu(currentLotteryId);
		return status;
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return TreeMap<String,List<BetMatch>>
	 * @throws
	 */
	public static TreeMap<String, List<BetMatch>> getMatchData() {
		return dataCache.getData();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return TreeMap<String,List<BetMatch>>
	 * @throws
	 */
	public static BetMatch getMatchById(String id) {
		return dataCache.getMatchById(id);
	}

	/**
	 * @方法说明:
	 * @参数: @param id
	 * @参数: @param bet
	 * @return void
	 * @throws
	 */
	public static void removeOneBet(String matchId, String bet) {
		lotteryCache.removeOneBet(currentLotteryId, matchId, bet);
	}

	/**
	 * @方法说明:
	 * @参数: @param id
	 * @参数: @param bet
	 * @return void
	 * @throws
	 */
	public static void addOneBet(String matchId, String bet) {
		lotteryCache.addOneBet(currentLotteryId, matchId, bet);
	}

	/**
	 * @方法说明:
	 * @参数: @param lotteryId
	 * @参数: @return
	 * @return Lottery
	 * @throws
	 */
	public static Lottery getLottery(String lotteryId) {
		return lotteryCache.getLotteryById(lotteryId);
	}

	/**
	 * @方法说明:
	 * @参数: @param basketId
	 * @参数: @return
	 * @return BetBasket
	 * @throws
	 */
	public static BetBasket getBasket(String basketId) {
		return basketCache.getBasketById(basketId);
	}

	/**
	 * @方法说明:
	 * @参数: @param lotteryId
	 * @参数: @param passway
	 * @return void
	 * @throws
	 */
	public static void setLotteryPassway(String lotteryId, List<String> passway) {
		lotteryCache.setLotteryPassway(lotteryId, passway);
	}

	/**
	 * @方法说明:
	 * @参数: @param lotteryId
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String getLotteryMatchIds(String lotteryId) {
		return lotteryCache.getLotteryById(lotteryId).getLotteryMatchIds();
	}

	/**
	 * @方法说明:
	 * @参数: @param lotteryId
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getLotteryMatchCount(String lotteryId) {
		return lotteryCache.getLotteryById(lotteryId).getBetMatchCount();
	}

	/**
	 * @param basketId
	 * @方法说明:
	 * @参数: @param matchCount
	 * @return void
	 * @throws
	 */
	public static void setBasketMatchCount(String basketId, int matchCount) {
		basketCache.setBasketMatchCount(basketId, matchCount);
	}

	/**
	 * @方法说明: 获取当前出票信息
	 * @参数: @return
	 * @return Lottery
	 * @throws
	 */
	public static Lottery getCurrentLottery() {
		return lotteryCache.getLotteryById(currentLotteryId);
	}

	/**
	 * @方法说明: 获取当前号码篮信息
	 * @参数: @return
	 * @return BetBasket
	 * @throws
	 */
	public static BetBasket getCurrentBasket() {
		return basketCache.getBasketById(currentBasketId);
	}

}
