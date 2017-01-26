package com.ibcs.desco.workshop.model;

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
@Table(name = "METER_TESTING_REGISTER")
public class MeterTestingRegister {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "METER_TEST_REG_ID_SEQ")
	@SequenceGenerator(name = "METER_TEST_REG_ID_SEQ", sequenceName = "METER_TEST_REG_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "sender_dept_name")
	private String senderDeptName;

	@Column(name = "ref_no")
	private String refNo;

	@Column(name = "consumer_name")
	private String consumerName;

	@Column(name = "consumer_address")
	private String consumerAddress;

	@Column(name = "sanctioned_load")
	private String sanctionedLoad;

	@Column(name = "meter_no")
	private String meterNo;

	@Column(name = "meter_source")
	private String meterSource;

	@Column(name = "meter_type")
	private String meterType;

	@Column(name = "ct_ratio_line")
	private String ctRatioLine;

	@Column(name = "ct_ratio_meter")
	private String ctRatioMeter;

	@Column(name = "received_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date receivedDate;

	@Column(name = "test_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date testDate;

	@Column(name = "reading_as_left")
	private String readingAsLeft;

	@Column(name = "omf_and_dmf")
	private String omfAndDmf;

	@Column(name = "seal_info")
	private String sealInfo;

	@Column(name = "delivery_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date deliveryDate;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "status")
	private String status;

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

	public String getSenderDeptName() {
		return senderDeptName;
	}

	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerAddress() {
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	public String getSanctionedLoad() {
		return sanctionedLoad;
	}

	public void setSanctionedLoad(String sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getMeterSource() {
		return meterSource;
	}

	public void setMeterSource(String meterSource) {
		this.meterSource = meterSource;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getCtRatioLine() {
		return ctRatioLine;
	}

	public void setCtRatioLine(String ctRatioLine) {
		this.ctRatioLine = ctRatioLine;
	}

	public String getCtRatioMeter() {
		return ctRatioMeter;
	}

	public void setCtRatioMeter(String ctRatioMeter) {
		this.ctRatioMeter = ctRatioMeter;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getReadingAsLeft() {
		return readingAsLeft;
	}

	public void setReadingAsLeft(String readingAsLeft) {
		this.readingAsLeft = readingAsLeft;
	}

	public String getOmfAndDmf() {
		return omfAndDmf;
	}

	public void setOmfAndDmf(String omfAndDmf) {
		this.omfAndDmf = omfAndDmf;
	}

	public String getSealInfo() {
		return sealInfo;
	}

	public void setSealInfo(String sealInfo) {
		this.sealInfo = sealInfo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
