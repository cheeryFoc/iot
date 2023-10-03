package com.hxk.demo.module.user.repository.copy;

import com.hxk.demo.module.user.entity.User;
import com.hxk.demo.module.user.entity.dto.UserRegisterDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserCopy {

    public User convertOfUserRegisterDTO(UserRegisterDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);

        return user;
    }
}
