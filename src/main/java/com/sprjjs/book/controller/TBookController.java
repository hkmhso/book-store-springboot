package com.sprjjs.book.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TCollect;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TBookService;
import com.sprjjs.book.service.TCollectionService;
import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/book")
public class TBookController {
	@Resource
	private TBookService tBookServiceImpl;
	
	@Resource
	private TCollectionService tCollectionServiceImpl;
	
	/**
	 * 查看图书详情
	 * @param isbn 图书编号
	 * @param request
	 * @return
	 */
	@RequestMapping("detail.action/{isbn}")
	public ModelAndView detail(@PathVariable String isbn,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		TBook selByisbn = tBookServiceImpl.selByisbn(isbn);
		//PS:将该图书详情放到session对象中,以便于用户直接下单该商品时，可以在order-confirm.jsp页面中直接取到
		SessionAttributeUtil.setAttribute(session, "tbook",selByisbn);
		System.out.println(selByisbn.toString());
		//PS:判断用户是否登录了，登录了才能看收藏记录，否则不能
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		PrintUtil.print(this.getClass(), "->>detail()->>"+tuser);
		//用户登录了，可以查看收藏记录了
		SessionAttributeUtil.getAttribute(session, "tuser");
		if(tuser!=null){
			PrintUtil.print(this.getClass(), "->>detail()->>用户登录了");
			//跳转到图书详情页面前,首先要判断该图书是否被收藏
			TCollect coll=new TCollect();
			PrintUtil.print(this.getClass(),"->>detail()->>"+(tuser));
			coll.setUid(tuser.getPhone());
			coll.setBook(isbn);
			boolean iscoll = tCollectionServiceImpl.isColl(coll);
			//将图书是否收藏的状态存放到request作用域中
			modelAndView.addObject("iscoll", iscoll);
		}
		//用户还没登录，不能查看收藏记录
		else{
			PrintUtil.print(this.getClass(), "->>detail()->>用户还没登录了");
		}
		modelAndView.setViewName("user/detail");
		return modelAndView;
	}
	
	/**
	 * 搜索功能
	 * @param title 搜索关键字
	 * @param request
	 * @return
	 */
	/*@RequestMapping("search.action")
	public String search(String title,HttpServletRequest request){
		if(title!=null&&!title.trim().equalsIgnoreCase("")){
			try {
				title=new String(title.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		PrintUtils.print(this.getClass(), "->>search()->>"+title);
		List<TBook> selByTitle = tBookServiceImpl.selByTitle(title);
		//将搜索到的所有图书放到session对象中
		HttpSession session = request.getSession();
		session.setAttribute("tbooks", selByTitle);
		session.setMaxInactiveInterval(3*24*3600);
		return "redirect:/user/search.html";
	}*/
	
	/**
	 * 查询全部图书
	 * @param request
	 * @return
	 */
	/*@RequestMapping("showallbook.action")
	public String showAllBooks(HttpServletRequest request){
		List<TBook> showAllBooks = tBookServiceImpl.selAll();
		//将全部图书信息存到request对象中
		request.setAttribute("books", showAllBooks);
		//跳到主页面
		return "forward:/user/index.html";
	}*/
	
	/**
	 * 更新图书上下架状态
	 * @param isbn
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping("admin/page/updStatus.action")
	public String updStatus(String isbn,Integer status,HttpSession session){
		int updStatus = tBookServiceImpl.updStatus(isbn, status);
		//更新成功,查询查询全部图书信息
		if(updStatus>0){
			List<TBook> showAllBooks = tBookServiceImpl.selAll();
			//将全部图书信息存到session对象中
			SessionAttributeUtil.setAttribute(session, "showAllBooks",showAllBooks);
			return "admin/page/product-list";
		}
		return "admin/page/product-list";
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
	 * @param title 搜索条件
	 * @param request
	 * @return
	 */
	@RequestMapping("showPage.action")
	public ModelAndView showPage(@RequestParam(defaultValue="1",value="pageNum") Integer pageNum,
			@RequestParam(defaultValue="8",value="pageSize") Integer pageSize,
			@RequestParam(required=false) String title){
    	//设置get请求编码格式
		if(title!=null&&!title.trim().equalsIgnoreCase("")){
			//设置请求编码格式
			try {
				title=new String(title.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		//PS:如果title为null，即没有搜索条件，则查询所有信息，否则按条件筛选
		PrintUtil.print(this.getClass(), "->showPage()->>"+title);
		PageInfo pageInfo = tBookServiceImpl.showPage(pageNum, pageSize,title);
		ModelAndView modelAndView=new ModelAndView();
		//将分页信息存放到request中;
		modelAndView.addObject("pageModel",pageInfo);
		//如果title不为null,则有搜索条件，则按条件筛选后跳到搜索页面
		if(title!=null){
			modelAndView.setViewName("user/search");
			return modelAndView;
		}
		//没有搜索条件，则查询所有，然后跳到主页面
		modelAndView.setViewName("user/index");
		return modelAndView;
	}

}
