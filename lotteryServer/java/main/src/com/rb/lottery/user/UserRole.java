package com.rb.lottery.user;

/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */

public class UserRole  implements java.io.Serializable {


    // Fields    

     /**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 7017545679985146625L;
	
	private Long urid;
    private User user;
    private Role role;


    // Constructors

    /** default constructor */
    public UserRole() {
    }

    
    /** full constructor */
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

   
    // Property accessors

    public Long getUrid() {
        return this.urid;
    }
    
    public void setUrid(Long urid) {
        this.urid = urid;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
   








}