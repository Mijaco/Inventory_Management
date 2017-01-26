package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ibcs.desco.cs.model.CondemnMst;

public class AuctionCommitteeDto {
	
	private CondemnMst condemnMst;	
	
	private String committeeName;
	
	private String memoNo;

	private String description;

	private String docPath;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date ccDate;
	
	private String condemnMstId;
	
	private List<String> userId;
	
	private List<String> aucCmtId;
	
	private List<String> memberType;

	private String remarks;

	public CondemnMst getCondemnMst() {
		return condemnMst;
	}

	public void setCondemnMst(CondemnMst condemnMst) {
		this.condemnMst = condemnMst;
	}

	public String getCommitteeName() {
		return committeeName;
	}

	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}

	public String getMemoNo() {
		return memoNo;
	}

	public void setMemoNo(String memoNo) {
		this.memoNo = memoNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public Date getCcDate() {
		return ccDate;
	}

	public void setCcDate(Date ccDate) {
		this.ccDate = ccDate;
	}

	public String getCondemnMstId() {
		return condemnMstId;
	}

	public void setCondemnMstId(String condemnMstId) {
		this.condemnMstId = condemnMstId;
	}

	public List<String> getUserId() {
		return userId;
	}
	
	

	public List<String> getAucCmtId() {
		return aucCmtId;
	}

	public void setAucCmtId(List<String> aucCmtId) {
		this.aucCmtId = aucCmtId;
	}

	public void setUserId(List<String> userId) {
		this.userId = userId;
	}

	public List<String> getMemberType() {
		return memberType;
	}

	public void setMemberType(List<String> memberType) {
		this.memberType = memberType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	

}
