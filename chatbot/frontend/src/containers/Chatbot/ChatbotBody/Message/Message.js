import React from 'react';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import robot from './images/robot.png';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import { deepOrange, deepPurple } from '@material-ui/core/colors';
import Grid from '@material-ui/core/Grid';
const useStyles = makeStyles({
  root:{
    display:'flex',
 
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
    display:'block',
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

<Grid container spacing={1}>
        <Grid item xs={12}>
        <Avatar alt="Chatbot" src={robot} className={classes.avatar} />
        </Grid>
        <Grid item xs={12}>
        <Box boxShadow={2} className={classes.textBot}>

          <Typography variant="body1" >
            {props.text}
            {
              
              props.link?<a href={props.link} style={{display:'block'}}>{props.link}</a>:null
             
         }
          </Typography>
         
          {
              
               props.picture? <img src={props.picture}  width="200px" height="200px" alt="DFI visual" />:null
              
          }
         
     
          
        </Box>
        </Grid></Grid>
        </div>
      
    );
  }

  else{
    message = (
      <div className={classes.root}>
        <Grid container spacing={1}>
        <Grid item xs={12}>
         <Avatar alt="Chatbot" color="primary" className={classes.orangeAvatar} style={{float:'right'}}> D</Avatar>
         </Grid>
         <Grid item xs={12}>
        <Box boxShadow={2} className={classes.textHuman} style={{float:'right',backgroundColor:'#26a69a', color:'white'}}>

          <Typography variant="body1" >
            {props.text}
         
          </Typography>
        
        </Box>
        </Grid>
      </Grid>
      
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