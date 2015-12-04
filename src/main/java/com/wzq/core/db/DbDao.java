package com.wzq.core.db;

import com.wzq.core.tools.CommonBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**wqf 封装了一些简单的数据库操作方法 因为项目的原因会涉及多个库 所以每个数据库操作都要指明dbType 数据源*/
public class DbDao {
	
	//一个连接最多同时运行几句SQL，防止下标过界
	private final static int excuteSQLnum = 20;
	
	/**
	 * 包装过数据库查询
	 * @param sql 
	 * @param dbType 数据源
	 * @return
	 * @throws java.sql.SQLException
	 */
	public static CommonBean getBean(String sql, String dbType) throws SQLException {
		PubQO qo = null;
		try {
			Connection connection = SQLUtil.getConn(dbType);
			qo = new PubQO(connection);
			return qo.getData(sql);
		} finally {
			if (qo != null) {
				qo.closeConnection();
			}
		}

	}

	/**
	 * 对数据库进行SQL操作
	 * @param sql
	 * @param dbType 数据源
	 * @throws java.sql.SQLException
	 */
	public static void executeUpdate(String sql, String dbType) throws SQLException {
		executeUpdate(new String[]{sql},dbType);
	}
	
	
	
	/**
	 * 对数据库进行SQL操作
	 * @param sql
	 * @param dbType 数据源
	 * @throws java.sql.SQLException
	 */
	public static void executeUpdate(String[] sql, String dbType) throws SQLException {
		PubQO qo = null;

			List<String> allSqls = new ArrayList<String>(Arrays.asList(sql));
			
			while(allSqls.size() > 0){
				List<String> nowSql = new ArrayList<String>();
				for(int i=0; i<excuteSQLnum; i++){
					if(allSqls.size() > 0)
						nowSql.add(allSqls.remove(0));
					else
						break;
				}
				
				try{
					if(nowSql.size() > 0){
						Connection c = SQLUtil.getConn(dbType);
						qo = new PubQO(c);
						qo.executeUpdate(nowSql.toArray(new String[0]));
					}	
				}finally{
					if(qo!=null){
						qo.closeConnection();
					}
				}
			
			}


	}
	
	

	/**
	 * 对数据库进行 bean操作
	 * @param bean
	 * @param dbType
	 * @throws java.sql.SQLException
	 */
	public static void excuteBean(CommonBean bean, String dbType) throws SQLException {
		PubDM dm = null;
		
		while(bean.getRowNum() > 0){
			CommonBean newBean = new CommonBean();
			newBean.setAttribute(bean.getAttribute());
			newBean.setBeanName(bean.getBeanName());
			newBean.setColNames(bean.getColNames());
			for(int i=0; i<excuteSQLnum; i++){
				if(bean.getRowNum() > 0){
					newBean.addRow(bean.getRowData(0));
					bean.removeRowData(0);
				}else{
					break;
				}
			}
			
			try{
				if(newBean.getRowNum() > 0){
					Connection c = SQLUtil.getConn(dbType);
					dm = new PubDM(c);
					dm.execute(newBean);
				}	
			}finally{
				if(dm!=null){
					dm.closeConnection();
				}
			}
		
		}

	}
}
