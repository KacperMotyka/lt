/**
 * @文件名称: MatchStatus.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-31 下午03:30:11
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;


/**
 * @类功能说明: 比赛状态类
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-12-31 下午03:30:11
 * @版本:       1.0.0
 */

public class MatchStatus implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 8629935711023810408L;
	
	private Long sid;
	private String name;
	
	public MatchStatus() {
		
	}
	
	public MatchStatus(Long sid) {
		this.sid = sid;
	}
	/**
	 * @return sid
	 */
	public Long getSid() {
		return sid;
	}
	/**
	 * @param sid sid
	 */
	public void setSid(Long sid) {
		this.sid = sid;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
