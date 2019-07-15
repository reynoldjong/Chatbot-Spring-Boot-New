import React from 'react';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import robot from './images/robot.png';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import { deepOrange, deepPurple } from '@material-ui/core/colors';
const useStyles = makeStyles({
  root:{
    display:'block'
  },
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

     
      <div className={classes.root}>

    
        <Avatar alt="Chatbot" src={robot} className={classes.avatar} />
        <Box boxShadow={2} className={classes.textBot}>

          <Typography variant="body1" >
            {props.text}
            {
              
              props.link?<a href={props.link} style={{display:'block'}}>{props.link}</a>:null
             
         }
          </Typography>
         
          {
              
               props.picture? <img src={props.picture} width="100px" height="100px" />:null
              
          }
         
     
          
        </Box>
        </div>
      
    );
  }

  else{
    message = (
      <div className={classes.root}>
         <Avatar alt="Chatbot" color="primary" className={classes.orangeAvatar} style={{float:'right'}}> D</Avatar>
        <Box boxShadow={2} className={classes.textHuman} style={{float:'right',backgroundColor:'#26a69a', color:'white'}}>

          <Typography variant="body1" >
            {props.text}
         
          </Typography>
        
        </Box>
      </div>
    );
  }


  return (
    <React.Fragment>

       {message}
       </React.Fragment>
    );
}

export default Message;