FROM mariadb:latest
ADD /docker/db-container/data.sql /docker-entrypoint-initdb.d/
#/docker-entrypoint-initdb.d/ -> MariaDB 컨테이너가 처음 시작할 때 자동으로 실행되는 스크립트 저장