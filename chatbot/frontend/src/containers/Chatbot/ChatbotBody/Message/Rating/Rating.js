import React from 'react';
import Rating from '@material-ui/lab/Rating';
import Typography from '@material-ui/core/Typography';
import ThumbUp from '@material-ui/icons/ThumbUp';
import ThumbDown from '@material-ui/icons/ThumbDown';
import Box from '@material-ui/core/Box';
import axios from 'axios';
import classes from './Rating.module.css';
import Grid from "@material-ui/core/Grid";
import qs from 'qs';
const BotRating = (props) => {
    
  const [submittedFeedback, setSubmittedFeedback] = React.useState(false);

  const [good, setGood] = React.useState(false);
  const [bad, setBad] = React.useState(false);
  let goodStyling= null;
  let badStyling = null;

  if(good){
    goodStyling = {color:'green'};
  }
  if(bad){
    badStyling = {color:'red'};
  }
  const submitFeedback = async (rating) =>{
    if(submittedFeedback === false){
      let message = "";
    if (Array.isArray(props.message)){
      props.message.forEach(function(word){
        message += word + " ";
      });
    }
    else{
      message = props.message;
    }
  
    if(submittedFeedback === false){
      const data = {
        message: message,
        answerRating: rating
      }
   
    await axios
    .post("/answerrating", qs.stringify(data))
    .then(response => {
    
      setSubmittedFeedback(true);
      // Update thumbs up or down with styling for visual feedback
      if(rating === "Good"){
        setGood(true);
      }
      else{
        setBad(true);
      }
    })
    .catch(function(error) {
      console.log(error);
    });
  }

  }
}

  return (
    <div>
      <Box component="fieldset" mb={3} mt={1} mr={8} borderColor="transparent">
        <Typography component="legend" align="right" variant="body1">How did I do?
        <Grid container spacing={0}>
        <Grid item xs={6}>
         <ThumbDown className={classes.Rating} onClick={()=>submitFeedback("Bad") } style={badStyling}/>
        </Grid>
        <Grid item xs={6}>
        <ThumbUp className={classes.Rating} onClick={()=>submitFeedback("Good") }style={goodStyling}/> 
        </Grid>
        </Grid>
        </Typography>
      </Box>
     </div>
  );
}

export default BotRating;