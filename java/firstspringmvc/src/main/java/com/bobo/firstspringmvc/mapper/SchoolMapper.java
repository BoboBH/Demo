package com.bobo.firstspringmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bobo.firstspringmvc.bean.School;

@Mapper
public interface SchoolMapper {

	public List<School> getSchools();
	public School getSchoolById(String id);
	public List<School> searchSchool(School sample);
	public void addSchool(School school);
	public void updateSchool(School school);
}
