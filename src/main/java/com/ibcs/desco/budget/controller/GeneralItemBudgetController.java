package com.ibcs.desco.budget.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.budget.model.CostCentre;
import com.ibcs.desco.budget.model.GeneralItemBudgetDtl;
import com.ibcs.desco.budget.model.GeneralItemBudgetMst;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.procurement.model.ProcurementPackageMst;

/*
 * @author  		Md. Ahasanul Ashid
 * @company 		IBCS Primax Software Ltd.
 * @Designation  	Programmer
 */

@Controller
public class GeneralItemBudgetController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	UserService userService;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.demandNote.prefix}")
	private String demandNotePrefix;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/generalItemUploadForm.do", method = RequestMethod.GET)
	public ModelAndView generalItemUploadForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSession", descoSession);
			return new ModelAndView("budget/uploadGnBudgetForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@RequestMapping(value = "/budget/uploadGnItemXLS.do", method = RequestMethod.POST)
	public ModelAndView uploadGnItemXLS(
			ProcurementPackageMst bean,
			@RequestParam("uploadGnBtExcelFile") MultipartFile uploadGnBtExcelFile) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String id = bean.getId().toString();
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id", id);
			String filePath = this.saveBdXlFileToDrive(uploadGnBtExcelFile);

			Vector<List<Cell>> vectorList = this.readXLS(filePath);

			List<String> duplicateItemList = this.saveXlsRecordToDB(vectorList,
					descoSession, filePath);

			return this.generalItemBudgetList(descoSession,
					duplicateItemList.toString());

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	private String saveBdXlFileToDrive(MultipartFile file) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String extension = "";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("xls")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();

					File dir = new File(descoWORootPath + File.separator
							+ "bd_xls");
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
			return "Please uplade a XLS file";
		}
		return "";
	}

	private Vector<List<Cell>> readXLS(String fileName) {
		Vector<List<Cell>> cellVectorHolder = new Vector<List<Cell>>();
		try {
			FileInputStream myInput = new FileInputStream(new File(fileName));
			Workbook wb = WorkbookFactory.create(myInput);
			Sheet sheet = wb.getSheetAt(0);
			int rowsCount = sheet.getLastRowNum();
			for (int i = 1; i <= rowsCount; i++) {
				Row row = sheet.getRow(i);
				int colCounts = row.getLastCellNum();
				List<Cell> list = new ArrayList<Cell>();
				System.out.println("Reading Row : " + (i + 1));
				for (int j = 0; j < colCounts; j++) {
					Cell cell = row.getCell(j);
					list.add(cell);
				}
				cellVectorHolder.addElement(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}

	private static Object getCellObject(Cell cell) {
		Object cellValue = null;

		if (cell == null) {
			cellValue = null;
		} else {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			}

			if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					cellValue = cell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_STRING:
					cellValue = cell.getRichStringCellValue();
					break;
				}
			}
		}

		return cellValue;

	}

	@SuppressWarnings("unchecked")
	private List<String> saveXlsRecordToDB(Vector<List<Cell>> vectorList,
			DescoSession descoSession, String filePath) {
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		// Validation of GeneralItemBudgetMst by Session Id and Item code		
		List<GeneralItemBudgetMst> mstList = (List<GeneralItemBudgetMst>) (Object) commonService
				.getObjectListByAnyColumn("GeneralItemBudgetMst",
						"descoSession.id", descoSession.getId().toString());
		Map<String, String> gnMstMap = new HashMap<String, String>();
		for (GeneralItemBudgetMst mst : mstList) {
			String concat = descoSession.getId() + "#" + mst.getItemCode();
			gnMstMap.put(concat, "1");
		}

		List<String> duplicateItemList = new ArrayList<String>();

		List<Cell> costCenterList = vectorList.get(0);

		for (int i = 1; i < vectorList.size(); i++) {
			List<Cell> list = vectorList.get(i);

			String itemCode = getCellObject(list.get(0)).toString();
			String itemCodeOld =getCellObject(list.get(1)).toString();
			String itemName = getCellObject(list.get(2)).toString();
			String uom = getCellObject(list.get(3)).toString();
			Double qty = (Double) getCellObject(list.get(4));
			Double unitCost = (Double) getCellObject(list.get(5));
			Double totalCost = (Double) getCellObject(list.get(6));

			String newConcat = descoSession.getId() + "#" + itemCode;
			String key = (gnMstMap.get(newConcat) == null) ? "0" : gnMstMap
					.get(newConcat);
			boolean flag = key.equals("1");

			if (flag) {
				duplicateItemList.add(itemCode);
			} else {
				// Start
				GeneralItemBudgetMst budgetMst = new GeneralItemBudgetMst(null,
						itemCode, itemCodeOld + "", itemName, uom, qty,
						unitCost, totalCost, descoSession, null, userName, now,
						filePath);
				commonService.saveOrUpdateModelObjectToDB(budgetMst);

				int maxId = (Integer) commonService
						.getMaxValueByObjectAndColumn("GeneralItemBudgetMst",
								"id");
				GeneralItemBudgetMst budgetMstDb = (GeneralItemBudgetMst) commonService
						.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst",
								"id", maxId + "");

				for (int j = 7; j < list.size(); j++) {
					int costCenterId = ((Double) getCellObject(costCenterList
							.get(j))).intValue();
					Double totalCost_cc = (Double) getCellObject(list.get(j));
					GeneralItemBudgetDtl budgetDtl = new GeneralItemBudgetDtl(
							null, costCenterId + "", totalCost_cc, budgetMstDb,
							userName, now);
					if (totalCost_cc > 0) {
						commonService.saveOrUpdateModelObjectToDB(budgetDtl);
					}
				}

				// End
			}

		}

		return duplicateItemList;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/generalItemList.do", method = RequestMethod.GET)
	public ModelAndView generalItemBudgetListGet() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSessionList", descoSessionList);
			return new ModelAndView("budget/sessionFormGnBudget", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/generalItemList.do", method = RequestMethod.POST)
	public ModelAndView generalItemBudgetList(DescoSession bean,
			String duplicateItemList) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id", bean
							.getId().toString());
			
			List<GeneralItemBudgetMst> generalItemBudgetMstList = (List<GeneralItemBudgetMst>) (Object) commonService
					.getObjectListByAnyColumn("GeneralItemBudgetMst",
							"descoSession.id", descoSession.getId().toString());

			model.put("generalItemBudgetMstList", generalItemBudgetMstList);
			model.put("descoSession", descoSession);
			model.put("duplicateItemList", duplicateItemList);
			return new ModelAndView("budget/gnBudgetList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/showGnItemBudget.do", method = RequestMethod.POST)
	public ModelAndView showGnItemBudget(GeneralItemBudgetMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			GeneralItemBudgetMst generalItemBudgetMstDb = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							bean.getId().toString());

			List<GeneralItemBudgetDtl> generalItemBudgetDtlList = (List<GeneralItemBudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("GeneralItemBudgetDtl",
							"budgetMst.id", bean.getId().toString());

			List<CostCentre> ccList = (List<CostCentre>) (Object) commonService
					.getAllObjectList("CostCentre");
			Map<String, String> ccMap = new HashMap<String, String>();
			for (CostCentre costCentre : ccList) {
				String id = costCentre.getCostCentreCode();
				String cName = costCentre.getCostCentreName();
				ccMap.put(id, cName);

			}

			for (GeneralItemBudgetDtl dtl : generalItemBudgetDtlList) {
				dtl.setCostCenterName(ccMap.get(dtl.getCostCenterId()));
			}

			model.put("generalItemBudgetMst", generalItemBudgetMstDb);
			model.put("generalItemBudgetDtlList", generalItemBudgetDtlList);
			model.put("costCentreList", ccList);
			return new ModelAndView("budget/showGnItemBudget", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

}
