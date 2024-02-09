package com.nextuple.notification.controller;

import com.nextuple.notification.service.SlackNotificationService;
import com.nextuple.notification.service.TeamsNotificationService;
import com.nextuple.notification.service.WebexNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class NotificationController {


    @Autowired
    private SlackNotificationService slackNotificationService;

    @Autowired
    private TeamsNotificationService teamsNotificationService;

    @Autowired
    private WebexNotificationService webexNotificationService;

    @PostMapping("/{message}/slack")
    public ResponseEntity<String> messageToSlack(@PathVariable(name = "message") String message)
    {
        slackNotificationService.sendMessageToSlack(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{message}/teams")
    public ResponseEntity<String> messageToTeams(@PathVariable(name = "message") String message)
    {
        teamsNotificationService.sendMessageToTeams(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{message}/webex")
    public ResponseEntity<String> messageToWebex(@PathVariable(name = "message") String message)
    {
        webexNotificationService.sendMessageToWebex(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{message}/all")
    public ResponseEntity<String> sendMessageToAll(@PathVariable(name = "message") String message)
    {
        slackNotificationService.sendMessageToSlack(message);
        teamsNotificationService.sendMessageToTeams(message);
        webexNotificationService.sendMessageToWebex(message);
        return ResponseEntity.ok(message);
    }
}
