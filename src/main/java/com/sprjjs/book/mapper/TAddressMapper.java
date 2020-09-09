package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TAddress;
import com.sprjjs.book.pojo.TAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAddressMapper {
    int countByExample(TAddressExample example);

    int deleteByExample(TAddressExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(TAddress record);

    int insertSelective(TAddress record);

    List<TAddress> selectByExample(TAddressExample example);

    TAddress selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") TAddress record, @Param("example") TAddressExample example);

    int updateByExample(@Param("record") TAddress record, @Param("example") TAddressExample example);

    int updateByPrimaryKeySelective(TAddress record);

    int updateByPrimaryKey(TAddress record);
}