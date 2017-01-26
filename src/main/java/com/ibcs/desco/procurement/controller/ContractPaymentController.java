package com.ibcs.desco.procurement.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.procurement.model.ContractManagement;
import com.ibcs.desco.procurement.model.ContractPayment;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SubStoreItemsService;

@Controller
@PropertySource("classpath:common.properties")
public class ContractPaymentController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Value("${desco.project.rootPath}")
	private String descoFilePath;
	
	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractorPaymentForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getWsContractorForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<DescoKhath> descoKhath = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhath", descoKhath);
		return new ModelAndView("procurement/payment/contractorPaymentForm",
				model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractorPaymentLoadContractInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadContractInfo( ) throws Exception {
		String toJson = "";
		List<ContractManagement> contractList = (List<ContractManagement>) (Object) commonService.getAllObjectList("ContractManagement");
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(contractList);
		return toJson;
	}
	
	//Author: Ihteshamul Alam
	@RequestMapping(value = "/contractorPaymentDetails.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadContractDetails(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ContractManagement contractDb = gson.fromJson(json, ContractManagement.class);
			ContractManagement contractManagement = (ContractManagement) commonService.getAnObjectByAnyUniqueColumn("ContractManagement", "contractNo", contractDb.getContractNo() );

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(contractManagement);

		} else {
			toJson = "failed";
			Thread.sleep(2000);
		}

		return toJson;
	}
	
	//Author: Ihteshamul Alam
	@RequestMapping(value = "/contractorPaymentSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsContractorSave( ContractPayment contractPayment,
			@RequestParam("refDoc") MultipartFile[] refDoc ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			String contractFilePath = this.saveFileToDrive(refDoc[0]);
			String descoCheckDocFilePath = this.saveFileToDrive(refDoc[1]);
			
			String contractNo = contractPayment.getContractNo();
			
			ContractManagement contractManagement = ( ContractManagement ) commonService.getAnObjectByAnyUniqueColumn("ContractManagement", "contractNo", contractNo);
			
			contractPayment.setContractorAppInvDoc(contractFilePath);
			contractPayment.setDescoCheckDoc(descoCheckDocFilePath);
			contractPayment.setId(null);
			contractPayment.setActive(true);
			contractPayment.setContractManagement(contractManagement);
			contractPayment.setCreatedBy( commonService.getAuthUserName() );
			contractPayment.setCreatedDate( new Date() );
			
			commonService.saveOrUpdateModelObjectToDB( contractPayment );
			
			//List<ContractPayment> contractPaymentList = ( List<ContractPayment> ) ( Object ) commonService.getAllObjectList("ContractPayment");
			//model.put("contractPaymentList", contractPaymentList);
			//return new ModelAndView("procurement/payment/contractorPaymentList", model);
			return new ModelAndView("redirect:/cms/contractArchive.do");
			
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
	
	private String saveFileToDrive(MultipartFile file) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String extension = "";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("pdf")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();

					File dir = new File(descoWORootPath + File.separator
							+ "procurement");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					serverFile = new File(dir.getAbsolutePath()
							+ File.separator + uniqueFileName + "." + extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					return "You failed to upload " + uniqueFileName + " => "
							+ e.getMessage();
				}
				return serverFile.getAbsolutePath();
			}
		} else {
			return "Please uplade a PDF file";
		}
		return "";
	}
	
	@RequestMapping(value = "/ws/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, ContractPayment contractPayment)
			throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = contractPayment.getDownloadPath();
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		InputStream inputStream = new BufferedInputStream(fis);
		String extension = "";

		int i = filePath.lastIndexOf('.');

		if (i > 0) {
			extension = filePath.substring(i + 1);
		}

		String agent = request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") != -1) {
			filePath = URLEncoder.encode("localPurchase." + extension, "UTF8");			
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("localPurchase." + extension, "UTF8","B");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=\""
					+ filePath + "\"");
		}

		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		byte by[] = new byte[32768];
		int index = inputStream.read(by, 0, 32768);
		while (index != -1) {
			out.write(by, 0, index);
			index = inputStream.read(by, 0, 32768);
		}
		inputStream.close();
		out.flush();

		return response;
	}
	
	//Author: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractorPaymentList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsContractorList() {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<ContractPayment> contractPaymentList = ( List<ContractPayment> ) ( Object ) commonService.getAllObjectList("ContractPayment");
			model.put("contractPaymentList", contractPaymentList);
			
			return new ModelAndView("procurement/payment/contractorPaymentList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}
}
