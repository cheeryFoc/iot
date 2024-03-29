package com.hxk.demo.module.user.repository;

import com.hxk.demo.module.user.entity.HeartBeatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * HeartBeatRecordRepository
 *
 * @author star
 */
public interface HeartBeatRecordRepository extends JpaRepository<HeartBeatRecord, Long> {

    Optional<HeartBeatRecord> findByProjectPathAndServerIpAndProcessNum(@Param("projectPath") String projectPath,
                                                                        @Param("serverIp") String serverIp,
                                                                        @Param("processNum") Integer processNum);

}
