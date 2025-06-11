package com.swfinal.register.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface RegisterMapper {
    int insertMember(Map<String, Object> param);

    int selectMemberDuplicateCount(Map<String, Object> parma);

    Map<String, Object> selectMemberInfo(Map<String, Object> parmas);

    int deleteMember(Map<String, Object> params);
}
