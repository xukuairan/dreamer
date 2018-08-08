该工程是redis集群、单机、哨兵的封装

配置如下：

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