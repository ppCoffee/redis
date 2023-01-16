/**
 * JedisTest.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月5日
 */

package com.evmtv.jedis;


import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.evmtv.jedis.config.JedisConfig;

import redis.clients.jedis.Jedis;

@SpringBootTest
class JedisTest {
	
	@Resource
	JedisConfig jedisConfig;
	
	@Test
	void test() {
		
		Jedis jedis = jedisConfig.getJedis();
		String ping = jedis.ping();
		System.out.println(ping);
		jedis.close();
	}

}
