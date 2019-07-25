import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import Box from '@material-ui/core/Box';
import IconButton from '@material-ui/core/IconButton';
import Close from '@material-ui/icons/Close';
import Toolbar from '@material-ui/core/Toolbar';
import classes from './ChatbotHeader.module.css';
/**
 * Componenet representing header of chatbot
 */
const ChatbotHeader = props => {
  return (
    <div className={classes.root}>
      <AppBar position="static" style={{borderTopLeftRadius: '10px',borderTopRightRadius: '10px'}}>
        <Box boxShadow={3} className={classes.Shadow} style={{borderTopLeftRadius: '10px',borderTopRightRadius: '10px'}}>
        
          <Toolbar>
          
            <Typography variant="h4" component="h1" className={classes.title}>
              {props.title}
            </Typography>
            <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="Menu" onClick={props.clickHandler}>
             
              <Close style={{ fontSize:'1.3em'}}/>
            </IconButton>
          </Toolbar>
        </Box>
      </AppBar>

    </div>


  );
};

export default ChatbotHeader;
