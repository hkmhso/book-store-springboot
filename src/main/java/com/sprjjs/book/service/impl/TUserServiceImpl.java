package com.sprjjs.book.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sprjjs.book.mapper.TUserMapper;
import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.pojo.TUserExample;
import com.sprjjs.book.service.TUserService;
@Service
public class TUserServiceImpl implements TUserService {
	@Resource
	private TUserMapper tUserMapper;
	
	@Override
	public TUser selByName(String uname) {
		TUserExample example = new TUserExample();
		example.createCriteria().andUnameEqualTo(uname);
		List<TUser> list = tUserMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	@Override
	public TUser login(TUser tuser) {
		TUserExample example=new TUserExample();
		example.createCriteria().andUnameEqualTo(tuser.getUname()).andUpwdEqualTo(tuser.getUpwd());
		List<TUser> list = tUserMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	@Override
	public TUser selByEmail(String email) {
		TUserExample example=new TUserExample();
		example.createCriteria().andEmailEqualTo(email);
		List<TUser> list = tUserMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	@Override
	public TUser selByPhone(String phone) {
		TUserExample example=new TUserExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TUser> list = tUserMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	@Override
	public int insTUser(TUser tuser) {
		return tUserMapper.insertSelective(tuser);
	}
	
	@Override
	public TUser selByUpwd(TUser tUser) {
		TUserExample example=new TUserExample();
		example.createCriteria().andUnameEqualTo(tUser.getUname()).andUpwdEqualTo(tUser.getUpwd());
		List<TUser> list = tUserMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public int updUpwd(TUser tUser) {
		TUserExample example=new TUserExample();
		example.createCriteria().andUnameEqualTo(tUser.getUname());
		int index = tUserMapper.updateByExampleSelective(tUser,example);
		return index;
	}
	
	
}
