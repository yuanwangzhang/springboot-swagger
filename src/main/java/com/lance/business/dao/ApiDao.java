package com.lance.business.dao;

import com.lance.business.pojo.model.SampleModel;
import com.lance.common.base.dao.BaseDao;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;

/**
 * <p>业务处理dao层
 *
 * @author lance
 * @since 2018-09-25
 **/
@Repository
@SuppressWarnings("unchecked")
public class ApiDao extends BaseDao
{
    /**
     * 根据标识取得信息
     *
     * @param key
     * @return
     */
    public SampleModel getTestInfoByKey(String key)
    {
        StringBuffer sql = new StringBuffer("\n");
        sql.append("SELECT \n");
        sql.append("        ?       AS key, \n");
        if (key.indexOf("1") > 0)
        {
            sql.append("    'A类'   AS kind, \n");
        }
        else
        {
            sql.append("    'B类'   AS kind, \n");
        }
        sql.append("        '是'    AS allowsearch \n");
        sql.append("FROM \n");
        sql.append("    dual \n");
        return getJBaseDao().queryForModel(sql.toString(), new String[]{key}, SampleModel.class);
    }

    /**
     * 调程序包测试
     *
     * @param key
     * @throws Exception
     */
    public void callPackAgeTest(String key) throws Exception
    {
        getJBaseDao().getJdbcTemplate().execute((CallableStatementCreator) connection ->
        {
            String storedProc = "{call ?:=pkg.sample(?)}";
            CallableStatement cs = connection.prepareCall(storedProc);
            // 注册输出参数的类型
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2, key);
            return cs;
        }, callableStatement ->
        {
            callableStatement.execute();
            // 获取游标一行的值
            ResultSet rs = (ResultSet) callableStatement.getObject(1);
            rs.next();
            String returnVal = rs.getString(1);
            rs.close();
            // 获取输出参数的值
            return returnVal;
        });
    }
}
