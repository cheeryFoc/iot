package com.hxk.demo.module.system.jdbc.mapper;

import com.hxk.demo.module.system.entity.vo.EventVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.jdbc.mapper
 * @className: MyNotionRowMapper
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class MyEventRowMapper implements RowMapper<EventVO> {
    @Override
    public EventVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventVO event = new EventVO();
        event.setId(rs.getLong("id"));
        event.setEventName(rs.getString("event_name"));
        event.setMessage(rs.getString("message"));
        event.setDeviceName(rs.getString("device_name"));
        event.setCreateDate(rs.getDate("create_date").toString());
        event.setUserName(rs.getString("user_name"));
        return event;
    }
}
