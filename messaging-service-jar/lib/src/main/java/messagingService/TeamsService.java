package messagingService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TeamsService {

    private String urlTeamsWebhook;

    public TeamsService(String urlTeamsWebhook){
        this.urlTeamsWebhook = urlTeamsWebhook;
    }
    public void sendMessageToTeams(String message) {
        StringBuilder messageBuider = new StringBuilder();
        messageBuider.append(message);
        processTeams(urlTeamsWebhook, messageBuider.toString());
    }

    private void processTeams(String webhookUrl, String message) {
        RestTemplate restTemplate = new RestTemplate();
        TeamsPayload teamsPayload = new TeamsPayload(message);
        restTemplate.postForObject(webhookUrl, teamsPayload, String.class);
        System.out.println("Successfully sent the message to Teams!");
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
