package messagingService;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.IOException;

public class SlackService {

    private String urlSlackWebHook;

    private String username;

    private String channel;

    public SlackService(String urlSlackWebHook, String username, String channel){
        this.urlSlackWebHook = urlSlackWebHook;
        this.username = username;
        this.channel = channel;
    }

    public void sendMessageToSlack(String message) {
        StringBuilder messageBuider = new StringBuilder();
        messageBuider.append(message);

        process(messageBuider.toString());
    }

    private void process(String message) {
        Payload payload = Payload.builder()
                .channel(channel)
                .username(username)
                .text(message)
                .build();
        try {
            WebhookResponse webhookResponse = Slack.getInstance().send(
                    urlSlackWebHook, payload);
            System.out.println("Successfully sent the message to slack!");
        } catch (IOException e) {
            System.out.println("Exception");
        }

    }

}
