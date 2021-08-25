package com.hvisions.jxhRoom.service.imp;/**
 * @author xhjing
 * @create 2021-08-03 10:15
 */

import com.hvisions.common.utils.DtoMapper;
import com.hvisions.jxhRoom.dto.EmailRecordsDTO;
import com.hvisions.jxhRoom.entity.HvEmailRecords;
import com.hvisions.jxhRoom.repository.EmailRecordsRepository;
import com.hvisions.jxhRoom.service.EmailRecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: EmailRecordsServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Service
@RequiredArgsConstructor
public class EmailRecordsServiceImpl implements EmailRecordsService {

    private final EmailRecordsRepository repository;

    @Override
    public List<EmailRecordsDTO> getByRecordsId(Integer recordsId) {
        List<HvEmailRecords> list = repository.findByRecordsIdOrderBySendTimeDesc(recordsId);
        return DtoMapper.convertList(list,EmailRecordsDTO.class);
    }

}