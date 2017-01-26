package com.ibcs.desco.common.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ibcs.desco.common.model.Email;

public interface EmailService {

	void sendMail(Email email, CommonsMultipartFile multipartFile);
}
