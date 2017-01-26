package com.ibcs.desco.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.localStore.been.LocalPurchaseMstDtl;

@Controller
@PropertySource("classpath:common.properties")
public class TestController {

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test/createUserList.do", method = RequestMethod.GET)
	public ModelAndView createUserListTest(LocalPurchaseMstDtl lpMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getAllObjectList("com.ibcs.desco.admin.model.AuthUser");
			model.put("authUserList", authUserList);
			return new ModelAndView("test/test_userAutoAddInTable", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@RequestMapping(value = "/test/addUser.do", method = RequestMethod.POST)
	@ResponseBody
	public String addUserTest(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			AuthUser user = gson.fromJson(json, AuthUser.class);
			Integer id = user.getId();

			AuthUser userDb = userService.getAuthUser(id);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(userDb);
		} else {
			Thread.sleep(1000);
			toJson = "fail";
		}

		return toJson;
	}

}
