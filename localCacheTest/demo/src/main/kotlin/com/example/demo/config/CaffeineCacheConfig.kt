package com.example.demo.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Caffeine 캐시 설정
 * 
 * TTL별로 다른 캐시 설정을 지원하는 동적 CacheManager
 * @LocalCache(ttlMinutes = X) 로 설정된 값에 따라 자동으로 다른 캐시 생성
 */
@Configuration
@EnableCaching
class CaffeineCacheConfig {
    
    @Bean
    fun cacheManager(): CacheManager {
        return DynamicTtlCacheManager()
    }
}

/**
 * TTL별로 동적으로 캐시를 생성하는 CacheManager
 * 
 * 사용 예:
 * @LocalCache(ttlMinutes = 1)  → 1분 TTL 캐시
 * @LocalCache(ttlMinutes = 5)  → 5분 TTL 캐시
 * @LocalCache(ttlMinutes = 60) → 60분 TTL 캐시
 */
class DynamicTtlCacheManager : CacheManager {
    
    // TTL별 CaffeineCacheManager 캐시
    private val cacheManagerMap = ConcurrentHashMap<Int, CaffeineCacheManager>()
    
    // 캐시 이름으로 만든 캐시들을 추적
    private val cacheMap = ConcurrentHashMap<String, Cache>()
    
    override fun getCache(name: String): Cache? {
        return cacheMap.getOrPut(name) {
            // 캐시명에서 TTL 추출: "ClassName_MethodName_ttl5" 형식
            val ttl = extractTtlFromCacheName(name) ?: 1
            createCacheWithTtl(name, ttl)
        }
    }
    
    override fun getCacheNames(): Collection<String> {
        return cacheMap.keys
    }
    
    /**
     * 지정된 TTL(분)로 캐시 생성
     */
    fun createCacheWithTtl(cacheName: String, ttlMinutes: Int): Cache {
        val cacheManager = cacheManagerMap.getOrPut(ttlMinutes) {
            CaffeineCacheManager().apply {
                setCaffeine(
                    Caffeine.newBuilder()
                        .expireAfterWrite(ttlMinutes.toLong(), TimeUnit.MINUTES)
                        .maximumSize(10000)
                        .recordStats()
                )
            }
        }
        
        return cacheManager.getCache(cacheName) 
            ?: throw IllegalStateException("Failed to create cache: $cacheName")
    }
    
    /**
     * 캐시명에 포함된 TTL 값 추출
     * 형식: "ClassName_MethodName" → Aspect에서 동적으로 추가됨
     */
    private fun extractTtlFromCacheName(name: String): Int? {
        return if (name.contains("_ttl")) {
            name.substringAfterLast("_ttl").toIntOrNull()
        } else {
            null
        }
    }
}


