package com.bobo.mapper;

import com.bobo.bean.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface UserMapper {
	
	//@Select("SELECT * FROM tb_user WHERE id = #{id}")
	public User getUserById(int id);

	//@Select("SELECT * FROM tb_user")
	public List<User> getUserList();
	
	//@Insert("insert into tb_user(username, age,grade,ctm) values(#{username},#{age},#{grade}, now())")
	public int add(User user);
	
//	@Update("update tb_user set username=#{username},age=#{age} where id=#{id}")
//	public int update(User user);
	
	//@Update("update tb_user set username=#{user.username},age=#{user.age},grade=#{user.grade} where id=#{id}")
	public int update(@Param("id") Integer id, @Param("user") User user);
	
	//@Delete("delete from tb_user where id=#{id}")
	public int delete(int id);
}
