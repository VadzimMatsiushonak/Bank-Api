package by.vadzimmatsiushonak.bank.api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public final static String VERIFICATION_CODES = "VERIFICATION_CODES";
    public final static Long CACHE_TTL = 5L;

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine
            .newBuilder()
            .expireAfterWrite(CACHE_TTL, TimeUnit.MINUTES)
            .maximumSize(1000);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(VERIFICATION_CODES);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    @Bean
    public Cache verifications(CacheManager cacheManager) {
        return cacheManager.getCache(VERIFICATION_CODES);
    }

}
