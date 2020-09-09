package com.sprjjs.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	/**
	 * 400页面
	 * @param request
	 * @return
	 */
	@RequestMapping("400")
	public String handle1(){
		return "redirect:/error/400.html";
	}
	
	/**
	 * 404页面
	 * @param request
	 * @return
	 */
	@RequestMapping("404")
	public String handle2(){
		System.out.println("404页面");
		return "redirect:/error/404.html";
	}
	
	/**
	 * 500页面
	 * @param request
	 * @return
	 */
	@RequestMapping("500")
	public String handle3(){
		return "redirect:/error/500.html";
	}

	/**
	 * 跳到WEB-INF目录下的页面
	 * @param html 页面
	 * @param referer 上一个页面url
	 * @return
	 */
	@RequestMapping("{html}")
	public String htmlAction(@PathVariable String html){
		if(html.trim().equalsIgnoreCase("400.html")){
			return "error/400";
		}else if(html.trim().equalsIgnoreCase("404.html")){
			return "error/404";
		}else if(html.trim().equalsIgnoreCase("500.html")){
			return "error/500";
		}
		return "redirect:/";
	}

}
