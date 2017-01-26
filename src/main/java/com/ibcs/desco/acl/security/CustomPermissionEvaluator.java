package com.ibcs.desco.acl.security;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibcs.desco.acl.controller.StaticResourceHelper;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
/**
 * A custom PermissionEvaluator implementation that uses a Map to check whether
 * a domain Object and access level exists for a particular user. This also uses
 * RoleHiearchy to retrieve the highest role possible for the user.
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

	protected static Logger logger = Logger.getLogger("security");
	// @Resource(name="permissionsMaping")
	@SuppressWarnings("rawtypes")
	public Map permissionsMap;

	@SuppressWarnings("rawtypes")
	public Map getPermissionsMap() {
		return permissionsMap;
	}

	public void setPermissionsMap() {
		this.permissionsMap = this.getp();
	}

	@Resource(name = "roleHierarchy")
	private RoleHierarchy roleHierarchy;

	public CustomPermissionEvaluator() {
		// this.permissionsMap=getp();
	}

	/**
	 * Evaluates whether the user has permission by delegating to
	 * hasPermission(String role, Object permission, Object domain)
	 */
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		// AccessControlListTag a=new AccessControlListTag();
		logger.debug("Evaluating expression using hasPermission signature #1");
		this.setPermissionsMap();
		logger.debug("Retrieving user's highest role");
		String role = getRole(authentication);

		logger.debug("****************");
		logger.debug("role: " + role);
		logger.debug("targetDomainObject: " + targetDomainObject);
		logger.debug("permission: " + permission);
		logger.debug("****************");

		// Check the type of object
		logger.debug("User is trying to access the object: "
				+ targetDomainObject);

		logger.debug("Check if user has permission");
		// Delegate to another hasPermission signature
		return hasPermission(role, permission, targetDomainObject);
	}

	/**
	 * Another hasPermission signature. We will not implement this.
	 */
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		logger.debug("Evaluating expression using hasPermission signature #2");

		return false;
	}

	/**
	 * Retrieves the user's highest role
	 */
	private String getRole(Authentication authentication) {
		String highestRole = null;

		try {
			/*
			 * roleHierarchy.getReachableGrantedAuthorities() Returns an array
			 * of all reachable authorities. Reachable authorities are the
			 * directly assigned authorities plus all authorities that are
			 * (transitively) reachable from them in the role hierarchy.
			 * 
			 * Example: Role hierarchy: ROLE_A > ROLE_B and ROLE_B > ROLE_C.
			 * Directly assigned authority: ROLE_A. Reachable authorities:
			 * ROLE_A, ROLE_B, ROLE_C.
			 */
			// Collection<? extends GrantedAuthority> auths = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());
			Collection<? extends GrantedAuthority> auths = authentication
					.getAuthorities();
			for (GrantedAuthority auth : auths) {
				highestRole = auth.getAuthority();				
				break;
			}
			logger.debug("Highest role hiearchy: "
					+ roleHierarchy
							.getReachableGrantedAuthorities(authentication
									.getAuthorities()));

		} catch (Exception e) {
			logger.debug("No authorities assigned");
		}

		return highestRole;
	}

	/**
	 * Evaluates whether the user has permission
	 */
	private Boolean hasPermission(String role, Object permission, Object domain) {
		logger.debug("Check if role exists: " + role);

		// System.out.println("permissionMap called");
		if (this.permissionsMap.containsKey(role)) {

			logger.debug("Role exists: " + role);

			// Retrieve userPermission object
			Permission userPermission = (Permission) permissionsMap.get(role);

			// Check if domain exists in Map
			logger.debug("Check if domain exists: "
					+ domain.getClass().getName());
			if (userPermission.getObjects().containsKey(
					domain.getClass().getName())) {
				logger.debug("Domain exists: " + domain.getClass().getName());

				// Loop the internal list and see if the class' full name
				// matches
				logger.debug("Check if permission exists: " + permission);
				for (String action : userPermission.getObjects().get(
						domain.getClass().getName())) {
					if (action.equals(permission)) {
						logger.debug("Permission exists: " + action);
						logger.debug("Permission Granted!");
						return true;
					}
				}
			}
		}

		// By default, do not give permission
		logger.debug("Permission Denied!");
		return false;
	}

	@SuppressWarnings("rawtypes")
	private Map getp() {
		/*
		 * PermInjection pi=new PermInjection(); =new
		 * HashMap<String,Permission>();
		 * x.put("ROLE_ADMIN",pi.adminPermission());
		 * x.put("ROLE_USER",pi.UserPermission());
		 */

		// AclManipulation ac=new AclManipulation();
		Map<String, Permission> x = StaticResourceHelper.getLoadPermission();
		return x;

	}
	
	 public boolean isAuthenticated(){

	       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	       return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

	   }
}
