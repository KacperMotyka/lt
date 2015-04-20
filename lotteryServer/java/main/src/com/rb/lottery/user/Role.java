package com.rb.lottery.user;

// default package

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 4608472610537256587L;
	// Fields

	private Long rid;
	private String rolename;
	private Long priority;

	private Set userrole = new HashSet(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(Long rid, String rolename, Long priority) {
		this.rid = rid;
		this.rolename = rolename;
		this.priority = priority;
	}

	/** full constructor */
	public Role(Long rid, String rolename, Long priority, Set ltUserRoles) {
		this.rid = rid;
		this.rolename = rolename;
		this.priority = priority;
		this.userrole = ltUserRoles;
	}

	// Property accessors

	public Long getRid() {
		return this.rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Set getUserrole() {
		return this.userrole;
	}

	public void setUserrole(Set ltUserRoles) {
		this.userrole = ltUserRoles;
	}

}