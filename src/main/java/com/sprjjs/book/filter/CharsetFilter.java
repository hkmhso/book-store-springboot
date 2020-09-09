package com.sprjjs.book.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
//表示对所有请求进行拦截
@WebFilter("/*")
public class CharsetFilter implements Filter {
    public CharsetFilter() {
        // TODO Auto-generated constructor stub
    }
	public void destroy() {
		// TODO Auto-generated method stub
	}
	//统一管理字符编码
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//设置请求编码格式
		request.setCharacterEncoding("utf-8");
		//设置响应编码格式
		response.setContentType("text/html;charset=utf-8");
		//放行servlet
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
