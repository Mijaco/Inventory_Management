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

@Entity
@Table(name = "Gen_Budget_Mst")
public class GeneralItemBudgetMst {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_budget_mst_seq")
	@SequenceGenerator(name = "gen_budget_mst_seq", sequenceName = "gen_budget_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_code_old")
	private String itemCodeOld;
	
	@Column(name = "item_name")
	private String itemName;
	
	@Column(name = "uom")
	private String uom;

	@Column(name = "qty")
	private Double qty;

	@Column(name = "unit_cost")
	private Double unitCost;

	@Column(name = "totalCost")
	private Double totalCost;
	
	@Column(name="ref_excel_file_path")
	private String refExcelFilePath;

	@ManyToOne
	@JoinColumn(name = "descoSession_id")
	private DescoSession descoSession;

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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCodeOld() {
		return itemCodeOld;
	}

	public void setItemCodeOld(String itemCodeOld) {
		this.itemCodeOld = itemCodeOld;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getRefExcelFilePath() {
		return refExcelFilePath;
	}

	public void setRefExcelFilePath(String refExcelFilePath) {
		this.refExcelFilePath = refExcelFilePath;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
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
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public GeneralItemBudgetMst(){
		
	}
	
	public GeneralItemBudgetMst(Integer id, String itemCode,
			String itemCodeOld, String itemName, String uom, Double qty, Double unitCost,
			Double totalCost, DescoSession descoSession, String remarks,
			String createdBy, Date createdDate, String refExcelFilePath) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.itemCodeOld = itemCodeOld;
		this.itemName = itemName;
		this.uom = uom;
		this.qty = qty;
		this.unitCost = unitCost;
		this.totalCost = totalCost;
		this.descoSession = descoSession;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.refExcelFilePath = refExcelFilePath;
	}
	
	

}
