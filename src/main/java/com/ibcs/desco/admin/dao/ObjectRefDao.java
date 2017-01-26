package com.ibcs.desco.admin.dao;

import java.util.List;

import com.ibcs.desco.admin.model.ObjectReference;

public interface ObjectRefDao {
	// data access for get all ObjectReference as List
			public List<ObjectReference> listObjects();

			public ObjectReference getObjectReference(int id);
}
