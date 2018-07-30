package com.song.mybatis.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.song.mybatis.tools.SerializableTools;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	/*
	 * 初始化jedis
	 */
	private Jedis jedis = new Jedis("127.0.0.1",6379);
	
	/*
	 * Mybatis会把映射文件的命名空间作为唯一标识cacheId，标识这个缓存策略属于哪一个namespace
	 * 
	 * 这里定义好，并提供一个构造器，初始化这个cacheId即可
	 */
	private String cacheId;
	
	public RedisCache(String cacheId){
		this.cacheId = cacheId;
	}
	
	public String getId() {
		return cacheId;
	}

	/*
	 * Mybatis在读取数据时，会自动调用此方法
	 * 将数据设置到缓存中。这里是写入Redis
	 */
	public void putObject(Object key, Object value) {
		try {
			jedis.set(SerializableTools.ObjToByteArray(key), SerializableTools.ObjToByteArray(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Mybatis会自动调用这个方法检测缓存中是否存在该对象。
	 * 既然是自己实现的缓存，那么当然是在Redis中去找。
	 */
	public Object getObject(Object key) {
		try {
			byte[] bt = jedis.get(SerializableTools.ObjToByteArray(key));
			if(bt == null){
				//如果没有这个对象，就直接返回null
				return null;
			}
			return SerializableTools.byteArrayToObj(bt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Mybatis缓存策略会自动检测内存的大小，由此决定是否删除缓存中的某些数据
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
	 * 清空缓存
	 */
	public void clear() {
		// 这个方法不建议实现
		jedis.flushAll();
	}

	public int getSize() {
		return Integer.parseInt(Long.toString(jedis.dbSize()));
	}

	public ReadWriteLock getReadWriteLock() {
		return new ReentrantReadWriteLock();
	}

}
