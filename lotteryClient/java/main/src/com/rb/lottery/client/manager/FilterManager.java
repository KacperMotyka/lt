/**
 * @文件名称: FilterManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-25 下午01:47:10
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.domain.FilterCriterion;
import com.rb.lottery.domain.FilterType;
import com.rb.threadpool.Task;
import com.rb.threadpool.ThreadPool;

/**
 * @类功能说明: 过滤处理
 * @类修改者: robin
 * @修改日期: 2011-10-31
 * @修改说明: 单例模式
 * @作者: robin
 * @创建时间: 2011-10-25 下午01:47:10
 * @版本: 1.0.0
 */

public class FilterManager {

	private static final Logger log = Logger.getLogger(FilterManager.class);

	private static FilterManager filterManager = null;

	List<String> prevCache = null;
	List<String> currentCache = new ArrayList<String>();

	private FilterManager() {
	}

	public static FilterManager getInstance() {
		if (filterManager == null) {
			filterManager = new FilterManager();
		}
		return filterManager;
	}

	/**
	 * @方法说明: 14胜负过滤
	 * @参数: @param betFile
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int do14Filter(File betFile) {
		try {
			String srcName = betFile.getName();
			String retName = "dist\\"
					+ srcName.substring(0, srcName.lastIndexOf('.'))
					+ "_result.txt";
			File resultFile = new File(retName);
			IOManager.getInstance().FileCopy(betFile, resultFile);

			initCache(currentCache, betFile);

			Scanner in = new Scanner(System.in);
			String command = "";
			int paramSize = 0;
			boolean status = false;
			System.out.print(">");
			while ((command = in.nextLine()) != null) {
				command = command.trim();
				if (command.equals("")) {
					System.out.print(">");
					continue;
				}
				command = command.toUpperCase();
				String[] cmds = command.split(" ");
				if (cmds[0].equalsIgnoreCase(SystemConstants.EXIT)) {
					System.out.print("确认退出系统(Y/N)?>");
					command = in.next();
					if (command.trim().equalsIgnoreCase("y")) {
						System.out.print("退出系统前保存数据(Y/N)?>");
						command = in.next();
						if (command.trim().equalsIgnoreCase("y")) {
							status = doSave(currentCache, resultFile);
							System.out.println("保存数据"
									+ (status ? SystemConstants.SUCCESS
											: SystemConstants.FAIL) + ".");
						}
					}
					System.out.println("退出系统.");
					break;
				}
				if (cmds[0].equalsIgnoreCase(SystemConstants.SAVE)) {
					System.out.print("保存当前更改(Y/N)?>");
					command = in.next();
					if (command.trim().equalsIgnoreCase("y")) {
						status = doSave(currentCache, resultFile);
						System.out.println("保存数据"
								+ (status ? SystemConstants.SUCCESS
										: SystemConstants.FAIL));
					}
					System.out.print(">");
					continue;
				}
				if (cmds[0].equalsIgnoreCase(SystemConstants.RESET)) {
					System.out.print("确认重置(Y/N)?>");
					command = in.next();
					if (command.trim().equalsIgnoreCase("y")) {
						prevCache = null;
						status = initCache(currentCache, betFile);
						System.out.println("重置"
								+ (status ? SystemConstants.SUCCESS
										: SystemConstants.FAIL) + ".");
					}
					System.out.print(">");
					continue;
				}
				if (cmds[0].equalsIgnoreCase(SystemConstants.ROLLBACK)) {
					System.out.print("确认回滚(Y/N)?>");
					command = in.next();
					if (command.trim().equalsIgnoreCase("y")) {
						status = rollback();
						System.out.println("回滚"
								+ (status ? SystemConstants.SUCCESS
										: (SystemConstants.FAIL + ",无法再次回滚!")));
					}
					System.out.print(">");
					continue;
				}
				if (cmds[0].equalsIgnoreCase(SystemConstants.DISPLAY)) {
					paramSize = cmds.length - 1;
					if (paramSize == 0) {
						displayAll(currentCache);
					} else {
						// TODO 显示其他方式
					}
					System.out.print(">");
					continue;
				}
				if (cmds[0].equalsIgnoreCase(SystemConstants.HELP)) {
					paramSize = cmds.length - 1;
					if (paramSize == 0) {
						displayHelp();
					} else {
						displayHelp(cmds[1]);
					}
					// TODO 其他显示方式
					System.out.print(">");
					continue;
				}
				if (cmds[0].equalsIgnoreCase(SystemConstants.DELETE)) {
					paramSize = cmds.length - 1;
					if (paramSize == 0) {
						System.out.println("命令语法不正确!");
					} else {
						int checkresult = 0;
						for (int i = 1; i < cmds.length; i++) {
							if (doDeleteCheck(cmds[i]) == -1) {
								checkresult = -1;
								break;
							}
						}
						if (checkresult != -1) {
							// 保存当前Cache保存到临时Cache,
							// 当操作成功则将临时Cache的值赋予回滚Cache,但操作失败则将临时Cache赋予当前Cache
							List tmpCache = new ArrayList();
							copyCache(currentCache, tmpCache);
							status = true;
							for (int i = 1; i < cmds.length; i++) {
								char operator = cmds[i].charAt(0);
								checkresult = doDeleteCheck(cmds[i]);
								int condition = checkresult % 10;
								int xNumber = checkresult / 10;
								status = status
										&& doSelect(currentCache, operator,
												condition, xNumber);
								if (status == false) // 过滤失败
								{
									copyCache(tmpCache, currentCache);
									System.out.println("过滤失败，已回滚至过滤前!");
									break;
								}
							}
							if (prevCache == null) {
								prevCache = new ArrayList();
							}
							copyCache(tmpCache, prevCache);
							System.out.println("过滤成功!");
							System.out.print(">");
							continue;
						} else {
							System.out.println("命令语法不正确!");
						}
					}
					System.out.print(">");
					continue;
				}
				System.out.println("命令不存在!");
				System.out.print(">");
			}
		} catch (Exception e) {
			System.out.println("文件" + betFile.getName() + "过滤失败.");
			e.printStackTrace();
		}
		return currentCache.size();
	}

	/**
	 * @方法说明: 高级上传分析
	 * @参数: @param file
	 * @参数: @return
	 * @return int 注数
	 * @throws
	 */
	public int doAnalysisUpload(File uploadFile) {
		int betNum = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					uploadFile));
			String line = "";
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals("")) {
					continue;
				}
				if (calcUploadBetNum(line) > 0) {
					betNum += calcUploadBetNum(line);
				} else {
					return -1;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return betNum;
	}

	/**
	 * @方法说明: 检查高级上传的每个投注
	 * @参数: @param line
	 * @参数: @return
	 * @return int (-1 非法， >0 注数)
	 * @throws
	 */
	private int calcUploadBetNum(String line) {
		int ilLegal = -1;
		if (line == null) {
			return ilLegal;
		}
		line = line.trim();
		int len = line.length();
		int betNum = 0; // 注数
		int part1 = 0, part2 = 0;
		int n = 2, m = 1, multiple = 1; // n串m ，倍投
		int selNum = 0;
		String integer_Regex = "[0-9]+";
		// String pattern_Regex = "[2-8]\\*[0-9]+[:|_]{1}[0-9]+";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		part1 = line.indexOf("|");
		if (part1 < 0) {
			return ilLegal;
		}
		// 抬头
		String title = line.substring(0, part1);
		if (part1 + 1 == len) {
			return ilLegal;
		}
		line = line.substring(part1 + 1);
		part2 = line.lastIndexOf("|");
		if (part2 < 0) {
			return ilLegal;
		}
		// 选择的场次
		String select = line.substring(0, part2);
		if (part2 + 1 == len) {
			return ilLegal;
		}
		// 投注方式
		String pattern = line.substring(part2 + 1);
		title = title.trim();
		select = select.trim();
		pattern = pattern.trim();
		// do analysis after split the String
		if (!title.equals("SPF") && !title.equals("让球胜平负")) {
			return ilLegal;
		}

		String[] selects = select.split(",");
		selNum = selects.length;
		List<String> sels = new ArrayList<String>();
		for (String sel : selects) {
			sel = sel.trim();
			String[] ret = sel.split("=");
			if (ret.length != 2) {
				return ilLegal;
			}
			// ret[0]为投注的场次
			ret[0] = ret[0].trim();
			int len0 = ret[0].length();
			if (len0 != 9) {
				return ilLegal;
			}
			if (!ret[0].matches(integer_Regex)) {
				return ilLegal;
			}
			if (sels.contains(ret[0])) {
				return -2; // 存在重复场次
			}
			Date dt = null;
			try {
				dt = sdf.parse("20" + ret[0].substring(0, 6));
			} catch (ParseException e) {
				e.printStackTrace();
				return ilLegal;
			}
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, -1);
			date = calendar.getTime();
			if (dt.before(date)) {
				return ilLegal;
			}
			sels.add(ret[0]);
			// ret[1]为投注的结果
			ret[1] = ret[1].trim();
			if (!ret[1].equals("3") && !ret[1].equals("1")
					&& !ret[1].equals("0")) {
				return ilLegal;
			}
		}

		int plen = pattern.length();
		int bt = pattern.indexOf(":");
		if (bt < 0) {
			bt = pattern.indexOf("_");
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
		multiple = Integer.parseInt(mul);
		int bplen = betPattern.length();
		int chuan = betPattern.indexOf("*");
		if (chuan < 0) {
			return ilLegal;
		}
		if (chuan + 1 == bplen) {
			return ilLegal;
		}
		String ns = betPattern.substring(0, chuan);
		ns = ns.trim();
		String ms = betPattern.substring(chuan + 1);
		ms = ms.trim();
		String ns_Regex = "[2-8]{1}";
		String ms_Regex = "[0-9]+";
		if (!ns.matches(ns_Regex) || !ms.matches(ms_Regex)) {
			return ilLegal;
		}
		n = Integer.parseInt(ns);
		m = Integer.parseInt(ms);
		if (n > selNum) // 大于实际值
		{
			return ilLegal;
		}
		int chnum = SystemFunctions.doCheckBetPattern(n, m);
		if (chnum < 0) {
			return ilLegal;
		}
		int dend = n - chnum;
		for (int i = n; i > dend; i--) {
			betNum += SystemFunctions.C(selNum, i);
		}
		betNum *= multiple;
		return betNum;
	}

	/**
	 * @param currentCache
	 * @方法说明: 过滤操作
	 * @参数: @param operator
	 * @参数: @param condition
	 * @参数: @param xNumber
	 * @return void
	 * @throws
	 */
	private boolean doSelect(List<String> currentCache, char operator,
			int condition, int xNumber) {
		int index = 0;
		int actual = 0;
		try {
			switch (operator) {
			case 'A': // 3的个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '3');
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '3');
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '3');
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '3');
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '3');
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'B': // 1的个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '1');
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '1');
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '1');
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '1');
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '1');
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'C': // 0的个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '0');
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '0');
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '0');
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '0');
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getCount(currentCache
								.get(index), '0');
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'D': // 最长连续3的个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '3');
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '3');
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '3');
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '3');
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '3');
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'E': // 最长连续1的个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '1');
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '1');
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '1');
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '1');
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '1');
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'F': // 最长连续0的个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '0');
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '0');
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '0');
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '0');
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getMxContinueCount(
								currentCache.get(index), '0');
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'G': // 断点个数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getBreakCount(currentCache
								.get(index));
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getBreakCount(currentCache
								.get(index));
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getBreakCount(currentCache
								.get(index));
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getBreakCount(currentCache
								.get(index));
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getBreakCount(currentCache
								.get(index));
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			case 'H': // 积分数
				switch (condition) {
				case 0: // >
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getScoCount(currentCache
								.get(index));
						if (actual > xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 1: // =
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getScoCount(currentCache
								.get(index));
						if (actual == xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 2: // <
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getScoCount(currentCache
								.get(index));
						if (actual < xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 3: // >=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getScoCount(currentCache
								.get(index));
						if (actual >= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				case 4: // <=
					for (index = 0; index < currentCache.size(); index++) {
						actual = SystemFunctions.getScoCount(currentCache
								.get(index));
						if (actual <= xNumber) {
							currentCache.remove(index);
							index--;
						}
					}
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @方法说明: Cache拷贝
	 * @参数: @param currentCache
	 * @参数: @param prevCache
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void copyCache(List srcCache, List destCache) {
		destCache.clear();
		destCache.addAll(srcCache);
	}

	/**
	 * @方法说明: 检查Delete语法
	 * @参数: @param cmds
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	private int doDeleteCheck(String param) {
		int result = -1;
		int cds = 0;
		int len = 0;
		if (param.equals("")) {
			return result;
		}
		param = param.trim().toLowerCase();
		len = param.length();
		if (len < 3) {
			return result;
		}
		char condition = param.charAt(0);
		// unchecked
		if (condition < 'a' || condition > 'z') {
			return result;
		}
		char option = param.charAt(1);
		char exoption = param.charAt(2);
		String number = "";
		if (option == '>') {
			if (exoption == '=') {
				if (len == 3) {
					return result;
				}
				cds = 3;
				number = param.substring(3);
			} else {
				cds = 0;
				number = param.substring(2);
			}
		} else if (option == '<') {
			if (exoption == '=') {
				if (len == 3) {
					return result;
				}
				cds = 4;
				number = param.substring(3);
			} else {
				cds = 2;
				number = param.substring(2);
			}
		} else if (option == '=') {
			cds = 1;
			number = param.substring(2);
		} else {
			return result;
		}
		String regex = RegexConstants.INTEGER_REGEX;
		if (!number.matches(regex)) {
			return result;
		} else {
			// 个位保存操作符(>(0), =(1), <(2), >=(3), <=(4)), 十位以上保存数字
			result = Integer.valueOf(number) * 10 + cds;
		}
		return result;
	}

	/**
	 * @方法说明: 显示帮助信息
	 * @参数: String
	 * @return void
	 * @throws
	 */
	private void displayHelp(String... command) {
		if (command == null || command.length == 0) {
			System.out.println("命令列表:");
			System.out.println(SystemConstants.COMMANDLIST);
			System.out.println("");
		} else {
			String param = command[0];
			System.out.println("命令【" + param + "】格式:");
			System.out.println("待完善...");
			// TODO
		}
	}

	/**
	 * @方法说明: 显示全部帮助信息
	 * @参数: @param currentCache
	 * @return void
	 * @throws
	 */
	private void displayAll(List<String> cache) {
		System.out.print("当前投注总注数: ");
		if (cache == null) {
			System.out.println("0");
			return;
		}
		System.out.println(cache.size());
		for (String bet : cache) {
			System.out.println(bet);
		}
		System.out.println("");
	}

	/**
	 * @方法说明: 回滚
	 * @参数: @param currentCache
	 * @参数: @param prevCache
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	private boolean rollback() {
		try {
			if (prevCache == null) {
				return false;
			}
			currentCache.clear();
			for (String prev : prevCache) {
				currentCache.add(prev);
			}
			prevCache.clear();
			prevCache = null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @方法说明: 保存
	 * @参数: @param currentCache
	 * @参数: @param resultFile
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	private boolean doSave(List<String> cache, File destFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
			for (String bet : cache) {
				writer.write(bet + SystemConstants.WRITE_LINE);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("文件" + destFile.getName() + "写入失败.");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("文件" + destFile.getName() + "写入失败.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @方法说明: 初始化缓存
	 * @参数: @param currentCache
	 * @参数: @param betFile
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private boolean initCache(List cache, File srcFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(srcFile));
			String line = "";
			if (cache == null) {
				cache = new ArrayList();
			} else {
				cache.clear();
			}
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (!line.equals("")) {
					if (isLegal(line)) {
						cache.add(line);
					} else {
						System.out.println("文件【" + srcFile.getName() + "】非法!");
						reader.close();
						return false;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("文件" + srcFile.getName() + "读取失败.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件" + srcFile.getName() + "读取失败.");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @方法说明: 投注是否合法
	 * @参数: @param line
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean isLegal(String bet) {
		if (bet == null) {
			return false;
		}
		String regex = "[3|1|0]{14}";
		return bet.matches(regex);
	}

	/**
	 * @方法说明: 选择小复式
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void selectSmlMulti(boolean flag) {
		MainFrame.getInstance().setBNLEnable(flag);
	}

	/**
	 * @方法说明: 执行过滤
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void doFilter() {
		log.info("开始过滤...");
		try {
			ThreadPool pool = ThreadPool.getInstance();
			FilterCriterion fc = null;
			MainFrame mf = MainFrame.getInstance();
			Task task;
			if (mf.isWinFilter()) {
				fc = new FilterCriterion(FilterType.胜场数, mf.getLeastWin(), mf
						.getMostWin(), mf.isWinFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isDrawFilter()) {
				fc = new FilterCriterion(FilterType.平场数, mf.getLeastDraw(), mf
						.getMostDraw(), mf.isDrawFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isLossFilter()) {
				fc = new FilterCriterion(FilterType.负场数, mf.getLeastLoss(), mf
						.getMostLoss(), mf.isLossFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isScoreFilter()) {
				fc = new FilterCriterion(FilterType.积分和, mf.getLeastScore(), mf
						.getMostScore(), mf.isScoreFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isBreakFilter()) {
				fc = new FilterCriterion(FilterType.断点数, mf.getLeastBreak(), mf
						.getMostBreak(), mf.isBreakFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isContinueFilter()) {
				fc = new FilterCriterion(FilterType.连号个数,
						mf.getLeastContinue(), mf.getMostContinue(), mf
								.isContinueFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isWinConFilter()) {
				fc = new FilterCriterion(FilterType.主场连胜, mf.getLeastWinCon(),
						mf.getMostWinCon(), mf.isWinConFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isDrawConFilter()) {
				fc = new FilterCriterion(FilterType.主场连平, mf.getLeastDrawCon(),
						mf.getMostDrawCon(), mf.isDrawConFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isLossConFilter()) {
				fc = new FilterCriterion(FilterType.主场连负, mf.getLeastLossCon(),
						mf.getMostLossCon(), mf.isLossConFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isWinDrawFilter()) {
				fc = new FilterCriterion(FilterType.胜平连号, mf.getLeastWinDraw(),
						mf.getMostWinDraw(), mf.isWinDrawFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isWinLossFilter()) {
				fc = new FilterCriterion(FilterType.胜负连号, mf.getLeastWinLoss(),
						mf.getMostWinLoss(), mf.isWinLossFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isDrawLossFilter()) {
				fc = new FilterCriterion(FilterType.平负连号,
						mf.getLeastDrawLoss(), mf.getMostDrawLoss(), mf
								.isDrawLossFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isDstSumFilter()) {
				fc = new FilterCriterion(FilterType.邻号间距和, mf.getLeastDstSum(),
						mf.getMostDstSum(), mf.isDstSumFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isNbrCrsFilter()) {
				fc = new FilterCriterion(FilterType.邻号乘积和, mf.getLeastNbrCrs(),
						mf.getMostNbrCrs(), mf.isNbrCrsFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isPosCrsFilter()) {
				fc = new FilterCriterion(FilterType.号码位积和, mf.getLeastPosCrs(),
						mf.getMostPosCrs(), mf.isPosCrsFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isFirstBetFilter()) {
				fc = new FilterCriterion(FilterType.第一赔率,
						mf.getLeastFirstBet(), mf.getMostFirstBet(), mf
								.isFirstBetFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isSecondBetFilter()) {
				fc = new FilterCriterion(FilterType.第二赔率, mf
						.getLeastSecondBet(), mf.getMostSecondBet(), mf
						.isSecondBetFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isThirdBetFilter()) {
				fc = new FilterCriterion(FilterType.第三赔率,
						mf.getLeastThirdBet(), mf.getMostThirdBet(), mf
								.isThirdBetFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isRangeRankFilter()) {
				fc = new FilterCriterion(FilterType.区间排名, mf
						.getLeastRangeRank(), mf.getMostRangeRank(), mf
						.isRangeRankFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isProbCrsFilter()) {
				fc = new FilterCriterion(FilterType.概率积, mf.getLeastProbCrs(),
						mf.getMostProbCrs(), mf.isProbCrsFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isExpectFilter()) {
				fc = new FilterCriterion(FilterType.值博率, mf.getLeastExpect(),
						mf.getMostExpect(), mf.isExpectFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isProbSumFilter()) {
				fc = new FilterCriterion(FilterType.概率和, mf.getLeastProbSum(),
						mf.getMostProbSum(), mf.isProbSumFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isOddsSumFilter()) {
				fc = new FilterCriterion(FilterType.赔率和, mf.getLeastOddsSum(),
						mf.getMostOddsSum(), mf.isOddsSumFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isOddsCrsFilter()) {
				fc = new FilterCriterion(FilterType.赔率积, mf.getLeastOddsCrs(),
						mf.getMostOddsCrs(), mf.isOddsCrsFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			if (mf.isRewardFilter()) {
				fc = new FilterCriterion(FilterType.奖金指数, mf.getLeastReward(),
						mf.getMostReward(), mf.isRewardFt());
				task = fc.generateFilterTask();
				if (pool.isSystemBusy()) {
					pool.addWorker();
				}
				pool.addTask(task);
			}
			boolean isWorking = pool.isWorking();
			while (isWorking) {
				Thread.sleep(500);
				log.debug(pool.getInfo());
				isWorking = pool.isWorking();
			}
			log.info("过滤结束.");
			mf.displaySingleBet(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
