package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TOrder;
import com.sprjjs.book.pojo.TOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOrderMapper {
    int countByExample(TOrderExample example);

    int deleteByExample(TOrderExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    List<TOrder> selectByExample(TOrderExample example);

    TOrder selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") TOrder record, @Param("example") TOrderExample example);

    int updateByExample(@Param("record") TOrder record, @Param("example") TOrderExample example);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);
}