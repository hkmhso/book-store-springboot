package com.sprjjs.book.service;

import com.sprjjs.book.pojo.TBookChild;

public interface TOitemService {

	/**
	 * 根据用户id和订单号和图书编号查询该订单项信息(只有单个商品)
	 * @param oid
	 * @param product
	 * @return
	 */
	TBookChild showSimpleOitemInfo(String uid,String oid,String product);
}
