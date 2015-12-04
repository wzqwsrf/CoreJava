package com.wzq.core.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wzq.core.tools.CommonBean;
import org.apache.log4j.Logger;
 

/**
 * 数据库访问基类
 * Utility for PreparedStatement to set argument
 * @author  GuoQing LI
 * @version $Id: SQLUtil.java,v 1.4 2011/12/23 14:41:29 wangzq Exp $
 */
public class SQLUtil implements Serializable {
	private static Logger log = Logger.getLogger(SQLUtil.class);
	
	public static String CON_KEY_UPLOAD = "upload";
	public static String CON_KEY_ALERT = "alert";
	public static String CON_KEY_CMDB = "cmdb";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3467128003956756011L;

	public final static Properties DB_CONF;
	
	public final static String[] ds_names;
	
	private static final Map<String, ComboPooledDataSource> connPools = new HashMap<String, ComboPooledDataSource >();
	
	static{
		Properties p = new Properties();

		String fileName = "jndi.properties";
		InputStream fileIn = SQLUtil.class.getClassLoader()
			.getResourceAsStream(fileName);
		try {
			if (fileIn == null) {
				fileIn = new FileInputStream("./conf/" + fileName);
			}

			p.load(fileIn);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DB_CONF = p;
		
		String ds = DB_CONF.getProperty("datasource");
		ds_names = ds.split(Pattern.quote("|"));
		for(String dsName : ds_names)
			try{
				connPools.put(dsName, readDs2Pool(dsName));
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static SQLUtil getInstance(){
		return new SQLUtil();
	}
	
	private static ComboPooledDataSource readDs2Pool(String name) throws Exception{
		String url = DB_CONF.getProperty(name + ".url");
		String drivername = DB_CONF.getProperty(name + ".drivername");
		String username = DB_CONF.getProperty(name + ".username");
		String password = DB_CONF.getProperty(name + ".password");
		String poolsize = DB_CONF.getProperty(name + ".poolsize");

		ComboPooledDataSource ds = new ComboPooledDataSource();   
		
		ds.setDriverClass(drivername);  // 参数由 Config 类根据配置文件读取   
        // 设置JDBC的URL   
        ds.setJdbcUrl(url);   
        //设置数据库的登录用户名   
        ds.setUser(username);   
        //设置数据库的登录用户密码   
        ds.setPassword(password);   
        //设置连接池的最大连接数   
        ds.setMaxPoolSize(50);   
         // 设置连接池的最小连接数   
        ds.setMinPoolSize(Integer.parseInt(poolsize));  
        
        ds.setCheckoutTimeout(1000);

        ds.setAcquireRetryAttempts(1);
	        
        ds.setAcquireRetryDelay(500);

		ds.setTestConnectionOnCheckout(true);


        
		return ds; 
	}
	
	public static Connection getConn(String key) throws SQLException{
		return connPools.get(key).getConnection();
	}
	
	public static Connection getConnection(){
		try{
			return getConn("upload");
		}catch (Exception e) {
			log.error("严重，取不到数据库连接", e);
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
		PubQO qo = null;
		try{
			Connection c  = SQLUtil.getInstance().getConnection();
			qo = new PubQO(c);
			CommonBean bean  = qo.getData("select * from actlink_auto");
			qo.executeQuery("insert into testaaa values ('"+bean.getRowNum()+"')");
			
			System.out.println(bean.getRowNum());
			log.info(bean.getRowNum());
			
		}catch(Exception e){
			System.err.println(e.getMessage());
			log.error("",e);
		}finally{
			if(qo!=null){
				qo.closeConnection();
			}
		}
		
	
	}
	

	
	public static boolean testDb(String key){
		// 是否有数据源
		if(!Arrays.asList(ds_names).contains(key)){
			log.error("当前不包含key为 " + key + " 的数据源");
			return false;
		}
		
		// 是否能取连接
		Connection conn = null;
		try{
			conn = getConn(key);
		}catch (Exception e) {
			log.error("在取数据源 " + key + " 的连接时发生错误", e);
			return false;
		}finally{
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					log.warn("在测试关闭数据源 " + key + " 的连接时发生错误", e);
					return false;
				}
		}
		
		return true;
	}
	
	/**
	 * 测试一遍所有数据源有效否
	 * @return
	 */
	public static boolean testAllDb(){
		for(String key : ds_names)
			if(!testDb(key))
				return false;
		
		return true;
	}
	
	
}
