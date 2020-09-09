package com.sprjjs.book.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sprjjs.book.pojo.BookResult;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TUserService;
import com.sprjjs.book.utils.SessionAttributeUtil;

@Controller
@RequestMapping("/user")
public class TUserController {
	@Resource
	private TUserService tUserServiceImpl;
	
	/**
	 * 负责跳转到templates目录下的页面
	 * @param html 页面
	 * @return
	 */
	@RequestMapping("{html}")
	public String htmlAction(@PathVariable String html){
		if(html.trim().equalsIgnoreCase("login.html")){
			return "user/login";
		}else if(html.trim().equalsIgnoreCase("lookforward.html")){
			return "user/lookforward";
		}else if(html.trim().equalsIgnoreCase("password-change.html")){
			return "user/password-change";
		}else if(html.trim().equalsIgnoreCase("regist.html")){
			return "user/regist";
		}
		return "";
	}

	/**
	 * 检验用户名存不存在&&查看用户名是否被占用
	 * @param uname 用户名
	 * @return
	 */
	@RequestMapping(value="unameCheck.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String unameCheck(String uname){
		TUser selByName = tUserServiceImpl.selByName(uname);
		if(selByName!=null){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 根据cookie中的TT_TOKEN属性值（也就是用户电话）查询用户信息
	 * @param token cookie中的TT_TOKEN属性值（也就是用户电话）
	 * @return
	 */
	@RequestMapping("token.action/{token}")
	@ResponseBody
	public BookResult<TUser> selByToken(@PathVariable String token,HttpSession session){
		TUser selByPhone = tUserServiceImpl.selByPhone(token);
		BookResult<TUser> result=new BookResult<TUser>();
		if(selByPhone!=null){
			//将用户信息保存都session作用域中
	        SessionAttributeUtil.setAttribute(session, "tuser", selByPhone);
			result.setStatus(200);
			result.setData(selByPhone);
			return result;
		}
		return result;
	}
	
	/**
	 * 登录
	 * @param tuser 登录信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="login.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String login(TUser tuser,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		PrintUtil.print(this.getClass(), "->>login()->>用户信息:"+tuser);
		TUser login = tUserServiceImpl.login(tuser);
		if(login!=null&&login.getRole()==0){
			//设置cookie信息,key:TT_TOKEN  value:用户电话号码(唯一标识)
			Cookie cookie=new Cookie("TT_TOKEN",login.getPhone());
			System.out.println("设置cookie");
			//设置cookie有效时，3天
			cookie.setMaxAge(3*24*3600);
			//获取项目发布到tomcat的项目名
			String contextPath  = request.getContextPath();
			//如果web项目没有指定 Application context 给它一个默认值“/”,否则设置 Cookie 的作用范围无效
	        if(contextPath.trim().equalsIgnoreCase("")){
	            contextPath = "/";
	        }
	        //设置有效路径
	        cookie.setPath(contextPath);
			//将用户信息保存都session作用域中
	        SessionAttributeUtil.setAttribute(session, "tuser", login);
			//添加到响应头中
			response.addCookie(cookie);
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 检验原密码是否正确
	 * @param tuser 用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="upwdCheck.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String upwdCheck(TUser tuser,HttpServletRequest request){
		TUser uaccountCheck = tUserServiceImpl.selByUpwd(tuser);
		if(uaccountCheck!=null){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 修改密码
	 * @param uname 用户名
	 * @param npwd 新密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="changePassword.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String changePassword(String uname,String npwd){
		TUser tUser=new TUser();
		tUser.setUname(uname);
		tUser.setUpwd(npwd);
		int changePassword = tUserServiceImpl.updUpwd(tUser);
		if(changePassword>0){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 检验邮箱是否被占用
	 * @param email 邮箱
	 * @return
	 */
	@RequestMapping(value="emailCheck.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String emailCheck(String email){
		TUser selByEmail = tUserServiceImpl.selByEmail(email);
		if(selByEmail!=null){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 检验手机号码是否被占用
	 * @param phone 是手机号码
	 * @return
	 */
	@RequestMapping(value="phoneCheck.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String phoneCheck(String phone){
		TUser selByPhone = tUserServiceImpl.selByPhone(phone);
		if(selByPhone!=null){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 这个控制器只负责用户还没登录时，想修改密码时，对其拦截的功能
	 * 因为@ResponseBody恒不跳转
	 * @return
	 */
	@RequestMapping("no_login_user.action")
	public String no_login_user(){
		return "";
	}

	/**
	 * 注册功能
	 * @param tuser 注册信息
	 * @return
	 */
	@RequestMapping(value="regist.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String regist(TUser tuser){
		int insTUser = tUserServiceImpl.insTUser(tuser);
		//注册成功，跳到用户登录页面
		if(insTUser>0){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 退出功能
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("logout.action")
	@ResponseBody
	public BookResult<TUser> logout(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		BookResult<TUser> result=new BookResult<TUser>();
		//只销毁session中存储的用户信息，并将cookie中的key对应的value置为空字符串
		SessionAttributeUtil.removeAttribute(session,"tuser");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if(name.trim().equalsIgnoreCase("TT_TOKEN")){
					//将cookie中的value置为""
					cookie.setValue("");
					//设置cookie有效时，3天
					cookie.setMaxAge(3*24*3600);
					//获取项目发布到tomcat的项目名
					String contextPath  = request.getContextPath();
					//如果web项目没有指定 Application context 给它一个默认值“/”,否则设置 Cookie 的作用范围无效
			        if(contextPath.trim().equalsIgnoreCase("")){
			            contextPath = "/";
			        }
			        //设置有效路径为当前项目
			        cookie.setPath(contextPath);
					response.addCookie(cookie);
					result.setStatus(200);
					return result;
				}
				PrintUtil.print(this.getClass(),"->>logout()->>退出");
				PrintUtil.print(this.getClass(),"->>logout()->>键："+cookie.getName()+"   值："+cookie.getValue());
			}
		}
		return result;
	}
					
}
