package com.sprjjs.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sprjjs.book.mapper.TBookMapper;
import com.sprjjs.book.mapper.TOitemMapper;
import com.sprjjs.book.mapper.TOrderMapper;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TOitem;
import com.sprjjs.book.pojo.TOitemExample;
import com.sprjjs.book.pojo.TOrder;
import com.sprjjs.book.pojo.TOrderExample;
import com.sprjjs.book.service.TCartService;
import com.sprjjs.book.service.TOrderService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TOrderServiceImpl implements TOrderService{

	@Resource
	private TOitemMapper tOitemMapper;

	@Resource
	private TBookMapper tBookMapper;

	@Resource
	private TCartService tCartServiceImpl;

	@Resource
	private TOrderMapper tOrderMapper;


	@Override
	public PageInfo showPageOrders(Integer pageNum, Integer pageSize,String uid) {
		//分页操作的原理是：在select * from xxxx where xxx=xxx and xxx=xxx ... 后面拼上limit pageNum,pageSize
		//创建分页查询条件，必须写在查询语句前面
		PageHelper.startPage(pageNum, pageSize);
		//[1]调用根据用户编号查询该用户的所有订单编号的方法
		List<TOrder> torders=showOrders(uid);
		PrintUtil.print(this.getClass(), "->>showPage()-->查询到的数据为："+torders);
		//PageInfo中包含了分页操作后所有的分页信息（页码,总页数，总记录数，当前页所显示的行数，当前页的数据）
		PageInfo pageInfo=new PageInfo<>(torders);
		return pageInfo;
	}

	//开启事务并指定事务管理器
	@Transactional(transactionManager = "transactionManager",rollbackForClassName="java.lang.Exception")
	@Override
	public int insOrderWithDelCart(List<TOitem> tOitems,String uid,List<String> book,TOrder tOrder) throws Exception{
		//[1]使用插入订单信息和订单项信息的方法
		int index=insOrder(tOitems,uid,tOrder);
		//[2]同时删除购物车提交订单的商品
		index+=tCartServiceImpl.delCarts(uid, book);
		//批量提交订单失败
		if(index!=(book.size()+tOitems.size()+1)){
			throw new Exception("操作失败，可能购物车中已经不存在该商品了！！");
		}
		//批量提交订单成功
		return index;
	}

	@Override
	public List<TBookChild> showSimpleOrderInfo(String uid,String oid) {
		//[1]调用根据用户编号和订单编号查询该订单编号的方法
		TOrder order = showSimpleOrder(uid,oid);
		//[2]再根据查询到的订单编号查询订单项目表，得到该订单信息(可能有一个或多个商品)
		TOitemExample example2=new TOitemExample();
		example2.createCriteria().andOidEqualTo(order.getOid());
		List<TOitem> oitems = tOitemMapper.selectByExample(example2);
		//[3]再根据该订单项中的图书编号查询图书表，得到该订单项信息(只有一个商品)
		List<TBookChild> list=new ArrayList<>();
		for(TOitem oitem:oitems){
			TBook book = tBookMapper.selectByPrimaryKey(oitem.getProduct());
			TBookChild child = new TBookChild(book.getIsbn(), book.getTitle(),
					book.getAuthor(), book.getPrice(),
					book.getPress(), book.getEdition(), book.getPublished(),
					book.getPages(), book.getWords(), book.getPackaging(),
					book.getFormat(), book.getForm(), book.getStatus());
			child.setCount(oitem.getCount());
			list.add(child);
		}
		return list;
	}

	@Override
	public int updOrder(String uid, TOrder tOrder) {
		TOrderExample example=new TOrderExample();
		example.createCriteria().andUidEqualTo(uid).andOidEqualTo(tOrder.getOid());
		return tOrderMapper.updateByExampleSelective(tOrder, example);
	}

	@Override
	public List<TOrder> showOrders(String uid) {
		//[1]先根据用户编号查询该用户的所有订单编号
		TOrderExample example=new TOrderExample();
		example.createCriteria().andUidEqualTo(uid);
		return tOrderMapper.selectByExample(example);
	}

	@Override
	public TOrder showSimpleOrder(String uid,String oid) {
		TOrderExample example=new TOrderExample();
		example.createCriteria().andUidEqualTo(uid).andOidEqualTo(oid);
		List<TOrder> orders = tOrderMapper.selectByExample(example);
		return orders!=null&&orders.size()>0?orders.get(0):null;
	}

	//开启事务并指定事务管理器
	@Transactional(transactionManager = "transactionManager",rollbackForClassName="java.lang.Exception")
	@Override
	public int insOrderWithOutDelCart(List<TOitem> tOitems, String uid, TOrder tOrder)
			throws Exception {
		//[1]使用插入订单信息和订单项信息的方法
		int index=insOrder(tOitems,uid,tOrder);
		//批量提交订单失败
		if(index!=(tOitems.size()+1)){
			throw new Exception("提交订单失败！！");
		}
		//批量提交订单成功
		return index;
	}


	@Override
	public int insOrder(List<TOitem> tOitems, String uid, TOrder tOrder){
		//PS:设置下单用户id
		tOrder.setUid(uid);
		int index=0;
		//[1]添加订单记录
		index+=tOrderMapper.insertSelective(tOrder);
		//[2]批量添加订单项目记录
		for (TOitem tOitem : tOitems) {
			index+=tOitemMapper.insertSelective(tOitem);
		}
		return index;
	}

	//开启事务并指定事务管理器
	@Transactional(transactionManager = "transactionManager")
	@Override
	public int delOrder(TOrder torder) throws Exception {
		int index=0;
		TOitemExample example=new TOitemExample();
		example.createCriteria().andOidEqualTo(torder.getOid());
		//[1]根据订单编号查询该订单有多少个订单项
		int count=tOitemMapper.countByExample(example);
		//[2]根据订单编号删除订单项目表信息
		index+=tOitemMapper.deleteByExample(example);
		//[3]根据用户编号和订单编号删除订单表信息
		TOrderExample example2=new TOrderExample();
		example2.createCriteria().andUidEqualTo(torder.getUid()).andOidEqualTo(torder.getOid());
		index+=tOrderMapper.deleteByExample(example2);
		PrintUtil.print(this.getClass(),"->>delOrder()->>count:"+count+"   index:"+index);
		if(index!=count+1){
			throw new Exception("删除订单失败，可能订单中的数据已不存在");
		}
		return index;
	}


	/**
	 * 使用PageHelper进行分页操作时，需要注意的点：
	 * 		PS:要想对原来的集合对象进行业务装配成另一个集合时，步骤为：
	 * 			第一步： 必须先通过原来的集合创建PageInfo对象(也就是在创建PageInfo对象时，
	 * 				      对原来的集合（查询所有的结果集）先不要进行装配成其他的集合。
	 * 			第二步：因为这样原来的集合类型才是Page类型，才能获取到分页的那些参数。
	 * 			第三步：然后再通过PageInfe的setList()方法重新设置为装配好的其他集合。
	 * 			第四步：这样子才能既可以获取到分页信息，又可以将结果集设置为装配好的其他集合。
	 */
	@Override
	public PageInfo showPageOrdersInfo(Integer pageNum, Integer pageSize,String uid) {
		//分页操作的原理是：在select * from xxxx where xxx=xxx and xxx=xxx ... 后面拼上limit pageNum,pageSize
		//创建分页查询条件，必须写在查询语句前面
		PageHelper.startPage(pageNum, pageSize);
		/*[第一步]在创建PageInfo对象时对原来的集合（查询所有的结果集）
	     	先不要进行装配成其他的集合。
		 */
		List<TOrder> torders=showOrders(uid);
		PrintUtil.print(this.getClass(), "->>showPage()-->??????????????"+torders);
		//PageInfo中包含了分页操作后所有的分页信息（页码,总页数，总记录数，当前页所显示的行数，当前页的数据）
		PageInfo pageInfo=new PageInfo<>(torders);
		//[第三步]然后再通过PageInfe的setList()方法重新设置为装配好的其他集合。
		pageInfo.setList(assemble(torders));
		PrintUtil.print(this.getClass(), "->>showPage()->>分页信息:"+pageInfo);
		return pageInfo;
	}

	/**
	 * 业务装配
	 * @return
	 */
	public List<List<TBookChild>> assemble(List<TOrder> torders){
		/**
		 * 大容器中的元素为每一个订单信息（可能有一个或多个商品）
		 */
		List<List<TBookChild>> tbookss=new ArrayList<>();
		//遍历该用户所有的订单
		for(TOrder torder:torders){
			//[2]再根据订单编号查询订单项目表,得到该订单信息(可能有一个或多个商品)
			TOitemExample example2=new TOitemExample();
			example2.createCriteria().andOidEqualTo(torder.getOid());
			List<TOitem> toitems= tOitemMapper.selectByExample(example2);
			/**
			 * 小容器中的元素为每一个订单编号中的每一个订单项信息(只有一个商品)
			 */
			List<TBookChild> tbooks=new ArrayList<>();
			//[3]再根据订单项的图书编号查询图书表，得到该订单项的信息(只有一个商品)
			for(TOitem toitem:toitems){
				TBook book = tBookMapper.selectByPrimaryKey(toitem.getProduct());
				TBookChild child = new TBookChild(book.getIsbn(), book.getTitle(),
						book.getAuthor(), book.getPrice(),
						book.getPress(), book.getEdition(), book.getPublished(),
						book.getPages(), book.getWords(), book.getPackaging(),
						book.getFormat(), book.getForm(), book.getStatus());
				child.setCount(toitem.getCount());
				//把每一个订单项信息添加到小容器中
				tbooks.add(child);
			}
			//把每一个订单信息添加到大容器中
			tbookss.add(tbooks);
		}
		return tbookss;
	}

}
