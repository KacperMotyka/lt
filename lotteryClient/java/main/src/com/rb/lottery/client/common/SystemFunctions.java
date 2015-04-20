/**
 * @文件名称: SystemFunctions.java
 * @类路径:   com.rb.lottery.common
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-1 上午10:56:14
 * @版本:     1.0.0
 */
package com.rb.lottery.client.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.system.SysConfig;

/**
 * @类功能说明: 系统常用方法
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-1 上午10:56:14
 * @版本: 1.0.0
 */

public final class SystemFunctions {

	/**
	 * @方法说明: string转换成boolean
	 * @参数: @param src
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public static boolean stringToBoolean(String src) {
		if (src == null) {
			return false;
		}
		if (SystemConstants.TRUE.equalsIgnoreCase(src)) {
			return true;
		}
		return false;
	}

	/**
	 * @方法说明: 投注类型转换
	 * @参数: @param typeName
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int convertByTypeName(String typeName) {
		int type = -1;
		if (typeName.equals(SystemConstants.BET_TYPES[0])) {
			type = 0;
		} else if (typeName.equals(SystemConstants.BET_TYPES[1])) {
			type = 1;
		} else if (typeName.equals(SystemConstants.BET_TYPES[2])) {
			type = 2;
		} else if (typeName.equals(SystemConstants.BET_TYPES[3])) {
			type = 3;
		}
		return type;
	}

	/**
	 * @方法说明: 投注合法性判断
	 * @参数: @param type
	 * @参数: @param bet
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isBetLegal(int type, String bet) {
		if (bet.equals(SystemConstants.EMPTY_STRING)) {
			return true;
		}
		int ret = 0;
		if (type == 0) {
			ret = calc14CombBetNum(bet);
			if (ret > 0) {
				return true;
			} else {
				return false;
			}
		}
		if (type == 1) {
			ret = calcR9CombBetNum(bet);
			if (ret > 0) {
				return true;
			} else {
				return false;
			}
		}
		if (type == 2 || type == 3) {
			ret = calcBDJZCombBetNum(bet);
			if (ret > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * @方法说明: 计算14胜负彩复式注数，格式[3|1|0]{1-3}[,[3|1|0]{1-3}]{13}
	 * @参数: @param bet
	 * @参数: @return
	 * @return int 返回0则非法
	 * @throws
	 */
	public static int calc14CombBetNum(String bet) {
		int betNum = 1;
		if (bet == null) {
			return SystemConstants.NONE;
		}
		bet = bet.trim();
		String[] bts = bet.split(SystemConstants.COMMA);
		if (bts.length != 14) {
			return SystemConstants.NONE;
		}
		String ret_Regex = RegexConstants.SFC14_REGEX;
		for (int i = 0; i < 14; i++) {
			bts[i] = bts[i].trim();
			if (!bts[i].matches(ret_Regex)) {
				return SystemConstants.NONE;
			} else {
				betNum *= bts[i].length();
			}
		}
		return betNum;
	}

	/**
	 * @方法说明: 组合排列
	 * @参数: @param n
	 * @参数: @param i
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int C(int n, int m) {
		m = m < (n - m) ? m : (n - m);
		if (m == 0) {
			return 1;
		} else {
			return n * C(n - 1, m - 1) / m;
		}
	}

	/**
	 * @方法说明: 计算N*M是否合法
	 * @参数: @param n
	 * @参数: @param m
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int doCheckBetPattern(int n, int m) {
		int ret = 0;
		for (int i = 0; i < n; i++) {
			ret += C(n, i);
			if (ret == m) {
				return i + 1;
			}
			if (ret > m) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * @方法说明: 从0到x-1中取y个数形成全排列结果
	 * @参数: @param ret 返回结果
	 * @参数: @param x
	 * @参数: @param y
	 * @return void
	 * @throws
	 */
	public static void generateQP(int[][] ret, int x, int y) {
		int Cnum = C(x, y);
		int[] assArr = new int[y];
		for (int i = 0; i < y; i++) {
			assArr[i] = i;
		}
		int wrtpos = y - 1;
		int rIndx = 0;
		while (rIndx < Cnum) {
			if (assArr[wrtpos] < x) {
				ret[rIndx] = assArr.clone();
				rIndx++;
				assArr[wrtpos]++;
			} else {
				wrtpos--;
				assArr[wrtpos]++;
				for (int i = wrtpos + 1; i < y; i++) {
					assArr[i] = assArr[i - 1] + 1;
				}
				while (assArr[wrtpos] >= x) {
					if (wrtpos == 0) {
						break;
					}
					wrtpos--;
					assArr[wrtpos]++;
					for (int i = wrtpos + 1; i < y; i++) {
						assArr[i] = assArr[i - 1] + 1;
					}
				}
				wrtpos = y - 1;
			}
		}

	}

