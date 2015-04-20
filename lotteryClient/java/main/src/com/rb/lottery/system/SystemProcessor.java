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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rb.lottery.client.UI.dialog.ExitSaveDialog;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.client.exception.LotteryException;
import com.rb.lottery.client.manager.IOManager;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.client.manager.SystemConfManager;
import com.rb.lottery.client.util.IniProcessor;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.domain.SfcMatch;
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

    // 文件更新超过该时间（5小时），直接从数据库读取数据，否则本地读取
    private static final long DATA_REFRSH_TIME = 5 * 3600 * 1000L;

    /**
     * @方法说明: 系统缓存初始化
     * @参数:
     * @return void
     * @throws
     */
    public static void init() {
        SystemCache.init();
        log4jInit();

    }

    /**
     * @方法说明:初始化日志配置
     * @参数:
     * @return void
     * @throws
     */
    public static void log4jInit() {
        PropertyConfigurator.configure(FilePathConstants.LOG4J_CONFIG_FILE);
    }

    /**
     * @方法说明: 系统初始化
     * @参数:
     * @return void
     * @throws
     */
    public static boolean initEnvironment() {
        try {
            // 客户端用户环境初始化
            File userConfFile = new File(FilePathConstants.USER_CONFIG_FILE);
            SystemConfManager.getInstance().initUserConfig(userConfFile);
            // 客户端系统环境初始化
            InputStream inputStream = new FileInputStream(FilePathConstants.SERVER_CONFIG_FILE);
            Properties serverProp = new Properties();
            try {
                serverProp.load(inputStream);
                String ip = serverProp.getProperty("server.ip");
                String portStr = serverProp.getProperty("server.port");
                int port = 8821;
                if (portStr.matches(RegexConstants.INTEGER_REGEX)) {
                    port = Integer.parseInt(portStr);
                } else {
                    JOptionPane.showMessageDialog(null, "连接服务器失败,请检查配置!");
                    return false;
                }
                SystemCache.serverIP = ip;
                SystemCache.serverPort = port;
                boolean success = SocketManager.getInstance().getEnvironment();
                if (!success) {
                    log.info("客户端环境初始化失败!");
                    return false;
                }
                // 设置连接超时时间
                /*
                 * SystemCache.socket .setSoTimeout(SystemEnvironment.getInstance ().connection_timeout .intValue());
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } catch (LotteryException e) {
            e.printStackTrace();
            return false;
        }
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
    public static List<UserSfQa> getRondomSafeQas() {
        List<UserSfQa> safeqas = null;

        return safeqas;
    }

    /**
     * @方法说明: 是否存在支付密码
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static boolean hasPayPassword() {
        if (SystemCache.isLogin && SystemCache.currentUser.getPaypassword() != null) {
            return true;
        }
        return false;
    }

    /**
     * @方法说明: 获取支付密码
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static String getPayPassword() {
        if (SystemCache.isLogin) {
            return SystemCache.currentUser.getPaypassword();
        }
        return null;
    }

    /**
     * @方法说明: 检查投注是否合法(投注时间)
     * @参数: @return
     * @return boolean
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidBet(Lottery lt) {
        SocketManager som = SocketManager.getInstance();
        int type = lt.getType();
        String qihao = lt.getQihao();
        Map<String, String> mulMatches = lt.getMulMatches();
        Map<String, List<String>> sinMatches = lt.getSinMatches();
        if (type < 2) {
            List<SfcMatch> sfcs = som.getSfcMatchByQh(qihao);
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
            calendar.add(Calendar.MINUTE, (int) (-SystemEnvironment.getInstance().single_bet_deadline)); // 投注截至时间
            earliest = calendar.getTime();
            if (earliest.after(new Date())) {
                return true;
            } else {
                return false;
            }
        } else if (type == 2) {
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
            List<BjdcMatch> bjdcs = som.getBjdcMatchByQhIds(qihao, SystemFunctions.setToArray(mids));
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
            calendar.add(Calendar.MINUTE, (int) (-SystemEnvironment.getInstance().single_bet_deadline)); // 投注截至时间
            earliest = calendar.getTime();
            if (earliest.after(new Date())) {
                return true;
            } else {
                return false;
            }
        } else if (type == 3) {
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
            List<JczqMatch> jczqs = som.getJczqMatchByQhIds(qihao, SystemFunctions.setToArray(mids));
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
            calendar.add(Calendar.MINUTE, (int) (-SystemEnvironment.getInstance().single_bet_deadline)); // 投注截至时间
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
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public static boolean isExitSave() {
        boolean autoSave = SysConfig.getInstance().isAutoSave();
        if (!autoSave) {
            int isOK = JOptionPane.showConfirmDialog(null, MessageConstants.EXIT_SAVE_INFO,
                    MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (isOK == 0) {
                autoSave = true;
            }
        }
        return autoSave;
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public static void saveAndExit() {
        SystemProcessor.saveAll();
        System.exit(0);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public static void saveUserConfig() {
        String file = FilePathConstants.USER_CONFIG_FILE;
        String section = SystemConstants.LABEL_BETTYPE;
        String variable = SystemConstants.BET_TYPE;
        String value = SystemConstants.BET_TYPES[UserConfig.getInstance().getTzlx()];
        try {
            IniProcessor.setProfileString(file, section, variable, value);
            section = SystemConstants.BETNUM;
            variable = SystemConstants.QIHAO;
            value = UserConfig.getInstance().getQihao();
            IniProcessor.setProfileString(file, section, variable, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public static void saveAll() {
        ExitSaveDialog dialog = new ExitSaveDialog();
        dialog.setVisible(true);
        String type = SystemConstants.BET_TYPES[SystemCache.currentType];
        String qihao = SystemCache.currentQihao;
        int cnt = 1;
        String prjPath = FilePathConstants.PROJECT_FILE_DIR + File.separator + type + qihao + SystemConstants.PART;
        String prjFileName = prjPath + cnt + FilePathConstants.PROJECT_FILE_POSTFIX;
        File prjFile = new File(prjFileName);
        if (!SystemCache.lotteryCache.isEmpty()) {
            while (prjFile.exists()) {
                cnt++;
                prjFileName = prjPath + cnt + FilePathConstants.PROJECT_FILE_POSTFIX;
                prjFile = new File(prjFileName);
            }
            IOManager.getInstance().doSavePrj(prjFileName);
        }
        if (!SystemCache.currentBasketId.equals(SystemConstants.EMPTY_STRING)) {
            cnt = 1;
            String retPath = FilePathConstants.RESULT_FILE_DIR + File.separator + type + qihao + SystemConstants.PART;
            String retFileName = retPath + cnt + FilePathConstants.RESULT_FILE_POSTFIX;
            File retFile = new File(retFileName);
            while (retFile.exists()) {
                cnt++;
                retFileName = retPath + cnt + FilePathConstants.RESULT_FILE_POSTFIX;
                retFile = new File(retFileName);
            }
            IOManager.getInstance().doSaveRet(retFileName);
        }
        saveUserConfig();
        // TODO save other configuration
        dialog.destroy();
    }

    /**
     * @方法说明:初始化系统
     * @参数:
     * @return void
     * @throws
     */
    public static Map<String, List<BetMatch>> initMatchData() {
        UserConfig uconf = UserConfig.getInstance();
        SocketManager sm = SocketManager.getInstance();
        IOManager iom = IOManager.getInstance();
        Map<String, List<BetMatch>> map = new HashMap<String, List<BetMatch>>();
        try {
            int type = uconf.getTzlx();
            String userQihao = uconf.getQihao();
            String sysQihao = SystemConstants.EMPTY_STRING;
            if (type < 0 || type > 3) {
                throw (new LotteryException(16));
            } else if (type < 2) {
                sysQihao = SysConfig.getInstance().getSfcQihao();
            } else if (type == 2) {
                sysQihao = SysConfig.getInstance().getBjdcQihao();
            } else {
                sysQihao = SysConfig.getInstance().getJczqPeriod();
            }
            // 是否过期
            if (!userQihao.equals(sysQihao)) {
                ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
                int isOK = JOptionPane.showConfirmDialog(null, MessageConstants.OUTOFDATE_INFO,
                        MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                if (isOK == 0) // YES
                {
                    boolean getFromServer = getFromServer(type, sysQihao);
                    if (getFromServer) {
                        map = sm.getGUIDataFromServer(type, sysQihao);
                    } else {
                        map = iom.getGUIDate(type, sysQihao);
                    }
                    uconf.setQihao(sysQihao);
                } else // NO
                {
                    boolean getFromServer = getFromServer(type, userQihao);
                    if (getFromServer) {
                        map = sm.getGUIDataFromServer(type, userQihao);
                    } else {
                        map = iom.getGUIDate(type, sysQihao);
                    }
                }
            } else {
                boolean getFromServer = getFromServer(type, sysQihao);
                if (getFromServer) {
                    map = sm.getGUIDataFromServer(type, sysQihao);
                } else {
                    map = iom.getGUIDate(type, sysQihao);
                }
                uconf.setQihao(sysQihao);
            }
            // 未取到用户指定期数的数据
            if (null == map) {
                map = sm.getGUIDataFromServer(type, sysQihao);
                uconf.setQihao(sysQihao);
            }
            SystemCache.initDataCache(map);
        } catch (LotteryException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @方法说明:期号切换获取该期号数据
     * @参数: @return
     * @return Map<String,List<BetMatch>>
     * @throws
     */
    public static Map<String, List<BetMatch>> getUpdateMatchData() {
        Map<String, List<BetMatch>> map = new HashMap<String, List<BetMatch>>();
        int type = UserConfig.getInstance().getTzlx();
        String userQihao = UserConfig.getInstance().getQihao();
        boolean getFromServer = getFromServer(type, userQihao);
        if (getFromServer) {
            map = SocketManager.getInstance().getGUIDataFromServer(type, userQihao);
        } else {
            map = IOManager.getInstance().getGUIDate(type, userQihao);
        }
        return map;
    }

    /**
     * @方法说明: 是否从服务器获取数据
     * @参数: @param type
     * @参数: @param qihao
     * @参数: @return
     * @return boolean
     * @throws
     */
    private static boolean getFromServer(int type, String qihao) {
        String fileName = "";
        String fileDir = "";
        File theFile = null;
        if (type < 2) {
            fileName = qihao + SystemConstants.SFC + FilePathConstants.DATA_FILE_POSTFIX;
            fileDir = FilePathConstants.SFC_BETDATA_DIR + SystemConstants.NEXT_FOLDER + fileName;
        } else if (2 == type) {
            fileName = qihao + SystemConstants.BJDC + FilePathConstants.DATA_FILE_POSTFIX;
            fileDir = FilePathConstants.BJDC_BETDATA_DIR + SystemConstants.NEXT_FOLDER + fileName;
        } else if (2 == type) {
            fileName = qihao + SystemConstants.JCZQ + FilePathConstants.DATA_FILE_POSTFIX;
            fileDir = FilePathConstants.JCZQ_BETDATA_DIR + SystemConstants.NEXT_FOLDER + fileName;
        }
        theFile = new File(fileDir);
        if (theFile.exists()) {
            long up = new Date().getTime() - theFile.lastModified();
            if (up < DATA_REFRSH_TIME) {
                return false;
            }
        }
        return true;
    }

}
