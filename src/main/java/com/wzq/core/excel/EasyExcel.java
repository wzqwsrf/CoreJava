package com.wzq.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.wzq.core.tools.CommonBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * 简单的EXCEL解析成CommonBean
 * 第一行解析成CommonBean 的colNames
 * 第二行开始解析实际内容
 * @author Administrator
 *
 */
public class EasyExcel {

	private static Logger log = Logger.getLogger(EasyExcel.class);

	public static CommonBean getPross(String filepath, String sheetName) throws FileNotFoundException, IOException {
		File file = new File(filepath);
		
		if (!file.exists()) {
//			file = new File("../conf/UPLOAD_KPI.xls");
		}

		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(filepath)));
		HSSFSheet sheet = getSheet(workbook, sheetName);
		if (sheet == null) {
			log.error("在" + filepath + "中找不到 " + sheetName + " 这个sheet!");
			return null;
		}

		CommonBean bean = new CommonBean();
		//取出第一行解析成CommonBean 的COLSNAME
		String colnames[] = prossColRow(sheet.getRow(0));
		if (colnames == null || colnames.length == 0) {
			log.error("在" + filepath + "中 " + sheetName + " 这个sheet里格式不正确!");
			return null;
		}
		bean.setColNames(colnames);

		int count = 0 ;
		//从第二行开始解析		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			//
			HSSFRow row = sheet.getRow(i);
			for (int j = 0; j < colnames.length; j++) {
				try {
					HSSFCell aCell = row.getCell(j);
					bean.addValue(colnames[j], getCellValue(aCell));
				} catch (Exception e) {
					e.fillInStackTrace();
				}
			}
		}

//		System.out.println(sheet.getLastRowNum());
		return bean;
	}

	/**
	 * 根据sheet名,从workbook中找到sheet
	 * @param workbook
	 * @param sheetName
	 * @return
	 */
	private static HSSFSheet getSheet(HSSFWorkbook workbook, String sheetName) {
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			if (sheetName.equalsIgnoreCase(workbook.getSheetName(i))) {
				return workbook.getSheetAt(i);
			}
		}
		return null;
	}

	/**
	 * 解析第一行
	 * @param row
	 * @return
	 */
	private static String[] prossColRow(HSSFRow row) {
		String cols[] = new String[row.getLastCellNum()];
		for (int i = 0; i < row.getLastCellNum(); i++) {
			HSSFCell aCell = row.getCell(i);
			if (getCellValue(aCell) != null) {
				cols[i] = getCellValue(aCell);
			}
		}
		return cols;
	}

	/**
	 * 取值
	 * @param cell
	 * @return
	 */
	private static String getCellValue(HSSFCell cell) {

		if (cell == null) {
			return "";
		}

		int cellType = cell.getCellType();

		if (HSSFCell.CELL_TYPE_NUMERIC == cellType || HSSFCell.CELL_TYPE_FORMULA == cellType) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (HSSFCell.CELL_TYPE_BOOLEAN == cellType) {
			return cell.getBooleanCellValue() + "";
		} else {
			return cell.getStringCellValue().trim(); 
		}

	}

}
