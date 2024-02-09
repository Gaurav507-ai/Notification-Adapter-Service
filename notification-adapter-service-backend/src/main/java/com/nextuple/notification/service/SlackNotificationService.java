package com.nextuple.notification.service;

import messagingService.SlackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackNotificationService {

    @Value("${slack.username}")
    private String slackUsername;

    @Value("${slack.channel}")
    private String slackChannel;

    @Value("${slack.webhook}")
    private String slackWebhook;

    @Value(("${slack.enable}"))
    private String slackEnable;

    public String sendMessageToSlack(String message)
    {
        if(slackEnable.equals("Y")){
            SlackService service = new SlackService(slackWebhook, slackUsername, slackChannel);
            service.sendMessageToSlack(message);
            return message;
        }
        else{
            return "Slack service is not enabled!";
        }
    }
}
