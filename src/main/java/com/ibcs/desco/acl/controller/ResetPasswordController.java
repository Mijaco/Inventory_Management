package com.ibcs.desco.acl.controller;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.acl.model.ResetToken;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.common.model.Email;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.EmailService;

@Controller
@PropertySource("classpath:common.properties")
public class ResetPasswordController {
	@Autowired
	CommonService commonService;

	@Autowired
	EmailService emailService;

	@Value("${desco.resetpassword.token.expire.hour}")
	private String tokenExpireHour;

	// Added by: Ihteshamul Alam
	// Send an email with reset password link
	@RequestMapping(value = "/auth/forgetPassword.do", method = RequestMethod.POST)
	@ResponseBody
	public String forgetPassword(@RequestBody String json,
			HttpServletRequest request) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			AuthUser authUser = gson.fromJson(json, AuthUser.class);

			String email = authUser.getEmail();
			if (email.equals("")) {
				toJson = "Invalid E-mail address";
			} else {
				AuthUser aUser = (AuthUser) commonService
						.getAnObjectByAnyUniqueColumn(
								"com.ibcs.desco.admin.model.AuthUser", "email",
								email);
				if (aUser == null) {
					toJson = "Invalid E-mail address";
				} else {
					// toJson = "Valid E-mail address";
					Email emailObj = new Email();
					String token = UUID.randomUUID().toString();
					String baseURL = "http://" + request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath();
					String finalURL = baseURL + "/auth/resetPassword.do?token="
							+ token;
					String body = "Here you get a password reset link for your DESCO account. "
							+ "Please click on this link for reset your password."
							+ "\n" + "\n" + finalURL;

					emailObj.setRecipint(email);
					emailObj.setSubject("Reset Password for DESCO");
					emailObj.setWrittenInformation(body);
					emailService.sendMail(emailObj, null);
					// int expire = request.get
					Date date = new Date();
					Date expiredDate = DateUtils.addHours(date,
							Integer.parseInt(tokenExpireHour));

					ResetToken resetToken = new ResetToken();

					resetToken.setId(null);
					resetToken.setToken(token);
					resetToken.setUserId(aUser.getUserid());
					resetToken.setEmail(email);
					resetToken.setName(aUser.getName());
					resetToken.setCreatedDate(date);
					resetToken.setExpiredDate(expiredDate);
					resetToken.setStatus("OPEN");
					commonService.saveOrUpdateModelObjectToDB(resetToken);

					toJson = "Sent a link to "
							+ email
							+ " to reset your password, Please check your inbox.";
				}
			}
			// ObjectWriter ow = new
			// ObjectMapper().writer().withDefaultPrettyPrinter();
			// toJson = ow.writeValueAsString(parentLocations);

		} else {
			Thread.sleep(3000);
			toJson = "fail";
		}

		return toJson;
	}

	// Added by: Ihteshamul Alam
	// Reset Password Form
	@RequestMapping(value = "/auth/resetPassword.do", method = RequestMethod.GET)
	public ModelAndView resetPassword(
			@ModelAttribute("resetToken") ResetToken resetToken) {

		Map<String, Object> model = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		String token = resetToken.getToken();
		ResetToken rsToken = (ResetToken) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.acl.model.ResetToken", "token",
						resetToken.getToken());
		Date tokenDate = rsToken.getExpiredDate();
		Date date = new Date();

		if (rsToken.getStatus().equalsIgnoreCase("EXPIRED")) {
			model.put("expiredflag", 1);
		} else if (rsToken.getStatus().equalsIgnoreCase("CLOSED")) {
			model.put("closedflag", 1);
		} else if (rsToken.getStatus().equalsIgnoreCase("OPEN")) {

			if (tokenDate.compareTo(date) < 0) {
				rsToken.setStatus("EXPIRED");
				commonService.saveOrUpdateModelObjectToDB(rsToken);
				model.put("expiredflag", 1);
			} else {
				model.put("openflag", 1);
			}
		}
		model.put("rsToken", rsToken);
		return new ModelAndView("acl/resetPassword", model);
	}
	
	//Added by: Ihteshamul Alam
	//Update Password action
	@RequestMapping(value="/auth/updatePassword.do", method=RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ResetToken resetToken = gson.fromJson(json, ResetToken.class);
			String email = resetToken.getEmail();
			Integer id = resetToken.getId();
			String password = resetToken.getPassword();
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer hashedPassword = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				hashedPassword.append(Integer.toString(
						(byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			//String finalPassword = hashedPassword.toString();
			
			ResetToken rsToken = ( ResetToken ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.acl.model.ResetToken", "id", id+"");
			AuthUser authUser = ( AuthUser ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "email", email);
			
			//String username = commonService.getAuthUserName();
			authUser.setPassword( hashedPassword.toString() );
			//authUser.setModifiedBy(username);
			authUser.setModifiedDate( new Date() );
			
			rsToken.setStatus("CLOSED");
			
			commonService.saveOrUpdateModelObjectToDB(authUser);
			commonService.saveOrUpdateModelObjectToDB(rsToken);
			
			Email emailsn = new Email();
			String body = "Dear "+ authUser.getUserid() +", The password for your Desco Web Inventory account " + authUser.getUserid() + " was recently changed.";
			emailsn.setRecipint(authUser.getEmail());
			//emailsn.setSubject();
			//emailsn.setRecipint(authUser.getEmail());
			emailsn.setSubject("Password Update Notification");
			emailsn.setWrittenInformation(body);
			emailService.sendMail(emailsn, null);
			
			//model.put("updateflag", "1");
			toJson = "success";
		} else {
			Thread.sleep(3000);
			toJson = "fail";
		}

		return toJson;
	}
}
