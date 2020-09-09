package com.sprjjs.book.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sprjjs.book.pojo.TAddress;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TOrder;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TAddressService;
import com.sprjjs.book.service.TCartService;
import com.sprjjs.book.service.TOitemService;
import com.sprjjs.book.service.TOrderService;
import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/oitem")
public class TOitemController {
	@Resource
	private TOitemService tOitemServiceImpl;
	
	@Resource
	private TOrderService tOrderServiceImpl;
	
	@Resource
	private TCartService tCartServiceImpl;
	
	@Resource
	private TAddressService tAddressServiceImpl;
	
	/**
	 * 根据用户id和图书编号和订单号查询该订单项信息(只有单个商品)
	 * @param oid 订单编号
	 * @param product 商品编号
	 * @param sta 订单状态
	 * @param request
	 * @return
	 */
	@RequestMapping("showSimpleOitemInfo.action")
	public String showSimpleOitemInfo(String oid,String product,String sta,HttpSession session){
		PrintUtil.print(this.getClass(),"->>showSimpleOitemInfo()->>编码前"+sta);
		if(sta!=null&&!sta.trim().equalsIgnoreCase("")){
			try {
				sta=new String(sta.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		PrintUtil.print(this.getClass(),"->>showSimpleOitemInfo()->>编码后"+sta);
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		String phone=tuser.getPhone();
		//[1]根据用户编号和订单编号查询该订单编号
		TOrder showSimpleOrder = tOrderServiceImpl.showSimpleOrder(phone, oid);
		//[2]根据用户编号,订单编号和图书编号查询该订单项信息
		TBookChild showSimpleOitemInfo= tOitemServiceImpl.showSimpleOitemInfo(phone,oid,product);
		//PS:将该订单下项信息伪装成一个集合，已便在显示订单详情的时候可以进行遍历
		List<TBookChild> showSimpleOrderInfo=new ArrayList<>();
		showSimpleOrderInfo.add(showSimpleOitemInfo);
		TAddress taddress=new TAddress();
		//[3]根据用户编号和地址id查询当前订单的地址信息
		taddress.setUid(tuser.getPhone());
		taddress.setRid(showSimpleOrder.getAid());
		TAddress orderAddress = tAddressServiceImpl.selAddress(taddress);
		//[4]将当前订单信息,下单地址信息和订单编号放到session作用域中
		SessionAttributeUtil.setAttribute(session, "torderInfo", showSimpleOrderInfo);
		SessionAttributeUtil.setAttribute(session, "torderAddress", orderAddress);
		SessionAttributeUtil.setAttribute(session, "torder", showSimpleOrder);
		//未付款
		if(sta.trim().equalsIgnoreCase("待付款")){
			sta="noPay";
		}else{ //已付款
			sta="yesPay";
		}
		return "redirect:/order/order-info.html?oid="+oid+"&sta="+sta;
	}
	
	
}
