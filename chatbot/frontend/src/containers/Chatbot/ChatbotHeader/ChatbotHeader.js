import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import IconButton from "@material-ui/core/IconButton";
import Minimize from "@material-ui/icons/Minimize";
import Toolbar from "@material-ui/core/Toolbar";
import classes from "./ChatbotHeader.module.css";
import { makeStyles, useTheme } from "@material-ui/core/styles";
const useStyles = makeStyles(theme => ({
  root: {
    flex: 1,
    borderTopLeftRadius: "10px",
    borderTopRightRadius: "10px"
  },
  shadow: {
    borderTopLeftRadius: "10px",
    borderTopRightRadius: "10px"
  },
  title: {},
  menuButton: {
    
    position:'absolute',
    right:'10px',

    bottom:'13px',
  }
}));
/**
 * Componenet representing header of chatbot
 */
const ChatbotHeader = props => {
  const classes = useStyles();
  return (
    <AppBar position="static" className={classes.root}>
      <Box boxShadow={3} >

        <Toolbar>
        <Typography variant="h4" component="h1" align="center" style={{marginLeft:'40px'}}>
            {props.title}
          </Typography>
          <IconButton
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="Minimize"
            onClick={props.clickHandler}
          >
            <Minimize className={classes.menuButton} style={{ fontSize: "1.3em" }} />
          </IconButton>
        </Toolbar>
      </Box>
    </AppBar>
  );
};

export default ChatbotHeader;
