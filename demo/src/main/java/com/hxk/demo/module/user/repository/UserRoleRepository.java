package com.hxk.demo.module.user.repository;

import com.hxk.demo.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.hxk.demo.module.user.entity.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * UserRoleRepository
 *
 * @author cheer
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    //接口会根据方法的名字进行sql语句的拼写
    List<UserRole> findByUserName(String userName);

    @Modifying
    void deleteByUserName(String userName);
}
