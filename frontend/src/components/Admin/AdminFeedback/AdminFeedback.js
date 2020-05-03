import React, { useEffect } from "react";
import { Redirect } from "react-router-dom";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import axios from "axios";
import Navbar from "../Navbar/Navbar";
import FeedbackTable from './FeedbackTable/FeedbackTable';
import RatingTable from './RatingTable/RatingTable';
import auth from '../../../auth/auth';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2)
  },
  dragAndDrop: {
    width: "100%",
    height: "300px",
    backgroundColor: "rgba(0,0,0,0.1)",
    border: "1px solid #ccc",
    padding: theme.spacing(2),
    borderRadius: "5px"
  }
}));

/**
 * Component representing a visual on all the feedback a user has uploaded
 * @param {props} props:
 *  @param {list} feedback - a list containing every file in Files.db
 */
const AdminFeedback = props => {

  const user = auth.getUser()


  
  /*
  const [allFeedback, setFeedback] = React.useState({
    feedback: []
  });
  */
  const [feedback, setFeedback] = React.useState([]);
  const [answerRating, setAnswerRating] = React.useState([]);
  const [average, setAverage] = React.useState([]);

  const viewAllFeedback = async () =>{
    await axios
      .get("/feedback")
      .then(response => {
          console.log(response)
        //let listSites = []
        //for(var key in response['date']){
         //   listSites.push(response['data'][key])
        //}
        setFeedback(response['data']['feedback']);
        setAverage(response['data']['average']);
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  const viewAllAnswerRating = async () =>{
    await axios
      .get("/rating")
      .then(response => {
          console.log(response)
        //let listSites = []
        //for(var key in response['date']){
         //   listSites.push(response['data'][key])
        //}
        setAnswerRating(response['data']);
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  const getCsvOfFeedback = async () => {
    // Create JSON object

    await axios
      .get("/feedback/exportCSV")
      .then(response => {

        console.log(response);
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "feedbackData.csv"); //or any other extension
        document.body.appendChild(link);
        link.click();
      })
      .catch(function(error) {
        console.log(error);
      });
  };


  const getCsvOfRating = async () => {
    // Create JSON object
    await axios
      .get("/rating/exportCSV")
      .then(response => {

        console.log(response);
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "ratingData.csv"); //or any other extension
        document.body.appendChild(link);
        link.click();
      })
      .catch(function(error) {
        console.log(error);
      });
  };

    // when component is first loaded we should load all the files in the database
    useEffect(() => {
      viewAllFeedback();
      viewAllAnswerRating();
    }, []);
    const classes = useStyles();

    if (!user) {
      return <Redirect to="/" />
    }
  

  document.body.style = "background: rgba(0,0,0,0.05);";
  return (
      <React.Fragment>
        <Navbar logOutHandler={props.logOutHandler} />

        <Box marginTop={3} marginBottom={3}>
          <Container maxWidth="md">
            <Paper className={classes.root}>
              <Typography variant="h4" component="h4" align="left">
                Current Average Rating: {average}
              </Typography>
              <Typography
                variant="body1"
                component="p"
                align="left"
                style={{ margin: "10px" }}
              >
                Take a look at what people are saying about DFI's Chatbot!
              </Typography>
              <Button
                onClick={getCsvOfFeedback}
                type="submit"
                color="secondary"
                variant="contained"
                style={{
                  display: "block",
                  marginTop: "20px",
                  position: "relative",
                  marginLeft: "auto",
                  marginBottom: "20px"
                }}
              >
                export feedback to csv
              </Button>
                <FeedbackTable
                  feedback={feedback}
                />
            </Paper>
          </Container>
        </Box>
            <Box marginTop={3} marginBottom={3}>
              <Container maxWidth="md">
                <Paper className={classes.root}>
                  <Typography variant="h4" component="h4" align="left">
                    How Is The Chatbot Performing?
                  </Typography>
                  <Typography
                    variant="body1"
                    component="p"
                    align="left"
                    style={{ margin: "10px" }}
                  >
                    Take a look at what people are picky about Chatbot's answers!
                  </Typography>
                  <Button
                    onClick={getCsvOfRating}
                    type="submit"
                    color="secondary"
                    variant="contained"
                    style={{
                      display: "block",
                      marginTop: "20px",
                      position: "relative",
                      marginLeft: "auto",
                      marginBottom: "20px"
                    }}
                  >
                    export rating to csv
                  </Button>
                    <RatingTable
                      answerRating={answerRating}
                    />
                </Paper>
              </Container>
            </Box>
      </React.Fragment>
    );
};

export default AdminFeedback;
