package com.ibcs.desco.common.service;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ibcs.desco.common.model.Email;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendMail(final Email email,
			final CommonsMultipartFile attachFile) {

		/*
		 * SimpleMailMessage mail = new SimpleMailMessage();
		 * mail.setTo(email.getRecipint()); mail.setSubject(email.getSubject());
		 * mail.setText(email.getWrittenInformation());
		 */

		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(
						mimeMessage, true, "UTF-8");
				messageHelper.setTo(email.getRecipint());
				messageHelper.setSubject(email.getSubject());
				messageHelper.setText(email.getWrittenInformation());
				// determines if there is an upload file, attach it to the
				// e-mail
				if (attachFile != null) {
					String attachName = attachFile.getOriginalFilename();
					if (!attachName.equals("")) {
						messageHelper.addAttachment(attachName,
								new InputStreamSource() {
									@Override
									public InputStream getInputStream()
											throws IOException {
										return attachFile.getInputStream();
									}
								});
					}
				}

			}

		});

	}

}
