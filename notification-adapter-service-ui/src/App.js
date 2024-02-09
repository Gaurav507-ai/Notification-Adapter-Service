import React from "react";
import "react-toastify/dist/ReactToastify.css";
import NotificationBar from "../src/components/NotificationBar";
import MessageForm from "./components/MessageForm";

function App() {
  return (
    <>
      <NotificationBar />
      <MessageForm />
    </>
  );
}

export default App;
