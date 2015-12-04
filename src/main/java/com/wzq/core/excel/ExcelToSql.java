/**Copyright 2013 Ultrapower Software Co. Ltd.
 * All right reserved. 
 * @author wangzq
 * @version 3.0.0
 *
 * @date 2013-6-25,上午11:15:52
 */
package com.wzq.core.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wzq.core.tools.CommonBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @ClassName: ExcelToSql 
 * @Description: 读取excel文件转成sql语句 
 * @author wangzq
 * @date 2013-6-25 上午11:15:52 
 * 
 * @version 3.0.0 
 */
public class ExcelToSql {

	private static Logger log = Logger.getLogger(ExcelToSql.class);

	public static void main(String[] args) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");

		SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm");

		String filePath = "D:/test.xls";
		CommonBean bean = null;
		try {
			bean = EasyExcel.getPross(filePath, "sheet1");
		} catch (Exception e) {
			log.error(filePath + "文件中解析UploadKpi Sheet有误,上报指标解析失败!"
					+ e.getMessage());
		}

		if (bean.getRowNum() == 0) {
			log.error(filePath + "文件中解析UploadKpi Sheet,上报指标个数竟然为0!");
		}
		List<String> tableNamesList = new ArrayList<String>();
		// System.out.println(bean.getRowNum());
		List<String> kpiList = new ArrayList<String>();

		for (int i = 0; i < bean.getRowNum(); i++) {
			String kpi = bean.getCellStrForce(i, "kpi");
			if ("".equalsIgnoreCase(kpi) || kpi == null) {
				System.err.println("error");
			}
			if (!kpiList.contains(kpi)) {
				kpiList.add(kpi);
			}
			for (int j = 1; j < bean.getColumnNum(); j++) {
				String time = "2012-08-14T00:00:00";
				long currentTime = 0;
				long newTime = 0;
				try {
					currentTime = dateFormat.parse(time).getTime() + (j - 1)
							* 3600 * 1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}

				newTime = currentTime + 3600 * 1000;
				String minTime = newFormat.format(currentTime);
				String maxTime = newFormat.format(newTime);
				if (minTime.equalsIgnoreCase("23:00")) {
					maxTime = "24:00";
				}

				String timeScope = minTime + "-" + maxTime;
				String domain = bean.getCellStrForce(i, timeScope);

				String sql = "insert into DOMAINPRIORITY values('" + kpi
						+ "','" + timeScope.replace("-", ",") + "','" + domain
						+ "','1','workday','0');";
				tableNamesList.add(sql);
				System.out.println(sql);
			}

		}

		writeExcel(tableNamesList);

		System.out.println("结束。。。。。。。。。。。。");

	}

	private static void writeExcel(List<String> sqlList) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("d:\\sql小时.xls");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "Sheet1");

		for (int i = 0; i < sqlList.size(); i++) {

			HSSFRow row = s.createRow(i);
			HSSFCell cell3 = row.createCell((short) 0);
			cell3.setCellValue(sqlList.get(i));

		}
		try {
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("文件写入完成");
	}
//	public static void main(String[] args) {
	//
//			String filePath = "D:/test.xls";
//			CommonBean bean = null;
//			try {
//				bean = EasyExcel.getPross(filePath, "Sheet2");
//			} catch (Exception e) {
//				log.error(filePath + "文件中解析UploadKpi Sheet有误,上报指标解析失败!"
//						+ e.getMessage());
//			}
	//
//			if (bean.getRowNum() == 0) {
//				log.error(filePath + "文件中解析UploadKpi Sheet,上报指标个数竟然为0!");
//			}
//			System.out.println("开始。。。。。。。。。。。。");
//			System.out.println(bean.getRowNum());
//			List<String> sqlList = new ArrayList<String>();
//			Map<String, Map<Integer, Integer>> domainMap = new HashMap<String, Map<Integer,Integer>>();
//			for (int i = 0; i < bean.getRowNum(); i++) {
//				String kpiId = bean.getCellStrForce(i, "KPI");
//				String kpiName = bean.getCellStrForce(i, "KPINAME");
//				String interval = bean.getCellStrForce(i, "INTERVAL");
//				String importance = bean.getCellStr(i, "IMPORTANCE");
//				String indexType = bean.getCellStrForce(i, "INDEXTYPE");
//				String min = bean.getCellStrForce(i, "MIN");
//				String max = bean.getCellStrForce(i, "MAX");
//				String chief = bean.getCellStrForce(i, "CHIEF");
//				String manager = bean.getCellStrForce(i, "MANAGER");
//				String direction = bean.getCellStrForce(i, "DIRECTION");
//				String factor1 = bean.getCellStrForce(i, "FACTOR1");
//				String factor2 = bean.getCellStrForce(i, "FACTOR2");
//				String factor3 = bean.getCellStrForce(i, "FACTOR3");
//				String factor4 = bean.getCellStrForce(i, "FACTOR4");
//				String factor5 = bean.getCellStrForce(i, "FACTOR5");
//				String domain = bean.getCellStrForce(i, "DOMAIN");
//				
//				String varmax = bean.getCellStrForce(i, "VARMAXINTERVAL");
//				String moreci = bean.getCellStrForce(i, "CI");
//				String code = bean.getCellStrForce(i, "CODE");
				
