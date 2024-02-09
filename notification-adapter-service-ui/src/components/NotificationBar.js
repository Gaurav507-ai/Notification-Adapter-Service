import React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

function NotificationBar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar
          variant="dense"
          sx={{
            displayNextuple: "flex",
            justifyContent: "space-between",
            bgcolor: "white",
          }}
        >
          <img src="nextuple-logo.png" alt="Nextuple" width={90} height={30} />
          <Typography
            variant="h6"
            color="#1565c0"
            component="div"
            sx={{ fontStyle: "italic" }}
          >
            Notification Adapter Service
          </Typography>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default NotificationBar;
