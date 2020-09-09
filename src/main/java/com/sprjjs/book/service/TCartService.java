package com.sprjjs.book.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TCart;

public interface TCartService {
	/**
	 * 根据uid查询购物车
	 * @return
	 */
	List<TCart> selAllByUid(String uid);

	/**
	 * 根据用户id和图书编号删除图书（批量删除，需要考虑到事务回滚）
	 */
	int delCarts(String uid,List<String> book) throws Exception;

	/**
	 * 根据uid和图书编号修改图书数量
	 */
	int updNum(String uid,String isbn,Integer count);

	/**
	 * 加入购物车
	 */
	int insCart(TCart cart);

	/**
	 * 根据uid和图书编号查询购物车是否已存在该图书，如果存在则修改数量即可，否则插入
	 */
	TCart selByUidandBook(String uid,String book);

	/**
	 * 确认订单
	 * @param uid
	 * @param books
	 * @return
	 */
	List<TBookChild> orderConfirm(String uid, String[] books);

	/**
	 * 分页操作
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的条数
	 * @param uid 用户编号
	 * @return
	 */
	PageInfo showPage(Integer pageNum,Integer pageSize,String uid);
}
