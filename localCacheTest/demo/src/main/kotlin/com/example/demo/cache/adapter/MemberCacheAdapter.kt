package com.example.demo.cache.adapter

import com.example.demo.cache.strategy.BaseCacheStrategy
import com.example.demo.dto.MemberGetRequestDTO
import org.springframework.stereotype.Component

/**
 * Member 조회 전용 캐싱 어댑터
 * 
 * MemberService.getMemberById() 메서드의 캐싱 로직을 담당
 * - 캐시 키: id 필드만 사용
 * - 플래그: isDbAccess 감지
 * - TTL: 1분 (고정)
 */
@Component
class MemberCacheAdapter() : BaseCacheStrategy() {
    
    /**
     * Member 조회용 캐시 키 생성
     * 형식: "id=1"
     */
    override fun generateCacheKey(args: Array<Any>): String {
        if (args.isEmpty()) return "empty"
        
        val firstArg = args[0]
        return if (firstArg is MemberGetRequestDTO) {
            "id=${firstArg.id}"
        } else {
            // 다른 타입이 들어오면 처리 (Future-proof)
            try {
                val field = firstArg::class.java.getDeclaredField("id")
                field.isAccessible = true
                val id = field.get(firstArg)
                "id=$id"
            } catch (e: Exception) {
                firstArg.toString()
            }
        }
    }
}
