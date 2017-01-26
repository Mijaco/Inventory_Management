package com.ibcs.desco.budget.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.budget.bean.BudgetMstDto;
import com.ibcs.desco.budget.model.BudgetDtl;
import com.ibcs.desco.budget.model.BudgetMst;
import com.ibcs.desco.budget.service.BudgetMstService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.AllLookUp;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.procurement.model.ProcurementPackageDtl;
import com.ibcs.desco.procurement.model.ProcurementPackageMst;

/*
 * Author: Galeb
 *
 */

@Controller
public class BudgetMstController extends Constrants{

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentsService departmentsService;

	@Autowired
	private BudgetMstService budgetMstService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/addNewBudget.do", method = RequestMethod.GET)
	public ModelAndView addNewBudget() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			//get all Session from DescoSession
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			
			//get all ItemCategory from ItemCategory
			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");
			
			//get BUDGET_EXPENDITURE_CATEGORY from AllLookUp by parent BUDGET_EXPENDITURE_CATEGORY
			List<AllLookUp> budgetExpenditureCategories = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							BUDGET_EXPENDITURE_CATEGORY);
			
			//get sources of fund from AllLookUp by parent SOURCE_OF_FUND
			List<AllLookUp> costSources = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							SOURCE_OF_FUND);
			
			//get types of budget from AllLookUp by parent BUDGET_TYPE
			List<AllLookUp> budgetTypes = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName", BUDGET_TYPE);
			
			model.put("descoSession", descoSession);
			model.put("categoryList", itemCategoryList);
			model.put("department", department);
			model.put("budgetExpenditureCategories", budgetExpenditureCategories);
			model.put("costSources", costSources);
			model.put("budgetTypes", budgetTypes);
			return new ModelAndView("budget/mainBudget/budgetEntryForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	// adding budgetMst and budgetDtl.
	// getting BudgetMst and BudgetDtls Using a dto named BudgetMstDto
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/addNewBudget.do", method = RequestMethod.POST)
	public ModelAndView addNewBudget(BudgetMstDto budgetMstDto) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			// adding new BudgetMst
			BudgetMst budgetMst = new BudgetMst();
			budgetMst.setId(null);
			budgetMst.setCreatedBy(userName);
			budgetMst.setCreatedDate(new Date());
			// setting user provided values from budgetMstDto
			budgetMst.setBudgetStatus("Draft");
			
			//setting BudgetType. getting object from AllLookUp by id
			/*budgetMst.setBudgetType( (AllLookUp) commonService.getAnObjectByAnyUniqueColumn(
					"AllLookUp", "id", budgetMstDto.getBudgetTypeId()));
		*/	
			
			//setting budgetType fix as "Capital Expenditure"
			budgetMst.setBudgetType("Capital Expenditure");
			budgetMst.setProjectFundSrc(budgetMstDto.getProjectFundSrc());
			//setting budget session as descoSession
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							budgetMstDto.getDescoSession());
			budgetMst.setDescoSession(descoSession);

			commonService.saveOrUpdateModelObjectToDB(budgetMst);

			Double budgetAmount = 0.0;
			budgetMst = (BudgetMst) commonService.getAnObjectByAnyUniqueColumn(
					"BudgetMst", "id", budgetMst.getId().toString());

			// adding new budgetDtls.
			// getting values from list of budgetMstDto
			for (int i = 0; i < budgetMstDto.getItemCode().size(); i++) {
				System.out.println(budgetMstDto.getItemId().get(i));
				
				//getting ItemMaster by id which saved recently
				ItemMaster itemMaster = (ItemMaster) commonService
						.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
								budgetMstDto.getItemCode().get(i));
				
				//getting expenditure category for budget as lookup from AllLookUp entity
				AllLookUp lookup = (AllLookUp) commonService
						.getAnObjectByAnyUniqueColumn("AllLookUp", "id",
								budgetMstDto.getLookUpId().get(i));
				//getting SourceOfFunding for budget as costSource from AllLookUp entity
				AllLookUp costSource = (AllLookUp) commonService
						.getAnObjectByAnyUniqueColumn("AllLookUp", "id",
								budgetMstDto.getCostSource().get(i));
				
				if (itemMaster != null) {
					BudgetDtl budgetDtl = new BudgetDtl();
					budgetDtl.setId(null);
					budgetDtl.setCreatedBy(userName);
					budgetDtl.setCreatedDate(new Date());
					budgetDtl.setBudgetMst(budgetMst);
					budgetDtl.setItemMaster(itemMaster);
					budgetDtl.setLookup(lookup);
					budgetDtl.setQty(budgetMstDto.getQty().get(i));
					budgetDtl.setRate(budgetMstDto.getRate().get(i));
					budgetDtl
							.setCostSource(costSource);
					budgetDtl.setTotalAmount(budgetDtl.getQty()
							* budgetDtl.getRate());
					if (budgetDtl.getTotalAmount() != null) {
						budgetAmount += budgetDtl.getTotalAmount();
					}

					commonService.saveOrUpdateModelObjectToDB(budgetDtl);
				}

			}

			budgetMst.setTotalAmount(budgetAmount);
			commonService.saveOrUpdateModelObjectToDB(budgetMst);

			/*List<BudgetDtl> budgetDtls = (List<BudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("BudgetDtl", "budgetMst",
							budgetMst.getId().toString());*/
			List<BudgetDtl> budgetDtls = budgetMstService.getValidBgtDtlListByMst(budgetMst.getId());

			model.put("department", department);
			model.put("budgetDtls", budgetDtls);

			return new ModelAndView("budget/mainBudget/budgetDetails", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}

	}

	// editing budgetMst and budgetDtl.
	// getting BudgetMst and BudgetDtls Using a dto named BudgetMstDto
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/editBudget.do", method = RequestMethod.POST)
	public ModelAndView editBudget(BudgetMstDto budgetMstDto) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			BudgetMst budgetMst = (BudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("BudgetMst", "id",
							budgetMstDto.getId());

			budgetMst.setModifiedBy(userName);
			budgetMst.setModifiedDate(new Date());
			// setting user provided values from budgetMstDto
//			budgetMst.setBudgetStatus(budgetMstDto.getBudgetStatus());
			//setting budget type from AllLookUp entity by id
//			budgetMst.setBudgetType((AllLookUp) commonService.getAnObjectByAnyUniqueColumn(
//					"AllLookUp", "id", budgetMstDto.getBudgetTypeId()));
			//setting sourceOfFunding 
			budgetMst.setProjectFundSrc(budgetMstDto.getProjectFundSrc());
			
			//getting budget session from descoSession entity
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							budgetMstDto.getDescoSession());
			//setting budget session from descoSession entity
			budgetMst.setDescoSession(descoSession);

			commonService.saveOrUpdateModelObjectToDB(budgetMst);

			Double budgetAmount = 0.0;
			budgetMst = (BudgetMst) commonService.getAnObjectByAnyUniqueColumn(
					"BudgetMst", "id", budgetMst.getId().toString());

			// adding new budgetDtls.
			// getting values from list of budgetMstDto
			for (int i = 0; i < budgetMstDto.getItemId().size(); i++) {
				//getting itemMaster recently saved
				ItemMaster itemMaster = (ItemMaster) commonService
						.getAnObjectByAnyUniqueColumn("ItemMaster", "id",
								budgetMstDto.getItemId().get(i));
				
				//getting expenditure category from AllLookUp entity as lookUp
				AllLookUp lookup = (AllLookUp) commonService
						.getAnObjectByAnyUniqueColumn("AllLookUp", "id",
								budgetMstDto.getLookUpId().get(i));
				
				//getting source of funding from AllLookUp
				AllLookUp costSource = (AllLookUp) commonService
						.getAnObjectByAnyUniqueColumn("AllLookUp", "id",
								budgetMstDto.getCostSource().get(i));
				
				BudgetDtl budgetDtl = null;
				if (itemMaster != null) {
					//checking if editing on budgetDtl
					if (budgetMstDto.getBudgetDtlId().get(i) != null
							&& !budgetMstDto.getBudgetDtlId().get(i).trim()
									.equals("")) {
						budgetDtl = (BudgetDtl) commonService
								.getAnObjectByAnyUniqueColumn("BudgetDtl",
										"id", budgetMstDto.getBudgetDtlId()
												.get(i));
					} else {
						//new budgetDtl
						budgetDtl = new BudgetDtl();
						budgetDtl.setCreatedBy(userName);
						budgetDtl.setCreatedDate(new Date());
						budgetDtl.setBudgetMst(budgetMst);
						budgetDtl.setItemMaster(itemMaster);
					}
					//setting the source of funding
					budgetDtl.setCostSource(costSource);
					//setting expenditure category for budgetDtl
					budgetDtl.setLookup(lookup);
					budgetDtl.setQty(budgetMstDto.getQty().get(i));
					budgetDtl.setRate(budgetMstDto.getRate().get(i));
					budgetDtl.setTotalAmount(budgetDtl.getQty()
							* budgetDtl.getRate());
					if (budgetDtl.getTotalAmount() != null) {
						budgetAmount += budgetDtl.getTotalAmount();
					}

					commonService.saveOrUpdateModelObjectToDB(budgetDtl);
				}

			}

			budgetMst.setTotalAmount(budgetAmount);
			commonService.saveOrUpdateModelObjectToDB(budgetMst);

			List<BudgetDtl> budgetDtls = (List<BudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("BudgetDtl", "budgetMst",
							budgetMst.getId().toString());

			model.put("department", department);
			model.put("budgetDtls", budgetDtls);

			return new ModelAndView("budget/mainBudget/budgetDetails", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}

	}


	// adding ReviseBudget on existing approved budget
	// getting BudgetMst and BudgetDtls Using a dto named BudgetMstDto
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/reviceBudget.do", method = RequestMethod.POST)
	public ModelAndView reviceBudget(BudgetMstDto budgetMstDto) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			//getting the budgetMst
			BudgetMst budgetMst = (BudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("BudgetMst", "id",
							budgetMstDto.getId());

			budgetMst.setBgtRevStatus("pending");
			commonService.saveOrUpdateModelObjectToDB(budgetMst);

			Double budgetAmount = 0.0;
		
			// adding new budgetDtls.
			// getting values from list of budgetMstDto
			for (int i = 0; i < budgetMstDto.getItemId().size(); i++) {
				ItemMaster itemMaster = (ItemMaster) commonService
						.getAnObjectByAnyUniqueColumn("ItemMaster", "id",
								budgetMstDto.getItemId().get(i));
				//getting expenditure category from AllLookUp
				AllLookUp lookup = (AllLookUp) commonService
						.getAnObjectByAnyUniqueColumn("AllLookUp", "id",
								budgetMstDto.getLookUpId().get(i));
				//getting sourceOfFunding from AllLookUp
				AllLookUp costSource = (AllLookUp) commonService
						.getAnObjectByAnyUniqueColumn("AllLookUp", "id",
								budgetMstDto.getCostSource().get(i));
				
				BudgetDtl budgetDtl = null;
				if (itemMaster != null) {
					
					budgetDtl = new BudgetDtl();
					budgetDtl.setCreatedBy(userName);
					budgetDtl.setCreatedDate(new Date());
					budgetDtl.setBudgetMst(budgetMst);
					budgetDtl.setItemMaster(itemMaster);					
					budgetDtl.setQty(budgetMstDto.getQty().get(i));
					budgetDtl.setRate(budgetMstDto.getRate().get(i));
					budgetDtl.setCostSource(costSource);
					budgetDtl.setLookup(lookup);
					//fields for revision budget
					budgetDtl.setRevBudgetApproved(false);
					budgetDtl.setRevBudget(true);//is revised budget 
					
					budgetDtl.setTotalAmount(budgetDtl.getQty()
							* budgetDtl.getRate());
					if (budgetDtl.getTotalAmount() != null) {
						budgetAmount += budgetDtl.getTotalAmount();
					}

					commonService.saveOrUpdateModelObjectToDB(budgetDtl);
				}

			}

			//budgetMst.setTotalAmount(budgetMst.getTotalAmount() + budgetAmount);
			//commonService.saveOrUpdateModelObjectToDB(budgetMst);

			List<BudgetDtl> budgetDtls = budgetMstService.getReviceBgtDtlListByMst(budgetMst.getId());

			model.put("department", department);
			model.put("budgetDtls", budgetDtls);

			return new ModelAndView("budget/mainBudget/budgetDetails", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}

	}

	//all budget from BudgetMst
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/getBudgetList.do", method = RequestMethod.GET)
	public ModelAndView getBudgetList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<BudgetMst> budgetMstList = (List<BudgetMst>) (Object) commonService
					.getAllObjectList("BudgetMst");

			model.put("budgetMstList", budgetMstList);
			model.put("department", department);
			return new ModelAndView("budget/mainBudget/budgetList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}
	
	//All revise budget not approved yet
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/getReciveBudgetList.do", method = RequestMethod.GET)
	public ModelAndView getReciveBudgetList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<BudgetMst> budgetMstList = (List<BudgetMst>) (Object) commonService
					.getObjectListByAnyColumn("BudgetMst", "bgtRevStatus", "pending");

			model.put("budgetMstList", budgetMstList);
			model.put("department", department);
			return new ModelAndView("budget/mainBudget/budgetReviceList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/showBudgetDetail.do", method = RequestMethod.GET)
	public ModelAndView showBudgetDetail(BudgetMst budgetMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*List<BudgetDtl> budgetDtlList = (List<BudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("BudgetDtl", "budgetMst",
							budgetMst.getId().toString());*/
			List<BudgetDtl> budgetDtls = budgetMstService.getValidBgtDtlListByMst(budgetMst.getId());

			
			model.put("budgetDtls", budgetDtls);
			model.put("department", department);
			return new ModelAndView("budget/mainBudget/budgetDetails", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/showReviseBudgetDetail.do", method = RequestMethod.GET)
	public ModelAndView showReviceBudgetDetail(BudgetMst budgetMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*List<BudgetDtl> budgetDtlList = (List<BudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("BudgetDtl", "budgetMst",
							budgetMst.getId().toString());*/
			//getting only revise budget from BudgetDtl by budgetMst id
			List<BudgetDtl> budgetDtls = budgetMstService.getReviceBgtDtlListByMst(budgetMst.getId());

			
			model.put("budgetDtls", budgetDtls);
			model.put("department", department);
			return new ModelAndView("budget/mainBudget/budgetDetails", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/editBudget.do", method = RequestMethod.GET)
	public ModelAndView editBudget(BudgetMst budgetMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*List<BudgetDtl> budgetDtls = (List<BudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("BudgetDtl", "budgetMst",
							budgetMst.getId().toString());*/
			List<BudgetDtl> budgetDtls = budgetMstService.getValidBgtDtlListByMst(budgetMst.getId());

			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");
			
			//getting All budgetExpenditureCategories from AllLookUp by parent BUDGET_EXPENDITURE_CATEGORY
			List<AllLookUp> budgetExpenditureCategories = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							BUDGET_EXPENDITURE_CATEGORY);
			
			//getting All funding Sources from AllLookUp by parent SOURCE_OF_FUND
			List<AllLookUp> costSources = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							SOURCE_OF_FUND);
			
			//getting All BUDGET TYPES from AllLookUp by parent BUDGET_TYPE
			List<AllLookUp> budgetTypes = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName", BUDGET_TYPE);
			
			model.put("descoSession", descoSession);
			model.put("categoryList", itemCategoryList);
			model.put("budgetDtls", budgetDtls);
			model.put("costSources", costSources);
			model.put("department", department);
			model.put("budgetTypes", budgetTypes);
			model.put("budgetExpenditureCategories", budgetExpenditureCategories);
			return new ModelAndView("budget/mainBudget/budgetEdit", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/reviceBudget.do", method = RequestMethod.GET)
	public ModelAndView reviceBudget(BudgetMst budgetMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {			
			budgetMst = (BudgetMst) commonService.getAnObjectByAnyUniqueColumn(
					"BudgetMst", "id", budgetMst.getId().toString());
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<AllLookUp> budgetExpenditureCategories = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							BUDGET_EXPENDITURE_CATEGORY);
			
			List<AllLookUp> costSources = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							SOURCE_OF_FUND);
			/*List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");*/

			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");
			//model.put("descoSession", descoSession);
			model.put("categoryList", itemCategoryList);
			model.put("budgetMst", budgetMst);
			model.put("department", department);
			model.put("costSources", costSources);
			model.put("budgetExpenditureCategories", budgetExpenditureCategories);
			return new ModelAndView("budget/mainBudget/budgetRevice", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/deleteBudget.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBudget(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		// ObjectWriter ow = new
		// ObjectMapper().writer().withDefaultPrettyPrinter();
		BudgetMst budgetMst = gson.fromJson(obj, BudgetMst.class);
		Integer id = budgetMst.getId();

		//getting budgetDtl list by budgetMst id
		List<BudgetDtl> budgetDtls = (List<BudgetDtl>) (Object) commonService
				.getObjectListByAnyColumn("BudgetDtl", "budgetMst", budgetMst
						.getId().toString());

		//deleting all BudgetDtls of budgetDtl list
		for (BudgetDtl budgetDtl : budgetDtls) {
			commonService.deleteAnObjectById("BudgetDtl", budgetDtl.getId());
		}
		//deleting BudgetMst
		commonService.deleteAnObjectById("BudgetMst", id);

		return "success";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/approveBudget.do", method = RequestMethod.POST)
	@ResponseBody
	public String approveBudget(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		BudgetMst budgetMst = gson.fromJson(obj, BudgetMst.class);
		budgetMst = (BudgetMst) commonService.getAnObjectByAnyUniqueColumn(
				"BudgetMst", "id", budgetMst.getId().toString());

		budgetMst.setBudgetStatus("Approved");
		budgetMst.setApprovedBy(commonService.getAuthUserName());
		budgetMst.setApprovedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(budgetMst);
		return "success";

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/approveReviceBudget.do", method = RequestMethod.POST)
	@ResponseBody
	public String approveReviceBudget(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		BudgetMst budgetMst = gson.fromJson(obj, BudgetMst.class);
		budgetMst = (BudgetMst) commonService.getAnObjectByAnyUniqueColumn(
				"BudgetMst", "id", budgetMst.getId().toString());

		budgetMst.setBgtRevStatus("approved");
		/*budgetMst.setApprovedBy(commonService.getAuthUserName());
		budgetMst.setApprovedDate(new Date());*/
		commonService.saveOrUpdateModelObjectToDB(budgetMst);
		
		//getting all revised BudgetDtls by BudgetMst id
		List<BudgetDtl> budgetDtlList = budgetMstService.getReviceBgtDtlListByMst(budgetMst.getId());
		if(budgetDtlList.size() > 0){
			for (BudgetDtl budgetDtl : budgetDtlList) {
				//setting Revision related information
				budgetDtl.setRevBudgetApproved(true);
				budgetDtl.setRevApprovedBy(commonService.getAuthUserName());
				budgetDtl.setRevApprovedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(budgetDtl);
			}
		}
		return "success";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/deleteBudgetDtl.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBudgetDtl(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		// ObjectWriter ow = new
		// ObjectMapper().writer().withDefaultPrettyPrinter();
		BudgetDtl budgetDtl = gson.fromJson(obj, BudgetDtl.class);
		Integer id = budgetDtl.getId();
		commonService.deleteAnObjectById("BudgetDtl", id);

		return "success";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/budgetMst/budgetSession/findOne.do", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String findBudgetMstByBudgetSession(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		DescoSession descoSession = gson.fromJson(obj, DescoSession.class);
		BudgetMst budgetMst = /*budgetMstService
				.getOneByBudgetSession(descoSession.getId());*/(BudgetMst) commonService.getAnObjectByAnyUniqueColumn(
						"BudgetMst", "descoSession.id", descoSession.getId().toString());

		String res = ow.writeValueAsString(budgetMst);
		return res;
	}

//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/budget/budgetMst/budgetSessionAndType/findOne.do", method = RequestMethod.POST)
//	@ResponseBody
//	@PreAuthorize("isAuthenticated()")
//	public String findBudgetMstByBudgetSessionAndType(@RequestBody String obj)
//			throws JsonGenerationException, JsonMappingException, IOException {
//
//		Gson gson = new Gson();
//		ObjectWriter ow = new ObjectMapper().writer()
//				.withDefaultPrettyPrinter();
//		BudgetMst budgetMst = gson.fromJson(obj, BudgetMst.class);
//		BudgetMst budgetMstDb = new BudgetMst();
//		List<BudgetMst> budgetMstList = (List<BudgetMst>) (Object) commonService
//				.getObjectListByTwoColumn("BudgetMst", "descoSession.id",
//						budgetMst.getDescoSession().getId().toString(),
//						"budgetType.id", budgetMst.getBudgetType().getId().toString());
//		if (budgetMstList != null && budgetMstList.size() > 0) {
//			budgetMstDb = budgetMstList.get(0);
//		}
//		String res = ow.writeValueAsString(budgetMstDb);
//		return res;
//	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/budgetMst/app/session/findOne.do", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String findProcurementMstByBudgetSession(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		DescoSession descoSession = gson.fromJson(obj, DescoSession.class);
		ProcurementPackageMst procurementPackageMst = new ProcurementPackageMst();
		List<ProcurementPackageMst> procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
				.getObjectListByAnyColumn("ProcurementPackageMst",
						"descoSession.id", descoSession.getId().toString());
		if (procurementPackageMstList != null
				&& procurementPackageMstList.size() > 0) {
			procurementPackageMst = procurementPackageMstList.get(0);
		}
		String res = ow.writeValueAsString(procurementPackageMst);
		return res;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/descoSession/findProcDetails.do", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String findAppItemsByDescoSession(@RequestBody String obj)
			throws JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new Gson();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		DescoSession descoSession = gson.fromJson(obj, DescoSession.class);

		List<ProcurementPackageDtl> procPackDtlList = (List<ProcurementPackageDtl>) (Object) commonService
				.getObjectListByAnyColumn("ProcurementPackageDtl",
						"procurementPackageMst.descoSession.id",
						descoSession.getId() + "");

		String res = ow.writeValueAsString(procPackDtlList);
		return res;
	}

}
