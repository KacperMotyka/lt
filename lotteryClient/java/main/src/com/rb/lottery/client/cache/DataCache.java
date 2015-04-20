/**
 * @文件名称: DataCache.java
 * @类路径:   com.rb.lottery.cache
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-10 上午10:42:29
 * @版本:     1.0.0
 */
package com.rb.lottery.client.cache;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.UserConfig;

/**
 * @类功能说明: 投注数据缓存
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-10 上午10:42:29
 * @版本: 1.0.0
 */

public class DataCache extends Cache {
    public int type = -1;
    public String qihao = SystemConstants.EMPTY_STRING;

    public DataCache() {
        data = new TreeMap<String, List<BetMatch>>();
    }

    public DataCache(Map<String, List<BetMatch>> dat) {
        data = new TreeMap<String, List<BetMatch>>(dat);
    }

    @Override
    public TreeMap<String, List<BetMatch>> getData() {
        TreeMap<String, List<BetMatch>> newdata = new TreeMap<String, List<BetMatch>>(data);
        return newdata;
    }

    /**
     * @return type
     */
    @SuppressWarnings("unchecked")
    public int getType() {
        Set keySet = data.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            List<BetMatch> matches = (List<BetMatch>) data.get(key);
            if (matches != null && matches.size() > 0) {
                type = matches.get(0).getType();
                break;
            }
        }
        if (type == -1) {
            type = UserConfig.getInstance().getTzlx();
        }
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
    @SuppressWarnings("unchecked")
    public String getQihao() {
        Set keySet = data.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            List<BetMatch> matches = (List<BetMatch>) data.get(key);
            if (matches != null && matches.size() > 0) {
                qihao = matches.get(0).getQihao();
                break;
            }
        }
        if (qihao.equals(SystemConstants.EMPTY_STRING)) {
            if (type < 2) {
                qihao = SysConfig.getInstance().getSfcQihao();
            } else if (type == 2) {
                qihao = SysConfig.getInstance().getBjdcQihao();
            } else {
                qihao = SysConfig.getInstance().getJczqPeriod();
            }
        }
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
     * 删除指定ID的比赛投注数据
     * 
     * @param Object
     *            value 比赛ID
     */
    @SuppressWarnings("unchecked")
    @Override
    public void removeByValue(Object value) {
        String val = (String) value;
        Set keySet = data.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            List<BetMatch> matches = (List<BetMatch>) data.get(key);
            if (matches != null && matches.size() > 0) {
                for (BetMatch match : matches) {
                    String id = match.getMatchId();
                    if (val.equals(id)) {
                        matches.remove(match);
                    }
                }
            }
        }
    }

    public void init(TreeMap<String, List<BetMatch>> dat) {
        data.clear();
        data.putAll(dat);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rb.lottery.cache.Cache#add(java.lang.Object)
     */
    @Override
    public void add(Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        type = -1;
        qihao = SystemConstants.EMPTY_STRING;
        super.clear();
    }

    public void removeByKey(String key) {
        data.remove(key);
    }

    /**
     * @方法说明:
     * @参数: @param id
     * @参数: @return
     * @return BetMatch
     * @throws
     */
    @SuppressWarnings("unchecked")
    public BetMatch getMatchById(String id) {
        Iterator eit = data.entrySet().iterator();
        while (eit.hasNext()) {
            Entry entry = (Entry) eit.next();
            List<BetMatch> matches = (List<BetMatch>) entry.getValue();
            if (matches != null && matches.size() > 0) {
                for (BetMatch match : matches) {
                    if (id.equals(match.getMatchId())) {
                        return match;
                    }
                }
            }
        }
        return null;
    }

}
