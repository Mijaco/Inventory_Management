package com.ibcs.desco.admin.controller;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.ObjectReference;
import com.ibcs.desco.admin.model.PermissionTable;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.ObjectRefService;
import com.ibcs.desco.admin.service.PermissionService;
import com.ibcs.desco.admin.service.RoleService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.dashboard.AdminPanel;
import com.ibcs.desco.common.model.Email;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.EmailService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	CommonService commonService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ObjectRefService objectRefService;

	@Autowired
	EmailService emailService;

	public void setObjectRefService(ObjectRefService objectRefService) {
		this.objectRefService = objectRefService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@RequestMapping(value = "/adminPanel.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#role, 'READ')")
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#adminPanel, 'READ')")
	public ModelAndView getAdminPanel(AdminPanel adminPanel) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		return new ModelAndView("admin/adminPanel", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/authUser.do", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#role, 'READ')")
	public ModelAndView getAuthUser(
			@Valid @ModelAttribute("command") Roles role, BindingResult result) {

		List<AuthUser> users = userService.listAuthUsersByRoleId(role
				.getRole_id());

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(role.getRole_id());

		for (int i = 0; i < permissions.size(); i++) {
			ObjectReference or = objectRefService
					.getObjectReference(permissions.get(i).getObject_id());
			permissions.get(i).setObjectName(or.getDisplay_name());

		}
		// Object List get
		List<ObjectReference> objectList = objectRefService.listObjects(); 
		
		List<Departments> departmentList = (List<Departments>)(Object)
				commonService.getAllObjectList("Departments");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("userList", users);
		model.put("departmentList", departmentList);
		model.put("permissionList", permissions);
		model.put("objectList", objectList);
		return new ModelAndView("admin/adminPanel", model);
	}

	// for save an AuthUser
	@RequestMapping(value = "/user/save.do", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#authUser, 'WRITE')")
	public ModelAndView saveAuthUser(
			@Valid @ModelAttribute("command") AuthUser authUser,
			BindingResult result) throws Exception {

		String userPassword = authUser.getUserid();
		Email email = new Email();
		try {
			String password = authUser.getPassword();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer hashedPassword = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				hashedPassword.append(Integer.toString(
						(byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			authUser.setPassword(hashedPassword.toString());
			userService.addAuthUser(authUser);
		} catch (Exception e) {
			throw e;
		}
		List<AuthUser> users = userService.listAuthUsersByRoleId(authUser
				.getRoleid());
		String body = "Your are the new member of Desco Inventory System. your username is: "
				+ authUser.getUserid()
				+ " And your password is: "
				+ userPassword;
		email.setRecipint(authUser.getEmail());
		email.setSubject("Urgent Notification");
		email.setWrittenInformation(body);
		emailService.sendMail(email, null);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("userList", users);
		return new ModelAndView("admin/adminPanel", model);
	}

	// for Delete a User
	@RequestMapping(value = "/user/delete.do", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#authUser, 'DELETE')")
	public ModelAndView deleteAuthUser(
			@Valid @ModelAttribute("command") AuthUser authUser) {
		userService.deleteAuthUser(authUser);
		List<AuthUser> users = userService.listAuthUsersByRoleId(authUser
				.getRoleid());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("userList", users);
		return new ModelAndView("admin/adminPanel", model);
	}

	// for save an Role
	@RequestMapping(value = "/role/save.do", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#role, 'WRITE')")
	public ModelAndView saveRoles(@Valid @ModelAttribute("command") Roles role,
			BindingResult result) throws Exception {
		if (role.getId() == null) {
			role.setId(0);
		}
		if (role.getId() == 0) {
			List<Roles> roleList = roleService.listRoles();

			Integer roleId = 0;

			Integer[] roleIdArry = new Integer[roleList.size()];
			int i = 0;
			for (Roles roles : roleList) {

				roleId = roles.getRole_id();

				roleIdArry[i] = roleId;
				i++;
			}

			Arrays.sort(roleIdArry);

			Integer id = roleIdArry[roleIdArry.length - 1];

			role.setRole_id(id + 1);
			
			// map all object for this role and save to permission table
			this.saveObjectReferDataToPermissionTable(role.getRole_id());
		}

		try {
			// roleService.addRoles(role);
			commonService.saveOrUpdateModelObjectToDB(role);
			System.out.print("Insert Successfull");
		} catch (Exception e) {
			throw e;
		}	
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		return new ModelAndView("admin/adminPanel", model);

	}
	
	// save all Object for a new Role
	@SuppressWarnings("unchecked")
	private void saveObjectReferDataToPermissionTable(Integer roleId){
		List<ObjectReference> orList = (List<ObjectReference>)(Object)
				commonService.getAllObjectList("com.ibcs.desco.admin.model.ObjectReference");
		for(ObjectReference or :orList){
			PermissionTable pt = new PermissionTable(null, roleId, or.getId(), 0, 0, 0, 0, 0, true, null, commonService.getAuthUserName(), new Date());
			commonService.saveOrUpdateModelObjectToDB(pt);
		}
	}

	// for Delete a Role
	@RequestMapping(value = "/role/delete.do", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#role, 'DELETE')")
	public ModelAndView deleteRole(@ModelAttribute("command") Roles role,
			BindingResult result) {
		roleService.deleteRoles(role);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		return new ModelAndView("admin/adminPanel", model);
	}

	// for edit an Roles
	@RequestMapping(value = "/role/edit.do", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#role, 'EDIT')")
	public ModelAndView deleteRoles(@ModelAttribute("command") Roles role,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", roleService.getRoles(role.getId()));
		model.put("roleList", roleService.listRoles());
		return new ModelAndView("admin/adminPanel", model);
	}

	// for save an Permission
	@RequestMapping(value = "/permission/save.do", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#permission, 'WRITE')")
	public ModelAndView savePermission(
			@Valid @ModelAttribute("command") PermissionTable permission,
			BindingResult result) throws Exception {
		permission.setP_id(null);

		try {
			permissionService.addPermissionTable(permission);
		} catch (Exception e) {
			throw e;
		}
		List<AuthUser> users = userService.listAuthUsersByRoleId(permission
				.getRole_id());

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(permission.getRole_id());
		for (int i = 0; i < permissions.size(); i++) {
			ObjectReference or = objectRefService
					.getObjectReference(permissions.get(i).getObject_id());
			permissions.get(i).setObjectName(or.getDisplay_name());

		}

		// Object List get
		List<ObjectReference> objectList = objectRefService.listObjects();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("userList", users);
		model.put("permissionList", permissions);
		model.put("objectList", objectList);
		return new ModelAndView("admin/adminPanel", model);
	}

	// for save an Permission
	@RequestMapping(value = "/permission/update.do", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#permission, 'EDIT')")
	public ModelAndView updatePermission(
			@Valid @ModelAttribute("command") PermissionTable permission,
			BindingResult result) throws Exception {
		try {
			permissionService.addPermissionTable(permission);
		} catch (Exception e) {
			throw e;
		}
		List<AuthUser> users = userService.listAuthUsersByRoleId(permission
				.getRole_id());

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(permission.getRole_id());

		// Object List get
		List<ObjectReference> objectList = objectRefService.listObjects();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("userList", users);
		model.put("objectList", objectList);
		model.put("permissionList", permissions);
		return new ModelAndView("admin/adminPanel", model);
	}

	// for Delete a permission
	@RequestMapping(value = "/permission/delete.do", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#permission, 'DELETE')")
	public ModelAndView deletePermission(
			@Valid @ModelAttribute("command") PermissionTable permission) {
		permissionService.deletePermissionTable(permission);
		List<AuthUser> users = userService.listAuthUsersByRoleId(permission
				.getRole_id());

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(permission.getRole_id());

		// Object List get
		List<ObjectReference> objectList = objectRefService.listObjects();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("userList", users);
		model.put("permissionList", permissions);
		model.put("objectList", objectList);
		return new ModelAndView("admin/adminPanel", model);
	}
}
