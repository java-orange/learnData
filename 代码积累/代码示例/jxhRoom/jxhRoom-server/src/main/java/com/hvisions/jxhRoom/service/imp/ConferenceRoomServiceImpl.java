package com.hvisions.jxhRoom.service.imp;
/**
 * @author xhjing
 * @create 2021-08-03 10:06
 */

import com.hvisions.common.utils.DtoMapper;
import com.hvisions.jxhRoom.dto.ConferenceRoomDTO;
import com.hvisions.jxhRoom.repository.ConferenceRoomRepository;
import com.hvisions.jxhRoom.service.ConferenceRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: ConferenceRoomServiceImpl</p>
 * <p>Description: 会议室serviceImpl</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/3</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Service
@RequiredArgsConstructor
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final ConferenceRoomRepository repository;

    @Override
    public List<ConferenceRoomDTO> getAll() {
        return DtoMapper.convertList(repository.findAll(), ConferenceRoomDTO.class);
    }

    @Override
    public ConferenceRoomDTO getById(Integer id) {
        return DtoMapper.convert(repository.getOne(id),ConferenceRoomDTO.class);
    }
}