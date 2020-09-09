package com.sprjjs.book.utils;

import javax.servlet.http.HttpSession;

public class  SessionAttributeUtil{

	/**
	 * 往session中存放键值对
	 * @param session session对象
	 * @param key  键名
	 * @param value  值
	 */
	public static void setAttribute(HttpSession session,String key,Object value){
		session.setAttribute(key, value);
		session.setMaxInactiveInterval(3*24*3600);
	}

	/**
	 * 通过键名获取session中对应的值
	 * @param session session对象
	 * @param key 键名
	 * @return 键对应的值
	 */
	public static Object getAttribute(HttpSession session,String key){
		return session.getAttribute(key);
	}

	/**
	 * 移除session中指定的键
	 * @param session session对象
	 * @param key 键名
	 */
	public static void removeAttribute(HttpSession session,String key){
		session.removeAttribute(key);
	}

	/**
	 * 移除session中所有的键
	 * @param session session对象
	 * @param key 键名
	 */
	public static void invalidate(HttpSession session){
		session.invalidate();
	}


}
