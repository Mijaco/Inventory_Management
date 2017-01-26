package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.State;

public interface StateService {
	// data access for add new State
	public void addState(State state);

	// data access for get all State as List
	public List<State> listStates();

	// data access for get specific one State information and update
	// State info
	public State getState(int id);

	// data access for Delete an State
	public void deleteState(State state);
}
