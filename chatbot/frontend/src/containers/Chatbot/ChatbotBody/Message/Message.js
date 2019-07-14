import React from 'react';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import robot from './images/robot.png';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
const useStyles = makeStyles({
    avatar: {
      margin: 10,
    },
    bigAvatar: {
      margin: 10,
      width: 60,
      height: 60,
    },
    text: {
        flexGrow: 1,
        padding:10,
        borderRadius:10,
        borderTopLeftRadius:0,
        width:'80%',
        position:'relative',
        right:'0px',
        backgroundColor:'white'
        
      },
  });
const Message = (props) => {
    const classes = useStyles();
    return(
        <React.Fragment>
             <Avatar alt="Chatbot" src={robot} className={classes.avatar} />
            <Box boxShadow={2} className={classes.text}>
           
            <Typography variant="body1" >
            {props.text}
          </Typography>
            </Box>
        </React.Fragment>
    );
}

export default Message;