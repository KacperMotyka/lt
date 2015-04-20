/**
 * @文件名称: LoginFlag.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-30 下午12:47:15
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;


/**
 * @类功能说明: 登录标志
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-3-30 下午12:47:15
 * @版本:       1.0.0
 */

public class LoginFlag {

	// 0=登录成功 1=密码错误 2=用户不存在 3=其他（网络原因等）
	private int loginFlag = -1;
	
	public LoginFlag() {
		
	}
	
	public LoginFlag(int login) {
		loginFlag = login;
	}

	/**
	 * @return loginFlag
	 */
	public int getLoginFlag() {
		return loginFlag;
	}

	/**
	 * @param loginFlag loginFlag
	 */
	public void setLoginFlag(int loginFlag) {
		this.loginFlag = loginFlag;
	}
}
