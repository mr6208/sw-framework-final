package com.swfinal.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginMapper {
    Map<String, Object> selectMemberInfo(Map<String, Object> parmas);

}
