package com.ibcs.desco.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelToDb {
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		String fileName = "D:\\desco_inventory\\Total Material Lists DESCO.xls";
		Vector dataHolder = read(fileName);
		// saveToDatabase(dataHolder);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector read(String fileName) {
		Vector cellVectorHolder = new Vector();
		try {
			FileInputStream myInput = new FileInputStream(new File(fileName));
			Workbook wb = WorkbookFactory.create(myInput);
			Sheet sheet = wb.getSheetAt(4);
			// Header header = sheet.getHeader();
			// Iterator rowIter = sheet.rowIterator();
			int rowsCount = sheet.getLastRowNum();
			for (int i = 0; i <= rowsCount; i++) {
				Row row = sheet.getRow(i);
				int colCounts = row.getLastCellNum();
				List list = new ArrayList();
				System.out.println("Total Number of Cols: " + colCounts);
				for (int j = 0; j < colCounts; j++) {
					Cell cell = row.getCell(j);
					list.add(cell);
					System.out.println("[" + i + "," + j + "]="
							+ cell.getStringCellValue());
				}

				cellVectorHolder.addElement(list);
			}

			/*
			 * while (rowIter.hasNext()) { //XSSFRow myRow = (XSSFRow)
			 * rowIter.next(); Row myRow = sheet.getRow(i); Iterator cellIter =
			 * myRow.cellIterator(); // Vector cellStoreVector=new Vector();
			 * List list = new ArrayList(); while (cellIter.hasNext()) {
			 * XSSFCell myCell = (XSSFCell) cellIter.next(); list.add(myCell); }
			 * cellVectorHolder.addElement(list); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private static void saveToDatabase(Vector dataHolder)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String itemSlNo = "";
		String itemCode = "";
		String description = "";
		String uom = "";
		System.out.println(dataHolder);

		Class.forName("oracle.jdbc.OracleDriver").newInstance();
		Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.1.19:1521:db11g", "desco", "desco");
		System.out.println("connection made...");

		for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
			List list = (List) iterator.next();

			if (list.get(0) != null && list.get(1) != null
					&& list.get(2) != null && list.get(3) != null) {
				itemSlNo = list.get(0).toString();
				itemCode = list.get(1).toString();
				description = list.get(2).toString();
				uom = list.get(3).toString();

				try {

					PreparedStatement stmt = con
							.prepareStatement("INSERT INTO desco_materials_List(item_no, item_code, description, uom) VALUES(?, ?, ?, ?)");
					stmt.setString(1, itemSlNo);
					stmt.setString(2, itemCode);
					stmt.setString(3, description);
					stmt.setString(4, uom);
					stmt.executeUpdate();

					System.out.println("Data is inserted");
					stmt.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		con.close();

	}

}