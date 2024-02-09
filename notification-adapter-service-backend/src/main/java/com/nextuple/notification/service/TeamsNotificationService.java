package com.nextuple.notification.service;

import messagingService.TeamsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TeamsNotificationService {

    @Value("${teams.webhook}")
    private String teamsWebhook;

    @Value("${teams.enable}")
    private String teamsEnable;

    public String sendMessageToTeams(String message)
    {
        if(teamsEnable.equals("Y")){
            TeamsService service = new TeamsService(teamsWebhook);
            service.sendMessageToTeams(message);
            return message;
        }
        else{
            return "Teams service is not enabled!";
        }
    }
}
