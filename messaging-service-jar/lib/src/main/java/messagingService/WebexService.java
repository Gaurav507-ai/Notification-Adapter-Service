package messagingService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WebexService {

    private String urlWebexWebhook;

    public WebexService(String urlWebexWebhook){
        this.urlWebexWebhook = urlWebexWebhook;
    }

    public void sendMessageToWebex(String message) {
        String payload = "{\"text\":\"" + message + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlWebexWebhook, requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("Successfully sent the message to Webex!");
            } else {
                System.out.println("Failed to send message to Webex. Response: " + responseEntity.getBody());
            }
        } catch (Exception e) {
            System.out.println("Error sending message to Webex: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
