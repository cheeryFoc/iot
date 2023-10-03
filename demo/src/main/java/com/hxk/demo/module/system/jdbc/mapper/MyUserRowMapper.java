package com.hxk.demo.module.system.jdbc.mapper;

import com.hxk.demo.module.user.entity.vo.UserVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyUserRowMapper implements RowMapper<UserVO> {

    @Override
    public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        // 获取结果集中的数据
        // 把数据封装成User对象
        UserVO user = new UserVO();
        user.setId(rs.getLong("id"));
        user.setUserName(rs.getString("user_name"));

        List<String> tmp =  Arrays.asList(rs.getString("role").split(","));
        List<String> roles = new ArrayList<String>(tmp);
        if (roles == null || roles.size() < 1 || "".equals(roles.get(0))) {
            roles.add("");
        }
        user.setRoles(roles);

        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setCreateDate(rs.getDate("create_date").toString());
        user.setNickName(rs.getString("nick_name"));
        return user;
    }

}
