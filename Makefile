up:
	docker compose -f docker-compose/common.yml \
		-f docker-compose/connect/connect.yml \
		-f docker-compose/es/docker-compose.yml \
		-f docker-compose/kafka/kafka_cluster.yml \
		-f docker-compose/kafka/zookeeper.yml up -d

down:
	docker compose -f docker-compose/common.yml \
		-f docker-compose/connect/connect.yml \
		-f docker-compose/es/docker-compose.yml \
		-f docker-compose/kafka/kafka_cluster.yml \
		-f docker-compose/kafka/zookeeper.yml down -v