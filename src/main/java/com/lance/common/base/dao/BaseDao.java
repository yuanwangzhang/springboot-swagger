package com.lance.common.base.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * baseDao
 * @author lance
 */
@Repository
@Data
public class BaseDao
{
    @Resource
    private HBaseDao hBaseDao;
    @Resource
    private JBaseDao jBaseDao;
}
