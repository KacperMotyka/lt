/**
 * @文件名称: HelpManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-31 下午02:46:30
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-31 下午02:46:30
 * @版本: 1.0.0
 */

public class HelpManager {

	private static HelpManager helpManager = null;

	private HelpManager() {
	}

	public static HelpManager geInstance() {
		if (helpManager == null) {
			helpManager = new HelpManager();
		}
		return helpManager;
	}
}
