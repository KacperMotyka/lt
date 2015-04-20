/**
 * @文件名称: LAPIrb.java
 * @类路径:   com.rb.lottery.jni
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-20 上午11:20:43
 * @版本:     1.0.0
 */
package com.rb.lottery.client.jni;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.rb.lottery.client.common.FilePathConstants;

/**
 * @类功能说明: 系统API -- JNI
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-20 上午11:20:43
 * @版本: 1.0.0
 */

public class LAPIrb {

	static {
		System.load(System.getProperty("user.dir") + File.separator
				+ FilePathConstants.SYSTEM_API_DLL_DIR);
	}

	/**
	 * @方法说明: string转换成boolean
	 * @参数: @param src
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public native boolean stringToBoolean(String src);

	/**
	 * @方法说明: 投注类型转换
	 * @参数: @param typeName
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int convertByTypeName(String typeName);

	/**
	 * @方法说明: 投注合法性判断
	 * @参数: @param type
	 * @参数: @param bet
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public native boolean isBetLegal(int type, String bet);

	/**
	 * @方法说明: 计算14胜负彩复式注数，格式[3|1|0]{1-3}[,[3|1|0]{1-3}]{13}
	 * @参数: @param bet
	 * @参数: @return
	 * @return int 返回0则非法
	 * @throws
	 */
	public native int calc14CombBetNum(String bet);

	/**
	 * @方法说明: 组合排列
	 * @参数: @param n
	 * @参数: @param i
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int C(int n, int m);

	/**
	 * @方法说明: 计算N*M是否合法
	 * @参数: @param n
	 * @参数: @param m
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int doCheckBetPattern(int n, int m);

	/**
	 * @方法说明: 从0到x-1中取y个数形成全排列结果
	 * @参数: @param ret 返回结果
	 * @参数: @param x
	 * @参数: @param y
	 * @return void
	 * @throws
	 */
	public native void generateQP(int[][] ret, int x, int y);

	/**
	 * @方法说明: 计算从x场比赛中取y场比赛形成y串1的注数
	 * @参数: @param bets 投注个数
	 * @参数: @param x 选择的场次
	 * @参数: @param y y串1投注
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int calcNum(int[] bets, int x, int y);

	/**
	 * @方法说明: 计算任9复式注数，格式[3|1|0]{1-3}[,[3|1|0]{1-3}]{13}
	 * @参数: @param bet
	 * @参数: @return
	 * @return int 返回0则非法
	 * @throws
	 */
	public native int calcR9CombBetNum(String bet);

	/**
	 * @方法说明: 计算复式投注注数，格式BET|(3|1|0|*)(,)(-|#)|M*N:X
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int calcBDJZCombBetNum(String bet);

	/**
	 * @方法说明: 字符串填充
	 * @参数: @param src 原串
	 * @参数: @param fill 填充串
	 * @参数: @param count 填充次数
	 * @参数: @param rurn 0-头填充 1-尾填充
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String polishing(String src, String fill, int count, int rurn);

	/**
	 * @方法说明: 百分比转换为浮点数
	 * @参数: @param src
	 * @参数: @return
	 * @return double
	 * @throws
	 */
	public native double percentToDouble(String src);

	/**
	 * @方法说明: 浮点数转换为百分比
	 * @参数: @param src
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String doubleToPercent(double src);

	/**
	 * @方法说明: 日期转换
	 * @参数: @param tmpstr
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String DateConvert(String src);

	/**
	 * @方法说明: 减1操作
	 * @参数: @param src
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String minusOne(String src);

	/**
	 * @方法说明: 减1天操作
	 * @参数: @param src 格式 2011-11-04
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String minusOneDay(String src);

	/**
	 * @方法说明: 生成期号列表
	 * @参数: @param type
	 * @参数: @param qihao
	 * @参数: @param maxQihaoCount
	 * @return void
	 * @throws
	 */
	public native String[] generateQihaos(int type, String qihao,
			int maxQihaoCount);

	/**
	 * @方法说明: 列号转化为对应投注值
	 * @参数: @param colIndex
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String convertToBet(int colIndex);

	/**
	 * @方法说明: 添加新的投注值，去重复
	 * @参数: @param origin
	 * @参数: @param add
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String betCombine(String origin, String add);

	/**
	 * @方法说明: 根据比赛场次得到可形成的串
	 * @参数: @param count
	 * @参数: @return
	 * @return List<String>
	 * @throws
	 */
	public native List<String> getChuanByMatchCount(int count);

	/**
	 * @方法说明: 是否为胆
	 * @参数: @param src
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int isDan(String src);

	/**
	 * @方法说明: 文件名是否包含特殊字符检测
	 * @参数: @param name
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public native boolean isLegalFileName(String name);

	/**
	 * @方法说明: 复式拆分为单式 格式31-10-0-130
	 * @参数: @param mbet
	 * @参数: @return
	 * @return List<String>
	 * @throws
	 */
	public native List<String> multiSplit(String mbet);

	/**
	 * @方法说明:全排列生成
	 * @参数: @param ret
	 * @参数: @param src
	 * @return void
	 * @throws
	 */
	private native void generateARQP(int[][] ret, int[] src);

	/**
	 * @方法说明:生成文件数据字串
	 * @参数: @param src
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native String toFileString(Map<String, List<String>> src);

	/**
	 * @方法说明:区域判断
	 * @参数: @param strLine
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public native boolean isSection(String strLine);

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

	/**
	 * @方法说明:
	 * @参数: @param odds
	 * @参数: @param winOdds
	 * @参数: @param drawOdds
	 * @参数: @param lossOdds
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public native char getOddPos(int odds, double winOdds, double drawOdds,
			double lossOdds);

	/**
	 * @方法说明: 获取匹配字符个数
	 * @参数: @param fisrtBet
	 * @参数: @param bet
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public native int getMatchingCount(String src, String bet);

	/**
	 * @方法说明: 获取倒数和
	 * @参数: @param datas
	 * @参数: @return
	 * @return double
	 * @throws
	 */
	public native double getRevSum(double... datas);

}
