package com.ibcs.desco.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.bean.CommitteeConvenerBean;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CommitteeConvener;
import com.ibcs.desco.cs.service.AuctionService;

/*
 * Author: Ihteshamul Alam
 * Programmer, IBCS
 */

@Controller
public class CommitteeConvenerController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AuctionService auctionService;
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/cc/committeeConvenerForm.do", method=RequestMethod.GET)
	public ModelAndView committeeConvenerForm() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Integer> contractorUserList =  auctionService.getContractorUserIdList();
		
		List<AuthUser> overallUserlist = ( List<AuthUser> ) ( Object) commonService.getAllObjectList("com.ibcs.desco.admin.model.AuthUser");
		
		Map<Integer, AuthUser> userMap = new HashMap<Integer, AuthUser>();
		for (AuthUser a : overallUserlist) {
			userMap.put(a.getId(), a);
		}
		
		for (Integer a : contractorUserList) {
			System.out.println(a);
			userMap.remove(a);
		}
		
		List<CommitteeConvener> committeeConvenerList = ( List<CommitteeConvener> ) ( Object ) commonService
				.getAllObjectList("CommitteeConvener");
		
		for( CommitteeConvener a : committeeConvenerList ) {
			userMap.remove( a.getAuthUser().getId() );
		}
		
		List<AuthUser> list = new ArrayList<AuthUser>(userMap.values());
		
		model.put("authuserList", list);
		
		return new ModelAndView("admin/committeeConvener", model);
	}
	
	@RequestMapping(value="/cc/loadUserInfo.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadUserInfo(@RequestBody String json) throws Exception {
		
		/*String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());*/
		
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuthUser authUser = gson.fromJson(json,
					AuthUser.class);
			Integer id = authUser.getId();

			AuthUser authUserDb = (AuthUser) (Object) commonService
					.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser",
							"id", id.toString());

			toJson = ow.writeValueAsString(authUserDb);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}
	
	@RequestMapping(value="/cc/saveCommitteeConvener.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveCommitteeConvener( CommitteeConvenerBean committeeConvenerBean ) {
		List<Integer> idList = null;
		
		idList = committeeConvenerBean.getId();
		
		for( int i = 0; i < idList.size(); i++ ) {
			AuthUser authUser = ( AuthUser ) commonService
					.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "id", idList.get(i).toString() );
			
			CommitteeConvener committeeConvener = new CommitteeConvener();
			
			if( authUser != null ) {
				committeeConvener.setId(null);
				committeeConvener.setAuthUser(authUser);
				committeeConvener.setCreatedBy( commonService.getAuthUserName() );
				committeeConvener.setCreatedDate( new Date() );
				
				commonService.saveOrUpdateModelObjectToDB(committeeConvener);
			}
		}
		
		return new ModelAndView("redirect:/cc/committeeConvenerList.do");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cc/committeeConvenerList.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView committeeConvenerList() {
		
		Map<String, Object> model = new HashMap<String, Object>();

		List<CommitteeConvener> committeeConvener = ( List<CommitteeConvener> ) ( Object ) commonService
				.getAllObjectList("CommitteeConvener");
		model.put("committeeConvener", committeeConvener);
		return new ModelAndView("admin/committeeConvenerList", model);
	}
	
	@RequestMapping(value="/cc/deleteUserFromCommitteeConvener.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteUserFromCommitteeConvener( CommitteeConvener committeeConvener ) {
		Integer id = committeeConvener.getId();
		
		commonService.deleteAnObjectById("CommitteeConvener", id);
		return new ModelAndView("redirect:/cc/committeeConvenerList.do");
	}
}
