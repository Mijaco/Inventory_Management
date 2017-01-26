package com.ibcs.desco.acl.model;


/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */

public class PermissionTable {
	
	private int p_id;
	
	
	private int role_id;
	
	
	private int object_id;
	
	
	private int p_read;
	
	
	private int p_write;
	
	
	private int p_edit;
	
	
	private int p_delete;

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
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

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getP_read() {
		return p_read;
	}

	public void setP_read(int p_read) {
		this.p_read = p_read;
	}

	public int getP_write() {
		return p_write;
	}

	public void setP_write(int p_write) {
		this.p_write = p_write;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

}
