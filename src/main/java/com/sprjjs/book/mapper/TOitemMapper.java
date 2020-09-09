package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TOitem;
import com.sprjjs.book.pojo.TOitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOitemMapper {
    int countByExample(TOitemExample example);

    int deleteByExample(TOitemExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(TOitem record);

    int insertSelective(TOitem record);

    List<TOitem> selectByExample(TOitemExample example);

    TOitem selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") TOitem record, @Param("example") TOitemExample example);

    int updateByExample(@Param("record") TOitem record, @Param("example") TOitemExample example);

    int updateByPrimaryKeySelective(TOitem record);

    int updateByPrimaryKey(TOitem record);
}