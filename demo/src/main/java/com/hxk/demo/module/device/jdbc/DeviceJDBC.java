package com.hxk.demo.module.device.jdbc;

import com.hxk.demo.module.device.entity.Device;
import com.hxk.demo.module.device.entity.vo.DeviceVo;
import com.hxk.demo.module.device.jdbc.mapper.DeviceVoRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.device.jdbc
 * @className: DeviceJDBC
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */

@Component
public class DeviceJDBC {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<DeviceVo> getList() {
        String sql = "select device.id, device.device_name, device.alias, device_group.group_name, device.last_modified_date, device.create_date from device inner join device_group on device.group_id = device_group.id";
        List<DeviceVo> list = jdbcTemplate.query(sql, new DeviceVoRowMapper());
        return list;
    }

    public List<DeviceVo> getDeviceByGroup(String group) {
        String sql = "select device.id, device.device_name, device.alias, device_group.group_name, device.last_modified_date, device.create_date from device inner join device_group on device.group_id = device_group.id where device_group.group_name = ?";
        String[] args = new String[]{group};
        int[] types = new int[]{Types.VARCHAR};
        List<DeviceVo> list = jdbcTemplate.query(sql, args, types,  new DeviceVoRowMapper());
        return list;
    }

}
