package com.wzq.core.db;

import com.wzq.core.tools.CommonBean;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 公共查询类。

 * 创建日期：(2001-5-25 17:27:50)
 * @author：韦建儒
 */
public class PubQO extends QueryObject {
	static java.util.Hashtable hSql = new java.util.Hashtable();

	public PubQO() {
		super();
	}

	public PubQO(Connection con) {
		super();
		this.con = con;
	}

	public String[][] getArr(CommonBean cb) {
		if (cb != null || cb.getRowNum() > 0) {
			String arr[][] = new String[cb.getRowNum()][cb.getColumnNum()];
			for (int i = 0; i < cb.getRowNum(); i++)
				for (int j = 0; j < cb.getColumnNum(); j++)
					arr[i][j] = cb.getCellStr(i, j);
			return arr;
		}
		return null;

	}
	
	
	public CommonBean getDataType(CommonBean bean) throws SQLException {

		if (bean == null || bean.getBeanName() == null)
			return null;

		
		String cols[] = bean.getColNames();
		if (cols == null || cols.length == 0)
			return null;

		strSql = " select * from " + bean.getBeanName() + " where 0>1";
		CommonBean cbDataType = getDataType();
		return cbDataType;

	}
	

}

