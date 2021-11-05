package com.grtids.gateway.util;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author daiqingsong
 * @Date 2021/10
 **/
@Component
public class RedisUtils {
	// jedis连接池
	private static JedisPool jedisPool;
	@Autowired
	private JedisPool jedisPoolTemp;

	@PostConstruct
	private void beforeInit() {
		jedisPool = this.jedisPoolTemp;
	}

	/**
	 * 存入一组键值对，设定 expireSecond过期时间，单位：s
	 *
	 * @param key
	 * @param value
	 * @param expireSecond
	 * @throws Exception
	 */
	public static Long addPair(String key, String value, int expireSecond) throws Exception {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			result = jedis.expire(key, expireSecond);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	/*
	 * public static void main(String[] args) { RedisUtils.addPair("ghgggg",
	 * "sdfsfsf", 60); System.out.println(RedisUtils.getPair("ghgggg")); }
	 */
	/**
	 * 删除某键值对
	 *
	 * @param key
	 * @throws AppException
	 * @throws Exception
	 */
	public static Long delPair(String key) throws Exception {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.del(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 通过key从redis缓存中取出value
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getPair(String key) throws Exception {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 判断是否存在某键值对
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Boolean pairIsExist(String key) throws Exception {
		Jedis jedis = null;
		Boolean result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 将某个值，加入到以key为键的set集合中 使用redis的set集合
	 *
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Long addSet(String key, String value) throws Exception {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.sadd(key, value);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 从redis的set集合中移除value
	 *
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static Long delFromSet(String key, String value) throws Exception {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.srem(key, value);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 判断set集合中是否包含value
	 *
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Boolean isInSet(String key, String value) throws Exception {
		Boolean InSet = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			InSet = jedis.sismember(key, value);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return InSet;
	}

	public static Boolean exists(String key) throws Exception {
		Jedis jedis = null;
		Boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}

	public static String setex(String key, int seconds, String value) throws Exception {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.setex(key, seconds, value);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// 返还到连接池
				jedis.close();
			}
		}
		return result;
	}
}
