package com.common;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class SlackPublisher {
    @Autowired
    RestOperations restTemplate ;

    String url = "https://hooks.slack.com/services/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";
    public void publish(String message){
        SlackApi api = new SlackApi(url);
        api.call(new SlackMessage(message));
    }
}