	/**
	 * @方法说明: 计算从x场比赛中取y场比赛形成y串1的注数
	 * @参数: @param bets 投注个数
	 * @参数: @param x 选择的场次
	 * @参数: @param y y串1投注
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int calcNum(int[] bets, int x, int y) {
		int Cnum = C(x, y);
		int[][] ret = new int[Cnum][y]; // 全排列结果
		generateQP(ret, x, y);
		int betNum = 0;
		for (int i = 0; i < Cnum; i++) {
			int oneBet = 1;
			for (int j = 0; j < y; j++) {
				oneBet *= bets[ret[i][j]];
			}
			betNum += oneBet;
		}
		return betNum;
	}

	/**
	 * @方法说明: 计算任9复式注数，格式[3|1|0]{1-3}[,[3|1|0]{1-3}]{13}
	 * @参数: @param bet
	 * @参数: @return
	 * @return int 返回0则非法
	 * @throws
	 */
	public static int calcR9CombBetNum(String bet) {
		int betNum = 1;
		int tzcc = 0;
		int dan = 0;
		if (bet == null) {
			return SystemConstants.NONE;
		}
		bet = bet.trim();
		String[] bts = bet.split(SystemConstants.COMMA);
		if (bts.length != 14) {
			return SystemConstants.NONE;
		}
		String ret_Regex = RegexConstants.BET_REGEX;
		String non_ret_Regex = RegexConstants.NONE_BET_REGEX;
		int[] bets = new int[14];
		int bCount = 0;
		for (int i = 0; i < 14; i++) {
			bts[i] = bts[i].trim();
			if (bts[i].matches(ret_Regex)) {
				tzcc++;
				if (bts[i].charAt(bts[i].length() - 1) == SystemConstants.ASTERISK) {
					dan++;
					betNum *= (bts[i].length() - 1);
				} else {
					bets[bCount++] = bts[i].length();
				}
			} else if (bts[i].matches(non_ret_Regex)) {
				continue;
			} else {
				return SystemConstants.NONE;
			}
		}
		if (dan > 9 || tzcc < 9) {
			return SystemConstants.NONE;
		}
		int x_left = 9 - dan;
		int z_left = tzcc - dan;
		betNum *= calcNum(bets, z_left, x_left);
		return betNum;
	}

