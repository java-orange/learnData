package com.hvisions.jxhRoom.repository;/**
 * @author xhjing
 * @create 2021-08-03 9:24
 */

import com.hvisions.jxhRoom.entity.HvConferenceRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Title: ConferenceRecordsRepository</p>
 * <p>Description: 会议记录reporsitory</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Repository
public interface ConferenceRecordsRepository extends JpaRepository<HvConferenceRecords, Integer>, JpaSpecificationExecutor<HvConferenceRecords> {

    // 根据当前房间号，结束时间之前, 开始时间之后
    @Deprecated
    List<HvConferenceRecords> findByBookIdAndEndTimeBeforeOrStartTimeAfter(Integer roomId, LocalDateTime startTime, LocalDateTime endTime);

    // 根据当前房价号，查询所有状态‘不过期’的记录（未开始与正在进行中）
    List<HvConferenceRecords> findByRoomIdAndStateNotAndEndTimeGreaterThanAndStartTimeLessThan(Integer roomId,Integer state, LocalDateTime startTime, LocalDateTime endTime);

    // 甘特图绘制
    List<HvConferenceRecords> findByStartTimeAfterAndEndTimeBefore(LocalDateTime startTime, LocalDateTime endTime);

    // 查询所有状态不是过期的的记录（未开始与正在进行中）
    List<HvConferenceRecords> findByStateNot(Integer state);

}

    