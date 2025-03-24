package com.example.SpringAPI.services;

import redis.clients.jedis.Jedis;

public class RedisTest {
    public static void main(String[] args) {
        String redisHost = "redis-19681.c83.us-east-1-2.ec2.redns.redis-cloud.com";
        int redisPort = 19681;
        String redisPassword = "Jy7*MbcmkQx$ph-";  // Replace with your actual password

        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            jedis.auth("adhi",redisPassword);  // Authenticate with Redis
            System.out.println("Connected: " + jedis.ping());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
