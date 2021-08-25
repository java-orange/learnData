package com.hvisions.jxhRoom.service.imp;/**
 * @author xhjing
 * @create 2021-08-03 8:58
 */

import com.hvisions.auth.client.UserClient;
import com.hvisions.auth.dto.user.UserDTO;
import com.hvisions.common.utils.DtoMapper;
import com.hvisions.common.vo.ResultVO;
import com.hvisions.jxhRoom.constants.RecordsStateConstants;
import com.hvisions.jxhRoom.dto.ConferenceRecordsDTO;
import com.hvisions.jxhRoom.dto.ConferenceRecordsQueryDTO;
import com.hvisions.jxhRoom.entity.HvConferenceRecords;
import com.hvisions.jxhRoom.entity.HvEmailRecords;
import com.hvisions.jxhRoom.repository.ConferenceRecordsRepository;
import com.hvisions.jxhRoom.repository.EmailRecordsRepository;
import com.hvisions.jxhRoom.service.ConferenceRecordsService;
import com.hvisions.jxhRoom.service.ConferenceRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: ConferenceRecordsServiceImpl</p>
 * <p>Description: 会议记录service实现</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Service
@RequiredArgsConstructor
public class ConferenceRecordsServiceImpl implements ConferenceRecordsService {

    private final ConferenceRecordsRepository recordsRepository;

    private final JavaMailSender javaMailSender;

    private final EmailRecordsRepository emailRecordsRepository;

    private final UserClient client;

    private final ConferenceRoomService roomService;

    @Value("${spring.mail.username}")
    private String sendEmail;


