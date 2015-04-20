package com.rb.lottery.user;


/**
 * UserSfQa entity. @author MyEclipse Persistence Tools
 */

public class UserSfQa implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 8473686710712267334L;
	
	private Long qaid;
	private User user;
	private String question;
	private String answer;

	// Constructors

	/** default constructor */
	public UserSfQa() {
	}

	/** full constructor */
	public UserSfQa(User user, String question, String answer) {
		this.user = user;
		this.question = question;
		this.answer = answer;
	}

	// Property accessors

	public Long getQaid() {
		return this.qaid;
	}

	public void setQaid(Long qaid) {
		this.qaid = qaid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}