package com.ibcs.desco.cs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.Email;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.EmailService;
import com.ibcs.desco.cs.bean.AuctionCommitteeDto;
import com.ibcs.desco.cs.bean.CondemnReportBean;
import com.ibcs.desco.cs.model.AuctionCommittees;
import com.ibcs.desco.cs.model.AuctionProcessMst;
import com.ibcs.desco.cs.model.CommitteeConvener;
import com.ibcs.desco.cs.model.CondemnDtl;
import com.ibcs.desco.cs.model.CondemnMst;
import com.ibcs.desco.cs.service.AuctionService;
import com.ibcs.desco.inventory.model.ItemMaster;

/*
 * @author Ahasanul Ashid
 * @designation Programmer
 * @company IBCS Primax Software Ltd.
 */

@Controller
@PropertySource("classpath:common.properties")
public class CondemnItemController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CommonService commonService;

	@Autowired
	AuctionService auctionService;
	

	@Autowired
	EmailService emailService;
	
	@Autowired
    private JavaMailSender mailSender;

	@Value("${desco.project.rootPath}")
	private String descoRootPath;

	@RequestMapping(value = "/auction/cc/sendToAdmin.do", method = RequestMethod.POST)
	public ModelAndView sendToAdmin(String id) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CondemnMst mstDB = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "id", id);

			if (mstDB != null) {

				AuctionProcessMst acMst = mstDB.getAuctionProcessMst();
				acMst.setCc_to_admin_flag(CONFIRMED);
				acMst.setCc_to_admin_date(new Date());
				acMst.setModifiedBy(commonService.getAuthUserName());
				acMst.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(acMst);
			}

			return new ModelAndView(
					"redirect:/auction/cc/createCcReport.do?id=" + id);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cc/createCcReport.do", method = RequestMethod.GET)
	public ModelAndView getUserviceableItems(String id) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<CondemnDtl> cDtlList = (List<CondemnDtl>) (Object) commonService
					.getObjectListByAnyColumn("CondemnDtl", "condemnMst.id", id);

			if (cDtlList != null && cDtlList.size() > 0) {
				Map<Integer, ItemMaster> itemMap = new HashMap<Integer, ItemMaster>();

				for (CondemnDtl d : cDtlList) {
					itemMap.put(d.getItemMaster().getId(), d.getItemMaster());
				}
				List<CondemnReportBean> beanList = new ArrayList<CondemnReportBean>();
				for (Integer key : itemMap.keySet()) {
					CondemnReportBean bean = new CondemnReportBean();
					Double totalQty = 0.0;
					bean.setItemMaster(itemMap.get(key));
					List<CondemnDtl> tempList = new ArrayList<CondemnDtl>();
					for (CondemnDtl d : cDtlList) {
						if (key.equals(d.getItemMaster().getId())) {
							tempList.add(d);
							totalQty += d.getCondemnQty();
						}
					}
					bean.setDtlList(tempList);
					bean.setTotalQty(totalQty);
					beanList.add(bean);
				}

				CondemnMst condemnMst = cDtlList.get(0).getCondemnMst();
				model.put("condemnMst", condemnMst);
				model.put("beanList", beanList);
			}

			return new ModelAndView(
					"centralStore/auction/condemnCommitteeReportForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	@RequestMapping(value = "/auction/cc/updateCondemnReport.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateItemByAjax(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			CondemnReportBean bean = gson.fromJson(json,
					CondemnReportBean.class);
			List<CondemnDtl> dtlList = bean.getDtlList();

			for (CondemnDtl d : dtlList) {
				CondemnDtl dtlDb = (CondemnDtl) commonService
						.getAnObjectByAnyUniqueColumn("CondemnDtl", "id", d
								.getId().toString());
				dtlDb.setCondemnQty(d.getCondemnQty());
				dtlDb.setShortQty(dtlDb.getQty() - dtlDb.getCondemnQty());
				dtlDb.setModifiedBy(commonService.getAuthUserName());
				dtlDb.setModifiedDate(new Date());
				dtlDb.setRemarks(d.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(dtlDb);
			}

			toJson = ow.writeValueAsString("success");

		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cs/loadAuctionDtl.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadAuctionDtl(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionProcessMst bean = gson.fromJson(json,
					AuctionProcessMst.class);

			List<CondemnDtl> condemnDtlList = (List<CondemnDtl>) (Object) commonService
					.getObjectListByAnyColumn("CondemnDtl",
							"condemnMst.auctionProcessMst.auctionName",
							bean.getAuctionName());
			toJson = ow.writeValueAsString(condemnDtlList);

		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/addCcConvener.do", method = RequestMethod.GET)
	public ModelAndView addCcConvener(String apId) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<CommitteeConvener> convenerList = (List<CommitteeConvener>) (Object) commonService
					.getAllObjectList("CommitteeConvener");

			List<CondemnMst> condemnMstList = (List<CondemnMst>) (Object) commonService
					.getAllObjectList("CondemnMst");

			if (apId != null && !apId.equals("")) {
				model.put("apId", apId);
			}

			model.put("convenerList", convenerList);
			model.put("condemnMstList", condemnMstList);

			return new ModelAndView("centralStore/auction/assignCCForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/addAcConvener.do", method = RequestMethod.GET)
	public ModelAndView addAcConvener(String apId) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<CommitteeConvener> convenerList = (List<CommitteeConvener>) (Object) commonService
					.getAllObjectList("CommitteeConvener");

			List<CondemnMst> condemnMstList = (List<CondemnMst>) (Object) commonService
					.getAllObjectList("CondemnMst");

			if (apId != null && !apId.equals("")) {
				model.put("apId", apId);
			}
			model.put("convenerList", convenerList);
			model.put("condemnMstList", condemnMstList);

			return new ModelAndView("centralStore/auction/assignACForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	//this method is not using now
	//TODO: remove this method
	@RequestMapping(value = "/auction/addCcConvener.do", method = RequestMethod.POST)
	public ModelAndView addCcConvenerDetails(AuctionCommittees bean,
			@RequestParam("referenceDoc") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String pk = bean.getCondemnMstId();
			String userPk = bean.getUserId();
			String memoNo = bean.getMemoNo();
			String remarks = bean.getRemarks();
			Date cDate = bean.getCcDate();
			String filePath = commonService.saveFileToDrive(file,
					descoRootPath, "condemn_committee");

			CondemnMst condemnMst = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "auctionProcessMst.id", pk);

			this.addConvener(userPk, CONDEMN_COMMITTEE, filePath, memoNo,
					remarks, cDate, condemnMst);

			AuctionProcessMst apMst = condemnMst.getAuctionProcessMst();
			apMst.setAdmin_to_cc_flag(CONFIRMED);
			apMst.setAdmin_to_cc_date(new Date());
			apMst.setModifiedBy(commonService.getAuthUserName());
			apMst.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(apMst);

			return new ModelAndView("redirect:/auction/admin/cccList.do");
		} catch (Exception e) {
			System.out.println(e.toString());
			model.put("errorMsg", e);
			return new ModelAndView("auction/error", model);
		}
	}

	//This is not using now
	//TODO: remove this method
	@RequestMapping(value = "/auction/addAcConvener.do", method = RequestMethod.POST)
	public ModelAndView addAcConvenerDetails(AuctionCommittees bean,
			@RequestParam("referenceDoc") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String pk = bean.getCondemnMstId();
			String userPk = bean.getUserId();
			String memoNo = bean.getMemoNo();
			String remarks = bean.getRemarks();
			Date cDate = bean.getCcDate();
			String filePath = commonService.saveFileToDrive(file,
					descoRootPath, "condemn_committee");

			CondemnMst condemnMst = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "auctionProcessMst.id", pk);

			this.addConvener(userPk, AUCTION_COMMITTEE, filePath, memoNo,
					remarks, cDate, condemnMst);

			AuctionProcessMst apMst = condemnMst.getAuctionProcessMst();
			apMst.setAdmin_to_auction_flag(CONFIRMED);
			apMst.setAdmin_to_auction_date(new Date());
			apMst.setModifiedBy(commonService.getAuthUserName());
			apMst.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(apMst);

			return new ModelAndView("redirect:/auction/admin/accList.do");
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("auction/error", model);
		}
	}
	
	@RequestMapping(value = "/auction/addAcConvenerCommittee.do", method = RequestMethod.POST)
	public ModelAndView addAcConvenerCommittee(AuctionCommitteeDto bean,
			@RequestParam("referenceDoc") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String pk = bean.getCondemnMstId();
//			String userPk = bean.getUserId();
			String memoNo = bean.getMemoNo();
			String remarks = bean.getRemarks();
			Date cDate = bean.getCcDate();
			String filePath = commonService.saveFileToDrive(file,
					descoRootPath, "condemn_committee");

			CondemnMst condemnMst = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "auctionProcessMst.id", pk);
			List<String> userIdList = bean.getUserId();
			List<String> memTypes = bean.getMemberType();
			if(userIdList.size()>0){
				for(int i = 0; i<userIdList.size();i++ ){
					this.addConvenerWithType(userIdList.get(i), memTypes.get(i), AUCTION_COMMITTEE, filePath, memoNo,
							remarks, cDate, condemnMst);
				}
			}
			

			AuctionProcessMst apMst = condemnMst.getAuctionProcessMst();
			apMst.setAdmin_to_auction_flag(CONFIRMED);
			apMst.setAdmin_to_auction_date(new Date());
			apMst.setModifiedBy(commonService.getAuthUserName());
			apMst.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(apMst);

			return new ModelAndView("redirect:/auction/admin/accList.do");
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("auction/error", model);
		}
	}
	
	
	@RequestMapping(value = "/auction/addCcConvenerCommittee.do", method = RequestMethod.POST)
	public ModelAndView addCcConvenerCommittee(AuctionCommitteeDto bean,
			@RequestParam("referenceDoc") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String pk = bean.getCondemnMstId();
//			String userPk = bean.getUserId();
			String memoNo = bean.getMemoNo();
			String remarks = bean.getRemarks();
			Date cDate = bean.getCcDate();
			String filePath = commonService.saveFileToDrive(file,
					descoRootPath, "condemn_committee");

			CondemnMst condemnMst = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "auctionProcessMst.id", pk);
			List<String> userIdList = bean.getUserId();
			List<String> memTypes = bean.getMemberType();
			if(userIdList.size()>0){
				for(int i = 0; i<userIdList.size();i++ ){
					this.addConvenerWithType(userIdList.get(i), memTypes.get(i), CONDEMN_COMMITTEE, filePath, memoNo,
							remarks, cDate, condemnMst);
				}
			}
			

			AuctionProcessMst apMst = condemnMst.getAuctionProcessMst();
			apMst.setAdmin_to_cc_flag(CONFIRMED);
			apMst.setAdmin_to_cc_date(new Date());
			apMst.setModifiedBy(commonService.getAuthUserName());
			apMst.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(apMst);

			return new ModelAndView("redirect:/auction/admin/cccList.do");
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("auction/error", model);
		}
	}

	//this method is not using now.
	//TODO: remove this method
	public void addConvener(String userPk, String committeName,
			String filePath, String memoNo, String remarks, Date cDate,
			CondemnMst condemnMst) {

		AuthUser convener = userService.getAuthUser(Integer.parseInt(userPk
				.trim()));
		AuctionCommittees obj = new AuctionCommittees();
		obj.setId(null);
		obj.setCreatedBy(commonService.getAuthUserName());
		obj.setCreatedDate(new Date());
		obj.setCommitteeName(committeName);
		obj.setDocPath(filePath);
		obj.setMemoNo(memoNo);
		obj.setRemarks(remarks);
		obj.setAuthUser(convener);
		obj.setCondemnMst(condemnMst);
		obj.setCcDate(cDate);
		
		commonService.saveOrUpdateModelObjectToDB(obj);
	}
	
	public void addConvenerWithType(String userPk, String memType, String committeName,
			String filePath, String memoNo, String remarks, Date cDate,
			CondemnMst condemnMst) {

		AuthUser convener = userService.getAuthUser(Integer.parseInt(userPk
				.trim()));
		AuctionCommittees obj = new AuctionCommittees();
		obj.setId(null);
		obj.setCreatedBy(commonService.getAuthUserName());
		obj.setCreatedDate(new Date());
		obj.setCommitteeName(committeName);
		obj.setDocPath(filePath);
		obj.setMemoNo(memoNo);
		obj.setRemarks(remarks);
		obj.setAuthUser(convener);
		obj.setCondemnMst(condemnMst);
		obj.setCcDate(cDate);
		obj.setMemberType(memType);
		
		//sending mail to committee member
		Email email = new Email();
		String body = "Hi Mr./Mrs. "+convener.getName()+",\n\n You Have selected as Auction Committee "+memType+". \n Please, Login to show details.";
		email.setRecipint(convener.getEmail());
		email.setSubject("DESCO Auction Committe Member");
		email.setWrittenInformation(body);
		emailService.sendMail(email, null);
		
		/*// creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(convener.getEmail());
        email.setSubject("Convener Committe Member of Web Based Desco Inventory Management System(IMS).");
        email.setText("Hi Mr./Mrs. "+convener.getName()+",\n You Have selected as Convener Committee Member. Please, login to show details");
         
        // sends the e-mail
        mailSender.send(email);*/
		
		commonService.saveOrUpdateModelObjectToDB(obj);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/admin/cccList.do", method = RequestMethod.GET)
	public ModelAndView cccList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByTwoColumn("AuctionCommittees",
							"committeeName", CONDEMN_COMMITTEE, "active", "1");*/
			List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByThreeColumn("AuctionCommittees",
							"committeeName", CONDEMN_COMMITTEE, "active", "1", "memberType", "Secretary");

			model.put("auctionCommitteeList", auctionCommitteeList);

			return new ModelAndView("centralStore/auction/cccList", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/admin/accList.do", method = RequestMethod.GET)
	public ModelAndView accList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByTwoColumn("AuctionCommittees",
							"committeeName", AUCTION_COMMITTEE, "active", "1");*/
			List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByThreeColumn("AuctionCommittees",
							"committeeName", AUCTION_COMMITTEE, "active", "1", "memberType", "Secretary");

			model.put("auctionCommitteeList", auctionCommitteeList);

			return new ModelAndView("centralStore/auction/accList", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	//auction committee to update
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/admin/acCmtUpdate.do", method = RequestMethod.GET)
	public ModelAndView acCmtUpdate(String id, Integer type) {
		//parameter type 
		//0=CONDEMN_COMMITTEE and 
		//1=AUCTION_COMMITTEE
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByTwoColumn("AuctionCommittees",
							"committeeName", AUCTION_COMMITTEE, "active", "1");*/
			List<CommitteeConvener> convenerList = (List<CommitteeConvener>) (Object) commonService
					.getAllObjectList("CommitteeConvener");
			List<AuctionCommittees> auctionCommitteeList = new ArrayList<AuctionCommittees>(); 
			if(type != null && type ==0 ){
				auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
						.getObjectListByThreeColumn("AuctionCommittees","active", "1", "condemnMst.id", id,"committeeName", CONDEMN_COMMITTEE);
			}else if(type != null && type == 1){
				auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
						.getObjectListByThreeColumn("AuctionCommittees","active", "1", "condemnMst.id", id,"committeeName", AUCTION_COMMITTEE);
			}
			

			model.put("convenerList", convenerList);
			model.put("auctionCommitteeList", auctionCommitteeList);

			return new ModelAndView("centralStore/auction/committeeUpdateForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	@RequestMapping(value = "/auction/updateConvenerCommittee.do", method = RequestMethod.POST)
	public ModelAndView updateConvenerCommittee(AuctionCommitteeDto bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String pk = bean.getCondemnMstId();
			String committeeName = bean.getCommitteeName();
			String memoNo = bean.getMemoNo();
			String remarks = bean.getRemarks();
			Date cDate = bean.getCcDate();
			/*String filePath = commonService.saveFileToDrive(file,
					descoRootPath, "condemn_committee");*/
			CondemnMst condemnMst = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "auctionProcessMst.id", pk);
			//getting full committee added previously
			List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByThreeColumn("AuctionCommittees","active", "1", "condemnMst.id", pk,"committeeName", committeeName);

			
			List<String> userIdList = bean.getUserId();
			//List<Integer> cmtIdsList = new ArrayList<Integer>();
			List<String> aucCmtList = bean.getAucCmtId();
			List<String> memTypes = bean.getMemberType();
			if(userIdList.size()>0){
				for(int i = 0; i<userIdList.size();i++ ){
					
					//if member already added before then update 
					if(aucCmtList.get(i) != null && !aucCmtList.get(i).equals("0")){
						AuctionCommittees auctionCommittees = (AuctionCommittees) commonService.getAnObjectByAnyUniqueColumn("AuctionCommittees", "id", aucCmtList.get(i));
						auctionCommittees.setMemberType(memTypes.get(i));
						auctionCommittees.setModifiedBy(commonService.getAuthUserName());
						auctionCommittees.setModifiedDate(new Date());
						commonService.saveOrUpdateModelObjectToDB(auctionCommittees);
						
						//removing object form list to deactivate
						
						for(AuctionCommittees each: auctionCommitteeList){					
							if(each.getId() == auctionCommittees.getId()){
								auctionCommitteeList.remove(each);	
								break;
							}
							
					}
						
						
					}else{
						//add new member
						this.addConvenerWithType(userIdList.get(i), memTypes.get(i), committeeName, null, memoNo,
								remarks, cDate, condemnMst);
					}
					
				}
				
				//deactivating members which are not included in updated members
				for(AuctionCommittees each: auctionCommitteeList){					
						each.setActive(false);
						commonService.saveOrUpdateModelObjectToDB(each);					
				}
			}
			

			return new ModelAndView("redirect:/auction/admin/cccList.do");
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("auction/error", model);
		}
	}

	/*//condemn committee to update
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/admin/ccCmtUpdate.do", method = RequestMethod.GET)
	public ModelAndView ccCmtUpdate(String condemnMstId) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByTwoColumn("AuctionCommittees",
							"committeeName", AUCTION_COMMITTEE, "active", "1");
			List<AuctionCommittees> auctionCommitteeList = (List<AuctionCommittees>) (Object) commonService
					.getObjectListByThreeColumn("AuctionCommittees",
							"committeeName", CONDEMN_COMMITTEE, "active", "1", "condemnMst.id", condemnMstId);

			model.put("auctionCommitteeList", auctionCommitteeList);

			return new ModelAndView("centralStore/auction/committeeUpdate", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}
*/
}
