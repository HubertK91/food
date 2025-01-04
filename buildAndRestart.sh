docker build -t food .
docker stop food || true
docker rm food || true
docker run -d -p 8080:8080 --name=food -e SPRING_PROFILES_ACTIVE=prod --network food-network --restart unless-stopped food