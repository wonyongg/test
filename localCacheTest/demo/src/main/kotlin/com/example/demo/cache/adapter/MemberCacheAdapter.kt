package com.example.demo.cache.adapter

import com.example.demo.cache.strategy.BaseCacheStrategy
import com.example.demo.dto.GetAllMembersRequestDTO
import org.springframework.stereotype.Component

/**
 * Member 조회 전용 캐싱 어댑터
 * 
 * MemberService의 전체 조회 메서드의 캐싱 로직을 담당
 * - 캐시 키: 파라미터 타입으로 구분 (GetAllMembersRequestDTO)
 * - TTL: 1분 (고정)
 */
@Component
class MemberCacheAdapter() : BaseCacheStrategy() {
    
    /**
     * 캐시 키 생성
     * GetAllMembersRequestDTO일 경우: "all_members"
     */
    override fun generateCacheKey(args: Array<Any>): String {
        if (args.isEmpty()) return "all_members"
        
        val firstArg = args[0]
        return if (firstArg is GetAllMembersRequestDTO) {
            "all_members"
        } else {
            firstArg.toString()
        }
    }
}
