import React from 'react';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import classes from './Message.module.css';
         //<Avatar alt="Chatbot" color="primary" className={classes.orangeAvatar} style={{float:'right'}}> D</Avatar>
        // borderTopRightRadius: 0,

const Message = (props) => {

  let message = null;
  let rootClasses = null;

  if (props.showing){
    rootClasses = classes.root;
   
  }
  else{
    rootClasses = classes.root + ' ' + classes.OpacityLow;
  }
  if (props.type === 'bot') {
    message = (

     
     
            
<Grid container spacing={1} className={rootClasses} onClick={props.showClickHandler}>
        <Grid item xs={2} style={{marginRight:'0px'}}>
   
         
         <div className={classes.avatar2} ></div>
     
 
        </Grid>
        <Grid item xs={10}>
          <p style={{textAlign:'left',fontWeight:'500', color:'#424242', fontSize:'0.8em',letterSpacing:'0.0.8em', marginBottom:'5px'}}>DFI Chatbot</p>
        <Box boxShadow={1} style={{border:'1px solid rgba(0,0,0,0.03)'}}className={classes.text + ' ' + classes.textBot}>

          <p style={{fontSize:'1.1em', padding:'0px', marginTop:'0px', marginBottom:'0px'}} >
            {props.text}
            { props.link?<a href={props.link} style={{display:'block'}}>{props.link}</a>:null} </p>
         
          {
              
               props.picture? <img src={props.picture}  width="100px" height="100px" alt="DFI visual" />:null
              
          }
        </Box>
        </Grid></Grid>

      
      
    );
  }

  else{
    message = (
      <div className={rootClasses} onClick={props.showClickHandler}>
        <Grid container spacing={1}>
        <Grid item xs={12}>

         </Grid>
         <Grid item xs={12}>
        <Box boxShadow={1} style={{border:'1px solid rgba(0,0,0,0.03)'}} className={classes.textHuman + ' ' + classes.text} style={{float:'right',backgroundColor:'#26a69a', color:'white'}}>

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