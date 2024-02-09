import React, { useState } from "react";
import { toast } from "react-toastify";
import {
  Button,
  TextField,
  Paper,
  Container,
  CircularProgress,
  Grid,
  Typography,
} from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import ServerlessForm from "./ServerlessForm";

function MessageForm() {
  const [message, setMessage] = useState("");
  const [isButtonEnabled, setButtonEnabled] = useState(false);
  const [isSendingSlack, setSendingSlack] = useState(false);
  const [isSendingTeams, setSendingTeams] = useState(false);
  const [isSendingWebex, setSendingWebex] = useState(false);
  const [isSendingAll, setSendingAll] = useState(false);
  const [characterCount, setCharacterCount] = useState(0);
  const maxCharacters = 2000;

  const handleTextChange = (event) => {
    const newText = event.target.value;
    setMessage(newText);
    setButtonEnabled(newText.trim() !== "");
    setCharacterCount(newText.length);
  };

  const handleSendMessageToBackend = async (platform) => {
    try {
      if (platform === "slack") {
        setSendingSlack(true);
      } else if (platform === "teams") {
        setSendingTeams(true);
      } else if (platform === "webex") {
        setSendingWebex(true);
      } else {
        setSendingAll(true);
      }

      const response = await fetch(
        `http://localhost:8080/${message}/${platform}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
          },
        }
      );

      if (response.ok) {
        toast.success("Message sent successfully!", { autoClose: 1000 });
        setMessage("");
        setCharacterCount(0);
      } else {
        toast.error("Failed to send message. Please try again.", {
          autoClose: 1000,
        });
      }
    } catch (error) {
      console.error("Error sending message:", error);
      toast.error("An unexpected error occurred. Please try again later.", {
        autoClose: 1000,
      });
    } finally {
      if (platform === "slack") {
        setSendingSlack(false);
      } else if (platform === "teams") {
        setSendingTeams(false);
      } else if (platform === "webex") {
        setSendingWebex(false);
      } else {
        setSendingAll(false);
      }
    }
    setCharacterCount(0);
  };

  return (
    <Container
      component="main"
      maxWidth="sm"
      style={{ marginTop: "50px", backgroundImage: `url(/logos.png)` }}
    >
      <Paper
        elevation={3}
        style={{
          padding: "20px",
          borderRadius: "8px",
          textAlign: "center",
          border: "0.5px solid #1565c0",
        }}
      >
        <TextField
          type="text"
          label="Enter the Message"
          variant="outlined"
          fullWidth
          multiline
          rows={5}
          value={message}
          onChange={handleTextChange}
          style={{ marginBottom: "10px" }}
          inputProps={{ maxLength: maxCharacters }}
        />
        <Typography variant="body2" color="textSecondary">
          Characters remaining: {maxCharacters - characterCount}
        </Typography>

        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <Typography variant="h6" gutterBottom>
              LIBRARY (JAR)
            </Typography>
            <Button
              onClick={() => handleSendMessageToBackend("slack")}
              disabled={
                !isButtonEnabled || isSendingSlack || message.trim() === ""
              }
              fullWidth
              variant="contained"
              color="primary"
              style={{ marginTop: "10px" }}
              startIcon={<SendIcon />}
            >
              {isSendingSlack ? (
                <CircularProgress size={24} />
              ) : (
                "Send to Slack"
              )}
            </Button>
            <Button
              onClick={() => handleSendMessageToBackend("teams")}
              disabled={
                !isButtonEnabled || isSendingTeams || message.trim() === ""
              }
              fullWidth
              variant="contained"
              color="secondary"
              style={{ marginTop: "10px" }}
              startIcon={<SendIcon />}
            >
              {isSendingTeams ? (
                <CircularProgress size={24} />
              ) : (
                "Send to Teams"
              )}
            </Button>
            <Button
              onClick={() => handleSendMessageToBackend("webex")}
              disabled={
                !isButtonEnabled || isSendingWebex || message.trim() === ""
              }
              fullWidth
              variant="contained"
              color="primary"
              style={{ marginTop: "10px" }}
              startIcon={<SendIcon />}
            >
              {isSendingWebex ? (
                <CircularProgress size={24} />
              ) : (
                "Send to Webex"
              )}
            </Button>
            <Button
              onClick={() => handleSendMessageToBackend("all")}
              disabled={
                !isButtonEnabled || isSendingAll || message.trim() === ""
              }
              fullWidth
              variant="contained"
              color="secondary"
              style={{ marginTop: "10px" }}
              startIcon={<SendIcon />}
            >
              {isSendingAll ? <CircularProgress size={24} /> : "Send to All"}
            </Button>
          </Grid>

          <Grid
            item
            xs={12}
            sm={1}
            style={{
              borderRight: "2px solid #1565c0",
              marginTop: "20px",
              marginLeft: "-25px",
            }}
          ></Grid>

          <Grid item xs={12} sm={5}>
            <Typography variant="h6" gutterBottom>
              SERVERLESS
            </Typography>
            <ServerlessForm
              isButtonEnabled={isButtonEnabled}
              message={message}
              onMessageSent={() => {
                setMessage("");
                setCharacterCount(0);
              }}
            />
          </Grid>
        </Grid>
      </Paper>
    </Container>
  );
}

export default MessageForm;
