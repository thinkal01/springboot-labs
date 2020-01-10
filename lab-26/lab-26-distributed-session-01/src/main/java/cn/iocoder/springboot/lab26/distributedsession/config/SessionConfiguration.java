package cn.iocoder.springboot.lab26.distributedsession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@Configuration
/**
 * 自动化配置 Spring Session 使用 Redis 作为数据源
 *
 * maxInactiveIntervalInSeconds 属性，Session 不活跃后的过期时间，默认为 1800 秒
 *
 * redisNamespace 属性，在 Redis 的 key 的统一前缀，默认为 "spring:session"
 *
 * redisFlushMode 属性，Redis 会话刷新模式(RedisFlushMode)。
 * 目前有两种，默认为 RedisFlushMode.ON_SAVE 。
 * RedisFlushMode.ON_SAVE ，在请求执行完成时，统一写入 Redis 存储。
 * RedisFlushMode.IMMEDIATE ，在每次修改 Session 时，立即写入 Redis 存储。
 *
 * cleanupCron 属性，清理 Redis Session 会话过期的任务执行 CRON 表达式，
 * 默认为 "0 * * * * *" 每分钟执行一次。Redis 自带 key 的过期，是惰性删除策略，
 * 实际过期的 Session 还在 Redis 中占用内存。
 * 所以，Spring Session 通过定时任务，删除 Redis 中过期的 Session ，尽快释放 Redis 的内存。
 */
@EnableRedisHttpSession
public class SessionConfiguration {

    /**
     * 创建 {@link RedisOperationsSessionRepository} 使用的 RedisSerializer Bean 。
     *
     * 具体可以看看 {@link RedisHttpSessionConfiguration#setDefaultRedisSerializer(RedisSerializer)} 方法，
     * 它会引入名字为 "springSessionDefaultRedisSerializer" 的 Bean 。
     *
     * @return RedisSerializer Bean
     */
    @Bean(name = "springSessionDefaultRedisSerializer")
    public RedisSerializer springSessionDefaultRedisSerializer() {
        // 采用 JSON 序列化方式。因为默认情况下，采用 Java 自带的序列化方式，可读性很差，所以进行替换。
        return RedisSerializer.json();
    }

//    @Bean
//    public CookieHttpSessionIdResolver sessionIdResolver() {
//        // 创建 CookieHttpSessionIdResolver 对象
//        CookieHttpSessionIdResolver sessionIdResolver = new CookieHttpSessionIdResolver();
//
//        // 创建 DefaultCookieSerializer 对象
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        sessionIdResolver.setCookieSerializer(cookieSerializer); // 设置到 sessionIdResolver 中
//        cookieSerializer.setCookieName("JSESSIONID");
//
//        return sessionIdResolver;
//    }

//    @Bean
//    public HeaderHttpSessionIdResolver sessionIdResolver() {
////        return HeaderHttpSessionIdResolver.xAuthToken();
////        return HeaderHttpSessionIdResolver.authenticationInfo();
//        return new HeaderHttpSessionIdResolver("token");
//    }

}
