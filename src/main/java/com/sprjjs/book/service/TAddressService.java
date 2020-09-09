package com.sprjjs.book.service;

import java.util.List;

import com.sprjjs.book.pojo.TAddress;

public interface TAddressService {
	/**
	 * 添加地址
	 * @param tAddress
	 * @return
	 */
	int insAddress(TAddress tAddress);

	/**
	 * 根据用户id查询所有地址
	 * @param uid
	 * @return
	 */
	List<TAddress> showAddress(String uid);

	/**
	 * 根据用户id和rid该查询地址信息
	 * @param tAddress
	 * @return
	 */
	TAddress selAddress(TAddress tAddress);

	/**
	 * 根据id删除地址
	 * @param rid
	 * @return
	 */
	int delByRid(Integer rid);

	/**
	 * 根据id查询该地址信息
	 * @param rid
	 * @return
	 */
	TAddress selByRid(Integer rid);

	/**
	 * 根据id更新地址信息
	 * @param address 地址信息
	 * @return
	 */
	int updByRid(TAddress address);


}

