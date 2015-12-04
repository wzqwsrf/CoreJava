package com.wzq.core.tools;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Vector;

public class CommonBean implements java.io.Serializable {

	private String beanName; //Bean 的名字

	private String attribute = null; //Bean属性，有多种用途，一种是为了区分不同的数据，另一种是向数据提交时声明是增删改操作类型

	private Hashtable hListData = null; //存放列表值

	private String[] colNames = null; //表头名字

	private Vector vecColumnName = null; //表头名字

	private Vector vecData = new Vector(); //结果集

	/**
	 * CommonBean 构造子注解。

	 */
	public CommonBean() {
		super();
	}

	public CommonBean[] toArray() {
		CommonBean[] retBean = new CommonBean[vecData.size()];
		for (int i = 0; i < retBean.length; i++) {
			retBean[i] = new CommonBean();
			retBean[i].beanName = beanName;
			retBean[i].attribute = attribute;
			retBean[i].colNames = colNames;
			retBean[i].vecColumnName = vecColumnName;
			retBean[i].vecData.add(this.getRowData(i));
		}
		return retBean;
	}

	/**
	 * 添加新值适用多条记录。。 创建日期：(2001-5-29 11:56:22)
	 */
	public void addObject(String sColName, Object cb) {
		if (sColName == null || sColName.trim().length() == 0)
			return;
		if (vecData == null)
			vecData = new Vector();
		String noputvalueflag = "noputvalue";
		boolean bNoPutValue = true;
		if (getColIndex(sColName) != -1) { //已经存在一条记录

			for (int i = 0; i < vecData.size(); i++) {
				Object obj = ((Vector) vecData.elementAt(i))
						.elementAt(getColIndex(sColName));
				if (obj != null && obj.equals(noputvalueflag)) {
					((Vector) vecData.elementAt(i)).setElementAt(cb,
							getColIndex(sColName));
					bNoPutValue = false;
					break;
				}
			}
			if (bNoPutValue) {
				Vector vecNewRow = new Vector();
				for (int i = 0; i < getColumnNum(); i++) {
					if (i == getColIndex(sColName))
						vecNewRow.addElement(cb);
					else
						vecNewRow.addElement(noputvalueflag);
				}
				vecData.addElement(vecNewRow);
			}
			return;

		} else { //新增

			if (colNames == null)
				colNames = new String[0];
			String newCols[] = new String[colNames.length + 1];
			for (int i = 0; i < colNames.length; i++)
				newCols[i] = colNames[i];
			newCols[colNames.length] = sColName;
			colNames = newCols;
			if (vecData.size() == 0) {
				Vector newRow = new Vector();
				newRow.addElement(cb);
				vecData.addElement(newRow);
			} else {
				((Vector) vecData.elementAt(0)).addElement(cb);
				for (int i = 1; i < vecData.size(); i++)
					((Vector) vecData.elementAt(i)).addElement(noputvalueflag);
			}
			return;

		}
	}

	public void removeColumn(String sColName) {
		if (getColIndex(sColName) == -1)
			return;

		if (hListData != null)
			hListData.remove(sColName.toLowerCase());

		int index = getColIndex(sColName);

		if (vecData != null) {
			for (int i = 0; i < getRowNum(); i++)
				((Vector) vecData.elementAt(i)).remove(index);
		}

		if (vecColumnName != null)
			vecColumnName.remove(index);

		Vector vecColNamesTemp = new Vector();
		for (int i = 0; i < getColumnNum(); i++) {
			if (index == i)
				continue;
			vecColNamesTemp.addElement(colNames[i]);
		}
		colNames = new String[colNames.length - 1];

		vecColNamesTemp.copyInto(colNames);

	}

	/*
	 public void sumColumn() {
	 if (vecColumnParam != null) {
	 for (int i = 0; i < vecColumnParam.size(); i++) {
	 ColumnBean colBean = (ColumnBean) vecColumnParam.get(i);
	 if (colBean.getType() != null
	 && (colBean.getType().equalsIgnoreCase("int") || colBean.getType().equalsIgnoreCase("double"))) {
	 double sumValue = 0.0;
	 for (int j = 0; j < vecData.size(); j++) {
	 double cellValue = 0;
	 try {
	 cellValue = Double.parseDouble(getCellStr(j, i));
	 } catch (Exception ex) {
	 //ignore
	 }
	 sumValue += cellValue;
	 }
	 if (colBean.getType().equalsIgnoreCase("int")) {
	 addValue(getColName(i), String.valueOf(new Double(sumValue).intValue()));
	 } else {
	 addValue(getColName(i), String.valueOf(sumValue));
	 }
	 } else {
	 addValue(getColName(i), "");
	 }
	 }
	 setCellObj(getRowNum() - 1, 0, "合计");
	 }
	 }
	 */
	public void addRow(Vector vecRow) {
		vecData.add(vecRow);
	}

