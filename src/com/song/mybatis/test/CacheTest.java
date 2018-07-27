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
	 * �������棺
	 * һ������(���ػ���):SqlSession����Ļ��棬һ��������һֱ�����ġ�SqlSession�����һ��Map
	 * 		�����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ�ŵ����ػ����У�
	 * 		�Ժ����Ҫ��ȡ��ͬ�����ݣ�ֱ�Ӵӻ������ã�û��Ҫ��ȥ��ѯ���ݿ⣻
	 * 
	 * 		һ������ʧЧ�����(û��ʹ�õ�һ������������Ч�����ǻ���Ҫ�����ݿⷢ����ѯ):
	 * 		1.SqlSession��ͬ��
	 * 		2.SqlSession��ͬ����ѯ������ͬ(��ǰһ�������л�û���������)
	 * 		3.SqlSession��ͬ�����β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)
	 * 		4.SqlSession��ͬ���ֶ������һ������(�������)
	 * 
	 * ��������(ȫ�ֻ���):����namespace����Ļ��棬һ��namespace��Ӧһ����������
	 * 		�������ƣ�
	 * 		1.һ���Ự����ѯһ�����ݣ�������ݾͻᱻ���ڵ�ǰ�Ự��һ��������
	 * 		2.����Ự�رգ�һ�������е����ݻᱻ���浽���������У��µĻỰ��ѯ��Ϣ���ͻ���ն�������
	 * 		3.��ͬ��namespace��ѯ�������ݻ�����Լ���Ӧ�Ļ�����(Map)
	 * 
	 * 		Ч�������ݻ�Ӷ��������л�ȡ
	 * 			��������ݶ����ȱ��ŵ�һ�������С�
	 * 			ֻ�лỰ�ύ���߹ر��Ժ�һ�������е����ݲŻ�ת�Ƶ�����������
	 * 
	 * 		ʹ�ã�
	 * 		1.����ȫ�ֶ�����������:<setting name="cacheEnabled" value="true"/>
	 * 		2.��mapper.xml ������ʹ�ö�������
	 * 		3.���ǵ�POJO����Ҫʵ�����л��ӿ�
	 * 
	 * 	�ͻ����йص�����/����:
	 * 		1.cacheEnabled=true / false :�رջ��棨��������رգ�һ������һֱ���õģ�
	 * 		2.ÿ��select��ǩ����useCache="true"
	 * 				false:��ʹ�û��棨һ��������Ȼʹ�ã��������治ʹ�ã�
	 * 		3.ÿ����ɾ�ı�ǩ�ģ�flushCache="true"Ĭ����true
	 * 			��ɾ�Ĳ���ִ��֮��ͻ�������棨һ���������涼�ᱻ�����
	 * 			��ѯ��ǩflushCacheĬ����false
	 * 		4.sqlSession.clearCache();ֻ�������ǰsession��һ������
	 * 		5.localCacheScope:���ػ��������򣺣�һ������SESSION��;��ǰ�Ự���������ݱ����ڻỰ�����У�
	 * 				STATEMENT:���Խ���һ�����棻
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
