/**
 * JedisConfig.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月5日
 */

package com.evmtv.jedis.config;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class JedisConfig {
	
	
	String host = "192.168.146.128";
	int port = 6379;
	
	public Jedis getJedis() {
		
		Jedis jedis = new Jedis(host,port);
		
		return jedis;
	}
}