	public void addRow(Vector vecRow, int pos) {
		vecData.add(pos, vecRow);
	}

	public int getRowNum() {
		if (vecData == null)
			return 0;
		return vecData.size();

	}

	public void insertRow(int pos) {
		Vector row = new Vector();
		for (int i = 0; i < getColumnNum(); i++) {
			row.add("");
		}
		vecData.add(pos, row);
	}

	/**
	 * 添加新值适用多条记录。。 创建日期：(2001-5-29 11:56:22)
	 * 
	 * @param sColName
	 *            java.lang.String
	 * @param sValue
	 *            java.lang.String
	 */
	public void addValue(String sColName, String sValue) {
/*		if (sColName == null || sColName.trim().length() == 0)
			return;
		if (vecData == null)
			vecData = new Vector();
		String noputvalueflag = "noputvalue";

		boolean bNoPutValue = true;
		if (getColIndex(sColName) != -1) { //已经存在一条记录

			for (int i = 0; i < vecData.size(); i++) {

				Object obj = ((Vector) vecData.elementAt(i))
						.elementAt(getColIndex(sColName));
				if (obj != null && obj.equals(noputvalueflag)) {
					((Vector) vecData.elementAt(i)).setElementAt(sValue,
							getColIndex(sColName));
					bNoPutValue = false;
					break;
				}
			}
			if (bNoPutValue) {
				Vector vecNewRow = new Vector();
				for (int i = 0; i < getColumnNum(); i++) {
					if (i == getColIndex(sColName))
						vecNewRow.addElement(sValue);
					else
						vecNewRow.addElement(noputvalueflag);
				}
				vecData.addElement(vecNewRow);
			}
			return;

		} else { //新增

			if (colNames == null)
				colNames = new String[0];
			String newCols[] = new String[colNames.length + 1];
			for (int i = 0; i < colNames.length; i++)
				newCols[i] = colNames[i];
			newCols[colNames.length] = sColName;
			colNames = newCols;
			if (vecData.size() == 0) {
				Vector newRow = new Vector();
				for (int i = 0; i < colNames.length - 1; i++)
					newRow.addElement(noputvalueflag);
				newRow.addElement(sValue);
				vecData.addElement(newRow);
			} else {
				((Vector) vecData.elementAt(0)).addElement(sValue);
				for (int i = 1; i < vecData.size(); i++)
					((Vector) vecData.elementAt(i)).addElement(noputvalueflag);
			}
			return;

		}*/
		addValueObj(sColName, sValue);
	}
	
	/**
	 * 同addValue方法 只不过这里是加入 Object作为value
	 * @param sColName
	 * @param value
	 */
	public void addValueObj(String sColName, Object value) {
		if (sColName == null || sColName.trim().length() == 0)
			return;
		if (vecData == null)
			vecData = new Vector();
		String noputvalueflag = "noputvalue";

		boolean bNoPutValue = true;
		if (getColIndex(sColName) != -1) { //已经存在一条记录

			for (int i = 0; i < vecData.size(); i++) {

				Object obj = ((Vector) vecData.elementAt(i))
						.elementAt(getColIndex(sColName));
				if (obj != null && obj.equals(noputvalueflag)) {
					((Vector) vecData.elementAt(i)).setElementAt(value,
							getColIndex(sColName));
					bNoPutValue = false;
					break;
				}
			}
			if (bNoPutValue) {
				Vector vecNewRow = new Vector();
				for (int i = 0; i < getColumnNum(); i++) {
					if (i == getColIndex(sColName))
						vecNewRow.addElement(value);
					else
						vecNewRow.addElement(noputvalueflag);
				}
				vecData.addElement(vecNewRow);
			}
			return;

		} else { //新增

			if (colNames == null)
				colNames = new String[0];
			String newCols[] = new String[colNames.length + 1];
			for (int i = 0; i < colNames.length; i++)
				newCols[i] = colNames[i];
			newCols[colNames.length] = sColName;
			colNames = newCols;
			if (vecData.size() == 0) {
				Vector newRow = new Vector();
				for (int i = 0; i < colNames.length - 1; i++)
					newRow.addElement(noputvalueflag);
				newRow.addElement(value);
				vecData.addElement(newRow);
			} else {
				((Vector) vecData.elementAt(0)).addElement(value);
				for (int i = 1; i < vecData.size(); i++)
					((Vector) vecData.elementAt(i)).addElement(noputvalueflag);
			}
			return;

		}
	}

