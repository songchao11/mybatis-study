package com.song.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.song.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {
	List<Employee> getEmpsByConditionIf(Employee employee);
	List<Employee> getEmpsByConditionTrim(Employee employee);
	List<Employee> getEmpsByConditionChoose(Employee employee);
	void updateEmpByCondition(Employee employee);
	List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);
	void insertEmps(@Param("emps") List<Employee> emps);
}
