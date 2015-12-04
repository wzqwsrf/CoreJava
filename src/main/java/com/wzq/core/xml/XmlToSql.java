package com.wzq.core.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wzq.core.tools.TimeTool;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2013-6-25 上午11:47:27
 * Description: 读取目录下的所有capes文件，转为sql语句插入到数据库
 */
public class XmlToSql {

    private static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) {

        List<String> sqlList = new ArrayList<String>();
        String dir_name = "D:\\send";
        File f = new File(dir_name);

        //返回该文件夹下所有文件的名称列表
        String[] fileNames = f.list();

        for (int j = 0; j < fileNames.length; j++) {
            String fileName = fileNames[j];
            String filePath = dir_name + "\\" + fileName;
            Document document;
            File file = new File(filePath);
            SAXReader saxReader = new SAXReader();
            int num = 0;
            try {
                document = saxReader.read(file);
                List<Element> rcds = document.selectNodes("/capes/variable/record");
                for (int i = 0; i < rcds.size(); i++) {

                    Element element = rcds.get(i);
                    Node kpiIdN = element.selectSingleNode("id");
                    String kpiId = kpiIdN.getText();
                    Node kpiNameN = element.selectSingleNode("name");
                    String kpiName = kpiNameN.getText();
                    Node valueNode = element.selectSingleNode("value");
                    String value = valueNode.getText();
                    Node ciIdNode = element.selectSingleNode("ciid");
                    String ciId = ciIdNode.getText();

                    String fileMsg[] = fileName.split("_");

                    Long[] times = changeBeginEndTime("abc",
                            fileMsg[3], fileMsg[4]);
                    String sql = "insert into upload_REcord (ID, KPI_ID, KPI_NAME, FILENAME, CI_ID, " +
                            "VALUE, CI_NAME, TYPE, RAW_VALUE, START_TIME, END_TIME, UPDATED, " +
                            "TIMES_VALUE, CHAIN_VALUE, ERROR_ID) " +
                            "values ('" + UUIDGenerator.getUUID() + "', '" + kpiId + "', " +
                            "'" + kpiName + "', '" + fileName + "', " +
                            "'" + ciId + "', " + value + ", 'CINAME', " +
                            "'CAPES', null, " + times[0] + ", " + times[1] + ", 1, null, null, null);";
                    sqlList.add(sql);
//					System.out.println(sql);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        writeExcel(sqlList);
        System.out.println("结束。。。。。。。。。。。。");
    }


    private static void writeExcel(List<String> sqlList) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("d:\\upload_record_sql.xls");
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

    public static Long[] changeBeginEndTime(String interval, String time,
                                            String seqNum) {
        Long[] beginEndTime = new Long[2];
        try {
            long longTime = yyyymmdd.parse(time).getTime();

            long beginTime = 0;
            long endTime = 0;

            if (interval.equalsIgnoreCase("2592000")
                    || interval.equalsIgnoreCase("86400")) {
                beginTime = longTime;
                if (interval.equalsIgnoreCase("2592000")) {
                    endTime = TimeTool.monthMove(beginTime, 1);
                } else {
                    endTime = TimeTool.dateMove(beginTime, 1);
                }
            } else {
                endTime = longTime + (Integer.parseInt(seqNum))
                        * Integer.parseInt(interval) * 1000;
                beginTime = endTime - Long.parseLong(interval) * 1000;
            }

            beginEndTime[0] = beginTime;
            beginEndTime[1] = endTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return beginEndTime;
    }


    public static class UUIDGenerator {


        public UUIDGenerator() {
            super();
        }

        public static String getUUID() {
            String uuid = UUID.randomUUID().toString();
            return uuid.replaceAll("-", "");
        }


    }

}

