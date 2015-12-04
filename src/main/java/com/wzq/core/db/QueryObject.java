package com.wzq.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Vector;

import com.wzq.core.tools.CommonBean;
import org.apache.log4j.Logger;

/**
 * 查询公共类。

 * 创建日期：(2001-3-16 11:38:53)
 * @author：韦建儒
 */
@SuppressWarnings("unchecked")
public abstract class QueryObject extends DataBaseObject {
	private int iPageNo = 0; //页号
	private int iMaxRow = 0; //每页最大行数
	private int iHasReadRows = 0; //这页已读行数
	private int iHasReadTotalRows = 0; //已读过总数
	private boolean bMoveCursor = false; //游标是否移动过
	private int MAXTOTALROWS = 100000000; //本系统能查的记录数为100000条
	public String strSql = null;
	private boolean tooMuchFlag = false;
	private static Logger log = Logger.getLogger(QueryObject.class);
	/**
	 * QueryObject 构造子注解。

	 * @exception javax.naming.NamingException 异常说明。

	 */
	public QueryObject() {
		super();
	}
	
	/**
	 * 生成一个有指定行的commonbean 同时对它们每行做些事情 直到做完所有行
	 * @param everyDoRow
	 * @return
	 */
	protected List<String> getCommenData2Do(int everyDoRow, CBRowsEvent cbre ,List<String> keys) throws Exception {
		Vector vRet = new Vector();

		int iColNum = getColumnCount();
		if(iColNum==-1)
			return null;

		String sHead[] = new String[iColNum];
		for (int i = 0; i < iColNum; i++)
			sHead[i] = getRSMD().getColumnLabel(i + 1);

		// 组commonbean
		int c = 0;
		boolean hasDo = false;
		while (nextrow()) {
			Vector vRow = new Vector();
			for (int i = 1; i <= iColNum; i++)
				vRow.addElement(getField(i));
			vRet.addElement(vRow);

			c++;
			hasDo = false;
			// 是否够行了
			if(c == everyDoRow){
				// 够了 整个commonbean 做一次事情
				CommonBean cb = new CommonBean();
				cb.setColNames(sHead);
				cb.setVecData(vRet);

				keys = cbre.doRowEvent(cb,keys);
				
				vRet = new Vector();
				hasDo = true;
				c = 0;
				//System.gc();
			}
		}
		
		// 如果最后一次没做 就做一次
		if(!hasDo && vRet != null){
			// 够了 做一次事情
			CommonBean cb = new CommonBean();
			cb.setColNames(sHead);
			cb.setVecData(vRet);

			keys = cbre.doRowEvent(cb ,keys);
		}
		
		realease();
		return keys;
	}	
	
	protected CommonBean getCommenData() throws SQLException {

		Vector vRet = new Vector();

		int iColNum = getColumnCount();
		if(iColNum==-1){
			return new CommonBean();
		}
		String sHead[] = new String[iColNum];
		for (int i = 0; i < iColNum; i++)
			sHead[i] = getRSMD().getColumnLabel(i + 1);

		while (nextrow()) {
			Vector vRow = new Vector();
			for (int i = 1; i <= iColNum; i++)
				vRow.addElement(getField(i));
			vRet.addElement(vRow);
		}
		realease();


		CommonBean cb = new CommonBean();
		cb.setColNames(sHead);
		cb.setVecData(vRet);
		return cb;
	}
	
