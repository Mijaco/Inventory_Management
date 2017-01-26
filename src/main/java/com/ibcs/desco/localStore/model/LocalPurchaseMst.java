package com.ibcs.desco.localStore.model;

import java.util.Date;
import java.util.List;

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

import com.ibcs.desco.admin.model.Departments;

@Entity
@Table(name = "LOCAL_PURCHASE_MST")
public class LocalPurchaseMst {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCAL_PURCHASE_MST_SEQ")
	@SequenceGenerator(name = "LOCAL_PURCHASE_MST_SEQ", sequenceName = "LOCAL_PURCHASE_MST_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "local_purchase_no")
	private String localPurchaseNo;
	
	@ManyToOne
	@JoinColumn(name = "department_mst", nullable = false)
	private Departments department;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "reference_no")
	private String referenceNo;

	@Column(name = "purchase_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date purchaseDate; 
	
	@Column(name="purchase_order_no")
	private String purchaseOrderNo;
	
	@Column(name = "supply_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date supplyDate; 

	@Column(name = "reference_doc")
	private String referenceDoc;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks")
	private String remarks;

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
	
	@Transient
	private List<LocalPurchaseDtl> localPurchaseDtl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getReferenceDoc() {
		return referenceDoc;
	}

	public void setReferenceDoc(String referenceDoc) {
		this.referenceDoc = referenceDoc;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getLocalPurchaseNo() {
		return localPurchaseNo;
	}

	public void setLocalPurchaseNo(String localPurchaseNo) {
		this.localPurchaseNo = localPurchaseNo;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}
	
	public Date getSupplyDate() {
		return supplyDate;
	}

	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public List<LocalPurchaseDtl> getLocalPurchaseDtl() {
		return localPurchaseDtl;
	}

	public void setLocalPurchaseDtl(List<LocalPurchaseDtl> localPurchaseDtl) {
		this.localPurchaseDtl = localPurchaseDtl;
	}

	public LocalPurchaseMst() {
	}

	public LocalPurchaseMst(Integer id, String localPurchaseNo,
			Departments department, String supplierName, String referenceNo,
			Date purchaseDate, String referenceDoc, boolean active,
			String remarks, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String purchaseOrderNo, Date supplyDate) {
		super();
		this.id = id;
		this.localPurchaseNo = localPurchaseNo;
		this.department = department;
		this.supplierName = supplierName;
		this.referenceNo = referenceNo;
		this.purchaseDate = purchaseDate;
		this.referenceDoc = referenceDoc;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.purchaseOrderNo = purchaseOrderNo;
		this.supplyDate = supplyDate;
	}


	@Override
	public String toString() {
		return "LocalPurchaseMst [id=" + id + ", localPurchaseNo="
				+ localPurchaseNo + ", department=" + department
				+ ", supplierName=" + supplierName + ", referenceNo="
				+ referenceNo + ", purchaseDate=" + purchaseDate
				+ ", purchaseOrderNo=" + purchaseOrderNo + ", supplyDate="
				+ supplyDate + ", referenceDoc=" + referenceDoc + ", active="
				+ active + ", remarks=" + remarks + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
}
