package com.chigua.sso.server.config;

import com.chigua.sso.core.store.SsoLoginStore;
import com.chigua.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuxueli 2018-04-03 20:41:07
 */
@Configuration
public class ChiGuaSsoConfig implements InitializingBean, DisposableBean {

    @Value("${xxl.sso.redis.address}")
    private String redisAddress;

    @Value("${xxl.sso.redis.expire.minute}")
    private int redisExpireMinute;

    @Override
    public void afterPropertiesSet() throws Exception {
        SsoLoginStore.setRedisExpireMinute(redisExpireMinute);
        JedisUtil.init(redisAddress);
    }

    @Override
    public void destroy() throws Exception {
        JedisUtil.close();
    }

}
