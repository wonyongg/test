# 회원 생성
curl -X POST http://localhost:8080/api/members \
-H "Content-Type: application/json" \
-d '{"name": "홍길동", "age": 30}'

# 전체 회원 조회
curl http://localhost:8080/api/members

# 개별 회원 조회
curl http://localhost:8080/api/members/1

# 회원 수정
curl -X PATCH http://localhost:8080/api/members/1 \
-H "Content-Type: application/json" \
-d '{"name": "김철수", "age": 25}'

# 회원 삭제
curl -X DELETE http://localhost:8080/api/members/1