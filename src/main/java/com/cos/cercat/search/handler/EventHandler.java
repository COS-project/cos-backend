package com.cos.cercat.search.handler;

import com.cos.cercat.search.kafka.DebeziumEvent;

public interface EventHandler {

    void process(DebeziumEvent event);

}
