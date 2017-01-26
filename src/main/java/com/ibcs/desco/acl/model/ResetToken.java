package com.ibcs.desco.acl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "RESET_TOKEN")
public class ResetToken {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reset_token_id_Seq")
	@SequenceGenerator(name = "reset_token_id_Seq", sequenceName = "reset_token_id_Seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "userId", nullable = false)
	private String userId;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "expired_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;

	@Column(name = "status", nullable = false)
	private String status;
	
	@Transient
	@Column(name="password")
	private String password;
	
	@Transient
	@Column(name="opassword")
	private String opassword;

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getStatus() {
		return status;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpassword() {
		return opassword;
	}

	public void setOpassword(String opassword) {
		this.opassword = opassword;
	}

	public Integer getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public String getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

