package com.hvisions.jxhRoom.scheduler;
/**
 * @author xhjing
 * @create 2021-08-05 10:33
 */

import com.hvisions.jxhRoom.constants.RecordsStateConstants;
import com.hvisions.jxhRoom.service.ConferenceRecordsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <p>Title: SchedulerTask</p>
 * <p>Description: 任务调度器</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/5</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerTask {

    private final ConferenceRecordsService recordsService;

    @Scheduled(cron="0 */1 * * * ?")   // 每分钟执行一次更新
    private void process(){
        log.info("当前时间：{}, 审查会议记录状态并做修改....",LocalDateTime.now());
        recordsService.fixState(RecordsStateConstants.COMPLETED);
    }

}