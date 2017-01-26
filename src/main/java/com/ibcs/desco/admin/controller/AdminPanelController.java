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
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.Email;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.EmailService;
import com.ibcs.desco.util.EmailValidator;

@Controller
@RequestMapping(value = "/adminpanel")
public class AdminPanelController extends Constrants{

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

	@RequestMapping(value = "/userList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#user, 'READ')")
	public ModelAndView getUserList(Roles role, AuthUser user) {

		List<AuthUser> userList = userService.listAuthUsersByRoleId(role
				.getRole_id());

		for (int i = 0; i < userList.size(); i++) {
			Departments dept = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							userList.get(i).getDeptId());
			if(dept != null){
				userList.get(i).setDepartmentName(dept.getDeptName());
			}
		}

		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				role.getRole_id() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userList", userList);
		model.put("roleName", roleDb.getRole());
		model.put("role_id", role.getRole_id());
		return new ModelAndView("admin/adminPanelUsersList", model);
	}

	@RequestMapping(value = "/permissionList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#pt, 'READ')")
	public ModelAndView getPermissionList(Roles role, PermissionTable pt) {

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(role.getRole_id());

		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				role.getRole_id() + "");

		for (int i = 0; i < permissions.size(); i++) {
			ObjectReference or = objectRefService
					.getObjectReference(permissions.get(i).getObject_id());
			permissions.get(i).setObjectName(or.getDisplay_name());

		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("permissionList", permissions);
		model.put("roleName", roleDb.getRole());
		model.put("role_id", roleDb.getRole_id());
		return new ModelAndView("admin/adminPanelPermissionList", model);
	}

	// for save an AuthUser
	@RequestMapping(value = "/user/save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#authUser, 'WRITE')")
	public ModelAndView saveAuthUser(
			@Valid @ModelAttribute("command") AuthUser authUser,
			BindingResult result) throws Exception {

		String userPassword = authUser.getPassword();
		String userName = authUser.getUserid();
		String userEmail = authUser.getEmail();
		AuthUser authUserByUserName = (com.ibcs.desco.admin.model.AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userName);

		AuthUser authUserByUserEmail = (com.ibcs.desco.admin.model.AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "email",
						userEmail);

		if (authUserByUserName != null || authUserByUserEmail != null) {
			Roles role = new Roles();
			role.setRole_id(authUser.getRoleid());
			return getNewUserForm(role, result);
		}

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
			authUser.setCreatedBy(commonService.getAuthUserName());
			authUser.setCreatedDate(new Date());
			authUser.setUserType(USER_TYPE_DESCO);
			userService.addAuthUser(authUser);
		} catch (Exception e) {
			throw e;
		}
		List<AuthUser> userList = userService.listAuthUsersByRoleId(authUser
				.getRoleid());
		String body = "Hi Mr./Mrs. "+authUser.getName()+",\nCongratulation! You are the new member of "
				+ "Web Based Desco Inventory Management System(IMS). "
				+ "\nUsername is: "
				+ authUser.getUserid()
				+ "\nPassword is: "
				+ userPassword+" \nThanks.";
		email.setRecipint(authUser.getEmail());
		email.setSubject("Registration Information of Web Based Desco Inventory Management System(IMS).");
		email.setWrittenInformation(body);
		emailService.sendMail(email, null);

		/*Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				authUser.getRoleid() + "");

		for (int i = 0; i < userList.size(); i++) {
			Departments dept = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							userList.get(i).getDeptId());
			userList.get(i).setDepartmentName(dept.getDeptName());
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("role_id", authUser.getRoleid());
		model.put("roleName", roleDb.getRole());
		model.put("userList", userList);
		return new ModelAndView("admin/adminPanelUsersList", model);*/
		return new ModelAndView("redirect:/adminPanel.do");
	}
	
		// for update an AuthUser
		@RequestMapping(value = "/user/update.do", method = RequestMethod.POST)
		@PreAuthorize("isAuthenticated()")
		@PostAuthorize("hasPermission(#authUser, 'WRITE')")
		public ModelAndView updateAuthUser(
				@Valid @ModelAttribute("command") AuthUser authUser,
				BindingResult result) throws Exception {

			String id = authUser.getId().toString();
			
			AuthUser authUserById = (com.ibcs.desco.admin.model.AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "id",
							id);
			
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
				authUserById.setPassword(hashedPassword.toString());
//				authUserById.setCreatedBy(commonService.getAuthUserName());
//				authUserById.setCreatedDate(new Date());
				
				authUserById.setRoleid(authUser.getRoleid());
				authUserById.setName(authUser.getName());
				authUserById.setDeptId(authUser.getDeptId());
				authUserById.setDepartmentName(authUser.getDepartmentName());
				authUserById.setDesignation(authUser.getDesignation());
				authUserById.setEmpId(authUser.getEmpId());
				authUserById.setActive( authUser.isActive() );
				authUserById.setLocked( authUser.getLocked() );
				authUserById.setModifiedBy( commonService.getAuthUserName() );
				authUserById.setModifiedDate( new Date() );
				userService.addAuthUser(authUserById);
			} catch (Exception e) {
				throw e;
			}
			
			String body = "Hi Mr./Mrs. "+authUser.getName() 
					+ "\nUser name: " + authUserById.getUserid()
					+",\nYour profile is updated by Admin for "
					+ "Web Based Desco Inventory Management System(IMS). "
					+ " \nThanks.";
			email.setRecipint(authUserById.getEmail());
			email.setSubject("Update Information of Web Based Desco Inventory Management System(IMS).");
			email.setWrittenInformation(body);
			emailService.sendMail(email, null);			
			return new ModelAndView("redirect:/adminPanel.do");			
		}

	// for Delete a User
	@RequestMapping(value = "/user/delete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#authUser, 'DELETE')")
	public ModelAndView deleteAuthUser(
			@Valid @ModelAttribute("command") AuthUser authUser) {

		AuthUser authUserDb = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "id",
						authUser.getId() + "");

		userService.deleteAuthUser(authUser);
		List<AuthUser> userList = userService.listAuthUsersByRoleId(authUserDb
				.getRoleid());
		for (int i = 0; i < userList.size(); i++) {
			Departments dept = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							userList.get(i).getDeptId());
			userList.get(i).setDepartmentName(dept.getDeptName());
		}

		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				authUserDb.getRoleid() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userList", userList);
		model.put("roleName", roleDb.getRole());
		model.put("role_id", authUserDb.getRoleid());
		return new ModelAndView("admin/adminPanelUsersList", model);
	}

	// for save an Role
	@RequestMapping(value = "/role/save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#role, 'WRITE')")
	public ModelAndView saveRoles(@Valid @ModelAttribute("command") Roles role,
			BindingResult result) throws Exception {
		String roleName = role.getRole().toUpperCase();
		role.setRole(roleName);
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
			role.setCreatedBy(commonService.getAuthUserName());
			role.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(role);
			System.out.print("Insert Successfull");
			return new ModelAndView("redirect:/adminPanel.do");
		} catch (Exception e) {
			throw e;
		}
		//return new ModelAndView("redirect:/adminPanel.do");
	}

	// save all Object for a new Role
	@SuppressWarnings("unchecked")
	private void saveObjectReferDataToPermissionTable(Integer roleId) {
		List<ObjectReference> orList = (List<ObjectReference>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.ObjectReference");
		for (ObjectReference or : orList) {
			PermissionTable pt = new PermissionTable(null, roleId, or.getId(),
					0, 0, 0, 0, 0, true, null, commonService.getAuthUserName(),
					new Date());
			commonService.saveOrUpdateModelObjectToDB(pt);
		}
	}

	// for Delete a Role
	@RequestMapping(value = "/role/delete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#role, 'DELETE')")
	public ModelAndView deleteRole(@ModelAttribute("command") Roles role,
			BindingResult result) {
		roleService.deleteRoles(role);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("deleteflag", 1);
		return new ModelAndView("redirect:/adminPanel.do");
	}

	// for edit an Roles
	@RequestMapping(value = "/role/edit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#role, 'EDIT')")
	public ModelAndView deleteRoles(@ModelAttribute("command") Roles role,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", roleService.getRoles(role.getId()));
		model.put("roleList", roleService.listRoles());
		return new ModelAndView("redirect:/adminPanel.do");
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
	@RequestMapping(value = "/permission/update.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#permission, 'EDIT')")
	public ModelAndView updatePermission(
			@Valid @ModelAttribute("command") PermissionTable permission,
			BindingResult result) throws Exception {
		try {
			permissionService.addPermissionTable(permission);
		} catch (Exception e) {
			throw e;
		}

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(permission.getRole_id());

		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				permission.getRole_id() + "");

		for (int i = 0; i < permissions.size(); i++) {
			ObjectReference or = objectRefService
					.getObjectReference(permissions.get(i).getObject_id());
			permissions.get(i).setObjectName(or.getDisplay_name());

		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("permissionList", permissions);
		model.put("roleName", roleDb.getRole());
		return new ModelAndView("admin/adminPanelPermissionList", model);
	}

	// for Delete a permission
	@RequestMapping(value = "/permission/delete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#permission, 'DELETE')")
	public ModelAndView deletePermission(
			@Valid @ModelAttribute("command") PermissionTable permission) {

		permissionService.deletePermissionTable(permission);

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(permission.getRole_id());
		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				permission.getRole_id() + "");

		for (int i = 0; i < permissions.size(); i++) {
			ObjectReference or = objectRefService
					.getObjectReference(permissions.get(i).getObject_id());
			permissions.get(i).setObjectName(or.getDisplay_name());

		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("permissionList", permissions);
		model.put("roleName", roleDb.getRole());
		model.put("deleteflag", 1);
		return new ModelAndView("admin/adminPanelPermissionList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNewUserForm.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#role, 'READ')")
	public ModelAndView getNewUserForm(
			@Valid @ModelAttribute("command") Roles role, BindingResult result) {

		List<Departments> departmentList = (List<Departments>) (Object) commonService
				.getAllObjectList("Departments");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("role_id", role.getRole_id());
		model.put("departmentList", departmentList);
		return new ModelAndView("admin/adminPanelUserForm", model);
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUserForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#authUser, 'WRITE')")
	public ModelAndView getUserForm(AuthUser authUser) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Departments> departmentList = (List<Departments>) (Object) commonService
					.getAllObjectList("Departments");

			model.put("roleList", roleService.listRoles());
			model.put("departmentList", departmentList);
			return new ModelAndView("admin/userForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("admin/errorAD", model);
		}
	}

	// Added By Ashid
	@RequestMapping(value = "/saveUser.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated() and hasPermission(#authUser, 'WRITE')")
	public ModelAndView saveUser(@ModelAttribute("authUser") AuthUser authUser)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userPassword = authUser.getPassword();
			String userId = authUser.getUserid();
			String userEmail = authUser.getEmail();
			AuthUser authUserByUserName = (com.ibcs.desco.admin.model.AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userId);

			AuthUser authUserByUserEmail = (com.ibcs.desco.admin.model.AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "email",
							userEmail);

			if (authUserByUserName != null || authUserByUserEmail != null) {
				Roles role = new Roles();
				role.setRole_id(authUser.getRoleid());
				return getNewUserForm(role, null);
			}

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
				authUser.setCreatedBy(commonService.getAuthUserName());
				authUser.setCreatedDate(new Date());
				userService.addAuthUser(authUser);
			} catch (Exception e) {
				throw e;
			}
			List<AuthUser> userList = userService
					.listAuthUsersByRoleId(authUser.getRoleid());
			String body = "Your are the new member of Desco Inventory System. your username is: "
					+ authUser.getUserid()
					+ " And your password is: "
					+ userPassword;
			email.setRecipint(authUser.getEmail());
			email.setSubject("Urgent Notification");
			email.setWrittenInformation(body);
			emailService.sendMail(email, null);

			Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role_id",
					authUser.getRoleid() + "");

			for (int i = 0; i < userList.size(); i++) {
				Departments dept = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments", "deptId",
								userList.get(i).getDeptId());
				userList.get(i).setDepartmentName(dept.getDeptName());
			}

			model.put("role_id", authUser.getRoleid());
			model.put("roleName", roleDb.getRole());
			model.put("userList", userList);
			return new ModelAndView("admin/adminPanelUsersList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("admin/errorAD", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/edit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#authUser, 'EDIT')")
	public ModelAndView getUserEditForm(
			@Valid @ModelAttribute("command") AuthUser authUser,
			BindingResult result) {

		AuthUser currentUser = userService.getAuthUser(authUser.getId());
		List<Departments> departmentList = (List<Departments>) (Object) commonService
				.getAllObjectList("Departments");

		Departments dept = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId",
						currentUser.getDeptId());

		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				currentUser.getRoleid() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("departmentList", departmentList);
		model.put("currentUser", currentUser);
		model.put("role", roleDb);
		model.put("dept", dept);
		return new ModelAndView("admin/adminPanelUserEditForm", model);
	}

	@RequestMapping(value = "/addAllObject.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#pt, 'WRITE')")
	public ModelAndView addObjectInPermissionList(Roles role, PermissionTable pt) {

		List<ObjectReference> objectRefList = objectRefService.listObjects();

		List<PermissionTable> currentPermissions = permissionService
				.listPermissionByRoleId(role.getRole_id());

		if (objectRefList.size() != currentPermissions.size()) {
			for (ObjectReference orf : objectRefList) {
				boolean found = false;
				for (PermissionTable permTab : currentPermissions) {
					if (orf.getId() == permTab.getObject_id()) {
						found = true;
						break;
					}
				}
				if (!found) {
					PermissionTable p = new PermissionTable(null,
							role.getRole_id(), orf.getId(), 0, 0, 0, 0, 0,
							true, null, commonService.getAuthUserName(),
							new Date());
					permissionService.addPermissionTable(p);
				}
			}
		}

		List<PermissionTable> permissions = permissionService
				.listPermissionByRoleId(role.getRole_id());

		Roles roleDb = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Roles", "role_id",
				role.getRole_id() + "");

		for (int i = 0; i < permissions.size(); i++) {
			ObjectReference or = objectRefService
					.getObjectReference(permissions.get(i).getObject_id());
			permissions.get(i).setObjectName(or.getDisplay_name());

		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("permissionList", permissions);
		model.put("roleName", roleDb.getRole());
		model.put("role_id", roleDb.getRole_id());
		return new ModelAndView("admin/adminPanelPermissionList", model);
	}

	@RequestMapping(value = "/checkUserRole.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkUserRole(Roles u) {
		String roleName = u.getRole().toUpperCase();
		String out = "";
		if (u.getRole().equals("")) {
			out = "unsuccess";
		} else {
			Roles ss = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role", roleName);

			if (ss == null) {
				out = "success";
			} else {
				out = "unsuccess";
			}
		}

		return out;
	}

	/*
	 * public String checkUser(@RequestBody String json, Roles u ) throws
	 * Exception { Gson gson = new GsonBuilder().create(); Boolean isJson =
	 * commonService.isJSONValid(json); String result = ""; String toJson = "";
	 * //Roles u = new Roles(); ObjectWriter ow = new ObjectMapper().writer()
	 * .withDefaultPrettyPrinter(); if (isJson) { Roles roles =
	 * gson.fromJson(json, Roles.class);
	 * 
	 * if (roles.getRole().equals("")) { result = "unsuccess"; } else {
	 * 
	 * u = (Roles) commonService.getAnObjectByAnyUniqueColumn( "Roles", "role",
	 * roles.getRole()); if (u == null) { result = "success"; } else { result =
	 * "unsuccess"; } }
	 * 
	 * toJson = ow.writeValueAsString(result);
	 * 
	 * } else { Thread.sleep(5000); } return toJson; }
	 */

	@RequestMapping(value = "/checkEmail.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkEmail(AuthUser authUser) {
		String email = authUser.getEmail();
		EmailValidator ev = new EmailValidator();
		boolean result = ev.validate(email);
		String response = "";
		if (!result) {
			response = "unsuccess";
		} else {
			AuthUser aUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "email",
							email);
			if (aUser == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}

	@RequestMapping(value = "/checkUserId.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkUserId(AuthUser authUser) {
		// String email = authUser.getEmail();
		String response = "";
		if (authUser.getUserid().equals("")) {
			response = "unsuccess";
		} else {
			AuthUser aUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							authUser.getUserid());
			if (aUser == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}
}
