package org.nextuple.services;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class WebexService {

    private String webhookUrl = "https://webexapis.com/v1/webhooks/incoming/Y2lzY29zcGFyazovL3VybjpURUFNOnVzLXdlc3QtMl9yL1dFQkhPT0svNWIzOTliMzktYmU5Ni00MTUzLWI0NDUtNzU2NTFjMGRlMWFl";

    @FunctionName("WebexService")
    public HttpResponseMessage runWebex(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("message");
        String message = request.getBody().orElse(query);

        if (message == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a message on the query string or in the request body").build();
        } else {
            String payload = "{\"text\":\"" + message + "\"}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(webhookUrl, requestEntity, String.class);

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully sent the message to Webex!");
                } else {
                    System.out.println("Failed to send message to Webex. Response: " + responseEntity.getBody());
                }
            } catch (Exception e) {
                System.out.println("Error sending message to Webex: " + e.getMessage());
                e.printStackTrace();
            }
            return request.createResponseBuilder(HttpStatus.OK).body(message).build();
        }
    }
}
