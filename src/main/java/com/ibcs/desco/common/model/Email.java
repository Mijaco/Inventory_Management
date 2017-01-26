package com.ibcs.desco.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Email_Information")
public class Email {

	@Id
	@Column(name = "emailNo")
	private int emailNo;
	public int getEmailNo() {
		return emailNo;
	}

	public void setEmailNo(int emailNo) {
		this.emailNo = emailNo;
	}

	@Column(name = "recipint")
	private String recipint;
	@Column(name = "subject")
	private String subject;
	@Column(name = "body_of_email")
	private String writtenInformation;

	public Email() {
	}

	public String getRecipint() {
		return recipint;
	}

	public void setRecipint(String recipint) {
		this.recipint = recipint;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWrittenInformation() {
		return writtenInformation;
	}

	public void setWrittenInformation(String writtenInformation) {
		this.writtenInformation = writtenInformation;
	}

	@Override
	public String toString() {
		return "Email [emailNo=" + emailNo + ", recipint=" + recipint + ", subject=" + subject + ", writtenInformation="
				+ writtenInformation + "]";
	}


}
