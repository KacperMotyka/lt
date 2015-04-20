/**
 * @文件名称: SystemProcessor.java
 * @类路径:   com.rb.lottery.system
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午01:42:16
 * @版本:     1.0.0
 */
package com.rb.lottery.system;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetForm;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.domain.SfcKj;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.server.common.FilePathConstants;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.common.SystemFunctions;
import com.rb.lottery.server.dao.BjdcDAO;
import com.rb.lottery.server.dao.JczqDAO;
import com.rb.lottery.server.dao.LogDAO;
import com.rb.lottery.server.dao.SfcDAO;
import com.rb.lottery.server.dao.SfcKjDAO;
import com.rb.lottery.server.dao.UserDAO;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.manager.SystemManager;
import com.rb.lottery.server.server.ServerThread;
import com.rb.lottery.server.timer.DateUpdateTimerTask;
import com.rb.lottery.user.User;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明: 系统处理器
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 下午01:42:16
 * @版本: 1.0.0
 */

public class SystemProcessor {

    private static final Logger log = Logger.getLogger(SystemProcessor.class);

    public static final LogDAO logDAO = new LogDAO();

    /**
     * @方法说明: 初始化日志配置
     * @参数:
     * @return void
     * @throws
     */
    public static void initLog4j() {
        // 初始化log4j
        PropertyConfigurator.configure(FilePathConstants.LOG4J_CONFIG_FILE);
    }

