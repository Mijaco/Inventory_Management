package com.ibcs.desco.acl.model;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
@SuppressWarnings("serial")
public class Source implements java.io.Serializable {

	// private int id;
	private int sourceId;
	private String sourceName;
	private String remarks;
	private String ifileNameConv;
	private String ofileNameConv;

	public Source() {
	}

	public Source(int id, int sourceId, String sourceName) {
		// this.id = id;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
	}

	public Source(int id, int sourceId, String sourceName, String remarks,
			String ifileNameConv, String ofileNameConv) {
		// this.id = id;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.remarks = remarks;
		this.ifileNameConv = ifileNameConv;
		this.ofileNameConv = ofileNameConv;
	}

	/*
	 * public int getId() { return this.id; }
	 * 
	 * public void setId(int id) { this.id = id; }
	 */
	public int getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return this.sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIfileNameConv() {
		return this.ifileNameConv;
	}

	public void setIfileNameConv(String ifileNameConv) {
		this.ifileNameConv = ifileNameConv;
	}

	public String getOfileNameConv() {
		return this.ofileNameConv;
	}

	public void setOfileNameConv(String ofileNameConv) {
		this.ofileNameConv = ofileNameConv;
	}

}
