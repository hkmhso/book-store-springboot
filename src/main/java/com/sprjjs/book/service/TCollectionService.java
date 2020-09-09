package com.sprjjs.book.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.TCollect;

public interface TCollectionService {
	/**
	 * 添加收藏
	 * @param col
	 * @return
	 */
	int insCollect(TCollect col);

	/**
	 * 显示该用户收藏商品信息
	 * @param uid
	 * @return
	 */
	List<TCollect> selAllByUid(String uid);

	/**
	 * 根据uid和图书编号查询是否已经收藏过
	 * @param uid
	 * @param isbn
	 * @return
	 */
	TCollect selByUidIsbn(String uid,String isbn);

	/**
	 * 取消收藏
	 * @param coll
	 * @return
	 */
	int delColl(TCollect coll);

	/**
	 * 判断是否收藏
	 * true:收藏了
	 * false：没收藏
	 * @param coll
	 * @return
	 */
	boolean isColl(TCollect coll);

	/**
	 * 分页操作
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的条数
	 * @param uid 用户编号
	 * @return
	 */
	PageInfo showPage(Integer pageNum,Integer pageSize,String uid);

}
