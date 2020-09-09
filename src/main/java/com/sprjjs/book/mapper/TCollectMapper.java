package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TCollect;
import com.sprjjs.book.pojo.TCollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCollectMapper {
    int countByExample(TCollectExample example);

    int deleteByExample(TCollectExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(TCollect record);

    int insertSelective(TCollect record);

    List<TCollect> selectByExample(TCollectExample example);

    TCollect selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") TCollect record, @Param("example") TCollectExample example);

    int updateByExample(@Param("record") TCollect record, @Param("example") TCollectExample example);

    int updateByPrimaryKeySelective(TCollect record);

    int updateByPrimaryKey(TCollect record);
}