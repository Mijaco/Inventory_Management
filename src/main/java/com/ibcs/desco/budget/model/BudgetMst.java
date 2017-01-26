package com.ibcs.desco.budget.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.inventory.model.AllLookUp;

/*
 * 
 * Author: Galeb 
 * 
 */

@Entity
@Table(name = "Budget_Mst")
public class BudgetMst {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_budget_mst_seq")
	@SequenceGenerator(name = "gen_budget_mst_seq", sequenceName = "gen_budget_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@ManyToOne
	@JoinColumn(name = "descoSession_id")
	private DescoSession descoSession;
	
	//previous requirement
	//budgetType will be dynamic. It should come from AllLookUp.
	/*@ManyToOne
	@JoinColumn(name = "budget_type_id")
	private AllLookUp budgetType;*/

	@Column(name = "project_fund_src")
	private String projectFundSrc;
	
	@Column(name = "budget_status")
	private String budgetStatus;	
	
	//budgetType is fix. only type is 'Capital Expenditure'
	@Column(name = "budget_type")
	private String budgetType;	
	
	

	@Column(name = "remarks")
	private String remarks;
	
	//budget revision status. values (null (no extensiton), pending, approved)
	@Column(name = "bgt_rev_status")
	private String bgtRevStatus;


	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "approved_by")
	private String approvedBy;	

	@Column(name = "approved_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "total_amount")
	private Double totalAmount;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
	}



	public String getProjectFundSrc() {
		return projectFundSrc;
	}

	public void setProjectFundSrc(String projectFundSrc) {
		this.projectFundSrc = projectFundSrc;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	

	public String getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	
	public String getBgtRevStatus() {
		return bgtRevStatus;
	}

	public void setBgtRevStatus(String bgtRevStatus) {
		this.bgtRevStatus = bgtRevStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public BudgetMst(){}

	public BudgetMst(Integer id, DescoSession descoSession,
			String projectFundSrc, String budgetStatus, String budgetType,
			String remarks, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, Double totalAmount) {
		super();
		this.id = id;
		this.descoSession = descoSession;
		this.projectFundSrc = projectFundSrc;
		this.budgetStatus = budgetStatus;
		this.budgetType = budgetType;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "BudgetMst [id=" + id + ", descoSession=" + descoSession
				+ ", projectFundSrc=" + projectFundSrc + ", budgetStatus="
				+ budgetStatus + ", budgetType=" + budgetType + ", remarks="
				+ remarks + ", bgtRevStatus=" + bgtRevStatus + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate="
				+ approvedDate + ", totalAmount=" + totalAmount + "]";
	}

	

	

	
	
	
	
}
