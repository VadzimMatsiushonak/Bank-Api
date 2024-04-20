package by.vadzimmatsiushonak.bank.api.config;

import by.vadzimmatsiushonak.bank.api.listener.CaffeineEvictionListener;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@Slf4j
@RequiredArgsConstructor
public class CacheConfig {

    public final static String CONFIRMATION_CODES = "CONFIRMATION_CODES";
    public final static Long CACHE_TTL = 5L;

    @Bean
    public Caffeine caffeineConfig(CaffeineEvictionListener evictionListener) {
        return Caffeine
            .newBuilder()
            .expireAfterWrite(CACHE_TTL, TimeUnit.SECONDS)
            .scheduler(Scheduler.systemScheduler()) // Enabled to allow cache 'refreshing' / 'check 'each ~1 second
            .evictionListener(evictionListener)
            .maximumSize(1000);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CONFIRMATION_CODES);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    @Bean
    public Cache confirmations(CacheManager cacheManager) {
        return cacheManager.getCache(CONFIRMATION_CODES);
    }

}
