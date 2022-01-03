updb:
	docker-compose up -d

stopdb:
	docker-compose stop

dev:
	mvn spring-boot:run

test:
	mvn compile test
