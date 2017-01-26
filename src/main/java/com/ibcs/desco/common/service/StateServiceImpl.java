package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.StateDao;
import com.ibcs.desco.common.model.State;

@Service
public class StateServiceImpl implements StateService {

	StateDao stateDao;

	public StateDao getStateDao() {
		return stateDao;
	}

	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}

	@Override
	public void addState(State state) {
		// TODO Auto-generated method stub
		stateDao.addState(state);
	}

	@Override
	public List<State> listStates() {
		// TODO Auto-generated method stub
		return stateDao.listStates();
	}

	@Override
	public State getState(int id) {
		// TODO Auto-generated method stub
		return stateDao.getState(id);
	}

	@Override
	public void deleteState(State state) {
		// TODO Auto-generated method stub
		stateDao.deleteState(state);
	}
}
