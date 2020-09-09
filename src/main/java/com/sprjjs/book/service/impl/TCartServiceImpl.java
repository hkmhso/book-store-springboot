package com.sprjjs.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sprjjs.book.mapper.TBookMapper;
import com.sprjjs.book.mapper.TCartMapper;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TCart;
import com.sprjjs.book.pojo.TCartExample;
import com.sprjjs.book.service.TCartService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;

@Service
public class TCartServiceImpl implements TCartService{
	@Resource
	private TCartMapper tCartMapper;

	@Resource
	private TBookMapper tBookMapper;

	@Override
	public List<TCart> selAllByUid(String uid) {
		TCartExample example = new TCartExample();
		example.createCriteria().andUidEqualTo(uid);
		List<TCart> list = tCartMapper.selectByExample(example);
		return list;
	}

	//开启事务，并指定事务管理器
	@Transactional(transactionManager = "transactionManager",rollbackForClassName="java.lang.Exception")
	@Override
	public int delCarts(String uid,List<String> book) throws Exception {
		int index=0;
		for (String isbn : book) {
			TCartExample example=new TCartExample();
			example.createCriteria().andUidEqualTo(uid).andBookEqualTo(isbn);
			index+=tCartMapper.deleteByExample(example);
		}
		//批量删除失败
		if(index!=book.size()){
			throw new Exception("操作失败，可能购物车中已经不存在该商品了！！");
		}
		//批量删除成功
		return index;
	}

	@Override
	public int updNum(String uid, String isbn, Integer count) {
		TCart cart=new TCart();
		cart.setCount(count);
		TCartExample example=new TCartExample();
		example.createCriteria().andUidEqualTo(uid).andBookEqualTo(isbn);
		return tCartMapper.updateByExampleSelective(cart, example);
	}

	@Override
	public int insCart(TCart cart) {
		return tCartMapper.insertSelective(cart);
	}

	@Override
	public TCart selByUidandBook(String uid, String book) {
		TCartExample example=new TCartExample();
		example.createCriteria().andUidEqualTo(uid).andBookEqualTo(book);
		List<TCart> list = tCartMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;

	}

	@Override
	public List<TBookChild> orderConfirm(String uid, String[] books) {
		TCartExample example = new TCartExample();
		List<String> list=new ArrayList<>();
		for(String book:books) {
			list.add(book);
		}
		example.createCriteria().andUidEqualTo(uid).andBookIn(list);
		List<TCart> list1 = tCartMapper.selectByExample(example);
		List<TBookChild> listCart = new ArrayList<>();
		for (TCart cart : list1) {
			TBook book = tBookMapper.selectByPrimaryKey(cart.getBook());
			TBookChild child = new TBookChild(book.getIsbn(), book.getTitle(),
					book.getAuthor(), book.getPrice(),
					book.getPress(), book.getEdition(), book.getPublished(),
					book.getPages(), book.getWords(), book.getPackaging(),
					book.getFormat(), book.getForm(), book.getStatus());
			child.setCount(cart.getCount());
			listCart.add(child);
		}
		return listCart;
	}

	@Override
	public PageInfo showPage(Integer pageNum, Integer pageSize,String uid) {
		//分页操作的原理是：在select * from xxxx where xxx=xxx and xxx=xxx ... 后面拼上limit pageNum,pageSize
		//创建分页查询条件，必须写在查询语句前面
		PageHelper.startPage(pageNum, pageSize);
		/*[第一步]在创建PageInfo对象时对原来的集合（查询所有的结果集）
	     		先不要进行装配成其他的集合。
		*/
		List<TCart> carts=selAllByUid(uid);
		PrintUtil.print(this.getClass(), "->>showPage()-->查询到的数据为："+carts);
		//PageInfo中包含了分页操作后所有的分页信息（页码,总页数，总记录数，当前页所显示的行数，当前页的数据）
		PageInfo pageInfo=new PageInfo<>(carts);
		//[第三步]然后再通过PageInfe的setList()方法重新设置为装配好的其他集合。
		pageInfo.setList(assemble(carts));
		PrintUtil.print(this.getClass(), "->>showPage()->>分页信息:"+pageInfo);
		return pageInfo;
	}

	/**
	 * 业务装配
	 * @return
	 */
	public List<TBookChild> assemble(List<TCart> carts){
		List<TBookChild> listCart = new ArrayList<>();
		for (TCart cart : carts) {
			TBook book = tBookMapper.selectByPrimaryKey(cart.getBook());
			TBookChild child = new TBookChild(book.getIsbn(), book.getTitle(),
					book.getAuthor(), book.getPrice(),
					book.getPress(), book.getEdition(), book.getPublished(),
					book.getPages(), book.getWords(), book.getPackaging(),
					book.getFormat(), book.getForm(), book.getStatus());
			child.setCount(cart.getCount());
			listCart.add(child);
		}
		return listCart;
	}

}
