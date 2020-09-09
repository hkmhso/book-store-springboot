package com.sprjjs.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sprjjs.book.mapper.TBookMapper;
import com.sprjjs.book.mapper.TCollectMapper;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TCollect;
import com.sprjjs.book.pojo.TCollectExample;
import com.sprjjs.book.service.TCollectionService;

@Service
public class TCollectionServiceImpl implements TCollectionService{
	@Resource
	private TCollectMapper tCollectMapper;

	@Resource
	private TBookMapper tBookMapper;

	@Override
	public int insCollect(TCollect col) {
		return tCollectMapper.insertSelective(col);
	}

	@Override
	public List<TCollect> selAllByUid(String uid) {
		TCollectExample example=new TCollectExample();
		example.createCriteria().andUidEqualTo(uid);
		List<TCollect> cols = tCollectMapper.selectByExample(example);
		return cols;
	}

	@Override
	public TCollect selByUidIsbn(String uid, String isbn) {
		TCollectExample example = new TCollectExample();
		example.createCriteria().andUidEqualTo(uid).andBookEqualTo(isbn);
		List<TCollect> list = tCollectMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public int delColl(TCollect coll) {
		TCollectExample example = new TCollectExample();
		example.createCriteria().andUidEqualTo(coll.getUid()).andBookEqualTo(coll.getBook());
		return tCollectMapper.deleteByExample(example);
	}

	@Override
	public boolean isColl(TCollect coll) {
		TCollect coll1 = selByUidIsbn(coll.getUid(),coll.getBook());
		if(coll1!=null) {
			return true;
		}
		return false;
	}

	/**
	 * 使用PageHelper进行分页操作时，需要注意的点：
	 * 		PS:要想对原来的集合对象进行业务装配成另一个集合时，步骤为：
	 * 			第一步： 必须先通过原来的集合创建PageInfo对象(也就是在创建PageInfo对象时，
	 * 				      对原来的集合（查询所有的结果集）先不要进行装配成其他的集合。
	 * 			第二步：因为这样原来的集合类型才是Page类型，才能获取到分页的那些参数。
	 * 			第三步：然后再通过PageInfe的setList()方法重新设置为装配好的其他集合。
	 * 			第四步：这样子才能既可以获取到分页信息，又可以将结果集设置为装配好的其他集合。
	 */
	@Override
	public PageInfo showPage(Integer pageNum, Integer pageSize,String uid) {
		//分页操作的原理是：在select * from xxxx where xxx=xxx and xxx=xxx ... 后面拼上limit pageNum,pageSize
		//创建分页查询条件，必须写在查询语句前面
		PageHelper.startPage(pageNum, pageSize);
		/*[第一步]在创建PageInfo对象时对原来的集合（查询所有的结果集）
		     先不要进行装配成其他的集合。
		*/
		List<TCollect> cols=selAllByUid(uid);
		PrintUtil.print(this.getClass(), "->>showPage()-->cols的类型："+cols.getClass());
		PrintUtil.print(this.getClass(), "->>showPage()-->查询到的数据为："+cols);
		//PageInfo中包含了分页操作后所有的分页信息（页码,总页数，总记录数，当前页所显示的行数，当前页的数据）
		PageInfo pageInfo=new PageInfo<>(cols);
		//[第三步]然后再通过PageInfe的setList()方法重新设置为装配好的其他集合。
		pageInfo.setList(assemble(cols));
		PrintUtil.print(this.getClass(), "->>showPage()-->pageInfo中的list属性的类型："+pageInfo.getList().getClass());
		PrintUtil.print(this.getClass(), "->>showPage()->>分页信息:"+pageInfo);
		return pageInfo;
	}

	/**
	 * 业务装配
	 * @return
	 */
	public List<TBook> assemble(List<TCollect> cols){
		List<TBook> books=new ArrayList<>();
		for(TCollect col:cols){
			books.add(tBookMapper.selectByPrimaryKey(col.getBook()));
		}
		return books;
	}

}
