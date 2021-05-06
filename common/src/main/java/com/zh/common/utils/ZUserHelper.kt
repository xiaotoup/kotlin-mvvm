package com.zh.common.utils

import com.blankj.utilcode.util.SPUtils

/**
 * 本地保持用户信息
 */
object ZUserHelper {
    //--------------------- 登录状态 -------------------------
    fun setIsLogin(isLogin: Boolean) {
        SPUtils.getInstance().put("isLogin", isLogin)
    }

    fun getIsLogin() {
        SPUtils.getInstance().getBoolean("isLogin")
    }

    //--------------------- 用户Id -------------------------
    fun setUserId(userId: String) {
        SPUtils.getInstance().put("userId", userId)
    }

    fun getUserId() {
        SPUtils.getInstance().getString("userId")
    }

    //--------------------- 用户头像 -------------------------
    fun setAvatar(avatar: String) {
        SPUtils.getInstance().put("avatar", avatar)
    }

    fun getAvatar() {
        SPUtils.getInstance().getString("avatar")
    }

    //--------------------- 用户昵称 -------------------------
    fun setUserName(userName: String) {
        SPUtils.getInstance().put("userName", userName)
    }

    fun getUserName() {
        SPUtils.getInstance().getString("userName")
    }

    //--------------------- 用户token -------------------------
    fun setToken(token: String) {
        SPUtils.getInstance().put("token", token)
    }

    fun getToken() {
        SPUtils.getInstance().getString("token")
    }

    //--------------------- 用户Mobile -------------------------
    fun setMobile(mobile: String) {
        SPUtils.getInstance().put("mobile", mobile)
    }

    fun getMobile() {
        SPUtils.getInstance().getString("mobile")
    }

    //--------------------- 用户PassWord -------------------------
    fun setPassWord(passWord: String) {
        SPUtils.getInstance().put("passWord", passWord)
    }

    fun getPassWord() {
        SPUtils.getInstance().getString("passWord")
    }

    //--------------------- 清除用户数据 -------------------------
    fun clearUser() {
        setIsLogin(false)
        setUserId("")
        setAvatar("")
        setUserName("")
        setToken("")
        setMobile("")
        setPassWord("")
    }
}