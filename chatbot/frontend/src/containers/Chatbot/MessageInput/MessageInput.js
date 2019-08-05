import React from "react";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";
import Fab from "@material-ui/core/Fab";
import SendIcon from "@material-ui/icons/Send";
import Paper from "@material-ui/core/Paper";
import media from "./MessageInput.module.css";

const useStyles = makeStyles(theme => ({
  paper: {
    textAlign: "center",
    color: theme.palette.text.secondary
  },
  textField: {
    padding: "5px",
    marginBottom: "5px",
    marginTop: "0px",
    marginLeft: "10px"
  },
  icon: {
    marginTop: "15px",
    marginLeft: "5px",
    "&:hover": {
      backgroundColor: "rgb(38, 166, 154)",
      transition: "0.5s ease-in"
    }
  }
}));

/**
 * Component representing message input
 * @param {props:function} addMessageHandler - function for adding messages
 */
const MessageInput = props => {
  const classes = useStyles();
  return (
    <div className={media.root}>
      <form onSubmit={props.addMessageHandler} autoComplete="off">
        <Paper
          style={{ borderTopRightRadius: "0px", borderTopLeftRadius: "0px" }}
        >
          <Grid container spacing={0}>
            <Grid item xs={10}>
              <TextField
                id="standard-name"
                name="userMessage"
                className={classes.textField}
                autoComplete="off"
                style={{ width: "95%" }}
                placeholder="Send a message!"
              />
            </Grid>
            <Grid item xs={2}>
              <Fab
                color="primary"
                aria-label="Send"
                size="small"
                className={classes.icon}
                type="submit"
              >
                <SendIcon />
              </Fab>
            </Grid>
          </Grid>
        </Paper>
      </form>
    </div>
  );
};

export default MessageInput;