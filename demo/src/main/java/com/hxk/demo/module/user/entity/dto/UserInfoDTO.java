package com.hxk.demo.module.user.entity.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.user.entity.dto
 * @className: UserInfoDTO
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class UserInfoDTO {

    private Long id;
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 30, message = "用户名长度为2-30")
    private String userName;
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 30, message = "昵称长度为2-30")
    private String nickName;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    @Size(max = 40, message = "邮箱最大长度为40")
    private String email;
    @NotBlank(message = "电话不能为空")
    @Size(min = 6, max = 15, message = "电话长度为6-15")
    private String phone;

    public UserInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
