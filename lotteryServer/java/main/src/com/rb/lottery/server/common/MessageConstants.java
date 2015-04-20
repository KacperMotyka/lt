/**
 * @文件名称: MessageConstants.java
 * @类路径:   com.rb.lottery.common
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-18 上午10:57:52
 * @版本:     1.0.0
 */
package com.rb.lottery.server.common;


/**
 * @类功能说明: 系统消息常量配置
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-18 上午10:57:52
 * @版本: 1.0.0
 */

public interface MessageConstants {

	// Messages
	public static final String INFOMATION = "信息提示";
	public static final String SELECT_PASSWAY = "选择过关方式";
	public static final String OUTOFDATE_INFO = "上次保存的期号与当前期号不同,是否更改当前期号?";
	public static final String NEW_PROJECT_INFO = "是否新建一个投注方案?";
	public static final String FILE_EXIST_INFO = "文件已存在，是否覆盖原文件?";
	public static final String ILEGAL_FILE_MESSAGE = "文件名不能包含下列任何字符之一：  \\ / : * ? \" < > |";
	public static final String NOT_CURRNET_TYPE = "投注方案非当前投注类型.";
	public static final String NOT_CURRNET_QIHAO = "投注方案非当前期号.";
	public static final String NO_MATCH_SELECT = "没有选择场次,无法投注.";
	public static final String NO_PASSWAY = "没有符合条件的过关方式,无法单式拆分.";
	public static final String NO_PASSWAYBET = "没有符合条件的过关方式,无法投注.";
	public static final String SINGLE_SUCCESS = "单式号码投注成功！\n共生成注数：";
	public static final String MULTI_SUCCESS = "复式号码投注成功！\n共生成注数：";
	public static final String PAGE_ERROR = "页码错误！";
	public static final String SAVE_PAROJECT_SUCCESS = "投注方案文件保存成功.";
	public static final String SAVE_RESULT_SUCCESS = "投注结果文件保存成功";
	public static final String BASKET_EXIST_INFO = "号码篮已存在, 添加新号码篮将覆盖原来号码篮,是否继续?";
	public static final String EXIT_SAVE_INFO = "退出系统将丢失未保存数据, 是否保存后退出?";
	public static final String EXIT_SAVING = "数据保存中...";
	public static final String TYPE_CHANGE_INFO = "投注类型切换,将丢失未保存信息,是否保存?";
	public static final String QIHAO_CHANGE_INFO = "期号切换,将丢失未保存信息,是否保存?";
	public static final String NO_CURRENT_PERIOD = "抱歉,暂时没有当前期数据!";
	public static final String SFCKJ_UPDATE_FAIL = "胜负彩开奖数据文件更新失败!";
	public static final String CONNECT_ERROR = "数据更新失败，请检查网络连接!";
	public static final String NUMBER_ERROR = "请输入正确数字!";
	public static final String INVALID_QIHAO_ERROR = "加载xml失败";
	public static final String UNKNOWN_ERROR = "未知错误";
	public static final String NO_DATA = "期数据未找到";
	public static final String ACCOUNT_INFO = "填写帐号信息";
	public static final String USER_INFO = "填写用户信息(选填)";
	public static final String ENTER_USERNAME = "用户名不能为空";
	public static final String EXIST_USERNAME = "用户已存在";
	public static final String AVAILABLE = "√";
	public static final String DIFFERENT_PASSWORD = "二次密码不一致";
	public static final String OBEY = "已阅读并同意服务协议";
	public static final String INVALID_CHINESE = "非中文";
	public static final String ENTER_CNAME = "中文姓名不能为空";
	public static final String INVALID_NUMBER = "无效数字";
	public static final String REGISTER_SUCCESS = "注册成功!";
	public static final String LOGOUT_CONFIRM = "确认退出?";
	public static final String REGISTER_CONFIRM = "确认注册信息?";
	public static final String ALREADY_LOGIN = "用户已登录，确认切换用户?";
	public static final String ENTER_OLD_PWD = "原始密码不能为空";
	public static final String NO_LOGIN = "用户未登录";
	public static final String CHANGEPWD_CONFIRM = "确认修改密码?";
	public static final String CHANGEPPWD_CONFIRM = "确认设置支付密码?";
	public static final String CHOOSE_CRYPTD = "填写密保问题信息(至少填写1个问题)";
	public static final String ENTER_ANSWER = "回答不能为空";
	public static final String CPD_CONFIRM = "密保问题只能通过申诉修改,确认提交密保问题答案?";
	public static final String ENTER_RECHARGE_AMOUNT = "充值金额不能为空";
	public static final String RECHARGE_CONFIRM = "确认充值?";
	public static final String INVALID_MULTI =  "无效倍数!";
	public static final String INVALID_UPLOAD = "无效方案!";
	public static final String TO_SYSTEMTRAY = "是否到系统托盘或直接退出?";
}
