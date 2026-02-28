package com.example.demo.cache.strategy

import org.springframework.cache.Cache
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

/**
 * CacheStrategy 기본 구현
 * 
 * Caffeine의 원자적 동작을 활용한 기본 캐싱 전략
 */
abstract class BaseCacheStrategy : CacheStrategy {
    
    /**
     * 캐시 우회 여부 (기본값: false)
     * - 오버라이드하여 커스텀 로직 추가 가능
     */
    override fun shouldBypassCache(args: Array<Any>): Boolean {
        if (args.isEmpty()) return false
        
        val firstArg = args[0]
        val fields = firstArg::class.java.declaredFields
        
        for (field in fields) {
            if (field.name.startsWith("is") && field.type == Boolean::class.java) {
                field.isAccessible = true
                try {
                    val value = field.getBoolean(firstArg)
                    // isDbAccess, isForceRefresh 등이 true면 캐시 우회
                    if (value && (field.name == "isDbAccess" || field.name == "isForceRefresh")) {
                        println("[캐시 우회] ${field.name} = true")
                        return true
                    }
                } catch (e: Exception) {
                    // 무시
                }
            }
        }
        
        return false
    }
    
    /**
     * 원자적 캐시 실행
     * Caffeine의 get(key, callable) 사용으로 Race Condition 자동 방지
     */
    override fun executeWithCache(
        cache: Cache,
        cacheKey: String,
        callable: Callable<Any?>
    ): Any? {
        val wasLoaded = AtomicBoolean(false)
        
        return cache.get(cacheKey, Callable {
            wasLoaded.set(true)
            println("[캐싱] 🔄 MISS: $cacheKey → DB 로드 중...")
            callable.call()
        }).also { result ->
            if (wasLoaded.get()) {
                println("[캐싱] ✅ 저장 완료: $cacheKey (원자적)")
            } else {
                println("[캐싱] 💾 HIT: $cacheKey")
            }
        }
    }
}
