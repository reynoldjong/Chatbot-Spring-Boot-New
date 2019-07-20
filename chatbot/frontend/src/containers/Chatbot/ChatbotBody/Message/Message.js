import React from 'react';
import Box from '@material-ui/core/Box';
import Avatar from '@material-ui/core/Avatar';
import robot from './images/robot.png';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import classes from './Message.module.css';
         //<Avatar alt="Chatbot" color="primary" className={classes.orangeAvatar} style={{float:'right'}}> D</Avatar>
        // borderTopRightRadius: 0,

const Message = (props) => {

  let message = null;

  if (props.type === 'bot') {
    message = (

     
     
            
<Grid container spacing={1} className={classes.root}>
        <Grid item xs={2}>
   
          <Avatar alt="Chatbot" src={robot} className={classes.avatar} style={{}} />
         
     
 
        </Grid>
        <Grid item xs={10}>
          <p style={{textAlign:'left'}}>DFI Chatbot</p>
        <Box boxShadow={2} className={classes.text + ' ' + classes.textBot}>

          <Typography variant="body1"  style={{fontSize:'1.1em'}}>
            {props.text}
            {
              
              props.link?<a href={props.link} style={{display:'block'}}>{props.link}</a>:null
             
         }
          </Typography>
         
          {
              
               props.picture? <img src={props.picture}  width="100px" height="100px" alt="DFI visual" />:null
              
          }
        </Box>
        </Grid></Grid>

      
      
    );
  }

  else{
    message = (
      <div className={classes.root}>
        <Grid container spacing={1}>
        <Grid item xs={12}>

         </Grid>
         <Grid item xs={12}>
        <Box boxShadow={2} className={classes.textHuman + ' ' + classes.text} style={{float:'right',backgroundColor:'#26a69a', color:'white'}}>

          <p style={{fontSize:'1.1em', padding:'0px', marginTop:'0px', marginBottom:'0px'}}>
            {props.text}
         
          </p>
        
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