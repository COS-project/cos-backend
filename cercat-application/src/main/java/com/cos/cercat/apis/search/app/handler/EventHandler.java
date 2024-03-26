package com.cos.cercat.apis.search.app.handler;

import com.cos.cercat.apis.search.app.dto.DebeziumEvent;

public interface EventHandler {

    void process(DebeziumEvent event);

}
