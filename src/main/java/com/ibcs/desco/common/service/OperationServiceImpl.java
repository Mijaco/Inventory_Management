package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.OperationDao;
import com.ibcs.desco.common.model.Operation;

@Service
public class OperationServiceImpl implements OperationService {

	OperationDao operationDao;

	public OperationDao getOperationDao() {
		return operationDao;
	}

	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}

	@Override
	public void addOperation(Operation operation) {
		// TODO Auto-generated method stub
		operationDao.addOperation(operation);
	}

	@Override
	public List<Operation> listOperations() {
		// TODO Auto-generated method stub
		return operationDao.listOperations();
	}

	@Override
	public Operation getOperation(int id) {
		// TODO Auto-generated method stub
		return operationDao.getOperation(id);
	}

	@Override
	public void deleteOperation(Operation operation) {
		// TODO Auto-generated method stub
		operationDao.deleteOperation(operation);
	}
}
