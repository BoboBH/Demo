package com.bobo.mybatis.bean;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.bobo.mybatis.bean.UserMessage;
@Mapper
public interface UserMessageMapper {

	@Select("select * from user_message where id=#{id}")
	public  List<UserMessage> getUserMessageById(@Param("id") int id);
	
	@Insert("insert into user_message(id, message) values(#{id, jdbcType=INTEGER}, #{message,jdbcType=VARCHAR})")
	public void save(UserMessage userMessage);
}
