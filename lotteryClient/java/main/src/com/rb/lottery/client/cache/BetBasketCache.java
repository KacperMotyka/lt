/**
 * @文件名称: BetBasketCache.java
 * @类路径:   com.rb.lottery.cache
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-14 下午04:11:21
 * @版本:     1.0.0
 */
package com.rb.lottery.client.cache;

import java.util.HashMap;
import java.util.Map;

import com.rb.lottery.domain.BetBasket;

/**
 * @类功能说明: 号码篮缓存
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-14 下午04:11:21
 * @版本: 1.0.0
 */

public class BetBasketCache extends Cache<String, BetBasket> {

    public BetBasketCache() {
        data = new HashMap<String, BetBasket>();
    }

    public BetBasketCache(Map<String, BetBasket> data) {
        this.data = new HashMap<String, BetBasket>(data);
    }

    @Override
    public HashMap<String, BetBasket> getData() {
        return (HashMap<String, BetBasket>) data;
    }

    @Override
    public void add(BetBasket basket) {
        String id = basket.getId();
        data.put(id, basket);
    }

    @Override
    public void removeByValue(BetBasket basket) {
        String id = basket.getId();
        data.remove(id);
    }

    /**
     * @方法说明:
     * @参数: @param basketId
     * @参数: @return
     * @return BetBasket
     * @throws
     */
    public BetBasket getBasketById(String basketId) {
        BetBasket basket = data.get(basketId);
        return basket;
    }

    /**
     * @方法说明:
     * @参数: @param basketId
     * @参数: @param matchCount
     * @return void
     * @throws
     */
    public void setBasketMatchCount(String basketId, int matchCount) {
        BetBasket basket = this.getBasketById(basketId);
        basket.setMatchCount(matchCount);
        data.put(basketId, basket);
    }

    @Override
    public void removeByKey(String key) {
        data.remove(key);
    }

    /**
     * @方法说明:
     * @参数: @param basketId
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean contains(String basketId) {
        return data.containsKey(basketId);
    }
}
