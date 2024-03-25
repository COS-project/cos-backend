package com.cos.cercat.search.app.handler;

import com.cos.cercat.search.app.dto.DebeziumEvent;

public interface EventHandler {

    void process(DebeziumEvent event);

}