//				String domain = bean.getCellStrForce(i, "domain");
//				String var = bean.getCellStrForce(i, "VAR");
//				String number = bean.getCellStrForce(i, "number");
//				int newDomain = (int)Double.parseDouble(domain);
//				int newNumber = (int)Double.parseDouble(number);
//				Map<Integer, Integer> numberMap = new HashMap<Integer, Integer>();
//				if (domainMap.containsKey(var)) {
//					numberMap = domainMap.get(var);
//					numberMap.put(newDomain, newNumber);
//					domainMap.put(var, numberMap);
//				}else {
//					numberMap.put(newDomain, newNumber);
//					domainMap.put(var, numberMap);
//				}
//				
//				if (interval.equalsIgnoreCase("季度")) {
//					interval = "7776000";
//				} else if (interval.equalsIgnoreCase("年")) {
//					interval = "31104000";
//				} else if (interval.equalsIgnoreCase("月")) {
//					interval = "2592000";
//				} else if (interval.equalsIgnoreCase("天")) {
//					interval = "86400";
//				} else if (interval.equalsIgnoreCase("小时")) {
//					interval = "3600";
//				}
	//
//				String id = interval+"_"+kpiId;
////				
//				String sql1 = null;
//				int newZero = 0;
//				if (zero.equalsIgnoreCase("是")) {
//					newZero = 1;
//				}
//				
//				if (kpiName.equalsIgnoreCase("按小时考核，考核时段09:00-17:00")) {
//					sqlList.add(kpiId);
//				}

				// System.out.println(kpiId +" "+fm);
//				sql1 = "insert into domainpriority (KPIID, TIMESCOPE, DOMAIN,PRIORITY, TIMETYPE)" + "values ('"
//						+ kpiId + "', '" + timeScope + "', '" + domain + "', '" + priority + "','" + timeType + "');";
//				System.out.println(sql1);
//				String sql2 = "insert into upload_capes (ID, KPI, " +
//				 		"KPI_NAME, INTERVAL, IMPORTANCE, INDEXTYPE, MIN, MAX, CHIEF, " +
//				 		"MANAGER, DIRECTION, DOMAINID, FACTOR1, FACTOR2, " +
//				 		"FACTOR3, FACTOR4, FACTOR5, VARMAXINTERVAL, MORECI, " +
//				 		"CODE)values " +
//				 		"('"+id+"', '"+kpiId+"', " +
//				 		"'"+kpiName+"', '"+interval+"', '"+importance+"', '"+indexType+"', " +
//				 		"'"+min+"', '"+max+"', '"+chief+"', '"+manager+"', '"+direction+"', '"+domain+"', '"+factor1+"', " +
//				 		"'"+factor2+"', '"+factor3+"', '"+factor4+"', '"+factor5+"', '"+varmax+"', '"+moreci+"', '"+code+"');";

				
//				
//				System.out.println(sql2);
//				
//				if (!sqlList.contains(sql2)) {
//					sqlList.add(sql2);
//				}else {
//					continue;
//				}
//				String sql3 = "update UPLOAD_KPI_INFO_EXT set EUID= "+(i+300)+" WHERE KPI_ID = '"+kpiId+"' ;";
//				System.out.println(sql3);
				
//				sql1 = "update cp_om_kpi_info set SOURCECODE = '"+zero+"' where kpi_id = '"+kpiId+"';";
//				System.out.println(sql1);

//			}
			
//			for(Entry<String, Map<Integer, Integer>> entry : domainMap.entrySet()){
//				
//				String sourcecode = entry.getKey();
//				Map<Integer, Integer> secMap = entry.getValue();
//				for(Entry<Integer, Integer> entry1 : secMap.entrySet()){
//					int domainId = entry1.getKey();
//					int seq = entry1.getValue();
//					String sql2 = "update cp_om_kpi_info set domain_id = "+domainId+" , dispose_order = "+seq+"  where sourcecode = '"+sourcecode+"';";
//					if (!sqlList.contains(sql2)) {
//						sqlList.add(sql2);
//					}else {
//						continue;
//					}
//				}
//				
//			}
//			
			
			
//			for (int i = 0; i < sqlList.size(); i++) {
//				System.out.println("'"+sqlList.get(i)+"',");
//			}
			
//			writeExcel(sqlList);
////			
//			System.out.println("结束。。。。。。。。。。。。");
//			
//		}

}
