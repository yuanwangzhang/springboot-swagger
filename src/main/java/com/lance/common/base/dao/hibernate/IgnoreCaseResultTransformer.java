package com.lance.common.base.dao.hibernate;


import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

import java.lang.reflect.Field;
import java.util.List;


/**
 * 修正hibernate返回自定义pojo类型时找不到属性的BUG
 * <p>
 * 主要发生在使用oracle时，查询返回的字段默认是大写的(除非SQL中指定了别名)，这导致返回自定义pojo类型时会报找不到属性的错误，该类用于修正此BUG。
 * <p>
 * 使用该类时SQL返回的字段名大小写或者带"_"都会被忽略，如数据库字段为 USER_NAME，自定义pojo的属性名为username就可以使用
 */

public class IgnoreCaseResultTransformer implements ResultTransformer
{
    private final Class<?> resultClass;
    private Field[] fields;
    private BeanUtilsBean beanUtilsBean;

    public IgnoreCaseResultTransformer(final Class<?> resultClass)
    {
        this.resultClass = resultClass;
        this.fields = this.resultClass.getDeclaredFields();
        beanUtilsBean = BeanUtilsBean.getInstance();
    }


    /**
     * aliases为每条记录的数据库字段名,ORACLE字段名默认为大写
     * <p>
     * tupe为与aliases对应的字段的值
     */

    @Override
    public Object transformTuple(final Object[] tuple, final String[] aliases)
    {
        Object result;
        try
        {
            result = this.resultClass.newInstance();
            for (int i = 0; i < aliases.length; i++)
            {
                for (Field field : this.fields)
                {
                    String fieldName = field.getName();
                    //数据库字段带下划线的时候也能保证使用，如数据库字段为 USER_NAME，自定义pojo的属性名为username就可以使用
                    if (fieldName.equalsIgnoreCase(aliases[i].replaceAll("_", "")))
                    {
                        beanUtilsBean.setProperty(result, fieldName, tuple[i]);
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName(), e);
        }
        return result;
    }

    @Override
    public List transformList(final List collection)
    {
        return collection;
    }
}