	/**
	 * @方法说明: 计算复式投注注数，格式BET|(3|1|0|*)(,)(-|#)|M*N:X
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int calcBDJZCombBetNum(String bet) {
		int betNum = 0;
		int ilLegal = -1;
		int tzcc = 0;
		int dan = 0;
		int len = 0;
		if (bet == null) {
			return ilLegal;
		}
		bet = bet.trim();
		len = bet.length();
		int part1 = bet.indexOf(SystemConstants.VERTICAL);
		if (part1 < 0 || part1 + 1 == len) {
			return ilLegal;
		}
		String title = bet.substring(0, part1);
		bet = bet.substring(part1 + 1);
		int part2 = bet.lastIndexOf(SystemConstants.VERTICAL);
		if (part2 < 0) {
			return ilLegal;
		}
		// 选择的场次
		String select = bet.substring(0, part2);
		len = bet.length();
		if (part2 + 1 == len) {
			return ilLegal;
		}
		// 投注方式
		String pattern = bet.substring(part2 + 1);
		title = title.trim();
		select = select.trim();
		pattern = pattern.trim();
		// do analysis after split the String
		if (!title.equalsIgnoreCase(SystemConstants.BET_STRING)) {
			return ilLegal;
		}

		String[] selects = select.split(SystemConstants.COMMA);
		int selNum = selects.length;
		String ret_Regex = RegexConstants.BET_REGEX;
		String non_ret_Regex = RegexConstants.NONE_BET_REGEX;
		int[] bets = new int[selNum];
		int[] dans = new int[selNum];
		int bCount = 0;
		int dCount = 0;
		int alldan = 1;
		for (String sel : selects) {
			sel = sel.trim();
			if (sel.matches(ret_Regex)) {
				tzcc++;
				if (sel.charAt(sel.length() - 1) == SystemConstants.ASTERISK) {
					dan++;
					alldan *= (sel.length() - 1);
					dans[dCount++] = sel.length() - 1;
				} else {
					bets[bCount++] = sel.length();
				}
			} else if (sel.matches(non_ret_Regex)) {
				continue;
			} else {
				return ilLegal;
			}
		}

		String integer_Regex = RegexConstants.INTEGER_REGEX;
		int plen = pattern.length();
		int bt = pattern.indexOf(SystemConstants.COLON);
		if (bt < 0) {
			bt = pattern.indexOf(SystemConstants.UNDERLINE);
			if (bt < 0) {
				return ilLegal;
			}
		}
		if (bt + 1 == plen) {
			return ilLegal;
		}
		String betPattern = pattern.substring(0, bt);
		betPattern = betPattern.trim();
		String mul = pattern.substring(bt + 1);
		mul = mul.trim();
		if (!mul.matches(integer_Regex)) {
			return ilLegal;
		}
		int multiple = Integer.parseInt(mul); // 倍数
		int bplen = betPattern.length();
		int chuan = betPattern.indexOf(SystemConstants.ASTERISK_STRING);
		if (chuan < 0) {
			return ilLegal;
		}
		if (chuan + 1 == bplen) {
			return ilLegal;
		}
		String ns = betPattern.substring(0, chuan);
		ns = ns.trim();
		if (!ns.matches(integer_Regex)) {
			return ilLegal;
		}
		int ins = Integer.parseInt(ns); // N
		if (ins == 0 || ins > tzcc) {
			return ilLegal;
		}
		String ms = betPattern.substring(chuan + 1);
		ms = ms.trim();
		if (!ms.matches(integer_Regex)) {
			return ilLegal;
		}
		int ims = Integer.parseInt(ms); // M
		if (ims == 0) {
			return ilLegal;
		}
		int bst = doCheckBetPattern(ins, ims); // N*M 当M=1, bst=1
		if (bst < 0) {
			return ilLegal;
		}
		// calculate
		if (dan >= ins) {
			for (int i = 0; i < bst; i++) {
				betNum += calcNum(dans, dan, (ins - i));
			}
		} else {
			for (int i = 0; i < bst; i++) {
				if (dan < (ins - i)) {
					int x_left = (ins - i) - dan;
					int z_left = tzcc - dan;
					betNum += alldan * calcNum(bets, z_left, x_left);
				} else {
					betNum += calcNum(dans, dan, (ins - i));
				}
			}
		}
		betNum *= multiple;
		return betNum;
	}

	/**
	 * @方法说明: 字符串填充
	 * @参数: @param src 原串
	 * @参数: @param fill 填充串
	 * @参数: @param count 填充次数
	 * @参数: @param rurn 0-头填充 1-尾填充
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String polishing(String src, String fill, int count, int rurn) {
		String result = src;
		if (rurn == 0) {
			for (int i = 0; i < count; i++) {
				result = fill + result;
			}
		} else {
			for (int i = 0; i < count; i++) {
				result += fill;
			}
		}
		return result;
	}

	/**
	 * @方法说明: 百分比转换为浮点数
	 * @参数: @param src
	 * @参数: @return
	 * @return double
	 * @throws
	 */
	public static double percentToDouble(String src) {
		double result;
		String str = src.substring(0, src.length() - 1);
		try {
			result = Double.parseDouble(str) / 100;
		} catch (Exception e) {
			e.printStackTrace();
			return SystemConstants.NONE;
		}
		return result;
	}

