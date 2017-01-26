package com.ibcs.desco.inventory.model;

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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "opening_balance")
public class OpeningBalance {

	@Id
	@Column(name = "opening_balance_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opening_balance_sequence")
	@SequenceGenerator(name = "opening_balance_sequence", sequenceName = "opening_balance_sequence", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "opening_date")
	private Date openingDate;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "balance_quantity")
	private Integer balanceQuantity;

	@Column(name = "rate")
	private Double rate;

	@Column(name = "amount")
	private Double amount;

	public OpeningBalance() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getBalanceQuantity() {
		return balanceQuantity;
	}

	public void setBalanceQuantity(Integer balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "OpeningBalance [id=" + id + ", openingDate=" + openingDate + ", itemName=" + itemName
				+ ", balanceQuantity=" + balanceQuantity + ", rate=" + rate + ", amount=" + amount + "]";
	}

}
