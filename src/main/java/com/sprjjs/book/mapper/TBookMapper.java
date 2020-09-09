package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TBook;
import com.sprjjs.book.pojo.TBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBookMapper {
    int countByExample(TBookExample example);

    int deleteByExample(TBookExample example);

    int deleteByPrimaryKey(String isbn);

    int insert(TBook record);

    int insertSelective(TBook record);

    List<TBook> selectByExample(TBookExample example);

    TBook selectByPrimaryKey(String isbn);

    int updateByExampleSelective(@Param("record") TBook record, @Param("example") TBookExample example);

    int updateByExample(@Param("record") TBook record, @Param("example") TBookExample example);

    int updateByPrimaryKeySelective(TBook record);

    int updateByPrimaryKey(TBook record);
}