	/**
	 * @方法说明: 浮点数转换为百分比
	 * @参数: @param src
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String doubleToPercent(double src) {
		String result = Math.round(src * 100) + SystemConstants.PERCENT;
		return result;
	}

	/**
	 * @方法说明: 日期转换
	 * @参数: @param tmpstr
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String DateConvert(String src) {
		String result = SystemConstants.EMPTY_STRING;
		String[] tmp = src.split(SystemConstants.YEAR);
		result += tmp[0] + SystemConstants.PART;
		tmp = tmp[1].split(SystemConstants.MONTH);
		result += tmp[0] + SystemConstants.PART;
		tmp = tmp[1].split(SystemConstants.DATE);
		result += tmp[0];
		return result;
	}

	/**
	 * @方法说明: 日期转换
	 * @参数: @param tmpstr
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public static String DateConvert(Date date) {
		String result = SystemConstants.EMPTY_STRING;
		result += date.getYear() + SystemConstants.PART;
		result += date.getMonth() + SystemConstants.PART;
		result += date.getDay();
		return result;
	}

	/**
	 * @方法说明: 减1操作
	 * @参数: @param src
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String minusOne(String src) {
		return (Integer.parseInt(src) - 1) + SystemConstants.EMPTY_STRING;
	}

	/**
	 * @方法说明: 减1天操作
	 * @参数: @param src 格式 2011-11-04
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String minusOneDay(String src) {
		String result = SystemConstants.EMPTY_STRING;
		DateFormat format = new SimpleDateFormat(
				SystemConstants.DEFAULT_DATEFORMAT);
		java.util.Date date = null;
		try {
			date = format.parse(src);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();
		result = format.format(date);
		return result;
	}

	/**
	 * @方法说明: 生成期号列表
	 * @参数: @param type
	 * @参数: @param qihao
	 * @参数: @param maxQihaoCount
	 * @return void
	 * @throws
	 */
	public static String[] generateQihaos(int type, String qihao,
			int maxQihaoCount) {
		String[] result = new String[maxQihaoCount];
		result[0] = qihao + SystemConstants.BLANK_STRING
				+ SystemConstants.CURRENT_PERIOD;
		if (type < 2) {
			int thisYear = Integer.parseInt(qihao.substring(2));
			int yearleft = thisYear - maxQihaoCount;
			if (yearleft >= 0) {
				for (int i = 1; i < maxQihaoCount; i++) {
					qihao = SystemFunctions.minusOne(qihao);
					result[i] = qihao;
				}
			} else {
				int i = 0;
				for (i = 1; i < thisYear; i++) {
					qihao = SystemFunctions.minusOne(qihao);
					result[i] = qihao;
				}
				String year = SystemFunctions.minusOne(qihao.substring(0, 2));
				year = SystemFunctions.polishing(year,
						SystemConstants.ZERO_STRING, 2 - year.length(), 0);
				String startQihao = year
						+ SysConfig.getInstance().getLastSfcCount();
				int end = i - yearleft;
				for (int j = i; j < end; j++) {
					result[j] = startQihao;
					startQihao = SystemFunctions.minusOne(startQihao);
				}
			}
		} else if (type == 2) {
			int thisMonth = Integer.parseInt(qihao.substring(4));
			int monthleft = thisMonth - maxQihaoCount;
			if (monthleft >= 0) {
				for (int i = 1; i < maxQihaoCount; i++) {
					qihao = SystemFunctions.minusOne(qihao);
					result[i] = qihao;
				}
			} else {
				int i = 0;
				for (i = 1; i < thisMonth; i++) {
					qihao = SystemFunctions.minusOne(qihao);
					result[i] = qihao;
				}
				String year = qihao.substring(0, 2);
				String month = SystemFunctions.minusOne(qihao.substring(2, 4));
				month = SystemFunctions.polishing(month,
						SystemConstants.ZERO_STRING, 2 - month.length(), 0);
				// solve the qihao defect
				if (month.equals("00")) {
					year = SystemFunctions.minusOne(year);
					month = "12";
				}
				String startQihao = year + month
						+ SysConfig.getInstance().getLastBjdcCount();
				int end = i - monthleft;
				for (int j = i; j < end; j++) {
					result[j] = startQihao;
					startQihao = SystemFunctions.minusOne(startQihao);
				}
			}
		} else {
			for (int i = 1; i < maxQihaoCount; i++) {
				qihao = SystemFunctions.minusOneDay(qihao);
				result[i] = qihao;
			}
		}
		return result;
	}

