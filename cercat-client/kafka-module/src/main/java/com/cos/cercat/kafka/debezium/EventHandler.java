package com.cos.cercat.kafka.debezium;

public interface EventHandler {

    void process(DebeziumEvent event);

}
