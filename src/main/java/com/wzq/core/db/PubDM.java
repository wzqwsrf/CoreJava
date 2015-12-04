package com.wzq.core.db;


import com.wzq.core.tools.CommonBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * 公共操作类名。 创建日期：(2001-5-25 18:04:45)
 * 
 * @author：韦建儒
 */
public final class PubDM extends DataBaseObject {
    /**
     * PubDM 构造子注解。

     */
    public PubDM() {   
        super();
    }

    /**
     * PubDM 构造子注解。

     */
    public PubDM(java.sql.Connection con) {
        super();
        this.con = con;
    }

    protected boolean execute(CommonBean cb[]) throws SQLException {
        return execute(cb, null);
    }
  
    
    public boolean execute(CommonBean cb) throws SQLException {
    	return execute(new CommonBean[]{cb});
    }
    
    public boolean execute(CommonBean cb[], CommonBean cbFlt[]) throws SQLException {
    	return execute(cb, cbFlt, false);
    }
    
    private List getCbParams(CommonBean cb){
    	List ret = new ArrayList();
    	int cn = cb.getColumnNum();
    	for(int i=0, si=cb.getRowNum(); i<si; i++){
    		Object[] rowParam = new Object[cn];
    		for(int j=0; j<cn; j++)
    			rowParam[j] = cb.getCellObj(i, j);
    		
    		ret.add(rowParam);
    	}
    	
    	return ret;
    }
    
    public boolean executePstmt(CommonBean cb[]) throws SQLException{
    	return executePstmt(cb, null);
    }
    
    public boolean executePstmt(CommonBean cb[], CommonBean[] flt) throws SQLException{
    	return execute(cb, flt, true);
    }

    public boolean executePstmt(CommonBean cb) throws SQLException{
    	return executePstmt(new CommonBean[]{cb});
    }
    
    /**
     * 执行。

     * 
     * @param eo
     *            EntityObject
     * @exception java.sql.SQLException
     *                数据库操作失败时，抛出此异常。

     */
    public boolean execute(CommonBean cb[], CommonBean cbFlt[], boolean pstmt) throws SQLException {

        if (cb == null) {
            throw new SQLException("cb[] is null!");
        }
        
        if(cb[0].getRowNum()==0){
        	return true;
        }

        String strSql[] = null;
        Vector vecSql = new Vector();
        Vector params = new Vector();
        
        for (int i = 0; i < cb.length; i++) {
            Vector vecSqlTemp = getSql(cb[i], cbFlt != null ? cbFlt[i] : null, pstmt);
            if (vecSqlTemp == null) {
                throw new SQLException("没填操作符（insert,del,update)或数据库表名或字段错了!");
            }
            for (int j = 0; j < vecSqlTemp.size(); j++) {
                vecSql.addElement(vecSqlTemp.elementAt(j));
            }
            // 对于每个cb 取参数
            if(pstmt){
            	params.addAll(getCbParams(cb[i]));
            }
        }

        strSql = new String[vecSql.size()];
        vecSql.copyInto(strSql);
        
        Object[][] objP = new Object[params.size()][];
        params.copyInto(objP);

        if(!pstmt)
        	return execute(strSql);
        else
        	return execute(strSql, objP);
    }

    /**
     * 执行。

     * 
     * @param eo
     *            EntityObject
     * @exception java.sql.SQLException
     *                数据库操作失败时，抛出此异常。

     */
    public boolean execute(String strSql) throws SQLException {

        return execute(new String[] { strSql });

    }
    
    public boolean execute(String strSql[]) throws SQLException {
        return executeUpdate(strSql);
    }
    
    public boolean execute(String strSql[], Object[][] params) throws SQLException {
    	return executeUpdateParam(strSql, params);
    }
    
    

    /**
     * 取得组装SQL。 创建日期：(2001-3-27 18:18:58)
     * 
     * @return java.lang.String
     */
    private Vector getAddSql(CommonBean cb, CommonBean cbFlt, boolean pstmt) throws SQLException {

        String strValue = "";
        String strSql = "";

        Vector vecSql = new Vector();
        CommonBean cbDatatype = getDataType(cb);
        if (cbDatatype == null) {
            throw new SQLException("getDataType(cb) is null");
        }

        for (int i = 0; i < cb.getRowNum(); i++) {
            strValue = "";
            strSql = " insert into " + cb.getBeanName() + "(";
            for (int j = 0; j < cb.getColNames().length; j++) {
                strSql += cb.getColNames()[j];

                String tempValue = null;
                tempValue = cb.getCellStr(i, j);
                if (tempValue == null || tempValue.trim().length() == 0) {
                    strValue += "null";
                }else if(pstmt){
                	strValue += "?";
                }else if (cbDatatype.getValue(cb.getColNames()[j]) != null
                        && cbDatatype.getValue(cb.getColNames()[j]).equals("1")) {
                    strValue += tempValue;
                } else {
                	    tempValue =tempValue.replaceAll("'", "''");
                        strValue += "'" + tempValue + "'";       
                }
                if (j == cb.getColNames().length - 1) {
                    strSql += ")values(";
                    strValue += ")";
                } else {
                    strSql += ",";
                    strValue += ",";
                }
            }
            vecSql.addElement(strSql + strValue);

        }

        return vecSql;

    }


    
    /**
     * 取得数据类型。 创建日期：(2001-5-25 17:17:41)
     * 
     * @return mycrm.pub.errmsg.ErrorMessage
     */
    private CommonBean getDataType(CommonBean cb) throws SQLException {

        return new PubQO(getConnection()).getDataType(cb);

    }

   
    /**
     * 此处插入方法说明。 创建日期：(2001-3-27 18:18:58)
     * 
     * @return java.lang.String
     */
    public String getFinalSql() {
        return null;
    }

