import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';

const useStyles = makeStyles({
    avatar: {
      margin: 10,
    },
    bigAvatar: {
      margin: 10,
      width: 30,
      height: 60,
      flexGrow:1,
    },
    root: {
        flexGrow: 1,
        
      },
      
      title: {
        flexGrow: 2,
      },
      menuButton: {
        marginRight: 29,
      },
      
  });
const ChatbotHeader = props => {
    const classes = useStyles();
  return (

<div className={classes.root}>
  


    <AppBar position="static">
    <Box boxShadow={3}>
    <Toolbar>
    <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="Menu">
            <MenuIcon />
          </IconButton>
      <Typography variant="h4" component="h2" className={classes.title}>
        {props.title}
      </Typography>
      </Toolbar>
      </Box>
    </AppBar>
   
    </div>
    
     
  );
};

export default ChatbotHeader;
