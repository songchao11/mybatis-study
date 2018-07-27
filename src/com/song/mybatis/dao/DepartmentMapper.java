package com.song.mybatis.dao;

import com.song.mybatis.bean.Department;

public interface DepartmentMapper {
	Department selectDept(Integer id);
	Department selectDeptPlus(Integer id);
	Department selectDeptByStep(Integer id);
}
