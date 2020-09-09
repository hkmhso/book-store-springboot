package com.sprjjs.book.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.TBook;

public interface TBookService {
	/**
	 * 根据图书编号查询图书信息
	 * @param isbn
	 * @return
	 */
	TBook selByisbn(String isbn);

	/**
	 * 搜索功能
	 * @param title
	 * @return
	 */
	List<TBook> selByTitle(String title);

	/**
	 * 查询所有图书
	 * @return
	 */
	List<TBook> selAll();

	/**
	 * 更新图书上下架状态
	 * @param isbn
	 * @param status
	 * @return
	 */
	int updStatus(String isbn,Integer status);

	/**
	 * 分页操作
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的条数
	 * @param title 搜索条件
	 * @return
	 */
	PageInfo showPage(Integer pageNum,Integer pageSize,String title);

	/**
	 * 按条件查询，模糊查询
	 * @param title 搜索条件
	 * @return
	 */
	List<TBook> selByCondition(String title);
}
