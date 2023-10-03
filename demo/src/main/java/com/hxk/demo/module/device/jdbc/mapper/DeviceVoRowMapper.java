package com.hxk.demo.module.device.jdbc.mapper;

import com.hxk.demo.module.device.entity.vo.DeviceVo;
import com.hxk.demo.utils.date.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.device.jdbc.mapper
 * @className: MyDeviceRowMapper
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class DeviceVoRowMapper implements RowMapper<DeviceVo> {

    @Override
    public DeviceVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(1);
        String deviceName = rs.getString(2);
        String alias = rs.getString(3);
        String groupName = rs.getString(4);
        String lastModifiedDate = DateUtils.parseDateToStr(DateUtils.getDate(), rs.getDate(5));
        String createDate = DateUtils.parseDateToStr(DateUtils.getDate(), rs.getDate(6));

        DeviceVo vo = new DeviceVo();
        vo.setId(id);
        vo.setDeviceName(deviceName);
        vo.setAlias(alias);
        vo.setGroupName(groupName);
        vo.setCreateDate(createDate);
        vo.setLastModifiedDate(lastModifiedDate);
        vo.setStatus("offline");
        return vo;
    }

}
