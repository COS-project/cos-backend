package com.cos.cercat.infra.debezium;

public interface EventHandler {

    void process(DebeziumEvent event);

}
