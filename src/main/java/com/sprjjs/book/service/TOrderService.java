package com.sprjjs.book.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TOitem;
import com.sprjjs.book.pojo.TOrder;

public interface TOrderService {
	/**
	 *  PS:用户也可以在购物车中进行批量下单，此时当前订单可能有一个或多商品。
	 * 	此时将添加订单信息，批量添加订单项目记录,同时删除购物车提交订单的商品视为一个事务
	 * 注意：(插入数据时，必须先往主表插入，然后才能往从表插入,删除数据则相反)
	 * @param tOitems 订单项目记录
	 * @param uid 要从购物车中删除的用户编号
	 * @param book 要从购物车中删除的图书编号
	 * @return
	 * @throws Exception
	 */
	int insOrderWithDelCart(List<TOitem> tOitems,String uid,List<String> book,TOrder torder) throws Exception;

	/**
	 * 用户在浏览商品详情的时候可以直接下单，此时当前订单只有一个商品。
	 * 此时将添加订单信息，批量添加订单项目记录视为一个事务
	 * 注意：(插入数据时，必须先往主表插入，然后才能往从表插入,删除数据则相反)
	 * @param tOitems 订单项目记录
	 * @param uid 要从购物车中删除的用户编号
	 * @param book 要从购物车中删除的图书编号
	 * @return
	 * @throws Exception
	 */
	int insOrderWithOutDelCart(List<TOitem> tOitems,String uid,TOrder torder) throws Exception;

	/**
	 *  PS:用户也可以在购物车中进行批量下单，此时当前订单可能有一个或多商品。
	 * 	此时将添加订单信息，批量添加订单项目记录,同时删除购物车提交订单的商品视为一个事务
	 * 注意：(插入数据时，必须先往主表插入，然后才能往从表插入,删除数据则相反)
	 * @param tOitems 订单项目记录
	 * @param uid 要从购物车中删除的用户编号
	 * @param book 要从购物车中删除的图书编号
	 * @return
	 * @throws Exception
	 */
	int insOrder(List<TOitem> tOitems,String uid,TOrder tOrder);

	/**
	 * 根据用户编号和订单号删除该订单信息，同时删除订单表和订单项信息，需要考虑到事务的回滚
	 * 注意：(插入数据时，必须先往主表插入，然后才能往从表插入,删除数据则相反)
	 * @param torder 要删除的订单信息
	 * @throws Exception
	 */
	int delOrder(TOrder torder) throws Exception;

	/**
	 * 根据用户id和订单号查询单个订单信息(可能有一个或多个商品)
	 * @param uid
	 * @param oid
	 * @return
	 */
	List<TBookChild>  showSimpleOrderInfo(String uid, String oid);

	/**
	 * 根据用户id和订单编号更新订单信息
	 * @param uid
	 * @param oid
	 * @param sta
	 * @return
	 */
	int updOrder(String uid,TOrder tOrder);

	/**
	 * 根据用户id查询该用户的所有订单编号
	 * @param uid
	 * @return
	 */
	List<TOrder> showOrders(String uid);

	/**
	 * 根据用户id和订单编号查询单个订单编号
	 * @param uid
	 * @param oid
	 * @return
	 */
	TOrder showSimpleOrder(String uid, String oid);

	/**
	 * 根据用户编号分页查询所有订单信息
	 * 注意：大容器套小容器：
	 * 			大容器中的元素为每一个订单信息（可能有一个或多个商品）
	 * 			小容器中的元素为每一个订单编号下的每一个订单项信息(只有一个商品)
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的条数
	 * @param uid 用户编号
	 * @return
	 */
	PageInfo showPageOrdersInfo(Integer pageNum,Integer pageSize,String uid);

	/**
	 * 根据用户编号分页查询所有订单编号
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的条数
	 * @param uid 用户编号
	 * @return
	 */
	PageInfo showPageOrders(Integer pageNum,Integer pageSize,String uid);

}
