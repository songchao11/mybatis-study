package com.song.mybatis.dao;

import java.util.List;

import com.song.mybatis.bean.Employee;

public interface EmployeeMapper {
	Employee getEmpById(Integer id);
	void addEmp(Employee employee);
	void updateEmp(Employee employee);
	void deleteEmpById(Integer id);
	Employee selectEmployee(Integer id);
	Employee selectEmpByStep(Integer id);
	List<Employee> selectEmps(Integer did);
	List<Employee> selectEmps1(Integer did, String gender);
	List<Employee> selectEmployees();
}
