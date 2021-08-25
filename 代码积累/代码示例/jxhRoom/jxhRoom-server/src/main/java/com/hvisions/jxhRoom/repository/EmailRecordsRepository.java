package com.hvisions.jxhRoom.repository;/**
 * @author xhjing
 * @create 2021-08-03 10:15
 */

import com.hvisions.jxhRoom.entity.HvEmailRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title: EmailRecordsRepository</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Repository
public interface EmailRecordsRepository extends JpaRepository<HvEmailRecords,Integer>, JpaSpecificationExecutor<HvEmailRecords> {

    /**
     * 根据recordId 返回对应的邮件列表
     * @param recordsId
     * @return
     */
    List<HvEmailRecords> findByRecordsIdOrderBySendTimeDesc(Integer recordsId);
}

    