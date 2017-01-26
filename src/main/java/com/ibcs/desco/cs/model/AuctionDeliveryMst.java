package com.ibcs.desco.cs.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.ibcs.desco.admin.model.Departments;

@Entity
@Table(name = "auction_delivery_mst")
public class AuctionDeliveryMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_delivery_mst_seq")
	@SequenceGenerator(name = "auction_delivery_mst_seq", sequenceName = "auction_delivery_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	// main start
	@ManyToOne
	@JoinColumn(name = "auction_work_order_mst_id", nullable = false)
	private AuctionWOEntryMst auctionWOEntryMst;

	@Column(name = "delivery_trxn_no")
	private String deliveryTrxnNo;
	
	@Column(name = "uuid")
	private String uuid;

	@Column(name = "delivery_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date deliveryDate;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_contact_no")
	private String receiverContactNo;

	@Column(name = "carried_by")
	private String carriedBy;

	@Column(name = "final_submit")
	private String finalSubmit = "0";

	@ManyToOne
	@JoinColumn(name = "department_mst_id", nullable = false)
	private Departments department;

	@Column(name = "gate_pass_no")
	private String gatePassNo;

	@Column(name = "store_ticket_no")
	private String storeTicketNo;
	// main end

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
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

	public AuctionWOEntryMst getAuctionWOEntryMst() {
		return auctionWOEntryMst;
	}

	public void setAuctionWOEntryMst(AuctionWOEntryMst auctionWOEntryMst) {
		this.auctionWOEntryMst = auctionWOEntryMst;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
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

	public String getFinalSubmit() {
		return finalSubmit;
	}

	public void setFinalSubmit(String finalSubmit) {
		this.finalSubmit = finalSubmit;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
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

	public String getDeliveryTrxnNo() {
		return deliveryTrxnNo;
	}

	public void setDeliveryTrxnNo(String deliveryTrxnNo) {
		this.deliveryTrxnNo = deliveryTrxnNo;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public String getStoreTicketNo() {
		return storeTicketNo;
	}

	public void setStoreTicketNo(String storeTicketNo) {
		this.storeTicketNo = storeTicketNo;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
