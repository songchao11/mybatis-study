package com.song.mybatis.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.song.mybatis.tools.SerializableTools;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	/*
	 * ��ʼ��jedis
	 */
	private Jedis jedis = new Jedis("127.0.0.1",6379);
	
	/*
	 * Mybatis���ӳ���ļ��������ռ���ΪΨһ��ʶcacheId����ʶ����������������һ��namespace
	 * 
	 * ���ﶨ��ã����ṩһ������������ʼ�����cacheId����
	 */
	private String cacheId;
	
	public RedisCache(String cacheId){
		this.cacheId = cacheId;
	}
	
	public String getId() {
		return cacheId;
	}

	/*
	 * Mybatis�ڶ�ȡ����ʱ�����Զ����ô˷���
	 * ���������õ������С�������д��Redis
	 */
	public void putObject(Object key, Object value) {
		try {
			jedis.set(SerializableTools.ObjToByteArray(key), SerializableTools.ObjToByteArray(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Mybatis���Զ��������������⻺�����Ƿ���ڸö���
	 * ��Ȼ���Լ�ʵ�ֵĻ��棬��ô��Ȼ����Redis��ȥ�ҡ�
	 */
	public Object getObject(Object key) {
		try {
			byte[] bt = jedis.get(SerializableTools.ObjToByteArray(key));
			if(bt == null){
				//���û��������󣬾�ֱ�ӷ���null
				return null;
			}
			return SerializableTools.byteArrayToObj(bt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Mybatis������Ի��Զ�����ڴ�Ĵ�С���ɴ˾����Ƿ�ɾ�������е�ĳЩ����
	 */
	public Object removeObject(Object key) {
		Object obj = getObject(key);
		try {
			jedis.del(SerializableTools.ObjToByteArray(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/*
	 * ��ջ���
	 */
	public void clear() {
		// �������������ʵ��
		jedis.flushAll();
	}

	public int getSize() {
		return Integer.parseInt(Long.toString(jedis.dbSize()));
	}

	public ReadWriteLock getReadWriteLock() {
		return new ReentrantReadWriteLock();
	}

}
