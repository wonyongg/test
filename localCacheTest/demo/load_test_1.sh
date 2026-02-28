#!/bin/bash

# Load Test Script 1: 초당 25개 요청 전송 (개선판)
# 요청을 약간 분산시켜 race condition 방지
# 사용법: ./load_test_1.sh

URL="http://localhost:8080/api/members/1"
REQUESTS_PER_SECOND=25
DURATION=${1:-120}

echo "🚀 Load Test 1 시작"
echo "   URL: $URL"
echo "   초당 요청: $REQUESTS_PER_SECOND개"
echo "   지속 시간: ${DURATION}초"
echo ""

START_TIME=$(date +%s)
END_TIME=$((START_TIME + DURATION))
REQUEST_COUNT=0
DELAY_MS=$((1000 / REQUESTS_PER_SECOND))  # 요청 간 딜레이 (밀리초)

while [ $(date +%s) -lt $END_TIME ]; do
    # 25개의 요청을 약간씩 분산시켜 실행
    for i in $(seq 1 $REQUESTS_PER_SECOND); do
        curl -s "$URL" > /dev/null &
        # 각 요청 사이에 약간의 지연 추가 (40ms)
        sleep 0.04
    done
    
    REQUEST_COUNT=$((REQUEST_COUNT + REQUESTS_PER_SECOND))
    sleep 1
done

wait

ELAPSED=$(($(date +%s) - START_TIME))
echo ""
echo "✅ Load Test 1 완료"
echo "   총 요청: $REQUEST_COUNT개"
echo "   소요 시간: ${ELAPSED}초"

