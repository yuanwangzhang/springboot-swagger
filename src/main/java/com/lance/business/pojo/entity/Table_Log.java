package com.lance.business.pojo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 日志表
 *
 * @author lance
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_LOG", indexes = {@Index(name = "TB_LOG_IDX", columnList = "logId")})
//@Entity
public class Table_Log
{
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String uuid;

    /**
     * 日志id
     */
    @Column(length = 32)
    private String logId;

    /**
     * 日志信息
     */
    @Column(length = 500)
    private String logMsg;

    /**
     * 日志时间
     */
    @Column(length = 20)
    private String logStamp;

}
