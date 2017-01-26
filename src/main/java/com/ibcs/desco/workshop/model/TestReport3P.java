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
@Table(name = "Test_Report_3P")
public class TestReport3P {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Test_Report_3P_SEQ")
	@SequenceGenerator(name = "Test_Report_3P_SEQ", sequenceName = "Test_Report_3P_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;
	
	@OneToOne
	@JoinColumn(name = "tsf_reg_mst_id")
	private TransformerRegister tsfRegMst;


	@Column(name = "pr_date", nullable = true)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prDate;
	
	@Column(name = "test_Date", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date testDate;
	
	@Transient
	private String temp;
	
	@Column( name = "impedance" )
	private String impedance;
	
	@Column(name = "ct_hv_a_b")
	private String ct_hv_a_b;
	
	@Column(name = "ct_hv_b_c")
	private String ct_hv_b_c;
	
	@Column(name = "ct_hv_c_a")
	private String ct_hv_c_a;
	
	@Column(name = "ct_lv_a_b")
	private String ct_lv_a_b;
	
	@Column(name = "ct_lv_b_c")
	private String ct_lv_b_c;
	
	@Column(name = "ct_lv_c_a")
	private String ct_lv_c_a;
	
	@Column(name = "ct_lv_a_n")
	private String ct_lv_a_n;
	
	@Column(name = "ct_lv_b_n")
	private String ct_lv_b_n;
	
	@Column(name = "ct_lv_c_n")
	private String ct_lv_c_n;
	
	@Column(name = "rt_av_a_b")
	private String rt_av_a_b;
	
	@Column(name = "rt_av_b_c")
	private String rt_av_b_c;
	
	@Column(name = "rt_av_c_a")
	private String rt_av_c_a;
	
	@Column(name = "rt_mlv_a_b")
	private String rt_mlv_a_b;
	
	@Column(name = "rt_mlv_b_c")
	private String rt_mlv_b_c;
	
	@Column(name = "rt_mlv_c_a")
	private String rt_mlv_c_a;
	
	@Column(name = "rt_mlv_a_n")
	private String rt_mlv_a_n;
	
	@Column(name = "rt_mlv_b_n")
	private String rt_mlv_b_n;
	
	@Column(name = "rt_mlv_c_n")
	private String rt_mlv_c_n;
	
	@Column(name = "rt_ratio")
	private String rt_ratio;
	
	@Column(name = "rt_remarks")
	private String rt_remarks;
	
	@Column(name = "irt_hv_lvg")
	private String irt_hv_lvg;
	
	@Column(name = "irt_lv_hvg")
	private String irt_lv_hvg;
	
	@Column(name = "irt_hv_lv")
	private String irt_hv_lv;
	
	@Column(name = "irt_oil_temp")
	private String irt_oil_temp;
	
	@Column(name = "irt_remarks")
	private String irt_remarks;
	
	@Column(name = "llit_hv_ab")
	private String llit_hv_ab;
	
	@Column(name = "llit_hv_bc")
	private String llit_hv_bc;
	
	@Column(name = "llit_hv_ca")
	private String llit_hv_ca;
	
	@Column(name = "llit_hvs_a")
	private String llit_hvs_a;
	
	@Column(name = "llit_hvs_b")
	private String llit_hvs_b;
	
	@Column(name = "llit_hvs_c")
	private String llit_hvs_c;
	
	@Column(name = "llit_lv_a")
	private String llit_lv_a;
	
	@Column(name = "llit_lv_b")
	private String llit_lv_b;
	
	@Column(name = "llit_lv_c")
	private String llit_lv_c;
	
	@Column(name = "llit_lv_n")
	private String llit_lv_n;
	
	@Column(name = "llit_lv_remarks")
	private String llit_lv_remarks;
	
	@Column(name = "llit_load_loss")
	private String llit_load_loss;
	
	@Column(name = "llit_copper_loss")
	private String llit_copper_loss;
	
	@Column(name = "llit_impedance_volt")
	private String llit_impedance_volt;
	
	@Column(name = "llit_percent_impedance")
	private String llit_percent_impedance;
	
	@Column(name = "llit_remarks")
	private String llit_remarks;
	
	@Column(name = "ilt_lvv_ab")
	private String ilt_lvv_ab;
	
	@Column(name = "ilt_lvv_bc")
	private String ilt_lvv_bc;
	
	@Column(name = "ilt_lvv_ca")
	private String ilt_lvv_ca;
	
	@Column(name = "ilt_lvv_an")
	private String ilt_lvv_an;
	
	@Column(name = "ilt_lvv_bn")
	private String ilt_lvv_bn;
	
	@Column(name = "ilt_lvv_cn")
	private String ilt_lvv_cn;
	
	@Column(name = "ilt_lva_a")
	private String ilt_lva_a;
	
	@Column(name = "ilt_lva_b")
	private String ilt_lva_b;
	
	@Column(name = "ilt_lva_c")
	private String ilt_lva_c;
	
	@Column(name = "ilt_remarks")
	private String ilt_remarks;
	
	@Column(name = "tot_dieletric_volt_transformer")
	private String tot_dieletric_volt_transformer;
	
	@Column(name = "tot_dieletric_kv")
	private String tot_dieletric_kv;
	
	@Column(name = "tot_remarks")
	private String tot_remarks;
	
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

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	
	public String getImpedance() {
		return impedance;
	}

	public void setImpedance(String impedance) {
		this.impedance = impedance;
	}

	public String getCt_hv_a_b() {
		return ct_hv_a_b;
	}

	public void setCt_hv_a_b(String ct_hv_a_b) {
		this.ct_hv_a_b = ct_hv_a_b;
	}

	public String getCt_hv_b_c() {
		return ct_hv_b_c;
	}

	public void setCt_hv_b_c(String ct_hv_b_c) {
		this.ct_hv_b_c = ct_hv_b_c;
	}

	public String getCt_hv_c_a() {
		return ct_hv_c_a;
	}

	public void setCt_hv_c_a(String ct_hv_c_a) {
		this.ct_hv_c_a = ct_hv_c_a;
	}

	public String getCt_lv_a_b() {
		return ct_lv_a_b;
	}

	public void setCt_lv_a_b(String ct_lv_a_b) {
		this.ct_lv_a_b = ct_lv_a_b;
	}

	public String getCt_lv_b_c() {
		return ct_lv_b_c;
	}

	public void setCt_lv_b_c(String ct_lv_b_c) {
		this.ct_lv_b_c = ct_lv_b_c;
	}

	public String getCt_lv_c_a() {
		return ct_lv_c_a;
	}

	public void setCt_lv_c_a(String ct_lv_c_a) {
		this.ct_lv_c_a = ct_lv_c_a;
	}

	public String getCt_lv_a_n() {
		return ct_lv_a_n;
	}

	public void setCt_lv_a_n(String ct_lv_a_n) {
		this.ct_lv_a_n = ct_lv_a_n;
	}

	public String getCt_lv_b_n() {
		return ct_lv_b_n;
	}

	public void setCt_lv_b_n(String ct_lv_b_n) {
		this.ct_lv_b_n = ct_lv_b_n;
	}

	public String getCt_lv_c_n() {
		return ct_lv_c_n;
	}

	public void setCt_lv_c_n(String ct_lv_c_n) {
		this.ct_lv_c_n = ct_lv_c_n;
	}

	public String getRt_av_a_b() {
		return rt_av_a_b;
	}

	public void setRt_av_a_b(String rt_av_a_b) {
		this.rt_av_a_b = rt_av_a_b;
	}

	public String getRt_av_b_c() {
		return rt_av_b_c;
	}

	public void setRt_av_b_c(String rt_av_b_c) {
		this.rt_av_b_c = rt_av_b_c;
	}

	public String getRt_av_c_a() {
		return rt_av_c_a;
	}

	public void setRt_av_c_a(String rt_av_c_a) {
		this.rt_av_c_a = rt_av_c_a;
	}

	public String getRt_mlv_a_b() {
		return rt_mlv_a_b;
	}

	public void setRt_mlv_a_b(String rt_mlv_a_b) {
		this.rt_mlv_a_b = rt_mlv_a_b;
	}

	public String getRt_mlv_b_c() {
		return rt_mlv_b_c;
	}

	public void setRt_mlv_b_c(String rt_mlv_b_c) {
		this.rt_mlv_b_c = rt_mlv_b_c;
	}

	public String getRt_mlv_c_a() {
		return rt_mlv_c_a;
	}

	public void setRt_mlv_c_a(String rt_mlv_c_a) {
		this.rt_mlv_c_a = rt_mlv_c_a;
	}

	public String getRt_mlv_a_n() {
		return rt_mlv_a_n;
	}

	public void setRt_mlv_a_n(String rt_mlv_a_n) {
		this.rt_mlv_a_n = rt_mlv_a_n;
	}

	public String getRt_mlv_b_n() {
		return rt_mlv_b_n;
	}

	public void setRt_mlv_b_n(String rt_mlv_b_n) {
		this.rt_mlv_b_n = rt_mlv_b_n;
	}

	public String getRt_mlv_c_n() {
		return rt_mlv_c_n;
	}

	public void setRt_mlv_c_n(String rt_mlv_c_n) {
		this.rt_mlv_c_n = rt_mlv_c_n;
	}

	public String getRt_ratio() {
		return rt_ratio;
	}

	public void setRt_ratio(String rt_ratio) {
		this.rt_ratio = rt_ratio;
	}

	public String getRt_remarks() {
		return rt_remarks;
	}

	public void setRt_remarks(String rt_remarks) {
		this.rt_remarks = rt_remarks;
	}

	public String getIrt_hv_lvg() {
		return irt_hv_lvg;
	}

	public void setIrt_hv_lvg(String irt_hv_lvg) {
		this.irt_hv_lvg = irt_hv_lvg;
	}

	public String getIrt_lv_hvg() {
		return irt_lv_hvg;
	}

	public void setIrt_lv_hvg(String irt_lv_hvg) {
		this.irt_lv_hvg = irt_lv_hvg;
	}

	public String getIrt_hv_lv() {
		return irt_hv_lv;
	}

	public void setIrt_hv_lv(String irt_hv_lv) {
		this.irt_hv_lv = irt_hv_lv;
	}

	public String getIrt_oil_temp() {
		return irt_oil_temp;
	}

	public void setIrt_oil_temp(String irt_oil_temp) {
		this.irt_oil_temp = irt_oil_temp;
	}

	public String getIrt_remarks() {
		return irt_remarks;
	}

	public void setIrt_remarks(String irt_remarks) {
		this.irt_remarks = irt_remarks;
	}

	public String getLlit_hv_ab() {
		return llit_hv_ab;
	}

	public void setLlit_hv_ab(String llit_hv_ab) {
		this.llit_hv_ab = llit_hv_ab;
	}

	public String getLlit_hv_bc() {
		return llit_hv_bc;
	}

	public void setLlit_hv_bc(String llit_hv_bc) {
		this.llit_hv_bc = llit_hv_bc;
	}

	public String getLlit_hv_ca() {
		return llit_hv_ca;
	}

	public void setLlit_hv_ca(String llit_hv_ca) {
		this.llit_hv_ca = llit_hv_ca;
	}

	public String getLlit_hvs_a() {
		return llit_hvs_a;
	}

	public void setLlit_hvs_a(String llit_hvs_a) {
		this.llit_hvs_a = llit_hvs_a;
	}

	public String getLlit_hvs_b() {
		return llit_hvs_b;
	}

	public void setLlit_hvs_b(String llit_hvs_b) {
		this.llit_hvs_b = llit_hvs_b;
	}

	public String getLlit_hvs_c() {
		return llit_hvs_c;
	}

	public void setLlit_hvs_c(String llit_hvs_c) {
		this.llit_hvs_c = llit_hvs_c;
	}

	public String getLlit_lv_a() {
		return llit_lv_a;
	}

	public void setLlit_lv_a(String llit_lv_a) {
		this.llit_lv_a = llit_lv_a;
	}

	public String getLlit_lv_b() {
		return llit_lv_b;
	}

	public void setLlit_lv_b(String llit_lv_b) {
		this.llit_lv_b = llit_lv_b;
	}

	public String getLlit_lv_c() {
		return llit_lv_c;
	}

	public void setLlit_lv_c(String llit_lv_c) {
		this.llit_lv_c = llit_lv_c;
	}

	public String getLlit_lv_n() {
		return llit_lv_n;
	}

	public void setLlit_lv_n(String llit_lv_n) {
		this.llit_lv_n = llit_lv_n;
	}

	public String getLlit_lv_remarks() {
		return llit_lv_remarks;
	}

	public void setLlit_lv_remarks(String llit_lv_remarks) {
		this.llit_lv_remarks = llit_lv_remarks;
	}

	public String getLlit_load_loss() {
		return llit_load_loss;
	}

	public void setLlit_load_loss(String llit_load_loss) {
		this.llit_load_loss = llit_load_loss;
	}

	public String getLlit_copper_loss() {
		return llit_copper_loss;
	}

	public void setLlit_copper_loss(String llit_copper_loss) {
		this.llit_copper_loss = llit_copper_loss;
	}

	public String getLlit_impedance_volt() {
		return llit_impedance_volt;
	}

	public void setLlit_impedance_volt(String llit_impedance_volt) {
		this.llit_impedance_volt = llit_impedance_volt;
	}

	public String getLlit_percent_impedance() {
		return llit_percent_impedance;
	}

	public void setLlit_percent_impedance(String llit_percent_impedance) {
		this.llit_percent_impedance = llit_percent_impedance;
	}

	public String getLlit_remarks() {
		return llit_remarks;
	}

	public void setLlit_remarks(String llit_remarks) {
		this.llit_remarks = llit_remarks;
	}

	public String getIlt_lvv_ab() {
		return ilt_lvv_ab;
	}

	public void setIlt_lvv_ab(String ilt_lvv_ab) {
		this.ilt_lvv_ab = ilt_lvv_ab;
	}

	public String getIlt_lvv_bc() {
		return ilt_lvv_bc;
	}

	public void setIlt_lvv_bc(String ilt_lvv_bc) {
		this.ilt_lvv_bc = ilt_lvv_bc;
	}

	public String getIlt_lvv_ca() {
		return ilt_lvv_ca;
	}

	public void setIlt_lvv_ca(String ilt_lvv_ca) {
		this.ilt_lvv_ca = ilt_lvv_ca;
	}

	public String getIlt_lvv_an() {
		return ilt_lvv_an;
	}

	public void setIlt_lvv_an(String ilt_lvv_an) {
		this.ilt_lvv_an = ilt_lvv_an;
	}

	public String getIlt_lvv_bn() {
		return ilt_lvv_bn;
	}

	public void setIlt_lvv_bn(String ilt_lvv_bn) {
		this.ilt_lvv_bn = ilt_lvv_bn;
	}

	public String getIlt_lvv_cn() {
		return ilt_lvv_cn;
	}

	public void setIlt_lvv_cn(String ilt_lvv_cn) {
		this.ilt_lvv_cn = ilt_lvv_cn;
	}

	public String getIlt_lva_a() {
		return ilt_lva_a;
	}

	public void setIlt_lva_a(String ilt_lva_a) {
		this.ilt_lva_a = ilt_lva_a;
	}

	public String getIlt_lva_b() {
		return ilt_lva_b;
	}

	public void setIlt_lva_b(String ilt_lva_b) {
		this.ilt_lva_b = ilt_lva_b;
	}

	public String getIlt_lva_c() {
		return ilt_lva_c;
	}

	public void setIlt_lva_c(String ilt_lva_c) {
		this.ilt_lva_c = ilt_lva_c;
	}

	public String getIlt_remarks() {
		return ilt_remarks;
	}

	public void setIlt_remarks(String ilt_remarks) {
		this.ilt_remarks = ilt_remarks;
	}

	public String getTot_dieletric_volt_transformer() {
		return tot_dieletric_volt_transformer;
	}

	public void setTot_dieletric_volt_transformer(String tot_dieletric_volt_transformer) {
		this.tot_dieletric_volt_transformer = tot_dieletric_volt_transformer;
	}

	public String getTot_dieletric_kv() {
		return tot_dieletric_kv;
	}

	public void setTot_dieletric_kv(String tot_dieletric_kv) {
		this.tot_dieletric_kv = tot_dieletric_kv;
	}

	public String getTot_remarks() {
		return tot_remarks;
	}

	public void setTot_remarks(String tot_remarks) {
		this.tot_remarks = tot_remarks;
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

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	
}
