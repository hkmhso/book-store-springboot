package com.sprjjs.book.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sprjjs.book.mapper.TAddressMapper;
import com.sprjjs.book.pojo.TAddress;
import com.sprjjs.book.pojo.TAddressExample;
import com.sprjjs.book.service.TAddressService;

@Service
public class TAddressServiceImpl implements TAddressService{
	@Resource
	private TAddressMapper tAddressMapper;
	
	@Override
	public int insAddress(TAddress tAddress) {
		return tAddressMapper.insert(tAddress);
	}
	
	@Override
	public List<TAddress> showAddress(String uid) {
		TAddressExample example=new TAddressExample();
		example.createCriteria().andUidEqualTo(uid);
		return tAddressMapper.selectByExample(example);
	}
	
	@Override
	public TAddress selAddress(TAddress tAddress) {
		TAddressExample example=new TAddressExample();
		example.createCriteria().andRidEqualTo(tAddress.getRid()).andUidEqualTo(tAddress.getUid());
		List<TAddress> list = tAddressMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public int delByRid(Integer rid) {
		return tAddressMapper.deleteByPrimaryKey(rid);
	}

	@Override
	public TAddress selByRid(Integer rid) {
		return tAddressMapper.selectByPrimaryKey(rid);
	}

	@Override
	public int updByRid(TAddress address) {
		return tAddressMapper.updateByPrimaryKeySelective(address);
	}

}
