package com.ibcs.desco.test.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


//created by galeb as test
@Entity(name = "AFM_CHART_OF_ACCOUNTS_TEST")
public class AfmChartOfAccountsTest {
	

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "afm_crt_o_accunts_id_Seq")
	@SequenceGenerator(name = "afm_crt_o_accunts_id_Seq", sequenceName = "afm_crt_o_accunts_id_Seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "ACCOUNT_HEAD_ID")
	private Long accountHeadId;
	
	@Column(name = "ACCOUNT_HEAD_NAME", length=100)
	private String accountHeadName;
	
	@Column(name = "PARENT_ACCOUNT_HEAD_ID")
	private Long parentAccountHeadId;
	
	@Column(name = "COMPANY_ID", length=10)
	private String companyId;

	@Column(name = "IS_SUBSIDIARY")
	private boolean isSubsidiary;
	
	@Column(name = "ACCOUNT_ID_TYPE", length=30)
	private String AccountIdType;
	
	@Column(name = "ACCOUNT_HEAD_TYPE", length=100)
	private String AccountHeadType;
	
	@Column(name = "IS_AGREEMENT")
	private Boolean isAgreement = false;
	
	@Column(name = "IS_INTEREST")
	private Boolean isInterest = false;
	
	@Column(name = "PL_BL_HEAD_ID", length=100)
	private String plBlHeadId;

	@Column(name = "NOTE_NO", length=30)
	private String noteNo;
	
	@Column(name = "ACCOUNT_CODE")
	private Long accountCode;
	
	@Column(name = "DEP_RATIO_OLD")
	private Long depRationOld;	
	
	@Column(name = "IS_BANK_CASH")
	private boolean isBankCash;

	@Column(name = "IS_BUDGET_HEAD")
	private int isBudgetHead;	
	
	@Column(name = "DEP_RATIO")
	private int depRatio;	
	
	@Column(name = "SECTION", length=50)
	private String section;
	
	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	
	@Column(name = "OPENING_DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date openingDate;

	@Column(name = "OPENING_BALANCE")
	private Double openingBalance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAccountHeadId() {
		return accountHeadId;
	}

	public void setAccountHeadId(Long accountHeadId) {
		this.accountHeadId = accountHeadId;
	}

	public String getAccountHeadName() {
		return accountHeadName;
	}

	public void setAccountHeadName(String accountHeadName) {
		this.accountHeadName = accountHeadName;
	}

	public Long getParentAccountHeadId() {
		return parentAccountHeadId;
	}

	public void setParentAccountHeadId(Long parentAccountHeadId) {
		this.parentAccountHeadId = parentAccountHeadId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsSubsidiary() {
		return isSubsidiary;
	}

	public void setIsSubsidiary(Boolean isSubsidiary) {
		this.isSubsidiary = isSubsidiary;
	}

	public String getAccountIdType() {
		return AccountIdType;
	}

	public void setAccountIdType(String accountIdType) {
		AccountIdType = accountIdType;
	}

	public String getAccountHeadType() {
		return AccountHeadType;
	}

	public void setAccountHeadType(String accountHeadType) {
		AccountHeadType = accountHeadType;
	}

	public Boolean getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(Boolean isAgreement) {
		this.isAgreement = isAgreement;
	}

	public Boolean getIsInterest() {
		return isInterest;
	}

	public void setIsInterest(Boolean isInterest) {
		this.isInterest = isInterest;
	}

	public String getPlBlHeadId() {
		return plBlHeadId;
	}

	public void setPlBlHeadId(String plBlHeadId) {
		this.plBlHeadId = plBlHeadId;
	}

	public String getNoteNo() {
		return noteNo;
	}

	public void setNoteNo(String noteNo) {
		this.noteNo = noteNo;
	}

	public Long getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(Long accountCode) {
		this.accountCode = accountCode;
	}

	public Long getDepRationOld() {
		return depRationOld;
	}

	public void setDepRationOld(Long depRationOld) {
		this.depRationOld = depRationOld;
	}

	public Boolean getIsBankCash() {
		return isBankCash;
	}

	public void setIsBankCash(Boolean isBankCash) {
		this.isBankCash = isBankCash;
	}

	public int getIsBudgetHead() {
		return isBudgetHead;
	}

	public void setIsBudgetHead(int isBudgetHead) {
		this.isBudgetHead = isBudgetHead;
	}

	public int getDepRatio() {
		return depRatio;
	}

	public void setDepRatio(int depRatio) {
		this.depRatio = depRatio;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public AfmChartOfAccountsTest(){		
	}
	
	public AfmChartOfAccountsTest(Integer id, Long accountHeadId,
			String accountHeadName, Long parentAccountHeadId, String companyId,
			boolean isSubsidiary, String accountIdType, String accountHeadType,
			Boolean isAgreement, Boolean isInterest, String plBlHeadId,
			String noteNo, Long accountCode, Long depRationOld,
			boolean isBankCash, int isBudgetHead, int depRatio, String section,
			boolean isActive, Date openingDate, Double openingBalance) {
		super();
		this.id = id;
		this.accountHeadId = accountHeadId;
		this.accountHeadName = accountHeadName;
		this.parentAccountHeadId = parentAccountHeadId;
		this.companyId = companyId;
		this.isSubsidiary = isSubsidiary;
		AccountIdType = accountIdType;
		AccountHeadType = accountHeadType;
		this.isAgreement = isAgreement;
		this.isInterest = isInterest;
		this.plBlHeadId = plBlHeadId;
		this.noteNo = noteNo;
		this.accountCode = accountCode;
		this.depRationOld = depRationOld;
		this.isBankCash = isBankCash;
		this.isBudgetHead = isBudgetHead;
		this.depRatio = depRatio;
		this.section = section;
		this.isActive = isActive;
		this.openingDate = openingDate;
		this.openingBalance = openingBalance;
	}

	@Override
	public String toString() {
		return "AfmChartOfAccountsTest [id=" + id + ", accountHeadId="
				+ accountHeadId + ", accountHeadName=" + accountHeadName
				+ ", parentAccountHeadId=" + parentAccountHeadId
				+ ", companyId=" + companyId + ", isSubsidiary=" + isSubsidiary
				+ ", AccountIdType=" + AccountIdType + ", AccountHeadType="
				+ AccountHeadType + ", isAgreement=" + isAgreement
				+ ", isInterest=" + isInterest + ", plBlHeadId=" + plBlHeadId
				+ ", noteNo=" + noteNo + ", accountCode=" + accountCode
				+ ", depRationOld=" + depRationOld + ", isBankCash="
				+ isBankCash + ", isBudgetHead=" + isBudgetHead + ", depRatio="
				+ depRatio + ", section=" + section + ", isActive=" + isActive
				+ ", openingDate=" + openingDate + ", openingBalance="
				+ openingBalance + "]";
	}
	

	

}
