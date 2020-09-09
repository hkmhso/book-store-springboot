package com.sprjjs.book.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.BookResult;
import com.sprjjs.book.pojo.TAddress;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TBookChild;
import com.sprjjs.book.pojo.TOitem;
import com.sprjjs.book.pojo.TOrder;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TAddressService;
import com.sprjjs.book.service.TBookService;
import com.sprjjs.book.service.TOrderService;
import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/order")
public class TOrderController {
	@Autowired
	private TOrderService tOrderServiceImpl;
	
	@Resource
	private TAddressService tAddressServiceImpl;
	
	@Resource
	private TBookService tBookServiceImpl;

	/**
	 * 负责跳转到templates目录下的页面
	 * @param html 页面
	 * @return
	 */
	@RequestMapping("{html}")
	public String htmlAction(@PathVariable String html){
		if(html.trim().equalsIgnoreCase("order-confirm.html")){
			return "user/order-confirm";
		}else if(html.trim().equalsIgnoreCase("payment.html")){
			return "user/payment";
		}else if(html.trim().equalsIgnoreCase("pay-success.html")){
			return "user/pay-success";
		}else if(html.trim().equalsIgnoreCase("pay-fail.html")){
			return "user/pay-fail";
		}else if(html.trim().equalsIgnoreCase("order-info.html")){
			return "user/order-info";
		}
		return "";
	}
	
