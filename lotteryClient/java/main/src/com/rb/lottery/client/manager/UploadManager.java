/**
 * @文件名称: UploadManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-8 下午02:24:57
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetForm;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.user.User;

/**
 * @类功能说明: 方案上传处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-8 下午02:24:57
 * @版本: 1.0.0
 */

public class UploadManager {

	private static final Logger log = Logger.getLogger(UploadManager.class);

	private static final String SFC14_FS_REGEX = "(3|1|0|31|13|30|03|10|01|310|301|130|103|031|013|*)";

	private static final int SECTION_LEN = 5;
	private static final String SFC14_FS = "SF14F";
	private static final String SFC14_DS = "SF14D";
	private static final String SFCR9_FS = "SFR9F";
	private static final String SFCR9_DS = "SFR9D";
	private static final String BJDC_FS = "BJDCF";
	private static final String BJDC_DS = "BJDCD";
	private static final String JCZQ_FS = "JCZQF";
	private static final String JCZQ_DS = "JCZQD";

	private static UploadManager uploadManager = null;

	public static UploadManager getInstance() {
		if (uploadManager == null) {
			uploadManager = new UploadManager();
		}
		return uploadManager;
	}

	private UploadManager() {

	}

	private void invalid() {
		JOptionPane.showMessageDialog(null, MessageConstants.INVALID_UPLOAD);
	}

	/**
	 * @方法说明: 上传投注方案
	 * @参数: @param fileName
	 * @return void
	 * @throws
	 */
	public void doUploadBet(String fileName) {
		log.info("检查上传投注方案: " + fileName);
		File upload = new File(fileName);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(upload),
					EncodingConstants.UTF8_ENCODING));
			Lottery lt = null;
			String line = null;
			String temp = null;
			int type = -1;
			int fd = 0;
			int len;
			int count;
			int section = -1;
			int multi = 1;
			long allbets = 0;
			List<BetForm> bfs = new ArrayList<BetForm>();
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals("")) {
					continue;
				}
				int theSection = checkSection(line);
				if (-1 == section) {
					if (-1 == theSection) {
						continue;
					} else {
						section = theSection;
						lt = new Lottery();
						type = section / 10;
						lt.setType(type);
						fd = section % 10;
						if (type < 3) {
							String[] qh = line.split("|");
							if (qh.length != 2) {
								invalid();
								return;
							}
							lt.setQihao(qh[1].trim());
						}

					}
				} else {
					if (-1 == theSection) {
						if (0 == fd) { // 复式，有倍投
							lt.setSingle(false);
							String[] mul = line.split(":");
							if (mul.length == 1) {
								multi = 1;
							} else if (mul.length == 2) {
								mul[1] = mul[1].trim();
								if (!mul[1]
										.matches(RegexConstants.POS_INTEGER_REGEX)) {
									invalid();
									return;
								}
								multi = Integer.parseInt(mul[1]);
							} else {
								invalid();
								return;
							}
							lt.setMulti(multi);
							if (0 == type) {
								String[] sfc14 = line.split(",");
								len = sfc14.length;
								count = 0;
								for (int i = 0; i < len; i++) {
									sfc14[i] = sfc14[i].trim();
									if (!sfc14[i].matches(SFC14_FS_REGEX)) {
										invalid();
										return;
									}
									temp = (i + 1) + "";
									temp = SystemFunctions.polishing(temp, "0",
											3 - temp.length(), 0);
									if (sfc14[i].equals("*")) {
										sfc14[i] = "310";
									}
									lt.addMulMatch(temp, sfc14[i]);
									count++;
								}
								if (14 != count) {
									invalid();
									return;
								}
								List<String> passway = new ArrayList<String>();
								passway.add("14串1");
								lt.setPassway(passway);
								boolean isValid = SystemProcessor
										.isValidBet(lt);
								if (!isValid) {
									invalid();
									return;
								}
								BetForm bf = generateBetForm(lt);

							} else if (1 == type) {

							} else if (2 == type) {

							} else if (3 == type) {

							}
						} else { // 单式,无倍投
							lt.setMulti(1);
						}
					} else if (section != theSection) {
						invalid();
						return;
					}
				}
			}

			BetBasket basket = new BetBasket();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 生成交易订单
	 * @参数: @param lt
	 * @参数: @return
	 * @return BetForm
	 * @throws
	 */
	private BetForm generateBetForm(Lottery lt) {
		User user = SystemCache.currentUser;
		BetBasket basket = BetBasket.generateBets(lt);
		BetForm betInfo = new BetForm(); // 投注订单
		String uuid = UUID.randomUUID().toString();
		betInfo.setBid(uuid);
		betInfo.setUser(user);
		betInfo.setBtime(new Date());
		betInfo.setLtType(Long.valueOf(lt.getType()));
		boolean isSingle = lt.isSingle();
		if (isSingle) {
			betInfo.setBtType((long) 20); // 单式上传
		} else {
			betInfo.setBtType((long) 21); // 复式上传
		}
		betInfo.setUpload(Long.valueOf(1));
		betInfo.setQihao(lt.getQihao());
		betInfo.setMids(lt.getLotteryMatchIds());
		if (isSingle) {

		} else {
			betInfo.setNumbers(lt.getLotteryNumbers());
		}

		int betCount = basket.getSingleBetCount();
		int mul = lt.getMulti();
		long money = SystemEnvironment.getInstance().money_per_bet * betCount
				* mul;
		betInfo.setBets(Long.valueOf(betCount));
		betInfo.setMulti(Long.valueOf(mul));
		betInfo.setMoney(money);
		betInfo.setPattern(lt.getPassway().toString());
		return betInfo;
	}

	/**
	 * @方法说明: 检查区间
	 * @参数: @param line
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	private int checkSection(String line) {
		int len = line.length();
		if (len < SECTION_LEN) {
			return -1;
		}
		String sec = line.substring(0, SECTION_LEN);
		if (sec.equalsIgnoreCase(SFC14_FS)) {
			return 0;
		}
		if (sec.equalsIgnoreCase(SFC14_DS)) {
			return 1;
		}
		if (sec.equalsIgnoreCase(SFCR9_FS)) {
			return 10;
		}
		if (sec.equalsIgnoreCase(SFCR9_DS)) {
			return 11;
		}
		if (sec.equalsIgnoreCase(BJDC_FS)) {
			return 20;
		}
		if (sec.equalsIgnoreCase(BJDC_DS)) {
			return 21;
		}
		if (sec.equalsIgnoreCase(JCZQ_FS)) {
			return 31;
		}
		if (sec.equalsIgnoreCase(JCZQ_DS)) {
			return 32;
		}
		return -1;
	}
}
