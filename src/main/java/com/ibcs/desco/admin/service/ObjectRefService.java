package com.ibcs.desco.admin.service;

import java.util.List;

import com.ibcs.desco.admin.model.ObjectReference;

public interface ObjectRefService {
	public List<ObjectReference> listObjects();

	public ObjectReference getObjectReference(int id);
}
