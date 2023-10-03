package com.hxk.demo.module.system.jdbc;

import com.hxk.demo.module.system.entity.vo.EventVO;
import com.hxk.demo.module.system.jdbc.mapper.MyEventRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.jdbc
 * @className: NotionJDBC
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Component
public class EventJDBC {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<EventVO> getList() {
        String sql = "select event.id, event.event_name, event.message, event.device_name,event.create_date, event.user_name from event ORDER BY id";
        List<EventVO> list = jdbcTemplate.query(sql, new MyEventRowMapper());
        return list;
    }

    public List<EventVO> getListByGroup(String group) {
        String sql = "select event.id, event.event_name, event.message, event.device_name, event.create_date, event.user_name from event where event.event_name = ? ORDER BY id";
        String[] args = new String[]{group};
        int[] types = new int[]{Types.VARCHAR};
        List<EventVO> list = jdbcTemplate.query(sql, args, types, new MyEventRowMapper());
        return list;
    }
}
