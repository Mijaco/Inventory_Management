package com.ibcs.desco.inventory.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibcs.desco.inventory.dao.ItemGroupDao;

public class UniqueItemGroupNameValidator implements ConstraintValidator<UniqueItemGroupName, String> {
	
	

	@Autowired
	private ItemGroupDao itemGroupDao;

	@Override
	public void initialize(UniqueItemGroupName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if(itemGroupDao == null) {
			return true;
		}
		return itemGroupDao.checkItemGroupName(username) == false;
	}

}
