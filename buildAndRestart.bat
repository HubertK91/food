docker build -t food .
docker stop food
docker rm food
docker run -d -p 8080:8080 --name food food