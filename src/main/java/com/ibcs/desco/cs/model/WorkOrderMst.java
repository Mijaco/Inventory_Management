package com.ibcs.desco.cs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "WORK_ORDER_MST")
public class WorkOrderMst {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wk_order_mst_seq")
	@SequenceGenerator(name = "wk_order_mst_seq", sequenceName = "wk_order_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "work_order_no")
	private String workOrderNo;

	@Column(name = "contract_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date contractDate;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "khath_id")
	private Integer khathId;

	@Column(name = "is_psi")
	private boolean psi = false;

	@Column(name = "is_pli")
	private boolean pli = false;

	@Column(name = "reference_document")
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
	private String khathName;
	
	public WorkOrderMst(){
		
	}
	
	public WorkOrderMst(Integer id, String workOrderNo, Date contractDate, String supplierName, Integer khathId,
			boolean psi, boolean pli, String referenceDoc, boolean active, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			String remarks){
		this.id = id;
		this.workOrderNo = workOrderNo;
		this.contractDate = contractDate;
		this.pli = pli;
		this.psi = psi;
		this.supplierName = supplierName;
		this.khathId = khathId;
		this.referenceDoc = referenceDoc;
		this.active = active;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.remarks = remarks;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public boolean isPsi() {
		return psi;
	}

	public void setPsi(boolean psi) {
		this.psi = psi;
	}

	public boolean isPli() {
		return pli;
	}

	public void setPli(boolean pli) {
		this.pli = pli;
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

	public String getReferenceDoc() {
		return referenceDoc;
	}

	public void setReferenceDoc(String referenceDoc) {
		this.referenceDoc = referenceDoc;
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
	
	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	/*
	 * @Column(name = "chalan_no") private String chalanNo;
	 * 
	 * @Column(name = "invoice_date")
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @DateTimeFormat(pattern = "yyyy-mm-dd") private Date invoiceDate;
	 * 
	 * @Column(name = "landing_date")
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @DateTimeFormat(pattern = "yyyy-mm-dd") private Date landingDate;
	 * 
	 * @Column(name = "delivery_date")
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @DateTimeFormat(pattern = "yyyy-mm-dd") private Date deliveryDate;
	 * 
	 * @Column(name = "bill_of_landing") private String billOfLanding;
	 * 
	 * @Column(name = "attachment_3") private String attachment3;
	 * 
	 * @Column(name = "attachment_psi") private String attachmentPsi;
	 * 
	 * @Column(name = "attachment_pli") private String attachmentPli;
	 */

}