	/**
	 * 
	 * 用户已经登录了，提交订单时，可以有两种情况
	 * 		1.1 用户在浏览商品详情的时候可以直接下单，此时当前订单只有一个商品。
	 * 			此时将添加订单信息，批量添加订单项目记录视为一个事务,在事务层完成
	 * 		1.2 用户也可以在购物车中进行批量下单，此时当前订单可能有一个或多商品。
	 * 			此时将添加订单信息，批量添加订单项目记录,同时删除购物车提交订单的商品视为一个事务,在事务层完成
	 * 注意：(插入数据时，必须先往主表插入，然后才能往从表插入,删除数据则相反)
	 * @param product 提交订单的图书编号
	 * @param price 提交订单的图书单价
	 * @param count 提交订单的图书数量
	 * @param oitem 插入的订单项目信息
	 * @param order 插入的订单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("addOrder.action")
	@ResponseBody
	public BookResult<Map<String,Object>> addOrder(String referer,@RequestParam("book") List<String> book,
			@RequestParam("price") List<Double> price,
			@RequestParam("count") List<Integer> count,TOitem toitem,TOrder tOrder,
			HttpServletRequest request){
		PrintUtil.print(this.getClass(), "->>addOrder()->>"+referer);
		//手动生成订单编号
		DateFormat date=new SimpleDateFormat("yyyyMMddHHmmss");
		String uid=((TUser)request.getSession().getAttribute("tuser")).getPhone();
		String oid=(uid.substring(uid.length()-3)+date.format(new Date()));
		/**
		 * 添加订单记录
		 */
		//设置订单编号
		tOrder.setOid(oid);
		/**
		 * 批量添加订单项目记录,同时删除购物车提交订单的商品
		 */
		List<TOitem> list=new ArrayList<>();
		for (int i=0;i<book.size();i++) {
			toitem=new TOitem();
			//设置订单编号
			toitem.setOid(oid);
			//设置提交订单的图书编号
			toitem.setProduct(book.get(i));
			//设置提交订单的图书单价
			toitem.setPrice(price.get(i));
			//设置提交订单的图书数量
			toitem.setCount(count.get(i));
			list.add(toitem);
		}
		BookResult<Map<String,Object>> result=new BookResult<Map<String,Object>>();
		try {
			//[1]用户在浏览商品详情的时候可以直接下单，此时当前订单只有一个商品。
			//Referer:http://localhost:8080/BookStore/book/detail.action/9787111563891
			String contextPath=request.getServletContext().getContextPath();
			if(referer.trim().contains(contextPath+"/book")){
				PrintUtil.print(this.getClass(),"->>addOrder()->>用户在浏览商品详情的时候可以直接下单，此时当前订单只有一个商品。");
				tOrderServiceImpl.insOrderWithOutDelCart(list,uid,tOrder);
			}
			//[2]用户也可以在购物车中进行批量下单，此时当前订单可能有一个或多商品。
			//Referer:http://localhost:8080/BookStore/cart/showCart.action
			else if(referer.trim().contains(contextPath+"/cart")){
				PrintUtil.print(this.getClass(),"->>addOrder()->>用户也可以在购物车中进行批量下单，此时当前订单可能有一个或多商品。");
				tOrderServiceImpl.insOrderWithDelCart(list,uid,book,tOrder);
			}
			result.setStatus(200);
			result.setMsg("提交订单成功！！");
			//将订单编号,支付总额,下单地址id放到响应体中，以便于浏览器获取到，然后传给支付页面
			Map<String, Object> map=new HashMap<>();
			map.put("oid", oid);
			map.put("payment", tOrder.getPayment());
			map.put("aid", tOrder.getAid());
			result.setData(map);
		} catch (Exception e) {
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据用户编号和订单号删除该订单信息，同时删除订单表和订单项信息，需要考虑到事务的回滚
	 * 注意：(插入数据时，必须先往主表插入，然后才能往从表插入,删除数据则相反)
	 * @param oid
	 * @return
	 */
	@RequestMapping("delOrder.action")
	@ResponseBody
	public BookResult<TOrder> delOrder(String oid,HttpServletRequest request){
		TOrder torder=new TOrder();
		torder.setUid(((TUser)request.getSession().getAttribute("tuser")).getPhone());
		torder.setOid(oid);
		BookResult<TOrder> result=new BookResult<TOrder>();
		try {
			tOrderServiceImpl.delOrder(torder);
			result.setStatus(200);
			result.setMsg("删除订单成功");
		} catch (Exception e) {
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 这个控制器只负责用户还没登录时，想操作订单时，对其拦截的功能
	 * 因为@ResponseBody恒不跳转
	 * @return
	 */
	@RequestMapping("no_login_order.action")
	public String no_login_order(){
		return "";
	}
	
	/**
	 * 确认订单时，可以有两种情况
			1.1 用户在浏览商品详情的时候可以直接下单，此时当前订单只有一个商品。
			1.2 用户也可以在购物车中进行批量下单，此时当前订单可能有一个或多商品。
	 * @param book 图书编号，一个或多个
	 * @param count 相对应的图书数量
	 * @param request
	 * @return
	 */
	@RequestMapping("orderConfirm.action")
	public String orderConfirm(@RequestHeader("Referer") String referer,
							   @RequestParam(value="book") List<String> book,
							   @RequestParam("count") List<Integer> count,
							   HttpSession session,
							   HttpServletRequest request){
		/**
		 * PS：referer有四种情况
		 * [1]用户浏览商品详情时直接下单，且不新增地址。
		 * [2]用户浏览商品详情直接下单，同时新增了地址后。
		 * [3]用户从购物车中下单，且不新增地址
		 * [4]用户在购物车中下单，同时新增了地址
		 */
		//PS：需要获取上一个页面，判断用户是浏览详情时直接下单还是到购物车中下单，然后再进行相对应的操作，且放到session作用域中
		String contextPath=request.getServletContext().getContextPath();
		if(referer.trim().contains(contextPath+"/cart")||referer.trim().contains(contextPath+"/book")){
			SessionAttributeUtil.setAttribute(session, "referer", referer);
		}
		PrintUtil.print(this.getClass(), "->>orderconfirm()->>"+count);
		PrintUtil.print(this.getClass(), "->>orderconfirm()->>"+book);
		//根据用户确认订单时的图书编号查询对应的图书信息
		List<TBookChild> childs=new ArrayList<>();
		int index=0;
		for (String tBookChild : book) {
			TBook tbook = tBookServiceImpl.selByisbn(tBookChild);
			TBookChild child = new TBookChild(tbook.getIsbn(), tbook.getTitle(), 
					tbook.getAuthor(), tbook.getPrice(),
					tbook.getPress(), tbook.getEdition(), tbook.getPublished(), 
					tbook.getPages(), tbook.getWords(), tbook.getPackaging(), 
					tbook.getFormat(), tbook.getForm(), tbook.getStatus());
			child.setCount(count.get(index));
			index++;
			childs.add(child);
		}
		SessionAttributeUtil.setAttribute(session, "orderconfirms", childs);
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		//[3]根据用户id查询所有地址信息
		List<TAddress> showAddress = tAddressServiceImpl.showAddress(tuser.getPhone());
		SessionAttributeUtil.setAttribute(session, "taddresses", showAddress);
		return "redirect:/order/order-confirm.html";
	}
	
	/**
	 * 根据用户编号和订单编号更改订单信息
	 * @param tOrder 订单信息，包含了订单状态,订单编号,支付总额
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updOrderInfo.action")
	public String updOrderInfo(TOrder tOrder,HttpServletRequest request) throws Exception{
		PrintUtil.print(this.getClass(),"->>updOrderInfo()->>"+tOrder);
		/*PrintUtil.print(this.getClass(),"->>updOrderInfo()->>编码前"+tOrder.getSta());
		if(tOrder.getSta()!=null&&!tOrder.getSta().trim().equalsIgnoreCase("")){
			tOrder.setSta(new String(tOrder.getSta().getBytes("iso-8859-1"), "utf-8"));
		}
		PrintUtil.print(this.getClass(),"->>updOrderInfo()->>编码后"+tOrder.getSta());*/
		String phone=((TUser)request.getSession().getAttribute("tuser")).getPhone();
		String sta=tOrder.getSta();
		if(sta.trim().equalsIgnoreCase("待发货")){
			PrintUtil.print(this.getClass(),"->>updOrderInfo()->>用户已付款");
			PrintUtil.print(this.getClass(),"->>updOrderInfo()->>"+sta);
			//交付成功，设置下单时间和交付时间，否则不设置
			Date date = new Date();
			tOrder.setPlaced(date);
			tOrder.setHandover(date);
			int upSta = tOrderServiceImpl.updOrder(phone,tOrder);
			PrintUtil.print(this.getClass(),"->>updOrderInfo()->>用户已付款-->更新结果行数:"+upSta);
			if(upSta>0){
				return "redirect:/order/pay-success.html?oid="
						+tOrder.getOid()+"&payment="+tOrder.getPayment()
						+"&aid="+tOrder.getAid()+"&sta=yesPay";	
			}
		}
		else if(sta.trim().equalsIgnoreCase("待付款")){
			PrintUtil.print(this.getClass(),"->>updOrderInfo()->>用户没付款");
	    	int upSta = tOrderServiceImpl.updOrder(phone,tOrder);
	    	PrintUtil.print(this.getClass(),"->>updOrderInfo()->>"+sta);
	    	PrintUtil.print(this.getClass(),"->>updOrderInfo()->>用户没付款-->更新结果行数:"+upSta);
	    	if(upSta>0){
	    		return "redirect:/order/pay-fail.html?oid="
	    				+tOrder.getOid()+"&payment="+tOrder.getPayment()
	    				+"&aid="+tOrder.getAid()+"&sta=noPay";
	    	}
	    }
		return "redirect:/order/payment.html";
	}
	
	/**
	 * 根据用户编号显示所有的订单信息
	 * @param request
	 * @return
	 */
	/*@RequestMapping("showOrdersInfo.action")
	public String showOrdersInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		String uid = ((TUser)session.getAttribute("tuser")).getPhone();
		//[1]根据用户id查询该用户的所有订单编号
		List<TOrder> showOrders = tOrderServiceImpl.showOrders(uid);
		//[2]根据用户id查询该用户的所有的订单信息
		List<List<TBookChild>> showOrdersInfo = tOrderServiceImpl.showOrdersInfo(uid);
		//[3]将该用户的所有订单编号和所有的订单信息放到requset作用域中，以便于浏览器显示
		request.setAttribute("torders", showOrders);
		request.setAttribute("tordersInfo", showOrdersInfo);
		return "user/order";
	}*/
	
	/**
	 * 根据用户编号和订单编号查询该订单信息(一个订单可能单个商品，有可能有多个商品)
	 * @param oid  订单编号
	 * @param aid 下单的地址
	 * @param sta 订单状态
	 * @param request
	 * @return
	 */
	@RequestMapping("showSimpleOrderInfo.action")
	public String showSimpleOrderInfo(String oid,Integer aid,String sta,HttpSession session){
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		String phone=tuser.getPhone();
		//[1]根据用户编号和订单编号查询该订单编号
		TOrder showSimpleOrder = tOrderServiceImpl.showSimpleOrder(phone, oid);
		//[2]根据用户编号和订单编号查询该订单信息
		List<TBookChild> showSimpleOrderInfo= tOrderServiceImpl.showSimpleOrderInfo(phone, oid);
		TAddress taddress=new TAddress();
		//[3]根据用户编号和地址id查询当前订单的地址信息
		taddress.setUid(tuser.getPhone());
		taddress.setRid(aid);
		TAddress orderAddress = tAddressServiceImpl.selAddress(taddress);
		//[4]将当前订单信息,下单地址信息和订单编号放到session作用域中
		SessionAttributeUtil.setAttribute(session, "torderInfo", showSimpleOrderInfo);
		SessionAttributeUtil.setAttribute(session, "torderAddress", orderAddress);
		SessionAttributeUtil.setAttribute(session, "torder", showSimpleOrder);
		return "redirect:/order/order-info.html?oid="+oid+"&sta="+sta;
	}
	
	/**
	 * 分页查询（查询全部或根据条件查询）
	 * 注意：每次点击搜索时，将pageIndex置为1，重新从第一页开始显示，否则都会从员工阅览的页数开始，
	 *      从而可能会导致查到数据了，没有显示数据。
	 *      
		怎么知道是否点击搜索按钮了呢？
		回答：当有搜索条件的时候，即搜索字段至少有一个不为空，点击一定点击了搜索按钮，则按搜索条件筛选。
		          当一个搜索条件都没有，即搜索字段都为空，则点击了搜索按钮但是没有输入搜索条件或没有点击搜索按钮，即查询全部数据。
	 * @param pageIndex 页码
	 * @param pageSize 一页显示的条目数 
	 * @param request
	 * @return
	 */
	@RequestMapping("showPageOrdersInfo.action")
	public ModelAndView showPageOrdersInfo(@RequestParam(defaultValue="1",value="pageNum") Integer pageNum,
			@RequestParam(defaultValue="2",value="pageSize") Integer pageSize,
			HttpSession session){
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		String uid=tuser.getPhone();
		//[1]根据用户id分页查询该用户的订单编号
		PageInfo OrdersInfoPageInfo = tOrderServiceImpl.showPageOrders(pageNum, pageSize,uid);
		//[2]根据用户编号分页查询该用户的订单信息
		PageInfo OrdersPageInfo = tOrderServiceImpl.showPageOrdersInfo(pageNum, pageSize,uid);
		ModelAndView modelAndView=new ModelAndView();
		//[3]将该用户的所有订单编号和订单信息存放到request中，以便于浏览器显示
		modelAndView.addObject("pageModel",OrdersPageInfo);
		modelAndView.addObject("torders",OrdersInfoPageInfo.getList());
		modelAndView.setViewName("user/order");
		//然后跳到订单页面
		return modelAndView;
	}
		
}
