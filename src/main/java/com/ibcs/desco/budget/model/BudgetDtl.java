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
import com.ibcs.desco.inventory.model.ItemMaster;

/*
 * 
 * Author: Galeb
 * 
 */

@Entity
@Table(name = "Budget_Dtl")
public class BudgetDtl {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_dtl_seq")
	@SequenceGenerator(name = "budget_dtl_seq", sequenceName = "budget_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "item_master_id")
	private ItemMaster itemMaster;
	
	@ManyToOne
	@JoinColumn(name = "budget_mst_id")
	private BudgetMst budgetMst;
	
	@ManyToOne
	@JoinColumn(name = "all_look_up_id")
	private AllLookUp lookup;
	
	@Column(name = "qty")
	private Double qty;

	@Column(name = "rate")
	private Double rate;

	@Column(name = "total_amount")
	private Double totalAmount;

	@ManyToOne
	@JoinColumn(name = "cost_source_id")
	private AllLookUp costSource;
	
	@Column(name = "remarks")
	private String remarks;
	
	//extended budget as revision. default false.
	@Column(name = "is_rev_bgt")
	private boolean revBudget = false;

	//extended budget as revision approval status 
	@Column(name = "is_rev_approved")
	private boolean revBudgetApproved = false;

	//extended budget as revision approved by
	@Column(name = "rev_approved_by")
	private String revApprovedBy;
	
	//extended budget as revision approval date
	@Column(name = "rev_approve_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date revApprovedDate;
	
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

	
	public ItemMaster getItemMaster() {
		return itemMaster;
	}


	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}


	public BudgetMst getBudgetMst() {
		return budgetMst;
	}


	public void setBudgetMst(BudgetMst budgetMst) {
		this.budgetMst = budgetMst;
	}


	public AllLookUp getLookup() {
		return lookup;
	}


	public void setLookup(AllLookUp lookup) {
		this.lookup = lookup;
	}


	public Double getQty() {
		return qty;
	}


	public void setQty(Double qty) {
		this.qty = qty;
	}


	public Double getRate() {
		return rate;
	}


	public void setRate(Double rate) {
		this.rate = rate;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}


	


	public AllLookUp getCostSource() {
		return costSource;
	}


	public void setCostSource(AllLookUp costSource) {
		this.costSource = costSource;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	


	public boolean isRevBudget() {
		return revBudget;
	}


	public void setRevBudget(boolean revBudget) {
		this.revBudget = revBudget;
	}


	public boolean isRevBudgetApproved() {
		return revBudgetApproved;
	}


	public void setRevBudgetApproved(boolean revBudgetApproved) {
		this.revBudgetApproved = revBudgetApproved;
	}


	

	public String getRevApprovedBy() {
		return revApprovedBy;
	}


	public void setRevApprovedBy(String revApprovedBy) {
		this.revApprovedBy = revApprovedBy;
	}


	public Date getRevApprovedDate() {
		return revApprovedDate;
	}


	public void setRevApprovedDate(Date revApprovedDate) {
		this.revApprovedDate = revApprovedDate;
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


	public void setId(Integer id) {
		this.id = id;
	}


	public BudgetDtl(){}

	public BudgetDtl(Integer id, ItemMaster itemMaster, BudgetMst budgetMst,
			Double qty, Double rate, String remarks, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate) {
		super();
		this.id = id;
		this.itemMaster = itemMaster;
		this.budgetMst = budgetMst;
		this.qty = qty;
		this.rate = rate;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}


	@Override
	public String toString() {
		return "BudgetDtl [id=" + id + ", itemMaster=" + itemMaster
				+ ", budgetMst=" + budgetMst + ", lookup=" + lookup + ", qty="
				+ qty + ", rate=" + rate + ", totalAmount=" + totalAmount
				+ ", costSource=" + costSource + ", remarks=" + remarks
				+ ", reviceBudget=" + revBudget + ", revBudgetApproved="
				+ revBudgetApproved + ", revApprovedBy=" + revApprovedBy
				+ ", revApprovedDate=" + revApprovedDate + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}

	

	
	
	
}
