package org.nextuple.services;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class TeamsService {


    private String webhookUrl = "https://aliciazanenextuple.webhook.office.com/webhookb2/056adeed-dcf1-415a-8077-7608d205e5cc@a504dc87-820c-4014-a6f7-9bd271ec731f/IncomingWebhook/8ba7cb2c176f4a4b9b337ce01b68488d/8e647978-03ea-426f-9700-f97dcbd1d891";
    @FunctionName("TeamsService")
    public HttpResponseMessage runTeams(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("message");
        String message = request.getBody().orElse(query);

        if (message == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a message on the query string or in the request body").build();
        } else {
            RestTemplate restTemplate = new RestTemplate();
            TeamsPayload teamsPayload = new TeamsPayload(message);
            restTemplate.postForObject(webhookUrl, teamsPayload, String.class);
            System.out.println("Successfully sent the message to Teams!");
            return request.createResponseBuilder(HttpStatus.OK).body(message).build();
        }
    }

    private static class TeamsPayload {
        private String text;

        public TeamsPayload(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
