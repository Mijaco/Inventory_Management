package com.ibcs.desco.inventory.model;

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

import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.inventory.bean.ProjectQtyBean;

@Entity
@Table(name = "physical_inventory")
public class PhysicalStoreInventory {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physical_inventory_seq")
	@SequenceGenerator(name = "physical_inventory_seq", sequenceName = "physical_inventory_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "inventory_Date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inventoryDate;	

	@Column(name = "item_Code")
	private String itemCode;

	@Column(name = "description")
	private String description;

	@Column(name = "uom")
	private String uom;

	@ManyToOne
	@JoinColumn(name = "desco_project_id", nullable = false)
	private DescoKhath descoKhath;

	@Transient
	private Integer projectId;
	
	@Transient
	private String dateText;

	@Column(name = "project_Name")
	private String projectName;
	
	@Transient
	private List<ProjectQtyBean> projectQtyList;

	@Column(name = "total_qty")
	private Double totalQty = 0.0;

	@Column(name = "counted_qty")
	private Double countedQty = 0.0;

	@Column(name = "counted_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date countedDate;

	@Column(name = "shortage_qty")
	private Double shortageQty = 0.0;

	@Column(name = "shortage_value")
	private Double shortageValue = 0.0;

	@Column(name = "surplus_qty")
	private Double surplusQty = 0.0;

	@Column(name = "surplus_value")
	private Double surplusValue = 0.0;

	@Column(name = "percentage")
	private Double percentage = 0.0;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "flag")
	private String flag;

	@Column(name = "status")
	private String status;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "isActive")
	private boolean isActive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public DescoKhath getDescoKhath() {
		return descoKhath;
	}

	public void setDescoKhath(DescoKhath descoKhath) {
		this.descoKhath = descoKhath;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public Double getCountedQty() {
		return countedQty;
	}

	public void setCountedQty(Double countedQty) {
		this.countedQty = countedQty;
	}

	public Date getCountedDate() {
		return countedDate;
	}

	public void setCountedDate(Date countedDate) {
		this.countedDate = countedDate;
	}

	public Double getShortageQty() {
		return shortageQty;
	}

	public void setShortageQty(Double shortageQty) {
		this.shortageQty = shortageQty;
	}

	public Double getShortageValue() {
		return shortageValue;
	}

	public void setShortageValue(Double shortageValue) {
		this.shortageValue = shortageValue;
	}

	public Double getSurplusQty() {
		return surplusQty;
	}

	public void setSurplusQty(Double surplusQty) {
		this.surplusQty = surplusQty;
	}

	public Double getSurplusValue() {
		return surplusValue;
	}

	public void setSurplusValue(Double surplusValue) {
		this.surplusValue = surplusValue;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<ProjectQtyBean> getProjectQtyList() {
		return projectQtyList;
	}

	public void setProjectQtyList(List<ProjectQtyBean> projectQtyList) {
		this.projectQtyList = projectQtyList;
	}

	public String getDateText() {
		return dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}
	
	

}
