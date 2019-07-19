import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import Box from '@material-ui/core/Box';

import IconButton from '@material-ui/core/IconButton';
import Minimize from '@material-ui/icons/Minimize';
import Toolbar from '@material-ui/core/Toolbar';
import classes from './ChatbotHeader.module.css';


const ChatbotHeader = props => {
  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Box boxShadow={3} className={classes.Shadow}>
          <Toolbar>
            <Typography variant="h5" component="h2" className={classes.title}>
              {props.title}
            </Typography>
            <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="Menu" onClick={props.clickHandler}>
              <Minimize />
            </IconButton>
          </Toolbar>
        </Box>
      </AppBar>

    </div>


  );
};

export default ChatbotHeader;
