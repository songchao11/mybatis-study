package com.song.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.song.mybatis.bean.Employee;
import com.song.mybatis.dao.EmployeeMapper;

public class CacheTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis-conf.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		return sqlSessionFactory;
	}
	
	/*
	 * 两级缓存：
	 * 一级缓存(本地缓存):SqlSession级别的缓存，一级缓存是一直开启的。SqlSession级别的一个Map
	 * 		与数据库同一次会话期间查询到的数据会放到本地缓存中，
	 * 		以后如果要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
	 * 
	 * 		一级缓存失效的情况(没有使用到一级缓存的情况，效果就是还需要向数据库发出查询):
	 * 		1.SqlSession不同。
	 * 		2.SqlSession相同，查询条件不同(当前一级缓存中还没有这个数据)
	 * 		3.SqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
	 * 		4.SqlSession相同，手动清除了一级缓存(缓存清空)
	 * 
	 * 二级缓存(全局缓存):基于namespace级别的缓存，一个namespace对应一个二级缓存
	 * 		工作机制：
	 * 		1.一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中
	 * 		2.如果会话关闭，一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就会参照二级缓存
	 * 		3.不同的namespace查询出的数据会放在自己对应的缓存中(Map)
	 * 
	 * 		效果：数据会从二级缓存中获取
	 * 			查出的数据都会先被放到一级缓存中。
	 * 			只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
	 * 
	 * 		使用：
	 * 		1.开启全局二级缓存配置:<setting name="cacheEnabled" value="true"/>
	 * 		2.在mapper.xml 中配置使用二级缓存
	 * 		3.我们的POJO类需要实现序列化接口
	 * 
	 * 	和缓存有关的设置/属性:
	 * 		1.cacheEnabled=true / false :关闭缓存（二级缓存关闭，一级缓存一直可用的）
	 * 		2.每个select标签都有useCache="true"
	 * 				false:不使用缓存（一级缓存依然使用，二级缓存不使用）
	 * 		3.每个增删改标签的：flushCache="true"默认是true
	 * 			增删改操作执行之后就会清除缓存（一级二级缓存都会被清除）
	 * 			查询标签flushCache默认是false
	 * 		4.sqlSession.clearCache();只是清除当前session的一级缓存
	 * 		5.localCacheScope:本地缓存作用域：（一级缓存SESSION）;当前会话的所有数据保存在会话缓存中；
	 * 				STATEMENT:可以禁用一级缓存；
	 * 
	 */
	@Test
	public void testFirstLevelCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee emp1 = empMapper.getEmpById(1);
			System.out.println(emp1);
			Employee emp2 = empMapper.getEmpById(1);
			System.out.println(emp2);
			System.out.println(emp1 == emp2);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void testSecondLevelCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper1 = sqlSession1.getMapper(EmployeeMapper.class);
			Employee emp1 = mapper1.getEmpById(1);
			System.out.println(emp1);
//			sqlSession1.close();
			EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
			Employee emp2 = mapper2.getEmpById(1);
			System.out.println(emp2);
//			sqlSession2.close();
		}finally{
			sqlSession1.close();
			sqlSession2.close();
		}
		
	}
	
}
