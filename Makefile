up:
	docker compose -f infrastructure/docker/common.yml \
		-f infrastructure/docker/connect/connect.yml \
		-f infrastructure/docker/es/docker-compose.yml \
		-f infrastructure/docker/kafka/kafka_cluster.yml \
		-f infrastructure/docker/kafka/zookeeper.yml up -d

down:
	docker compose -f infrastructure/docker/common.yml \
		-f infrastructure/docker/connect/connect.yml \
		-f infrastructure/docker/es/docker-compose.yml \
		-f infrastructure/docker/kafka/kafka_cluster.yml \
		-f infrastructure/docker/kafka/zookeeper.yml down -v