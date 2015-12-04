package com.wzq.core.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import com.wzq.core.tools.StrTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 数据库连接类。   

 * 创建日期：(2001-3-16 11:04:51)
 * @author：韦建儒
 */
public abstract class DataBaseObject {

	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private Statement stmt;
	public Connection con;
	private PreparedStatement pstmt;

	protected Integer hiddenRsRowCount[] = new Integer[3];
	private static Log log = LogFactory.getLog(DataBaseObject.class);

	/**
	 * DataBaseObject 构造子注解。

	 */
	public DataBaseObject() {
		super();

	}
	public void closeConnection() throws SQLException {
		if (getConnection() != null) {
			getConnection().close();
			setConnection(null);
		}
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (rsmd != null) {
			rsmd = null;
		}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		if(pstmt != null){
			pstmt.close();
			pstmt = null;
		}

	}
	public void closeRS() throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (rsmd != null) {
			rsmd = null;
		}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		if(pstmt != null){
			pstmt.close();
			pstmt = null;
		}
	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-3-27 18:02:29)
	 * @return java.lang.String
	 */
	private void doQuery() throws SQLException {
		long ib = System.currentTimeMillis(); 
		executeQuery(getFinalSql());
		long ie = System.currentTimeMillis();

		//mycrm.out.print("\n\n =================查询完成, 您共花了 " + (ie - ib) + " 毫秒 ================================================");
	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-3-16 12:56:49)
	 * @param sql java.lang.String
	 */
	protected void executeQuery(String sql) throws SQLException {
		String strSqlBeforeFormat = sql;

		//log.info(sql);
		//System.err.println(sql);
		try {
			if (con.isClosed())
				throw new SQLException("con is close");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			//closeConnection();
		}

	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-3-16 12:56:49)
	 * @param sql java.lang.String
	 */
	public boolean executeUpdate(String[] sql) throws SQLException {

		if (sql == null)
			throw new SQLException("sql is null");

		try {
			if (con.isClosed())
				throw new SQLException("con is close");

			stmt = con.createStatement();
			
			for (int i = 0; i < sql.length; i++) {
				
				try{
					if (sql[i] == null || sql[i].trim().length() == 0)
						throw new SQLException("sql is null");
					
					
					log.info(sql[i]);
					System.err.println(sql[i]);
					
					stmt.executeUpdate(sql[i]);
				}catch(Exception e){
					log.error(e.getMessage() +" "+sql[i]);
					System.err.println(e.getMessage() +" "+sql[i]);
				}
			
			}
			
			return true;

		} finally {
		}

	}
	
	public boolean executeUpdateParam(String sql, Object[] params) throws SQLException{
		return executeUpdateParam(new String[]{sql}, new Object[][]{params});
	}
	
	public boolean executeUpdateParam(String[] sql, Object[][] params) throws SQLException{
		if (sql == null)
			throw new SQLException("sql is null");

		try {
			if (con.isClosed())
				throw new SQLException("con is close");

			for (int i = 0; i < sql.length; i++) {
				
				try{
					if (sql[i] == null || sql[i].trim().length() == 0)
						throw new SQLException("sql is null");
					
					// sql语句
					pstmt = con.prepareStatement(sql[i]);
					
					// 添加参数
					for(int j=0; j<params[i].length; j++)
						if(params[i][j] instanceof Date)
							pstmt.setObject(j + 1, new Timestamp( ((Date)params[i][j]).getTime() ));
						else
							pstmt.setObject(j + 1, params[i][j]);
					
					// log
					System.err.println(logSqlAndParam(sql[i], params[i]));
					
					pstmt.execute();
					
					pstmt.close();
					pstmt = null;
				}catch(Exception e){
					log.error(e.getMessage()+" "+logSqlAndParam(sql[i], params[i]));
				}
				
			}
			
			return true;

		} finally {
		}
	}
	
	
	public boolean executeUpdate(String sql) throws SQLException {
		return executeUpdate(new String[] { sql });
	}

	
	protected int getColumnCount() throws SQLException {
		if (rsmd == null)
			doQuery();
		if (rsmd == null){
			//System.err.println("ResultSet is null.");
			return -1;
		}
		return rsmd.getColumnCount();
	}
	protected Connection getConnection() throws SQLException {
		return con;
	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-3-27 18:18:58)
	 * @return java.lang.String
	 */
	public abstract String getFinalSql();
	protected ResultSet getResultSet() throws SQLException {
		if (rs == null)
			doQuery();
		if (rs == null)
			throw new SQLException("rs is null");
		return rs;
	}
	protected ResultSetMetaData getRSMD() throws SQLException {
		if (rs == null)
			doQuery();
		if (rsmd == null)
			throw new SQLException("rsmd is null");
		return rsmd;
	}
	public boolean next() throws SQLException {
		if (rs == null)
			getResultSet();
		if (rs == null)
			throw new SQLException("rs is null");
		return rs.next();
	}
	public void setConnection(Connection con) {
		this.con = con;
	}
	

	protected Object getField(int column, boolean convertToString) throws SQLException {
		if (rs == null || rsmd == null)
			throw new SQLException("ResultSet is null.");
		Object obj = rs.getObject(column);
		
		//
		if (rsmd.getColumnType(column) == -9) {
			obj = rs.getString(column);
		}
		if (rsmd.getColumnType(column) == Types.TIMESTAMP) {
			if (obj != null && obj.toString().length() > 9) {

				String sObject = obj.toString();
				if (sObject.lastIndexOf(".") != -1) {
					sObject = sObject.substring(0, sObject.lastIndexOf("."));
					String sTemp = sObject.substring(10, sObject.length());
					sTemp = StrTool.replaceStr(sTemp, ":", "");
					sTemp = StrTool.replaceStr(sTemp, "0", "");
					if (sTemp.trim().equals(""))
						sObject = sObject.substring(0, 10);
				}
				obj = sObject;

			}
		}
		if (rsmd.getColumnType(column) == Types.CLOB) { //对oracle的超长字符类型CLOB的特殊处理


//			if (obj != null) {
//				oracle.sql.CLOB clobTemp = (oracle.sql.CLOB) obj;
//				String strTemp = clobTemp.getSubString((long) 1, (int) clobTemp.length());
//				obj = strTemp;
//			}
		}
		return obj;

	}
	
	/**
	 * log sql信息
	 * @param sql
	 * @param params
	 * @return
	 */
	protected String logSqlAndParam(String sql, Object[] params){
		if(params == null)
			params = new Object[0];
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if(params.length > 0){
			sb.append("\nParameters:\n");
			for(int i=0; i<params.length; i++){
				sb.append("\tIndex: " + (i + 1));
				sb.append("\tClass Name: " + params[i].getClass().getName());
				sb.append("\tValue of String: " + params[i].toString() + "\n");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
}

