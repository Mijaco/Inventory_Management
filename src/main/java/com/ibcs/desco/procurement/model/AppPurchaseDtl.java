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

@Entity
@Table(name = "APP_PURCHASE_DTL")
public class AppPurchaseDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_purchase_dtl_seq")
	@SequenceGenerator(name = "app_purchase_dtl_seq", sequenceName = "app_purchase_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "proc_pack_dtl_id", nullable = false)
	private ProcurementPackageDtl procurementPackageDtl;

	@ManyToOne
	@JoinColumn(name = "app_purchase_mst_id", nullable = false)
	private AppPurchaseMst appPurchaseMst;

	@Column(name = "purchase_cost")
	private double purchaseCost = 0.0;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProcurementPackageDtl getProcurementPackageDtl() {
		return procurementPackageDtl;
	}

	public void setProcurementPackageDtl(
			ProcurementPackageDtl procurementPackageDtl) {
		this.procurementPackageDtl = procurementPackageDtl;
	}

	public AppPurchaseMst getAppPurchaseMst() {
		return appPurchaseMst;
	}

	public void setAppPurchaseMst(AppPurchaseMst appPurchaseMst) {
		this.appPurchaseMst = appPurchaseMst;
	}

	public double getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(double purchaseCost) {
		this.purchaseCost = purchaseCost;
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

	public AppPurchaseDtl(Integer id,
			ProcurementPackageDtl procurementPackageDtl,
			AppPurchaseMst appPurchaseMst, double purchaseCost, boolean active,
			String remarks, String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.procurementPackageDtl = procurementPackageDtl;
		this.appPurchaseMst = appPurchaseMst;
		this.purchaseCost = purchaseCost;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppPurchaseDtl() {

	}

}
