package com.ibcs.desco.acl.service;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ibcs.desco.acl.dao.AclManipulation;
import com.ibcs.desco.acl.dao.DBAction;
import com.ibcs.desco.acl.model.AuthUser;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class MyUserDetailsService implements UserDetailsService {

	// @Resource(name = "sessionRegistry")

	@Autowired
	private SessionRegistryImpl sessionRegistry;
	
	protected static Logger logger = Logger.getLogger("service");

	DBAction dba;

	// load user information before login
	@SuppressWarnings("unused")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		// logger.info("hello");
		// Assembler a=new Assembler();

		AclManipulation ac = new AclManipulation();
		DBAction db = new DBAction();
		AuthUser userx = new AuthUser();
		List<AuthUser> l = db.searchUser(username);
		try {
			userx = l.get(0);
			// added by taleb
			userx.setLoginCounter(userx.getLoginCounter()+1);
			db.update(userx);	
			// logger.info(userx.getUserid()+"hellop");
		} catch (Exception e) {
			logger.info(e + "eror");
		}

		String xusername = userx.getUserid();
		String password = userx.getPassword();
		//boolean enabled = true;
		boolean enabled = userx.isActive();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		//boolean accountNonLocked = true;
		boolean accountNonLocked = !(userx.isLocked());
		// UserDetails userDetails = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
				2);

		authorities.add(new SimpleGrantedAuthority(ac.getRoleName(userx
				.getRoleid())));
		// if(userx.getRoleid()==1)
		// authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		// else {
		// authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		// }

		// System.out.println(userx.getRoleid());

		List<Object> l1 = sessionRegistry.getAllPrincipals();

		// for(int j=0;j<l1.size();j++){
		// User u2=(User) l1.get(j);
		// System.out.println(u2.getUsername()+"\n");
		//
		// if(xusername.equals(u2.getUsername())){
		// accountNonExpired = false;
		// enabled=false;
		// credentialsNonExpired=false;
		// }
		// }

		User user = new User(xusername, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}
}
