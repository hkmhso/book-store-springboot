package com.sprjjs.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TAdminController {
	
	/**
	 * 负责跳转到WEB-INF目录下的页面
	 * @param html 页面
	 * @return
	 */
	@RequestMapping("{html}")
	public String htmlAction(@PathVariable String html){
		if(html.trim().equalsIgnoreCase("login.html")){
			return "admin/login";
		}
		return "";
	}

}
