package com.hxk.demo.module.user.service;

import com.hxk.demo.module.user.entity.UserRole;
import com.hxk.demo.module.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserRoleService
 *
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> listUserRoles(String userName) {
        return userRoleRepository.findByUserName(userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserName(String userName) {
        userRoleRepository.deleteByUserName(userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setRoleAsRoot(String userName) {
        UserRole userRole = new UserRole();
        userRole.setUserName(userName);
        userRole.setRole("ROLE_ROOT");
        userRoleRepository.save(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

}
