package com.ibcs.desco.workshop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Test_Report_1P")
public class TestReport1P {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Test_Report_1P_SEQ")
	@SequenceGenerator(name = "Test_Report_1P_SEQ", sequenceName = "Test_Report_1P_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@OneToOne
	@JoinColumn(name = "tsf_reg_mst_id")
	private TransformerRegister tsfRegMst;
	
	@Transient
	private String temp;

	@Column(name = "pr_date", nullable = true)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prDate;

	@Column(name = "test_Date", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date testDate;

	@Column(name = "mt_ht_g")
	private String mt_ht_g;

	@Column(name = "mt_ht_ht")
	private String mt_ht_ht;

	@Column(name = "mt_lt_g")
	private String mt_lt_g;

	@Column(name = "mt_lt_lt")
	private String mt_lt_lt;

	@Column(name = "mt_ht_lt")
	private String mt_ht_lt;

	@Column(name = "mt_remarks")
	private String mt_remarks;

	@Column(name = "vrt_ht_h1_h2")
	private String vrt_ht_h1_h2;

	@Column(name = "vrt_lt_x1_x2")
	private String vrt_lt_x1_x2;

	@Column(name = "vrt_remarks")
	private String vrt_remarks;

	@Column(name = "sct_ht_h1_h2")
	private String sct_ht_h1_h2;

	@Column(name = "sct_ht_lx")
	private String sct_ht_lx;

	@Column(name = "sct_ht_wa")
	private String sct_ht_wa;

	@Column(name = "sct_lt_lx")
	private String sct_lt_lx;

	@Column(name = "sct_tsc_loss")
	private String sct_tsc_loss;

	@Column(name = "oct_lt_h1_h2")
	private String oct_lt_h1_h2;

	@Column(name = "oct_lt_wa")
	private String oct_lt_wa;

	@Column(name = "oct_lt_lx")
	private String oct_lt_lx;

	@Column(name = "oct_ht_lx")
	private String oct_ht_lx;

	@Column(name = "oct_toc_loss")
	private String oct_toc_loss;

	@Column(name = "odst")
	private String odst;

	@Column(name = "isActive")
	private boolean active = true;

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

	public TransformerRegister getTsfRegMst() {
		return tsfRegMst;
	}

	public void setTsfRegMst(TransformerRegister tsfRegMst) {
		this.tsfRegMst = tsfRegMst;
	}

	public Date getPrDate() {
		return prDate;
	}

	public void setPrDate(Date prDate) {
		this.prDate = prDate;
	}

	public String getMt_ht_g() {
		return mt_ht_g;
	}

	public void setMt_ht_g(String mt_ht_g) {
		this.mt_ht_g = mt_ht_g;
	}

	public String getMt_ht_ht() {
		return mt_ht_ht;
	}

	public void setMt_ht_ht(String mt_ht_ht) {
		this.mt_ht_ht = mt_ht_ht;
	}

	public String getMt_lt_g() {
		return mt_lt_g;
	}

	public void setMt_lt_g(String mt_lt_g) {
		this.mt_lt_g = mt_lt_g;
	}

	public String getMt_lt_lt() {
		return mt_lt_lt;
	}

	public void setMt_lt_lt(String mt_lt_lt) {
		this.mt_lt_lt = mt_lt_lt;
	}

	public String getMt_ht_lt() {
		return mt_ht_lt;
	}

	public void setMt_ht_lt(String mt_ht_lt) {
		this.mt_ht_lt = mt_ht_lt;
	}

	public String getMt_remarks() {
		return mt_remarks;
	}

	public void setMt_remarks(String mt_remarks) {
		this.mt_remarks = mt_remarks;
	}

	public String getVrt_ht_h1_h2() {
		return vrt_ht_h1_h2;
	}

	public void setVrt_ht_h1_h2(String vrt_ht_h1_h2) {
		this.vrt_ht_h1_h2 = vrt_ht_h1_h2;
	}

	public String getVrt_lt_x1_x2() {
		return vrt_lt_x1_x2;
	}

	public void setVrt_lt_x1_x2(String vrt_lt_x1_x2) {
		this.vrt_lt_x1_x2 = vrt_lt_x1_x2;
	}

	public String getVrt_remarks() {
		return vrt_remarks;
	}

	public void setVrt_remarks(String vrt_remarks) {
		this.vrt_remarks = vrt_remarks;
	}

	public String getSct_ht_h1_h2() {
		return sct_ht_h1_h2;
	}

	public void setSct_ht_h1_h2(String sct_ht_h1_h2) {
		this.sct_ht_h1_h2 = sct_ht_h1_h2;
	}

	public String getSct_ht_lx() {
		return sct_ht_lx;
	}

	public void setSct_ht_lx(String sct_ht_lx) {
		this.sct_ht_lx = sct_ht_lx;
	}

	public String getSct_ht_wa() {
		return sct_ht_wa;
	}

	public void setSct_ht_wa(String sct_ht_wa) {
		this.sct_ht_wa = sct_ht_wa;
	}

	public String getSct_lt_lx() {
		return sct_lt_lx;
	}

	public void setSct_lt_lx(String sct_lt_lx) {
		this.sct_lt_lx = sct_lt_lx;
	}

	public String getSct_tsc_loss() {
		return sct_tsc_loss;
	}

	public void setSct_tsc_loss(String sct_tsc_loss) {
		this.sct_tsc_loss = sct_tsc_loss;
	}

	public String getOct_lt_h1_h2() {
		return oct_lt_h1_h2;
	}

	public void setOct_lt_h1_h2(String oct_lt_h1_h2) {
		this.oct_lt_h1_h2 = oct_lt_h1_h2;
	}

	public String getOct_lt_wa() {
		return oct_lt_wa;
	}

	public void setOct_lt_wa(String oct_lt_wa) {
		this.oct_lt_wa = oct_lt_wa;
	}

	public String getOct_lt_lx() {
		return oct_lt_lx;
	}

	public void setOct_lt_lx(String oct_lt_lx) {
		this.oct_lt_lx = oct_lt_lx;
	}

	public String getOct_ht_lx() {
		return oct_ht_lx;
	}

	public void setOct_ht_lx(String oct_ht_lx) {
		this.oct_ht_lx = oct_ht_lx;
	}

	public String getOct_toc_loss() {
		return oct_toc_loss;
	}

	public void setOct_toc_loss(String oct_toc_loss) {
		this.oct_toc_loss = oct_toc_loss;
	}

	public String getOdst() {
		return odst;
	}

	public void setOdst(String odst) {
		this.odst = odst;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

		
	

}
