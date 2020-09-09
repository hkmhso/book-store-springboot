package com.sprjjs.book.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.sprjjs.book.service.TBookService;
import com.sprjjs.book.mapper.TBookMapper;
import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TBookExample;
import com.sprjjs.book.utils.PrintUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TBookServiceImpl implements TBookService {
	@Resource
	private TBookMapper tBookMapper;

	@Override
	public TBook selByisbn(String isbn) {
		TBookExample example=new TBookExample();
		example.createCriteria().andIsbnEqualTo(isbn);
		List<TBook> list = tBookMapper.selectByExample(example);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public List<TBook> selByTitle(String title) {
		TBookExample example=new TBookExample();
		example.createCriteria().andTitleLike("%"+title+"%");
		return tBookMapper.selectByExample(example);
	}

	@Override
	public List<TBook> selAll() {
		return tBookMapper.selectByExample(new TBookExample());
	}

	@Override
	public int updStatus(String isbn, Integer status) {
		TBookExample example=new TBookExample();
		example.createCriteria().andIsbnEqualTo(isbn);
		TBook tBook=new TBook();
		tBook.setIsbn(isbn);
		tBook.setStatus(status);
		return tBookMapper.updateByPrimaryKeySelective(tBook);
	}

	@Override
	public List<TBook> selByCondition(String title) {
		/**
		 * 思路：条件不为空时，往where语句拼接条件即可，否则不拼接,模糊查询
		 */
		TBookExample example = new TBookExample();
		example.createCriteria().andTitleLike("%"+title+"%");
		return tBookMapper.selectByExample(example);
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
	public PageInfo showPage(Integer pageNum, Integer pageSize, String title) {
		//分页操作的原理是：在select * from xxxx where xxx=xxx and xxx=xxx ... 后面拼上limit pageNum,pageSize
		//创建分页查询条件，必须写在查询语句前面
		PageHelper.startPage(pageNum, pageSize);
		List<TBook> list=null;
		//[1]当没有条件时，就查询全部
		if(title==null){
			list=selAll();
		}
		//[2]当至少有一个条件时，就按照条件查询
		else{
			list=selByCondition(title);
		}
		PrintUtil.print(this.getClass(), "->>showPage()-->list的类型为："+list.getClass());
		PrintUtil.print(this.getClass(), "->>showPage()-->查询到的数据为："+list);
		//PageInfo中包含了分页操作后所有的分页信息（页码,总页数，总记录数，当前页所显示的行数，当前页的数据）
		PageInfo<TBook> pageInfo=new PageInfo<>(list);
		PrintUtil.print(this.getClass(), "->>showPage()->>分页信息:"+pageInfo);
		return pageInfo;
	}
}
