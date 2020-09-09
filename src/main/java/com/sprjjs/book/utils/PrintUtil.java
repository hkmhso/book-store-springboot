package com.sprjjs.book.utils;

public class PrintUtil {
	//打印信息
	public static void print(Class<?> clazz,Object msg) {
		//通过反射获取类名
		String clsName = clazz.getSimpleName();
		System.out.println("{"+clsName+"}"+msg);
	}

}
