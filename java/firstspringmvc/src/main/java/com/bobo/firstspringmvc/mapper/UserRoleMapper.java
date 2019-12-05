package com.bobo.firstspringmvc.mapper;

import java.util.List;

import com.bobo.firstspringmvc.bean.UserRole;

public interface UserRoleMapper {

	List<UserRole> getAllUserRoles();
	UserRole getUserRoleById(String id);
	List<UserRole> searchUserRole(UserRole sample);
	void addUserRole(UserRole userRole);
	void updateUserRole(UserRole userRole);
	void deleteUserRole(String id);
}
