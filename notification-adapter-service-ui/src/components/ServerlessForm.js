import React, { useState } from "react";
import { Button, CircularProgress } from "@mui/material";
import { toast } from "react-toastify";
import SendIcon from "@mui/icons-material/Send";

function ServerlessForm({ isButtonEnabled, message, onMessageSent }) {
  const [isSendingSlack, setSendingSlack] = useState(false);
  const [isSendingTeams, setSendingTeams] = useState(false);
  const [isSendingWebex, setSendingWebex] = useState(false);
  const serverlessEndpoints = {
    slack:
      "https://app-notification-adapter-service-240128190400.azurewebsites.net/api/SlackService?code=jLNiRkghKdW006iTTT2CzDXumWCq9XebypxTWrw64ATWAzFuaPGIYA==&message=",
    teams:
      "https://app-notification-adapter-service-240128190400.azurewebsites.net/api/TeamsService?code=GnLb65gUoUh2NTDlI3N_lFWP_mQUjeTT0mCEOURamJMTAzFulTh6hw==&message=",
    webex:
      "https://app-notification-adapter-service-240128190400.azurewebsites.net/api/WebexService?code=zxU-JB92CkIOue4MgXfJITd4Pa11EwLR1tdY3DA6hhu6AzFur1FOzg==&message=",
  };

  const handleSendMessageToServerless = async (platform) => {
    try {
      if (platform === "slack") {
        setSendingSlack(true);
      } else if (platform === "teams") {
        setSendingTeams(true);
      } else if (platform === "webex") {
        setSendingWebex(true);
      }
      const url = `${serverlessEndpoints[platform]}${message}`;

      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      });

      if (response.ok) {
        toast.success(`Message sent successfully!`, { autoClose: 1000 });
        onMessageSent();
      } else {
        toast.error(`Failed to send message. Please try again.`, {
          autoClose: 1000,
        });
      }
    } catch (error) {
      console.error(`Error sending message via serverless:`, error);
      toast.error(`An unexpected error occurred. Please try again later.`, {
        autoClose: 1000,
      });
    } finally {
      if (platform === "slack") {
        setSendingSlack(false);
      } else if (platform === "teams") {
        setSendingTeams(false);
      } else if (platform === "webex") {
        setSendingWebex(false);
      }
    }
  };
  return (
    <div>
      <Button
        onClick={() => handleSendMessageToServerless("slack")}
        disabled={!isButtonEnabled || isSendingSlack || message.trim() === ""}
        fullWidth
        variant="contained"
        color="primary"
        style={{ marginTop: "10px" }}
        startIcon={<SendIcon />}
      >
        {isSendingSlack ? <CircularProgress size={24} /> : "Send to Slack"}
      </Button>
      <Button
        onClick={() => handleSendMessageToServerless("teams")}
        disabled={!isButtonEnabled || isSendingTeams || message.trim() === ""}
        fullWidth
        variant="contained"
        color="secondary"
        style={{ marginTop: "10px" }}
        startIcon={<SendIcon />}
      >
        {isSendingTeams ? <CircularProgress size={24} /> : "Send to Teams"}
      </Button>
      <Button
        onClick={() => handleSendMessageToServerless("webex")}
        disabled={!isButtonEnabled || isSendingWebex || message.trim() === ""}
        fullWidth
        variant="contained"
        color="primary"
        style={{ marginTop: "10px" }}
        startIcon={<SendIcon />}
      >
        {isSendingWebex ? <CircularProgress size={24} /> : "Send to Webex"}
      </Button>
    </div>
  );
}

export default ServerlessForm;
