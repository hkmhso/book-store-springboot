package com.sprjjs.book.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.BookResult;
import com.sprjjs.book.pojo.TCart;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TCartService;
import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/cart")
public class TCartController {
	@Resource
	private TCartService tCartServiceImpl;
	
	/**
	 * 用户已经登录，添加购物车
	 * 不存在，则添加，存在，则修改商品数量即可
	 * @param tcart 添加或修改时购物车信息
	 * @return
	 */
	@RequestMapping(value="addToCart.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String addToCart(TCart tcart,HttpSession session){
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		tcart.setUid(tuser.getPhone());
		//插入购物车前先判断购物车中是否存在该图书，如果存在，则进行修改数量即可，否则新增
		TCart selByUidandBook = tCartServiceImpl.selByUidandBook(tcart.getUid(), tcart.getBook());
		//购物车中不存在该图书
		if(selByUidandBook==null){
			int insCart = tCartServiceImpl.insCart(tcart);
			if(insCart>0){
				return "yes";
			}
			return "no";
		}
		//存在，修改商品数量即可
		else{
			int updNum = tCartServiceImpl.updNum(tcart.getUid(), tcart.getBook(), selByUidandBook.getCount()+tcart.getCount());
			if(updNum>0){
				return "yes";
			}
			return "no";
		}
		
	}
	
	/**
	 * 这个控制器只负责用户还没登录时，想操作购物车时，对其拦截的功能
	 * 因为@ResponseBody恒不跳转
	 * @return
	 */
	@RequestMapping("no_login_cart.action")
	public String no_login_cart(){
		return "";
	}
	
	/**
	 * 显示购物车
	 * @param request
	 * @return
	 */
	/*@RequestMapping("showCart.action")
	public String showCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<TBookChild> selByUid = tCartServiceImpl.selAllByUid(((TUser)session.getAttribute("tuser")).getPhone());
		//将购物车信息存放到request作用域中
		request.setAttribute("tcarts", selByUid);
		//然后跳到购物车页面
		return "user/cart";
	}*/
	
	/**
	 * 从购物车中删除对应的图书（批量删除，需要考虑到事务回滚）
	 * @param book 图书编号，多个
	 * @param uid  用户编号
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteCartItem.action")
	@ResponseBody
	public BookResult<TCart> deleteCartItems(@RequestParam(value="book") List<String> book,HttpSession session){
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		BookResult<TCart> result=new BookResult<TCart>();
		try {
			tCartServiceImpl.delCarts(tuser.getPhone(),book);
			result.setStatus(200);
			result.setMsg("批量删除成功");
		} catch (Exception e) {
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 修改购物车图书数量
	 * @param tcart 修改时购物车信息
	 * @return
	 */
	@RequestMapping(value="changeItemNum.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String changeItemNum(TCart tcart,HttpSession session){
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		int updNum = tCartServiceImpl.updNum(tuser.getPhone(), tcart.getBook(), tcart.getCount());
		if(updNum>0){
			return "yes";
		}
		return "no";
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
	@RequestMapping("showPageCartInfo.action")
	public ModelAndView showPageCartInfo(@RequestParam(defaultValue="1",value="pageNum") Integer pageNum,
			@RequestParam(defaultValue="2",value="pageSize") Integer pageSize,
			HttpSession session){
		//PS:判断用户是否登录了，登录了才能看收藏记录，否则不能
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		PageInfo pageInfo = tCartServiceImpl.showPage(pageNum, pageSize,
				tuser.getPhone());
		ModelAndView modelAndView=new ModelAndView();
		//将分页信息存放到request中
		modelAndView.addObject("pageModel",pageInfo);
		//然后跳到购物车页面
		modelAndView.setViewName("user/cart");
		return modelAndView;
	}
}
