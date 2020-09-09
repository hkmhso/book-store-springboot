package com.sprjjs.book.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sprjjs.book.pojo.TCollect;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TCollectionService;
import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/collect")
public class TCollectionController {
	@Resource
	private TCollectionService tCollectionServiceImpl;

	/**
	 * 用户已经登录了，添加收藏
	 * @param tcol  添加时收藏信息
	 * @return
	 */
	@RequestMapping(value="addCollect.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String addCollect(TCollect tcol,HttpSession session){
		String phone=((TUser)SessionAttributeUtil.getAttribute(session,"tuser")).getPhone();
		tcol.setUid(phone);
		//先判断用户是否收藏了该图书，如果没有，则进行收藏,否则提示用户是否要取消收藏
		TCollect selByUidIsbn = tCollectionServiceImpl.selByUidIsbn(tcol.getUid(), tcol.getBook());
		//没有收藏
		if(selByUidIsbn==null){
			int insCollect = tCollectionServiceImpl.insCollect(tcol);
			if(insCollect>0){
				return "noCollAndSuccColl";
			}
			return "noCollButFailColl";
		}
		//已经收藏
		return "yesColl";
	}

	/**
	 * 这个控制器只负责用户还没登录时，想操作收藏时，对其拦截的功能
	 * 因为@ResponseBody恒不跳转
	 * @return
	 */
	@RequestMapping("no_login_collect.action")
	public String no_login_collect(){
		return "";
	}

	/**
	 * 用户已经登录，取消收藏
	 * @param tcol 取消时收藏信息
	 * @return
	 */
	@RequestMapping(value="cancelCollect.action/{book}",produces="text/html;charset=utf-8")
	@ResponseBody
	public String cancelCollect(@PathVariable String book,HttpSession session){
		//先判断用户是否收藏了该图书，如果没有，则进行收藏,否则提示用户是否要取消收藏
		TCollect tcol=new TCollect();
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		tcol.setUid(tuser.getPhone());
		tcol.setBook(book);
		int delColl = tCollectionServiceImpl.delColl(tcol);
		if(delColl>0){
			//取消收藏成功
			return "yes";
		}
		//取消收藏失败
		return "no";
	}

	/**
	 * 显示所有收藏的图书信息
	 * @param request
	 * @return
	 */
	/*@RequestMapping("showCollection.action")
	public String showCollects(HttpServletRequest request){
		String uid=((TUser)request.getSession().getAttribute("tuser")).getPhone();
		List<TBook> showCollect = tCollectionServiceImpl.selAllByUid(uid);
		//将所有收藏的图书信息保存到request作用域中
		request.setAttribute("tcolls", showCollect);
		//然后跳到收藏页面
		return "user/collect";
	}*/

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
	@RequestMapping("showPageCollInfo.action")
	public ModelAndView showPageCollInfo(@RequestParam(defaultValue="1",value="pageNum") Integer pageNum,
										 @RequestParam(defaultValue="2",value="pageSize") Integer pageSize,
										 HttpSession session){
		TUser tuser = (TUser)SessionAttributeUtil.getAttribute(session, "tuser");
		PageInfo pageInfo = tCollectionServiceImpl.showPage(pageNum, pageSize,
				tuser.getPhone());
		ModelAndView modelAndView=new ModelAndView();
		//将分页信息存放到request中
		modelAndView.addObject("pageModel",pageInfo);
		//然后跳到收藏页面
		modelAndView.setViewName("user/collect");
		return modelAndView;
	}

}
