package com.cos.cercat.messaging.debezium;

public interface EventHandler {

    void process(DebeziumEvent event);

}