	/**
	 * @方法说明:获取行数，包括时间栏行
	 * @参数: @param data
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static int getRowSize(TreeMap<String, List<BetMatch>> data) {
		int result = 0;
		if (data != null) {
			Set keySet = data.keySet();
			result += keySet.size();
			Iterator iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				List<BetMatch> matches = data.get(key);
				if (matches != null) {
					result += matches.size();
				}
			}
		}
		return result;
	}

	/**
	 * @方法说明: 列号转化为对应投注值
	 * @参数: @param colIndex
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String convertToBet(int colIndex) {
		if (colIndex == 7) {
			return SystemConstants.BET_WIN;
		}
		if (colIndex == 8) {
			return SystemConstants.BET_DRAW;
		}
		if (colIndex == 9) {
			return SystemConstants.BET_LOSS;
		}
		if (colIndex == 10) {
			return SystemConstants.BET_DAN;
		}
		return SystemConstants.BET_WDL;
	}

	/**
	 * @方法说明: 添加新的投注值，去重复
	 * @参数: @param origin
	 * @参数: @param add
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static String betCombine(String origin, String add) {
		String result = origin;
		int oriLen = origin.length();
		char ch = origin.charAt(oriLen - 1);
		int addLen = add.length();
		if (ch == SystemConstants.ASTERISK) {
			for (int i = 0; i < addLen; i++) {
				char ach = add.charAt(i);
				if (result.indexOf(ach) < 0) {
					result = result.substring(0, oriLen - 1) + ach + ch;
				}
			}
		} else {
			for (int i = 0; i < addLen; i++) {
				char ach = add.charAt(i);
				if (result.indexOf(ach) < 0) {
					result += ach;
				}
			}
		}
		return result;
	}

	/**
	 * @方法说明: 根据比赛场次得到可形成的串
	 * @参数: @param count
	 * @参数: @return
	 * @return List<String>
	 * @throws
	 */
	public static List<String> getChuanByMatchCount(int count) {
		List<String> result = new ArrayList<String>();
		String chuan = SystemConstants.EMPTY_STRING;
		if (count < 1) {
			return result;
		}
		// 最多14场
		if (count > 14) {
			count = 14;
		}
		result.add(SystemConstants.PASSWAY_SINGLE);
		for (int i = 2; i <= count; i++) {
			int ccnt = 0;
			for (int j = 0; j < i; j++) {
				ccnt += C(i, j);
				chuan = i + SystemConstants.CHUAN + ccnt;
				result.add(chuan);
			}
		}
		return result;
	}

	/**
	 * @方法说明: 是否为胆
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int isDan(String src) {
		int last = src.length() - 1;
		return (src.charAt(last) == SystemConstants.ASTERISK ? 1 : 0);
	}

	/**
	 * @方法说明: 文件名是否包含特殊字符检测
	 * @参数: @param name
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isLegalFileName(String name) {
		int len = RegexConstants.ILEGAL_FILE_REGEX.length();
		for (int i = 0; i < len; i++) {
			if (name.indexOf(RegexConstants.ILEGAL_FILE_REGEX.charAt(i)) >= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @方法说明: 复式拆分为单式 格式31-10-0-130
	 * @参数: @param mbet
	 * @参数: @return
	 * @return List<String>
	 * @throws
	 */
	public static List<String> multiSplit(String mbet) {
		List<String> result = new ArrayList<String>();
		String[] bets = mbet.split(SystemConstants.PART);
		int len = bets.length;
		int tlen = 0;
		int[] blens = new int[len];
		int dl = 1;
		for (int i = 0; i < len; i++) {
			tlen = bets[i].trim().length();
			blens[i] = tlen;
			dl *= tlen;
		}
		int[][] ret = new int[dl][len];
		generateARQP(ret, blens);
		for (int i = 0; i < dl; i++) {
			String tmp = SystemConstants.EMPTY_STRING;
			for (int j = 0; j < len; j++) {
				tmp += bets[j].charAt(ret[i][j]);
			}
			result.add(tmp);
		}
		return result;
	}

	/**
	 * @方法说明:全排列生成
	 * @参数: @param ret
	 * @参数: @param src
	 * @return void
	 * @throws
	 */
	private static void generateARQP(int[][] ret, int[] src) {
		int jlen = src.length;
		int[] assArr = new int[jlen];
		int wrtpos = jlen - 1;
		int rIndx = 0;
		while (assArr[0] < src[0]) {
			if (assArr[wrtpos] < src[wrtpos]) {
				ret[rIndx] = assArr.clone();
				rIndx++;
				assArr[wrtpos]++;
			} else {
				assArr[wrtpos] = 0;
				wrtpos--;
				assArr[wrtpos]++;
				while (assArr[wrtpos] >= src[wrtpos]) {
					if (wrtpos == 0) {
						break;
					}
					assArr[wrtpos] = 0;
					wrtpos--;
					assArr[wrtpos]++;
				}
				wrtpos = jlen - 1;
			}
		}
	}

