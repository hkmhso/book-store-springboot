package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TCart;
import com.sprjjs.book.pojo.TCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCartMapper {
    int countByExample(TCartExample example);

    int deleteByExample(TCartExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(TCart record);

    int insertSelective(TCart record);

    List<TCart> selectByExample(TCartExample example);

    TCart selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") TCart record, @Param("example") TCartExample example);

    int updateByExample(@Param("record") TCart record, @Param("example") TCartExample example);

    int updateByPrimaryKeySelective(TCart record);

    int updateByPrimaryKey(TCart record);
}