package com.ibcs.desco.workshop.model;

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
@Table(name = "XFORMER_CLOSEOUT_DTL")
public class TransformerCloseOutDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "X_CLOSEOUT_DTL_ID_SEQ")
	@SequenceGenerator(name = "X_CLOSEOUT_DTL_ID_SEQ", sequenceName = "X_CLOSEOUT_DTL_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Transient
	private int closeoutMstId;

	@ManyToOne
	@JoinColumn(name = "MST_ID")
	private TransformerCloseOutMst transformerCloseOutMst;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "balance")
	private double balance=0.0;

	@Column(name = "RCV_PUR_CASH_QTY")
	private double rcvPurCashQty=0.0;

	@Column(name = "RCV_FROM_STORE_QTY")
	private double rcvFromStoreQty=0.0;

	@Column(name = "TOTAL_QTY")
	private double totalQty=0.0;

	@Column(name = "MATERIALS_CONSUME")
	private double materialsConsume=0.0;

	@Column(name = "MATERIALS_RETURN")
	private double materialsReturn=0.0;

	@Column(name = "ACTUAL_RETURN")
	private double actualReturn=0.0;

	@Column(name = "QTY_SHORT")
	private double qtyShort=0.0;

	@Column(name = "QTY_EXCESS")
	private double qtyExcess=0.0;

	@Column(name = "IS_ACTIVE")
	private boolean active = true;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public double getMaterialsConsume() {
		return materialsConsume;
	}

	public void setMaterialsConsume(double materialsConsume) {
		this.materialsConsume = materialsConsume;
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

	public int getCloseoutMstId() {
		return closeoutMstId;
	}

	public void setCloseoutMstId(int closeoutMstId) {
		this.closeoutMstId = closeoutMstId;
	}

	public TransformerCloseOutMst getTransformerCloseOutMst() {
		return transformerCloseOutMst;
	}

	public void setTransformerCloseOutMst(TransformerCloseOutMst transformerCloseOutMst) {
		this.transformerCloseOutMst = transformerCloseOutMst;
	}

	public double getRcvPurCashQty() {
		return rcvPurCashQty;
	}

	public void setRcvPurCashQty(double rcvPurCashQty) {
		this.rcvPurCashQty = rcvPurCashQty;
	}

	public double getRcvFromStoreQty() {
		return rcvFromStoreQty;
	}

	public void setRcvFromStoreQty(double rcvFromStoreQty) {
		this.rcvFromStoreQty = rcvFromStoreQty;
	}

	public double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(double totalQty) {
		this.totalQty = totalQty;
	}

	public double getMaterialsReturn() {
		return materialsReturn;
	}

	public void setMaterialsReturn(double materialsReturn) {
		this.materialsReturn = materialsReturn;
	}

	public double getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(double actualReturn) {
		this.actualReturn = actualReturn;
	}

	public double getQtyShort() {
		return qtyShort;
	}

	public void setQtyShort(double qtyShort) {
		this.qtyShort = qtyShort;
	}

	public double getQtyExcess() {
		return qtyExcess;
	}

	public void setQtyExcess(double qtyExcess) {
		this.qtyExcess = qtyExcess;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
