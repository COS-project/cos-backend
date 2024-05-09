#!/bin/bash

IS_BLUE=$(docker compose ps | grep cercat-blue)
DEFAULT_CONF=" data/nginx/nginx.conf"
MAX_RETRIES=40

check_service() {
  local RETRIES=0
  local URL=$1
  while [ $RETRIES -lt $MAX_RETRIES ]; do
    echo "Checking service at $URL/health... (attempt: $((RETRIES+1)))"
    sleep 3

    REQUEST=$(curl -s "$URL/health")  # -s 옵션은 조용한 모드로 curl 명령어의 출력을 감춥니다.
    if echo "$REQUEST" | grep -q "health check success"; then  # 응답에서 "health check success" 문자열이 있는지 확인합니다.
      echo "Health check passed."
      return 0
    fi

    RETRIES=$((RETRIES+1))
  done;

  echo "Failed to check service after $MAX_RETRIES attempts."
  return 1
}

if [ -z "$IS_BLUE" ];then
  echo "### GREEN => BLUE ###"

  echo "1. BLUE 이미지 받기"
  docker compose pull cercat-blue

  echo "2. BLUE 컨테이너 실행"
  docker compose up -d cercat-blue --scale cercat-blue=2

  echo "3. health check"
  if ! check_service "http://127.0.0.1:8080"; then
    echo "BLUE health check 가 실패했습니다."
    exit 1
  fi

  echo "4. nginx 재실행"
  sudo cp data/nginx/nginx-blue.conf data/nginx/nginx.conf
  sudo docker compose exec -it nginx nginx -s reload

  echo "5. GREEN  컨테이너 내리기"
  docker compose stop cercat-green
  docker compose rm -f cercat-green

else
  echo "### BLUE => GREEN ###"

  echo "1. GREEN 이미지 받기"
  docker compose pull cercat-green

  echo "2. GREEN 컨테이너 실행"
  docker compose up -d cercat-green --scale cercat-green=2

  echo "3. health check"
  if ! check_service "http://127.0.0.1:8081"; then
      echo "GREEN health check 가 실패했습니다."
      exit 1
    fi

  echo "4. nginx 재실행"
  sudo cp data/nginx/nginx-green.conf data/nginx/nginx.conf
  sudo docker compose exec -it nginx nginx -s reload

  echo "5. BLUE 컨테이너 내리기"
  docker compose stop cercat-blue
  docker compose rm -f cercat-blue
fi