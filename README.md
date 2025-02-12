# redis 에서 lua script 를 사용하다 생긴 문제를 정리

## 실행 방법
1. redis 서버가 필요해 ./docker/docker-compose.yaml 에 redis 컨테이너를 실행할 수 있습니다.
```Bash
docker-compose up -d
```

application 을 실행하면 2 개의 데이터가 redis 에 key, value로 들어가게 됩니다.
```JavaScript
admin: {
  
}
```