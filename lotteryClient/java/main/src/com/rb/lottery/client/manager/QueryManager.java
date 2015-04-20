/**
 * @文件名称: QueryManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-31 下午02:39:56
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-31 下午02:39:56
 * @版本: 1.0.0
 */

public class QueryManager {

	private static QueryManager queryManager = null;

	private QueryManager() {
	}

	public static QueryManager geInstance() {
		if (queryManager == null) {
			queryManager = new QueryManager();
		}
		return queryManager;
	}
}
