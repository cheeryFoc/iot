package com.hxk.demo.module.system.service;

import com.hxk.demo.module.user.entity.User;
import com.hxk.demo.module.user.repository.UserRepository;
import com.hxk.demo.module.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    public void initRole(String userName) {
        // 预检查用户名是否存在,不存在就初始化
        Optional<User> userOptional = userRepository.findByUserName(userName);

        if (!userOptional.isPresent()) {
            User user = new User();
            user.setUserName(userName);
            user.setNickName(userName);
            user.setEmail(userName + "@iot.com");
            user.setPhone("1234567890");
            String cryptPassword = bCryptPasswordEncoder.encode("123456");
            user.setPassword(cryptPassword);
            userRepository.save(user);
//            throw new AlreadyExistsException("Save failed, the user name already exist.");
            userRoleService.setRoleAsRoot(userName);
        }

    }
}
