/**
 * @文件名称: BetBasket.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-14 下午04:14:40
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.swing.JOptionPane;

import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.common.SystemFunctions;

/**
 * @类功能说明: 号码篮
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-14 下午04:14:40
 * @版本: 1.0.0
 */

public class BetBasket implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 1437193995593957523L;

	private String id;
	private String name;
	private int type;
	private String qihao;
	private int matchCount;
	private Map<String, List<String>> multiBets; // 有重复注
	private Map<String, List<String>> singleBets; // 有重复注

	public BetBasket() {
		id = UUID.randomUUID().toString();
		name = SystemConstants.DEFAULT_BASCKET.trim();
		type = -1;
		qihao = SystemConstants.EMPTY_STRING;
		matchCount = 0;
		multiBets = new TreeMap<String, List<String>>();
		singleBets = new TreeMap<String, List<String>>();
	}

	/**
	 * @类名: BetBasket.java
	 * @描述: TODO
	 * @param type2
	 * @param qihao2
	 */
	public BetBasket(int type, String qihao) {
		id = UUID.randomUUID().toString();
		name = SystemConstants.DEFAULT_BASCKET.trim();
		this.type = type;
		this.qihao = qihao;
		matchCount = 0;
		multiBets = new TreeMap<String, List<String>>();
		singleBets = new TreeMap<String, List<String>>();
	}

	/**
	 * @类名: BetBasket.java
	 * @描述: TODO
	 * @param id
	 * @param name
	 * @param betCount
	 * @param type
	 * @param qihao
	 * @param matchCount
	 * @param matchIds
	 * @param bets
	 */
	public BetBasket(String id, String name, int type, String qihao,
			int matchCount, String matchIds,
			Map<String, List<String>> multiBets,
			Map<String, List<String>> singleBets) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.qihao = qihao;
		this.matchCount = matchCount;
		this.multiBets = multiBets;
		this.singleBets = singleBets;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return matchCount
	 */
	public int getMatchCount() {
		initMatchCount();
		return matchCount;
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void initMatchCount() {
		Set<String> idSet = new HashSet<String>();
		Iterator it = multiBets.keySet().iterator();
		while (it.hasNext()) {
			String idStr = (String) it.next();
			String[] ids = idStr.split(SystemConstants.COMMA);
			for (String id : ids) {
				idSet.add(id);
			}
		}
		matchCount = idSet.size();
	}

	/**
	 * @param matchCount
	 *            matchCount
	 */
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	/**
	 * @return multiBet
	 */
	public Map<String, List<String>> getMultiBets() {
		return multiBets;
	}

	/**
	 * @param multiBet
	 *            multiBet
	 */
	public void setMultiBets(Map<String, List<String>> multiBets) {
		this.multiBets = multiBets;
	}

	/**
	 * @return bets
	 */
	public Map<String, List<String>> getSingleBets() {
		return singleBets;
	}

	/**
	 * @param bets
	 *            bets
	 */
	public void setSingleBets(Map<String, List<String>> singleBets) {
		this.singleBets = singleBets;
	}

	@SuppressWarnings("unchecked")
	public int getMultiBetCount() {
		Iterator it = multiBets.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String mid = (String) it.next();
			count += multiBets.get(mid).size();
		}
		return count;
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getSingleBetCount() {
		Iterator it = singleBets.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String mid = (String) it.next();
			count += singleBets.get(mid).size();
		}
		return count;
	}

	/**
	 * @方法说明:
	 * @参数: @param id
	 * @参数: @param choose
	 * @return void
	 * @throws
	 */
	public void addMultiBet(String id, String choose) {
		if (multiBets.containsKey(id)) {
			multiBets.get(id).add(choose);
		} else {
			List<String> tmp = new ArrayList<String>();
			tmp.add(choose);
			multiBets.put(id, tmp);
		}
	}

	/**
	 * @方法说明: 添加1注单式投注
	 * @参数: @param id
	 * @参数: @param choose
	 * @return void
	 * @throws
	 */
	public void addSingleBet(String id, String choose) {
		if (singleBets == null) {
			singleBets = new TreeMap<String, List<String>>();
		}
		if (singleBets.containsKey(id)) {
			singleBets.get(id).add(choose);
		} else {
			List<String> tmp = new ArrayList<String>();
			tmp.add(choose);
			singleBets.put(id, tmp);
		}
	}

	/**
	 * @方法说明: 批量添加单式投注
	 * @参数: @param sinMatches
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void addAllSingleBet(Map<String, List<String>> sinMatches) {
		if (singleBets == null) {
			singleBets = new TreeMap<String, List<String>>();
		}
		Iterator it = sinMatches.keySet().iterator();
		while (it.hasNext()) {
			String mid = (String) it.next();
			List<String> numbers = singleBets.get(mid);
			if (null == numbers) {
				numbers = new ArrayList<String>();
			}
			numbers.addAll(sinMatches.get(mid));
			singleBets.put(mid, numbers);
		}
	}

	/**
	 * @方法说明: 根据彩票信息生成单式和复式号码篮
	 * @参数: @param lt
	 * @return BetBasket
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static BetBasket generateBets(Lottery lt) {
		BetBasket basket = new BetBasket(lt.getType(), lt.getQihao());
		Map<String, String> mulMatches = lt.getMulMatches(); // 复式
		Map<String, List<String>> sinMatches = lt.getSinMatches(); // 单式
		basket.addAllSingleBet(sinMatches);
		// 过关方式
		List<String> passway = lt.getPassway();
		int matchCount = 0;
		// 复式投注结果
		Map<String, List<String>> multiBets = new TreeMap<String, List<String>>();
		// 单式投注结果
		Map<String, List<String>> singleBets = new TreeMap<String, List<String>>();
		// 胆比赛投注结果集
		Map<String, String> dan_bets = new TreeMap<String, String>();
		// 非胆比赛投注结果集
		Map<String, String> no_dan_bets = new TreeMap<String, String>();
		Set keySet = mulMatches.keySet();
		Iterator it = keySet.iterator();
		int dans = 0;
		int nodans = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = mulMatches.get(key);
			if (SystemFunctions.isDan(value) > 0) {
				dan_bets.put(key, value.substring(0, value.length() - 1));
				dans++;
			} else {
				no_dan_bets.put(key, value);
				nodans++;
			}
			matchCount++;
		}
		basket.setMatchCount(matchCount);
		if (matchCount == 0) {
			JOptionPane.showMessageDialog(null,
					MessageConstants.NO_MATCH_SELECT);
			return null;
		}
		Set dk = dan_bets.keySet();
		String[] danKey = new String[dk.size()];
		Iterator dkit = dk.iterator();
		int tmp = 0;
		while (dkit.hasNext()) {
			String key = (String) dkit.next();
			danKey[tmp] = key;
			tmp++;
		}
		Set ndk = no_dan_bets.keySet();
		String[] nodanKey = new String[ndk.size()];
		Iterator ndkit = ndk.iterator();
		tmp = 0;
		while (ndkit.hasNext()) {
			String key = (String) ndkit.next();
			nodanKey[tmp] = key;
			tmp++;
		}
		if (passway != null && passway.size() > 0) {
			for (String pasw : passway) {
				int n = 0;
				int m = 0;
				// 单关
				if (pasw.indexOf(SystemConstants.CHUAN) < 0) {
					n = 1;
					m = 1;
				} else {
					String[] n_m = pasw.split(SystemConstants.CHUAN);
					n = Integer.parseInt(n_m[0].trim());
					m = Integer.parseInt(n_m[1].trim());
				}
				int dcbp = SystemFunctions.doCheckBetPattern(n, m);
				for (int i = 0; i < dcbp; i++) {
					int mtc = n - i; // mtc串1
					// 大于实际透出场次=>无效
					if (mtc > matchCount) {
						continue;
					}
					// 比赛ID串
					String matchIds = SystemConstants.EMPTY_STRING;
					String mbet = SystemConstants.EMPTY_STRING;
					List<String> mbets = new ArrayList<String>();
					// 胆≥投注场次
					if (dans >= mtc) {
						int Cnum = SystemFunctions.C(dans, mtc);
						int[][] ret = new int[Cnum][mtc]; // 全排列结果
						SystemFunctions.generateQP(ret, dans, mtc);
						for (int j = 0; j < Cnum; j++) {
							matchIds = SystemConstants.EMPTY_STRING;
							mbet = SystemConstants.EMPTY_STRING;
							for (int k = 0; k < mtc; k++) {
								if (k == 0) {
									matchIds += danKey[ret[j][k]];
									mbet += dan_bets.get(danKey[ret[j][k]]);
								} else {
									matchIds += SystemConstants.COMMA
											+ danKey[ret[j][k]];
									mbet += SystemConstants.PART
											+ dan_bets.get(danKey[ret[j][k]]);
								}
							}
							if (multiBets.containsKey(matchIds)) {
								mbets = multiBets.get(matchIds);
							}
							mbets.add(mbet);
							multiBets.put(matchIds,
									new ArrayList<String>(mbets));
							mbets.clear();
						}
					} else { // 胆<投注场次
						// 胆全选
						for (int j = 0; j < dans; j++) {
							if (j == 0) {
								matchIds += danKey[j];
								mbet += dan_bets.get(danKey[j]);
							} else {
								matchIds += SystemConstants.COMMA + danKey[j];
								mbet += SystemConstants.PART
										+ dan_bets.get(danKey[j]);
							}
						}
						String tmpId = matchIds;
						String tmpBet = mbet;
						// 非胆剩余场次
						int ndl = mtc - dans;
						int Cnum = SystemFunctions.C(nodans, ndl);
						int[][] ret = new int[Cnum][ndl]; // 全排列结果
						SystemFunctions.generateQP(ret, nodans, ndl);
						for (int j = 0; j < Cnum; j++) {
							matchIds = tmpId;
							mbet = tmpBet;
							for (int k = 0; k < ndl; k++) {
								if (k == 0 && dans == 0) {
									matchIds += nodanKey[ret[j][k]];
									mbet += no_dan_bets
											.get(nodanKey[ret[j][k]]);
								} else {
									matchIds += SystemConstants.COMMA
											+ nodanKey[ret[j][k]];
									mbet += SystemConstants.PART
											+ no_dan_bets
													.get(nodanKey[ret[j][k]]);
								}
							}
							if (multiBets.containsKey(matchIds)) {
								mbets = multiBets.get(matchIds);
							}
							mbets.add(mbet);
							multiBets.put(matchIds,
									new ArrayList<String>(mbets));
							mbets.clear();
						}
					}

				}
			}
			Iterator mulit = multiBets.keySet().iterator();
			while (mulit.hasNext()) {
				String mid = (String) mulit.next();
				List<String> tmpList = multiBets.get(mid);
				for (String mbt : tmpList) {
					List<String> sbets = SystemFunctions.multiSplit(mbt);
					if (singleBets.containsKey(mid)) {
						sbets.addAll(singleBets.get(mid));
					}
					singleBets.put(mid, sbets);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, MessageConstants.NO_PASSWAY);
			return null;
		}
		basket.setMultiBets(multiBets);
		basket.setSingleBets(singleBets);
		return basket;
	}

}
