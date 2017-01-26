package com.ibcs.desco.test.controller;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.cs.model.AuctionCategory;
import com.ibcs.desco.localStore.been.LocalPurchaseMstDtl;
import com.ibcs.desco.test.model.AfmChartOfAccountsTest;
import com.ibcs.desco.test.service.AfmChartOfAccountsTestService;

@Controller
@PropertySource("classpath:common.properties")
public class AfmChartOfAccountsTestController {

	@Autowired
	private AfmChartOfAccountsTestService afmChartOfAccountsTestService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test/afmChartOfAccountsTest/createAccount.do", method = RequestMethod.GET)
	public ModelAndView createAfmChartOfAccountsTest(AfmChartOfAccountsTest afmChartOfAccountsTest) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("test/addAfmChartOfAccountsTest", model);
	}
	
	
	@RequestMapping(value = "/test/afmChartOfAccountsTest/createAccount.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView addAfmChartOfAccountsTest( AfmChartOfAccountsTest afmChartOfAccountsTest ) {
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println(">>>>>>>>>>>>>>>>>");
		System.out.println(afmChartOfAccountsTest.toString());
		try {			
			afmChartOfAccountsTestService.addCentralStoreItems(afmChartOfAccountsTest);			
			return new ModelAndView("redirect:/test/afmChartOfAccountsTest/createAccount.do");
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test/afmChartOfAccountsTest/findOne/{id}", method = RequestMethod.GET)
	public ModelAndView createAfmChartOfAccountsTest(@PathVariable Long id) {
		AfmChartOfAccountsTest afmChartOfAccountsTest = afmChartOfAccountsTestService.getAfmChartOfAccountsTest(id);
		
		return new ModelAndView("test/addAfmChartOfAccountsTest", "parent", afmChartOfAccountsTest);
		
		
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test/afmChartOfAccountsTest/ajax/findOne.do", method = RequestMethod.GET)
	@ResponseBody
	public String createAfmChartOfAccountsTestAjax(@PathVariable Long code) {
		Gson gson = new Gson();
		AfmChartOfAccountsTest afmChartOfAccountsTest = afmChartOfAccountsTestService.getAfmChartOfAccountsTest(code);
		
		return gson.toJson(afmChartOfAccountsTest);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test/afmChartOfAccountsTest/ajax/findOne.do", method = RequestMethod.POST)
	@ResponseBody
	public String createAfmChartOfAccountsTestAjaxPost(@RequestBody String obj) throws JsonGenerationException, JsonMappingException, IOException {
		Gson gson = new Gson();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		AfmChartOfAccountsTest accountsTest = gson.fromJson(obj, AfmChartOfAccountsTest.class);
		AfmChartOfAccountsTest afmChartOfAccountsTest = afmChartOfAccountsTestService.getAfmChartOfAccountsTestByCode(accountsTest.getAccountCode());
		if(afmChartOfAccountsTest != null){
			System.out.println("response found");
			System.out.println(afmChartOfAccountsTest.toString());
		}else{
			System.out.println("response found null");
		}
		String res = ow.writeValueAsString(afmChartOfAccountsTest);
		return res;
	}
}
