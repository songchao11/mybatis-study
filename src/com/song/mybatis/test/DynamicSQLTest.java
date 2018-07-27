package com.song.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.song.mybatis.bean.Department;
import com.song.mybatis.bean.Employee;
import com.song.mybatis.dao.EmployeeMapperDynamicSQL;

public class DynamicSQLTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis-conf.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		return sqlSessionFactory;
	}
	
	@Test
	public void test1() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL empMapperDynamic = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee emp = new Employee(1, "zhang", "ÄÐ", null);
			List<Employee> emps = empMapperDynamic.getEmpsByConditionIf(emp);
			System.out.println(emps);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test2() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL empMapperDynamic = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee emp = new Employee(null, "zhang", "ÄÐ", null);
			List<Employee> emps = empMapperDynamic.getEmpsByConditionTrim(emp);
			System.out.println(emps);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test3() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL empMapperDynamic = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee emp = new Employee(null, null, null, null);
			List<Employee> emps = empMapperDynamic.getEmpsByConditionChoose(emp);
			System.out.println(emps);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test4() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL empMapperDynamic = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee emp = new Employee(1, "kaige", null, null);
			empMapperDynamic.updateEmpByCondition(emp);
			sqlSession.commit();
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test5() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL empMapperDynamic = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(1);
			ids.add(3);
			ids.add(5);
			List<Employee> emps = empMapperDynamic.getEmpsByConditionForeach(ids);
			System.out.println(emps);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void test6() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL empMapperDynamic = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Employee> emps = new ArrayList<Employee>();
			emps.add(new Employee(null, "durant", "ÄÐ", "durant@qq.com", new Department(1)));
			emps.add(new Employee(null, "harden", "ÄÐ", "harden@qq.com", new Department(2)));
			emps.add(new Employee(null, "white", "ÄÐ", "white@qq.com", new Department(1)));
			empMapperDynamic.insertEmps(emps);
			sqlSession.commit();
		}finally{
			sqlSession.close();
		}
	}
	
}
