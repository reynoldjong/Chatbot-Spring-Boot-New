import React from 'react';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import robot from './images/robot.png';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import { deepOrange, deepPurple } from '@material-ui/core/colors';
const useStyles = makeStyles({
  avatar: {
    margin: 10,
 
  },
  bigAvatar: {
    margin: 10,
    width: 60,
    height: 60,
  },
  textBot: {
    flexGrow: 1,
    padding: 10,
    borderRadius: 10,
    borderTopLeftRadius: 0,
    width: '80%',
    position: 'relative',
    right: '0px',
    backgroundColor: 'white',
    textAlign:'left',
    margin:'10px'

  },
  textHuman:{
    flexGrow: 1,
    padding: 10,
    borderRadius: 10,
    borderTopRightRadius: 0,
    width: '80%',
    position: 'relative',
    right: '0px',
    backgroundColor: 'white',
    textAlign:'left',
    margin:'10px'

  },
   orangeAvatar: {
    margin: 10,
    color: '#fff',
    backgroundColor: '#3f51b5',
  },
});
const Message = (props) => {
  const classes = useStyles();
  let message = null;

  if (props.type === 'bot') {
    message = (
      <React.Fragment>
        <Avatar alt="Chatbot" src={robot} className={classes.avatar} />
        <Box boxShadow={2} className={classes.textBot}>

          <Typography variant="body1" >
            {props.text}
          </Typography>
        </Box>
      </React.Fragment>
    );
  }

  else{
    message = (
      <React.Fragment>
         <Avatar alt="Chatbot" color="primary" className={classes.orangeAvatar} style={{float:'right'}}> D</Avatar>
        <Box boxShadow={2} className={classes.textHuman} style={{float:'right',backgroundColor:'#26a69a', color:'white'}}>

          <Typography variant="body1" >
            {props.text}
          </Typography>
        </Box>
      </React.Fragment>
    );
  }


  return (
    <React.Fragment>

       {message}
       </React.Fragment>
    );
}

export default Message;