package com.ibcs.desco.procurement.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.procurement.model.ContractManagement;

/*
 * Temporary Controller
 * Author: Ihteshamul Alam (Shimul), IBCS
 * Date: 31/08/2016
 * 
 */

@Controller
public class ContractManagementController extends Constrants {
	
	@Autowired
	CommonService commonService;
	
	@Value("${desco.project.rootPath}")
	private String descoWORootPath;
	
	@SuppressWarnings("unused")
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
	
	@RequestMapping(value = "/cms/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, ContractManagement contractManagement)
			throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = contractManagement.getDownloadPath();
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
}
