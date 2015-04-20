/**
 * @文件名称: RegexConstants.java
 * @类路径:   com.rb.lottery.common
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-18 上午10:27:43
 * @版本:     1.0.0
 */
package com.rb.lottery.server.common;

/**
 * @类功能说明: 正则表达式常量
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-18 上午10:27:43
 * @版本: 1.0.0
 */

public interface RegexConstants {

	// Regex
	public static final String INTEGER_REGEX = "^[+-]?\\d+$";	// 整数
	public static final String POS_INTEGER_REGEX = "^[+]?\\d+$";	// 正整数
	public static final String SFC14_REGEX = "(3|1|0|31|13|30|03|10|01|310|301|130|103|031|013)";
	public static final String BET_REGEX = "(3|1|0|31|13|30|03|10|01|310|301|130|103|031|013|3\\*|1\\*|0\\*|31\\*|13\\*|30\\*|03\\*|10\\*|01\\*|310\\*|301\\*|130\\*|103\\*|031\\*|013\\*)";
	public static final String NONE_BET_REGEX = "(\\-|#)";
	public static final String POSITIVE_FLOAT_REGEX = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
	public static final String PERCENT_FLOAT_REGEX = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))%$";
	public static final String DATE_REGEX = "^\\d{4}(\\-|\\/|\\_)\\d{1,2}-\\d{1,2}$";
	public static final String DAN_REGEX = "\\*";
	public static final String ILEGAL_FILE_REGEX = "~`!@#$%^&()[]{}?<>:,\"";
	public static final String LEFT_BRACKET = "\\[";
	public static final String RIGHT_BRACKET = "\\]";
	public static final String COMMON = "(:|：)";
	public static final String POSTCODE_REGEX = "([0-9]{3})+.([0-9]{4})+"; // 邮政编码的匹配模式
	public static final String PHONE_REGEX = "([0-9]{2})+-([0-9]{4})+-([0-9]{4})+"; // 固话的匹配模式
	public static final String MOBILE_REGEX = "([0-9]{3})+-([0-9]{4})+-([0-9]{4})+"; // 手机的匹配模式
	public static final String EMAIL_REGEX = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+[.]([a-zA-Z0-9_-])+"; // 电子邮件模式匹配
	public static final String IDENTIFY_REGEX = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";	// 身份证模式匹配
	
	// other regex
	public static final String POS_ZER_INT =  "^\\d+$";		//非负整数（正整数 + 0） 
	public static final String POS_INT = "^[0-9]*[1-9][0-9]*$";		//正整数 
	public static final String NON_POS_ZER_INT = "^((-\\d+)|(0+))$";	//非正整数（负整数 + 0） 
	public static final String NON_POS_INT = "^-[0-9]*[1-9][0-9]*$";	//负整数 
	public static final String INT = "^-?\\d+$";	//整数 
	public static final String POS_ZER_FLT = "^\\d+(\\.\\d+)?$";	//非负浮点数（正浮点数 + 0） 
	public static final String POS_FLT = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";	//正浮点数
	public static final String NON_POS_ZER_FLT = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";	//非正浮点数（负浮点数 + 0） 
	public static final String NON_POS_FLT = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";	//负浮点数 
	public static final String FLT = "^(-?\\d+)(\\.\\d+)?$";	//浮点数 
	public static final String AZ = "^[A-Za-z]+$";	//由26个英文字母组成的字符串 
	public static final String UPPER_CASE = "^[A-Z]+$";	//由26个英文字母的大写组成的字符串 
	public static final String LOWER_CASE = "^[a-z]+$";	//由26个英文字母的小写组成的字符串 
	public static final String AZ_NUMBER = "^[A-Za-z0-9]+$";	//由数字和26个英文字母组成的字符串 
	public static final String AZ_NUMBER_LINE = "^\\w+$";	//由数字、26个英文字母或者下划线组成的字符串 
	public static final String O_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";		//email地址 
	public static final String O_URL = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";	//url


}