	/**
	 * 因为addvalue方法需要很多检查，处理大量数据效率较低，所有加入此方法 先用这个方法加入一列，以后用setCellObj方法
	 * 
	 * @param colName
	 *            列名
	 * @param defaultValue
	 *            默认值

	 */
	public void addColumn(String colName, String defaultValue) {
		String[] newCol = new String[colNames.length + 1];
		System.arraycopy(colNames, 0, newCol, 0, colNames.length);
		newCol[newCol.length - 1] = colName;
		colNames = newCol;
		for (int i = 0; i < vecData.size(); i++) {
			((Vector) vecData.get(i)).add(defaultValue);
		}
	}

	/**
	 * wangxp 2005-06-26 将指定列用GUID填充
	 * 
	 * @param colName
	 *            列名
	 */
	/*
	    public void addColumnGUID(String colName) {
	        String[] newCol = new String[colNames.length + 1];
	        System.arraycopy(colNames, 0, newCol, 0, colNames.length);
	        newCol[newCol.length - 1] = colName;
	        colNames = newCol;
	        for (int i = 0; i < vecData.size(); i++) {
	            ((Vector) vecData.get(i)).add(Oid.getOid());
	        }
	    }*/

	/**
	 * 添加新值,只适用一条记录。 创建日期：(2001-5-29 11:56:22)
	 * 
	 * @param sColName
	 *            java.lang.String
	 * @param sValue
	 *            java.lang.String
	 */
	public void addValueBack(String sColName, String sValue) {
		if (sColName == null || sColName.trim().length() == 0)
			return;
		if (vecColumnName == null)
			vecColumnName = new Vector();
		if (vecData == null)
			vecData = new Vector();
		if (getColIndex(sColName) != -1)
			((Vector) vecData.elementAt(0)).setElementAt(sValue,
					getColIndex(sColName));
		else {
			if (vecData.size() == 0) {
				Vector vecRow = new Vector();
				vecRow.addElement(sValue);
				vecData.addElement(vecRow);
			} else
				((Vector) vecData.elementAt(0)).addElement(sValue);
			vecColumnName.addElement(sColName);

		}

		colNames = new String[vecColumnName.size()];
		vecColumnName.copyInto(colNames);
	}

	/**
	 * 克隆。 创建日期：(2001-7-31 13:53:36)
	 */
	/* public CommonBean cloneMe() {

	     CommonBean newCb = new CommonBean();
	     if (getColumnParam() != null) {
	         Vector vecRow = new Vector();
	         for (int i = 0; i < getColumnParam().size(); i++)
	             vecRow.addElement(((ColumnBean) getColumnParam().elementAt(i)).cloneMe());

	         newCb.setColumnParam(vecRow);
	     }
	     newCb.setBeanName(getBeanName());

	     String ss[] = new String[getColumnNum()];

	     for (int i = 0; i < getColumnNum(); i++)
	         ss[i] = getColName(i);
	     newCb.setColNames(ss);

	     newCb.setAttribute(getAttribute());

	     if (hListData != null) {
	         java.util.Enumeration enum = hListData.keys();
	         Hashtable hNewListData = new Hashtable();
	         while (enum.hasMoreElements()) {
	             Object obj = enum.nextElement();
	             String s1[][] = (String[][]) hListData.get(obj);
	             String s2[][] = new String[s1.length][2];
	             for (int i = 0; i < s1.length; i++) {
	                 s2[i][0] = s1[i][0];
	                 s2[i][1] = s1[i][1];
	             }
	             hNewListData.put(obj, s2);
	         }

	         newCb.hListData = hNewListData;
	     }
	     Vector newData = new Vector();
	     newCb.setVecData(newData);
	     if (vecData != null) {
	         for (int i = 0; i < vecData.size(); i++) {
	             Vector vecRow = (Vector) vecData.elementAt(i);
	             Vector vecNewRow = new Vector();
	             for (int j = 0; j < vecRow.size(); j++) {
	                 Object obj = vecRow.elementAt(j);
	                 if (obj instanceof CommonBean) {
	                     obj = ((CommonBean) obj).cloneMe();
	                 }
	                 vecNewRow.addElement(obj);
	             }

	             newData.addElement(vecNewRow);
	         }
	     }
	     return newCb;
	 }
	 */

