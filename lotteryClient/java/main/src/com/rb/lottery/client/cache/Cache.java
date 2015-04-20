/**
 * @文件名称: Cache.java
 * @类路径:   com.rb.lottery.cache
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-10 上午10:47:21
 * @版本:     1.0.0
 */
package com.rb.lottery.client.cache;

import java.util.Map;


/**
 * @类功能说明: 数据缓存
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-10 上午10:47:21
 * @版本:       1.0.0
 */

public abstract class Cache<K, V> {
	Map<K, V> data;
	
	public  Cache() {
	}
	
	/**
	 * @return data
	 */
	public Map<K, V> getData() {
		return data;
	}

	/**
	 * @param data data
	 */
	public void setData(Map<K, V> data) {
		this.data = data;
	}

	public void removeByKey(K key) {
		data.remove(key);
	}	
	
	public void add(K key, V value) {
		data.put(key, value);
	}
	
	public void clear() {
		data.clear();
	}
	
	public abstract void removeByValue(V value);
	
	public abstract void add(V value);
}
