package com.sprjjs.book.controller;

import javax.servlet.http.HttpSession;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/")
public class PageController {
	
	/**
	 * 欢迎页
	 * @return
	 */
	@RequestMapping("")
	public String welcome(){
		//跳到查询所有图书信息的控制器，以便于在页面渲染出来
		return "forward:/book/showPage.action";
	}

	/**
	 * 跳到WEB-INF目录下的页面
	 * @param html 页面
	 * @param referer 上一个页面url
	 * @return
	 */
	@RequestMapping("{html}")
	public String htmlAction(@PathVariable String html,@RequestHeader("Referer") String referer,HttpSession session){
		if(!html.trim().equalsIgnoreCase("favicon.ico")){
			//将Referer属性值放到session作用域中
			SessionAttributeUtil.setAttribute(session, "referer", referer);
			PrintUtil.print(this.getClass(), "->>htmlAction()->>"+html);
			if(html.trim().equalsIgnoreCase("chooseRole.html")){
				return "chooseRole";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}

}
