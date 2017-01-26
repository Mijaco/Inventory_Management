package com.ibcs.desco.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestDevAshid {

	public static void doIt(Object Object) {

		System.out.println(Object.getClass());

	}

	@SuppressWarnings("unused")
	private static Vector<List<Cell>> readXLS(String fileName) {
		Vector<List<Cell>> cellVectorHolder = new Vector<List<Cell>>();
		try {
			FileInputStream myInput = new FileInputStream(new File(fileName));
			Workbook wb = WorkbookFactory.create(myInput);
			Sheet sheet = wb.getSheetAt(1);
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

	@SuppressWarnings("unused")
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

	public static String filterAmpersion(String stringData) {
		if (stringData.contains("&")) {
			stringData = stringData.replace("&", "'||'&'||'");
		}
		return stringData;
	}
	
	public static int checkDuplicate(String stringData) {
		String s=stringData.toLowerCase();
		
		int repeat=0;		
		for(int i=0; i<s.length(); i++){
			int distinct=0;
			for (int j = 0; j < s.length(); j++) {

	            if(s.charAt(i)==s.charAt(j))
	            { distinct++; }
	        } 
			if(distinct>0){
				repeat++;
			}
		}
		
		return repeat;
	}

	public static void main(String[] args) {
		 String name = "MIC_S&D_AGRABAD";
		 System.out.println(checkDuplicate(name));
		

	}

}
