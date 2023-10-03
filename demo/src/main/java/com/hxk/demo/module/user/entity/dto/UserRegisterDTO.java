package com.hxk.demo.module.user.entity.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserDTO
 *
 * @author star
 **/
public class UserRegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 30, message = "用户名长度为2-30")
    private String userName;

    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 30, message = "昵称长度为2-30")
    private String nickName;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 15, message = "密码长度为6-15")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    @Size(max = 40, message = "邮箱最大长度为40")
    private String email;

    public UserRegisterDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}