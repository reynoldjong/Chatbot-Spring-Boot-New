import React from 'react';
import Typography from '@material-ui/core/Typography';
import ThumbUp from '@material-ui/icons/ThumbUp';
import ThumbDown from '@material-ui/icons/ThumbDown';
import Box from '@material-ui/core/Box';
import axios from 'axios';
import classes from './Rating.module.css';
import Grid from "@material-ui/core/Grid";
import qs from 'qs';

/**
 * Component represents a rating for a message coming back from watson or lucene
 * @param props
 *  {string, [string]} message- Message from either watson or lucene
 */
const BotRating = (props) => {
  // Feedback can only be submitted once so this bool is used to track whether
  // feedback has been submitted or not
  const [submittedFeedback, setSubmittedFeedback] = React.useState(false);

  const [good, setGood] = React.useState(false);
  const [bad, setBad] = React.useState(false);
  /**
   * styles are used as a visual cue to the user indicating that they have
   * rated the question
   */
  let goodStyling= null;
  let badStyling = null;
  // if the user rated the question as good then the color is green and  red for bad
  if(good){
    goodStyling = {color:'green'};
  }
  if(bad){
    badStyling = {color:'red'};
  }
  /**
   * Function is used to send a rating to the back end which is recorded in the database
   * along with the message
   * @param {string} rating - Either "Good" or "Bad"
   */
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