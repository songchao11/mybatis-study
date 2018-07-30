package com.song.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.song.mybatis.bean.Department;
import com.song.mybatis.bean.Employee;
import com.song.mybatis.dao.DepartmentMapper;
import com.song.mybatis.dao.EmployeeMapper;

public class MybatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis-conf.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		return sqlSessionFactory;
	}

	/*
	 * 1.根据xml配置文件创建一个SqlSessionFactory对象
	 * 
	 */
	@Test
	public void  test() throws IOException{
		
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		//获取sqlSession实例,能直接执行已经映射的sql语句
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			Employee emp = sqlSession.selectOne("com.song.mybatis.dao.EmployeeMapper.getEmpById", 1);
			System.out.println(emp);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test1() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
		Employee emp = employeeMapper.getEmpById(1);
		System.out.println(emp);
		sqlSession.close();
		
	}
	
	@Test
	public void testInsert() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = new Employee(null, "Rose", "男", "rose@qq.com");
			employeeMapper.addEmp(employee);
			System.out.println(employee);
		}finally{
			sqlSession.close();
		}
		
	}
	
	@Test
	public void testUpdate() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = new Employee(1, "lionkk", "男", "lionkk@qq.com");
			employeeMapper.updateEmp(employee);
			sqlSession.commit();
		}finally{
			sqlSession.close();
		}
		
	}
	
	@Test
	public void testDelete() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			employeeMapper.deleteEmpById(4);
			sqlSession.commit();
		}finally{
			sqlSession.close();
		}
		
	}
	@Test
	public void test3() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee emp = empMapper.selectEmployee(1);
			System.out.println(emp);
			System.out.println(emp.getDept());
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test4() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee emp = empMapper.selectEmpByStep(1);
			System.out.println(emp);
			System.out.println(emp.getDept());
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test5() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			DepartmentMapper deptMapper = sqlSession.getMapper(DepartmentMapper.class);
			Department dept = deptMapper.selectDept(1);
			System.out.println(dept);
			
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test6() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			DepartmentMapper deptMapper = sqlSession.getMapper(DepartmentMapper.class);
			Department dept = deptMapper.selectDeptPlus(1);
			System.out.println(dept);
			System.out.println(dept.getEmps());
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test7() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			DepartmentMapper deptMapper = sqlSession.getMapper(DepartmentMapper.class);
			Department dept = deptMapper.selectDeptByStep(1);
			System.out.println(dept.getDepartmentName());
			System.out.println(dept.getEmps());
		}finally{
			sqlSession.close();
		}
		
	}
	
}
