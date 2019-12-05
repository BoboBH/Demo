package com.bobo.fristsba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.bobo.fristsba.domain.Role;

/*
 * Author:bobo.huang
 * Role Mapper:
 * support sql and classpath:mapper/*.mapper.xml;
 * need configuration in application.properties
	mybatis.typeAliasesPackage=com.bobo.fristsba.domain
	mybatis.mapperLocations=classpath:mapper/*.mapper.xml
 */
@Mapper
public interface RoleMapper {
	@Select("SELECT a.id as id,a.name as name FROM role a inner join user_role b on a.id = b.role_id where b.user_id = #{userId} ")
	@Results({
	    })
	List<Role> getRolesByUserId(String userId);
	List<Role> searchRole(Role sample);
	Role getRoleById(String id);
	void addRole(Role role);
	void updateRole(Role role);
	void deleteRole(String id);
}
