package com.sprjjs.book.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sprjjs.book.mapper.TBookMapper;
import com.sprjjs.book.mapper.TCartMapper;
import com.sprjjs.book.mapper.TOitemMapper;
import com.sprjjs.book.mapper.TOrderMapper;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TOitem;
import com.sprjjs.book.pojo.TOitemExample;
import com.sprjjs.book.pojo.TOrder;
import com.sprjjs.book.service.TOitemService;
import com.sprjjs.book.service.TOrderService;
@Service
public class TOitemServiceImpl implements TOitemService{
	@Resource
	private TOitemMapper tOitemMapper;

	@Resource
	private TCartMapper tCartMapper;

	@Resource
	private TOrderMapper tOrderMapper;

	@Resource
	private TBookMapper tBookMapper;

	@Resource
	private TOrderService tOrderServiceImpl;

	@Override
	public TBookChild showSimpleOitemInfo(String uid,String oid, String product) {
		//[1]调用根据用户编号和订单编号查询该订单编号的方法
		TOrder order = tOrderServiceImpl.showSimpleOrder(uid,oid);
		//[2]再根据查询到的订单编号查询订单项目表，得到该订单信息(可能有一个或多个商品)
		TOitemExample example2=new TOitemExample();
		example2.createCriteria().andOidEqualTo(order.getOid());
		List<TOitem> oitems = tOitemMapper.selectByExample(example2);
		//[3]再根据该订单项中的图书编号查询图书表，得到该订单项信息(只有一个商品)
		TBookChild child=null;
		for(TOitem oitem:oitems){
			if(oitem.getProduct().trim().equalsIgnoreCase(product)){
				TBook book = tBookMapper.selectByPrimaryKey(oitem.getProduct());
				child = new TBookChild(book.getIsbn(), book.getTitle(),
						book.getAuthor(), book.getPrice(),
						book.getPress(), book.getEdition(), book.getPublished(),
						book.getPages(), book.getWords(), book.getPackaging(),
						book.getFormat(), book.getForm(), book.getStatus());
				child.setCount(oitem.getCount());
			}
		}
		return child;
	}

}
