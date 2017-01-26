package com.ibcs.desco.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ibcs.desco.common.model.Email;
import com.ibcs.desco.common.service.EmailService;

@Controller
public class EmailController {

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/email.do", method = RequestMethod.GET)
	public String showEmail(Model model) {
		Email email = new Email();
		model.addAttribute(email);
		return "test/email";
	}

	@RequestMapping(value = "/sendemail.do", method = RequestMethod.POST)
	public String doSendEmail(@ModelAttribute("email") Email email, @RequestParam("fileName") CommonsMultipartFile file, Model model) {
		emailService.sendMail(email, file);
		model.addAttribute(new Email());
		// forwards to the view named "Result"
		return "redirect:/email.do";
	}
}
