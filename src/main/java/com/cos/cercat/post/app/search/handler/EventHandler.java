package com.cos.cercat.post.app.search.handler;

import com.cos.cercat.post.app.search.DebeziumEvent;

public interface EventHandler {

    void process(DebeziumEvent event);

}
