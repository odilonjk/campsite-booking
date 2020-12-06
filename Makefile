docker-build:
	@./gradlew clean build test
	@docker build . -t campsite-booking

docker-run:
	@docker run --name campsite-booking -d --rm -p 8080:8080 campsite-booking:latest

docker-stop:
	@docker stop campsite-booking
