/**
 * @文件名称: Lottery.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-21 下午03:27:33
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;

/**
 * @类功能说明: POJO-彩票基类
 * @类修改者: robin
 * @修改日期: 2012-02-29
 * @修改说明: 增加新字段
 * @作者: robin
 * @创建时间: 2011-10-21 下午03:27:33
 * @版本: 1.0.0
 */

public class Lottery implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -5125343494184460451L;

	protected String lotteryId; // 每张彩票的唯一标识

	protected int type; // 类型
	protected String qihao; // 期号
	protected String bet;
	// ver 1.0.0 不支持单复式混合使用
	protected boolean single; // 是否单式
	protected Map<String, String> mulMatches; // 投注场次(复式)
	protected Map<String, List<String>> sinMatches; // 投注场次(单式,可能有重复注)
	protected List<String> passway; // 过关方式
	protected int multi; // 投注倍数
	protected String kjNumber; // 所有比赛开奖号码串

	protected int[] selects = { 0, 0, 0, 0 }; // 胆,单选,双选,三选个数

	public Lottery() {
		lotteryId = UUID.randomUUID().toString();
		qihao = SystemConstants.EMPTY_STRING;
		bet = SystemConstants.EMPTY_STRING;
		single = false;
		mulMatches = new TreeMap<String, String>();
		sinMatches = new TreeMap<String, List<String>>();
		passway = new ArrayList<String>();
		multi = 1;
	}

	public Lottery(int type) {
		lotteryId = UUID.randomUUID().toString();
		this.type = type;
		qihao = SystemConstants.EMPTY_STRING;
		bet = SystemConstants.EMPTY_STRING;
		single = false;
		mulMatches = new TreeMap<String, String>();
		sinMatches = new TreeMap<String, List<String>>();
		passway = new ArrayList<String>();
		multi = 1;
	}

	/**
	 * @类名: Lottery.java
	 * @描述: TODO
	 * @param type
	 * @param qihao
	 * @param bet
	 */
	public Lottery(int type, String qihao, String bet) {
		lotteryId = UUID.randomUUID().toString();
		this.type = type;
		this.qihao = qihao;
		this.bet = bet;
		single = false;
		mulMatches = new TreeMap<String, String>();
		sinMatches = new TreeMap<String, List<String>>();
		passway = new ArrayList<String>();
		multi = 1;
	}

	/**
	 * @return lotteryId
	 */
	public String getLotteryId() {
		return lotteryId;
	}

	/**
	 * @param lotteryId
	 *            lotteryId
	 */
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return qihao
	 */
	public String getQihao() {
		return qihao;
	}

	/**
	 * @param qihao
	 *            qihao
	 */
	public void setQihao(String qihao) {
		this.qihao = qihao;
	}

	/**
	 * @return bet
	 */
	public String getBet() {
		return bet;
	}

	/**
	 * @param bet
	 *            bet
	 */
	public void setBet(String bet) {
		this.bet = bet;
	}

	/**
	 * @return single
	 */
	public boolean isSingle() {
		return single;
	}

	/**
	 * @param single
	 *            single
	 */
	public void setSingle(boolean single) {
		this.single = single;
	}

	/**
	 * @return mulMatches
	 */
	public Map<String, String> getMulMatches() {
		return mulMatches;
	}

	/**
	 * @param mulMatches
	 *            mulMatches
	 */
	public void setMulMatches(Map<String, String> mulMatches) {
		this.mulMatches = mulMatches;
	}

	/**
	 * @return sinMatches
	 */
	public Map<String, List<String>> getSinMatches() {
		return sinMatches;
	}

	/**
	 * @param sinMatches
	 *            sinMatches
	 */
	public void setSinMatches(Map<String, List<String>> sinMatches) {
		this.sinMatches = sinMatches;
	}

	/**
	 * @return passway
	 */
	public List<String> getPassway() {
		return passway;
	}

	/**
	 * @param passway
	 *            passway
	 */
	public void setPassway(List<String> passway) {
		this.passway = passway;
	}

	/**
	 * @方法说明: 删除1场比赛的若干投注
	 * @参数: @param id
	 * @参数: @param colIndex
	 * @return void
	 * @throws
	 */
	public void remove(String id, String removeVal) {
		if (mulMatches.containsKey(id)) {
			String originVal = mulMatches.get(id);
			String newVal = originVal;
			int isDan = SystemFunctions.isDan(originVal);
			int oriLen = originVal.length() - isDan;
			int rmvLen = removeVal.length();
			if (rmvLen > 1) {
				mulMatches.remove(id);
				removeIlegalPassway();
				selects[oriLen]--;
				selects[0] -= isDan;
			} else {
				String regex = removeVal;
				if (regex.equals(SystemConstants.BET_DAN)) {
					regex = RegexConstants.DAN_REGEX;
					selects[0] -= isDan;
					newVal = newVal.replaceAll(regex,
							SystemConstants.EMPTY_STRING);
					mulMatches.put(id, newVal);
				} else {
					if (oriLen < 2) {
						mulMatches.remove(id);
						removeIlegalPassway();
						selects[oriLen]--;
						selects[0] -= isDan;
					} else {
						selects[oriLen]--;
						selects[oriLen - 1]++;
						newVal = newVal.replaceAll(regex,
								SystemConstants.EMPTY_STRING);
						mulMatches.put(id, newVal);
					}
				}
			}
		}
	}

	/**
	 * @方法说明: 去除非法串
	 * @参数:
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void removeIlegalPassway() {
		if (type > 2) {
			int matchCount = this.getBetMatchCount();
			if (passway == null || passway.size() == 0) {
				return;
			}
			Iterator it = passway.iterator();
			while (it.hasNext()) {
				String psw = (String) it.next();
				String[] spt = psw.split(SystemConstants.CHUAN);
				String mtc = spt[0].trim();
				if (mtc.matches(RegexConstants.INTEGER_REGEX)) {
					int imtc = Integer.parseInt(mtc);
					if (imtc > matchCount) {
						it.remove();
					}
				} else if (mtc.equals(SystemConstants.PASSWAY_SINGLE.trim())) {
					if (0 == matchCount) {
						it.remove();
					}
				} else {
					it.remove();
				}
			}
		}
	}

	/**
	 * @方法说明: 添加1场比赛的若干投注
	 * @参数: @param id
	 * @参数: @param colIndex
	 * @return void
	 * @throws
	 */
	public void add(String id, String addVal) {
		String newVal = SystemConstants.EMPTY_STRING;
		int addLen = addVal.length();
		if (mulMatches.containsKey(id)) {
			String originVal = mulMatches.get(id);
			int oriDan = SystemFunctions.isDan(originVal);
			int oriLen = originVal.length() - oriDan;
			newVal = SystemFunctions.betCombine(originVal, addVal);
			int newDan = SystemFunctions.isDan(newVal);
			int newLen = newVal.length() - newDan;
			int addDan = newDan - oriDan;
			selects[0] += addDan;
			selects[oriLen]--;
			selects[newLen]++;
			mulMatches.put(id, newVal);
		} else {
			if (!addVal.equals(SystemConstants.BET_DAN)) {
				mulMatches.put(id, addVal);
				selects[addLen]++;
			}
		}
	}

	/**
	 * @方法说明: 获取投注场次
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getBetMatchCount() {
		int result = 0;
		Set keySet = mulMatches.keySet();
		if (keySet != null) {
			result = keySet.size();
		}
		return result;
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getBetStatu() {
		String result = SystemConstants.BET_TYPES[type]
				+ SystemConstants.BLANK_STRING;
		int psw = passway.size();
		String paswy = SystemConstants.EMPTY_STRING;
		if (psw == 0) {
			paswy = SystemConstants.UNSELECT_PASSWAY;
		} else if (psw == 1) {
			paswy = passway.get(0);
		} else {
			paswy = SystemConstants.MULTI_PASSWAY;
		}
		result += paswy + SystemConstants.BLANK_STRING;
		result += SystemConstants.SINGLE_SELECT + SystemConstants.LEFT_BRACKET
				+ selects[1] + SystemConstants.RIGHT_BRACKET;
		result += SystemConstants.DOUBLE_SELECT + SystemConstants.LEFT_BRACKET
				+ selects[2] + SystemConstants.RIGHT_BRACKET;
		result += SystemConstants.TRIBLE_SELECT + SystemConstants.LEFT_BRACKET
				+ selects[3] + SystemConstants.RIGHT_BRACKET;
		result += SystemConstants.DAN + SystemConstants.LEFT_BRACKET
				+ selects[0] + SystemConstants.RIGHT_BRACKET;
		return result;
	}

	/**
	 * @return selects
	 */
	public int[] getSelects() {
		return selects;
	}

	/**
	 * @param selects
	 *            selects
	 */
	public void setSelects(int[] selects) {
		this.selects = selects;
	}

	/**
	 * @param selects
	 *            selects
	 */
	public void setSelects(String src) {
		String[] tmp = src.split(SystemConstants.COMMA);
		tmp[3] = tmp[3].trim();
		selects[0] = Integer.parseInt(tmp[3]);
		tmp[0] = tmp[0].trim();
		selects[1] = Integer.parseInt(tmp[0]);
		tmp[1] = tmp[1].trim();
		selects[2] = Integer.parseInt(tmp[1]);
		tmp[2] = tmp[2].trim();
		selects[3] = Integer.parseInt(tmp[2]);
	}

	public String getSelectsString() {
		String result = selects[1] + SystemConstants.COMMA + selects[2]
				+ SystemConstants.COMMA + selects[3] + SystemConstants.COMMA
				+ selects[0];
		return result;
	}

	/**
	 * @方法说明: 添加一场比赛投注（复式）
	 * @参数: @param matchId
	 * @参数: @param betNumber
	 * @return void
	 * @throws
	 */
	public void addMulMatch(String matchId, String betNumber) {
		if (mulMatches == null) {
			mulMatches = new TreeMap<String, String>();
		}
		mulMatches.put(matchId, betNumber);
	}

	/**
	 * @方法说明: 添加一注投注（单式）
	 * @参数: @param mids
	 * @参数: @param betNumber
	 * @return void
	 * @throws
	 */
	public void addSinMatch(String mids, String betNumber) {
		if (sinMatches == null) {
			sinMatches = new TreeMap<String, List<String>>();
		}
		List<String> bets = sinMatches.get(mids);
		if (null == bets) {
			bets = new ArrayList<String>();
		}
		bets.add(betNumber);
		sinMatches.put(mids, bets);
	}

	/**
	 * @方法说明:获取投注比赛场次号
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getLotteryMatchIds() {
		String result = SystemConstants.EMPTY_STRING;
		if (single) {
			Iterator it = sinMatches.keySet().iterator();
			if (it.hasNext()) {
				result = (String) it.next();
			}
		} else {
			Iterator it = mulMatches.keySet().iterator();
			int tmp = 0;
			while (it.hasNext()) {
				String id = (String) it.next();
				if (tmp == 0) {
					result += id;
				} else {
					result += SystemConstants.COMMA + id;
				}
				tmp++;
			}
		}
		return result;
	}

	/**
	 * @方法说明:获取投注比赛单式号码
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getLotteryNumbers() {
		String result = SystemConstants.EMPTY_STRING;
		Set keySet = mulMatches.keySet();
		Iterator it = keySet.iterator();
		int tmp = 0;
		while (it.hasNext()) {
			String id = (String) it.next();
			String number = mulMatches.get(id);
			if (tmp == 0) {
				result += number;
			} else {
				result += SystemConstants.COMMA + number;
			}
			tmp++;
		}
		return result;
	}

	/**
	 * @方法说明:获取投注比赛单式号码
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public byte[] getLotterySinNumbers() {
		Iterator it = sinMatches.keySet().iterator();
		int tmp = 0;
		byte[] data = null;
		byte[] temp;
		while (it.hasNext()) {
			String id = (String) it.next();
			List<String> numbers = sinMatches.get(id);
			if (numbers != null && numbers.size() > 0) {
				for (String num : numbers) {
					if (tmp == 0) {
						temp = num.getBytes();
						data = temp.clone();
					} else {
						temp = ('\n' + num).getBytes();
						data = SystemFunctions.bytecat(data, temp);
					}
					tmp++;
				}
			}
		}
		return data;
	}

	/**
	 * @return multi
	 */
	public int getMulti() {
		return multi;
	}

	/**
	 * @param multi
	 *            multi
	 */
	public void setMulti(int multi) {
		this.multi = multi;
	}

	/**
	 * @return kjNumber
	 */
	public String getKjNumber() {
		return kjNumber;
	}

	/**
	 * @param kjNumber
	 *            kjNumber
	 */
	public void setKjNumber(String kjNumber) {
		this.kjNumber = kjNumber;
	}

	public void addPassway(String[] passways) {
		if (passway == null) {
			passway = new ArrayList<String>();
		}
		for (int i = 0; i < passways.length; i++) {
			passway.add(passways[i]);
		}
	}

	/**
	 * @方法说明: 投注单转化为彩票信息
	 * @参数: @param bet
	 * @参数: @return
	 * @return Lottery
	 * @throws
	 */
	public static Lottery convertFromBet(BetForm bf) {
		if (bf == null) {
			return null;
		}
		Lottery lt = new Lottery();
		lt.setType(Integer.valueOf(bf.getLtType().toString()));
		lt.setQihao(bf.getQihao());
		long bt = bf.getBtType() % 10;
		if (0 == bt) {
			lt.setSingle(true);
		} else {
			lt.setSingle(false);
		}
		lt.setKjNumber(bf.getKjnumbers());
		String passway = bf.getPattern();
		passway = passway.substring(1, passway.length() - 1);
		lt.addPassway(passway.split(SystemConstants.COMMA));
		String mid = bf.getMids().trim();
		String[] mids = mid.split(SystemConstants.COMMA);
		String[] numbers = bf.getNumbers().trim().split(SystemConstants.COMMA);
		int mlen = mids.length;
		int nlen = numbers.length;
		// 非法投注单
		if (mlen != nlen) {
			return null;
		}
		for (int i = 0; i < mlen; i++) {
			lt.addMulMatch(mids[i], numbers[i]);
		}
		Blob singles = bf.getSinnumbers();
		if (null != singles) {
			try {
				InputStream ins = singles.getBinaryStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				ins.close();
				// 处理单式号码
				int len = data.length;
				int start = 0;
				String number = null;
				for (int i = 0; i < len; i++) {
					if (',' == data[i]) {
						number = new String(data, start, i - start);
						start = i + 1;
						lt.addSinMatch(mid, number);
					}
				}
				number = new String(data, start, len - start);
				lt.addSinMatch(mid, number);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lt;
	}
}
