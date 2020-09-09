package com.sprjjs.book.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sprjjs.book.pojo.TAddress;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.service.TAddressService;
import com.sprjjs.book.utils.SessionAttributeUtil;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/address")
public class TAddressController {
	@Resource
	private TAddressService tAddressServiceImpl;

	/**
	 * 负责跳转到templates目录下的页面
	 * @param html 页面
	 * @return
	 */
	@RequestMapping("{html}")
	public String htmlAction(@PathVariable String html){
		if(html.trim().equalsIgnoreCase("address-add.html")){
			return "user/address-add";
		}
		return "";
	}

	/**
	 * 添加地址
	 * @param tAddress 地址信息
	 * @param request 
	 * @return
	 */
	@RequestMapping(value="addAddress.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String addAddress(TAddress tAddress,HttpSession session){
		tAddress.setUid(((TUser)SessionAttributeUtil.getAttribute(session, "tuser")).getPhone());
		//DateFormat date=new SimpleDateFormat("yyyy--MM--dd HH:mm:ss");
		tAddress.setAdded(new Date());
		int insAddress = tAddressServiceImpl.insAddress(tAddress);
		if(insAddress>0){
			return "yes";
		}
		return "no";
	}

	/**
	 * 删除地址
	 * @param rid 地址id
	 * @return
	 */
	@RequestMapping(value="delAddress.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String addAddress(Integer rid){
		int index = tAddressServiceImpl.delByRid(rid);
		if(index>0){
			return "yes";
		}
		return "no";
	}

	/**
	 * 根据id查询该地址信息
	 * @param rid 地址id
	 * @return
	 */
	@RequestMapping("address-edit.html")
	public ModelAndView selByRid(Integer rid,HttpServletRequest request){
		ModelAndView mv=new ModelAndView();
		TAddress address = tAddressServiceImpl.selByRid(rid);
		mv.addObject("selByRid",address);
		mv.setViewName("user/address-edit");
		return mv;
	}

	/**
	 * 根据id更新该地址信息
	 * @param tAddress 地址信息
	 * @return
	 */
	@RequestMapping(value="updAddress.action",produces="text/html;charset=utf-8")
	@ResponseBody
	public String updAddress(TAddress tAddress){
        tAddress.setAdded(new Date());
		int index = tAddressServiceImpl.updByRid(tAddress);
		if(index>0){
			return "yes";
		}
		return "no";
	}
	
	/**
	 * 这个控制器只负责用户还没登录时，想添加地址时，对其拦截的功能
	 * 因为@ResponseBody恒不跳转
	 * @return
	 */
	@RequestMapping("no_login_address.action")
	public String no_login_address(){
		return "";
	}
	
}
