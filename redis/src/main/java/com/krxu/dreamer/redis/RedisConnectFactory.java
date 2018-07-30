package com.krxu.dreamer.redis;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.Pool;

import java.util.*;

public class RedisConnectFactory {

    private String connectUrls;

    private String password;

    private JedisPoolConfig config;

    private Pool<ShardedJedis> pool;

    /**
     * spring 初始化
     */
    public void init() {
        String[] connectUrlArray = connectUrls.split(",");
        int sentinelCount = 0;
        int clusterCount = 0;
        for (String connectUrl : connectUrlArray) {
            String ip = connectUrl.split("\\:")[0];
            String port = connectUrl.split("\\:")[1];
            Jedis jedis = null;
            try {
                jedis = new Jedis(ip, Integer.valueOf(port));
                if (null != password && password.trim().length() > 0) {
                    jedis.auth(password);
                }
                String sentinelInfo = jedis.info("Sentinel");
                if (null != sentinelInfo && sentinelInfo.trim().length() > 0) {
                    sentinelCount++;
                } else {
                    clusterCount++;
                }
            } finally {
                jedis.close();
            }
        }
        if (sentinelCount != connectUrlArray.length || clusterCount != connectUrlArray.length) {
            throw new RuntimeException("redis配置错误");
        }
        //哨兵模式 或 集群模式
        if (sentinelCount > 0) {
            Set<String> sentinels = new HashSet(Arrays.asList(connectUrls.split(",")));
            pool = new ShardedJedisSentinelPool( parseMasters(sentinels), sentinels ,config,password);
        } else {
            List<JedisShardInfo> shards = new ArrayList<>();
            for (String connectUrl : connectUrlArray) {
                String ip = connectUrl.split("\\:")[0];
                String port = connectUrl.split("\\:")[1];
                JedisShardInfo jedisShardInfo = new JedisShardInfo(ip, Integer.valueOf(port));
                shards.add(jedisShardInfo);
            }
            pool = new ShardedJedisPool(config, shards);
        }
    }

    /**
     * redis操作对象
     *
     * @return
     */
    public ShardedJedis getShardedJedis(){
        ShardedJedis jedis = pool.getResource();
        return jedis;
    }

    /**
     * 返还redis操作对象
     *
     * @param shardedJedis
     */
    public void returnShardedJedis(ShardedJedis shardedJedis) {
        try {
            this.pool.returnResource(shardedJedis);
        } catch (Throwable var3) {
            this.pool.returnBrokenResource(shardedJedis);
        }
    }

    public void setConnectUrls(String connectUrls) {
        this.connectUrls = connectUrls;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfig(JedisPoolConfig config) {
        this.config = config;
    }

    private static List<String> parseMasters(Set<String> sentinels) {
        List<String> result = new ArrayList();
        Set<String> sentinelSet = new HashSet();
        if (sentinels != null && sentinels.size() != 0) {
            Iterator sentinelIt = sentinels.iterator();

            while (sentinelIt.hasNext()) {
                String sentinel = (String) sentinelIt.next();
                HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
                List<Map<String, String>> masters = null;
                Jedis jedis = null;

                try {
                    jedis = new Jedis(hap.getHost(), hap.getPort());
                    masters = jedis.sentinelMasters();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (null != jedis) {
                        jedis.close();
                    }
                }

                if (masters != null && masters.size() > 0) {
                    Iterator masterIt = masters.iterator();
                    while (masterIt.hasNext()) {
                        Map<String, String> master = (Map) masterIt.next();
                        sentinelSet.add(master.get("name"));
                    }
                }
            }
            result.addAll(sentinelSet);
            return result;
        } else {
            return result;
        }
    }

    private static HostAndPort toHostAndPort(List<String> iap) {
        if (null != iap && iap.size() == 2) {
            String host = iap.get(0);
            int port = Integer.parseInt(iap.get(1));
            return new HostAndPort(host, port);
        } else {
            throw new JedisDataException("input parameter is invalid.param=" + iap);
        }
    }
}