	protected CommonBean getCommenData(int maxrows) throws SQLException {

		Vector vRet = new Vector();

		int iColNum = getColumnCount();
		String sHead[] = new String[iColNum];
		for (int i = 0; i < iColNum; i++)
			sHead[i] = getRSMD().getColumnLabel(i + 1);
		int count = 0;
		while (nextrow()) {
			Vector vRow = new Vector();
			for (int i = 1; i <= iColNum; i++)
				vRow.addElement(getField(i));
			vRet.addElement(vRow);
			if (count++ > maxrows)
				break;
		}

		realease();
		CommonBean cb = new CommonBean();
		cb.setColNames(sHead);
		cb.setVecData(vRet);
		return cb;
	}
	/**
		查询sql
	 */
	public CommonBean[] getData(String[] sSql) {
		if (sSql == null)
			return null;
		CommonBean cb[] = new CommonBean[sSql.length];

		for (int i = 0; i < cb.length; i++)
			cb[i] = getData(sSql[i]);
		return cb;
	}
	/**
		查询sql
	 */
	public CommonBean getData(String sSql) {
		strSql = sSql;
		log.info(sSql);
		System.err.println(sSql);
		try {
			return getCommenData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	public CommonBean getDataWithError(String sSql) throws SQLException {
		strSql = sSql;
		return getCommenData();
	}

	public CommonBean getData(String sSql, int maxrows) {
		strSql = sSql;
		try {
			return getCommenData(maxrows);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected CommonBean getDataType() throws SQLException {

		Vector vecRet = new Vector();
		Vector vecRow = new Vector();

		int iColNum = getColumnCount();
		String sHead[] = new String[iColNum];
		for (int i = 0; i < iColNum; i++) {
			sHead[i] = getRSMD().getColumnLabel(i + 1);

			int iType = getRSMD().getColumnType(i + 1);
			String sType = "0";
			switch (iType) {
				case Types.BIGINT :
				case Types.BINARY :
				case Types.BIT :
				case Types.DECIMAL :
				case Types.DOUBLE :
				case Types.FLOAT :
				case Types.INTEGER :
				case Types.NULL :
				case Types.NUMERIC :
				case Types.REAL :
				case Types.SMALLINT :
					sType = "1";
					break;
				case Types.TIME :
				case Types.TIMESTAMP :
				case Types.TINYINT :
				case Types.CHAR :
				case Types.DATE :
				case Types.VARBINARY :
				case Types.VARCHAR :
				case Types.LONGVARCHAR :
				case Types.LONGVARBINARY :
					sType = "0";
					break;
				default :
					sType = "0";
			}

			vecRow.addElement(sType);
		}
		vecRet.addElement(vecRow);

		realease();
		CommonBean cb = new CommonBean();
		cb.setColNames(sHead);
		cb.setVecData(vecRet);
		return cb;
	}
	private Object getField(int column) throws SQLException {
		Object obj = getField(column, false);
		//if (obj != null && obj.getClass() == String.class)
		//return mycrm.pub.tool.strtool.StrTool.GB2Unicode(obj.toString());
		//else
		return obj;
	}
	
	
	/**
	 *  必须添加的方法，这可以实现对SQL语句的统一管理。

	 * @。

	 */
	public String getFinalSql() {
		return strSql;
	}
	/**
	 * 反回已读过的记录数。

	 * 创建日期：(2001-4-13 19:32:13)
	 * @return int
	 */
//	private int getHasReadRows() {
//		return iHasReadRows;
//	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-4-13 17:54:42)
	 * @return int
	 */
	private int getIMaxRow() {
		return iMaxRow;
	}
	//该方法暂时不能用

	public int getIResultRows() {
		return iHasReadTotalRows;
	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-4-13 17:42:12)
	 * @return int
	 */
	private int getPageNo() {
		return iPageNo;
	}
	//该方法暂时不能用

	public Vector getRow() throws SQLException {
		int iCount;
		iCount = getColumnCount();
		Vector vRow = new Vector();
		for (int i = 1; i <= iCount; i++)
			vRow.addElement(getField(i));
		return vRow;

	}
	protected ResultSet getRS() throws SQLException {
		ResultSet rs = getResultSet();
		if (rs != null)
			while (rs.next());

		if (rs != null)
			try {
				rs.beforeFirst();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		closeRS();
		return rs;
	} /**
				 * 此处插入方法说明。

				 * 创建日期：(2001-6-8 17:33:04)
				 * @return boolean
				 */
	/**
	 * 是否分页。

	 * 创建日期：(2001-4-13 19:28:46)
	 * @return boolean
	 */
	private boolean isPageDiv() {
		//假如页号和每页最大行数不为0则需要为页;
		return (getPageNo() > 0 && getIMaxRow() > 0);
	}
	public boolean isTooMuchFlag() {
		return tooMuchFlag;
	}
	public boolean nextrow() throws SQLException {

		if (isPageDiv() && (!bMoveCursor)) //设置分页且游标未动过
			{
			bMoveCursor = true;

			int iLastRowNo = (getPageNo() - 1) * getIMaxRow(); //上页的最大行号


			if (getResultSet() != null) {
				while (iHasReadTotalRows < iLastRowNo && next()) //用next() 而不用absolute(int)是因为网上数据变动较大，可能数据已被删除很多，例外较多


					{
					iHasReadTotalRows++;
					if (iHasReadTotalRows > MAXTOTALROWS) {
						setTooMuchFlag(true);
						return false;
					}
				}
			}

		}

		boolean bPagDiv = (!isPageDiv()) || isPageDiv() && iHasReadRows++ < getIMaxRow();
		//不分页 或分页且已读数小于最大可读数，


		if (bPagDiv) {
			if (next()) {
				iHasReadTotalRows++;
				if (iHasReadTotalRows > MAXTOTALROWS) {
					setTooMuchFlag(true);
					return false;
				}
				return true;
			}
			return false;
		} else
			return false;
	} /**
				 * 此处插入方法说明。

				 * 创建日期：(2001-6-16 16:40:04)
				 * @param iMaxTotal int
				 */
	public Vector readAll() throws SQLException {

		Vector vRet = new Vector();

		int iCount;
		iCount = getColumnCount();
		while (nextrow()) {
			Vector vRow = new Vector();
			for (int i = 1; i <= iCount; i++)
				vRow.addElement(getField(i));
			vRet.addElement(vRow);
		}
		realease();
		return vRet;
	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-4-13 19:51:37)
	 */
	public void realease() throws SQLException {
		bMoveCursor = false;
		iMaxRow = 0;
		iPageNo = 0;
		iHasReadRows = 0;

		while (next()) {
			iHasReadTotalRows++;
		}
		closeRS();

	}
	/**
	 * 设置已读过的记录数。

	 * 创建日期：(2001-4-13 19:32:13)
	 * @param newHasReadRows int
	 */
//	private void setHasReadRows(int newHasReadRows) {
//		iHasReadRows = newHasReadRows;
//	}
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-4-13 17:54:42)
	 * @param newIMaxRow int
	 */
	public void setIMaxRow(int newIMaxRow) {
		iMaxRow = newIMaxRow;
	}
	public void setMAXTOTALROWS(int iMaxTotal) {
		MAXTOTALROWS = iMaxTotal;
	} /**
				 * 此处插入方法说明。

				 * 创建日期：(2001-6-8 17:33:04)
				 * @param newTooMuchFlag boolean
				 */
	/**
	 * 此处插入方法说明。

	 * 创建日期：(2001-4-13 17:42:12)
	 * @param newPageNo int
	 */
	public void setPageNo(int newPageNo) {
		iPageNo = newPageNo;
	}
	public void setTooMuchFlag(boolean newTooMuchFlag) {
		tooMuchFlag = newTooMuchFlag;
	}
	
	/**
	 * 要对commonbean 每行做的事
	 * @author Administrator
	 *
	 */
	public static interface CBRowsEvent{
		public void doRowEvent(CommonBean commonBeanRow) throws Exception;
		public List<String> doRowEvent(CommonBean commonBeanRow, List<String> keys) throws Exception;
	}

	/**
	 * 做一些事情
	 * @param sql
	 * @param everyCBRowCount
	 * @param cbre
	 * @throws Exception
	 */
	public void getAndDoData(String sql, int everyCBRowCount, CBRowsEvent cbre ,List<String> keys) throws Exception{
		strSql = sql;
		log.info(sql);
		System.err.println(sql);
		getCommenData2Do(everyCBRowCount, cbre ,keys);
	}
}

