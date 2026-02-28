package com.example.demo.cache.strategy

import org.springframework.cache.Cache
import java.util.concurrent.Callable

/**
 * 캐싱 전략 인터페이스
 * 
 * 각 서비스의 캐싱 로직을 독립적으로 구현하기 위한 인터페이스
 * Strategy Pattern으로 캐싱 전략을 정의
 */
interface CacheStrategy {
    /**
     * 캐시 키 생성
     */
    fun generateCacheKey(args: Array<Any>): String
    
    /**
     * 캐시 우회 여부 판단
     */
    fun shouldBypassCache(args: Array<Any>): Boolean
    
    /**
     * 캐시명 반환
     */
    fun getCacheName(className: String, methodName: String, ttlMinutes: Int): String {
        return "${className}_${methodName}_ttl${ttlMinutes}"
    }
    
    /**
     * 캐시 실행
     */
    fun executeWithCache(
        cache: Cache,
        cacheKey: String,
        callable: Callable<Any?>
    ): Any?
}
