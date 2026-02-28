package com.example.demo.annotation

/**
 * 로컬 캐싱을 위한 애너테이션
 * 
 * 사용 예:
 * @LocalCache(ttlMinutes = 1, cacheKeyFields = ["id"])
 * fun getMemberById(request: MemberGetRequestDTO): MemberResponseDTO?
 * 
 * 자동 생성되는 캐시명: "MemberService_getMemberById"
 * 
 * @param ttlMinutes TTL (분 단위, 기본값: 1)
 * @param cacheKeyFields 캐시 키로 사용할 필드명 배열
 *                       (isDbAccess 등 플래그 필드 제외)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalCache(
    val ttlMinutes: Int = 1,
    val cacheKeyFields: Array<String> = []
)
