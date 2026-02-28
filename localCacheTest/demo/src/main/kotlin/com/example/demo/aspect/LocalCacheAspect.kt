package com.example.demo.aspect

import com.example.demo.annotation.LocalCache
import com.example.demo.cache.adapter.MemberCacheAdapter
import com.example.demo.cache.strategy.CacheStrategy
import com.example.demo.config.DynamicTtlCacheManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.util.concurrent.Callable

/**
 * @LocalCache 애너테이션을 처리하는 범용 AOP Aspect
 * 
 * 역할:
 * 1. @LocalCache 애너테이션 감지
 * 2. 서비스별 캐싱 어댑터 선택 및 실행
 * 3. CacheManager에 위임하여 캐싱 수행
 * 
 * 특징:
 * - Aspect는 최소화 (정말 필요한 것만)
 * - 실제 캐싱 로직은 어댑터에 위임 (전략 패턴)
 * - 새 서비스 추가 시 새 어댑터만 생성하면 됨
 */
@Aspect
@Component
class LocalCacheAspect(
    private val cacheManager: CacheManager,
    private val memberCacheAdapter: MemberCacheAdapter
) {
    
    @Around("@annotation(localCache)")
    fun cacheableAdvice(
        joinPoint: ProceedingJoinPoint,
        localCache: LocalCache
    ): Any? {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val args = joinPoint.args
        
        // 1️⃣ 서비스별 캐싱 전략 선택
        val cacheStrategy = selectCacheStrategy(joinPoint)
        
        // 2️⃣ 캐시 우회 여부 확인
        if (cacheStrategy.shouldBypassCache(args)) {
            println("[AOP 캐싱] ✅ DB 직접 접근 (캐시 우회)")
            return joinPoint.proceed()
        }
        
        // 3️⃣ 캐시명 생성
        val className = joinPoint.target.javaClass.simpleName
        val methodName = method.name
        val ttlMinutes = localCache.ttlMinutes
        val cacheName = cacheStrategy.getCacheName(className, methodName, ttlMinutes)
        
        // 4️⃣ 캐시 객체 획득
        val cache = if (cacheManager is DynamicTtlCacheManager) {
            cacheManager.createCacheWithTtl(cacheName, ttlMinutes)
        } else {
            cacheManager.getCache(cacheName)
                ?: throw IllegalStateException("Failed to get cache: $cacheName")
        }
        
        // 5️⃣ 캐시 키 생성
        val cacheKey = cacheStrategy.generateCacheKey(args)
        
        println("[AOP 캐싱] 캐시명: $cacheName, 키: $cacheKey, TTL: ${ttlMinutes}분")
        
        // 6️⃣ 캐싱 실행 (어댑터의 전략 사용)
        return cacheStrategy.executeWithCache(cache, cacheKey, Callable {
            joinPoint.proceed()
        })
    }
    
    /**
     * 서비스 클래스에 따라 적절한 캐싱 전략(어댑터) 선택
     * 
     * 확장 방법:
     * 1. ProductService → ProductCacheAdapter 추가
     * 2. UserService → UserCacheAdapter 추가
     * 3. 여기에 when 절 추가
     */
    private fun selectCacheStrategy(joinPoint: ProceedingJoinPoint): CacheStrategy {
        val className = joinPoint.target.javaClass.simpleName
        
        return when (className) {
            "MemberService" -> memberCacheAdapter
            // "ProductService" -> productCacheAdapter (나중에 추가)
            // "UserService" -> userCacheAdapter (나중에 추가)
            else -> throw IllegalArgumentException("Unsupported service: $className")
        }
    }
}


