package com.lance.common.base.dao;

import com.lance.common.base.dao.hibernate.Finder;
import com.lance.common.base.dao.hibernate.IgnoreCaseResultTransformer;
import com.lance.common.base.dao.hibernate.OrderBy;
import com.lance.common.base.page.Pagination;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hibernate baseDao
 *
 * @author lance
 */
@Repository
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unchecked")
public class HBaseDao<T> extends HibernateDaoSupport
{
    /**
     * 注入sessionFactory
     *
     * @param sessionFactory
     */
    @Resource
    public void setSessionFactoryOverride(SessionFactory sessionFactory)
    {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    public T save(T obj)
    {
        Assert.notNull(obj, "Object is empty");
        this.getHibernateTemplate().save(obj);
        return obj;
    }

    /**
     * 删除对象
     *
     * @param obj
     */
    public void delete(T obj)
    {
        Assert.notNull(obj, "Object is empty");
        this.getHibernateTemplate().delete(obj);
    }

    /**
     * 根据id删除
     *
     * @param cls
     * @param id
     * @return
     */
    public T deleteById(Class<T> cls, Serializable id)
    {
        Assert.notNull(id, "Primary key is empty");
        T entity = this.getHibernateTemplate().load(cls, id);
        this.getHibernateTemplate().delete(entity);
        return entity;
    }

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    public T update(T obj)
    {
        Assert.notNull(obj, "Object is empty");
        this.getHibernateTemplate().update(obj);
        return obj;
    }

    /**
     * 保存或更新
     *
     * @param obj
     * @return
     */
    public T saveOrUpdate(T obj)
    {
        Assert.notNull(obj, "Object is empty");
        this.getHibernateTemplate().saveOrUpdate(obj);
        return obj;
    }

    /**
     * 合并对象
     *
     * @param obj
     * @return
     */
    public T merge(T obj)
    {
        Assert.notNull(obj, "Object is empty");
        return (T) this.getHibernateTemplate().merge(obj);
    }

    /**
     * 根据主键获取值
     *
     * @param cls
     * @param id
     * @return
     */
    public T get(Class<T> cls, Serializable id)
    {
        Assert.notNull(id, "Primary key is empty");
        T entity = this.getHibernateTemplate().get(cls, id);
        return entity;
    }

    /**
     * 查询
     * 是否利用数据库的 for update 子句加锁
     *
     * @param cls
     * @param id
     * @param lock
     * @return
     */
    public T get(Class<T> cls, Serializable id, boolean lock)
    {
        Assert.notNull(id, "Primary key is empty");
        T entity = null;
        if (lock)
        {
            entity = this.getHibernateTemplate().get(cls, id, LockMode.UPGRADE);
        }
        else
        {
            entity = this.getHibernateTemplate().get(cls, id);
        }
        return entity;
    }

    /**
     * 加载对象
     *
     * @param cls
     * @param id
     * @return
     */
    public T load(Class<T> cls, Serializable id)
    {
        Assert.notNull(id, "Primary key is empty");
        return this.getHibernateTemplate().load(cls, id);
    }

    /**
     * 加载对象
     *
     * @param c
     * @param id
     * @param lock
     * @return
     */
    public T load(Class<T> c, Serializable id, boolean lock)
    {
        Assert.notNull(id, "Primary key is empty");
        T entity = null;
        if (lock)
        {
            entity = (T) this.getHibernateTemplate().load(c, id, LockMode.UPGRADE);
        }
        else
        {
            entity = (T) this.getHibernateTemplate().load(c, id);
        }
        return entity;
    }

    /**
     * 重载对象
     *
     * @param obj
     */
    public void refresh(T obj)
    {
        Assert.notNull(obj, "Object is empty");
        this.getHibernateTemplate().refresh(obj);
    }

    /**
     * 通过Finder获得分页数据
     *
     * @param finder   Finder对象
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return
     */
    public Pagination find(Finder finder, int pageNo, int pageSize)
    {
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1)
        {
            p.setList(new ArrayList());
            return p;
        }
        List list = this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(finder.getOrigHql());
            finder.setParamsToQuery(query);
            query.setFirstResult(p.getFirstResult());
            query.setMaxResults(p.getPageSize());
            if (finder.isCacheable())
            {
                query.setCacheable(true);
            }
            return query.list();
        });
        p.setList(list);
        return p;
    }

    /**
     * 通过Finder获得列表数据
     *
     * @param finder
     * @return
     */
    public List find(Finder finder)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = finder.createQuery(session);
            return query.list();
        });
    }

    /**
     * 排序查询
     *
     * @param c
     * @param orders
     * @return
     */
    public List findAllWithOrder(Class<T> c, OrderBy... orders)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(c);
        if (orders != null)
        {
            for (OrderBy order : orders)
            {
                criteria.addOrder(order.getOrder());
            }
        }
        return this.getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * 分页排序查询
     *
     * @param c
     * @param pageNo
     * @param pageSize
     * @param orders
     * @return
     */
    public List findAllWithOrder(Class<T> c, int pageNo, int pageSize, OrderBy... orders)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(c);
        if (orders != null)
        {
            for (Order order : OrderBy.asOrders(orders))
            {
                criteria.addOrder(order);
            }
        }
        return this.getHibernateTemplate().findByCriteria(criteria, pageNo, pageSize);
    }

    /**
     * 辅助函数
     * 获得Finder的记录总数
     *
     * @param finder
     * @return
     */
    private int countQueryResult(Finder finder)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(finder.getRowCountHql());
            finder.setParamsToQuery(query);
            if (finder.isCacheable())
            {
                query.setCacheable(true);
            }
            return ((Number) query.iterate().next()).intValue();
        });
    }

    // hql

    /**
     * 执行hql(非预编译)
     *
     * @param hql
     * @return
     */
    public int executeHql(String hql)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            return query.executeUpdate();
        });
    }

    /**
     * 执行hql(预编译)
     *
     * @param hql
     * @return
     */
    public int executeHql(String hql, Map<String, Object> params)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params))
            {
                for (String key : params.keySet())
                {
                    query.setParameter(key, params.get(key));
                }
            }
            return query.executeUpdate();
        });
    }

    /**
     * 查询对象列表(非预编译)
     *
     * @param hql
     * @return
     */
    public List<T> find(String hql)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            return query.list();
        });
    }

    /**
     * 查询对象列表(预编译)
     *
     * @param hql
     * @param params
     * @return
     */
    public List<T> find(String hql, Map<String, Object> params)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params))
            {
                for (String key : params.keySet())
                {
                    query.setParameter(key, params.get(key));
                }
            }
            return query.list();
        });
    }

    /**
     * 分页查询对象列表(非预编译)
     *
     * @param hql
     * @param page
     * @param rows
     * @return
     */
    public List<T> find(String hql, int page, int rows)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
        });
    }

    /**
     * 分页查询对象列表(预编译)
     *
     * @param hql
     * @param params
     * @param page
     * @param rows
     * @return
     */
    public List<T> find(String hql, Map<String, Object> params, int page, int rows)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params))
            {
                for (String key : params.keySet())
                {
                    query.setParameter(key, params.get(key));
                }
            }
            return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
        });
    }

    /**
     * 获取行数(非预编译)
     *
     * @param hql
     * @return
     */
    public Long count(String hql)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            return (Long) query.uniqueResult();
        });
    }

    /**
     * 获取行数(预编译)
     *
     * @param hql
     * @return
     */
    public Long count(String hql, Map<String, Object> params)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params))
            {
                for (String key : params.keySet())
                {
                    query.setParameter(key, params.get(key));
                }
            }
            return (Long) query.uniqueResult();
        });
    }

    /**
     * 通过HQL查询对象列表
     *
     * @param hql
     * @param values
     * @return
     */
    public List find(String hql, Object... values)
    {
        return createQuery(hql, values).list();
    }

    /**
     * 通过HQL查询唯一对象
     *
     * @param hql
     * @param values
     * @return
     */
    public Object findUnique(String hql, Object... values)
    {
        return createQuery(hql, values).uniqueResult();
    }

    /**
     * 辅助函数
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理
     *
     * @param queryString
     * @param values
     * @return
     */
    private Query createQuery(String queryString, Object... values)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Query query = session.createQuery(queryString);
            if (values != null)
            {
                for (int i = 0; i < values.length; i++)
                {
                    query.setParameter(i, values[i]);
                }
            }
            return query;
        });
    }

    /**
     * 辅助函数
     * 根据Criterion条件创建Criteria,后续可进行更多处理
     *
     * @param clazz
     * @param criterions
     * @return
     */
    public Criteria createCriteria(Class<T> clazz, Criterion... criterions)
    {
        return this.getHibernateTemplate().execute(session ->
        {
            Criteria criteria = session.createCriteria(clazz);
            for (Criterion c : criterions)
            {
                criteria.add(c);
            }
            return criteria;
        });
    }

    //Nativity-Sql...

    /**
     * 根据sql查询出自定义对象列表(非预编译)
     *
     * @param sql
     * @param cls
     * @return
     * @throws Exception
     */
    public List<T> queryForListBySql(String sql, Class<T> cls) throws Exception
    {
        return this.getHibernateTemplate().execute(session ->
        {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            List<T> result = sqlQuery.setResultTransformer(new IgnoreCaseResultTransformer(cls)).list();
            if (CollectionUtils.isEmpty(result))
            {
                return null;
            }
            else
            {
                return result;
            }
        });
    }

    /**
     * 根据sql查询出自定义对象列表(非预编译)
     *
     * @param sql
     * @param cls
     * @return
     * @throws Exception
     */
    public List<T> queryForListBySql(String sql, Object[] args, Class cls) throws Exception
    {
        return this.getHibernateTemplate().execute(session ->
        {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            if (ArrayUtils.isEmpty(args))
            {
                for (int i = 0; i < args.length; i++)
                {
                    sqlQuery.setParameter(i, args[i]);
                }
            }
            List<T> result = sqlQuery.setResultTransformer(new IgnoreCaseResultTransformer(cls)).list();
            if (CollectionUtils.isEmpty(result))
            {
                return null;
            }
            else
            {
                return result;
            }
        });
    }
}
