package com.ibcs.desco.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "AVG_PRICE")
public class AvgPrice {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avg_price_sequence")
	@SequenceGenerator(name = "avg_price_sequence", sequenceName = "avg_price_sequence", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "ITEMCODE")
	private String itemCode;
	
	@Transient
	private String itemName;
	
	@Column(name = "no_item")
	private Double itemQtyTotal;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "last_pur_item_qty")
	private Double last_pur_item_qty;
	
	@Column(name = "last_pur_item_price")
	private Double last_pur_item_price;

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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemQtyTotal() {
		return itemQtyTotal;
	}

	public void setItemQtyTotal(Double itemQtyTotal) {
		this.itemQtyTotal = itemQtyTotal;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getLast_pur_item_qty() {
		return last_pur_item_qty;
	}

	public void setLast_pur_item_qty(Double last_pur_item_qty) {
		this.last_pur_item_qty = last_pur_item_qty;
	}

	public Double getLast_pur_item_price() {
		return last_pur_item_price;
	}

	public void setLast_pur_item_price(Double last_pur_item_price) {
		this.last_pur_item_price = last_pur_item_price;
	}

		
}