    /**
     * 取得组装SQL。 创建日期：(2001-3-27 18:18:58)
     * 
     * @return java.lang.String
     */
    private Vector getSql(CommonBean cb, CommonBean cbFlt, boolean pstmt) throws SQLException {

        if (cb == null) {
            throw new SQLException("cb(operate data) is null");
        } else if (cb.getAttribute() == null) {
            throw new SQLException("cb.getAttribute()(operate data) is null");
        } else if (cb.getAttribute().equals("insert")) {
            return getAddSql(cb, cbFlt, pstmt);
        } else if (cb.getAttribute().equals("update")) {
            return getUpdateSql(cb, cbFlt, pstmt);
        } else {
            throw new SQLException("cb.getAttribute() is not not in(insert)");
        }

    }
    
    
    
    /**
     * 取得组装SQL。 创建日期：(2001-3-27 18:18:58)
     * 
     * @return java.lang.String
     */
    private Vector getUpdateSql(CommonBean cb, CommonBean cbFlt, boolean pstmt) throws SQLException {

        String strValue = "";
        String strSql = "";

        Vector vecSql = new Vector();
     
        CommonBean cbFltType = null;
        CommonBean cbDatatype = getDataType(cb);
        String subSql[] = null;
        String whereSql = " Where (1>0) ";
        if (cbFlt != null) {
            subSql = new String[cbFlt.getRowNum()];
            if (cbFlt.getBeanName() == null) {
                cbFlt.setBeanName(cb.getBeanName());
            }
          
            
            
            for (int m = 0; m < cbFlt.getRowNum(); m++) {
                subSql[m] = "";
                for (int n = 0; n < cbFlt.getColumnNum(); n++) {
                    strValue = cbFlt.getCellStr(m, cbFlt.getColName(n));
                    if (strValue == null) {
                    	whereSql += " and " + cbFlt.getColName(n) + " is null ";
                    } else {                   
                        strValue = "'" + strValue + "'";
                        whereSql += " and " + cbFlt.getColName(n) + "=" + strValue;
                    }
                }

            }
        }

        strValue= "";
        for (int i = 0; i < cb.getRowNum(); i++) {
            strSql = " update  " + cb.getBeanName() + " set  ";
            for (int j = 0; j < cb.getColumnNum(); j++) {
            	 strValue= "";
                if (cb.getColNames()[j].toLowerCase().equals("id")) {
                    continue;
                }
                String tempValue = null;
                tempValue = cb.getCellStr(i, j);
                
                if (tempValue == null || tempValue.trim().length() == 0) {
                    strValue = "null";
                }else if(pstmt){
                	// 如果是为prepareStatement准备的
                	strValue = "?";
                }else if (cbDatatype.getValue(cb.getColNames()[j]) != null
                        && cbDatatype.getValue(cb.getColNames()[j]).equals("1")) {
                    strValue += tempValue;
                } else {
                	tempValue =tempValue.replaceAll("'", "''");
                    strValue += "'" + tempValue + "'";
                }
                              
                strSql += cb.getColNames()[j] + " = " + strValue + ",";
            }
            
            strSql = strSql.substring(0, strSql.length()-1);
            strSql = strSql+ whereSql;
            vecSql.add(strSql);
            
        }
        return vecSql;

    }


    


    //此表是否被引用


    private CommonBean getUseThisTableObjName(CommonBean cb) throws SQLException {

        String strSql = " select count(*) as NUM from " + cb.getBeanName() + " where " + cb.getValue("sField") + "='"
                + cb.getValue("ID") + "'";

        if (cb.getValue("sWhere") != null && !cb.getValue("sWhere").trim().equals("")) {
            strSql += " and " + cb.getValue("sWhere");

        }
        CommonBean cbRet = new PubQO(getConnection()).getData(strSql);

        CommonBean cbData = new CommonBean();

        if (cbRet.getValue("NUM").compareTo("0") > 0) {

            cbData.addValue("Objs", cbRet.getValue("NUM"));
        }

        return cbData;
    }


}
