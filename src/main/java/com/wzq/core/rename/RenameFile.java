package com.wzq.core.rename;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2013-5-28 下午05:30:11
 * Description: 修改某个文件夹下的所有的文件名称
 */

public class RenameFile {

    private static Log log = LogFactory.getLog(RenameFile.class);

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public static void findFile(String filePath) {

        File file = new File(filePath);
        if (file.isDirectory()) {
            String[] fileDir = file.list();
            String fileNameWithPath;
            for (int i = 0; i < fileDir.length; i++) {
                String fileNewName = "";
                System.out.println(file.getAbsolutePath() + "\\" + fileDir[i]);
                fileNameWithPath = file.getAbsolutePath() + "\\" + fileDir[i];

                String[] fs = fileDir[i].split("_");
                String[] fileMsg = fs[4].split(Pattern.quote("."));

//				fileMsg[0] = fileMsg[0].replace(fileMsg[0].substring(0, 8), "20131227");
//				String mtime = fs[3];
//				long date = 0;
                try {
//					date = dateFormat.parse( mtime ).getTime();
//					long move = TimeTool.dateMove( date, -1 );
//					String time = dateFormat.format( move );
//				    String num = "";
//				    if ( i+1<10 ) {
//				    	num = "00"+(i+1);
//					}else {
//						num = "0"+(i+1);
//					}

                    fileNewName = fs[0] + "_" + fs[1] + "_" + fs[2] + "_20140213_" + fs[4] + "_" + fs[5];

                    System.out.println(fileNewName);
                    List<String> allLineList = getTxtAllLines("d:\\1\\" + fileDir[i]);
                    StringBufferDemo(new File("d:\\2\\" + fileNewName), allLineList);
//					File oldFile = new File(fileNameWithPath);
//					oldFile.renameTo( new File( "d:\\2\\" + fileNewName ) );
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

    public static void StringBufferDemo(File file, List<String> allLineList) throws IOException {
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, true);
        for (int i = 0; i < allLineList.size(); i++) {
            StringBuffer sb = new StringBuffer();
            sb.append(allLineList.get(i) + "\n");
            out.write(sb.toString().getBytes("GBK"));
        }
        out.close();
    }

    /**
     * @param fileName
     * @return
     * @Description: TODO
     * @date 2013-7-11,下午04:30:48
     * @author wangzq
     * @version 3.0.0
     */
    public static List<String> getTxtAllLines(String fileName) {
        List<String> allLineList = new ArrayList<String>();
        try {
            String encoding = "GBK";
            File file = new File(fileName);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    log.info(lineTxt);
                    allLineList.add(lineTxt.replace("2014-02-14", "2014-02-13"));
                }
                read.close();
            } else {
                log.error("找不到指定的文件");
            }
        } catch (Exception e) {
            log.error("读取文件内容出错");
            e.printStackTrace();
        }
        return allLineList;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // TODO Auto-generated method stub
        findFile("d://1");
    }
}
