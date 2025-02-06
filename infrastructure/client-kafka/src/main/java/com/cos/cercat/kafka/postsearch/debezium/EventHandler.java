package com.cos.cercat.kafka.postsearch.debezium;

public interface EventHandler {

    void process(DebeziumEvent event);

}