    @Override
    public Page<ConferenceRecordsDTO> getPage(ConferenceRecordsQueryDTO dto) {
        Specification<HvConferenceRecords> specification = new Specification<HvConferenceRecords>() {
            @Override
            public Predicate toPredicate(Root<HvConferenceRecords> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //预测条件集合
                List<Predicate> predicateList = new ArrayList<>();
                if (dto.getBookId() != null) {
                    Path<Integer> bookId = root.get("bookId");
                    predicateList.add(criteriaBuilder.equal(bookId,dto.getBookId()));
                }
                if (dto.getRoomId() != null) {
                    Path<Integer> roomId = root.get("roomId");
                    predicateList.add(criteriaBuilder.equal(roomId,dto.getRoomId()));
                }
                if (dto.getState() != null) {
                    Path<Integer> state = root.get("state");
                    predicateList.add(criteriaBuilder.equal(state,dto.getState()));
                }

                /**
                 *                              时间冲突规则：
                 *
                 *                  a                                   b
                 *
                 *  -------------startTime---------------------------endTime--------------------------
                 *   "s1...e1"                "s2.......e2"                       "s3....e3"
                 *          "s4...............e4"          "s5.....................e5"
                 *    "s6.............................................................e6"
                 *
                 *    s1 s3 可放行，其余均被占用
                 *
                 *    可写 两种sql
                 *
                 *    1. select * from xxx where not ( (endTime < a) or (startTime > b) )
                 *    2. select * from xxx where endTime > a and startTime < b
                 *
                 *    本次采用第二种， 数据库有例子，ok
                 *
                 */

                if (dto.getStartTime() != null) {
                    Path<LocalDateTime> endTime = root.get("endTime");
                    predicateList.add(criteriaBuilder.greaterThan(endTime, dto.getStartTime()));
                }
                if (dto.getEndTime() != null) {
                    Path<LocalDateTime> startTime = root.get("startTime");
                    predicateList.add(criteriaBuilder.lessThan(startTime, dto.getEndTime()));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        Page<HvConferenceRecords> result = recordsRepository.findAll(specification, dto.getRequest());
        return DtoMapper.convertPage(result, ConferenceRecordsDTO.class);
    }

    @Override
    @Transactional
    public int save(ConferenceRecordsDTO records) {
        if (confirmTime(records)) {
            throw new RuntimeException("该会议室在该时间段内已被预定，请重新预定");
        }
        records.setState(RecordsStateConstants.NOT_START);
        HvConferenceRecords saveRecords = recordsRepository.save(DtoMapper.convert(records, HvConferenceRecords.class));
        // 发送邮件
        if (saveRecords.getIsSend()) {
            sendMessage(saveRecords);
        }
        return saveRecords.getId();
    }

    // 发送邮件信息 并存入记录 todo: 比较垃圾，后期优化
    protected void sendMessage(HvConferenceRecords records) {

        String duration = "";
        String hostName = "";
        String participants = "";
        String place = "";
        String theme = "";

        String bookEmail = "";
        String bookName = "";
        List<String> participantsName = new ArrayList<>();
        List<String> participantsEmail = new ArrayList<>();

// ------------------------------------处理数据---------------------------------------------------
        Integer bookId = records.getBookId();
        String[] ids = records.getParticipantIds().replace("，", ",").split(",");
        LocalDateTime endTime = records.getEndTime();
        LocalDateTime startTime = records.getStartTime();
        Integer hostId = records.getHostId();
        Integer roomId = records.getRoomId();
        theme = records.getTheme();

        for (String stringId : ids) {
            Integer id = NumberUtils.parseNumber(stringId, Integer.class);
            ResultVO<UserDTO> participantUser = client.getUser(id);
            if (participantUser.isSuccess()) {
                participantsName.add(participantUser.getData().getUserName());
                participantsEmail.add(participantUser.getData().getEmail());
            }
        }
        ResultVO<UserDTO> hostUser = client.getUser(hostId);
        if (hostUser.isSuccess()) {
            hostName = hostUser.getData().getUserName();
            String hostEmail = hostUser.getData().getEmail();
            participantsEmail.add(hostEmail);
        }
        ResultVO<UserDTO> bookUser = client.getUser(bookId);
        if (bookUser.isSuccess()) {
            bookName = bookUser.getData().getUserName();
            bookEmail = bookUser.getData().getEmail();
            participantsEmail.add(bookEmail);
        }

        // 与会人姓名集合
        participants = participantsName.toString();
        List<String> emailList = participantsEmail.stream().filter(email -> email != null).distinct().collect(Collectors.toList());
        place = roomService.getById(roomId).getRoomName();

        duration = new StringBuilder().append(startTime).append(" - ").append(endTime).toString();
// ------------------------------------处理数据结束---------------------------------------------------
        // 发送邮件

//        JavaMailSenderImpl javaMailSender = emailUtils.createMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendEmail);
        message.setSubject(theme);

        StringBuilder text = new StringBuilder();
        text.append("会议预定人：").append(bookName).append("\r\n")
                .append("会议时间：").append(duration).append("\r\n")
                .append("会议地点：").append(place).append("\r\n")
                .append("会议主题：").append(theme).append("\r\n")
                .append("会议主持人：").append(hostName).append("\r\n")
                .append("与会人信息：").append(participants);

        message.setText(text.toString());
        message.setTo(emailList.toArray(new String[emailList.size()]));
        javaMailSender.send(message);

        HvEmailRecords hvEmailRecords = new HvEmailRecords();
        hvEmailRecords.setRecordsId(records.getId());
        hvEmailRecords.setDuration(duration);
        hvEmailRecords.setHostName(hostName);
        hvEmailRecords.setSendTime(LocalDateTime.now());
        hvEmailRecords.setPlace(place);
        hvEmailRecords.setTheme(theme);
        hvEmailRecords.setParticipants(participants);

        emailRecordsRepository.save(hvEmailRecords);

    }



    // 确认预约时间不冲突。
    private Boolean confirmTime(ConferenceRecordsDTO records) {
        List<HvConferenceRecords> needCompareRecords;
        LocalDateTime now = LocalDateTime.now();
        Integer roomId = records.getRoomId();
        LocalDateTime startTime = records.getStartTime();
        LocalDateTime endTime = records.getEndTime();
        if (now.compareTo(startTime) > 0) {
            throw new RuntimeException("会议开始时间已过，请重新确定会议时间");
        }
        if(endTime.compareTo(startTime) < 0) {
            throw new RuntimeException("会议结束时间不能小于等于会议开始时间");
        }
        needCompareRecords = recordsRepository.findByRoomIdAndStateNotAndEndTimeGreaterThanAndStartTimeLessThan(roomId, RecordsStateConstants.COMPLETED, startTime,endTime);
        // 修改 去除该记录不比较
        if (records.getId() != null) {
            needCompareRecords = needCompareRecords.stream().filter(records1 -> records1.getId() != records.getId()).collect(Collectors.toList());
        }
        return needCompareRecords.size() > 0;
    }

    @Override
    @Transactional
    public int update(ConferenceRecordsDTO records) {
        HvConferenceRecords one = recordsRepository.getOne(records.getId());
        if (one == null) {
            throw new RuntimeException("数据有误，请确认无误后再进行修改");
        }
        // 因状态有定时任务做修改，故需要重新获取。
        Integer state = one.getState();
        records.setState(state);
        if (state == null) {
            throw new RuntimeException("数据有误，请联系管理员");
        } else if (state == RecordsStateConstants.PROGRESSING) {
            throw new RuntimeException("该会议正在进行中，不能修改");
        } else if (state == RecordsStateConstants.COMPLETED) {
            throw new RuntimeException("该会议已经过期，不能修改");
        }
        if (confirmTime(records)) {
            throw new RuntimeException("该会议室在该时间段内已被预定，请重新预定");
        }
        HvConferenceRecords saveRecords = recordsRepository.save(DtoMapper.convert(records, HvConferenceRecords.class));
        // 发送邮件
        if (saveRecords.getIsSend()) {
            sendMessage(saveRecords);
        }
        return saveRecords.getId();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        HvConferenceRecords records = recordsRepository.getOne(id);
        if (records != null) {
            Integer state = records.getState();
            if (state == RecordsStateConstants.PROGRESSING) {
                throw new RuntimeException("该会议正在进行中，不能删除");
            }
            recordsRepository.deleteById(id);
        }else {
            throw new RuntimeException("找不到对应的会议记录");
        }
    }

    // 甘特图
    @Override
    public List<ConferenceRecordsDTO> getGanttChart(LocalDateTime startTime, LocalDateTime endTime) {
        List<HvConferenceRecords> list = recordsRepository.findByStartTimeAfterAndEndTimeBefore(startTime, endTime);
        if (list != null) {
            return DtoMapper.convertList(list,ConferenceRecordsDTO.class);
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void fixState(Integer state) {
        List<HvConferenceRecords> recordsRepositoryByStateNot = recordsRepository.findByStateNot(state);
        if (recordsRepositoryByStateNot != null && recordsRepositoryByStateNot.size() > 0) {
            for (HvConferenceRecords records : recordsRepositoryByStateNot) {
                LocalDateTime now = LocalDateTime.now();
                // 当前时间处于 预定时间-结束时间之间
                if (now.compareTo(records.getStartTime()) > 0 && now.compareTo(records.getEndTime()) < 0) {
                    records.setState(RecordsStateConstants.PROGRESSING);
                    // 当前时间超过结束时间
                } else if (now.compareTo(records.getEndTime()) > 0) {
                    records.setState(RecordsStateConstants.COMPLETED);
                }
                recordsRepository.save(records);
            }
        }
    }
}