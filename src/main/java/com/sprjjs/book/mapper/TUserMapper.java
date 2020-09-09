package com.sprjjs.book.mapper;

import com.sprjjs.book.pojo.TUser;
import com.sprjjs.book.pojo.TUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserMapper {
    int countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int deleteByPrimaryKey(String phone);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);

    TUser selectByPrimaryKey(String phone);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}