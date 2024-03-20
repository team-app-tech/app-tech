## Prerequisites
* Docker

## start 
```agsl
    docker build -f dockerfile.dev --platform linux/x86_64 -t yeongsang2/panta-dev:latest .
    docker run -p 8080:8080 yeongsang2/panta-dev:latest
```

## swagger-ui
http://localhost:8080/swagger-ui/index.html

