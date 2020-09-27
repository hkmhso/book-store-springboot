package com.sprjjs.book.interceptor;

import com.sprjjs.book.controller.TUserController;
import com.sprjjs.book.service.TBookService;
import com.sprjjs.book.service.TUserService;
import com.sprjjs.book.pojo.BookResult;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.utils.HttpClientUtil;
import com.sprjjs.book.utils.JsonUtil;
import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	@Resource
	private TUserService userInfServiceImpl;

	@Resource
	private TBookService tBookServiceImpl;

	@Resource
	private TUserController tUserController;

	//进入控制器方法之前执行的方法
	/**
	 * 适用时机：一般用于处理用户未登录时，不能访问购物车，订单，收藏，设置等等模块
	 * 拦截的情况有以下几种情况：
	 * 	 1. 用户还没登录过，想操作购物车，订单...时，先跳到登录页给用户登录，登录成功后将用户信息存到session中，
	 * 		以便于在项目中共享用户信息，并将TT_TOKEN作为key，用户id作为value给浏览器响应一个cookie信息，从而达到免登录的功能。

	 2 用户登录过了，就可以操作上面的模块了。

	 3 用户点击退出按钮，这时将session中的存储用户信息的键值对销毁和cookie的value置为空串，从而
	 可以避免用户退出浏览器后，又根据id从数据库中重新查询，然后又可以实现免登录这种问题。

	 4 用户在数据库中注销该账号了，不管cookie有没有失效，该用户都是不存在的。
	 *
	 * 特别注意：只拦截购物车模块，订单模块，收藏模块，设置等等模块的控制器，
	 * 		       就一些需要先登录才能操作的模块，其他的控制器一律放行
	 */
	/**
	 * request:请求对象
	 * response:响应对象
	 * handler:被拦截的控制器对象
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		PrintUtil.print(this.getClass(), "-->preHandle()->>uri:"+requestURI);
		//先判断cookie中是否存在用户信息,为空或为空字符串的时都是用户未登录的情况
		String token=null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				PrintUtil.print(this.getClass(),"->>preHandle()->>键："+cookie.getName()+"   值："+cookie.getValue());
				if(cookie.getName().trim().equalsIgnoreCase("TT_TOKEN")){
					token=cookie.getValue();
				}
			}
		}
		//只拦截购物车模块，订单模块，收藏模块，设置等等模块的控制器，就一些需要先登录才能操作的模块
		if(requestURI.contains("/cart")||
				requestURI.contains("/order")||
				requestURI.contains("/oitem")||
				requestURI.contains("/collect")||
				requestURI.contains("/user/password-change.html")||
				requestURI.contains("/address")||
				requestURI.contains("/user/no_login_user.action")) {
			PrintUtil.print(this.getClass(), "-->preHandle()->>拦截uri:"+requestURI);
			//[1]先根据TT_TOKEN查询用户的账号是否还在数据库中存在
			String json = HttpClientUtil.doPost("http://47.104.107.11/user/token.action/"+token);
			BookResult selByToken = JsonUtil.jsonToPojo(json, BookResult.class);
			if(token!=null&&!token.trim().equalsIgnoreCase("")&&selByToken.getData()!=null){
				PrintUtil.print(this.getClass(), "->>preHandle()->>BookResult中的date类型："+selByToken.getData().getClass());
				//[2]已登录过了且用户没有在数据库中注销账号，可以操作购物车，订单等等模块了
				PrintUtil.print(this.getClass(), "->>preHandle()->>已登录过了且用户没有在数据库中注销账号");
    			/*[3]再判断session是否失效了，如果没有失效，则直接从原来的session取出用户信息即可，
    				  否则创建一个新的session,再重新将用户信息放到新的session中
    			*/
				//[3.1]session失效了，创建新的session并将用户信息重新放到session中
				HttpSession session = request.getSession();
				PrintUtil.print(this.getClass(), "->>preHandle()->>session是否失效了："+session.getAttribute("tuser")==null);
				if(session.getAttribute("tuser")==null){
					PrintUtil.print(this.getClass(), "->>preHandle()->>session失效了，创建新的session并将用户信息重新放到session中");
					//PS:将LinkedHashMap中的键值对装配到TUser中
					LinkedHashMap<String,Object> link=(LinkedHashMap<String,Object>)selByToken.getData();
					TUser user=new TUser();
					user.setUpwd(link.get("upwd").toString());
					user.setEmail(link.get("email").toString());
					user.setPhone(link.get("phone").toString());
					user.setRole((Integer)link.get("role"));
					user.setUname(link.get("uname").toString());
					session.setAttribute("tuser",user);
					session.setMaxInactiveInterval(3*24*3600);
					return true;
				}
				//[3.2]session没有失效，则直接从原来的session取出用户信息即可，
				PrintUtil.print(this.getClass(), "->>preHandle()->>session没有失效，直接从原来的session取出用户信息即可");
				return true;
			}
			PrintUtil.print(this.getClass(), "未登录过或点击退出操作了或用户在数据库中注销账号");
			//[2]未登录过或点击退出操作了或用户在数据库中注销账号，想操作购物车，订单等等模块的时候，跳到登录页面先进行登录
			response.sendRedirect("/chooseRole.html");
			return false;
		}
		PrintUtil.print(this.getClass(), "-->preHandle()->>放行uri:"+requestURI);
		//其他的控制器一律放行
		return true;
	}

	//执行完控制器，进入jsp之前执行的方法
	//适用时机：日志打印，页面被访问的次数，敏感词语过滤
	/**
	 *
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param handler 被拦截的控制器对象
	 * @param modelAndView  ModelAndView对象，包含控制器往作用域中放入的数据和要跳转的物理视图逻辑名，没有则为null
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		//通过modelAndView对象可以拿到控制器放入到作用域中的数据并可以进行过滤，且还可以拿到物理视图逻辑名。

	}

	//执行完jsp执行的方法
	//适用时机：记录执行过程的一些异常
	/**
	 *
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param handler 被拦截的控制器对象
	 * @param ex  异常对象，可以获取到运行过程中出现的异常信息，如果没有异常信息，则返回null
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
