up:
	docker compose -f docker/common.yml \
		-f docker/kafka/kafka_cluster.yml \
		-f docker/kafka/zookeeper.yml up -d

down:
	docker compose -f docker/common.yml \
		-f docker/kafka/kafka_cluster.yml \
		-f docker/kafka/zookeeper.yml down -v
