package com.bucuoa.passport.entity;

public class SessionUser {
    private Long userId; // 用户ID

    private String userName; // 用户名

    private String userIcon; // 用户小图像

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getUserName() {

	return userName;
    }

    public void setUserName(String userName) {

	this.userName = userName;
    }

    public String getUserIcon() {
	return userIcon;
    }

    public void setUserIcon(String userIcon) {
	this.userIcon = userIcon;
    }

}
