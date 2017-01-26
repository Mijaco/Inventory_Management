package com.ibcs.desco.subStore.model;

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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "SS_VEHICLE_PERMISSION")
public class SSVehiclePermission {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ss_vehicle_permission_id_seq")
	@SequenceGenerator(name = "ss_vehicle_permission_id_seq", sequenceName = "ss_vehicle_permission_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "slip_no")
	private String slipNo;

	@Column(name = "contractor_name")
	private String contractorName;

	@Column(name = "entry_time")
	@DateTimeFormat(pattern = "hh:mm:ss a dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryTime;

	@Column(name = "entry_purpose")
	private String entryPurpose;

	@Column(name = "vehicle_type")
	private String vehicleType;

	@Column(name = "vehicle_number")
	private String vehicleNumber;

	@Column(name = "driving_licence_no")
	private String drivingLicenceNo;

	@Column(name = "requisition_by")
	private String requisitionBy;

	@Column(name = "is_active")
	private Boolean active = true;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "number_of_person")
	private String numberOfPerson;

	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "vehicle_no")
	private String vehicleNo;

	@Transient
	private String returnState;

	@Transient
	private String stage;

	@Transient
	private String return_to;

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

	public String getReturnState() {
		return returnState;
	}

	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}

	public Integer getId() {
		return id;
	}

	public String getSlipNo() {
		return slipNo;
	}

	public String getContractorName() {
		return contractorName;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public String getEntryPurpose() {
		return entryPurpose;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public String getDrivingLicenceNo() {
		return drivingLicenceNo;
	}

	public String getRequisitionBy() {
		return requisitionBy;
	}

	public Boolean getActive() {
		return active;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public void setEntryPurpose(String entryPurpose) {
		this.entryPurpose = entryPurpose;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public void setDrivingLicenceNo(String drivingLicenceNo) {
		this.drivingLicenceNo = drivingLicenceNo;
	}

	public void setRequisitionBy(String requisitionBy) {
		this.requisitionBy = requisitionBy;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getReturn_to() {
		return return_to;
	}

	public void setReturn_to(String return_to) {
		this.return_to = return_to;
	}

	public String getNumberOfPerson() {
		return numberOfPerson;
	}

	public void setNumberOfPerson(String numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

}
