package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.Operation;

public interface OperationDao {
	// data access for add new Operation
	public void addOperation(Operation operation);

	// data access for get all Operation as List
	public List<Operation> listOperations();

	// data access for get specific one Operation information and update
	// Operation info
	public Operation getOperation(int id);

	// data access for Delete an Operation
	public void deleteOperation(Operation operation);

}
