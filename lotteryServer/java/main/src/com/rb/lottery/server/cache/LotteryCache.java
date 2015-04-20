/**
 * @文件名称: LotteryCache.java
 * @类路径:   com.rb.lottery.cache
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:49:48
 * @版本:     1.0.0
 */
package com.rb.lottery.server.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rb.lottery.domain.Lottery;

/**
 * @类功能说明: 出票数据缓存
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 下午12:49:48
 * @版本: 1.0.0
 */

public class LotteryCache extends Cache {

    public LotteryCache() {
        data = new HashMap<String, Lottery>();
    }

    public LotteryCache(Map<String, Lottery> data) {
        this.data = new HashMap<String, Lottery>(data);
    }

    @Override
    public HashMap<String, Lottery> getData() {
        return (HashMap<String, Lottery>) data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rb.lottery.cache.Cache#removeByValue(java.lang.Object)
     */
    @Override
    public void removeByValue(Object value) {
        Lottery lottery = (Lottery) value;
        String id = lottery.getLotteryId();
        data.remove(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void add(Object value) {
        Lottery lottery = (Lottery) value;
        String id = lottery.getLotteryId();
        data.put(id, lottery);
    }

    /**
     * @方法说明: 获取对应彩票的出票信息
     * @参数: @param currentLotteryId
     * @参数: @return
     * @return String
     * @throws
     */
    public String getBetStatu(String currentLotteryId) {
        Lottery lottery = (Lottery) data.get(currentLotteryId);
        return lottery.getBetStatu();
    }

    /**
     * @方法说明: 获取当前彩票信息
     * @参数: @param lotteryId
     * @参数: @return
     * @return Lottery
     * @throws
     */
    public Lottery getLotteryById(String lotteryId) {
        Lottery lottery = (Lottery) data.get(lotteryId);
        return lottery;
    }

    /**
     * @方法说明:
     * @参数: @param id
     * @参数: @param bet
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void removeOneBet(String lotteryId, String matchId, String bet) {
        Lottery lottery = getLotteryById(lotteryId);
        lottery.remove(matchId, bet);
        data.put(lotteryId, lottery);
    }

    /**
     * @方法说明:
     * @参数: @param id
     * @参数: @param bet
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void addOneBet(String lotteryId, String matchId, String bet) {
        Lottery lottery = getLotteryById(lotteryId);
        lottery.add(matchId, bet);
        data.put(lotteryId, lottery);
    }

    /**
     * @方法说明:
     * @参数: @param lotteryId
     * @参数: @param passway
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void setLotteryPassway(String lotteryId, List<String> passway) {
        Lottery lottery = getLotteryById(lotteryId);
        lottery.setPassway(passway);
        data.put(lotteryId, lottery);
    }

    public void removeByKey(String key) {
        data.remove(key);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isEmpty() {
        return (data == null || data.size() == 0);
    }
}
