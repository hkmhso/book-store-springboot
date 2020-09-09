package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TRecommended;
import com.sprjjs.book.pojo.TRecommendedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TRecommendedMapper {
    int countByExample(TRecommendedExample example);

    int deleteByExample(TRecommendedExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(TRecommended record);

    int insertSelective(TRecommended record);

    List<TRecommended> selectByExample(TRecommendedExample example);

    TRecommended selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") TRecommended record, @Param("example") TRecommendedExample example);

    int updateByExample(@Param("record") TRecommended record, @Param("example") TRecommendedExample example);

    int updateByPrimaryKeySelective(TRecommended record);

    int updateByPrimaryKey(TRecommended record);
}