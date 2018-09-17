package com.krxu.dreamer.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.Pool;

import java.util.*;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018-07-28
 * @description redis连接工厂
 */
public class RedisConnectFactory {

    private static final Logger logger = Logger.getLogger(RedisConnectFactory.class);

    /**
     * 连接地址 ip1:port1,ip2:port2
     */
    private String connectUrls;

    /**
     * 密码
     */
    private String password;

    /**
     * 配置
     */
    private JedisPoolConfig config;

    /**
     * 连接池
     */
    private Pool<ShardedJedis> pool;

    /**
     * 初始化连接池
     */
    public void init() {
        if (null == connectUrls || connectUrls.trim().length() == 0) {
            throw new RuntimeException("jedis connection url is empty");
        }
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
            } catch (Exception ex) {
                logger.error("check redis error", ex);
                throw new RuntimeException("check redis error: " + connectUrl, ex);
            } finally {
                if (jedis != null) {
                    jedis.disconnect();
                    jedis.close();
                }
            }
        }
        if (sentinelCount > 0 && sentinelCount != connectUrlArray.length
                || clusterCount > 0 && clusterCount != connectUrlArray.length) {
            logger.error("redis配置错误,集群or哨兵数量不匹配");
            throw new RuntimeException("redis配置错误,集群or哨兵数量不匹配");
        }
        //哨兵模式 或 集群模式
        if (sentinelCount > 0) {
            Set<String> sentinels = new HashSet(Arrays.asList(connectUrls.split(",")));
            pool = new ShardedJedisSentinelPool(parseMasters(sentinels), sentinels, config, password);
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
    public ShardedJedis getShardedJedis() {
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

    /**
     * 销毁集群连接池
     */
    public void destroyPool() {
        if (null != this.pool) {
            this.pool.destroy();
            this.pool = null;
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

    /**
     * 获取哨兵监控的master命名列表
     *
     * @param sentinels 哨兵列表
     * @return
     */
    private static List<String> parseMasters(Set<String> sentinels) {
        List<String> result = new ArrayList();
        Set<String> sentinelSet = new HashSet();
        if (sentinels != null && sentinels.size() != 0) {
            Iterator sentinelIt = sentinels.iterator();

            //遍历配置的每个哨兵
            while (sentinelIt.hasNext()) {
                String sentinel = (String) sentinelIt.next();
                HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
                List<Map<String, String>> masters = null;
                Jedis jedis = null;

                try {
                    jedis = new Jedis(hap.getHost(), hap.getPort());
                    masters = jedis.sentinelMasters();
                } catch (Exception ex) {
                    logger.error("Parse master by sentinels error", ex);
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

    /**
     * ip端口转换对象
     *
     * @param iap
     * @return
     */
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
