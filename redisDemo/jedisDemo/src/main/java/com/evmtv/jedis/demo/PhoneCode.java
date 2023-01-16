package com.evmtv.jedis.demo;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
	
	static String host = "192.168.146.128";
	
    public static void main(String[] args) {
        //模拟验证码发送
        String vcode = verifyCode("13678765435");
        if(vcode != null) {
        	 //模拟验证码校验
            getRedisCode("13678765435",vcode);
        }
    }

    //3 验证码校验
    public static void getRedisCode(String phone,String code) {
        //从redis获取验证码
        Jedis jedis = new Jedis(host,6379);
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";
        String redisCode = jedis.get(codeKey);
        //判断
        if(redisCode.equals(code)) {
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
        jedis.close();
    }

    //2 每个手机每天只能发送三次，验证码放到redis中，设置过期时间120
    public static String verifyCode(String phone) {
        //连接redis
        Jedis jedis = new Jedis(host,6379);

        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode"+phone+":count";
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";

        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if(count == null) {
            //没有发送次数，第一次发送
            //设置发送次数是1
            jedis.setex(countKey,24*60*60,"1");
        } else if(Integer.parseInt(count)<=2) {
            //发送次数+1
            jedis.incr(countKey);
            System.out.println("current countKey " + (Integer.valueOf(count) + 1));
        } else if(Integer.parseInt(count)>2) {
            //发送三次，不能再发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
            return null;
        }

        //发送验证码放到redis里面
        String vcode = getCode();
        System.out.println(vcode);
        
        jedis.setex(codeKey,120,vcode);
        jedis.close();
        
        return vcode;
    }

    //1 生成6位数字验证码
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for(int i=0;i<6;i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }
}