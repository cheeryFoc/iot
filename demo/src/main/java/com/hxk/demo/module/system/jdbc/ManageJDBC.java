package com.hxk.demo.module.system.jdbc;

import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.exception.common.CodeEnum;
import com.hxk.demo.exception.common.CommonException;
import com.hxk.demo.module.user.entity.vo.UserVO;
import com.hxk.demo.module.system.jdbc.mapper.MyUserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;

@Component
public class ManageJDBC {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<UserVO> getList() {
        String sql = "select user.id, user.user_name, user_role.role, user.email, user.phone, user.create_date, user.nick_name  from user left join user_role on user.user_name = user_role.user_name where user.user_name not in('root','xixixi') ORDER BY id";
        List<UserVO> list = jdbcTemplate.query(sql, new MyUserRowMapper());
        return list;
    }

    public List<UserVO> getInfoByName(String userName) {
        String sql = "select user.id, user.user_name, user_role.role, user.email, user.phone, user.create_date, user.nick_name  from user left join user_role on user.user_name = user_role.user_name where user.user_name like ? and user.user_name not in('root','xixixi') ORDER BY id";
        String[] args = new String[]{userName};
        int[] types = new int[]{Types.VARCHAR};
        List<UserVO> list = jdbcTemplate.query(sql, args, types, new MyUserRowMapper());
        return list;
    }

    public UserVO getUserVO(String userName) {
        String sql = "select user.id, user.user_name, (SELECT group_concat(user_role.role) FROM user_role WHERE user_role.user_name like ?) as role, user.email, user.phone, user.create_date, user.nick_name  from user where user.user_name = ?";
        String[] args = new String[]{userName, userName};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR};
        List<UserVO> list = jdbcTemplate.query(sql, args, types, new MyUserRowMapper());
        if (list.isEmpty()) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        return list.get(0);
    }
}
