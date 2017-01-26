package com.ibcs.desco.procurement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.procurement.model.ProcurementProcessCommittee;

/*
 * Author: Shimul, IBCS 
 * Date: 30/08/2016
 * 
 */

@Controller
public class ProcurementProcess {
	
	@Autowired
	CommonService commonService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/process/procurementProcessForm.do", method=RequestMethod.GET)
	public ModelAndView procurementProcessForm() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<AuthUser> userlist = ( List<AuthUser> ) ( Object ) commonService.getAllObjectList("com.ibcs.desco.admin.model.AuthUser");
		model.put("userlist", userlist);
		return new ModelAndView("procurement/process/procurementProcessForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/process/procurementProcessSave.do", method=RequestMethod.POST)
	public ModelAndView procurementProcessSave( AuthUser authUser ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			String userid = authUser.getUserid();
			Boolean isActive = authUser.isActive();
			String remarks = authUser.getRemarks();
			
			AuthUser user = ( AuthUser ) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "id", userid.toString());
			
			ProcurementProcessCommittee procProcessCommittee = (ProcurementProcessCommittee) commonService.getAnObjectByAnyUniqueColumn("ProcurementProcessCommittee", "authUser.id", userid);
			if( procProcessCommittee == null ) {
				ProcurementProcessCommittee procProsComm = new ProcurementProcessCommittee();
				procProsComm.setId(null);
				procProsComm.setAuthUser(user);
				procProsComm.setActive(isActive);
				procProsComm.setRemarks(remarks);
				procProsComm.setCreatedBy( commonService.getAuthUserName() );
				procProsComm.setCreatedDate( new Date() );
				
				commonService.saveOrUpdateModelObjectToDB( procProsComm );
			}
			
			List<ProcurementProcessCommittee> procCommList = ( List<ProcurementProcessCommittee> )  ( Object ) commonService.getAllObjectList("ProcurementProcessCommittee");
			model.put("procCommList", procCommList);
			return new ModelAndView("procurement/process/procurementProcessList", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/process/procurementProcessList.do", method=RequestMethod.GET)
	public ModelAndView procurementProcessList() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ProcurementProcessCommittee> procCommList = ( List<ProcurementProcessCommittee> )  ( Object ) commonService.getAllObjectList("ProcurementProcessCommittee");
			model.put("procCommList", procCommList);
			return new ModelAndView("procurement/process/procurementProcessList", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
	
	@RequestMapping(value="/process/editProcurementProcess.do", method=RequestMethod.POST)
	public ModelAndView editProcurementProcess( ProcurementProcessCommittee procurementProcessCommittee) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = procurementProcessCommittee.getId();
			ProcurementProcessCommittee ppComm = ( ProcurementProcessCommittee ) commonService.getAnObjectByAnyUniqueColumn("ProcurementProcessCommittee", "id", id.toString());
			model.put("ppComm", ppComm);
			return new ModelAndView("procurement/process/editprocurementProcess", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/process/procurementProcessUpdate.do", method=RequestMethod.POST)
	public ModelAndView procurementProcessUpdate( ProcurementProcessCommittee procurementProcessCommittee ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = procurementProcessCommittee.getId();
			Boolean isActive = procurementProcessCommittee.isActive();
			String remarks = procurementProcessCommittee.getRemarks();
			
			ProcurementProcessCommittee procProcessCommittee = (ProcurementProcessCommittee) commonService.getAnObjectByAnyUniqueColumn("ProcurementProcessCommittee", "id", id.toString());
			procProcessCommittee.setActive(isActive);
			procProcessCommittee.setRemarks(remarks);
			procProcessCommittee.setModifiedBy( commonService.getAuthUserName() );
			procProcessCommittee.setModifiedDate( new Date() );
			
			commonService.saveOrUpdateModelObjectToDB(procProcessCommittee);
			
			List<ProcurementProcessCommittee> procCommList = ( List<ProcurementProcessCommittee> )  ( Object ) commonService.getAllObjectList("ProcurementProcessCommittee");
			model.put("procCommList", procCommList);
			return new ModelAndView("procurement/process/procurementProcessList", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/process/deleteProcurementProcess.do", method=RequestMethod.POST)
	public ModelAndView deleteProcurementProcess( ProcurementProcessCommittee procurementProcessCommittee ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = procurementProcessCommittee.getId();
			commonService.deleteAnObjectById("ProcurementProcessCommittee", id);
			
			List<ProcurementProcessCommittee> procCommList = ( List<ProcurementProcessCommittee> )  ( Object ) commonService.getAllObjectList("ProcurementProcessCommittee");
			model.put("procCommList", procCommList);
			return new ModelAndView("procurement/process/procurementProcessList", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
}
