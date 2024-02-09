package org.nextuple.services;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.IOException;
import java.util.Optional;


public class SlackService {

    private String webhookUrl = "https://hooks.slack.com/services/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";
    private String channel = "#hackathon";
    private String username = "gaurav.kundwani";



    @FunctionName("SlackService")
    public HttpResponseMessage runSlack(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("message");
        String message = request.getBody().orElse(query);

        if (message == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a message on the query string or in the request body").build();
        } else {
            StringBuilder messageBuider = new StringBuilder();
            messageBuider.append(message);

            process(messageBuider.toString());
            return request.createResponseBuilder(HttpStatus.OK).body(message).build();
        }
    }

    private void process(String message) {
        Payload payload = Payload.builder()
                .channel(channel)
                .username(username)
                .text(message)
                .build();
        try {
            WebhookResponse webhookResponse = Slack.getInstance().send(
                    webhookUrl, payload);
            System.out.println("Successfully sent the message to slack!");
        } catch (IOException e) {
            System.out.println("Exception");
        }

    }
}
