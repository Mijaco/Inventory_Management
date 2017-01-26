package com.ibcs.desco.cs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "auction_view")
public class AuctionView {

	@Id
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "dept_id", nullable = false)
	private Departments departments;

	@ManyToOne
	@JoinColumn(name = "khath_id", nullable = false)
	private DescoKhath descoKhath;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private StoreLocations storeLocations;

	@ManyToOne
	@JoinColumn(name = "item_mst_id", nullable = false)
	private ItemMaster itemMaster;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "plus_quantity")
	private Double plusQuantity;

	@Column(name = "minus_quantity")
	private Double minusQuantity;

	@Column(name = "balance_qty")
	private Double balanceQty;

	@Column(name = "transaction_date", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date transactionDate;

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public DescoKhath getDescoKhath() {
		return descoKhath;
	}

	public void setDescoKhath(DescoKhath descoKhath) {
		this.descoKhath = descoKhath;
	}

	public StoreLocations getStoreLocations() {
		return storeLocations;
	}

	public void setStoreLocations(StoreLocations storeLocations) {
		this.storeLocations = storeLocations;
	}

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

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

	public Double getPlusQuantity() {
		return plusQuantity;
	}

	public void setPlusQuantity(Double plusQuantity) {
		this.plusQuantity = plusQuantity;
	}

	public Double getMinusQuantity() {
		return minusQuantity;
	}

	public void setMinusQuantity(Double minusQuantity) {
		this.minusQuantity = minusQuantity;
	}

	public Double getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Double balanceQty) {
		this.balanceQty = balanceQty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	

}
