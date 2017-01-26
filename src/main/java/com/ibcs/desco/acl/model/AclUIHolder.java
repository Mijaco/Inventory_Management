package com.ibcs.desco.acl.model;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class AclUIHolder {

	private int id;
	private String role;
	private String object;
	private int p_read;
	private int p_write;
	private int p_edit;
	private int p_delete;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getP_delete() {
		return p_delete;
	}

	public void setP_delete(int p_delete) {
		this.p_delete = p_delete;
	}

	public int getP_edit() {
		return p_edit;
	}

	public void setP_edit(int p_edit) {
		this.p_edit = p_edit;
	}

	public int getP_write() {
		return p_write;
	}

	public void setP_write(int p_write) {
		this.p_write = p_write;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public int getP_read() {
		return p_read;
	}

	public void setP_read(int p_read) {
		this.p_read = p_read;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
