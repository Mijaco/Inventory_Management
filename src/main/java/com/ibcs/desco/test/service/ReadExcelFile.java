package com.ibcs.desco.test.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.test.dao.ExceToDbDao;
import com.ibcs.desco.test.model.ItemDescription;

@Component
public class ReadExcelFile {

	private static final String FILE_PATH = "D:\\desco_inventory\\Total Material Lists DESCO.xls";

	@Autowired
	ExceToDbDao excelToDbDao;

	@SuppressWarnings("unchecked")
	public void insertIntoDbFromExcel() {
		
		System.out.println("checkkkk");
		List<ItemDescription> itemList = getStudentsListFromExcel();
		List<ItemCategory> itemcategoryList = new ArrayList<ItemCategory>();
		List<ItemMaster> itemMasterList = new ArrayList<ItemMaster>();

		for (int j = 0; j < itemList.size(); j++) {

			if (StringUtils.isEmpty(itemList.get(j).getItemCode())) {
				ItemCategory itemCategory = new ItemCategory();
				itemCategory.setCategoryId(Integer.valueOf(itemList.get(j + 1).getItemCode().substring(0, 3)));
				itemCategory.setCategoryName(itemList.get(j).getItemName());
				itemCategory.setCreatedBy("admin");
				itemCategory.setCreatedDate(new Date());
				excelToDbDao.saveItemCategory(itemCategory);
			} else {
				ItemMaster itemMaster = new ItemMaster();
				itemMaster.setCategoryId(Integer.valueOf(itemList.get(j).getItemCode().substring(0, 3)));
				itemMaster.setItemId(itemList.get(j).getItemCode());
				itemMaster.setItemName(itemList.get(j).getItemName());
				itemMaster.setItemActive(1);
				itemMaster.setSpecialApproval(0);
				itemMaster.setUnitCode(itemList.get(j).getUnit());
				itemMaster.setCreatedBy("admin");
				itemMaster.setCreateDate(new Date());
				excelToDbDao.saveItemMaster(itemMaster);
			}
		}

		for (ItemCategory itemCategory : itemcategoryList) {
			System.out.println(itemCategory);
		}
		System.out.println(itemMasterList.size());
		for (ItemMaster itemmaster : itemMasterList) {
			System.out.println(itemmaster);
		}

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private static List getStudentsListFromExcel() {
		List<ItemDescription> itemDescriptionList = new ArrayList<ItemDescription>();

		// Get first/desired sheet from the workbook

		FileInputStream fis = null;
		try {
			InputStream ExcelFileToRead = new FileInputStream(FILE_PATH);
			HSSFWorkbook workbook = new HSSFWorkbook(ExcelFileToRead);

			fis = new FileInputStream(FILE_PATH);

			// Using XSSF for xlsx format, for xls use HSSF

			int numberOfSheets = workbook.getNumberOfSheets();

			// looping over each workbook sheet
			/* for (int i = 0; i < numberOfSheets; i++) { */
			/* int count = 0; */
			HSSFSheet sheet = workbook.getSheetAt(4);
			Iterator rowIterator = sheet.iterator();
			System.out.println("last row number : " + sheet.getLastRowNum());

			// iterating over each row
			for (int count = 0; count < sheet.getLastRowNum(); count++) {

				ItemDescription itemDescription = new ItemDescription();
				Row row = (Row) rowIterator.next();
				Iterator cellIterator = row.cellIterator();

				// Iterating over each cell (column wise) in a particular
				// row.
				while (cellIterator.hasNext()) {

					Cell cell = (Cell) cellIterator.next();
					// The Cell Containing String will is name.
					if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
						if (cell.getColumnIndex() == 0) {
							itemDescription.setItemCategory(String.valueOf(cell.getNumericCellValue()));
						}

						if (cell.getColumnIndex() == 1) {
							itemDescription.setItemCode(String.valueOf(cell.getNumericCellValue()));
						}
						// The Cell Containing numeric value will contain
						// marks
					} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {

						if (cell.getColumnIndex() == 1) {
							itemDescription.setItemCode(String.valueOf(cell.getStringCellValue()));
						}
						// Cell with index 2 contains marks in Science
						else if (cell.getColumnIndex() == 2) {
							itemDescription.setItemName(String.valueOf(cell.getStringCellValue()));
						}
						// Cell with index 3 contains marks in English
						else if (cell.getColumnIndex() == 3) {
							itemDescription.setUnit(String.valueOf(cell.getStringCellValue()));
						}
					}
				}
				// end iterating a row, add all the elements of a row in
				// list
				/*
				 * System.out.println(" count: " + count +
				 * " : item description : " + itemDescription);
				 */
				itemDescriptionList.add(count, itemDescription);

				if (count == sheet.getLastRowNum()) {
					break;
				}
			}
			/* } */

			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemDescriptionList;
	}

}
