/**
 * @文件名称: FilterAPI.java
 * @类路径:   com.rb.lottery.jni
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-13 上午11:18:01
 * @版本:     1.0.0
 */
package com.rb.lottery.client.jni;

import java.io.File;

import com.rb.lottery.client.common.FilePathConstants;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-13 上午11:18:01
 * @版本: 1.0.0
 */

public class FilterAPI {

	static {
		System.load(System.getProperty("user.dir") + File.separator
				+ FilePathConstants.FILTER_API_DLL_DIR);
	}

	/**
	 * @方法说明: 获取个数
	 * @参数: @param string
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getCount(String src, char dest);

	/**
	 * @方法说明: 计算积分数
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getScoCount(String src);

	/**
	 * @方法说明:计算断点数
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getBreakCount(String src);

	/**
	 * @方法说明: 计算连场数,投注结果中相邻两个号码相同就是一个连号,如：33、11、00
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getContinueCount(String src);

	/**
	 * @方法说明: 计算最长连场数
	 * @参数: @param src 原串
	 * @参数: @param dest 目标字符
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getMxContinueCount(String src, char dest);

	/**
	 * @方法说明: 单注中comb1和comb2任意组合在一起的最大连续场次数（在该连续场次中必须包含comb1和comb2）
	 * @参数: @param src
	 * @参数: @param comb1
	 * @参数: @param comb2
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getCombConCount(String src, char comb1, char comb2);

	/**
	 * @方法说明:邻号间距和过滤,一个投注结果中,相邻两个号码的号码间距之和
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getDistanceSum(String src);

	/**
	 * @方法说明: 邻号乘积和过滤,相邻两个号码的乘积之和
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getNbrCrsSum(String src);

	/**
	 * @方法说明: 号码位积和过滤,比赛的3、1、0结果与其序号的乘积之和
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getPosCrsSum(String src);
}
