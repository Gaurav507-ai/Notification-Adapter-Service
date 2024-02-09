package com.nextuple.notification.service;

import messagingService.WebexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebexNotificationService {

    @Value("${webex.webhook}")
    private String webexWebhook;

    @Value("${webex.enable}")
    private String webexEnable;

    public String sendMessageToWebex(String message)
    {
        if(webexEnable.equals("Y")){
            WebexService service = new WebexService(webexWebhook);
            service.sendMessageToWebex(message);
            return message;
        }
        else{
            return "Webex service is not enabled!";
        }
    }

}