	/**
	 * @方法说明:生成文件数据字串
	 * @参数: @param src
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static String toFileString(Map<String, List<String>> src) {
		String result = SystemConstants.EMPTY_STRING;
		if (src != null) {
			Iterator it = src.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				List<String> value = src.get(key);
				if (value != null && value.size() > 0) {
					for (String val : value) {
						result += key + SystemConstants.EQUAL + val
								+ SystemConstants.WRITE_LINE;
					}
				}
			}
		}
		return result;
	}

	/**
	 * @方法说明:区域判断
	 * @参数: @param strLine
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isSection(String strLine) {
		if (strLine == null || strLine.length() == 0) {
			return false;
		}
		return (strLine.charAt(0) == SystemConstants.LEFT_BRACKET);
	}

	/**
	 * @方法说明: 获取个数
	 * @参数: @param string
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getCount(String src, char dest) {
		int result = 0;
		int len = src.length();
		for (int i = 0; i < len; i++) {
			if (src.charAt(i) == dest) {
				result++;
			}
		}
		return result;
	}

	/**
	 * @方法说明: 计算积分数
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getScoCount(String src) {
		String str = src.trim();
		int sco = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			sco += (str.charAt(i) - SystemConstants.ZERO);
		}
		return sco;
	}

	/**
	 * @方法说明:计算断点数
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getBreakCount(String src) {
		int bk = 0;
		int len = src.length();
		for (int i = 1; i < len; i++) {
			if (src.charAt(i) != src.charAt(i - 1)) {
				bk++;
			}
		}
		return bk;
	}

	/**
	 * @方法说明: 计算连场数,投注结果中相邻两个号码相同就是一个连号,如：33、11、00
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getContinueCount(String src) {
		int con = 0;
		int len = src.length();
		for (int i = 1; i < len; i++) {
			if (src.charAt(i) == src.charAt(i - 1)) {
				con++;
			}
		}
		return con;
	}

	/**
	 * @方法说明: 计算最长连场数
	 * @参数: @param src 原串
	 * @参数: @param dest 目标字符
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getMxContinueCount(String src, char dest) {
		int mxCon = 0;
		int curCon = 0;
		int len = src.length();
		for (int i = 0; i < len; i++) {
			if (src.charAt(i) == dest) {
				curCon++;
			} else {
				if (curCon > mxCon) {
					mxCon = curCon;
				}
				curCon = 0;
			}
		}
		// 最后串
		if (curCon > mxCon) {
			mxCon = curCon;
		}
		return mxCon;
	}

	/**
	 * @方法说明: 单注中comb1和comb2任意组合在一起的最大连续场次数（在该连续场次中必须包含comb1和comb2）
	 * @参数: @param src
	 * @参数: @param comb1
	 * @参数: @param comb2
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getCombConCount(String src, char comb1, char comb2) {
		int mxCon = 0;
		int curCon = 0;
		int len = src.length();
		boolean flag1 = false;
		boolean flag2 = false;
		for (int i = 0; i < len; i++) {
			if (src.charAt(i) == comb1) {
				curCon++;
				flag1 = true;
			} else if (src.charAt(i) == comb2) {
				curCon++;
				flag2 = true;
			} else {
				// 是否包含comb1和comb2
				if (flag1 && flag2 && curCon > mxCon) {
					mxCon = curCon;
				}
				flag1 = false;
				flag2 = false;
				curCon = 0;
			}
		}
		// 最后串
		if (flag1 && flag2 && curCon > mxCon) {
			mxCon = curCon;
		}
		return mxCon;
	}

	/**
	 * @方法说明:邻号间距和过滤,一个投注结果中,相邻两个号码的号码间距之和
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getDistanceSum(String src) {
		int distance = 0;
		int len = src.length();
		int dis = 0;
		for (int i = 1; i < len; i++) {
			dis = src.charAt(i) - src.charAt(i - 1);
			if (dis < 0) {
				dis = -dis;
			}
			distance += dis;
		}
		return distance;
	}

	/**
	 * @方法说明: 邻号乘积和过滤,相邻两个号码的乘积之和
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getNbrCrsSum(String src) {
		int crsSum = 0;
		int len = src.length();
		int crs = 0;
		for (int i = 1; i < len; i++) {
			crs = (src.charAt(i) - '0') * (src.charAt(i - 1) - '0');
			crsSum += crs;
		}
		return crsSum;
	}

	/**
	 * @方法说明: 号码位积和过滤,比赛的3、1、0结果与其序号的乘积之和
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getPosCrsSum(String src) {
		int posCrsSum = 0;
		int len = src.length();
		int crs = 0;
		for (int i = 0; i < len; i++) {
			crs = (src.charAt(i) - '0') * (i + 1);
			posCrsSum += crs;
		}
		return posCrsSum;
	}

	/**
	 * @方法说明:
	 * @参数: @param odds
	 * @参数: @param winOdds
	 * @参数: @param drawOdds
	 * @参数: @param lossOdds
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public static char getOddPos(int odds, double winOdds, double drawOdds,
			double lossOdds) {
		char result = SystemConstants.ASTERISK;
		if (odds == 1) {
			if (winOdds - drawOdds < 0.0001) {
				if (winOdds - lossOdds < 0.0001) {
					result = SystemConstants.THREE;
				} else {
					result = SystemConstants.ZERO;
				}
			} else {
				if (drawOdds - lossOdds < 0.0001) {
					result = SystemConstants.ONE;
				} else {
					result = SystemConstants.ZERO;
				}
			}
		} else if (odds == 2) {
			if (winOdds - drawOdds < 0.0001) {
				if (winOdds - lossOdds < 0.0001) {
					if (drawOdds - lossOdds < 0.0001) {
						result = SystemConstants.ONE;
					} else {
						result = SystemConstants.ZERO;
					}
				} else {
					result = SystemConstants.THREE;
				}
			} else {
				if (drawOdds - lossOdds < 0.0001) {
					if (winOdds - lossOdds < 0.0001) {
						result = SystemConstants.THREE;
					} else {
						result = SystemConstants.ZERO;
					}
				} else {
					result = SystemConstants.ONE;
				}
			}
		} else if (odds == 3) {
			if (winOdds - drawOdds > 0.0001) {
				if (winOdds - lossOdds > 0.0001) {
					result = SystemConstants.THREE;
				} else {
					result = SystemConstants.ZERO;
				}
			} else {
				if (drawOdds - lossOdds > 0.0001) {
					result = SystemConstants.ONE;
				} else {
					result = SystemConstants.ZERO;
				}
			}
		}
		return result;
	}

	/**
	 * @方法说明: 获取匹配字符个数
	 * @参数: @param fisrtBet
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public static int getMatchingCount(String src, String bet) {
		int len = src.length();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if (src.charAt(i) == bet.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @方法说明: 获取倒数和
	 * @参数: @param datas
	 * @参数: @return
	 * @return double
	 * @throws
	 */
	public static double getRevSum(double... datas) {
		double result = 0.0;
		if (datas == null || datas.length == 0) {
			return result;
		}
		for (double data : datas) {
			result += (1.0 / data);
		}
		return result;
	}

	/**
	 * @方法说明: 字串连接
	 * @参数: @param buf1
	 * @参数: @param buf2
	 * @参数: @return
	 * @return byte[]
	 * @throws
	 */
	public static byte[] bytecat(byte[] buf1, byte[] buf2) {
		byte[] bufret = null;
		int len1 = 0;
		int len2 = 0;
		if (buf1 != null) {
			len1 = buf1.length;
		}
		if (buf2 != null) {
			len2 = buf2.length;
		}
		if (len1 + len2 > 0) {
			bufret = new byte[len1 + len2];
		}
		if (len1 > 0) {
			System.arraycopy(buf1, 0, bufret, 0, len1);
		}
		if (len2 > 0) {
			System.arraycopy(buf2, 0, bufret, len1, len2);
		}
		return bufret;
	}

	/**
	 * @方法说明: set to array
	 * @参数: @param mids
	 * @参数: @return
	 * @return String[]
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static String[] setToArray(Set<String> mids) {
		String[] array = new String[mids.size()];
		Iterator it = mids.iterator();
		int i = 0;
		while (it.hasNext()) {
			array[i] = (String) it.next();
			i++;
		}
		return array;
	}
}
