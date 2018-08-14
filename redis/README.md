该工程是redis集群、单机、哨兵的封装

配置如下：
//集群配置集群redis的地址，哨兵模式配置哨兵地址
<bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
  <property name="maxTotal" value="${redis_maxTotal}"/>
  <property name="maxIdle" value="${redis_maxIdle}"/>
  <property name="maxWaitMillis" value="${redis_maxWaitMillis}"/>
  <property name="testOnBorrow" value="${redis_testOnBorrow}"/>      
</bean>

<bean id="redisConnectFactory" class="com.krxu.dreamer.redis.RedisConnectFactory" init-method="init">
   <property name="config" ref="jedisPoolConfig"/>
   <property name="connectUrls" value="${redis_connect_urls}"/>
</bean>

<bean id="redisCacheManager" class="com.krxu.dreamer.redis.manager.impl.RedisCacheManagerImpl">
   <property name="redisConnectFactory" ref="redisConnectFactory"/>
</bean>

集群模式poll用一致性hash落数据

哨兵模式pool初始化：

1、根据哨兵获取master name列表
2、根据master的name获取到master的ip、port,哨兵启动MasterListener获取currentHostMasters，sub/pub模式实现监听currentHostMasters变化
3、哨兵currentHostMasters与master对比创建pool


