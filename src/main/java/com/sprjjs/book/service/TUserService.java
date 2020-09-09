package com.sprjjs.book.service;

import com.sprjjs.book.pojo.TUser;

public interface TUserService {
	/**
	 * 查询用户存不存在&&查看用户名是否被占用
	 * @param uname
	 * @return
	 */
	TUser selByName(String uname);

	/**
	 * 用户登录
	 * @param tuser
	 * @return
	 */
	TUser login(TUser tuser);

	/**
	 * 查看邮箱是否被占用
	 * @param email
	 * @return
	 */
	TUser selByEmail(String email);

	/**
	 * 查看原密码是否正确
	 * @param email
	 * @return
	 */
	TUser selByUpwd(TUser tUser);

	/**
	 * 查看手机号码是否被占用
	 * @param phone
	 * @return
	 */
	TUser selByPhone(String phone);

	/**
	 * 用户注册
	 * @param tuser
	 * @return
	 */
	int insTUser(TUser tuser);

	/**
	 * 修改密码
	 * @param uname
	 * @param upwd
	 * @return
	 */
	int updUpwd(TUser tUser);
}
