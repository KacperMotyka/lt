/**
 * @文件名称: User.java
 * @类路径:   com.rb.lottery.user
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-21 下午01:13:15
 * @版本:     1.0.0
 */
package com.rb.lottery.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @类功能说明: 
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-2-21 下午01:13:15
 * @版本:       1.0.0
 */

public class User implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6479135000855255328L;
	
	private String userid;
	private String username;
	private String password;
	private String paypassword;
	private Long type;
	private Long status;
	private Date createtime;
	private Date lastupdate;
	private Long cpdfailtime;
	
	Set userrole = new HashSet(0);
	Set userinfo = new HashSet(0);
	Set safeqa = new HashSet(0);
	
	public User() {
		type = Long.valueOf(2);		// 一般用户
		status = Long.valueOf(11);	// 未激活
		createtime = new Date();
		cpdfailtime = Long.valueOf(0);
	}
	/**
	 * @return userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return paypassword
	 */
	public String getPaypassword() {
		return paypassword;
	}
	/**
	 * @param paypassword paypassword
	 */
	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}
	/**
	 * @return type
	 */
	public Long getType() {
		return type;
	}
	/**
	 * @param type type
	 */
	public void setType(Long type) {
		this.type = type;
	}
	/**
	 * @return status
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * @param status status
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return userrole
	 */
	public Set getUserrole() {
		return userrole;
	}
	/**
	 * @param userrole userrole
	 */
	public void setUserrole(Set userrole) {
		this.userrole = userrole;
	}
	/**
	 * @return userinfo
	 */
	public Set getUserinfo() {
		return userinfo;
	}
	/**
	 * @param userinfo userinfo
	 */
	public void setUserinfo(Set userinfo) {
		this.userinfo = userinfo;
	}
	/**
	 * @return safeqa
	 */
	public Set getSafeqa() {
		return safeqa;
	}
	/**
	 * @param safeqa safeqa
	 */
	public void setSafeqa(Set safeqa) {
		this.safeqa = safeqa;
	}
	/**
	 * @return createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime createtime
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return lastupdate
	 */
	public Date getLastupdate() {
		return lastupdate;
	}
	/**
	 * @param lastupdate lastupdate
	 */
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	/**
	 * @return cpdfailtime
	 */
	public Long getCpdfailtime() {
		return cpdfailtime;
	}
	/**
	 * @param cpdfailtime cpdfailtime
	 */
	public void setCpdfailtime(Long cpdfailtime) {
		this.cpdfailtime = cpdfailtime;
	}
}
