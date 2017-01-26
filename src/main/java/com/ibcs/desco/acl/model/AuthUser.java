package com.ibcs.desco.acl.model;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
@SuppressWarnings("serial")
public class AuthUser implements java.io.Serializable {

	private int id;

	private String password;

	private String email;

	private String name;

	private String userid;

	private int roleid;

	private boolean locked;

	private boolean active;

	private Integer loginCounter = 0;

	public AuthUser() {
	}

	public AuthUser(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthUser(int id, String password, String email, String userid,
			int roleid, String name, boolean locked, boolean active) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.userid = userid;
		this.roleid = roleid;
		this.name = name;
		this.locked = locked;
		this.active = active;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getRoleid() {
		return this.roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getLoginCounter() {
		return loginCounter;
	}

	public void setLoginCounter(Integer loginCounter) {
		this.loginCounter = loginCounter;
	}

}
