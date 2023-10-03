package com.hxk.demo.module.user.service;
import com.hxk.demo.module.user.entity.HeartBeatRecord;
import com.hxk.demo.module.user.entity.dto.HeartBeatRecordDTO;
import com.hxk.demo.module.user.repository.HeartBeatRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * HeartBeatRecordService
 *
 * @author star
 */
@Service
public class HeartBeatRecordService {

    @Autowired
    private HeartBeatRecordRepository heartBeatRecordRepository;

    public Page<HeartBeatRecordDTO> listByPage(Pageable pageable) {
        Page<HeartBeatRecord> heartBeatRecordPage = heartBeatRecordRepository.findAll(pageable);
        return heartBeatRecordPage.map(heartBeatRecord -> {
            HeartBeatRecordDTO recordDTO = new HeartBeatRecordDTO();
            BeanUtils.copyProperties(heartBeatRecord, recordDTO);
            return recordDTO;
        });
    }
}
