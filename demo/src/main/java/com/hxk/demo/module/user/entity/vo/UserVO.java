package com.hxk.demo.module.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public class UserVO {

    private Long id;

    private String userName;

    private String nickName;
    
    private List<String> roles;

    private String email;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;

    public UserVO() {
    }

    public UserVO(Long id, String userName, String nickName, List<String> roles, String email, String phone, String createDate) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.roles = roles;
        this.email = email;
        this.phone = phone;
        this.createDate = createDate;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", roles=" + roles +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }

}
