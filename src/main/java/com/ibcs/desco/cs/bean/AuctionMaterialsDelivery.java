package com.ibcs.desco.cs.bean;

import java.util.List;

public class AuctionMaterialsDelivery {

	private Integer mstId;

	private List<Integer> id;
	private List<Double> receivedQty;

	private String deliveryDate;
	
	private String uuid;

	private String receiverName;

	private String receiverContactNo;

	private String carriedBy;

	private List<String> itemCode;

	public Integer getMstId() {
		return mstId;
	}

	public void setMstId(Integer mstId) {
		this.mstId = mstId;
	}

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	public List<Double> getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(List<Double> receivedQty) {
		this.receivedQty = receivedQty;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverContactNo() {
		return receiverContactNo;
	}

	public void setReceiverContactNo(String receiverContactNo) {
		this.receiverContactNo = receiverContactNo;
	}

	public String getCarriedBy() {
		return carriedBy;
	}

	public void setCarriedBy(String carriedBy) {
		this.carriedBy = carriedBy;
	}

	public List<String> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

}
