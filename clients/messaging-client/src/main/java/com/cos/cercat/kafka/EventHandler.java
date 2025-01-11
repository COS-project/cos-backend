package com.cos.cercat.kafka;

public interface EventHandler {

    void process(DebeziumEvent event);

}
