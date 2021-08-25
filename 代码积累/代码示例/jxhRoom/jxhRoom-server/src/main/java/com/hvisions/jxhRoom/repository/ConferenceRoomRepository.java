package com.hvisions.jxhRoom.repository;/**
 * @author xhjing
 * @create 2021-08-03 10:08
 */

import com.hvisions.jxhRoom.entity.HvConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Title: ConferenceRoomRepository</p>
 * <p>Description: 会议室</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Repository
public interface ConferenceRoomRepository extends JpaRepository<HvConferenceRoom,Integer>, JpaSpecificationExecutor<HvConferenceRoom> {

}

    