    /**
     * @方法说明: 系统初始化
     * @参数:
     * @return void
     * @throws
     */
    public static boolean initEnvironment(String version) {
        try {
            SystemManager sys = SystemManager.getInstance();
            // 初始化系统环境配置
            boolean initEnv = sys.initSysEnvironment(version);
            // 初始化业务环境配置
            File sysConfFile = new File(FilePathConstants.SYSTEM_CONFIG_FILE);
            boolean initConf = sys.initSysConfig(sysConfFile);
            if (initEnv && initConf) {
                log.info("系统/业务环境配置成功.");
            } else {
                log.info("系统/业务环境配置失败.");
                return false;
            }
            // 测试数据库连接
            boolean connected = sys.testDatabaseConnect();
            if (!connected) {
                return false;
            }
        } catch (LotteryException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @方法说明: 启动数据定时更新线程
     * @参数:
     * @return void
     * @throws
     */
    public static boolean startDateUpdateThread() {
        try {
            boolean autoUpdate = SysConfig.getInstance().isAutoUpdate();
            boolean autoKj = SysConfig.getInstance().isAutoKj();
            if (autoUpdate) {
                SystemCache.updateTimer = new Timer();
                DateUpdateTimerTask updateTask = new DateUpdateTimerTask();
                SystemCache.updateTimer.schedule(updateTask, SystemEnvironment.getInstance().delay_period,
                        SystemEnvironment.getInstance().update_period);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @方法说明: 停止数据定时更新线程
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static boolean stopUpdateTimer() {
        try {
            if (null != SystemCache.updateTimer) {
                SystemCache.updateTimer.cancel();
                SystemCache.updateTimer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @方法说明: 启动服务器端监听端口
     * @参数:
     * @return void
     * @throws
     */
    public static boolean startServerListener() {
        try {
            SystemCache.server = new ServerThread();
            SystemCache.server.start();
            // 启动服务后端口监听
            log.info("启动服务器监听端口[" + SystemCache.server.getPort() + "].");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @方法说明: 停止服务器端监听端口
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static boolean stopServerListener() {
        try {
            SystemCache.server.stopService();
            SystemCache.server.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        log.info("停止服务器监听端口[" + SystemCache.server.getPort() + "].");
        SystemCache.server = null;
        return true;
    }

    /**
     * @方法说明: 初始化投注比赛数据
     * @参数: @return
     * @return Map<String,List<BetMatch>>
     * @throws
     */
    public static void updateMatchData(Map<String, List<BetMatch>> map) {
        if (map != null) {
            SystemCache.initDataCache(map);
        }
    }

    /**
     * @方法说明: 初始化出票信息
     * @return void
     * @throws
     */
    public static void initLottery() {
        SystemCache.initLottery();
    }

    /**
     * @方法说明: 初始化出票信息
     * @参数: @param lottery
     * @return void
     * @throws
     */
    public static void initLottery(Lottery lottery) {
        SystemCache.initLottery(lottery);
    }

    /**
     * @方法说明: 初始化新的号码篮
     * @return void
     * @throws
     */
    public static void addBasket(BetBasket basket) {
        SystemCache.addBasket(basket);
    }

    /**
     * @方法说明: 初始化当前号码篮
     * @参数: @param lottery
     * @return void
     * @throws
     */
    public static void initBasket(BetBasket basket) {
        SystemCache.initBasket(basket);
    }

    /**
     * @param currentLotteryId
     * @方法说明:获取投注状态信息
     * @参数: @return
     * @return String
     * @throws
     */
    public static String getBetStatu() {
        String status = SystemCache.getBetStatu();
        return status;
    }

    /**
     * @方法说明: 更新投注状态信息
     * @参数:
     * @return void
     * @throws
     */
    public static void updateBetStatu() {

    }

    /**
     * @方法说明:
     * @参数: @param maxQihaoCount
     * @参数: @return
     * @return String[]
     * @throws
     */
    public static String[] generateQihaos(int maxQihaoCount) {
        return SystemFunctions.generateQihaos(SystemCache.currentType, SystemCache.currentQihao, maxQihaoCount);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return TreeMap<String,List<BetMatch>>
     * @throws
     */
    public static TreeMap<String, List<BetMatch>> getMatchData() {
        return SystemCache.getMatchData();
    }

    /**
     * @方法说明:
     * @参数: @param free
     * @return void
     * @throws
     */
    public static void setIdleMemory(String free) {
        SystemCache.free_memory = free;
        // MainFrame.getInstance().setIdleMemory();
    }

    /**
     * @方法说明:
     * @参数: @param id
     * @参数: @param convertToBet
     * @return void
     * @throws
     */
    public static void removeOneBet(String id, String bet) {
        SystemCache.removeOneBet(id, bet);
    }

    /**
     * @方法说明:
     * @参数: @param id
     * @参数: @param convertToBet
     * @return void
     * @throws
     */
    public static void addOneBet(String id, String bet) {
        SystemCache.addOneBet(id, bet);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return Object
     * @throws
     */
    public static Lottery getLottery(String lotteryId) {
        return SystemCache.getLottery(lotteryId);
    }

    /**
     * @方法说明:
     * @参数: @param passway
     * @return void
     * @throws
     */
    public static void setLotteryPassway(String lotteryId, List<String> passway) {
        SystemCache.setLotteryPassway(lotteryId, passway);
    }

    /**
     * @方法说明:
     * @参数: @param currentLotteryId
     * @参数: @return
     * @return String
     * @throws
     */
    public static String getLotteryMatchIds(String lotteryId) {
        return SystemCache.getLotteryMatchIds(lotteryId);
    }

    /**
     * @方法说明:
     * @参数: @param currentLotteryId
     * @参数: @return
     * @return int
     * @throws
     */
    public static int getLotteryMatchCount(String lotteryId) {
        return SystemCache.getLotteryMatchCount(lotteryId);
    }

    /**
     * @param basketId
     * @方法说明:
     * @参数: @param matchCount
     * @return void
     * @throws
     */
    public static void setBasketMatchCount(String basketId, int matchCount) {
        SystemCache.setBasketMatchCount(basketId, matchCount);
    }

    /**
     * @方法说明: 根据彩票信息生成单式和复式号码篮
     * @参数: @param lotteryId
     * @return BetBasket
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static BetBasket generateCurrentBets(String lotteryId) {
        BetBasket basket = new BetBasket(SystemCache.currentType, SystemCache.currentQihao);
        Lottery lt = SystemCache.getLottery(lotteryId);
        Map<String, String> matches = lt.getMulMatches(); // 复式
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
        Set keySet = matches.keySet();
        Iterator it = keySet.iterator();
        int dans = 0;
        int nodans = 0;
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = matches.get(key);
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
            JOptionPane.showMessageDialog(null, MessageConstants.NO_MATCH_SELECT);
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
                                    matchIds += SystemConstants.COMMA + danKey[ret[j][k]];
                                    mbet += SystemConstants.PART + dan_bets.get(danKey[ret[j][k]]);
                                }
                            }
                            if (multiBets.containsKey(matchIds)) {
                                mbets = multiBets.get(matchIds);
                            }
                            mbets.add(mbet);
                            multiBets.put(matchIds, new ArrayList<String>(mbets));
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
                                mbet += SystemConstants.PART + dan_bets.get(danKey[j]);
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
                                    mbet += no_dan_bets.get(nodanKey[ret[j][k]]);
                                } else {
                                    matchIds += SystemConstants.COMMA + nodanKey[ret[j][k]];
                                    mbet += SystemConstants.PART + no_dan_bets.get(nodanKey[ret[j][k]]);
                                }
                            }
                            if (multiBets.containsKey(matchIds)) {
                                mbets = multiBets.get(matchIds);
                            }
                            mbets.add(mbet);
                            multiBets.put(matchIds, new ArrayList<String>(mbets));
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

    /**
     * @方法说明:
     * @参数: @param basketId
     * @参数: @return
     * @return BetBasket
     * @throws
     */
    public static BetBasket getBasket(String basketId) {
        return SystemCache.getBasket(basketId);
    }

    /**
     * @方法说明: 生成指定赔率单式投注串
     * @参数: @param odds
     * @参数: @param matchIds
     * @参数: @return
     * @return String
     * @throws
     */
    public static String generateSpeOdds(int odds, String matchIds) {
        String speBet = SystemConstants.EMPTY_STRING;
        if (matchIds == null || matchIds.length() == 0) {
            return speBet;
        }
        String[] ids = matchIds.split(SystemConstants.COMMA);
        for (String id : ids) {
            BetMatch match = SystemCache.getMatchById(id);
            speBet += SystemFunctions.getOddPos(odds, match.getWinOdds(), match.getDrawOdds(), match.getLossOdds());
        }
        return speBet;
    }

    /**
     * @方法说明:获取区间排名
     * @参数: @param matchIds
     * @参数: @return
     * @return List<String>
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static List<String> generateRanks(String matchIds) {
        List<String> ranks = new ArrayList<String>();
        if (matchIds == null || matchIds.length() == 0) {
            return ranks;
        }
        String[] ids = matchIds.split(SystemConstants.COMMA);
        int len = ids.length;
        double[][] odds = new double[len][4];
        for (int i = 0; i < len; i++) {
            BetMatch match = SystemCache.getMatchById(ids[i]);
            double wod = match.getWinOdds();
            double dod = match.getDrawOdds();
            double lod = match.getLossOdds();
            double rvs = SystemFunctions.getRevSum(wod, dod, lod);
            odds[i][3] = 1.0 / wod / rvs;
            odds[i][1] = 1.0 / dod / rvs;
            odds[i][0] = 1.0 / lod / rvs;
        }
        String multi = SystemConstants.BET_WDL;
        multi = SystemFunctions.polishing(multi, SystemConstants.PART + SystemConstants.BET_WDL, len - 1, 1);
        List<String> singleBets = SystemFunctions.multiSplit(multi);
        Map<String, Double> map = new HashMap<String, Double>();
        if (singleBets != null && singleBets.size() > 0) {
            for (String bt : singleBets) {
                double dl = 1000000.0;
                int blen = bt.length();
                for (int i = 0; i < blen; i++) {
                    int c = bt.charAt(i) - '0';
                    dl *= odds[i][c];
                }
                map.put(bt, Double.valueOf(dl));
            }
        }
        List<Entry<String, Double>> infos = new ArrayList<Entry<String, Double>>(map.entrySet());
        // 排序
        Collections.sort(infos, new Comparator<Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        for (Entry entry : infos) {
            String bet = (String) entry.getKey();
            ranks.add(bet);
        }
        return ranks;
    }

    /**
     * @方法说明: 获取当前用户密保验证
     * @参数:
     * @return List<UserSfQa>
     * @throws
     */
    public static List<UserSfQa> getRondomSafeQas(User user) {
        SystemEnvironment sysEnv = SystemEnvironment.getInstance();
        UserDAO userDao = new UserDAO();
        List<UserSfQa> safeqas = userDao.getSafeQaByUserId(user.getUserid());
        if (safeqas != null && safeqas.size() > 0) {
            int qaNumber = safeqas.size();
            // 生成随即密保验证
            if (qaNumber > sysEnv.qa_max_answer) {
                int removeNum = qaNumber - sysEnv.qa_max_answer;
                for (int i = 0; i < removeNum; i++) {
                    Random rand = new Random();
                    int removeIdx = Math.abs(rand.nextInt()) % (qaNumber - i);
                    safeqas.remove(removeIdx);
                }
                qaNumber = sysEnv.qa_max_answer;
            }
        }
        return safeqas;
    }

    /**
     * @方法说明: 是否存在支付密码
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static boolean hasPayPassword(User user) {
        return (null != user.getPaypassword());
    }

    /**
     * @方法说明: 获取支付密码
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static String getPayPassword(User user) {
        return user.getPaypassword();
    }

    /**
     * @方法说明: 获取帐户余额
     * @参数: @return
     * @return String
     * @throws
     */
    public static Double getLeftMoney(User user) {
        Double left = Double.valueOf(0.0);
        UserDAO userDao = new UserDAO();
        CapitalInfo capInfo = userDao.getCapitalInfoByUserId(user.getUserid());
        if (capInfo != null) {
            left = capInfo.getRemain();
        }
        return left;
    }

    /**
     * @方法说明:获取帐户资产明细
     * @参数: @return
     * @return double
     * @throws
     */
    public static CapitalInfo getCapitalInfo(User user) {
        UserDAO userDao = new UserDAO();
        CapitalInfo capInfo = userDao.getCapitalInfoByUserId(user.getUserid());
        return capInfo;
    }

    /**
     * @方法说明: 检查投注是否合法(投注时间)
     * @参数: @return
     * @return boolean
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidBet(Lottery lt) {
        SystemEnvironment sysEnv = SystemEnvironment.getInstance();
        int type = lt.getType();
        String qihao = lt.getQihao();
        Map<String, String> mulMatches = lt.getMulMatches();
        Map<String, List<String>> sinMatches = lt.getSinMatches();
        if (type < 2) {
            SfcDAO sfcDao = new SfcDAO();
            List<SfcMatch> sfcs = sfcDao.getSfcMatchesByQh(qihao);
            if (sfcs == null || sfcs.size() == 0) {
                return false;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, 10);
            Date earliest = calendar.getTime();
            for (SfcMatch sfc : sfcs) {
                Date deadline = sfc.getPlayTime();
                if (earliest.after(deadline)) {
                    earliest = deadline;
                }
            }
            calendar.setTime(earliest);
            calendar.add(Calendar.MINUTE, (int) (-sysEnv.multi_bet_deadline)); // 投注截至时间
            earliest = calendar.getTime();
            if (earliest.after(new Date())) {
                return true;
            } else {
                return false;
            }
        } else if (type == 2) {
            BjdcDAO bjdcDao = new BjdcDAO();
            Set<String> mids = new HashSet<String>();
            mids.addAll(mulMatches.keySet());
            int mulSize = mids.size();
            Set<String> sinmids = new HashSet<String>();
            Iterator it = sinMatches.keySet().iterator();
            int sinSize = 0;
            while (it.hasNext()) {
                String mid = (String) it.next();
                mid = mid.trim();
                String[] mds = mid.split(",");
                sinSize = mds.length;
                for (int i = 0; i < sinSize; i++) {
                    sinmids.add(mds[i]);
                }
            }
            sinSize = sinmids.size();

            mids.addAll(sinmids);
            List<BjdcMatch> bjdcs = bjdcDao.getBjdcMatchesByQhIds(qihao, mids);
            if (bjdcs == null) {
                return false;
            }
            int matchSize = bjdcs.size();
            if (matchSize != mulSize && matchSize != sinSize) {
                return false;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, 10);
            Date earliest = calendar.getTime();
            for (BjdcMatch bjdc : bjdcs) {
                Date deadline = bjdc.getPlayTime();
                if (earliest.after(deadline)) {
                    earliest = deadline;
                }
            }
            calendar.setTime(earliest);
            calendar.add(Calendar.MINUTE, (int) (-sysEnv.multi_bet_deadline)); // 投注截至时间
            earliest = calendar.getTime();
            if (earliest.after(new Date())) {
                return true;
            } else {
                return false;
            }
        } else if (type == 3) {
            JczqDAO jczqDao = new JczqDAO();
            Set<String> mids = new HashSet<String>();
            mids.addAll(mulMatches.keySet());
            int mulSize = mids.size();
            Set<String> sinmids = new HashSet<String>();
            Iterator it = sinMatches.keySet().iterator();
            int sinSize = 0;
            while (it.hasNext()) {
                String mid = (String) it.next();
                mid = mid.trim();
                String[] mds = mid.split(",");
                sinSize = mds.length;
                for (int i = 0; i < sinSize; i++) {
                    sinmids.add(mds[i]);
                }
            }
            sinSize = sinmids.size();

            mids.addAll(sinmids);
            List<JczqMatch> jczqs = jczqDao.getJczqMatchesByQhIds(qihao, mids);
            if (jczqs == null) {
                return false;
            }
            int matchSize = jczqs.size();
            if (matchSize != mulSize && matchSize != sinSize) {
                return false;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, 10);
            Date earliest = calendar.getTime();
            for (JczqMatch jczq : jczqs) {
                Date deadline = jczq.getPlayTime();
                if (earliest.after(deadline)) {
                    earliest = deadline;
                }
            }
            calendar.setTime(earliest);
            calendar.add(Calendar.MINUTE, (int) (-sysEnv.multi_bet_deadline)); // 投注截至时间
            earliest = calendar.getTime();
            if (earliest.after(new Date())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @方法说明: 北京单场开奖
     * @参数: @param bet
     * @参数: @return
     * @return int 中奖注数
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static int doBjdcKj(BetForm bet) {
        SystemEnvironment sysEnv = SystemEnvironment.getInstance();
        Map<String, String> kjs = bet.getKjMidNum();
        Map<String, Double> sps = bet.getMidSps();
        Lottery lt = Lottery.convertFromBet(bet);
        BetBasket basket = BetBasket.generateBets(lt);
        Map<String, List<String>> singles = basket.getSingleBets();
        Iterator it = singles.keySet().iterator();
        int award = 0;
        double sp = 1.0;
        double preward = 0.0;
        double pstward = 0.0;
        double singleward = 0.0;
        String awardNum = null;
        char onekj = '*';
        boolean isZJ = true;
        while (it.hasNext()) {
            awardNum = "";
            sp = 1.0;
            String mid = (String) it.next();
            List<String> numbers = singles.get(mid);
            String[] mids = mid.trim().split(SystemConstants.COMMA);
            for (int i = 0; i < mids.length; i++) {
                awardNum += kjs.get(mids[i]);
                sp *= sps.get(mids[i]);
            }
            // 可能存在重复注
            for (String num : numbers) {
                isZJ = true;
                int len = num.length();
                for (int i = 0; i < len; i++) {
                    onekj = awardNum.charAt(i);
                    if (onekj != '*' && num.charAt(i) != onekj) {
                        isZJ = false;
                        break;
                    }
                }
                if (isZJ) {
                    award++;
                    singleward = sysEnv.money_per_bet * sp * sysEnv.bjdc_rate;
                    // 单注奖金小于单注投注金额
                    if (singleward < sysEnv.money_per_bet) {
                        singleward = sysEnv.money_per_bet;
                    }
                    preward += singleward;
                    // 单注奖金大于缴税限定
                    if (singleward >= sysEnv.tax_reward) {
                        pstward += singleward * (1 - sysEnv.tax_rate);
                    } else {
                        pstward += singleward;
                    }
                }
            }
        }
        long multi = bet.getMulti();
        preward *= multi;
        pstward *= multi;
        // 保留位数
        preward = Double.valueOf(sysEnv.moneyformat.format(preward));
        pstward = Double.valueOf(sysEnv.moneyformat.format(pstward));
        if (0 == award) {
            bet.setWinflag((long) 0);
        } else {
            bet.setWinflag((long) 1);
            bet.setRewardBets((long) award);
            bet.setBfreward(preward);
            bet.setAfreward(pstward);
            bet.setRewardflag((long) 1);
        }
        bet.setDtime(new Date());
        return award;
    }

    /**
     * @方法说明: 竞彩足球开奖
     * @参数: @param bet
     * @参数: @return
     * @return int 中奖注数
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static int doJczqKj(BetForm bet) {
        SystemEnvironment sysEnv = SystemEnvironment.getInstance();
        Map<String, String> kjs = bet.getKjMidNum();
        Map<String, Double> sps = bet.getMidSps();
        Lottery lt = Lottery.convertFromBet(bet);
        BetBasket basket = BetBasket.generateBets(lt);
        Map<String, List<String>> singles = basket.getSingleBets();
        Iterator it = singles.keySet().iterator();
        int award = 0;
        double sp = 1.0;
        double preward = 0.0;
        double pstward = 0.0;
        double singleward = 0.0;
        String awardNum = null;
        char onekj = '*';
        boolean isZJ = true;
        while (it.hasNext()) {
            awardNum = "";
            sp = 1.0;
            String mid = (String) it.next();
            List<String> numbers = singles.get(mid);
            String[] mids = mid.trim().split(SystemConstants.COMMA);
            for (int i = 0; i < mids.length; i++) {
                awardNum += kjs.get(mids[i]);
                sp *= sps.get(mids[i]);
            }
            // 可能存在重复注
            for (String num : numbers) {
                isZJ = true;
                int len = num.length();
                for (int i = 0; i < len; i++) {
                    onekj = awardNum.charAt(i);
                    if (onekj != '*' && num.charAt(i) != onekj) {
                        isZJ = false;
                        break;
                    }
                }
                if (isZJ) {
                    award++;
                    // 浮动奖金
                    if (1 == mids.length) {
                        singleward = sysEnv.money_per_bet * sp * sysEnv.jczq_rate;
                    } else {
                        singleward = sysEnv.money_per_bet * sp;
                    }

                    // 单注奖金小于单注投注金额
                    if (singleward < sysEnv.money_per_bet) {
                        singleward = sysEnv.money_per_bet;
                    }
                    preward += singleward;
                    // 单注奖金大于缴税限定
                    if (singleward >= sysEnv.tax_reward) {
                        pstward += singleward * (1 - sysEnv.tax_rate);
                    } else {
                        pstward += singleward;
                    }
                }
            }
        }
        long multi = bet.getMulti();
        preward *= multi;
        pstward *= multi;
        // 保留位数
        preward = Double.valueOf(sysEnv.moneyformat.format(preward));
        pstward = Double.valueOf(sysEnv.moneyformat.format(pstward));
        if (0 == award) {
            bet.setWinflag((long) 0);
        } else {
            bet.setWinflag((long) 1);
            bet.setRewardBets((long) award);
            bet.setBfreward(preward);
            bet.setAfreward(pstward);
            bet.setRewardflag((long) 1);
        }
        bet.setDtime(new Date());
        return award;
    }

    /**
     * @方法说明: 14场胜负彩开奖
     * @参数: @param bet
     * @参数: @return
     * @return int [] 一等和二等中奖注数
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static int[] doSfc14Kj(BetForm bet) {
        SystemEnvironment sysEnv = SystemEnvironment.getInstance();
        int[] award = { 0, 0 };
        int first = 0;
        int second = 0;
        String qihao = bet.getQihao();
        String kjnum = bet.getKjnumbers();
        kjnum = kjnum.replaceAll(" ", "");
        char onekj = '*';
        Lottery lt = Lottery.convertFromBet(bet);
        BetBasket basket = BetBasket.generateBets(lt);
        Map<String, List<String>> singles = basket.getSingleBets();
        Iterator it = singles.keySet().iterator();
        while (it.hasNext()) {
            String mid = (String) it.next();
            List<String> numbers = singles.get(mid);
            int rightCount = 0;
            // 可能存在重复注
            for (String num : numbers) {
                rightCount = 0;
                for (int i = 0; i < 14; i++) {
                    onekj = kjnum.charAt(i);
                    if (onekj == '*' || onekj == num.charAt(i)) { // 存在非正常结束情况
                        rightCount++;
                    }
                }
                if (14 == rightCount) {
                    first++;
                }
                if (13 == rightCount) {
                    second++;
                }
            }
        }
        if (0 == first && 0 == second) {
            bet.setWinflag((long) 0);
        } else {
            bet.setWinflag((long) 1);
            bet.setRewardBets((long) first);
            bet.setReward2Bets((long) second);
            SfcKjDAO skdao = new SfcKjDAO();
            SfcKj sfckj = skdao.getSfcKjByQihao(qihao);
            long multi = bet.getMulti();
            int fp = sfckj.getFirstPrize();
            int sp = sfckj.getSecondPrize();
            double peraward = (first * fp + second * sp) * multi;
            peraward = Double.valueOf(sysEnv.moneyformat.format(peraward));
            bet.setBfreward(peraward);
            double pstaward = 0.0;
            if (fp >= sysEnv.tax_reward) {
                pstaward += first * fp * (1.0 - sysEnv.tax_rate);
            } else {
                pstaward += first * fp;
            }
            if (sp >= sysEnv.tax_reward) {
                pstaward += second * sp * (1.0 - sysEnv.tax_rate);
            } else {
                pstaward += second * sp;
            }
            pstaward *= multi;
            pstaward = Double.valueOf(sysEnv.moneyformat.format(pstaward));
            bet.setAfreward(pstaward);
            bet.setRewardflag((long) 1);
        }
        bet.setDtime(new Date());
        award[0] = first;
        award[1] = second;
        return award;
    }

    /**
     * @方法说明: 14场胜负彩任选9场开奖
     * @参数: @param bet
     * @参数: @return
     * @return int 中奖注数
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static int doSfcR9Kj(BetForm bet) {
        SystemEnvironment sysEnv = SystemEnvironment.getInstance();
        String qihao = bet.getQihao();
        int award = 0;
        Map<String, String> kjs = bet.getKjMidNum();
        Lottery lt = Lottery.convertFromBet(bet);
        BetBasket basket = BetBasket.generateBets(lt);
        Map<String, List<String>> singles = basket.getSingleBets();
        Iterator it = singles.keySet().iterator();
        String awardNum = null;
        char onekj = '*';
        boolean isZJ = true;
        while (it.hasNext()) {
            awardNum = "";
            String mid = (String) it.next();
            List<String> numbers = singles.get(mid);
            String[] mids = mid.trim().split(SystemConstants.COMMA);
            for (int i = 0; i < mids.length; i++) {
                awardNum += kjs.get(mids[i]);
            }
            // 可能存在重复注
            for (String num : numbers) {
                isZJ = true;
                int len = num.length();
                for (int i = 0; i < len; i++) {
                    onekj = awardNum.charAt(i);
                    if (onekj != '*' && num.charAt(i) != onekj) {
                        isZJ = false;
                        break;
                    }
                }
                if (isZJ) {
                    award++;
                }
            }
        }
        if (0 == award) {
            bet.setWinflag((long) 0);
        } else {
            bet.setWinflag((long) 1);
            bet.setRewardBets((long) award);
            SfcKjDAO skdao = new SfcKjDAO();
            SfcKj sfckj = skdao.getSfcKjByQihao(qihao);
            long multi = bet.getMulti();
            int rp = sfckj.getR9Prize();
            double peraward = award * rp * multi;
            peraward = Double.valueOf(sysEnv.moneyformat.format(peraward));
            bet.setBfreward(peraward);
            double pstaward = 0.0;
            if (rp >= sysEnv.tax_reward) {
                pstaward = award * rp * (1.0 - sysEnv.tax_rate);
            } else {
                pstaward = award * rp;
            }
            pstaward *= multi;
            pstaward = Double.valueOf(sysEnv.moneyformat.format(pstaward));
            bet.setAfreward(pstaward);
            bet.setRewardflag((long) 1);
        }
        bet.setDtime(new Date());
        return award;
    }

}
