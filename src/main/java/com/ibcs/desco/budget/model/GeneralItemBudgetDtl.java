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
import javax.persistence.Transient;

@Entity
@Table(name = "Gen_Budget_Dtl")
public class GeneralItemBudgetDtl {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_budget_dtl_seq")
	@SequenceGenerator(name = "gen_budget_dtl_seq", sequenceName = "gen_budget_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "cost_center_id")
	private String costCenterId;
	
	@Transient
	private String costCenterName;
	
	@Column(name = "totalCost")
	private Double totalCost;

	@ManyToOne
	@JoinColumn(name = "gen_budgetMst_id")
	private GeneralItemBudgetMst budgetMst;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public GeneralItemBudgetMst getBudgetMst() {
		return budgetMst;
	}

	public void setBudgetMst(GeneralItemBudgetMst budgetMst) {
		this.budgetMst = budgetMst;
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
	
	
	
	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public GeneralItemBudgetDtl(){
		
	}

	public GeneralItemBudgetDtl(Integer id, String costCenterId,
			Double totalCost, GeneralItemBudgetMst budgetMst, String createdBy,
			Date createdDate) {
		super();
		this.id = id;
		this.costCenterId = costCenterId;
		this.totalCost = totalCost;
		this.budgetMst = budgetMst;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	
}
