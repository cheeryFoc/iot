package com.hxk.demo.module.user.entity.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.user.entity.dto
 * @className: AccountDTO
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class AccountDTO {

    private Long id;

    private String userName;

    @NotBlank(message = "旧密码不能为空")
    @Size(min = 6, max = 15, message = "密码长度为6-15")
    private String password;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 15, message = "密码长度为6-15")
    private String newPassword;

    public AccountDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