	/**
	 * Bean属性。 创建日期：(2001-7-14 13:39:55)
	 * 
	 * @return java.lang.String
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * 取得报表名字。 创建日期：(2001-5-30 15:37:53)
	 * 
	 * @return java.lang.String
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * 取得单元数据。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.Object
	 * @param iRow
	 *            int
	 * @param iCol
	 *            int
	 */
	public Object getCellObj(int iRow, int iCol) {
		if (vecData == null || iCol == -1 || iRow == -1
				|| iRow > getRowNum() - 1 || iCol > getColumnNum() - 1)
			return null;
		Vector vecRow = ((Vector) vecData.elementAt(iRow));

		if (vecRow != null && vecRow.size() > iCol)
			return vecRow.elementAt(iCol);
		else
			return null;
	}

	/**
	 * 取得单元数据。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.Object
	 * @param iRow
	 *            int
	 * @param strColName
	 *            String
	 */
	public Object getCellObj(int iRow, String strColName) {
		Object obj = getCellObj(iRow, getColIndex(strColName));

		return obj;
	}

	/**
	 * 取得单元数据。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.Object
	 * @param iRow
	 *            int
	 * @param iCol
	 *            int
	 */
	public String getCellStr(int iRow, int iCol) {
		if (getCellObj(iRow, iCol) == null)
			return null;
		return getCellObj(iRow, iCol).toString();
	}

	/**
	 * 取得单元数据。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.Object
	 * @param iRow
	 *            int
	 * @param strColName
	 *            String
	 */
	public String getCellStr(int iRow, String strColName) {
		Object obj = getCellObj(iRow, getColIndex(strColName));
		if (obj == null)
			return null;
		return obj.toString();
	}
	
	
	/**
	 * 取得单元数据  wqf  创建日期：(2009-11-16 10:00:00)
	 * @param iRow
	 * @param strColName
	 * @return
	 */
	public String getCellStrForce(int iRow, String strColName) {
		Object obj = getCellObj(iRow, getColIndex(strColName));
		if (obj == null)
			return "";
		return obj.toString().trim();
	}
	
	
	public String getCellStrForce(int iRow, int i) {
		Object obj = getCellObj(iRow, i);
		if (obj == null)
			return "";
		return obj.toString().trim();
	}

	public int getCellInt(int iRow, String strColName) {
		return getCellInt(iRow, getColIndex(strColName));
	}

