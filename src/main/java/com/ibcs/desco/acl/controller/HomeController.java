package com.ibcs.desco.acl.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.acl.model.Menu;
import com.ibcs.desco.acl.model.ResetToken;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
@Controller
public class HomeController  extends Constrants {
	
	@Autowired
	CommonService commonService;
	
	// for get Login Page
	@RequestMapping(value = "/auth/login.do", method = RequestMethod.GET)
	public String getLoginPage(
			@RequestParam(value = "error", required = false) boolean error,
			ModelMap model, HttpServletRequest request) {
		
		if(!commonService.getAuthUserName().equals("anonymousUser")){
			return "redirect:/common.do";
		}

		if (error == true) {
			// Assign an error message
			model.put("errors",
					"Invalid username or password!");

		} else {
			model.put("errors", "");
		}

		// This will resolve to /WEB-INF/jsp/loginpage.jsp

		javax.servlet.http.HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		session.setAttribute("hello", this.getDate());
		session.setAttribute("ip", request.getRemoteAddr());

		return "acl/loginpage";
	}
	
	// for logout
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET) 
	public String logoutPage(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/auth/login.do?logout";
	}

	// for Database Error
	@RequestMapping(value = "/error.do", method = RequestMethod.GET) 
	public String databaseErrorPage() {		
		return "acl/error";
	}

	// an action which add new data
	/*@RequestMapping(value = "/addNew.do")
	@PreAuthorize("hasPermission(#user, 'READ')")
	// @PreAuthorize("hasRole('ROLE_ADMIN')") // does worked
	// @PostFilter("hasPermission(#user, 'WRITE')" )
	public ModelAndView home(Locale locale, Model model,
			com.ibcs.desco.acl.model.User user) {

		ModelAndView mv = new ModelAndView("test/addNew");
		mv.addObject("test", user.getUserid() + "I am from Server");
		// model.addAttribute("test", user.getUserid() + "I am from Server");
		return mv; 
	}*/

	// date formating
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date).toString();

	}

	// after login go the index page by this action
	@RequestMapping(value = "/common.do")
	@PreAuthorize("isAuthenticated()")
	// @PreAuthorize("hasPermission(#menux, 'WRITE')")
	public ModelAndView getCommonPage(
			SecurityContextHolderAwareRequestWrapper req, Menu menux,
			HttpServletRequest request, HttpServletResponse response) {
		//DBAction db = new DBAction();
		
		ModelAndView mv = null;
		AuthUser authUser = ( AuthUser ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", commonService.getAuthUserName());
		if(authUser.getLoginCounter()==null){			
				mv = new ModelAndView("acl/remitPassword");
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				mv.addObject("serverTime", " , " + dateFormat.format(date));
				mv.addObject("authenticatedUser", authUser);
				return mv;			
		}	
		
		String roleName = commonService.getAuthRoleName();
		if(roleName.contains(ROLE_CN_PD_USER) || roleName.contains(ROLE_SND_CN_USER) || roleName.contains(ROLE_WO_CN_USER)){
			mv = new ModelAndView("contractor/contractorCommon");
		}else{			
			mv = new ModelAndView("acl/index");
		}		

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		mv.addObject("serverTime", " , " + dateFormat.format(date));
		mv.addObject("authenticatedUser", authUser);
		// mv.addObject("permissionList", map);
		return mv;
	}

	// worked this action when an user has no permisson on a page or object
	@RequestMapping(value = "/auth/denied.do", method = RequestMethod.GET)
	public ModelAndView accessDenay(Locale locale, Model model,
			com.ibcs.desco.acl.model.User user) {

		ModelAndView mv = new ModelAndView("acl/accessDenay");

		return mv;
	}

	// for Welcome page
	/*@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView welcome() {
		return new ModelAndView("acl/index");
	}*/
	
	@RequestMapping(value="/myProfile.do", method= RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getMyProfile() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		String username = commonService.getAuthUserName();
		AuthUser userinfo = ( AuthUser ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", username);
		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId", userinfo.getDeptId());
		
		model.put("userinfo", userinfo);
		model.put("role", commonService.getAuthRoleName());
		model.put("department", department);
		return new ModelAndView("acl/myProfile", model);
	} ///myprofile.do
	
	@RequestMapping(value="/changePassword.do", method= RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String changePassword( ResetToken resetToken ) throws NoSuchAlgorithmException {
		//changePassword
		
		String output = "";
		
		Integer id = resetToken.getId();
		String opassword = resetToken.getOpassword();
		String password = resetToken.getPassword();
		
		AuthUser athUser = ( AuthUser ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "id", id.toString());
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(opassword.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer hashedPassword = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			hashedPassword.append(Integer.toString(
					(byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		if( hashedPassword.toString().equals( athUser.getPassword() ) ) {
			md.update(password.getBytes());

			byte nbyteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer nhashedPassword = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				nhashedPassword.append(Integer.toString(
						(nbyteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			athUser.setPassword( nhashedPassword.toString() );
			athUser.setModifiedDate( new Date() );
			athUser.setModifiedBy( commonService.getAuthUserName() );
			commonService.saveOrUpdateModelObjectToDB(athUser);
			output = "success";
		} else {
			output="opdnm";
		}
		
		return output;
	}
	
	@RequestMapping(value="/changedPassword.do", method= RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String changedPassword( ResetToken resetToken ) throws NoSuchAlgorithmException {
		//changePassword
		
		String output = "";
		
		String opassword = resetToken.getOpassword();
		String password = resetToken.getPassword();
		
		AuthUser athUser = ( AuthUser ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", commonService.getAuthUserName());
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(opassword.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer hashedPassword = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			hashedPassword.append(Integer.toString(
					(byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		if( hashedPassword.toString().equals( athUser.getPassword() ) ) {
			md.update(password.getBytes());

			byte nbyteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer nhashedPassword = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				nhashedPassword.append(Integer.toString(
						(nbyteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			athUser.setPassword( nhashedPassword.toString() );
			athUser.setLoginCounter(1);
			athUser.setModifiedDate( new Date() );
			athUser.setModifiedBy( commonService.getAuthUserName() );
			commonService.saveOrUpdateModelObjectToDB(athUser);
			output = "redirect:/logout.do";
		} else {
			output="redirect:/common.do";
		}
		
		return output;
	}
	
}
