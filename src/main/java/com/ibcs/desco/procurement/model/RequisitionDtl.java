package com.ibcs.desco.procurement.model;

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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Requisition_Dtl")
public class RequisitionDtl {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stu_dtl_id_Seq")
	@SequenceGenerator(name = "stu_dtl_id_Seq", sequenceName = "stu_dtl_id_Seq", allocationSize = 1, initialValue = 1)
	private int id;
	
	@Column(name = "item_Name")
	private String itemName;
	
	@Column(name = "item_Code")
	private String itemCode;
	
	@Column(name = "uom")
	private String uom;
	
	@Column(name = "quantity")
	private String quantity;
	
	@Column(name = "cost_Center")
	private String costCenter;
	
	@ManyToOne
    @JoinColumn(name="requisition_id")
	private RequisitionMst requisitionMst;
	
	@Transient
	private int requisitionId;
	

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_Date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createdDate;
	
	@Column(name = "modified_by")
	private String modifiedby;
	
	@Column(name = "modified_Date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date modifiedDate;
	
	@Column(name = "isActive")
	private boolean isActive;
	
	@Column(name = "remarks")
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public RequisitionMst getRequisitionMst() {
		return requisitionMst;
	}

	public void setRequisitionMst(RequisitionMst requisitionMst) {
		this.requisitionMst = requisitionMst;
	}

	public int getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(int requisitionId) {
		this.requisitionId = requisitionId;
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

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	
}
