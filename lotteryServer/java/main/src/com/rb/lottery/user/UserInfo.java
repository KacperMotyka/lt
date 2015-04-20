package com.rb.lottery.user;


import java.util.Date;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInfo implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6161870941256852125L;
	
	private String userid;
	private User user;
	private String cname;
	private String ename;
	private Long sex;
	private Long age;
	private Date birthday;
	private String birthplace;
	private String address;
	private String post;
	private String email;
	private String qq;
	private String phone;
	private String mobile;
	private String bloodtype;
	private Double weight;
	private Double height;

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(String userid, User user) {
		this.userid = userid;
		this.user = user;
	}

	/** full constructor */
	public UserInfo(String userid, User user, String cname, String ename,
			Long sex, Long age, Date birthday, String birthplace,
			String address, String post, String email, String qq, String phone,
			String mobile, String bloodtype, Double weight, Double height) {
		this.userid = userid;
		this.user = user;
		this.cname = cname;
		this.ename = ename;
		this.sex = sex;
		this.age = age;
		this.birthday = birthday;
		this.birthplace = birthplace;
		this.address = address;
		this.post = post;
		this.email = email;
		this.qq = qq;
		this.phone = phone;
		this.mobile = mobile;
		this.bloodtype = bloodtype;
		this.weight = weight;
		this.height = height;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getAge() {
		return this.age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthplace() {
		return this.birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBloodtype() {
		return this.bloodtype;
	}

	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

}