	public int getCellInt(int iRow, int iCol) {
		if (getCellObj(iRow, iCol) == null)
			return 0;

		Object obj = getCellObj(iRow, iCol);
		if (obj == null)
			return 0;
		if (obj.toString().trim().length() < 1) {
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}

	public double getCellDouble(int iRow, String strColName) {
		return getCellDouble(iRow, getColIndex(strColName));
	}
	
	
	
	/**
	 * wqf
	 * @param iRow
	 * @param strColName
	 * @return
	 */
	public long getCellLong(int iRow, String strColName) {
		return getCellLong(iRow, getColIndex(strColName));
	}

	/**
	 * wqf
	 */
	private long getCellLong(int iRow, int iCol) {
		Object obj = getCellObj(iRow, iCol);
		if (obj == null)
			return 0;
		if (obj.toString().trim().length() < 1) {
			return 0;
		}
		return Long.parseLong(obj.toString());
	}

	/**
	 * 不会返回null
	 * @param iRow
	 * @param iCol
	 * @return
	 */
	public double getCellDouble(int iRow, int iCol) {
		Object obj = getCellObj(iRow, iCol);
		if (obj == null)
			return 0;
		if (obj.toString().trim().length() < 1) {
			return 0;
		}
		return Double.parseDouble(obj.toString());
	}

	/**
	 * 取得字段所在列。 创建日期：(2001-5-21 10:08:18)
	 */
	public int getColIndex(String strColName) {
		if (colNames == null)
			return -1;
		for (int i = 0; i < colNames.length; i++) {
			if (colNames[i] != null && colNames[i].equalsIgnoreCase(strColName))
				return i;
		}
		return -1;
	}

	/**
	 * 取得字段的枚举值。 创建日期：(2001-5-21 10:08:18)
	 */
	public Hashtable getColListValue() {
		return hListData;
	}

	/**
	 * 取得字段的枚举值。 创建日期：(2001-5-21 10:08:18)
	 */
	public String[][] getColListValue(String strColName) {
		if (colNames == null || hListData == null)
			return null;
		strColName = strColName.toLowerCase();
		String sValues[][] = (String[][]) hListData.get(strColName);
		return sValues;
	}

	/**
	 * 取得字段名。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.String
	 * @param iCol
	 *            int
	 */
	public String getColName(int iCol) {
		if (colNames == null || iCol == -1 || iCol > getColumnNum() - 1)
			return null;
		return colNames[iCol];
	}

	/**
	 * 列字数组。 创建日期：(2001-6-8 16:01:11)
	 * 
	 * @return java.util.Vector
	 */
	public String[] getColNames() {
		return colNames;
	}

	/**
	 * 取得列数。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return int
	 */
	public int getColumnNum() {
		if (colNames == null)
			return 0;
		return getColNames().length;
	}

	/**
	 * 取得字段的枚举值。 创建日期：(2001-5-21 10:08:18)
	 */
	public CommonBean getCommonBean(String strBeanName) {
		if (strBeanName == null || hListData == null)
			return null;
		strBeanName = strBeanName.toLowerCase();
		//String sValues[][] = (String[][]) hListData.get(strColName);
		CommonBean cb = (CommonBean) hListData.get(strBeanName);
		return cb;
	}

	/**
	 * 取第一行的数据的第sColName列的值。 创建日期：(2001-5-29 11:56:22)
	 * 
	 * @return java.lang.String
	 * @param sColName
	 *            java.lang.String
	 */
	public String getValue(String sColName) {
		if (sColName == null || sColName.trim().length() == 0)
			return null;
		//临时方案
		//如果value 是'null' 就返回空
		String value = getCellStr(0, sColName);
		if ("null".equals(value)) {
			return "";
		} else {
			return value;
		}
	}

	/**
	 * 取得所有数据。 创建日期：(2001-5-21 10:05:55)
	 * 
	 * @return java.util.Vector
	 */
	public Vector getVecData() {
		return vecData;
	}

	public Vector getRowData(int i) {
		return (Vector) vecData.get(i);
	}

	public Vector removeRowData(String colName, String cellContent) {
		Vector vRet = null;
		for (int i = 0; i < vecData.size(); i++) {
			if (getCellStr(i, colName).equals(cellContent)) {
				vRet = (Vector) vecData.get(i);
				vecData.remove(i);
				break;
			}
		}
		return vRet;
	}

	/**
	 * 此处插入方法描述。 创建日期：(2002-9-19 16:19:23)
	 * 
	 * @param iRow
	 *            int
	 */
	public void removeRowData(int iRow) {
		if (iRow >= 0 && vecData != null && vecData.size() > 0
				&& iRow < vecData.size()) {
			vecData.remove(iRow);
		}
	}

	/**
	 * 改变列名。 创建日期：(2001-8-2 13:24:02)
	 * 
	 * @param strOldName
	 *            java.lang.String
	 * @param strNewName
	 *            java.lang.String
	 */
	public boolean reNameCol(String strOldName, String strNewName) {

		if (strOldName == null || strOldName.trim().length() == 0)
			return false;
		if (getColNames() == null || getColNames().length == 0)
			return false;

		int index = getColIndex(strOldName);
		if (index == -1)
			return false;
		getColNames()[index] = strNewName;

		return true;

	}

	/**
	 * Bean属性。 创建日期：(2001-7-14 13:39:55)
	 * 
	 * @param newAttribute
	 *            java.lang.String
	 */
	public void setAttribute(String newAttribute) {
		attribute = newAttribute;
	}

	/**
	 * 设报表名。 创建日期：(2001-5-30 15:37:53)
	 * 
	 * @param newBeanName
	 *            java.lang.String
	 */
	public void setBeanName(String newBeanName) {
		beanName = newBeanName;
	}

	/**
	 * 取得单元数据。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.Object
	 * @param iRow
	 *            int
	 * @param iCol
	 *            int
	 */
	public boolean setCellObj(int iRow, int iCol, Object obj) {
		if (vecData == null || iCol == -1 || iRow == -1
				|| iRow > getRowNum() - 1 || iCol > getColumnNum() - 1)
			return false;

		((Vector) vecData.elementAt(iRow)).setElementAt(obj, iCol);
		return true;
	}

	/**
	 * 取得单元数据。 创建日期：(2001-5-21 10:08:18)
	 * 
	 * @return java.lang.Object
	 * @param iRow
	 *            int
	 * @param strColName
	 *            String
	 */
	public boolean setCellObj(int iRow, String strColName, Object obj) {
		return setCellObj(iRow, getColIndex(strColName), obj);

	}

	/**
	 * 取得字段的枚举值。 创建日期：(2001-5-21 10:08:18)
	 */
	public void setColListValue(String strColName, String[][] sValues) {
		if (strColName == null)
			return;
		if (sValues == null)
			sValues = new String[0][];
		if (hListData == null)
			hListData = new Hashtable();
		strColName = strColName.toLowerCase();
		hListData.put(strColName, sValues);
		return;
	}

	/**
	 * 取得字段的枚举值。 创建日期：(2001-5-21 10:08:18)
	 */
	public void setColListValue(Hashtable hData) {
		hListData = hData;
	}

	/**
	 * 各列的定义参数。 创建日期：(2001-6-8 16:01:11)
	 */
	public void setColNames(String[] newColNames) {
		colNames = newColNames;
	}

	/**
	 * 取得字段的枚举值。 创建日期：(2001-5-21 10:08:18)
	 */
	public void setCommonBean(String strColName, CommonBean cb) {
		if (cb == null || strColName == null)
			return;
		if (hListData == null)
			hListData = new Hashtable();
		strColName = strColName.toLowerCase();
		hListData.put(strColName, cb);
		return;
	}

	/**
	 * 设置数据。 创建日期：(2001-5-21 10:05:55)
	 * 
	 * @param newVecData
	 *            java.util.Vector
	 */
	public void setVecData(Vector newVecData) {
		vecData = newVecData;
	}

	/*  public Object clone() {

	      CommonBean cbClone = cloneMe();
	      return cbClone;

	  }*/
	//两个bean合并成一个bean
	public CommonBean merge(CommonBean cb) {
		if (cb != null) {
			for (int i = 0; i < cb.getRowNum(); i++) {
				addRow(cb.getRowData(i));
			}
		}
		return this;
	}
	
	public String[] getRowString(int rowIndex){
		String[] ret = new String[getColumnNum()];
		for(int i=0; i<ret.length; i++){
			String val = getCellStr(rowIndex, i);
			ret[i] = val == null ? "" : val;
		}
		
		return ret;
	}
	
	
	public static String getRound(double b,int rount){
		String values =Double.toString(b) ;
		
		return getRound(new BigDecimal(values), rount);
	}
	
	
	public static String getRound(BigDecimal value, int rount) {
/*		//int rount = 2;
		
		BigDecimal b = value;
		
		return (double)((int)(b.doubleValue()*100.0)/100.0)+"";
		BigDecimal one = new BigDecimal("1");
		String r = b.divide(one, rount, BigDecimal.ROUND_HALF_UP).toString();
		
		return r;*/
		return getRoundNum(value, rount).toString();
	}
	
	public static BigDecimal getRoundNum(BigDecimal value, int rount) {
		//int rount = 2;
		
		BigDecimal b = value;
		/*
		return (double)((int)(b.doubleValue()*100.0)/100.0)+"";*/
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, rount, BigDecimal.ROUND_DOWN);
	}
	
	public static BigDecimal getRoundNum(double value, int rount) {
		//int rount = 2;
		String values =Double.toString(value) ;
		return getRoundNum(new BigDecimal(values), rount);
	}
	
	public static void main(String args[]){
	    double a = 0.8834;
	    double b = 3.23232;
	    
	    System.err.println(getRoundNum(a,4));
	    System.err.println(CommonBean.getRound(a, 4));
	    System.err.println(getRoundNum(b,0));
	}
	
}