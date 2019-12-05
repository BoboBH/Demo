package com.bobo.firstspringmvc.mapper;

import java.util.List;

import com.bobo.firstspringmvc.bean.Role;

public interface RoleMapper {
	List<Role> getAllRoles();
	Role getRoleById(String id);
	List<Role> searchRole(Role sample);
	void addRole(Role role);
	void updateRole(Role role);
	void deleteRole(String id);
	List<Role> getRolesByUserId(String userId);
}
