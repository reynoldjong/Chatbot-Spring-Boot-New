import React, { useEffect, useState } from "react";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import axios from "axios";
import qs from "qs";
import Navbar from "../Navbar/Navbar";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import DocumentsTable from './DocumentsTable/DocumentsTable';
const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2)
  }
}));

/**
 * Compoenent representing all the documents the admin has uploaded
 * and has the abillity for admins to uplaod or remove documents
 * @param {props} props:
 *  @param {list} feedback - a list containing every file in Files.db
 *  @param {function} addFileHandler - function which adds a file to the database
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const AdminCrawler = props => {
  // when component is first loaded we should load all the files in the database

  const classes = useStyles();

  const [crawledStatus, setCrawledStatus] = React.useState("");
  const [sites, setSites] = React.useState([]);

  const crawl = async event => {
    // Create JSON object
    const url = event.target.url.value;
    const depth = event.target.depth.value;
    if (url === null) {
      url = "";
    }

    if (event.target.depth.value === null) {
      depth = 0;
    }

    event.preventDefault();
    const data = {
      url: url,
      depth: depth
    };

    console.log(data);

    await axios
      .post("/webcrawler", qs.stringify(data))
      .then(response => {
        setCrawledStatus("Successfully crawled " + url);
      })
      .catch(function(error) {
        setCrawledStatus("An error has occured for " + url);
        console.log(error);
      });
  };

  const getCrawledSites = async () =>{
      console.log('hello');
    await axios
      .get("/webcrawler")
      .then(response => {
          console.log(response)
        //let listSites = []
        //for(var key in response['date']){
         //   listSites.push(response['data'][key])
        //}
        setSites(response['data']['links']);
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  useEffect(() => {
    getCrawledSites();
  }, []);

  document.body.style = "background: rgba(0,0,0,0.05);";
  let admin = null;
  if (props.loggedIn) {
    admin = (
      <React.Fragment>
        <Navbar logOutHandler={props.logOutHandler} />

        <Box marginTop={3} marginBottom={3}>
          <Container maxWidth="md">
            <Paper className={classes.root}>
              <Typography variant="h4" component="h4" align="left">
                Crawl Websites To Help The Chatbot Learn
              </Typography>
              <Typography
                variant="body1"
                component="p"
                align="left"
                style={{ margin: "10px" }}
              >
                Input a URL and the Number of pages and let our web crawler deal
                with the rest.
              </Typography>
              <form onSubmit={crawl}>
                <Grid container spacing={0}>
                  <Grid item xs={12}>
                    <TextField
                      id="standard-name"
                      name="url"
                      label="URL"
                      margin="normal"
                      style={{ width: "50%" }}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      id="standard-uncontrolled"
                      name="depth"
                      label="Depth"
                      margin="normal"
                      style={{ width: "50%" }}
                    />
                  </Grid>
                </Grid>

                <Button
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
                  Crawl Website
                </Button>
              </form>
              <Typography>{crawledStatus}</Typography>
            </Paper>
          </Container>
        </Box>


        
      <Box marginTop={3} marginBottom={3}>
        <Container maxWidth="md">
          <Paper className={classes.root}>
            <Typography variant="h4" component="h4" align="left">
              What's In The Chatbot's Brain?
            </Typography>
            <Typography
              variant="body1"
              component="p"
              align="left"
              style={{ margin: "10px" }}
            >
              Take a look at what websites the chatbot is thinking about
            </Typography>
          

            <DocumentsTable
              sites={sites}
            />
          </Paper>
        </Container>
      </Box>
      </React.Fragment>
    );
  } else {
    admin = <h2>Not Logged in</h2>;
  }
  return <React.Fragment>{admin}</React.Fragment>;
};

export default AdminCrawler;
