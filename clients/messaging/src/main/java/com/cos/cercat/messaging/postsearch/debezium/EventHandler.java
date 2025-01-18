package com.cos.cercat.messaging.postsearch.debezium;

public interface EventHandler {

    void process(DebeziumEvent event);

}
