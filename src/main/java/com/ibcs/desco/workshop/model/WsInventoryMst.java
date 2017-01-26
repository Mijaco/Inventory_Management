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
@Table(name = "WS_INVENTORY_MST")
public class WsInventoryMst {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WS_INV_MST_ID_SEQ")
	@SequenceGenerator(name = "WS_INV_MST_ID_SEQ", sequenceName = "WS_INV_MST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "type_of_work")
	private String typeOfWork;

	@Column(name = "ws_inventory_no")
	private String wsInventoryNo;

	@Column(name = "inventory_date")
	private Date inventoryDate;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "transformer_type")
	private String transformerType;

	@Column(name = "transformer_serial_no")
	private String transformerSerialNo;

	@Column(name = "manufactured_name")
	private String manufacturedName;

	@Column(name = "manufactured_year")
	private String manufacturedYear;

	@Column(name = "received_from")
	private String receivedFrom;

	@Column(name = "last_repair_date")
	private Date lastRepairDate;

	@Column(name = "received_date")
	private Date receivedDate;
//----------------------------
	@Column(name = "megger_result_ht_lt")
	private String meggerResultHtLt;

	@Column(name = "megger_result_ht_e")
	private String meggerResultHtE;

	@Column(name = "megger_result_lt_e")
	private String meggerResultLtE;

	@Column(name = "megger_result_lt_lt_ab")
	private String meggerResultLtLtAb;

	@Column(name = "megger_result_lt_lt_bc")
	private String meggerResultLtLtBc;

	@Column(name = "megger_result_lt_lt_ca")
	private String meggerResultLtLtCa;

	@Column(name = "megger_result_ht_ht_ab")
	private String meggerResultHtHtAb;

	@Column(name = "megger_result_ht_ht_bc")
	private String meggerResultHtHtBc;

	@Column(name = "megger_result_ht_ht_ca")
	private String meggerResultHtHtCa;

	@Column(name = "is_painting_required")
	private String isPaintingRequired;
	
//-------------------------------
	@Transient
	private int transformerRegisterId;

	@ManyToOne
	@JoinColumn(name = "transformer_register_id")
	private TransformerRegister transformerRegister;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "isApproved")
	private boolean approved = false;

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

	public String getTypeOfWork() {
		return typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(String transformerType) {
		this.transformerType = transformerType;
	}

	public String getTransformerSerialNo() {
		return transformerSerialNo;
	}

	public void setTransformerSerialNo(String transformerSerialNo) {
		this.transformerSerialNo = transformerSerialNo;
	}

	public String getManufacturedName() {
		return manufacturedName;
	}

	public void setManufacturedName(String manufacturedName) {
		this.manufacturedName = manufacturedName;
	}

	public String getManufacturedYear() {
		return manufacturedYear;
	}

	public void setManufacturedYear(String manufacturedYear) {
		this.manufacturedYear = manufacturedYear;
	}

	public int getTransformerRegisterId() {
		return transformerRegisterId;
	}

	public void setTransformerRegisterId(int transformerRegisterId) {
		this.transformerRegisterId = transformerRegisterId;
	}

	public TransformerRegister getTransformerRegister() {
		return transformerRegister;
	}

	public void setTransformerRegister(TransformerRegister transformerRegister) {
		this.transformerRegister = transformerRegister;
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getWsInventoryNo() {
		return wsInventoryNo;
	}

	public void setWsInventoryNo(String wsInventoryNo) {
		this.wsInventoryNo = wsInventoryNo;
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public Date getLastRepairDate() {
		return lastRepairDate;
	}

	public void setLastRepairDate(Date lastRepairDate) {
		this.lastRepairDate = lastRepairDate;
	}

	public String getMeggerResultHtLt() {
		return meggerResultHtLt;
	}

	public void setMeggerResultHtLt(String meggerResultHtLt) {
		this.meggerResultHtLt = meggerResultHtLt;
	}

	public String getMeggerResultHtE() {
		return meggerResultHtE;
	}

	public void setMeggerResultHtE(String meggerResultHtE) {
		this.meggerResultHtE = meggerResultHtE;
	}

	public String getMeggerResultLtE() {
		return meggerResultLtE;
	}

	public void setMeggerResultLtE(String meggerResultLtE) {
		this.meggerResultLtE = meggerResultLtE;
	}

	public String getMeggerResultLtLtAb() {
		return meggerResultLtLtAb;
	}

	public void setMeggerResultLtLtAb(String meggerResultLtLtAb) {
		this.meggerResultLtLtAb = meggerResultLtLtAb;
	}

	public String getMeggerResultLtLtBc() {
		return meggerResultLtLtBc;
	}

	public void setMeggerResultLtLtBc(String meggerResultLtLtBc) {
		this.meggerResultLtLtBc = meggerResultLtLtBc;
	}

	public String getMeggerResultLtLtCa() {
		return meggerResultLtLtCa;
	}

	public void setMeggerResultLtLtCa(String meggerResultLtLtCa) {
		this.meggerResultLtLtCa = meggerResultLtLtCa;
	}

	public String getMeggerResultHtHtAb() {
		return meggerResultHtHtAb;
	}

	public void setMeggerResultHtHtAb(String meggerResultHtHtAb) {
		this.meggerResultHtHtAb = meggerResultHtHtAb;
	}

	public String getMeggerResultHtHtBc() {
		return meggerResultHtHtBc;
	}

	public void setMeggerResultHtHtBc(String meggerResultHtHtBc) {
		this.meggerResultHtHtBc = meggerResultHtHtBc;
	}

	public String getMeggerResultHtHtCa() {
		return meggerResultHtHtCa;
	}

	public void setMeggerResultHtHtCa(String meggerResultHtHtCa) {
		this.meggerResultHtHtCa = meggerResultHtHtCa;
	}

	public String getIsPaintingRequired() {
		return isPaintingRequired;
	}

	public void setIsPaintingRequired(String isPaintingRequired) {
		this.isPaintingRequired = isPaintingRequired;
	}

}
