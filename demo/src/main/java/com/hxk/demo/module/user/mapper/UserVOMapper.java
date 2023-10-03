package com.hxk.demo.module.user.mapper;

import com.hxk.demo.module.user.entity.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserVOMapper {

    public UserVO getUserByName(@Param("userName") String userName);
    public List<UserVO> getListByName(String userName);
}
