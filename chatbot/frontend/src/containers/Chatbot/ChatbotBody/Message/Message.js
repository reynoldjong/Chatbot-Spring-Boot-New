import React from 'react';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import robot from './images/robot.png';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { relative } from 'path';
         //<Avatar alt="Chatbot" color="primary" className={classes.orangeAvatar} style={{float:'right'}}> D</Avatar>
        // borderTopRightRadius: 0,
const useStyles = makeStyles({
  root:{
    display:'flex',
 
  },
  avatar: {
    margin: 10,
    flexGrow:1,
    bottom:0,
    position:'relative',
  
    float:'left',
    bottom:'0px',
    
    
 
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
    width: '100%',
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
        <Grid item xs={3}>
        <Avatar alt="Chatbot" src={robot} className={classes.avatar} />
        </Grid>
        <Grid item xs={9}>
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